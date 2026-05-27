package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.dtos.EmployeeSaleReportDTO;
import org.example.repos.ReportRepository;

import java.util.List;

@RequiredArgsConstructor
public class MainService {

    private final ReportRepository reportRepository;

    public List<EmployeeSaleReportDTO> getEmployeeSalesReport(){
        return reportRepository.getEmployeeSalesReport();
    }
}
