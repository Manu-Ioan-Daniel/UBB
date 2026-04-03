namespace CSharpLab3.exceptions;

public class ServiceException : Exception
{
    public ServiceException(string message) : base(message) { }
}