package Moblima;

public class Review {
	private String Review;
	private User user;
	
	public Review(String review,User user) {
        this.Review = review;
        this.user=user;
    }

    public void SetReview(String review) {
        this.Review = review;

    }

    public String GetReview() {

        return this.Review;
    }
    public User GetUser() {

        return this.user;
    }

    @Override
    public String toString() {
        return "User: "+ this.user.getName()+'\n'+Review;
    }
}
