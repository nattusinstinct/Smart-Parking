package smartparking.dto;

public class ParkingTicket {

    private String ticketId;
    private Vehicle vehicle;
    private int slotNumber;
    private int entryTime;
    private int exitTime;
    private int fee;
    private boolean closed;

    public ParkingTicket(String ticketId, Vehicle vehicle, int slotNumber, int entryTime) {
        this(ticketId, vehicle, slotNumber, entryTime, 0, 0, false);
    }

    public ParkingTicket(String ticketId, Vehicle vehicle, int slotNumber, int entryTime, int exitTime, int fee, boolean closed) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slotNumber = slotNumber;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.fee = fee;
        this.closed = closed;
    }

    public void close(int exitTime) {
        this.exitTime = exitTime;
        this.fee = getChargeableHours() * vehicle.getHourlyRate();
        this.closed=true;
    }

    public int getDurationMinutes() {
        return exitTime - entryTime;
    }

    public int getChargeableHours() {
        int minutes = getDurationMinutes();
        int hours = minutes / 60;
        if (hours < 1) {
            hours = 1;
        }
        return hours;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public int getEntryTime() {
        return entryTime;
    }

    public int getExitTime() {
        return exitTime;
    }

    public int getFee() {
        return fee;
    }

    public boolean isClosed() {
        return closed;
    }
}
