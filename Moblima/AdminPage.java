package Moblima;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminPage {
    private static Scanner in;
    
    public AdminPage() throws FileNotFoundException {

        BookMyShowInterface BookMyShow = new BookMyShow();

        try {
			BookMyShow.readMovieFromTextFile("MovieList.txt");
		}catch (Exception e) {
			System.out.println("Movies File Read Error");
		}

        int option = 0;
        in = new Scanner(System.in);
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
            while(true) {
                System.out.print("Admin Main Menu - Enter option ('-1' to exit): ");
                try {
                    option = in.nextInt();
                }catch(InputMismatchException e) {
                    System.out.println("Invalid Input. Please re-enter.");
                    in.next();
                    continue;
                }
                switch (option) {
                case -1:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    return;
                case 1:
                    BookMyShow.createMovie("MovieList.txt");
                    break;
                case 2:
                    BookMyShow.updateMovie("MovieList.txt");
                    break;
                case 3:
                    BookMyShow.removeMovie("MovieList.txt");
                    break;
                case 4:
                    BookMyShow.showAllMovies();
                    break;
                case 5:
                    BookMyShow.createShow();
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    Admin admin = Admin.getInstance();
                    admin.exit();
                    break;
                }
            }
        } 
        while(option != -1);
    }
}