import java.sql.*;

public class DatabaseHandler {
    private Connection conn;


    public DatabaseHandler() {
        // Constructor initializes the connection to the database
        conn = connect();
    }

    // This method connects to the SQLite database and creates the tasks table if it doesn't already exist
    // In your ToDoList.DatabaseHandler class

    private Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:tasks.db";
            conn = DriverManager.getConnection(url);

            // Create the tasks table
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY, \"task\" TEXT)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // This method adds a task to the tasks table
    public void addTask(String task) {
        String sql = "INSERT INTO tasks(\"task\") VALUES(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, task);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // This method deletes a task from the tasks table
    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getTasks() {
        String sql = "SELECT id, \"task\" FROM tasks";
        StringBuilder tasks = new StringBuilder();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Loop over the result set, appending each task to the tasks string
            while (rs.next()) {
                tasks.append(rs.getInt("id")).append(": ").append(rs.getString("task")).append("\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks.toString();
    }

}
