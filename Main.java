public class Main {
    public static void main(String[] args) {
        try {
            StudentController controller = new StudentController();
            StudentView view = new StudentView(controller);
            view.showMenu();
        } catch (Exception e) {
            System.out.println("Application Error: " + e.getMessage());
        }
    }
}
