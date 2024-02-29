import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BlogDAO db = new BlogDAO();
        BlogService service = new BlogService();
        Scanner sc = new Scanner(System.in);

        db.refresh();

        while (true) {
            int choice = choices();

            switch (choice) {
                case 1:
                    service.addUser();
                    break;

                case 2:
                    service.addTag();
                    break;

                case 3:
                    service.addArticle();
                    break;

                case 4:
                    service.printUsers();
                    break;

                case 5:
                    service.printTags();
                    break;

                case 6:
                    service.printArticles();
                    break;

                default:
                    break;
            }

            if (choice == 0) {
                break;
            }
        }
    }

    private static int choices() {
        System.out.println("1. Add User");
        System.out.println("2. Add Tag");
        System.out.println("3. Add Article");
        System.out.println();
        System.out.println("4. Print Users");
        System.out.println("5. Print Tags");
        System.out.println("6. Print Articles");
        System.out.println();

        System.out.print("Your input: ");

        Scanner sc = new Scanner(System.in);
        String inp = sc.nextLine();

        if (inp.equals("n") || inp.equals("N")) {
            return 0;
        }

        return Integer.valueOf(inp);
    }
}
