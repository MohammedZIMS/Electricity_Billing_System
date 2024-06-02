import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBases {
    Connection connection;
    Statement statement;

    String user = "root";
    String url = "jdbc:mysql://localhost:33060/e_billing_system";
    String password = "1z7a1h0i5d9u0l4#7@5";

    public DataBases() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
