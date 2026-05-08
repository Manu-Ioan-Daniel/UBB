using System.Windows;
using Shared.models;
using Shared.services;
using Shared.utils;

namespace CSharpLab_3.views;

public partial class ModifyWindow : Window, IObserver
{
    private readonly IMainService _mainService;

    public ModifyWindow(IMainService mainService)
    {
        InitializeComponent();
        _mainService = mainService;
        _mainService.AddObserver(this);
        LoadTable();
    }

    private void LoadTable()
    {
        ReservationsTable.ItemsSource = _mainService.FindReservations();

    }

    private void HandleCancelReservation(object sender, RoutedEventArgs e)
    {
        if (ReservationsTable.SelectedItem == null)
        {
            MessageBox.Show("Please select a reservation", "No selection", MessageBoxButton.OK, MessageBoxImage.Information);
            return;
        }
        dynamic selected = ReservationsTable.SelectedItem;
        _mainService.CancelReservation(selected.Id);
    }

    public void Update() => Dispatcher.Invoke(LoadTable);
}