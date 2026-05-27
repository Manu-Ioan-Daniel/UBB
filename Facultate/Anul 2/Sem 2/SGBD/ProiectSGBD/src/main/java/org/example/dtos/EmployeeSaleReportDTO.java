package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeSaleReportDTO {
    private String employeeName;
    private String department;
    private Long noCustomers;
    private Double revenue;
    private Double avgDiscount;
}