# Data Object Access Pattern
数据访问对象模式或DAO模式用于将低级数据访问API或操作与高级业务服务分开。 以下是数据访问对象模式的参与者。

1. 数据访问对象接口 - 此接口定义要对模型对象执行的标准操作。

2. Data Access Object具体类 - 该类实现上述接口。 此类负责从数据源获取数据，该数据源可以是database / xml或任何其他存储机制。

3. 模型对象或值对象 - 此对象是包含get / set方法的简单POJO，用于存储使用DAO类检索的数据。

![dao_pattern_uml_diagram](./dao_pattern_uml_diagram.jpg?raw=true)

## 1.Student
```java
public class Student {
    private String name;
    private int rollNo;

    public Student(String name, int rollNo) {
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
## 2.dao
```java
public interface StudentDao {
    List<Student> getAllStudents();

    Student getStudent(int rollNo);

    void updateStudent(Student student);

    void deleteStudent(Student student);
}
```
```java
public class StudentDaoImpl implements StudentDao {

    //list is working as a database
    private List<Student> students;

    public StudentDaoImpl() {
        students = new ArrayList<>();
        Student student1 = new Student("Robert", 0);
        Student student2 = new Student("John", 1);
        students.add(student1);
        students.add(student2);
    }

    @Override
    public void deleteStudent(Student student) {
        students.remove(student.getRollNo());
        System.out.println("Student: Roll No " + student.getRollNo() + ", deleted from database");
    }

    //retrive list of students from the database
    @Override
    public List<Student> getAllStudents() {
        return students;
    }

    @Override
    public Student getStudent(int rollNo) {
        return students.get(rollNo);
    }

    @Override
    public void updateStudent(Student student) {
        students.get(student.getRollNo()).setName(student.getName());
        System.out.println("Student: Roll No " + student.getRollNo() + ", updated in the database");
    }
}
```
## 3.Test
```java
public class DaoPatternDemo {
    public static void main(String[] args) {
        StudentDao studentDao = new StudentDaoImpl();

        //print all students
        for (Student student : studentDao.getAllStudents()) {
            System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
        }


        //update student
        Student student = studentDao.getAllStudents().get(0);
        student.setName("Michael");
        studentDao.updateStudent(student);

        //get the student
        studentDao.getStudent(0);
        System.out.println("Student: [RollNo : " + student.getRollNo() + ", Name : " + student.getName() + " ]");
    }
}
```
