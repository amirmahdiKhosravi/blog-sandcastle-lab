import java.util.Scanner;

public class BlogService {

    public void addUser() {
        BlogDAO db = new BlogDAO();
        Scanner sc = new Scanner(System.in);

        System.out.println("*************** CREATE USER *********************");

        System.out.print("What is the name of the user: ");
        String userName = sc.nextLine();
        System.out.print("User's age: ");
        int userAge = Integer.valueOf(sc.nextLine());
        System.out.println();

        db.createUser(userName, userAge);
    }

    public void printUsers() {
        BlogDAO db = new BlogDAO();
        db.printUsers();
    }

    public void addTag() {
        BlogDAO db = new BlogDAO();
        Scanner sc = new Scanner(System.in);

        System.out.println("*************** CREATE TAG *********************");

        System.out.print("What is the title of the tag: ");
        String tagTitle = sc.nextLine();
        System.out.println();

        db.createTag(tagTitle);
    }

    public void printTags() {
        BlogDAO db = new BlogDAO();
        db.printTags();
    }

    public void addArticle() {
        BlogDAO db = new BlogDAO();
        Scanner sc = new Scanner(System.in);

        System.out.println("*************** CREATE ARTICLE *********************");

        /* Choose user */
        db.printUsers();

        System.out.print("User ID: ");
        String userIdStr = sc.nextLine();
        long userId = processInput(userIdStr);
        if (userId == 0) {
            System.out.println("FAILED: Invalid Input");
            return;
        }

        /* Choose tags */
        db.printTags();

        System.out.print("Tag IDs (comma-separated): ");
        String tagIdsInput = sc.nextLine();
        String[] tagStrIds = tagIdsInput.replace(" ", "").split(",");

        long[] tagIds = new long[tagStrIds.length];
        int i = 0;
        for (String idStr : tagStrIds) {
            long tagId = processInput(idStr);

            if (tagId == 0L) {
                System.out.println("FAILED: Invalid Input");
                return;
            }

            tagIds[i] = tagId;
            i++;
        }

        /* Enter article */
        System.out.println("Article title: ");
        String articleTitle = sc.nextLine();

        System.out.println("Article body: ");
        String articleBody = sc.nextLine();

        db.createArticle(articleBody, articleTitle, userId, tagIds);
    }

    public void printArticles() {
        BlogDAO db = new BlogDAO();

        db.printArticles();
    }

    private long processInput(String inp) {
        if (inp.equals("n")) {
            return 0L;
        } else {
            try {
                long id = Long.valueOf(inp);
                return id;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            return 0L;
        }
    }
}
