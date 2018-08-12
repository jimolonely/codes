# MVC Pattern

想必很熟悉了。

![mvc_pattern_uml_diagram](./mvc_pattern_uml_diagram.jpg?raw=true)

## Model
```java
public class Student {
    private String rollNo;
    private String name;

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```
## View
```java
public class StudentView {
    public void printStudentDetails(String studentName, String studentRollNo) {
        System.out.println("Student: [Name=" + studentName + ",RollNo=" + studentRollNo + "]");
    }
}
```
## Controller
```java
public class StudentController {
    private Student model;
    private StudentView view;

    public StudentController(Student model, StudentView view) {
        this.model = model;
        this.view = view;
    }

    public String getRollNo() {
        return model.getRollNo();
    }

    public void setRollNo(String rollNo) {
        model.setRollNo(rollNo);
    }
```
## Test
```java
public class MVCPatternDemo {
    public static void main(String[] args) {

        StudentView studentView = new StudentView();

        StudentController controller = new StudentController(getStudentFromDB(), studentView);

        controller.updateView();

        controller.setName("hehe");

        controller.updateView();

    }

    private static Student getStudentFromDB() {
        Student student = new Student();
        student.setName("jimo");
        student.setRollNo("12334");
        return student;
    }
}
/*
Student: [Name=jimo,RollNo=12334]
Student: [Name=hehe,RollNo=12334]
*/
``` 