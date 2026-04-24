using System.Windows;
using Shared.services;

namespace CSharpLab_3.views;

public partial class ReservationFormWindow : Window
{
    private readonly IMainService _mainService;
    private readonly long _rideId;

    public ReservationFormWindow(IMainService mainService, long rideId)
    {
        InitializeComponent();
        _mainService = mainService;
        _rideId = rideId;
    }

    private void HandleConfirm(object sender, RoutedEventArgs e)
    {
        if (string.IsNullOrEmpty(ClientNameField.Text) || string.IsNullOrEmpty(NoSeatsField.Text))
        {
            MessageBox.Show("Please fill in all fields", "Invalid input", MessageBoxButton.OK, MessageBoxImage.Information);
            return;
        }
        var noSeats = int.Parse(NoSeatsField.Text);
        try
        {
            _mainService.MakeReservation(_rideId, ClientNameField.Text, noSeats);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message, "Error", MessageBoxButton.OK, MessageBoxImage.Error);
        }

        Close();
    }
}