import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBases {
    Connection connection;
    Statement statement;

    String user = "root";
    String url = "jdbc:mysql://localhost:3306/e_billing_system";
    String password = "123456789";

    public DataBases() {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
