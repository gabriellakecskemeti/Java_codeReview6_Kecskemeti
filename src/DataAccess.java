import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataAccess {

    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/cr7_gabriella?useTimezone=true&serverTimezone=UTC";

    public DataAccess() throws SQLException, ClassNotFoundException , ConnectException {

        // Load JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Establish connection to database
           connection = DriverManager.getConnection(
                    url, "root", "");

            // We will enable auto commit so each single update sent to the database will be commited immediately
            connection.setAutoCommit(false);
            connection.setReadOnly(false);

    }

    public void closeDb() throws SQLException {
        //System.out.println("Closing connection...");
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }




}
