import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
    private Connection conn;

    public StudentController() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:students.db");
        String sql = "CREATE TABLE IF NOT EXISTS Student (" +
                     "StudentID INTEGER PRIMARY KEY," +
                     "Name TEXT NOT NULL," +
                     "Department TEXT NOT NULL," +
                     "Marks REAL NOT NULL)";
        conn.createStatement().execute(sql);
    }

    public void insertStudent(Student student) throws SQLException {
        String sql = "INSERT INTO Student (StudentID, Name, Department, Marks) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, student.getStudentId());
        ps.setString(2, student.getName());
        ps.setString(3, student.getDepartment());
        ps.setDouble(4, student.getMarks());
        ps.executeUpdate();
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Student");
        while (rs.next()) {
            list.add(new Student(
                rs.getInt("StudentID"),
                rs.getString("Name"),
                rs.getString("Department"),
                rs.getDouble("Marks")
            ));
        }
        return list;
    }

    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, student.getName());
        ps.setString(2, student.getDepartment());
        ps.setDouble(3, student.getMarks());
        ps.setInt(4, student.getStudentId());
        return ps.executeUpdate() > 0;
    }

    public boolean deleteStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM Student WHERE StudentID=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, studentId);
        return ps.executeUpdate() > 0;
    }
}
