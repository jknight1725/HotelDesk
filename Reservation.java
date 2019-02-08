import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.time.temporal.ChronoUnit;
import java.util.Date;
public class Reservation {

    private Customer customer;
    private Date checkIn;
    private Date checkOut;
    private float price;
    private Room room;


    /**
     * Initializes the Reservation
     * @param customer Customer object.
     * @param checkIn customer check in date.
     * @param checkOut  customer checkout date.
     * @param price price for customer's total stay.
     */
    public Reservation(Customer customer,Date  checkIn, Date  checkOut,
                       float price, Room room){

        this.customer = customer;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.price = price;
        this.room = room;

    }

    public Reservation(){

    }//end Reservation()

    // Getters
    public Customer getCustomerr() { return customer; }
    public Date  getCheckIn() { return checkIn; }
    public Date  getCheckOut() { return checkOut; }
    public float getPrice() { return price; }
    public Room getRoom() {return room;}

    // Setters
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setCheckIn(Date  checkIn) { this.checkIn = checkIn; }
    public void setCheckOut(Date  checkOut) {
        this.checkOut = checkOut;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public void setRoom(Room room) { this.room = room; }

    public float calculatePrice( Date checkIn, Date checkOut,  Room room){

        long days = ChronoUnit.DAYS.between(checkIn.toInstant(), checkOut.toInstant());
        float price1 =  room.getRate() * days;
        return price1;


    }//end CalculatePrice

    public String toString(){


        LocalDateTime checkInDate = this.checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime checkOutDate = this.checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        float price = this.calculatePrice(this.checkIn, this.checkOut, this.room);
        String reservationString = "RESERVATION INFO:\nCustomer:\n" + this.customer.toString();
        reservationString = reservationString + "\nCheck In: " + checkInDate + "\nCheck Out: " + checkOutDate +
                "\nPrice: " + price;
        reservationString = reservationString + "\nRoom: " + this.room.toString() + "\n";

        return reservationString;


    }//end toString()





}

