package Moblima;
import java.util.ArrayList;

public class Booking {
    private int studentTicket;
    private int adultTicket;
    private Show selectedShow;
    private ArrayList<Seats> seatList;
    private User user;
    private double adultPrice;
    private double studentPrice;

    public Booking(User user){
        seatList = new ArrayList<>();
        studentTicket = 0;
        adultTicket = 0;
        studentPrice = 0.0;
        adultPrice = 0.0;
        selectedShow = null;
        this.user = user;
    }

    public double getAdultPrice(){
        return this.adultPrice;
    }

    public void setAdultPrice(Double price){
        this.adultPrice = price;
    }

    public double getStudentPrice(){
        return this.studentPrice;
    }

    public void setStudentPrice(Double price){
        this.studentPrice = price;
    }

    public User getUser(){
        return this.user;
    }

    public int getStudentTicketNum(){
        return this.studentTicket;
    }

    public void setStudentTicket(int num){
        this.studentTicket = num;
    } 

    public int getAdultTicketNum(){
        return this.adultTicket;
    }

    public void setAdultTicket(int num){
        this.adultTicket = num;
    } 

    public ArrayList<Seats> getSeats() {
        return seatList;
    }

    public void setSeats(ArrayList<Seats> seats){
        this.seatList = seats;
    }

    public void setShow(Show show){
        this.selectedShow = show;
    }

    public Show getShow(){
        return this.selectedShow;
    }

/* 
    private boolean duplicateInput(Seats s1, ArrayList<Seats> chosenSeats){
        boolean duplicate = false;
                        
        for (Seats o : chosenSeats){
            if (o.getSeat().equals(s1.getSeat())){
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }

    public ArrayList<Seats> selectSeats(Show showSelected, SeatHandler seatHandler){
        seatHandler.printAvailableSeats(showSelected);

        ArrayList<Seats> chosenSeats = new ArrayList<Seats>();
        while (true){
            int seatNum = getNumberOfTicket();
            if (seatNum == 0) return null;

            if (seatHandler.checkCapacity(seatNum, showSelected)){
                for(int j = 0; j < seatNum; j++){
                    while (true){
                        System.out.print("Seat number for Ticket " + Integer.toString(j+1) + ": ");
                        String seats = scanner.nextLine();

                        if (seats == "XX" || seats.length() != 2){
                            System.out.println("Invalid seat");
                            continue;

                        } else if (seats == "0") return null;

                        String row = String.valueOf(seats.charAt(0)).toUpperCase();
                        String col = String.valueOf(seats.charAt(1));
                        Seats s1 = new Seats(row, col);

                        if (seatHandler.checkSeatAvailability(s1, showSelected) && !duplicateInput(s1, chosenSeats)){
                            chosenSeats.add(s1);
                            break;
                        } else{
                            System.out.println("Seat is not available, please try again");
                            System.out.println("Enter 0 to exit");
                        }
                        
                        if (chosenSeats.size() == seatNum) break;
                    }
                }
                break;
            }
        }
        return chosenSeats;
    }

    public int seatConfirmation(ArrayList<Seats> seatList, Show showSelected, SeatHandler seatHandler){
        while(true){
            System.out.print("Confirm ticket booking (Y/N): ");
            char confirmation = scanner.next().charAt(0);
            if (Character.toUpperCase(confirmation) == 'Y'){
                for (Seats s: seatList){
                    seatHandler.removeSeats(s, showSelected);
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

    public Show selectShow(ArrayList<Show> allShows){
        while (true){
            System.out.print("Please enter the show number to watch [0 to exit] => ");
            int choice = getIntUserInput();
            
            if (choice == 0){
                break;
            } else if (choice > allShows.size() || choice < 0){
                System.out.println("Input out of range");
            } else {
                for(Show s: allShows){
                    if(s.getID() == choice){
                        return s;
                    }
                }
                System.out.println("Invalid input, please try again");
            }
        }
        return null;
    }

    public ArrayList<Ticket> bookSeats(Show showSelected, User user1, SeatHandler seatHandler){
        ArrayList<Seats> seatlist = selectSeats(showSelected, seatHandler);
        int confirmation = 0;
        if (seatlist != null){
            confirmation = seatConfirmation(seatlist, showSelected, seatHandler);
        }
        ArrayList<Ticket> ticketList = new ArrayList<>();
        if (confirmation == 1){
            for(Seats s: seatlist) ticketList.add(showSelected.bookTicket(user1, s));
        }
        return ticketList;
    }
    */
}

