package Moblima;

import java.util.ArrayList;
import java.util.Scanner;

public class BookingInputs {
    public BookingInputs(){}

    // returns -1 if invalid input
	public static int getIntUserInput(){
		int intInput = -1;
		String input = "";
		Scanner in = new Scanner(System.in);
		input = in.nextLine();
		try {
			intInput = Integer.parseInt(input);
		}catch (NumberFormatException e) {
			System.out.println("Invalid input: " + input + " is not a number");
			System.out.println("");
		}
		return intInput;
	}

    public static int getCineplex(){
		System.out.print("Enter Cineplex to book [0 to exit] => ");
		int userInput = getIntUserInput();
		return userInput;
	}

    public static String getSearchString(){
		System.out.print("Enter movie name to search [0 to exit] => ");
		Scanner in = new Scanner(System.in);
		String searchString = in.nextLine();
		searchString = searchString.toLowerCase();
		return searchString;
	}

    public static Seats getSeatSelection(int number){
		while(true){
			System.out.print("Seat number for Ticket " + Integer.toString(number+1) + ": ");
			Scanner in = new Scanner(System.in);
			String seats = in.nextLine();

			if (seats == "XX" || seats.length() != 2){
				System.out.println("Invalid seat");
				continue;
			} else if (seats == "0") return null;

			String row = String.valueOf(seats.charAt(0)).toUpperCase();
			String col = String.valueOf(seats.charAt(1));
			Seats s1 = new Seats(row, col);
			return s1;
		}
	}

    public static Show getShow(ArrayList<Show> allShows){
        while (true){
            System.out.print("Please enter the show number to watch [0 to exit] => ");
            int choice = getIntUserInput();
            
            if (choice == 0){
                break;
            } else if (choice > allShows.size() || choice < 0){
                System.out.println("Input out of range");
            } else {
				return ShowHandler.getShowByID(allShows, choice);
            }
            System.out.println("Invalid input, please try again");
            }
        return null;
    }

    public static int getNumberOfTicket(String category){
        int numTickets = -1;
        while (true){
            System.out.println();
            System.out.print("How many " + category + " tickets would you like to book? => ");
            numTickets = getIntUserInput();
            if (numTickets >= 0) break;
        }
        return numTickets;
	}

	public static User getUserInformation(){
		Scanner in = new Scanner(System.in);
		int number = -1;
		String email = "";
        System.out.print("Please enter your name: ");
        String name = in.nextLine();

		while (true){
			System.out.print("Please enter your email: ");
        	email = in.nextLine();
			if (email.contains("@")){
				break;
			}else{
				System.out.println("Invalid email, make sure it contains '@'");
			}
		}

		while(number == -1){
			System.out.print("Please enter your number: ");
        	number = getIntUserInput();
		}

        return new User(name, email, number);
    }

	public static char getConfirmation(double totalPrice){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Total price: " + Double.toString(totalPrice));
		System.out.print("Confirm ticket booking (Y/N): ");
        char confirmation = scanner.next().charAt(0);
		return confirmation;
	}
}
