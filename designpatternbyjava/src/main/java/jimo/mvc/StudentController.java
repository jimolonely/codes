package jimo.mvc;

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

    public String getName() {
        return model.getName();
    }

    public void setName(String name) {
        model.setName(name);
    }

    public void updateView() {
        view.printStudentDetails(model.getName(), model.getRollNo());
    }
}
