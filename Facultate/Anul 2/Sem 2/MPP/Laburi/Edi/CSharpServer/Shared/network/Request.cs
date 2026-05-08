using System.Text.Json.Serialization;

namespace Shared.network;

public class Request
{
    [JsonInclude]
    public RequestType Type { get; private set; }

    [JsonInclude]
    public object? Data { get; private set; }

    public Request(RequestType type, object? data)
    {
        Type = type;
        Data = data;
    }
}