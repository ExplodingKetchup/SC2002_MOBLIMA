package Moblima;

import java.util.ArrayList;

public class cinemaHandler {
	
	private ArrayList<Cinema> allCinema;
	private String cineplexName;
	
	public cinemaHandler(String cineplexName) {
		allCinema = new ArrayList<Cinema>();
		this.cineplexName = cineplexName;
	}
	
	public void addCinema(String classtype, int seat_capacity, Cineplex cineplex) {
		Cinema newCinema = new Cinema(classtype,seat_capacity, cineplex);
		allCinema.add(newCinema);
	}
	
	public void printCinema() {
		for (Cinema temp : allCinema) {
			System.out.println(temp);
		}
	}
	
	public ArrayList<Cinema> getAllCinema(){
		return allCinema;
	}
}
