using System.IO;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Windows;
using Shared.network;
using Shared.services;
using Shared.dtos;
using Shared.models;
using Shared.utils;

namespace CSharpLab_3.network;

public class ServicesObjectProxy : IMainService, IAuthService
{
    private readonly TcpClient _client;
    private readonly NetworkStream _stream;
    private readonly StreamReader _reader;
    private readonly StreamWriter _writer;

    private readonly List<IObserver> _observers = new();
    private readonly CancellationTokenSource _cts = new();

    private readonly object _sendLock = new();

    public ServicesObjectProxy(string host, int port)
    {
        _client = new TcpClient(host, port);
        _stream = _client.GetStream();

        _reader = new StreamReader(_stream, Encoding.UTF8);
        _writer = new StreamWriter(_stream, Encoding.UTF8) { AutoFlush = true };

        StartReader();
    }

    private void StartReader()
    {
        new Thread(() =>
        {
            while (!_cts.IsCancellationRequested)
            {
                var json = _reader.ReadLine();
                if (json == null) continue;

                var response = JsonSerializer.Deserialize<Response>(json);
                if (response == null) continue;

                if (response.Type == ResponseType.UpdateNotification)
                {
                    Application.Current?.Dispatcher?.BeginInvoke(() =>
                    {
                        foreach (var obs in _observers.ToList())
                            obs.Update();
                    });

                    continue;
                }

                lock (_pendingResponses)
                {
                    _pendingResponses.Add(response);
                    Monitor.PulseAll(_pendingResponses);
                }
            }
        })
        { IsBackground = true }.Start();
    }

    private readonly List<Response> _pendingResponses = new();

    private Response ReadResponse()
    {
        lock (_pendingResponses)
        {
            while (_pendingResponses.Count == 0)
                Monitor.Wait(_pendingResponses);

            var r = _pendingResponses[0];
            _pendingResponses.RemoveAt(0);
            return r;
        }
    }

    private Response SendAndWait(Request request)
    {
        lock (_sendLock)
        {
            var json = JsonSerializer.Serialize(request);
            _writer.WriteLine(json);

            var response = ReadResponse();

            if (response.Type == ResponseType.Error)
                throw new Exception(response.Message);

            return response;
        }
    }


    public bool Authenticate(string username, string password)
    {
        var resp = SendAndWait(
            new Request(RequestType.Login, new LoginDTO(username, password))
        );

        return resp.Type == ResponseType.AuthSuccess;
    }


    public List<Ride> GetAllRides()
    {
        var resp = SendAndWait(new Request(RequestType.GetAllRides, null));
        return JsonSerializer.Deserialize<List<Ride>>(resp.Data.ToString())!;
    }

    public List<RideDTO> FindRides(DateOnly date, string hour, string destination)
    {
        var resp = SendAndWait(new Request(RequestType.FindRides,
            new string[] { date.ToString(), hour, destination }));

        return JsonSerializer.Deserialize<List<RideDTO>>(resp.Data.ToString())!;
    }

    public void MakeReservation(long rideId, string clientName, int seats)
    {
        SendAndWait(new Request(RequestType.MakeReservation,
            new MakeReservationDTO(rideId, clientName, seats)));
    }

    public void CancelReservation(long id)
    {
        SendAndWait(new Request(RequestType.CancelReservation,
            new CancelReservationDTO(id)));
    }

    public Ride GetRide(long rideId)
    {
        var resp = SendAndWait(new Request(RequestType.GetRide, rideId));
        return JsonSerializer.Deserialize<Ride>(resp.Data.ToString())!;
    }

    public List<Reservation> FindReservations()
    {
        var resp = SendAndWait(new Request(RequestType.FindReservations, null));
        return JsonSerializer.Deserialize<List<Reservation>>(
            ((JsonElement)resp.Data).GetRawText()
        )!;
    }


    public void AddObserver(IObserver observer)
    {
        _observers.Add(observer);
    }

    public void RemoveObserver(IObserver observer)
    {
        _observers.Remove(observer);
    }
}