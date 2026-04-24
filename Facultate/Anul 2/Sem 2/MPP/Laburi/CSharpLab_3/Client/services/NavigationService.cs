
using CSharpLab_3.network;
using CSharpLab_3.views;
using Shared.models;
using Shared.services;
namespace CSharpLab_3.services;


public static class NavigationService
{

    
    public static void OpenMainWindow()
    {
        
        var window = new MainWindow(ServiceLocator.Proxy);
        window.Show();
    }

    public static void OpenModifyWindow(IMainService mainService)
    {
        var window = new ModifyWindow(mainService);
        window.Show();
    }

    public static void OpenReservationForm(IMainService mainService, long rideId)
    {
        var window = new ReservationFormWindow(mainService, rideId);
        window.Show();
    }

    public static void OpenSearchForm(IMainService mainService)
    {
        var window = new SearchFormWindow(mainService);
        window.Show();
    }
}