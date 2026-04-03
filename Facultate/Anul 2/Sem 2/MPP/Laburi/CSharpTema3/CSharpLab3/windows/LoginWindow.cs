
using System.Windows.Media;
using CSharpLab3.services;
using CSharpLab3.utils;

namespace CSharpLab3.windows;

public partial class LoginWindow : Window
{
    private readonly AuthService _authService;

    public LoginWindow(AuthService authService)
    {
        InitializeComponent();
        _authService = authService;
    }

    private void HandleLogin(object sender, RoutedEventArgs e)
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
}