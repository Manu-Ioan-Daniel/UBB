
using System.Windows;
using System.Windows.Media;
using CSharpLab_3.services;
using Shared.services;

namespace CSharpLab_3.views;

public partial class LoginWindow : Window
{
    private readonly IAuthService _authService;

    public LoginWindow(IAuthService authService)
    {
        InitializeComponent();
        _authService = authService;
    }

    private void HandleLogin(object sender, RoutedEventArgs e)
    {
        try
        {
            if (_authService.Authenticate(UsernameBox.Text, PasswordBox.Password))
            {
                NavigationService.OpenMainWindow();
                Close();
            }
            else
            {
                ErrorLabel.Text = "Invalid credentials";
                UsernameBox.BorderBrush = Brushes.Red;
                PasswordBox.BorderBrush = Brushes.Red;
            }
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message, "Login Crash");
        }
    }
}