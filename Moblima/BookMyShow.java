package Moblima;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BookMyShow implements BookMyShowInterface{
	
	private movieHandler movieHandler;
	private cineplexHandler cineplexHandler;
	private cinemaHandler cinemaHandler;
	private Cinema cinema;
	private Cineplex cineplex;
	private Scanner in;
	private SeatHandler seatHandler;
	private ShowHandler showHandler;
	SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
	Scanner scanner = new Scanner(System.in);
	
	public BookMyShow() {
		this.movieHandler = new movieHandler();
		this.cineplexHandler = new cineplexHandler();
		this.showHandler = new ShowHandler();
		this.seatHandler = new SeatHandler();
		in = new Scanner(System.in);
	}
	
	public void initializeExample() {
		Cineplex jurong = cineplexHandler.addCineplex("JurongPoint");
		cinemaHandler JurongPoint = new cinemaHandler("JurongPoint");
		JurongPoint.addCinema("Standard", 35, jurong);
		JurongPoint.addCinema("Platinum", 10, jurong);
		cineplexHandler.getAllCineplex().get(cineplexHandler.getSize()-1).setHall(JurongPoint);
		JurongPoint.addCinema("VIP", 1, jurong);
		
		User ayush = new User("Ayush","ayus@gmail.com",3293131);
        
        //Movie ironMan = new Movie("Iron Man","showing","Jon Favreaue", movieHandler);
        //Movie avengers = new Movie("Avengers: End Game", "showing","Jon Favreaue", movieHandler);

		Movie ironMan = new Movie("Iron Man 2","showing","Jon Favreaue","AAA", "Example Cast...", movieHandler);
        Movie avengers = new Movie("Avengers: End Game", "showing","Jon Favreaue","BBB", "Example Cast...", movieHandler);
        String dateInString = "Friday, Jun 7, 2020 09:00:00 AM";
		try {
			Date date = formatter.parse(dateInString);
			Show show1 = showHandler.addShows(date, ironMan, cineplexHandler.getAllCineplex().get(0).getHall().get(0), seatHandler);
			Show show2 = showHandler.addShows(date,avengers,cineplexHandler.getAllCineplex().get(0).getHall().get(0), seatHandler);
			Show show3 = showHandler.addShows(date,avengers,cineplexHandler.getAllCineplex().get(0).getHall().get(1), seatHandler);
			Show show4 = showHandler.addShows(date,avengers,cineplexHandler.getAllCineplex().get(0).getHall().get(2), seatHandler);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void showExample() {
		cineplexHandler.printAllCineplex();
		System.out.println("Show all cinemas:");
		cineplexHandler.getAllCineplex().get(0).printAllCinema();
	}
	
	public void showAllMovies() {
		System.out.println("Showing all movies...");
		int count =1;
		for (Movie temp : movieHandler.getMovie()) {
			System.out.print(count+": ");
			System.out.println(temp);
			count++;
		}
	}
	
	public void readMovieFromTextFile(String fileName) throws FileNotFoundException{
	    	FileReader movieDatabase = new FileReader(fileName);
	    	Scanner read = new Scanner(movieDatabase);
	    	read.useDelimiter("\\||\\r\\n");
	    	read.nextLine();
	    	String movieName, movieStatus, movieDirector, movieSynopsis, movieCast;
	    	Movie newMovie;
	    	while(read.hasNext()) {
	    		//read.next();
	    		movieName = read.next();
	    		movieStatus = read.next();
	    		movieDirector = read.next();
	    		movieSynopsis = read.next();
	    		movieCast = read.next();
	    		newMovie = new Movie(movieName, movieStatus, movieDirector, movieSynopsis, movieCast, movieHandler);
	    	}
	    	read.close();
	}

	public User getUserInformation(){
        System.out.print("Please enter your name: ");
        String name = in.nextLine();

        System.out.print("Please enter your email: ");
        String email = in.nextLine();

        System.out.print("Please enter your number: ");
        double number = in.nextDouble();

        return new User(name, email, number);
    }
	
	public void BookMovie() {
		int booking_option = 0;
		ArrayList<Show> allShows = null;
		ArrayList<Ticket> tickets = null;
		User user1 = getUserInformation();
		Booking newBooking = new Booking(user1);
		do{
			System.out.println("--------------MOBLIMA BOOKING MENU!--------------");
			System.out.println("| 01: List All Shows                            |");
			System.out.println("| 02: Search Shows by Name                      |");
			System.out.println("| 03: View Movie by Location                    |");
			System.out.println("| 04: Go Back                                   |");
			System.out.println("-------------------------------------------------");
			System.out.print("Enter option ('4' to return):");

			booking_option = in.nextInt();
			switch(booking_option){
				case 1:
					newBooking.setShowSelection(showHandler.getAllShows());
					break;
				case 2:
					System.out.print("Enter movie name to search [0 to exit] => ");
					Scanner newScanner = new Scanner(System.in);
					String searchString = newScanner.nextLine();
					searchString = searchString.toLowerCase();
					newBooking.setShowSelection(showHandler.searchShows(searchString));
					break;
				case 3:
					cineplexHandler.printAllCineplex();
        			System.out.print("Enter Cineplex to book [0 to exit] => ");
					in.nextLine();
        			int userInput = getIntUserInput();
					newBooking.setShowSelection(showHandler.printAllShowsByLocation(cineplexHandler.getAllCineplex().get(userInput-1))); 
					break;
				case 4:
					break;
				default:
					System.out.println("Invalid Input");
			}

			
			tickets = book(newBooking);
		} while(booking_option != 4);
	}

	// returns -1 if invalid input
	public int getIntUserInput(){
		int intInput = -1;
		String input = "";
		input = scanner.nextLine();
		try {
			intInput = Integer.parseInt(input);
		}catch (NumberFormatException e) {
			System.out.println("Invalid input: " + input + " is not a number");
			System.out.println("");
		}
		return intInput;
	}

	public ArrayList<Ticket> book(Booking newBooking){
		if (newBooking.getShowSelection() != null){
			ShowHandler.printAllShows(newBooking.getShowSelection());
			Show selectedShow = selectShow(newBooking.getShowSelection());
			newBooking.setShow(selectedShow);
			seatHandler.printAvailableSeats(selectedShow);
			newBooking.setNumOfTickets(getNumberOfTicket());
			if (newBooking.getNumOfTickets() == 0) return null;
			ArrayList<Seats> seats = selectSeats(newBooking);
			newBooking.setSeats(seats);
			if (seats != null){
				return bookSeats(newBooking);
			}
		} else {
			System.out.println("No shows found");
		}
		return null;
	}

	public boolean duplicateInput(Seats s1, ArrayList<Seats> chosenSeats){
        boolean duplicate = false;
                        
        for (Seats o : chosenSeats){
            if (o.getSeat().equals(s1.getSeat())){
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }

    public ArrayList<Seats> selectSeats(Booking newBooking){

        ArrayList<Seats> chosenSeats = new ArrayList<Seats>();
        while (true){
            if (seatHandler.checkCapacity(newBooking.getNumOfTickets(), newBooking.getShow())){
                for(int j = 0; j < newBooking.getNumOfTickets(); j++){
                    while (true){
                        System.out.print("Seat number for Ticket " + Integer.toString(j+1) + ": ");
                        String seats = scanner.nextLine();

                        if (seats == "XX" || seats.length() != 2){
                            System.out.println("Invalid seat");
                            continue;
                        } else if (seats == "0") return null;

                        String row = String.valueOf(seats.charAt(0)).toUpperCase();
                        String col = String.valueOf(seats.charAt(1));
                        Seats s1 = new Seats(row, col);

                        if (seatHandler.checkSeatAvailability(s1, newBooking.getShow()) && !duplicateInput(s1, chosenSeats)){
                            chosenSeats.add(s1);
                            break;
                        } else{
                            System.out.println("Seat is not available, please try again");
                            System.out.println("Enter 0 to exit");
                        }
                        
                        if (chosenSeats.size() == j+1) break;
                    }
                }
                break;
            }
        }
        return chosenSeats;
    }

    public int seatConfirmation(Booking newBooking){
        while(true){
            System.out.print("Confirm ticket booking (Y/N): ");
            char confirmation = scanner.next().charAt(0);
            if (Character.toUpperCase(confirmation) == 'Y'){
                for (Seats s: newBooking.getSeats()){
                    seatHandler.removeSeats(s, newBooking.getShow());
                }
                return 1;
            } else if (Character.toUpperCase(confirmation) == 'N'){
                System.out.println("Booking unsuccessful");
                System.out.println("Returning back to main menu");
                return 0;
            } else {
                System.out.println("Enter only Y or N");
            }
        }
    }

	public Show selectShow(ArrayList<Show> allShows){
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

	public int getNumberOfTicket(){
        int numTickets = -1;
        while (true){
            System.out.println();
            System.out.print("How many tickets would you like to book? [0 to go back] => ");
            numTickets = getIntUserInput();
            if (numTickets >= 0) break;
        }
        return numTickets;
    }

	public ArrayList<Ticket> bookSeats(Booking newBooking){
        ArrayList<Seats> seatlist = newBooking.getSeats();
        int confirmation = 0;
        if (seatlist != null){
            confirmation = seatConfirmation(newBooking);
        }
        ArrayList<Ticket> ticketList = new ArrayList<>();
        if (confirmation == 1){
            for(Seats s: seatlist) ticketList.add(newBooking.getShow().bookTicket(newBooking.getUser(), s));
        }
        return ticketList;
    }

	public void showAllMoviesTicket() {
		System.out.println("Sort Movies by: 1.Ticket sales, 2.Ratings");
		int sort = scanner.nextInt();
        scanner.nextLine();
        int count =1;
        if(sort==1) {
        	movieHandler.sortByTicketSales();
        	for (Movie temp : movieHandler.getMovie()) {
				System.out.print(count+": ");
				System.out.print(temp);
				System.out.println("Ticket Sales: "+temp.getTicketsSize());
				count++;
        	}
        }
        else {
        	movieHandler.sortByRatings();
        	for (Movie temp : movieHandler.getMovie()) {
    			System.out.print(count+": ");
    			System.out.print(temp);
    			if (temp.getAverageRatings() == -1)
    				System.out.println("Rating: NA");
    			else 
    				System.out.println("Rating: "+temp.getAverageRatings());
    			count++;
        	}
        }
		
		
	}

	public void searchMovie(String searchString) {
		System.out.println("Showing results for: "+searchString);
		for (Movie temp : movieHandler.getMovie()) {
			if(temp.getName().contentEquals(searchString)) {
				System.out.println(temp);
				System.out.print("Average Movie Rating: ");
				if (temp.getAverageRatings() == -1)
					System.out.println("NA");
				else
					System.out.println(temp.getAverageRatings());
				System.out.println("Most Recent Reviews:");
				System.out.println(temp.getReview());
			}
		}
	}

	public void createShow() {
		int movieOption = -1, cineplexOption =-1, cinemaOption =-1;
		String dateInString;
		Date showtime = null;
		
		showAllMovies();
		System.out.print("Select Movie to create show:");
		try{
			movieOption = in.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("Invalid Input. Please re-enter.");
			in.next();
		}
		Movie selectedMovie = movieHandler.getMovie().get(movieOption-1);
		
		System.out.print("Enter date and showtime (E.g. Friday, Jun 7, 2020 09:00:00 AM): ");
		in.nextLine();
		dateInString = in.nextLine();
		try {
			showtime = formatter.parse(dateInString);
			System.out.println(showtime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		cineplexHandler.printAllCineplex();
		System.out.print("Select Cineplex: ");
		try{
			cineplexOption = in.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("Invalid Input. Please re-enter.");
			in.next();
		}
		
		cineplexHandler.getAllCineplex().get(cineplexOption-1).printAllCinema();
		System.out.print("Select Cinema: ");
		try{
			cinemaOption = in.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("Invalid Input. Please re-enter.");
			in.next();
		}
		
		Show show1 = new Show(showtime,selectedMovie,cineplexHandler.getAllCineplex().get(cineplexOption-1).getHall().get(cinemaOption-1));
		System.out.println(show1);
	}
}
