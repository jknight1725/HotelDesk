public class Customer {

    String firstName;
    String lastName;
    String address;
    String emailAddress;
    int idNumber;
    Reservation reservation;
    Room room;

    public Customer(){

        this.firstName = "";
        this.lastName = "";
        this.address = "";
        this.emailAddress = "";
        this.idNumber= 0;
        this.reservation = null;
        this.room = null;

    }

    public Customer(String _firstName, String _lastName, String _address, String _emailAddress,
                    int _idNumber){

        this.firstName = _firstName;
        this.lastName = _lastName;
        this.address = _address;
        this.emailAddress = _emailAddress;
        this.idNumber = _idNumber;

    }//end


    // Getters
    public int getIdNumber() { return idNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getEmailAddress() { return emailAddress; }
    public Reservation getReservation() { return reservation; }
    public Room getRoom() { return room; }

    // Setters
    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }
    public void setReservation(Room room) { this.room = room; }
    public void setRoom(Room _room) {this.room = _room;}


    /*******************************************************************
    //toString() returns a string representation of the Customer object*
    *******************************************************************/
    public String toString(){

        String customerString = "Name: " + this.firstName + " " + this.lastName + "\nAddress: \n" + this.address +
                "\nEmail: " + this.emailAddress + "\nID#: " + this.idNumber;

        return customerString;

    }//end toString()

}// end Customer()
