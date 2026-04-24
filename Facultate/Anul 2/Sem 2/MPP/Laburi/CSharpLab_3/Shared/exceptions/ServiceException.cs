namespace Shared.exceptions;

public class ServiceException : Exception
{
    public ServiceException(string message) : base(message) { }
}