package Moblima;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BookMyShowApp {
	private static Scanner in;
	
	public static void main(String[] args) {
		
		BookMyShowInterface bookMyShow = new BookMyShow();
		
		try {
			bookMyShow.readMovieFromTextFile("MovieList.txt");
		}catch (Exception e) {
			System.out.println("Movies File Read Error");
		}
		
		int option = 0;
		in = new Scanner(System.in);
		do {
			while(true) {
	            System.out.println();
	            System.out.println("-----------------MOBLIMA MAIN MENU---------------");
	        	System.out.println("| 01: Show all Movies                           |");
	        	System.out.println("| 02: View Showtimes                            |");
	        	System.out.println("| 03: Review/Rate Movies                        |");
	        	System.out.println("| 04: Initialize/Show Example                   |");
	        	System.out.println("| 05: View Top 5 Movies                         |");
	        	System.out.println("| 06: Booking  Menu                             |");
	        	System.out.println("| 07: Show Booking History                      |");
	        	System.out.println("| 08: Search Movie                              |");
	        	System.out.println("| 09: Create Show                               |");
				System.out.println("| 10: ADMIN Login                               |");
	            System.out.println("-------------------------------------------------");
	            System.out.println();
     

        		System.out.print("Main Menu - Enter option ('-1' to exit):");
        		
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
        			return;
        		case 1:
					bookMyShow.showAllMovies();
        			break;
        		case 2:
					bookMyShow.showShowTimes();
        			break;
        		case 3:
        			bookMyShow.createRatingReview();
        			break;
        		case 4:
					bookMyShow.initializeExample();
					System.out.println("Done!");
					bookMyShow.showExample();
        			break;
        		case 5:
        			bookMyShow.showAllMoviesTicket();
        			break;
        		case 6:
					bookMyShow.BookMovie();
        			break;
        		case 7:
        			bookMyShow.showBookingHist();
        			break;
        		case 8:
					String searchString="";
					System.out.print("Enter the movie title: ");
					in.nextLine();
					searchString = in.nextLine();
					bookMyShow.searchMovie(searchString);
        			break;
        		case 9:
					bookMyShow.createShow();
        			break;
        		case 10:
					Admin admin = Admin.getInstance();
					admin.attachBookMyShow(bookMyShow);
					admin.start();
        			return;
        		}
        	}
		} while(option != -1);
	}
}
