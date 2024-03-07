import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BlogDAO {

    public void refresh() {
        Connection c = Helper.getConnection();

        try {
            for (String sql : Helper.DB_REFRESH_SQLS) {
                Statement stmt = c.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                System.out.println("Table " + (sql.contains("DROP") ? "dropped" : "created"));
            }

            c.commit();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long createUser(String userName, int userAge) {
        /*-----------------------------------------------------------------*/
        String sql = String.format("<ADD YOUR SQL HERE>",
                userName, userAge);
        /*-----------------------------------------------------------------*/

        try {
            Connection c = Helper.getConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("Save successful");

            rs.next();
            long id = rs.getLong(1); // id of the just saved user

            stmt.close();
            c.commit();
            c.close();

            return id;

        } catch (SQLException e) {
            e.printStackTrace();

            return 0;
        }
    }

    public void printUsers() {
        /*-----------------------------------------------------------------*/
        String sql = "SELECT * FROM users";
        /*-----------------------------------------------------------------*/

        try {
            Connection c = Helper.getConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Helper.rawPrint(rs);

            rs.close();
            stmt.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long createTag(String tag) {
        /*-----------------------------------------------------------------*/
        String sql = String.format("<ADD YOUR SQL HERE>", tag);
        /*-----------------------------------------------------------------*/

        try {
            Connection c = Helper.getConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("Save successful");

            rs.next();
            long id = rs.getLong(1);

            stmt.close();
            c.commit();
            c.close();

            return id;

        } catch (SQLException e) {
            e.printStackTrace();

            return 0;
        }
    }

    public void createArticle(String articleBody, String articleTitle, long userId, long[] tagIds) {
        try {
            Connection c = Helper.getConnection();

            /* Insert into 'article' table */
            /*-----------------------------------------------------------------*/
            String articleSql = String.format(
                    "ADD YOUR SQL HERE",
                    userId, articleBody);
            /*-----------------------------------------------------------------*/

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(articleSql);
            rs.next();
            long articleId = rs.getLong(1);
            stmt.close();
            /* 'article' entry completed */

            /* Insert into 'title' table */
            /*-----------------------------------------------------------------*/
            String titleSql = String.format(
                    "<ADD YOUR SQL HERE>",
                    articleTitle, articleId);
            /*-----------------------------------------------------------------*/

            stmt = c.createStatement();
            stmt.executeUpdate(titleSql);
            stmt.close();
            /* 'title' entry completed */

            if (tagIds.length != 0) {
                /* Insert into 'tag_article_mapping' table */

                /*---------------------------------------------------------------------------------*/
                String mapping = "";
                for (int i = 0; i < tagIds.length; i++) {
                    mapping += String.format("(%d, %d)", articleId, tagIds[i]);

                    if (i < tagIds.length - 1) {
                        mapping += ",";
                    }
                }

                String tagArticleMappingSql = String.format(
                        "INSERT INTO tag_article_mapping (article_id, tag_id) VALUES %s", mapping);
                /*----------------------------------------------------------------------------------*/

                stmt = c.createStatement();
                stmt.executeUpdate(tagArticleMappingSql);
                stmt.close();
                /* 'tag_article_mapping' entry completed */
            }

            c.commit();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printTags() {
        /*-----------------------------------------------------------------*/
        String sql = "SELECT * FROM tag";
        /*-----------------------------------------------------------------*/

        try {
            Connection c = Helper.getConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Helper.rawPrint(rs);

            rs.close();
            stmt.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printArticles() {
        /*-----------------------------------------------------------------*/
        String sql = "SELECT " +
                "    ti.text AS title," +
                "    a.text AS text," +
                "    u.name AS author," +
                "    string_agg(t.text, ', ') AS tags" +
                " FROM article a" +
                " JOIN users u ON a.user_id = u.id" +
                " JOIN tag_article_mapping tam ON a.id = tam.article_id" +
                " JOIN tag t ON t.id = tam.tag_id" +
                " JOIN title ti ON ti.article_id = a.id" +
                " GROUP BY ti.text, a.text, u.name";
        /*-----------------------------------------------------------------*/

        try {
            Connection c = Helper.getConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            Helper.rawPrint(rs);

            rs.close();
            stmt.close();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(long userId) {
        /*-----------------------------------------------------------------*/
        String sql = String.format("<ADD YOUR SQL HERE>", userId);
        /*-----------------------------------------------------------------*/

        try {
            Connection c = Helper.getConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);

            System.out.println("Delete successful");

            stmt.close();
            c.commit();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTag(long tagId) {
        /*-----------------------------------------------------------------*/
        String sql = String.format("<ADD YOUR SQL HERE>", tagId);
        /*-----------------------------------------------------------------*/

        try {
            Connection c = Helper.getConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);

            System.out.println("Delete successful");

            stmt.close();
            c.commit();
            c.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
