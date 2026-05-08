using System.Windows;
using CSharpLab_3.services;
using Shared.models;
using Shared.services;
using Shared.utils;

namespace CSharpLab_3.views;

public partial class MainWindow : Window, IObserver
{
    private readonly IMainService _mainService;

    public MainWindow(IMainService service)
    {
        InitializeComponent();
        _mainService = service;
        service.AddObserver(this);
        Load();
    }

    private void Load()
    {
        CoursesTable.ItemsSource = _mainService.GetAllRides();
    }

    public void Update()
    {
        Dispatcher.Invoke(Load);
    }
    

    private void HandleSearch(object sender, RoutedEventArgs e)
    {
        NavigationService.OpenSearchForm(_mainService);
    }

    private void HandleModify(object sender, RoutedEventArgs e)
    {   
       
        NavigationService.OpenModifyWindow(_mainService);
    }

    private void HandleReservation(object sender, RoutedEventArgs e)
    {
        if (CoursesTable.SelectedItem is not Ride ride)
        {
            MessageBox.Show("Please select a ride", "No selection", MessageBoxButton.OK, MessageBoxImage.Information);
            return;
        }
        NavigationService.OpenReservationForm(_mainService, ride.Id);
    }

    private void HandleLogout(object sender, RoutedEventArgs e) => Close();

}