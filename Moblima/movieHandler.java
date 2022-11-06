package Moblima;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.*;
public class movieHandler {
	private ArrayList<Movie> movie;
	
	public movieHandler() {
		this.movie = new ArrayList<>();
	}
	
	public ArrayList<Movie> getMovie(){
		return movie;
	}
	
	public void sortByTicketSales() {
		Collections.sort(movie, new SortbyTicket().reversed());
    }
	
	public void sortByRatings() {
		Collections.sort(movie, new SortbyRating().reversed());
    }
	
	public void printAllMoviesNames() {
		int count =0;
		for (Movie temp : movie) {
			System.out.print(count+": ");
			temp.getName();
			count++;
		}
	}

	
}
