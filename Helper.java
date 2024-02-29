import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Helper {

    public static final String[] DB_REFRESH_SQLS = {
            "DROP TABLE IF EXISTS users",
            "DROP TABLE IF EXISTS article",
            "DROP TABLE IF EXISTS tag",
            "DROP TABLE IF EXISTS title",
            "DROP TABLE IF EXISTS tag_article_mapping",
            "CREATE TABLE users (" +
                    "    id SERIAL PRIMARY KEY," +
                    "    name VARCHAR(50)," +
                    "    age INT" +
                    ")",
            "CREATE TABLE article (" +
                    "    id SERIAL PRIMARY KEY," +
                    "    user_id INT NOT NULL," +
                    "    text VARCHAR(256)" +
                    ")",
            "CREATE TABLE tag (" +
                    "    id SERIAL PRIMARY KEY," +
                    "    text VARCHAR(25)" +
                    ")",
            "CREATE TABLE title (" +
                    "    id SERIAL PRIMARY KEY," +
                    "    text VARCHAR(100)," +
                    "    article_id INT NOT NULL" +
                    ")",
            "CREATE TABLE tag_article_mapping (" +
                    "    tag_id INT NOT NULL," +
                    "    article_id INT NOT NULL," +
                    "    PRIMARY KEY (tag_id, article_id)" +
                    ")"
    };

    public static Connection getConnection() {
        Connection c = null;

        try {
            Class.forName(Config.DB_DRIVER);
            c = DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
            c.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return c;
    }

    public static void rawPrint(ResultSet rs) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();

        System.out.println(
                "*************************************************************************************************************");

        for (int i = 1; i <= columnCount; i++) {
            System.out.print(rs.getMetaData().getColumnName(i) + "\t\t");
        }
        System.out.println();
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");

        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getObject(i) + "\t\t");
            }
            System.out.println();
        }

        System.out.println(
                "*************************************************************************************************************");
    }
}
