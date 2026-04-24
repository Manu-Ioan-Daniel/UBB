using System.Windows;
using Shared.services;

namespace CSharpLab_3.views;

public partial class SearchFormWindow : Window
{
    private readonly IMainService _mainService;

    public SearchFormWindow(IMainService mainService)
    {
        InitializeComponent();
        _mainService = mainService;
    }

    private void HandleSearch(object sender, RoutedEventArgs e)
    {
        if (DatePicker.SelectedDate == null || string.IsNullOrEmpty(DepartureHourField.Text) || string.IsNullOrEmpty(DestinationField.Text))
        {
            MessageBox.Show("Please fill in all fields", "Invalid input", MessageBoxButton.OK, MessageBoxImage.Error);
            return;
        }
        try
        {
            var date = DateOnly.FromDateTime(DatePicker.SelectedDate.Value);
            SeatsTable.ItemsSource = _mainService.FindRides(date, DepartureHourField.Text, DestinationField.Text);
        }
        catch (Exception ex)
        {
            MessageBox.Show(ex.Message, "Search failed", MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }
}