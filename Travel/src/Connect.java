import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connect {
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "travel";
    private final String HOST = "localhost:3306";
    private final String CONNECTION =
            String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
    
    public ResultSet rs;
    
    

    Connection con;
    private PreparedStatement preparedStatement;
    private static Connect instance;

    private Connect() {
        try {
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public static Connect getInstance() {
        if (instance == null) {
            return new Connect();
        }
        return instance;
    }

    public ResultSet execQuery(String query) {
        try {
            preparedStatement = con.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing query: " + e.getMessage());
        }
        return null;
    }

    public void execUpdate(String query) {
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing update: " + e.getMessage());
        }
    }
    


    public void regisData(String userId, String username, String userEmail, String userPassword, String userGender) {
   	 String insertQuery = "INSERT INTO msuser (UserId, Username, UserEmail, UserPassword, UserGender) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, userEmail);
            preparedStatement.setString(4, userPassword);
            preparedStatement.setString(5, userGender);
     

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing update: " + e.getMessage());
        }
   }
    public boolean usernameExistsInDatabase(String username) {
        boolean exists = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String query = "SELECT COUNT(*) FROM msuser WHERE Username = ?";

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                exists = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception (log or throw)
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exists;
    }

    public boolean emailExistsInDatabase(String email) {
        boolean exists = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        String query = "SELECT COUNT(*) FROM msuser WHERE UserEmail = ?";

        try {
            conn = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                exists = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
         
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exists;
    }
    public String getUserIdFromUsername(String username) {
        String query = "SELECT UserID FROM msuser WHERE Username = '" + username + "'";
        try {
            ResultSet resultSet = execQuery(query);
            if (resultSet.next()) {
                return resultSet.getString("UserID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}