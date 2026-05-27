package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;
import org.example.dtos.EmployeeSaleReportDTO;
import org.example.services.MainService;

public class MainController {

    @Setter
    private MainService mainService;

    @FXML
    private TableColumn<EmployeeSaleReportDTO, Double> avgDiscountColumn;

    @FXML
    private TableColumn<EmployeeSaleReportDTO, String> departmentColumn;

    @FXML
    private TableColumn<EmployeeSaleReportDTO, String> nameColumn;

    @FXML
    private TableColumn<EmployeeSaleReportDTO, Long> noCustomersColumn;

    @FXML
    private TableColumn<EmployeeSaleReportDTO, Double> revenueColumn;

    @FXML
    private TableView<EmployeeSaleReportDTO> table1;

    public void init(){
        initializeTable();
    }

    private void initializeTable(){
        avgDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("avgDiscount"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        noCustomersColumn.setCellValueFactory(new PropertyValueFactory<>("noCustomers"));
        revenueColumn.setCellValueFactory(new PropertyValueFactory<>("revenue"));
        table1.setItems(FXCollections.observableList(mainService.getEmployeeSalesReport()));
    }

}
