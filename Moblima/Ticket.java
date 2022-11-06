package Moblima;

import java.util.Date;
import java.util.ArrayList;

public class Ticket {
	 private static int idCounter=0;
	    private int id;
	    private String owner;
	    private Date bookingTime;
	    private Seats seat_no;
	    private Show bookedShow;
		private Cinema cinemaSelected;
		private Double price;

	    public Ticket() {
	        idCounter += 1;
	        this.id = idCounter;
	    }

		public void setPrice(Double price){
			this.price = price;
		}

		public Double getPirce(){
			return this.price;
		}

		public void setCinema(Cinema cinemaSelected){
			this.cinemaSelected = cinemaSelected;
		}

		public Cinema getCinema(){
			return cinemaSelected;
		}

	    public String getTicketInfo(){
	        return null;
	    }

	    public String getOwner() {
	        return owner;
	    }

	    public void setOwner(String owner) {
	        this.owner = owner;
	    }

	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public Date getBookingTime() {
	        return bookingTime;
	    }

	    public void setBookingTime(Date bookingTime) {
	        this.bookingTime = bookingTime;
	    }

	    public Seats getSeat() {
	        return seat_no;
	    }

	    public void setSeat(Seats seat) {
	        this.seat_no = seat;
	    }

	    @Override
	    public String toString() {
	        return "Ticket{" +
	                "owner='" + owner  +
	                ", bookingTime=" + bookingTime +
	                ", Seats booked=" + seat_no +
	                ", bookedShow=" + bookedShow +
	                '}';
	    }

	    public Show getBookedShow() {
	        return bookedShow;
	    }

	    public void setBookedShow(Show bookedShow) {
	        this.bookedShow = bookedShow;
	    }
}
