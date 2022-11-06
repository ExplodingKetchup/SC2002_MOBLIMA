package Moblima;

import java.util.ArrayList;

public class Movie  {
	 	private String name;
	    private String status;
	    private String synopsis;
	    private String director;
	    private String cast;
	    private String review;
	    private ArrayList<Rating> ratings;
	    private ArrayList<Review> reviews;
	    private movieHandler movieH;
	    private double Ratings;
	    public ArrayList<Ticket> ticketlist;

	    public Movie(String name,String status, String director, String synopsis, String cast, movieHandler movieH) {
	        this.name = name;
	        this.status = status;
	        this.director = director;
	        this.synopsis = synopsis;
	        this.cast = cast;
	        this.ratings = new ArrayList<>();
	        this.reviews = new ArrayList<>();
	        this.ticketlist=new ArrayList<>();
	        movieH.getMovie().add(this);
	    }
	    
	    /**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * @return the synopsis
		 */
		public String getSynopsis() {
			return synopsis;
		}

		/**
		 * @return the director
		 */
		public String getDirector() {
			return director;
		}

		/**
		 * @return the cast
		 */
		public String getCast() {
			return cast;
		}

		/**
		 * @return the ratings
		 */
		public double getRatings() {
			return Ratings;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @param director the director to set
		 */
		public void setDirector(String director) {
			this.director = director;
		}

		/**
		 * @param review the review to set
		 */
		public void setReview(String review) {
			this.review = review;
		}

		/**
		 * @param ratings the ratings to set
		 */
		public void setRatings(double ratings) {
			Ratings = ratings;
		}

		public void setSynopsis(String synopsis) {
	    	this.synopsis=synopsis;
	    }
	    
	    public void setCast(String cast) {
	    	this.cast=cast;
	    }

	    public String getName() {
	        return name;
	    }

	    public void addRatings(Rating R) {
	        this.ratings.add(R);
	    }
	    public void addReview(Review R) {
	        this.reviews.add(R);
	    }
	    
	    public ArrayList<Review> getReview(){
	        return reviews;
	    }
	    
	    public void addticket(Ticket ticket) {
	    	ticketlist.add(ticket);
	    }
	    
	    public int getTicketsSize() {
	    	return ticketlist.size();
	    }
	    
	    public double getAverageRatings() {
	    	Ratings=0;
	    	if(ratings.size()<=1) {
	    		return -1;
	    	}
	    	else {
	    		for (Rating temp : ratings) {
	    			Ratings+=temp.GetRating();}
	    		return Ratings/ratings.size();
	    	}
	    }
	    
	    @Override
	    public String toString() {
	        return "Title: " + name + "\n" +
	                "Status: " + status + "\n" +
	                "Director: " + director + "\n" +
	                "Cast: " + cast + "\n" +
	                "Synopsis: " + synopsis + "\n"
	                ;
	    }
}
