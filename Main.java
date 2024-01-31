import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    /*
     * Enter your student id and number, and the connection within the Sandcastle
     * should be taken care of.
     */
    public static final String STUDENT_ID = "kv20kh";
    public static final String STUDENT_NUMBER = "7129480";

    public static final String DB_DRIVER = "org.postgresql.Driver";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/" + STUDENT_ID;
    public static final String DB_USERNAME = STUDENT_ID;
    public static final String DB_PASSWORD = STUDENT_NUMBER;

    public Connection getConnection() {
        Connection c = null;

        try {
            Class.forName(DB_DRIVER);
            c = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            c.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }

    public void refreshDatabase() throws SQLException {
        Connection c = getConnection();

        Statement stmt = c.createStatement();
        String sql = "DROP TABLE IF EXISTS blog.user";
        stmt.executeUpdate(sql);
        stmt.close();

        stmt = c.createStatement();
        sql = "CREATE TABLE blog.user ("
                + " id      INT NOT NULL,"
                + " name    VARCHAR(50) NOT NULL,"
                + " age     INT NOT NULL"
                + ")";
        stmt.executeUpdate(sql);
        stmt.close();

        c.commit();
        c.close();
    }

    public void createUser(User user) throws SQLException {
        String sql = String.format("INSERT INTO blog.user (id, name, age) VALUES (%d, '%s', %d)",
                user.id, user.name, user.age);

        Connection c = getConnection();
        Statement stmt = c.createStatement();
        stmt.executeUpdate(sql);

        System.out.println("Save successful");

        stmt.close();
        c.commit();
        c.close();
    }

    public void printUsers() throws SQLException {
        String sql = "SELECT * FROM blog.user";

        Connection c = getConnection();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");

            System.out.println(String.format("ID = %d, NAME = %s, AGE = %d", id, name, age));
        }

        rs.close();
        stmt.close();
        c.close();
    }

    public static void main(String[] args) {
        Main m = new Main();

        try {
            m.refreshDatabase();

            m.createUser(m.new User(0, "Pranjal", 5));
            m.createUser(m.new User(1, "Karl", 7));

            m.printUsers();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class User {
        public int id;
        public String name;
        public int age;

        public User(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }
}
