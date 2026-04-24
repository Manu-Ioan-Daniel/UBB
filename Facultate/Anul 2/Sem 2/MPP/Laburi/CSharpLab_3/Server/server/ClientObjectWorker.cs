using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using Shared.dtos;
using Shared.network;
using Shared.services;
using Server.factories;

namespace Server.server;

public class ClientObjectWorker
{
    private readonly IMainService _mainService;
    private readonly IAuthService _authService;
    private readonly Socket _socket;

    private NetworkStream _stream;
    private StreamReader _reader;
    private StreamWriter _writer;

    private volatile bool _connected = true;

    private static readonly object LockObj = new();
    private static readonly List<ClientObjectWorker> Clients = new();

    public ClientObjectWorker(Socket socket)
    {
        _socket = socket;

        _mainService = ServiceFactory.Instance.GetMainService();
        _authService = ServiceFactory.Instance.GetAuthService();

        lock (LockObj)
        {
            Clients.Add(this);
            Console.WriteLine($"[SERVER] Client added. Total clients: {Clients.Count}");
        }
    }

    public void Run()
    {
        try
        {
            Console.WriteLine("[SERVER] Worker started");

            _stream = new NetworkStream(_socket);
            _reader = new StreamReader(_stream, Encoding.UTF8);
            _writer = new StreamWriter(_stream, Encoding.UTF8) { AutoFlush = true };

            while (_connected)
            {
                var json = _reader.ReadLine();

                if (json == null)
                {
                    Console.WriteLine("[SERVER] Client disconnected (null read)");
                    break;
                }

                Console.WriteLine($"[SERVER] RAW REQUEST: {json}");

                var request = JsonSerializer.Deserialize<Request>(json);

                if (request == null)
                {
                    Console.WriteLine("[SERVER] Invalid request");
                    Send(Response.Error("Invalid request"));
                    continue;
                }

                Console.WriteLine($"[SERVER] REQUEST TYPE: {request.Type}");

                var response = HandleRequest(request);

                Console.WriteLine($"[SERVER] RESPONSE TYPE: {response.Type}");

                Send(response);
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine("[SERVER] ERROR: " + ex.Message);
        }
        finally
        {
            Close();
        }
    }

    private Response HandleRequest(Request request)
    {
        try
        {
            return request.Type switch
            {
                RequestType.Login => HandleLogin(request.Data),

                RequestType.GetAllRides =>
                    new Response(ResponseType.RidesFound,
                        _mainService.GetAllRides(), null),

                RequestType.FindRides => HandleFindRides(request.Data),

                RequestType.MakeReservation => HandleMakeReservation(request.Data),

                RequestType.CancelReservation => HandleCancelReservation(request.Data),

                RequestType.GetRide =>
                    new Response(ResponseType.RideFound,
                        _mainService.GetRide((long)request.Data!), null),

                RequestType.FindReservations =>
                    new Response(ResponseType.ReservationsFound,
                        _mainService.FindReservations(), null),

                _ => Response.Error("Unknown request")
            };
        }
        catch (Exception ex)
        {
            Console.WriteLine("[SERVER] HandleRequest ERROR: " + ex.Message);
            return Response.Error(ex.Message);
        }
    }

    private Response HandleLogin(object data)
    {
        var dto = JsonSerializer.Deserialize<LoginDTO>(data.ToString()!);

        Console.WriteLine($"[SERVER] LOGIN attempt: {dto.Username}");

        var ok = _authService.Authenticate(dto.Username, dto.Password);

        Console.WriteLine($"[SERVER] LOGIN result: {ok}");

        return ok
            ? new Response(ResponseType.AuthSuccess, null, null)
            : Response.Error("Invalid credentials");
    }

    private Response HandleFindRides(object data)
    {
        var arr = JsonSerializer.Deserialize<string[]>(data.ToString()!);

        Console.WriteLine("[SERVER] FindRides request");

        var result = _mainService.FindRides(
            DateOnly.Parse(arr[0]),
            arr[1],
            arr[2]
        );

        Console.WriteLine($"[SERVER] FindRides result: {result.Count}");

        return new Response(ResponseType.RidesFound, result, null);
    }

    private Response HandleMakeReservation(object data)
    {
        var dto = JsonSerializer.Deserialize<MakeReservationDTO>(data.ToString()!);

        Console.WriteLine($"[SERVER] MakeReservation: ride={dto.RideId}, seats={dto.Seats}");

        _mainService.MakeReservation(dto.RideId, dto.ClientName, dto.Seats);

        Console.WriteLine("[SERVER] Reservation saved -> notifying clients");

        NotifyAllClients();

        return new Response(ResponseType.ReservationMade, null, null);
    }

    private Response HandleCancelReservation(object data)
    {
        var dto = JsonSerializer.Deserialize<CancelReservationDTO>(data.ToString()!);

        Console.WriteLine($"[SERVER] CancelReservation: {dto.ReservationId}");

        _mainService.CancelReservation(dto.ReservationId);

        Console.WriteLine("[SERVER] Reservation cancelled -> notifying clients");

        NotifyAllClients();

        return new Response(ResponseType.ReservationCanceled, null, null);
    }

    private void Send(Response response)
    {
        var json = JsonSerializer.Serialize(response);
        Console.WriteLine($"[SERVER] SEND -> {response.Type}");
        _writer.WriteLine(json);
    }

    private void NotifyAllClients()
    {
        lock (LockObj)
        {
            Console.WriteLine($"[SERVER] NotifyAllClients -> {Clients.Count} clients");

            foreach (var client in Clients.ToList())
            {
                try
                {
                    client.Send(new Response(ResponseType.UpdateNotification, null, null));
                }
                catch (Exception ex)
                {
                    Console.WriteLine("[SERVER] Removing dead client: " + ex.Message);
                    Clients.Remove(client);
                }
            }
        }
    }

    private void Close()
    {
        _connected = false;

        lock (LockObj)
        {
            Clients.Remove(this);
            Console.WriteLine($"[SERVER] Client removed. Remaining: {Clients.Count}");
        }

        try { _reader?.Close(); } catch { }
        try { _writer?.Close(); } catch { }
        try { _stream?.Close(); } catch { }
        try { _socket?.Close(); } catch { }

        Console.WriteLine("[SERVER] Connection closed");
    }
}