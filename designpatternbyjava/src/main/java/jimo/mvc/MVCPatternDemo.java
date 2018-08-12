package jimo.mvc;

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
