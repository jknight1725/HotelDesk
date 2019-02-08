//Cody Gardner
import java.awt.desktop.SystemSleepEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler implements Runnable {
    DataInputStream input;
    DataOutputStream output;
    Socket lonelySock;
    Hotel _mainHotel;

    public ClientHandler(Socket s, Hotel hotel) {
        try {
            this.lonelySock = s;
            this.input = new DataInputStream(lonelySock.getInputStream());
            this.output = new DataOutputStream(lonelySock.getOutputStream());
        } catch (IOException io) {
            System.out.println(io);
        }
        this._mainHotel = hotel;
    }

    public void run() {
        try {
            output.writeUTF("Main menu started");
            int menuControl = -1;
//          Scanner userInput = new Scanner(System.in);
            boolean isInt;


            while (menuControl != 0) {
                try {
                    output.writeUTF("Hotel: " + _mainHotel.getHotelName() + "\n");
                    displayMenu();
                    //Validate user input
                    do {
                        menuControl = userSelection(6);
                        isInt = true;
                    } while (!(isInt));

                    switch (menuControl) {
                        case 1:
                            System.out.println("To " + lonelySock.getInetAddress() + ": ADD GUEST:\n");
                            output.writeUTF("ADD GUEST:\n");
                            _mainHotel.addCustomer(addGuest());
                            break;

                        case 2:
                            if (_mainHotel.getCustomerList().isEmpty()) {
                                System.out.println("To " + lonelySock.getInetAddress() + ": Customer List is Empty...\n");
                                output.writeUTF("Customer List is Empty...\n");
                            } else {
                                System.out.println("To " + lonelySock.getInetAddress() + ": REMOVE GUEST:\n");
                                output.writeUTF("REMOVE GUEST:\n");
                                if (!_mainHotel.removeCustomer(removeCustomer(_mainHotel))) {
                                    System.out.println("To " + lonelySock.getInetAddress() + ": Error: Customer no longer available\n");
                                    output.writeUTF("Error: Customer no longer available\n");
                                }
                            }
                            break;

                        case 3:
                            System.out.println("To " + lonelySock.getInetAddress() + ": ADD RESERVATION:\n");
                            output.writeUTF("ADD RESERVATION:\n");
                            _mainHotel.addReservation(addReservation(_mainHotel));
                            break;

                        case 4:
                            if (_mainHotel.getReservationList().isEmpty()) {
                                System.out.println("To " + lonelySock.getInetAddress() + ": Error: Reservation List is Empty\n");
                                output.writeUTF("Error: Reservation List is Empty\n");
                            } else {
                                System.out.println("To " + lonelySock.getInetAddress() + ": REMOVE RESERVATION:\n");
                                output.writeUTF("REMOVE RESERVATION:\n");
                                if (!_mainHotel.removeReservation(removeReservation(_mainHotel))) {
                                    System.out.println("To " + lonelySock.getInetAddress() + ": Error: Reservation is" +
                                            " no Longer Available\n");
                                    output.writeUTF("Error: Reservation is no Longer Available\n");
                                }
                            }
                            break;

                        case 5:
                            System.out.println("To " + lonelySock.getInetAddress() + ": ADD ROOM:\n");
                            output.writeUTF("ADD ROOM:\n");
                            _mainHotel.addRoom(addRoom());
                            break;

                        case 6:
                            if (_mainHotel.getRoomList().isEmpty()) {
                                System.out.println("To " + lonelySock.getInetAddress() + ": Room List is Empty...\n");
                                output.writeUTF("Room List is Empty...\n");
                            } else {
                                System.out.println("To " + lonelySock.getInetAddress() + ": REMOVE ROOM:\n");
                                output.writeUTF("REMOVE ROOM:\n");
                                if (!_mainHotel.removeRoom(removeRoom(_mainHotel))) {
                                    System.out.println("To " + lonelySock.getInetAddress() + ": Error: Room no longer available\n");
                                    output.writeUTF("Error: Room no longer available\n");
                                }
                            }
                            break;

                        case 0:
                            System.out.println("To " + lonelySock.getInetAddress() + ": EXITING MAIN MENU");
                            output.writeUTF("EXITING MAIN MENU");
                            break;

                        default:
                            System.out.println("To " + lonelySock.getInetAddress() + ": There was an error attempting " +
                                    "to excuse the user's command.");
                            output.writeUTF("There was an error attempting to execute" +
                                    "the user's command.");
                            break;

                    }//end switch(menuControl)
                }catch(SocketException e){
                    System.out.println("Error: Lost connection with " + lonelySock.getInetAddress() + "\nClosing Socket");
                    menuControl = 0;
                }
            }//end (whileMenuControl == 0)
        }catch(IOException io){
            System.out.println(io);
        }

        //Properly close all sockets and streams before exiting thread
        try{
            input.close();
            output.close();
            lonelySock.close();
        }catch(IOException io){
            System.out.println(io);
        }
    }//end run()

    private int userSelection(int max){
        String str = "-1";
            while(Integer.valueOf(str) < 0 || Integer.valueOf(str) > max) {
                try {
                    System.out.println("To " + lonelySock.getInetAddress() + ": Enter a Selection");
                    output.writeUTF("Enter a Selection:  ");
                    str = this.input.readUTF();

                    if (Integer.valueOf(str) < 0) {
                        System.out.println("To " + lonelySock.getInetAddress() + ": Error: Minimum Value is 0...");
                        output.writeUTF("Error: Minimum Value is 0...");
                    } else if (Integer.valueOf(str) > max) {
                        System.out.println("To " + lonelySock.getInetAddress() + ": Error: Maximum Value is " + max);
                        output.writeUTF("Error: Maximum value is " + max);
                    }
                }catch(Exception e){
                    System.out.println("To " + lonelySock.getInetAddress() + ": Error: " + e.getMessage());
                    try {
                        output.writeUTF("Error: " + e.getMessage());
                    }catch(Exception q){
                        System.out.println(q.getMessage());
                    }
                }
            }
        return Integer.valueOf(str);
    }

    /****************************************************
     * displayMenu() prints the menu for the user       *
     ****************************************************/
    public void displayMenu(){
        try {
            System.out.println("To " + lonelySock.getInetAddress() + ": Please review the list of options and " +
                    "enter the number that corresponds to your choice.\n");
            output.writeUTF("Please review the list of options and enter the number\n" +
                    "that corresponds to your choice.\n");
            System.out.println("To " + lonelySock.getInetAddress() + ": OPTIONS:\n" + "1: ADD GUEST\n2: REMOVE GUEST\n" +
                    "3: ADD RESERVATION\n4: REMOVE RESERVATION\n5: ADD ROOM\n6: REMOVE ROOM\n0: EXIT MAIN MENU\n");
            output.writeUTF("OPTIONS:\n" + "1: ADD GUEST\n2: REMOVE GUEST\n3: ADD RESERVATION\n" +
                    "4: REMOVE RESERVATION\n5: ADD ROOM\n6: REMOVE ROOM\n0: EXIT MAIN MENU\n");
        }catch (IOException io){
            System.out.println(io);
        }
    }//end displayMenu()

    /*********************************************************
     * addGuest() allows the user to input guest information.*
     * A guest object is built from the user's specifications*
     * and added to the hotel.                               *
     * *****************************************************/
    public Customer addGuest(){
        Customer newGuest = null;
        try {
            System.out.println("To " + lonelySock.getInetAddress() + ": Please enter the guest's information");
            output.writeUTF("Please enter the guest's information");
            boolean isInt;
//            Scanner userInput = new Scanner(System.in);
            Room newGuestRoom = null;
            Reservation newGuestReservation = null;

            System.out.println("To " + lonelySock.getInetAddress() + ": FIRST NAME:\n");
            output.writeUTF("FIRST NAME:\n");
            String firstName = input.readUTF();

            System.out.println("To " + lonelySock.getInetAddress() + ": LAST NAME:\n");
            output.writeUTF("LAST NAME:\n");
            String lastName = input.readUTF();

            System.out.println("To " + lonelySock.getInetAddress() + ": ADDRESS:\n");
            output.writeUTF("ADDRESS:\n");
            String address = input.readUTF();

            System.out.println("To " + lonelySock.getInetAddress() + ": ID #:\n");
            output.writeUTF("ID #:\n");
            int idNumber = Integer.valueOf(input.readUTF());

            System.out.println("To " + lonelySock.getInetAddress() + ": EMAIL ADDRESS:\n");
            output.writeUTF("EMAIL ADDRESS:\n");
            //Close scanner to resolve errors at runtime where the scanner has
            //unwanted string due to input validation above.
//            userInput = null;
//            userInput = new Scanner(System.in);
            String emailAddress = input.readUTF();

            newGuest = new Customer(firstName, lastName, address, emailAddress, idNumber);

        }catch(IOException io){
            System.out.println(io);
        }
        return newGuest;

    }//end addGuest()

    /*****************************************************************************
     * addRoom() allows the user to create a room and have it added to the Hotel.*
     ****************************************************************************/
    public Room addRoom(){

        Room tempRoom = new Room();
        try {
            System.out.println("To " + lonelySock.getInetAddress() + ": Please enter the room information");
            output.writeUTF("Please enter the room information");
            boolean isInt;

            System.out.println("To " + lonelySock.getInetAddress() + ": FLOOR:\n");
            output.writeUTF("FLOOR:\n");
            int floorNum = Integer.valueOf(input.readUTF());

            output.writeUTF("ROOM NUMBER:\n");
            int roomNum = Integer.valueOf(input.readUTF());

            System.out.println("To " + lonelySock.getInetAddress() + ": ROOM STATUS:\n");
            System.out.println("To " + lonelySock.getInetAddress() + ": Please make a selection from one of the following:\n");
            System.out.println("To " + lonelySock.getInetAddress() + ": What is the STATUS of the room?\n\nROOM STATUS:" +
                    "\n\nVACANT\nOCCUPIED\nOUT OF SERVICE\n");
            System.out.println("To " + lonelySock.getInetAddress() + ": Please enter a V for VACANT a O for OCCUPIED" +
                    " or an OS for OUT OF SERVICE.\n");
            output.writeUTF("ROOM STATUS:\n");
            output.writeUTF("Please make a selection from one of the following:\n");
            output.writeUTF("What is the STATUS of  the room?\n\nROOM STATUS:\n\nVACANT\nOCCUPIED\nOUT OF SERVICE\n");
            output.writeUTF("Please enter a V for VACANT a O for OCCUPIED or an OS for OUT OF SERVICE.\n");
            //Clear scanner
//            userInput = null;
//            userInput = new Scanner(System.in);
            String roomStatus = input.readUTF();
            Status currentStatus = null;
            //Validate user's input
            while (!roomStatus.equals("V") && !roomStatus.equals("O") && !roomStatus.equals("OS")) {

                System.out.println("To " + lonelySock.getInetAddress() + ": Please enter either V, O, or OS.\n");
                output.writeUTF("Please enter either V, O, or OS.\n");
                roomStatus = input.readUTF();

            }//end (!roomStatus.equals("V") && !roomStatus.equals("O") && !roomStatus.equals("OS"))
            if (roomStatus.equals("V")) {

                currentStatus = Status.VACANT;

            }//end if(createRoom.equals("V"))
            if (roomStatus.equals("O")) {

                currentStatus = Status.OCCUPIED;

            }//end if(createRoom.equals("O"))
            if (roomStatus.equals("OS")) {

                currentStatus = Status.OUT_OF_SERVICE;

            }//end if(createRoom.equals("OS"))

            System.out.println("To " + lonelySock.getInetAddress() + ": ROOM RATE:\n");
            output.writeUTF("ROOM RATE:\n");
            boolean isFloat;
            Float roomRate = 0F;

            tempRoom.setRate(roomRate);
            tempRoom.setFloor(floorNum);
            tempRoom.setRoomNumber(roomNum);
            tempRoom.setStatus(currentStatus);
        }catch (IOException io){
            System.out.println(io);
        }
        return tempRoom;

    }//end addRoom()

    /*****************************************************************************
     * addReservation() allows the user to create a reservation and have it added*
     * to the Hotel.                                                             *
     ****************************************************************************/
    public Reservation addReservation(Hotel _mainHotel){

        Reservation tempReservation = null;
        int customerIndex = 1;
        boolean isInt;
//        Scanner userInput = new Scanner(System.in);

        try {
            System.out.println("To " + lonelySock.getInetAddress() + "Please select a customer to assign to this reservation.\n");
            output.writeUTF("Please select a customer to assign to this reservation.\n");
            System.out.println("To " + lonelySock.getInetAddress() + ": Please enter the number that appears next " +
                    "to the customer you would like to add\n");
            output.writeUTF("Please enter the number that appears next to the customer you would like to add\n" +
                    "for this reservation.\n");
            if (!_mainHotel.getCustomerList().isEmpty()) {

                for (Customer customer : _mainHotel.getCustomerList()) {

                    output.writeUTF(customerIndex + " " + customer.getFirstName() + " " + customer.getLastName());
                    customerIndex++;

                }//for(Customer customer : _mainHotel.getCustomerList())
                int customerChoice = userSelection(_mainHotel.getCustomerList().size());

                String dateInput = "";
                Date checkInDate = null;
                SimpleDateFormat dateParser = new SimpleDateFormat("MM-dd-yyyy HH:mm");
                System.out.println("To " + lonelySock.getInetAddress() + ": Please enter the check in date. " +
                        "format: MM-DD-YYYY HH:MM\nFor example: 05-26-1979 22:35 would be a valid format for the date\n");
                output.writeUTF("Please enter the check in date. format: MM-DD-YYYY HH:MM\n"
                        + "For example: 05-26-1979 22:35 would be a valid format for the date\n");

                do {
                    try {

                        dateInput = input.readUTF();
                        //dateParser will return null if there is an error.
                        checkInDate = dateParser.parse(dateInput);

                    } catch (Exception e) {

                        System.out.println("To " + lonelySock.getInetAddress() + ": The error while parsing the date " +
                                "string was: " + e.getMessage() + "\nPlease attempt to enter the date again in " +
                                "the format: MM-dd-yyyy HH:mm");
                        output.writeUTF("The error while parsing the date string was: " + e.getMessage() +
                                "\nPlease attempt to enter the date again in the format: MM-dd-yyyy HH:mm");

                    }//end catch(Exception e)

                } while (checkInDate == null); //Will continue until the date is parsed from the user's input.

                Date checkOutDate = null;
                System.out.println("To " + lonelySock.getInetAddress() + ": Please enter the check out date. format: " +
                        "MM-DD-YYYY HH:MM\nFor example: 05-26-1979 22:35 would be a valid format for the date\n");
                output.writeUTF("Please enter the check out date. format: MM-DD-YYYY HH:MM\n"
                        + "For example: 05-26-1979 22:35 would be a valid format for the date\n");

                do {
                    try {

                        dateInput = input.readUTF();
                        //dateParser will return null if there is an error.
                        checkOutDate = dateParser.parse(dateInput);

                    } catch (Exception e) {

                        System.out.println("To " + lonelySock.getInetAddress() + ": The error while parsing the date " +
                                "string was: " + e.getMessage() + "\nPlease attempt to enter the date again in " +
                                "the format: MM-dd-yyyy HH:mm");
                        output.writeUTF("The error while parsing the date string was: " + e.getMessage() +
                                "\nPlease attempt to enter the date again in the format: MM-dd-yyyy HH:mm");

                    }//end catch(Exception e)

                } while (checkOutDate == null); //Will continue until the date is parsed from the user's input.

                //Keep track of index of room in order to get user input.
                int roomIndex = 0;

                System.out.println("To " + lonelySock.getInetAddress() + ": Please select a room to assign to this" +
                        " reservation.\n");
                output.writeUTF("Please select a room to assign to this reservation.\n");
                System.out.println("To " + lonelySock.getInetAddress() + ": Please enter the number that appears next" +
                        " to the room you would like to add for this reservation.\n");
                output.writeUTF("Please enter the number that appears next to the room you would like to add " +
                        "for this reservation.\n");
                if (!_mainHotel.getRoomList().isEmpty()) {

                    for (Room room : _mainHotel.getRoomList()) {
                        System.out.println("To " + lonelySock.getInetAddress() + ": " + roomIndex + 1 + ": " +
                                room.getRoomNumber());
                        output.writeUTF(roomIndex + 1 + ": " + room.getRoomNumber());

                        roomIndex++;

                    }//for(Customer customer : _mainHotel.getCustomerList())
                    int roomChoice = userSelection(_mainHotel.getRoomList().size());

                    tempReservation = new Reservation(_mainHotel.getCustomerList().get(customerChoice - 1),
                            checkInDate, checkOutDate, 0F, _mainHotel.getRoomList().get(roomChoice - 1));

                    float tempReservationPrice = tempReservation.calculatePrice(tempReservation.getCheckIn(),
                            tempReservation.getCheckOut(), tempReservation.getRoom());

                    tempReservation.setPrice(tempReservationPrice);

                }//if (_mainHotel.getRoomList() != null)
                else {

                    System.out.println("To " + lonelySock.getInetAddress() + ": The room list is empty." +
                            " You can't have a hotel with no rooms silly!!");
                    output.writeUTF("The room list is empty. You can't have a hotel with no rooms silly!!");

                }//end else roomList is empty

            }//end if Customer list is not empty
            else {

                System.out.println("To " + lonelySock.getInetAddress() + ": There are no customers in the customerList.");
                output.writeUTF("There are no customers in the customerList.");
                System.out.println("To " + lonelySock.getInetAddress() + ": Please add a customer to the " +
                        "list before creating any reservations!!");
                output.writeUTF("Please add a customer to the list before creating any reservations!!");

            }//else the customer list is empty

        }catch (IOException io){
            System.out.println(io);
        }//end of try/catch

        return tempReservation;

    }//end addReservation()

    /*****************************************************************************
     * removeReservation() allows the user to remove a reservation from the hotel*
     * records.                                                                  *
     ****************************************************************************/
    public Reservation removeReservation(Hotel _mainHotel){

        int reservationIndex = 0;
        int reservationChoice = 0;
        boolean isInt;
        Reservation res = new Reservation();
//        Scanner userInput = new Scanner(System.in);

        try {
            System.out.println("To " + lonelySock.getInetAddress() + ": Please select a RESERVATION you would like to" +
                            " remove from the following list.");
            output.writeUTF("Please select the RESERVATION you would like to remove from the following list.");
            System.out.println("To " + lonelySock.getInetAddress() + ": RESERVATIONS:\n");
            output.writeUTF("RESERVATIONS:\n");

            for (Reservation reservation : _mainHotel.getReservationList()) {

                System.out.println("To " + lonelySock.getInetAddress() + ": " + (reservationIndex + 1) + ": " +
                    reservation.toString());
                output.writeUTF((reservationIndex + 1) + ": " + reservation.toString());
                reservationIndex++;

            }//end for(Reservation reservation : _mainHotel.getReservationList()
            //Validate user input
            reservationChoice = userSelection(_mainHotel.getReservationList().size());

            try {
                res = _mainHotel.getReservationList().get(reservationChoice - 1);
            }catch(IndexOutOfBoundsException ioe){
                System.out.println("To " + lonelySock.getInetAddress() + ": Error: Room no longer available\n");
                output.writeUTF("Error: Room no longer available\n");
                return null;
            }
        }catch (IOException io){
            System.out.println(io);
        }

        return res;

    }//end removeReservation(Hotel _mainHotel)

    /*****************************************************************************
     * removeRoom() allows the user to remove a room from the hotel              *
     * records.                                                                  *
     ****************************************************************************/
    public Room removeRoom(Hotel _mainHotel){
        int roomIndex = 0;
        int roomChoice = 0;
        Room tempRoom = new Room();
        boolean isInt;
//        Scanner userInput = new Scanner(System.in);

        try {
            System.out.println("To " + lonelySock.getInetAddress() + ": Please select the ROOM you would like to" +
                    "remove from the following list.");
            output.writeUTF("Please select the ROOM you would like to remove from the following list.");
            System.out.println("To " + lonelySock.getInetAddress() + ": ROOMS:\n");
            output.writeUTF("ROOMS:\n");

            for (Room room : _mainHotel.getRoomList()) {

                System.out.println("To " + lonelySock.getInetAddress() + ": " + (roomIndex + 1) + ": " + room.toString());
                output.writeUTF((roomIndex + 1) + ": " + room.toString());
                roomIndex++;

            }//end for(Room room : _mainHotel.getRoomList())
            roomChoice = userSelection(_mainHotel.getRoomList().size());



            try {
                tempRoom = _mainHotel.getRoomList().get(roomChoice - 1);
            }catch(IndexOutOfBoundsException ioe){
             System.out.println("To " + lonelySock.getInetAddress() + ": Error: Room no longer available\n");
             output.writeUTF("Error: Room no longer available\n");
             return null;
            }
        }catch(IOException io){
            System.out.println(io);
        }
        return tempRoom;
    }//end removeRoom(Hotel _mainHotel)

    public Customer removeCustomer(Hotel _mainHotel){
        int customerIndex = 0;
        int customerChoice = 0;
        Customer tempCustomer = new Customer();

        try {
            System.out.println("To " + lonelySock.getInetAddress() + ":  Please select the GUEST you would like to" +
                    " remove from the following list.");
            output.writeUTF("Please select the GUEST you would like to remove from the following list.");
            System.out.println("To " + lonelySock.getInetAddress() + ":  GUESTS:\n");
            output.writeUTF("GUESTS:\n");

            for (Customer customer : _mainHotel.getCustomerList()) {

                System.out.println("To: " + lonelySock.getInetAddress() + ": " + (customerIndex + 1) + ": "
                        + customer.toString());
                output.writeUTF((customerIndex + 1) + ": " + customer.toString());
                customerIndex++;

            }//end for(Customer customer : _mainHotel.getCustomerList())
            customerChoice = userSelection(_mainHotel.getCustomerList().size());
            try {
                tempCustomer = _mainHotel.getCustomerList().get(customerChoice - 1);
            }catch(IndexOutOfBoundsException ioe){
                System.out.println("To " + lonelySock.getInetAddress() + ": Error: Customer no longer available\n");
                output.writeUTF("Error: Customer no longer available\n");
            }
        }catch(IOException io){
            System.out.println(io);
        }
        return tempCustomer;

    }//end removeRoom(Hotel _mainHotel)
}
