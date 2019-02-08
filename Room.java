import java.util.ArrayList;
import java.util.List;

// An Enum class
enum Status
{
    VACANT, OCCUPIED, OUT_OF_SERVICE;
}


public class Room {

    private int floor;
    private int roomNumber;
    private Status status;
    private static float rate;


    /**
     * Initializes the Room
     *
     * @param floor      floor for the room.
     * @param roomNumber room number for the room.
     * @param rate       price for room for one day.
     */
    public Room(int floor, int roomNumber, float rate) {
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.rate = rate;

    }

    public Room() {
    }

    // Getters
    public int getFloor() {
        return floor;
    }

    public int getRoomNumber() {
        return roomNumber;
    }



    public Status getStatus() {
        return status;
    }



    public static float getRate() {
        return rate;
    }

    // Setters
    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public void setRate(float rate) {
        this.rate = rate;
    }


    public String toString(){

        String roomString = "Room #: " + this.roomNumber + "\nFloor : " + this.floor+ "\nStatus: " + this.status
                + "\nRate: " + this.rate + "\n";

        return roomString;

    }//end toString()

}//end Room

