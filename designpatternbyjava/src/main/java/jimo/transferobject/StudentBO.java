package jimo.transferobject;

import java.util.ArrayList;
import java.util.List;

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
