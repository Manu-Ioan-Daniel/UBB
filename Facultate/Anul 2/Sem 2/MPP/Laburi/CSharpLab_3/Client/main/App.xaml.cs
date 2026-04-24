using System.Windows;
using CSharpLab_3.network;
using CSharpLab_3.views;

namespace CSharpLab_3.main;

public partial class App
{
    protected override void OnStartup(StartupEventArgs e)
    {
        base.OnStartup(e);
        
        var login = new LoginWindow(ServiceLocator.Proxy);
        login.Show();
    }
}