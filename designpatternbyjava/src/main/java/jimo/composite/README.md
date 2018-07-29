# Composite Pattern
![composite_pattern_uml_diagram](./composite_pattern_uml_diagram.jpg?raw=true)

## 1.
```java
public class Employee {
    private String name;
    private String dept;
    private int salary;
    private List<Employee> subordinates;

    public Employee(String name, String dept, int salary) {
        this.name = name;
        this.dept = dept;
        this.salary = salary;
        this.subordinates = new ArrayList<>();
    }

    public void add(Employee e) {
        subordinates.add(e);
    }

    public void remove(Employee e) {
        subordinates.remove(e);
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    @Override
    public String toString() {
        return "Employee: [Name: " + name + ",Dept: " + dept + ",Salary: " + salary + "]";
    }
}
```
## 2.
```java
public class CompositePatternDemo {
    public static void main(String[] args) {
        Employee CEO = new Employee("John", "CEO", 30000);

        Employee headSales = new Employee("Robert", "Head Sales", 20000);

        Employee headMarketing = new Employee("Michel", "Head Marketing", 20000);

        Employee clerk1 = new Employee("Laura", "Marketing", 10000);
        Employee clerk2 = new Employee("Bob", "Marketing", 10000);

        Employee salesExecutive1 = new Employee("Richard", "Sales", 10000);
        Employee salesExecutive2 = new Employee("Rob", "Sales", 10000);

        CEO.add(headMarketing);
        CEO.add(headSales);

        headMarketing.add(clerk1);
        headMarketing.add(clerk2);

        headSales.add(salesExecutive1);
        headSales.add(salesExecutive2);

        //print all employees of the organization
        System.out.println(CEO);

        for (Employee headEmployee : CEO.getSubordinates()) {
            System.out.println(headEmployee);

            for (Employee employee : headEmployee.getSubordinates()) {
                System.out.println(employee);
            }
        }
    }
}
/**
* Employee: [Name: John,Dept: CEO,Salary: 30000]
  Employee: [Name: Michel,Dept: Head Marketing,Salary: 20000]
  Employee: [Name: Laura,Dept: Marketing,Salary: 10000]
  Employee: [Name: Bob,Dept: Marketing,Salary: 10000]
  Employee: [Name: Robert,Dept: Head Sales,Salary: 20000]
  Employee: [Name: Richard,Dept: Sales,Salary: 10000]
  Employee: [Name: Rob,Dept: Sales,Salary: 10000]
* */
```
