package Moblima;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeatHandler {
    private Map<Show, ArrayList<Seats>> allSeats;

    public SeatHandler(){
        allSeats = new HashMap<>();
    }

    public ArrayList<Seats> initializeSeats(Show show){
        ArrayList<Seats> newSeats = new ArrayList<Seats>();
        int cols = 10;
        int rows = show.getAvailableSeats() / cols;
        int remaining = show.getAvailableSeats() - (rows * cols);

        for (int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                Seats temp = new Seats(String.valueOf((char)(i + 65)), Integer.toString(j));
                newSeats.add(temp);
            }
        }
        for(int x = 0; x < remaining; x++){
            newSeats.add(new Seats(String.valueOf((char)(rows + 65)), Integer.toString(x)));
        }

        this.allSeats.put(show, newSeats);
        return newSeats;
    }

    public ArrayList<Seats> getSeatList(Show s){
        ArrayList<Seats> seats = null;
        for (Map.Entry<Show, ArrayList<Seats>> entry : this.allSeats.entrySet()) {
            if(s.getID() == entry.getKey().getID()){
                seats = entry.getValue();
            }
        }
        return seats;
    }

    public void printAvailableSeats(Show s){
        ArrayList<Seats> seats = getSeatList(s);
        
        System.out.println("Seats still available: ");
        int i = 0;
        int x = 10;

        if (seats.size() < 10){
            x = seats.size();
        }
        while (i < x){
            System.out.print("  ----   ");
            i++;
        }

        System.out.println();
        i = 0;
        for(Seats seat : seats){
            System.out.print("|  " + seat.getSeat() + "  | ");
            if((i+1) % 10 == 0 && i != 0){
                System.out.println("");
                i = 0;
                while (i < 10){
                    System.out.print("  ----   ");
                    i++;
                }
                System.out.println();
                
                i = -1;
            }
            i++;
        }
        System.out.println();
        for (int j = 0; j < i ; j++){
            System.out.print("  ----   ");
        }
    }

    public boolean checkSeatAvailability(Seats s1, Show s){
        ArrayList<Seats> seats = getSeatList(s);
        boolean available = false;
                        
        for (Seats o : seats){
            if (o.getSeat().equals(s1.getSeat())){
                available = true;
                break;
            }
        }
        return available;
    }

    public boolean checkCapacity(int numOfTickets, Show s){
        if (numOfTickets > s.getAvailableSeats()){
            return false;
        }else{
            return true;
        }
    }

    public void removeSeats(Seats seat, Show s){
        ArrayList<Seats> allSeats = getSeatList(s);

        System.out.println("removing seat: " + seat.getSeat());
        for (Seats seats : allSeats){
            if (seats.getSeat().equals(seat.getSeat())){
                seats.setRow("X");
                seats.setCol("X");
                break;
            }
        }

        this.allSeats.put(s, allSeats);
    }

    public static boolean duplicateSeatInput(Seats s1, ArrayList<Seats> chosenSeats){
        boolean duplicate = false;
        for (Seats o : chosenSeats){
            if (o.getSeat().equals(s1.getSeat())){
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }
}
