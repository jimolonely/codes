# Transfer Object Pattern
当我们想要从客户端到服务器一次性传递具有多个属性的数据时，使用传输对象模式。 传输对象也称为值对象。 Transfer Object是一个简单的POJO类，具有getter / setter方法，并且是可序列化的，因此可以通过网络传输。 它没有任何行为。 服务器端业务类通常从数据库中提取数据并填充POJO并将其发送到客户端或按值传递。 对于客户端，传输对象是只读的。 客户端可以创建自己的传输对象并将其传递给服务器，以便一次性更新数据库中的值。 以下是此类设计模式的实体。

1. 业务对象 - 业务服务使用数据填充传输对象。

2. 传输对象 - 简单的POJO，具有仅setter/getter属性的方法。

3. 客户端 - 客户端请求或将传输对象发送到业务对象。

## 1.VO
```java
public class StudentVO {
    private String name;
    private int rollNo;

    public StudentVO(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }
}
```
## 2.BO
```java
public class StudentBO {
    List<StudentVO> students;

    public StudentBO() {
        students = new ArrayList<>();
        students.add(new StudentVO("jimo", 0));
        students.add(new StudentVO("hehe", 1));
    }

    public void deleteStudent(StudentVO student) {
        students.remove(student.getRollNo());
        System.out.println("delete student,roll No:" + student.getRollNo());
    }

    public List<StudentVO> getStudents() {
        return students;
    }

    public StudentVO getStudent(int rollNo) {
        return students.get(rollNo);
    }

    public void updateStudent(StudentVO student) {
        students.get(student.getRollNo()).setName(student.getName());
        System.out.println("Student: Roll No " + student.getRollNo() + ", updated in the database");
    }
}
```
## 2.Client
```java
public class DataTransferObject {
    public static void main(String[] args) {
        StudentBO studentBusinessObject = new StudentBO();

        //print all students
        for (StudentVO student : studentBusinessObject.getStudents()) {
            System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
        }

        //update student
        StudentVO student = studentBusinessObject.getStudents().get(0);
        student.setName("Michael");
        studentBusinessObject.updateStudent(student);

        //get the student
        student = studentBusinessObject.getStudent(0);
        System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");

    }
}
/*
Student: [RollNo : 0, Name : jimo ]
Student: [RollNo : 1, Name : hehe ]
Student: Roll No 0, updated in the database
Student: [RollNo : 0, Name : Michael ]
*/
```