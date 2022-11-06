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
	private UserHandler userhandler;
	SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
	Scanner scanner = new Scanner(System.in);
	
	public BookMyShow() {
		this.movieHandler = new movieHandler();
		this.cineplexHandler = new cineplexHandler();
		this.showHandler = new ShowHandler();
		this.seatHandler = new SeatHandler();
		userhandler= new UserHandler();
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

	public void showShowTimes(){
		ShowHandler.printAllShows(showHandler.getAllShows());
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
	
	public void BookMovie() {
		int booking_option = 0;
		User user1 = BookingInputs.getUserInformation();
		userhandler.getUsers().add(user1);
		ArrayList<Ticket> tickets = new ArrayList<>();
		do{
			ArrayList<Show> shows = null;
			System.out.println("--------------MOBLIMA BOOKING MENU!--------------");
			System.out.println("| 01: List All Shows                            |");
			System.out.println("| 02: Search Shows by Name                      |");
			System.out.println("| 03: View Movie by Location                    |");
			System.out.println("| 04: Go Back                                   |");
			System.out.println("-------------------------------------------------");
			System.out.print("Enter option ('4' to return): ");

			booking_option = BookingInputs.getIntUserInput();

			switch(booking_option){
				case 1:
					shows = showHandler.getAllShows();
					break;
				case 2:
					String searchString = BookingInputs.getSearchString();
					shows = showHandler.searchShows(searchString);
					break;
				case 3:
					cineplexHandler.printAllCineplex();
        			int userInput = BookingInputs.getCineplex();
					shows = showHandler.getAllShowsByLocation(cineplexHandler.getAllCineplex().get(userInput-1));
					break;
				case 4:
					return;
				default:
					System.out.println("Invalid Input");
			}
			if (shows == null){
				System.out.println("No shows found");
			} else {
				ArrayList <Ticket> ticket = bookShow(shows, user1);
				if (ticket != null){
					tickets.addAll(ticket);
				}
			}
		} while(booking_option != 4);
	}

	public ArrayList<Ticket> bookShow (ArrayList<Show> shows, User user1){
		Booking newBooking = new Booking(user1);
		ShowHandler.printAllShows(shows);
		Show selectedShow = BookingInputs.getShow(shows);
		if (selectedShow == null) return null;
		newBooking.setShow(selectedShow);
		seatHandler.printAvailableSeats(selectedShow);
		newBooking.setAdultTicket(BookingInputs.getNumberOfTicket("Adult"));
		newBooking.setStudentTicket(BookingInputs.getNumberOfTicket("Student"));

		if (newBooking.getStudentTicketNum() == 0 && newBooking.getAdultTicketNum() == 0){
			System.out.println("Cancelling booking");
			return null;
		}

		ArrayList<Seats> seats = selectSeats(newBooking);
		if (seats != null){
			newBooking.setSeats(seats);
			ArrayList<Ticket> tickets = bookSeats(newBooking);
			return tickets;
		}
		return null;
	}

	public ArrayList<Seats> selectSeats (Booking newBooking){
        ArrayList<Seats> chosenSeats = new ArrayList<Seats>();
		int totalTickets = newBooking.getAdultTicketNum() + newBooking.getStudentTicketNum();
		if (!seatHandler.checkCapacity(totalTickets, newBooking.getShow())){
			System.out.println("Not enough tickets remaining");
			return null;
		}
        while (true){
			for(int j = 0; j < totalTickets; j++){
				while (true){
					Seats s1 = BookingInputs.getSeatSelection(j);
					if (s1 == null) return null;
					if (seatHandler.checkSeatAvailability(s1, newBooking.getShow()) && !SeatHandler.duplicateSeatInput(s1, chosenSeats)){
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
        return chosenSeats;
    }

	public ArrayList<Ticket> bookSeats(Booking newBooking){
        ArrayList<Seats> seatlist = newBooking.getSeats();
        int confirmation = 0;
        if (seatlist != null){
            confirmation = bookingConfirmation(newBooking);
        }
        ArrayList<Ticket> ticketList = new ArrayList<>();
        if (confirmation == 1){
            for(Seats s: seatlist) {
				if (newBooking.getAdultTicketNum() > 0){
					ticketList.add(newBooking.getShow().bookTicket(newBooking.getUser(), s, newBooking.getAdultPrice()));
					newBooking.setAdultTicket(newBooking.getAdultTicketNum()-1);
				} else {
					ticketList.add(newBooking.getShow().bookTicket(newBooking.getUser(), s, newBooking.getStudentPrice()));
					newBooking.setStudentTicket(newBooking.getStudentTicketNum()-1);
				}
			}
        }
        return ticketList;
    }

	public void calcPrice(Booking newBooking){
		Settings settings = Settings.getInstance();
		Double studentPrice = 0.0;
		Double adultPrice = 0.0;
		String cinemaClass = newBooking.getShow().getCinema().getCinemaClass();
		if (cinemaClass== "Standard"){
			studentPrice = Double.parseDouble(settings.getProperty("ticket_price_student_standard"));
			adultPrice = Double.parseDouble(settings.getProperty("ticket_price_adult_standard"));
		} else if (cinemaClass == "Platinum"){
			studentPrice = Double.parseDouble(settings.getProperty("ticket_price_student_premium"));
			adultPrice = Double.parseDouble(settings.getProperty("ticket_price_adult_premium"));
		} else {
			studentPrice = Double.parseDouble(settings.getProperty("ticket_price_student_vip"));
			adultPrice = Double.parseDouble(settings.getProperty("ticket_price_adult_vip"));
		}

		newBooking.setAdultPrice(adultPrice);
		newBooking.setStudentPrice(studentPrice);
	}

    public int bookingConfirmation(Booking newBooking){
		calcPrice(newBooking);
		double totalPrice = newBooking.getStudentTicketNum() * newBooking.getStudentPrice() + newBooking.getAdultTicketNum() * newBooking.getAdultPrice();
        while(true){
            char confirmation = BookingInputs.getConfirmation(totalPrice);
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

	// should be in movie handler
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

	// add this to showhandler
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

	public movieHandler getMovieDatabase() {
		return movieHandler;
	}

	public void createMovie(String fileName){
        in = new Scanner(System.in);
        String movieAddName, movieAddStatus, movieAddDirector, movieAddSynopsis, movieAddCasts;
        System.out.print("Enter full name of Movie: ");
        movieAddName = in.nextLine();
        System.out.print("Enter status of Movie (Coming Soon, Now Showing): ");
        movieAddStatus = in.nextLine();
        System.out.print("Enter director of Movie: ");
        movieAddDirector = in.nextLine();
        System.out.print("Enter synopsis of Movie: ");
        movieAddSynopsis = in.nextLine();
        System.out.print("Enter casts of Movie (e.g. Steve Rogers, Borat, Mr Bean): ");
        movieAddCasts = in.nextLine();
		Movie addNewMovie;
        addNewMovie = new Movie(movieAddName, movieAddStatus, movieAddDirector, movieAddSynopsis, movieAddCasts, movieHandler);
    }

	public void updateMovie(String fileName){
		int movieOption = 0, moviePartOption = 0;
		String movieName, movieStatus, movieDirector, movieSynopsis, movieCasts;
		String movieUpdateName, movieUpdateStatus, movieUpdateDirector, movieUpdateSynopsis, movieUpdateCasts;
		showAllMovies();
		System.out.print("Which Movie would you like to update? (e.g. 1): ");
		try{
			movieOption = in.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("Invalid Input. Please re-enter.");
			in.next();
		}
		Movie selectedMovie = movieHandler.getMovie().get(movieOption-1);
		movieName = selectedMovie.getName();
		movieStatus = selectedMovie.getStatus();
		movieDirector = selectedMovie.getDirector();
		movieSynopsis = selectedMovie.getSynopsis();
		movieCasts = selectedMovie.getCast();

		System.out.println("1.Title: " + movieName);
		System.out.println("2.Status: " + movieStatus);
		System.out.println("3.Director: " + movieDirector);
		System.out.println("4.Cast: " + movieCasts);
		System.out.println("5.Synopsis: " + movieSynopsis);

		do{
			System.out.println("Select which to update (Enter '-1' to confirm & exit): ");
			moviePartOption = in.nextInt();
			switch(moviePartOption){
				case 1:
					System.out.print("Update full name of Movie: ");
					in = new Scanner(System.in);
					movieUpdateName = in.nextLine();
					selectedMovie.updateName(movieUpdateName);
					break;
				case 2:
					System.out.print("Update status of Movie (Coming Soon, Now Showing): ");
					in = new Scanner(System.in);
					movieUpdateStatus = in.nextLine();
					selectedMovie.updateStatus(movieUpdateStatus);
					break;
				case 3:
					System.out.print("Update director of Movie: ");
					in = new Scanner(System.in);
					movieUpdateDirector = in.nextLine();
					selectedMovie.updateDirector(movieUpdateDirector);
					break;
				case 4:
					System.out.print("Update casts of Movie (e.g. Steve Rogers, Borat, Mr Bean): ");
					in = new Scanner(System.in);
					movieUpdateCasts = in.nextLine();
					selectedMovie.updateCasts(movieUpdateCasts);
					break;
				case 5:
					System.out.print("Update synopsis of movie: ");
					in = new Scanner(System.in);
					movieUpdateSynopsis = in.nextLine();
					selectedMovie.updateSynopsis(movieUpdateSynopsis);
					break;
				default:
					System.out.println("Invalid Input");
			}
		} while(moviePartOption != -1);
		System.out.println(selectedMovie);
	}

	public void removeMovie(String fileName){
		int movieRemoveOption = 0;
		showAllMovies();
		System.out.print("Which Movie would you like to update? (e.g. 1): ");
		try{
			movieRemoveOption = in.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("Invalid Input. Please re-enter.");
			in.next();
		}
		Movie selectedMovie = movieHandler.getMovie().get(movieRemoveOption-1);
		System.out.println("The following movie has been deleted!");
		System.out.println(selectedMovie);
		selectedMovie.removeMovie(movieRemoveOption-1);
	}
	public void showBookingHist() {
		for (Ticket temp : userhandler.getUsers().get(userhandler.getSize()).getTickets()) {
			System.out.println( "Ticket{" +
	                " owner='" +  userhandler.getUsers().get(userhandler.getSize()).getName() +
	                ", bookingTime=" + temp.getBookingTime() +
	                ", Seats booked=" + temp.getSeat().getSeat() +
	                ", bookedShow=" + temp.getBookedShow()+
	                '}');
		}
	}
	public void createRatingReview() {
		int option=0;

		do {
		System.out.println("| 01: Rate a Movie                           |");
		System.out.println("| 02: Review a Movie                         |");
		System.out.println("| 03: Exit                                   |");
		option = scanner.nextInt();
        scanner.nextLine();
        switch(option){
		case 1:
			System.out.println("Which Movie would you like to rate?");
			String name=in.nextLine();
			
			for (Movie temp : movieHandler.getMovie()) {
				if(temp.getName().contentEquals(name)) {
					System.out.println("Enter your rating from 1 to 5:");
					double score = scanner.nextInt();
			        scanner.nextLine();
					temp.addRatings(new Rating(score,userhandler.getUsers().get(userhandler.getSize())));
					System.out.println("Review added");
				}
			}
			break;
		case 2:
			System.out.println("Which Movie would you like to review?");
			String name1=in.nextLine();
			for (Movie temp : movieHandler.getMovie()) {
				if(temp.getName().contentEquals(name1)) {
					System.out.println("Enter your review:");
					String text = in.nextLine();
					temp.addReview(new Review(text,userhandler.getUsers().get(userhandler.getSize())));
					System.out.println("Review added");
					break;
				}
			}
			break;
		case 3:
			break;
		default:
			System.out.println("Invalid Input");
	}
		}while(option!=3);
	}
	
}
