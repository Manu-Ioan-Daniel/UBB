namespace Shared.services;

public interface IAuthService
{
    public bool Authenticate(string username, string password);
}