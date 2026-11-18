package smartparking.dto;

public class ParkingSlot {

    private int slotNumber;
    private boolean occupied;
    private Vehicle vehicle;

    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.occupied = false;
        this.vehicle = null;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.occupied = true;
        
    }

    public void release() {
        this.occupied = false;
        //vehicle is changed to null 
        this.vehicle=null;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
