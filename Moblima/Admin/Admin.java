package Moblima.Admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Moblima.MovieBookerApp;
import Moblima.MovieBookerInterface;
import Moblima.Entities.Cinema;
import Moblima.Entities.Cineplex;
import Moblima.Entities.Movie;
import Moblima.Entities.Movie.MovieStatus;
import Moblima.Entities.Movie.MovieType;
import Moblima.Entities.Show;
import Moblima.Entities.Cinema.HallType;
import Moblima.Exceptions.InvalidInputException;
import Moblima.Utils.SettingsController;
import Moblima.Utils.SettingsForm;
import Moblima.Utils.UtilityInputs;
import Moblima.Utils.UtilityOutput;
import Moblima.Handlers.CinemaHandler;
import Moblima.Handlers.CineplexHandler;
import Moblima.Handlers.MovieHandler;
import Moblima.Handlers.SeatHandler;
import Moblima.Handlers.ShowHandler;

/**
 * Controller of the admin module. Creates and delegates tasks to other classes.
 * @author Dann Wee
 * @version 1.0
 */
public class Admin implements AdminLogic, LoginObserver {

    private LoginPage loginUI;
    private AdminForm adminUI;
    private SettingsController settingsController;

    private MovieBookerInterface movieBooker;
    private MovieHandler movieHandler;
    private ShowHandler showHandler;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    /**
     * Constructor for Admin.
     * @param movieBooker Object of movieBooker
     */
    public Admin(MovieBookerInterface movieBooker) {
        adminUI = new AdminForm(this);
        settingsController = new SettingsController(new SettingsForm());
        this.movieBooker = movieBooker;
        movieHandler = MovieHandler.getInstance();
        showHandler = ShowHandler.getInstance();
    }

    public void start() {
        //adminUI.show();
        login();
    }

    /**
     * Launch the login function.
     * @return <code>true</code> if the login is successful, <code>false</code> otherwise.
     */
    private void login() {
        IDandPasswords idandPasswords = new IDandPasswords();
		loginUI = new LoginPage(idandPasswords.getLoginInfo());
        loginUI.addLoginObserver(this);
    }

    /**
     * Launch the admin UI when the login is successful.
     */
    public void loginSuccess() {
        adminUI.show();
    }

    /**
     * Exit the admin UI when going back to user view.
     */
    public void exit() {
        loginUI = null;
        adminUI = null;
        settingsController = null;
        MovieBookerApp.showUserView(movieBooker);
    }

    /**
    * View all movie listings
    */
    public void showAllMovies(){
        movieBooker.showAllMovies();
    }

    /**
     * Create a new movie listing 
     */
    public void createMovie() {
        String movieAddName, movieAddDirector, movieAddSynopsis, movieAddCasts;
        MovieStatus movieAddStatus = null;
        MovieType movieAddType = null;
        UtilityOutput.printInputMessage("Enter Title of Movie => ");
        movieAddName = UtilityInputs.getStringUserInput();
        
        int x = 1 ;
        for (MovieType type : MovieType.values()){
            UtilityOutput.printMessage(Integer.toString(x) + ". " + type);
            x++;
        }
        while (movieAddType == null){
            UtilityOutput.printInputMessage("Enter Movie Type [Enter '0' to exit] => ");
            int choice = UtilityInputs.getIntUserInput();
            
            try{
                switch(choice){
                    case 0:
                        return;
                    case 1:
                    	movieAddType = MovieType.STANDARD_DIGITAL;
                        break;
                    case 2:
                    	movieAddType = MovieType.BLOCKBUSTER;
                        break;
                    case 3: 
                    	movieAddType = MovieType.DIGITAL_3D;
                        break;
                    default:
                        throw new InvalidInputException("Please enter only numbers listed on the menu only");
                    }
                } catch(InvalidInputException e){
                    UtilityOutput.printMessage(e.getMessage());
                }
        }
        
        x = 1 ;
        for (MovieStatus status : MovieStatus.values()){
            UtilityOutput.printMessage(Integer.toString(x) + ". " + status);
            x++;
        }
        while (movieAddStatus == null){
            UtilityOutput.printInputMessage("Enter Movie Status [Enter '0' to exit] => ");
            int choice = UtilityInputs.getIntUserInput();
            
            try{
                switch(choice){
                    case 0:
                        return;
                    case 1:
                    	movieAddStatus = MovieStatus.COMING_SOON;
                        break;
                    case 2:
                    	movieAddStatus = MovieStatus.NOW_SHOWING;
                        break;
                    case 3: 
                    	movieAddStatus = MovieStatus.PREVIEW;
                        break;
                    case 4: 
                    	movieAddStatus = MovieStatus.END_OF_SHOWING;
                        break;
                    default:
                        throw new InvalidInputException("Please enter only numbers listed on the menu only");
                    }
                } catch(InvalidInputException e){
                    UtilityOutput.printMessage(e.getMessage());
                }
        }
        
        UtilityOutput.printInputMessage("Enter Director of Movie => ");
        movieAddDirector = UtilityInputs.getStringUserInput();
        UtilityOutput.printInputMessage("Enter Casts of movie (e.g. Steve Rogers, Borat, Mr Bean) => ");
        movieAddCasts = UtilityInputs.getStringUserInput();
        UtilityOutput.printInputMessage("Enter Synopsis of Movie => ");
        movieAddSynopsis = UtilityInputs.getStringUserInput();
        Movie addNewMovie = movieHandler.createMovie(movieAddName, movieAddType, movieAddStatus, movieAddDirector, movieAddSynopsis, movieAddCasts);
        UtilityOutput.printMessage("SUCCESS! " + movieAddName + " has been added!");
        UtilityOutput.printMessage(addNewMovie.toString());
    }

    /**
     * Update an existing movie listing.
     */
    public void updateMovie() {
        int movieOption = 0, moviePartOption = 0;
        String movieUpdateValue;
        movieBooker.showAllMovies();
        while (true){
            UtilityOutput.printInputMessage("Enter MovieID (e.g. 1) [Enter '0' to exit] => ");
            movieOption = UtilityInputs.getIntUserInput();
            if (movieOption == 0) return;
            else if (movieOption == -1){
                UtilityOutput.printMessage("Please re-enter.");
                continue;
            }
            else if (movieOption < -1 || movieOption > movieHandler.sizeMovie()){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else{
                break;
            }
        }
        Movie selectedMovie = movieHandler.getMovie().get(movieOption-1);
        UtilityOutput.printMessage("1.Title: " + selectedMovie.getName());
        UtilityOutput.printMessage("2.Type: " + selectedMovie.getType());
        UtilityOutput.printMessage("3.Status: " + selectedMovie.getStatus());
        UtilityOutput.printMessage("4.Director: " + selectedMovie.getDirector());
        UtilityOutput.printMessage("5.Cast: " + selectedMovie.getCast());
        UtilityOutput.printMessage("6.Synopsis: " + selectedMovie.getSynopsis());

        while(true){
            UtilityOutput.printInputMessage("Enter Choice (e.g. 1) [Enter '0' to confirm & exit] => ");
            moviePartOption = UtilityInputs.getIntUserInput();
            if (moviePartOption == 0){
                break;
            }
            else if (moviePartOption == -1){
                UtilityOutput.printMessage("Please re-enter.");
                continue;
            }
            else if (moviePartOption < -1 || moviePartOption > 6){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else if (moviePartOption == 2) {
            	int x = 1 ;
                for (MovieType type : MovieType.values()){
                    UtilityOutput.printMessage(Integer.toString(x) + ". " + type);
                    x++;
                }
               
                UtilityOutput.printInputMessage("Enter Movie Type to update [Enter '0' to exit] => ");
                int choice = UtilityInputs.getIntUserInput();
                
                try{
                    switch(choice){
                        case 0:
                            return;
                        case 1:
                        	selectedMovie.setType(MovieType.STANDARD_DIGITAL);
                            break;
                        case 2:
                        	selectedMovie.setType(MovieType.BLOCKBUSTER);
                            break;
                        case 3: 
                        	selectedMovie.setType(MovieType.DIGITAL_3D);
                            break;
                        default:
                            throw new InvalidInputException("Please enter only numbers listed on the menu only");
                        }
                    } catch(InvalidInputException e){
                        UtilityOutput.printMessage(e.getMessage());
                    }
            }else if (moviePartOption == 3) {
            	int x = 1 ;
                for (MovieStatus status : MovieStatus.values()){
                    UtilityOutput.printMessage(Integer.toString(x) + ". " + status);
                    x++;
                }
               
                UtilityOutput.printInputMessage("Enter Movie Status to update [Enter '0' to exit] => ");
                int choice = UtilityInputs.getIntUserInput();
                
                try{
                    switch(choice){
                        case 0:
                            return;
                        case 1:
                        	selectedMovie.setStatus(MovieStatus.COMING_SOON);
                            break;
                        case 2:
                        	selectedMovie.setStatus(MovieStatus.NOW_SHOWING);
                            break;
                        case 3: 
                        	selectedMovie.setStatus(MovieStatus.PREVIEW);
                            break;
                        case 4:
                        	selectedMovie.setStatus(MovieStatus.END_OF_SHOWING);
                        	break;
                        default:
                            throw new InvalidInputException("Please enter only numbers listed on the menu only");
                        }
                    } catch(InvalidInputException e){
                        UtilityOutput.printMessage(e.getMessage());
                    }
            }else {
                UtilityOutput.printInputMessage("Enter New Value => ");
                movieUpdateValue = UtilityInputs.getStringUserInput();
                switch(moviePartOption){
                case 1:
                    selectedMovie.setName(movieUpdateValue);
                    break;
                case 2:
                	break;
                case 3:
                	break;
                case 4:
                    selectedMovie.setDirector(movieUpdateValue);
                    break;
                case 5:
                    selectedMovie.setCast(movieUpdateValue);
                    break;
                case 6:
                    selectedMovie.setSynopsis(movieUpdateValue);
                    break;
                }
            }
        } 
        UtilityOutput.printMessage("UPDATED! " + selectedMovie.getName() + " has been updated!");
        UtilityOutput.printMessage(selectedMovie.toString());
    }

    /**
     * Remove an existing movie listing.
     */
    public void removeMovie() {
        int movieRemoveOption = 0;
		movieBooker.showAllMovies();
        while (true){
            UtilityOutput.printInputMessage("Enter MovieID (e.g. 1) [Enter '0' to exit] => ");
            movieRemoveOption = UtilityInputs.getIntUserInput();
            if (movieRemoveOption == 0){
                break;
            }
            else if (movieRemoveOption == -1){
                UtilityOutput.printMessage("Please re-enter.");
                continue;
            }
            else if (movieRemoveOption < -1){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else{
                MovieHandler.getInstance().removeMovie(movieRemoveOption);
                break;
            }
        }
    }

    /**
     * View all shows.
     */
    public void showAllShows(){
        while(true){
            if (showHandler.getAllShows().size() != 0 ){
			    ShowHandler.printAllShows(showHandler.getAllShows());
                break;
            }else{
                UtilityOutput.printMessage("NO SHOWS SHOWN.");
                break;
            }
        }
    }

    /**
     * Create a new show.
     */
    public void createShow() {
        CineplexHandler cineplexHandler = CineplexHandler.getInstance();
		MovieHandler movieHandler = MovieHandler.getInstance();
		ShowHandler showHandler = ShowHandler.getInstance();
		SeatHandler seatHandler = SeatHandler.getInstance();
		int movieOption = -1, cineplexOption =-1, cinemaOption =-1;
		String dateInString;
		Date showtime = null;
		
		movieBooker.showAllMovies();
        while (true){
		    UtilityOutput.printInputMessage("Enter MovieID [Enter '0' to exit] => ");
		    movieOption = UtilityInputs.getIntUserInput();
            if (movieOption == 0) return;
            else if (movieOption == -1){
                UtilityOutput.printMessage("Please re-enter.");
                continue;
            }
            else if (movieOption < -1){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else if (MovieHandler.getMovieByID(movieHandler.getMovie(), movieOption) == null){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else{
                break;
            }
        }
        Movie selectedMovie = MovieHandler.getMovieByID(movieHandler.getMovie(), movieOption);
		while(true){
            UtilityOutput.printInputMessage("Enter Date & Showtime (Format: 31/07/2022 21:00:00) => ");
		    dateInString = UtilityInputs.getStringUserInput();
		    try {
		    	showtime = formatter.parse(dateInString);
		    	UtilityOutput.printMessage(showtime.toString());
                break;
		    } catch (ParseException e) {
		    	UtilityOutput.printMessage("Wrong format. Please re-enter.");
		    }
        }
        if (cineplexHandler.getAllCineplex().size() != 0 ){
            cineplexHandler.printAllCineplex();
            while (true){
                UtilityOutput.printInputMessage("Enter CineplexID [Enter '0' to exit] => ");
                cineplexOption = UtilityInputs.getIntUserInput();
                if (cineplexOption == 0) return;
                else if (cineplexOption == -1){
                    UtilityOutput.printMessage("Please re-enter.");
                    continue;
                }
                else if (cineplexOption < -1 || cineplexOption > cineplexHandler.getAllCineplex().size()){
                    UtilityOutput.printMessage("Invalid input. Please re-enter.");
                    continue;
                }
                else{
                    break;
                }
            }
            Cineplex cineplex = cineplexHandler.getAllCineplex().get(cineplexOption-1);
            if (CinemaHandler.getInstance().getCinemaFromCineplex(cineplex).size() != 0){
                ArrayList<Cinema> cinemaList = CinemaHandler.getInstance().getCinemaFromCineplex(cineplex);
                for (int i = 0; i < cinemaList.size(); i++){
                    UtilityOutput.printMessage(Integer.toString(i+1) + ". " + cinemaList.get(i).getCinemaClass() + ", " + cinemaList.get(i).getCapacity());
                }
                while (true){
                    UtilityOutput.printInputMessage("Enter CinemaID [Enter '0' to exit] => ");
                    cinemaOption = UtilityInputs.getIntUserInput();
                    if (cinemaOption == 0) return;
                    else if (cinemaOption == -1){
                        UtilityOutput.printMessage("Please re-enter.");
                        continue;
                    }
                    else if (cinemaOption < -1 || cinemaOption > CinemaHandler.getInstance().getCinemaFromCineplex(cineplex).size()){
                        UtilityOutput.printMessage("Invalid input. Please re-enter.");
                        continue;
                    }
                    else{
                        break;
                    }
                }
		        Show show1 = showHandler.addShows(showtime,selectedMovie,CinemaHandler.getInstance().getCinemaFromCineplex(cineplex).get(cinemaOption-1), seatHandler);
                UtilityOutput.printMessage("SUCCESS! The following Show has been created!");
		        UtilityOutput.printMessage(show1.toString());
            }
            else{
                UtilityOutput.printMessage("NO CINEMA SHOWN.");
            }
        }
        else{
            UtilityOutput.printMessage("NO CINEPLEX SHOWN.");
        }
    }

    /**
     * Update an existing show date and time.
     */
    public void updateShow() {
        ShowHandler showHandler = ShowHandler.getInstance();
		while(true){
            if (showHandler.getAllShows().size() != 0 ){
			    ShowHandler.printAllShows(showHandler.getAllShows());
                int choice = -1;
			    while (true){
                    UtilityOutput.printInputMessage("Enter ShowID [Enter '0' to exit] => ");
                    choice = UtilityInputs.getIntUserInput();
                    if (choice == 0) return;
                    else if (choice == -1){
                        UtilityOutput.printMessage("Please re-enter.");
                        continue;
                    }
                    else if (choice < -1){
                        UtilityOutput.printMessage("Invalid input. Please re-enter.");
                        continue;
                    }
                    else if (ShowHandler.getShowByID(showHandler.getAllShows(), choice) == null){
                        UtilityOutput.printMessage("Invalid input. Please re-enter.");
                        continue;
                    }
                    else{
                        break;
                    }
                }
			    Show selectedShow = ShowHandler.getShowByID(showHandler.getAllShows(), choice);
			    UtilityOutput.printMessage("You have selected the following:\n " + selectedShow.toString());
                while(true){
                    UtilityOutput.printInputMessage("Enter new Date & Time [Format: 31/07/2022 21:00:00] => ");
			        String dateInString = UtilityInputs.getStringUserInput();
			        try{
				        Date date = formatter.parse(dateInString);
				        selectedShow.setShowTime(date);
                        break;
			        }catch(ParseException e){
				        UtilityOutput.printMessage("Wrong format. Please re-enter.");
			        }
                }
                UtilityOutput.printMessage("UPDATED! The following Show has been updated!");
                UtilityOutput.printMessage(selectedShow.toString());
            }else{
                UtilityOutput.printMessage("NO SHOWS SHOWN.");
                break;
            }
		}	
    }

    /**
     * Remove an existing show.
     */
    public void deleteShow(){
        if (showHandler.getAllShows().size() != 0 ){
            ArrayList<Show> allShows = ShowHandler.getInstance().getAllShows();
            ShowHandler.printAllShows(allShows);
            Show selectedShow = UtilityInputs.getShow(allShows);
            ShowHandler.getInstance().removeShow(selectedShow);
            UtilityOutput.printMessage("DELETED! The following Show has been deleted!");
            UtilityOutput.printMessage(selectedShow.toString());
        }else{
            UtilityOutput.printMessage("NO SHOWS SHOWN.");
        }
    }

    /**
     * View all cineplexes.
     */
    public void showAllCineplexes(){
        CineplexHandler cineplexHandler = CineplexHandler.getInstance();
        if (cineplexHandler.getAllCineplex().size() != 0 ){
            cineplexHandler.printAllCineplex();
        }
        else{
            UtilityOutput.printMessage("NO CINEPLEX SHOWN.");
        }
    }

    /**
     * Create a new cineplex.
     */
    public void addNewCineplex(){
        CineplexHandler cineplexHandler = CineplexHandler.getInstance();
        UtilityOutput.printInputMessage("Enter Cineplex Name [Enter '0' to exit] => ");
        String location = UtilityInputs.getStringUserInput();
        if (location.equals("0")) {
            return;
        }
        cineplexHandler.addCineplex(location, CinemaHandler.getInstance());
        UtilityOutput.printMessage("SUCCESS! " + location + " Cineplex has been added!");
        cineplexHandler.printAllCineplex();
    }

    /**
     * Update an existing cineplex.
     */
    public void updateCineplex(){
        CineplexHandler cineplexHandler = CineplexHandler.getInstance();
        if (cineplexHandler.getAllCineplex().size() != 0 ){
            cineplexHandler.printAllCineplex();
        }else{
            UtilityOutput.printMessage("NO CINEPLEX SHOWN");
            return;
        }
        int cineplexOption = -1;
        while(true){
            UtilityOutput.printInputMessage("Enter CineplexID [Enter '0' to exit] => ");
            cineplexOption = UtilityInputs.getIntUserInput();
            if (cineplexOption == 0) return;
            else if (cineplexOption == -1){
                UtilityOutput.printMessage("Please re-enter.");
                continue;
            }
            else if (cineplexOption < -1 || cineplexOption > cineplexHandler.getSize()){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else{
                Cineplex c = CineplexHandler.getInstance().getAllCineplex().get(cineplexOption-1);
                UtilityOutput.printInputMessage("Enter New Value => ");
                String cineplexUpdateString = UtilityInputs.getStringUserInput();
                UtilityOutput.printMessage("UPDATED! " + c.getLocation() + " has been updated to " + cineplexUpdateString + " !");
                c.setCineplex(cineplexUpdateString);
                UtilityOutput.printMessage(c.toString());
                break;
            }
        }
    }

    /**
     * Remove an existing movie cineplex.
     */
    public void removeCineplex(){
        CineplexHandler cineplexHandler = CineplexHandler.getInstance();
        if (cineplexHandler.getAllCineplex().size() != 0 ){
            cineplexHandler.printAllCineplex();
        }else{
            UtilityOutput.printMessage("NO CINEPLEX SHOWN");
            return;
        }
        while (true){
            UtilityOutput.printInputMessage("Enter CineplexID [Enter '0' to exit] => ");
            int choice = UtilityInputs.getIntUserInput();
            if (choice == 0) return;
            else if (choice == -1){
                UtilityOutput.printMessage("Please re-enter.");
                continue;
            }
            else if (choice < -1){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else{
                CineplexHandler.getInstance().removeCineplex(choice);
                break;
            }
        }
    }

    /**
     * View all cinemas.
     */    
    public void showAllCinemas(){
        CineplexHandler cineplexHandler = CineplexHandler.getInstance();
        int cineplexOption = -1;
        if (cineplexHandler.getAllCineplex().size() != 0 ){
            cineplexHandler.printAllCineplex();
            while (true){
                UtilityOutput.printInputMessage("Enter CineplexID [Enter '0' to exit] => ");
                cineplexOption = UtilityInputs.getIntUserInput();
                if (cineplexOption == 0) return;
                else if (cineplexOption == -1){
                    UtilityOutput.printMessage("Please re-enter.");
                    continue;
                }
                else if (cineplexOption < -1 || cineplexOption > cineplexHandler.getAllCineplex().size()){
                    UtilityOutput.printMessage("Invalid input. Please re-enter.");
                    continue;
                }
                else{
                    break;
                }
            }
            Cineplex cineplex = cineplexHandler.getAllCineplex().get(cineplexOption-1);
            if (CinemaHandler.getInstance().getCinemaFromCineplex(cineplex).size() != 0){
                for (Cinema c : CinemaHandler.getInstance().getCinemaFromCineplex(cineplex)){
                    UtilityOutput.printMessage(c.getCinemaID() + ". " + c.getCinemaClass() + " => " + c.getCapacity() + " seats");
                }
            }
            else{
                UtilityOutput.printMessage("NO CINEMAS SHOWN.");
            }
        }
        else{
            UtilityOutput.printMessage("NO CINEPLEX SHOWN");
        }
    }

    /**
     * Create a new cinema.
     */
    public void addNewCinema(){
        CineplexHandler cineplexHandler = CineplexHandler.getInstance();
        if (cineplexHandler.getAllCineplex().size() != 0 ){
            cineplexHandler.printAllCineplex();
        }else{
            UtilityOutput.printMessage("NO CINEPLEX SHOWN");
            return;
        }
        int userChoice = -1;
        while (true){
            UtilityOutput.printInputMessage("Enter CineplexID [Enter '0' to exit] => ");
            userChoice = UtilityInputs.getIntUserInput();
            if (userChoice == 0) return;
            else if (userChoice == -1){
                UtilityOutput.printMessage("Please re-enter.");
                continue;
            }
            else if (userChoice < -1 || userChoice > cineplexHandler.getSize()){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else{
                break;
            }
        }
        Cineplex c = CineplexHandler.getInstance().getAllCineplex().get(userChoice - 1);
        int x = 1;
        for (HallType hall : HallType.values()){
            UtilityOutput.printMessage(Integer.toString(x) + ". " + hall);
            x++;
        }
        HallType type = null;
        while (type == null){
            UtilityOutput.printInputMessage("Enter Hall Type [Enter '0' to exit] => ");
            int choice = UtilityInputs.getIntUserInput();
            try{
                switch(choice){
                    case 0:
                        return;
                    case 1:
                        type = HallType.STANDARD;
                        break;
                    case 2:
                        type = HallType.PREMIUM;
                        break;
                    case 3: 
                        type = HallType.VIP;
                        break;
                    case 4:
                        type = HallType.IMAX_3D;
                        break;
                    default:
                        throw new InvalidInputException("Please enter only numbers listed on the menu only");
                    }
                } catch(InvalidInputException e){
                    UtilityOutput.printMessage(e.getMessage());
                }
            }
            int cap = 0;
            while (true){
                UtilityOutput.printInputMessage("Enter Seat Capacity => ");
                cap = UtilityInputs.getIntUserInput();
                if (cap > 0) break;
                else {
                    UtilityOutput.printMessage("Please re-enter.");
                }
            }
            CinemaHandler.getInstance().addCinema(type, cap, c);
            UtilityOutput.printMessage("SUCCESS! The Cinema has been created!");
    }

    /**
     * Update an existing cinema.
     */
    public void updateCinema(){
        CineplexHandler cineplexHandler = CineplexHandler.getInstance();
        if (cineplexHandler.getAllCineplex().size() != 0 ){
            cineplexHandler.printAllCineplex();
        }else{
            UtilityOutput.printMessage("NO CINEPLEX SHOWN");
            return;
        }
        int userChoice = -1;
        while (true){
            UtilityOutput.printInputMessage("Enter CineplexID [Enter '0' to exit] => ");
            userChoice = UtilityInputs.getIntUserInput();
            if (userChoice == 0) return;
            else if (userChoice == -1){
                UtilityOutput.printMessage("Please re-enter.");
                continue;
            }
            else if (userChoice < -1 || userChoice > cineplexHandler.getSize()){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else{
                break;
            }
        }
        Cineplex c = CineplexHandler.getInstance().getAllCineplex().get(userChoice - 1);
        if (CinemaHandler.getInstance().getCinemaFromCineplex(c).size() != 0){
            for (Cinema cinema : CinemaHandler.getInstance().getCinemaFromCineplex(c)){
                UtilityOutput.printMessage(cinema.getCinemaID() + ". " + cinema.getCinemaClass() + " => " + cinema.getCapacity() + " seats");
            }
        }else{
            UtilityOutput.printMessage("NO CINEMA SHOWN");
            return;
        }
        int choice = -1;
        while (true){
            UtilityOutput.printInputMessage("Enter CinemaID [Enter '0' to exit] => ");
            choice = UtilityInputs.getIntUserInput();
            if (choice == 0) return;
            else if (choice == -1){
                UtilityOutput.printMessage("Please re-enter.");
                continue;
            }
            else if (choice < -1 || choice > CinemaHandler.getInstance().getSize()){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else{
                break;
            }
        }
        int cap = 0;
        while (true){
            UtilityOutput.printInputMessage("Enter Seat Capacity => ");
            cap = UtilityInputs.getIntUserInput();
            if (cap > 0) break;
            else {
                UtilityOutput.printMessage("Please re-enter.");
            }
        }
        CinemaHandler.getInstance().updateCinema(choice, cap, c);
        UtilityOutput.printMessage("UPDATED! The Cinema has been updated!");
    }

    /**
     * Remove an existing movie cinema.
     */
    public void removeCinema(){
        CineplexHandler cineplexHandler = CineplexHandler.getInstance();
        if (cineplexHandler.getAllCineplex().size() != 0 ){
            cineplexHandler.printAllCineplex();
        }else{
            UtilityOutput.printMessage("NO CINEPLEX SHOWN");
            return;
        }
        int userChoice = -1;
        while (true){
            UtilityOutput.printInputMessage("Enter CinplexID [Enter '0' to exit] => ");
            userChoice = UtilityInputs.getIntUserInput();
            if (userChoice == 0) return;
            else if (userChoice == -1){
                UtilityOutput.printMessage("Please re-enter.");
                continue;
            }
            else if (userChoice < -1 || userChoice > cineplexHandler.getSize()){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else{
                break;
            }
        }
        Cineplex cineplex = CineplexHandler.getInstance().getAllCineplex().get(userChoice - 1);
        if (CinemaHandler.getInstance().getCinemaFromCineplex(cineplex).size() != 0){
            for (Cinema cinema : CinemaHandler.getInstance().getCinemaFromCineplex(cineplex)){
                UtilityOutput.printMessage(cinema.getCinemaID() + ". " + cinema.getCinemaClass() + " => " + cinema.getCapacity() + " seats");
            }
        }else{
            UtilityOutput.printMessage("NO CINEMA SHOWN");
            return;
        }
        int cinemaOption = -1;
        while (true){
            UtilityOutput.printInputMessage("Enter CinemaID [Enter '0' to exit] => ");
            cinemaOption = UtilityInputs.getIntUserInput();
            if (cinemaOption == 0) return;
            else if (cinemaOption == -1){
                UtilityOutput.printMessage("Please re-enter.");
                continue;
            }
            else if (cinemaOption < -1){
                UtilityOutput.printMessage("Invalid input. Please re-enter.");
                continue;
            }
            else{
                break;
            }
        }
        CinemaHandler.getInstance().deleteCinema(cineplex, cinemaOption);
    }

    /**
     * Launch configure system settings.
     */    
    public void manageSettings() {
        settingsController.launch();
    }
}