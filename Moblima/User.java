package Moblima;

import java.util.ArrayList;
public class User {
	private static int idCounter=0;
    private int id;
    private String name;
    private String email;
    private int number;
    public ArrayList<Ticket> bookingHistory;

    public User(String name,String email, int number) {
        idCounter += 1;
        this.id = idCounter;
        this.name = name;
        this.email=email;
        this.number=number;
        this.bookingHistory= new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public int getNumber() {
        return number;
    }
    public ArrayList<Ticket> getTickets(){
    	return bookingHistory;
    }
}
