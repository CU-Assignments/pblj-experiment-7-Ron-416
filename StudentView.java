import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentView {
    private StudentController controller;
    private Scanner sc;

    public StudentView(StudentController controller) {
        this.controller = controller;
        this.sc = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine();
            try {
                if (choice.equals("1")) addStudent();
                else if (choice.equals("2")) viewStudents();
                else if (choice.equals("3")) updateStudent();
                else if (choice.equals("4")) deleteStudent();
                else if (choice.equals("5")) break;
                else System.out.println("Invalid choice");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addStudent() throws SQLException {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Department: ");
        String dept = sc.nextLine();
        System.out.print("Marks: ");
        double marks = Double.parseDouble(sc.nextLine());
        controller.insertStudent(new Student(id, name, dept, marks));
        System.out.println("Student added.");
    }

    private void viewStudents() throws SQLException {
        List<Student> list = controller.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : list) {
                System.out.println("ID: " + s.getStudentId() + ", Name: " + s.getName() +
                                   ", Department: " + s.getDepartment() + ", Marks: " + s.getMarks());
            }
        }
    }

    private void updateStudent() throws SQLException {
        System.out.print("ID to update: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New Name: ");
        String name = sc.nextLine();
        System.out.print("New Department: ");
        String dept = sc.nextLine();
        System.out.print("New Marks: ");
        double marks = Double.parseDouble(sc.nextLine());
        boolean updated = controller.updateStudent(new Student(id, name, dept, marks));
        System.out.println(updated ? "Student updated." : "Student not found.");
    }

    private void deleteStudent() throws SQLException {
        System.out.print("ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean deleted = controller.deleteStudent(id);
        System.out.println(deleted ? "Student deleted." : "Student not found.");
    }
}
