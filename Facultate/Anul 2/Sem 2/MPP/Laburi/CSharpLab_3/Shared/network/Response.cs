
using System.Text.Json.Serialization;

namespace Shared.network;

public class Response
{
    [JsonInclude]
    public ResponseType Type { get; private set; }

    [JsonInclude]
    public object? Data { get; private set; }

    [JsonInclude]
    public string? Message { get; private set; }

    public Response(ResponseType type, object? data, string? message)
    {
        Type = type;
        Data = data;
        Message = message;
    }

    public static Response Error(string message)
    {
        return new Response(ResponseType.Error, null, message);
    }
}