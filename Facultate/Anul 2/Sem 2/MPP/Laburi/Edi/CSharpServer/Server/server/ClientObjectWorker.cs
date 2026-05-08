using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
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

    private NetworkStream? _stream;
    private StreamReader? _reader;
    private StreamWriter? _writer;

    private volatile bool _connected = true;

    private static readonly JsonSerializerOptions JsonOptions = new()
    {
        PropertyNameCaseInsensitive = true,
        Converters = { new JsonStringEnumConverter() }
    };

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

                var request = JsonSerializer.Deserialize<Request>(json, JsonOptions);

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
                RequestType.Login => HandleLogin(request.Data!),

                RequestType.GetAllRides =>
                    new Response(ResponseType.RidesFound,
                        _mainService.GetAllRides(), null),

                RequestType.FindRides => HandleFindRides(request.Data!),

                RequestType.MakeReservation => HandleMakeReservation(request.Data!),

                RequestType.CancelReservation => HandleCancelReservation(request.Data!),

                RequestType.GetRide =>
                    new Response(ResponseType.RideFound,
                        _mainService.GetRide(GetLongFromObject(request.Data)), null),

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

    private long GetLongFromObject(object? data)
    {
        if (data == null)
            throw new ArgumentException("Missing id value");

        if (data is JsonElement je)
        {
            if (je.ValueKind == JsonValueKind.Number)
            {
                return je.GetInt64();
            }

            var raw = je.GetRawText();

            if (long.TryParse(raw.Trim('"'), out var v))
                return v;

            try
            {
                return JsonSerializer.Deserialize<long>(raw, JsonOptions);
            }
            catch
            {
                throw new ArgumentException($"Cannot parse numeric id from JsonElement: {raw}");
            }
        }

        if (data is long l)
            return l;

        if (data is int i)
            return i;

        if (data is string s && long.TryParse(s, out var parsed))
            return parsed;

        try
        {
            return Convert.ToInt64(data);
        }
        catch (Exception ex)
        {
            throw new ArgumentException($"Cannot convert request data to long: {ex.Message}", ex);
        }
    }

    private static string ExtractJsonText(object? data)
    {
        if (data is JsonElement elem)
            return elem.GetRawText();

        return data?.ToString() ?? string.Empty;
    }

    private Response HandleLogin(object? data)
    {
        try
        {
            string dataStr = ExtractJsonText(data);
            var dto = JsonSerializer.Deserialize<LoginDTO>(dataStr, JsonOptions);

            if (dto == null)
            {
                Console.WriteLine("[SERVER] LOGIN: DTO is null after deserialization");
                return Response.Error("Invalid login data");
            }

            Console.WriteLine($"[SERVER] LOGIN attempt: {dto.Username}");

            var ok = _authService.Authenticate(dto.Username, dto.Password);

            Console.WriteLine($"[SERVER] LOGIN result: {ok}");

            return ok
                ? new Response(ResponseType.AuthSuccess, null, null)
                : Response.Error("Invalid credentials");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"[SERVER] LOGIN ERROR: {ex.Message}");
            return Response.Error($"Login error: {ex.Message}");
        }
    }

    private Response HandleFindRides(object? data)
    {
        try
        {
            string dataStr = ExtractJsonText(data);
            var arr = JsonSerializer.Deserialize<string[]>(dataStr, JsonOptions);

            if (arr == null || arr.Length < 3)
                return Response.Error("Invalid find rides data");

            Console.WriteLine("[SERVER] FindRides request");

            var result = _mainService.FindRides(
                DateOnly.Parse(arr[0]),
                arr[1],
                arr[2]
            );

            Console.WriteLine($"[SERVER] FindRides result: {result.Count}");

            return new Response(ResponseType.RidesFound, result, null);
        }
        catch (Exception ex)
        {
            Console.WriteLine($"[SERVER] FindRides ERROR: {ex.Message}");
            return Response.Error($"FindRides error: {ex.Message}");
        }
    }

    private Response HandleMakeReservation(object? data)
    {
        try
        {
            string dataStr = ExtractJsonText(data);
            var dto = JsonSerializer.Deserialize<MakeReservationDTO>(dataStr, JsonOptions);

            if (dto == null)
                return Response.Error("Invalid reservation data");

            Console.WriteLine($"[SERVER] MakeReservation: ride={dto.RideId}, seats={dto.Seats}");

            _mainService.MakeReservation(dto.RideId, dto.ClientName, dto.Seats);

            Console.WriteLine("[SERVER] Reservation saved -> notifying clients");

            NotifyAllClients();

            return new Response(ResponseType.ReservationMade, null, null);
        }
        catch (Exception ex)
        {
            Console.WriteLine($"[SERVER] MakeReservation ERROR: {ex.Message}");
            return Response.Error($"MakeReservation error: {ex.Message}");
        }
    }

    private Response HandleCancelReservation(object? data)
    {
        try
        {
            string dataStr = ExtractJsonText(data);
            var dto = JsonSerializer.Deserialize<CancelReservationDTO>(dataStr, JsonOptions);

            if (dto == null)
                return Response.Error("Invalid cancellation data");

            Console.WriteLine($"[SERVER] CancelReservation: {dto.ReservationId}");

            _mainService.CancelReservation(dto.ReservationId);

            Console.WriteLine("[SERVER] Reservation cancelled -> notifying clients");

            NotifyAllClients();

            return new Response(ResponseType.ReservationCanceled, null, null);
        }
        catch (Exception ex)
        {
            Console.WriteLine($"[SERVER] CancelReservation ERROR: {ex.Message}");
            return Response.Error($"CancelReservation error: {ex.Message}");
        }
    }

    private void Send(Response response)
    {
        if (_writer == null)
            throw new InvalidOperationException("Writer is not initialized");

        var json = JsonSerializer.Serialize(response, JsonOptions);
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

        try { _reader?.Close(); } catch (Exception ex) { Console.WriteLine("[SERVER] Reader close error: " + ex.Message); }
        try { _writer?.Close(); } catch (Exception ex) { Console.WriteLine("[SERVER] Writer close error: " + ex.Message); }
        try { _stream?.Close(); } catch (Exception ex) { Console.WriteLine("[SERVER] Stream close error: " + ex.Message); }
        try { _socket.Close(); } catch (Exception ex) { Console.WriteLine("[SERVER] Socket close error: " + ex.Message); }

        Console.WriteLine("[SERVER] Connection closed");
    }
}

