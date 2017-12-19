## 1.[combine-two-tables](https://leetcode.com/problems/combine-two-tables/description/)
Table: Person
```sql
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| PersonId    | int     |
| FirstName   | varchar |
| LastName    | varchar |
+-------------+---------+
PersonId is the primary key column for this table.
```
Table: Address
```sql
+-------------+---------+
| Column Name | Type    |
+-------------+---------+
| AddressId   | int     |
| PersonId    | int     |
| City        | varchar |
| State       | varchar |
+-------------+---------+
AddressId is the primary key column for this table.
```

Write a SQL query for a report that provides the following information for each person in the Person table, regardless if there is an address for each of those people:

FirstName, LastName, City, State

A:
```sql
SELECT Person.FirstName, Person.LastName, Address.City, Address.State from Person 
LEFT JOIN Address on Person.PersonId = Address.PersonId
```
## 2.[second-highest-salary](https://leetcode.com/problems/second-highest-salary/description/)
Write a SQL query to get the second highest salary from the Employee table.
```sql
+----+--------+
| Id | Salary |
+----+--------+
| 1  | 100    |
| 2  | 200    |
| 3  | 300    |
+----+--------+
```
For example, given the above Employee table, the query should return 200 as the second highest salary. If there is no second highest salary, then the query should return null.
```sql
+---------------------+
| SecondHighestSalary |
+---------------------+
| 200                 |
+---------------------+
```
A:
```sql
SELECT
    (SELECT DISTINCT
            Salary
        FROM
            Employee
        ORDER BY Salary DESC
        LIMIT 1 OFFSET 1) AS SecondHighestSalary

or

SELECT
    IFNULL(
      (SELECT DISTINCT Salary
       FROM Employee
       ORDER BY Salary DESC
        LIMIT 1 OFFSET 1),
    NULL) AS SecondHighestSalary
```
