
using CSharpLab3.factories;
using CSharpLab3.services;
using CSharpLab3.views;

namespace CSharpLab3.utils;

public static class NavigationService
{
    public static void OpenLoginWindow()
    {
        var authService = ServiceFactory.Instance.GetAuthService();
        var window = new LoginWindow(authService);
        window.Show();
    }

    public static void OpenMainWindow()
    {
        var mainService = ServiceFactory.Instance.GetMainService();
        var window = new MainWindow(mainService);
        window.Show();
    }

    public static void OpenModifyWindow(MainService mainService)
    {
        var window = new ModifyWindow(mainService);
        window.Show();
    }

    public static void OpenReservationForm(MainService mainService, long rideId)
    {
        var window = new ReservationFormWindow(mainService, rideId);
        window.Show();
    }

    public static void OpenSearchForm(MainService mainService)
    {
        var window = new SearchFormWindow(mainService);
        window.Show();
    }
}