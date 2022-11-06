package Moblima;

public class Rating {

    private double rating;
    private User user;

    public Rating(double rating,User user) {
        this.rating = rating;
        this.user=user;
    }

    public void SetRating(double rating) {
        this.rating = rating;

    }

    public double GetRating() {

        return rating;
    }
    public User GetUser() {

        return this.user;
    }

}