![[Cursuri - SGBD.pdf]]


```sql
SELECT C.customer_id, C.name, SUM(O.total_amount) AS total_spent, COUNT(DISTINCT O.order_id)
FROM Customers C
JOIN Orders O ON C.customer_id = O.customer_id
JOIN Order_Items OI ON OI.order_id = O.order_id
JOIN Products P ON P.product_id = OI.product_id
WHERE P.category = 'Electronics' AND C.country = 'Germania' AND 
mata cu data
GROUP BY C.customer_id
```