import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:products.db");
    }

    private static void createTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Product (" +
                     "ProductID INTEGER PRIMARY KEY," +
                     "ProductName TEXT NOT NULL," +
                     "Price REAL NOT NULL," +
                     "Quantity INTEGER NOT NULL)";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    private static void insertProduct(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Product ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("Enter Quantity: ");
        int qty = Integer.parseInt(sc.nextLine());
        String sql = "INSERT INTO Product (ProductID, ProductName, Price, Quantity) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.setString(2, name);
        pstmt.setDouble(3, price);
        pstmt.setInt(4, qty);
        pstmt.executeUpdate();
        System.out.println("Product added.");
    }

    private static void viewProducts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Product";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("ProductID") +
                               ", Name: " + rs.getString("ProductName") +
                               ", Price: " + rs.getDouble("Price") +
                               ", Quantity: " + rs.getInt("Quantity"));
        }
    }

    private static void updateProduct(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Product ID to update: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New Product Name: ");
        String name = sc.nextLine();
        System.out.print("New Price: ");
        double price = Double.parseDouble(sc.nextLine());
        System.out.print("New Quantity: ");
        int qty = Integer.parseInt(sc.nextLine());
        String sql = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setDouble(2, price);
        pstmt.setInt(3, qty);
        pstmt.setInt(4, id);
        int rows = pstmt.executeUpdate();
        if (rows > 0) System.out.println("Product updated.");
        else System.out.println("Product not found.");
    }

    private static void deleteProduct(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Product ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());
        String sql = "DELETE FROM Product WHERE ProductID = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        int rows = pstmt.executeUpdate();
        if (rows > 0) System.out.println("Product deleted.");
        else System.out.println("Product not found.");
    }

    public static void main(String[] args) {
        try (Connection conn = connect(); Scanner sc = new Scanner(System.in)) {
            createTable(conn);
            while (true) {
                System.out.println("\n1. Create Product");
                System.out.println("2. View Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                String choice = sc.nextLine();
                if (choice.equals("1")) insertProduct(conn, sc);
                else if (choice.equals("2")) viewProducts(conn);
                else if (choice.equals("3")) updateProduct(conn, sc);
                else if (choice.equals("4")) deleteProduct(conn, sc);
                else if (choice.equals("5")) break;
                else System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
