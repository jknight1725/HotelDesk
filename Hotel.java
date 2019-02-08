//Rhys Butler
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String hotelName;
    private ArrayList<Customer> customerList;
    private ArrayList<Reservation> reservationList;
    private ArrayList<Room> roomList;

    /*********************************
     * Default constructor for Hotel *
     * ******************************/
    public Hotel(){

        this.hotelName = "";
        this.customerList = new ArrayList<Customer>(20);
        this.reservationList = new ArrayList<Reservation>(20);
        this.roomList = new ArrayList<Room>(20);
    }//end default constructor.

    /**********************************
     * Name Hotel constructor         *
     * *******************************/
    public Hotel(String _hotelName){

        this.hotelName = _hotelName;
        this.customerList = new ArrayList<Customer>(20);
        this.reservationList = new ArrayList<Reservation>(20);
        this.roomList = new ArrayList<Room>(20);

    }//end name Hotel constructor

    /****************************************
     * Fully parameterized Hotel constructor*
     * *************************************/
    public Hotel(String _hotelName, ArrayList<Customer> _customerList, ArrayList<Reservation> _reservationList,
                 ArrayList<Room> _roomList){

        this.hotelName = _hotelName;
        this.customerList = _customerList;
        this.reservationList = _reservationList;
        this.roomList = _roomList;

    }//end parameterized Hotel constructor.

    /********************************************************
     * getHotelName(String _hotelName) returns the hotelName*
     * *****************************************************/
    public String getHotelName(){

        return this.hotelName;

    }//end getHotelName()

    /********************************************************
     * getCustomerList returns the customerList             *
     * *****************************************************/
    public ArrayList<Customer> getCustomerList(){

        return this.customerList;

    }//end getCustomerList()

    /********************************************************
     * getRoomList() returns the roomList                   *
     * *****************************************************/
    public ArrayList<Room> getRoomList(){

        return this.roomList;

    }//end getCustomerList()

    /********************************************************
     * getReservationList() returns the roomList            *
     * *****************************************************/
    public ArrayList<Reservation> getReservationList(){

        return this.reservationList;

    }//end getCustomerList()

    /***************************************
     *addCustomer(Customer _newCustomer)   *
     * adds a customer to the customerList *
     ***************************************/
    public boolean addCustomer(Customer _newCustomer){
        boolean addCheck = false;
        synchronized (this.customerList) {
            addCheck = this.customerList.add(_newCustomer);
        }
        return addCheck;
    }// end addCustomer()

    /***************************************
     *removeCustomer(Customer _oldCustomer)*
     * removes a customer from the         *
     * customerList                        *
     ***************************************/
    public boolean removeCustomer(Customer _oldCustomer){
        boolean removeCheck = false;
        synchronized (this.customerList) {
            try {
                removeCheck = this.customerList.remove(_oldCustomer);
            }catch(IndexOutOfBoundsException ioe){
                return false;
            }
        }
        return removeCheck;

    }//end removeCustomer()

    /***********************************************
     * addReservation(Reservation _newReservation  *
     * adds a Reservation to the reservationList   *
     * ********************************************/
    public boolean addReservation(Reservation _newReservation){
        boolean addCheck = false;
        synchronized (this.reservationList) {
            addCheck = this.reservationList.add(_newReservation);
        }
        return addCheck;

    }//end addReservation()

    /************************************************
     * removeReservation(Reservation _newReservation*
     * adds a Reservation to the reservationList    *
     * *********************************************/
    public boolean removeReservation(Reservation _oldReservation){

        boolean removeCheck = false;
        synchronized (this.reservationList) {
            try {
                this.reservationList.remove(_oldReservation);
            }catch(IndexOutOfBoundsException ioe){
                return false;
            }
        }
        return removeCheck;

    }//end addReservation()

    /***************************************
     *addRoom(Room _newRoom) adds a room to*
     * the roomList                        *
     ***************************************/
    public boolean addRoom(Room _newRoom){

        boolean addCheck = false;
        synchronized (this.reservationList) {
            this.roomList.add(_newRoom);
        }
        return addCheck;

    }//end addRoom()

    /**************************************************************
     * removeRoom(Room _oldRoom) removes a room from the room list*
     * ***********************************************************/
    public boolean removeRoom(Room _oldRoom){

        boolean removeCheck = false;
        synchronized (this.roomList) {
            try {
                this.roomList.remove(_oldRoom);
            }catch(IndexOutOfBoundsException ioe){
                return false;
            }
        }
        return removeCheck;

    }//end removeRoom()

}
