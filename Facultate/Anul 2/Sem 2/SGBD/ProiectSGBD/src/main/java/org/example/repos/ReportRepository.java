package org.example.repos;

import org.example.dtos.EmployeeSaleReportDTO;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class ReportRepository {

    public List<EmployeeSaleReportDTO> getEmployeeSalesReport() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNativeQuery("""
                SELECT 
                    E.employee_name, 
                    E.department, 
                    COUNT(DISTINCT O.customer_id) AS noCustomers, 
                    SUM(P.price * OI.quantity * (1 - OI.discount / 100)) AS revenue, 
                    AVG(OI.discount) AS avgDiscount
                FROM Employees E
                JOIN Orders O ON E.employee_id = O.employee_id
                JOIN Order_Items OI ON O.order_id = OI.order_id
                JOIN Products P ON P.product_id = OI.product_id
                GROUP BY E.employee_id, E.employee_name, E.department
                HAVING revenue > 5000 AND COUNT(O.order_id) > 3
                ORDER BY revenue DESC
            """, Object[].class).getResultList().stream()
                    .map(row -> new EmployeeSaleReportDTO(
                            (String) row[0],
                            (String) row[1],
                            ((Number) row[2]).longValue(),
                            ((Number) row[3]).doubleValue(),
                            ((Number) row[4]).doubleValue()
                    ))
                    .toList();
        }
    }


}