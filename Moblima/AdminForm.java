package Moblima;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class is the UI for the admin module.
 * @author Nghia Nguyen
 * @version 1.0
 */
public final class AdminForm extends Form {

    private Admin admin;

    /**
     * Constructor for AdminForm.
     * @param admin the <code>Admin</code> object to be used.
     */
    public AdminForm(Admin admin) {
        super();
        this.admin = admin;
    }

    /**
     * Show the form.
     * @return <code>null</code>.
     */
    public Object show() {
        exitFlag = false;
        //BookMyShowInterface BookMyShow = new BookMyShow();
        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println();
            System.out.println("-------------MOBLIMA ADMIN MAIN MENU--------------");
            System.out.println("| 01: Create Movie Listing                       |");
            System.out.println("| 02: Update Movie Listing                       |");
            System.out.println("| 03: Remove Movie Listing                       |");
            System.out.println("| 04: View all Movie Listings                    |");
            System.out.println("| 05: Create Movie Showtime                      |");
            System.out.println("| 06: Update Movie Showtime                      |");
            System.out.println("| 07: Remove Movie Showtime                      |");
            System.out.println("| 08: Configure System Settings                  |");
            System.out.println("| 09: USER VIEW                                  |");
            System.out.println("--------------------------------------------------");
            System.out.println();
            System.out.print("Admin Main Menu - Enter option ('-1' to exit): ");
            try {
                option = sc.nextInt();
            }catch(InputMismatchException e) {
                System.out.println("Invalid Input. Please re-enter.");
                sc.next();
                continue;
            }
            switch (option) {
            case -1:
                System.out.println("Goodbye!");
                System.exit(0);
                return null;
            case 1:
                admin.createMovie();
                break;
            case 2:
                admin.updateMovie();
                break;
            case 3:
                admin.removeMovie();
                break;
            case 4:
                admin.showAllMovies();
                break;
            case 5:
                admin.createShow();
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                admin.manageSettings();
                break;
            case 9:
                admin.exit();
                this.exit();
                break;
            default:
                System.out.println("Invalid Input. Please re-enter.");
                continue;
            }
        } while(!exitFlag);
        return null;
    }
}
