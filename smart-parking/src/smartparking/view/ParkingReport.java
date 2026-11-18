package smartparking.view;

import smartparking.dto.ParkingLot;
import smartparking.dto.ParkingSlot;
import smartparking.dto.ParkingTicket;
import smartparking.service.ParkingService;

public class ParkingReport {

    private ParkingLot parkingLot;
    private ParkingService service;

    public ParkingReport(ParkingLot parkingLot, ParkingService service) {
        this.parkingLot = parkingLot;
        this.service = service;
    }

    public int getOccupiedSlots() {
        int count = 0;
        for (ParkingSlot slot : parkingLot.getSlots()) {
            if (slot.isOccupied()) {
                count++;
            }
        }
        return count;
    }

    public int getAvailableSlots() {
        return parkingLot.getTotalSlots() - getOccupiedSlots();
    }

    public void printStatus() {
        System.out.println("   +---------------- " + parkingLot.getName() + " ----------------+");
        String slotList = "";
        for (ParkingSlot slot : parkingLot.getSlots()) {
            String who = "-";
            if (slot.getVehicle() != null) {
                who = slot.getVehicle().getRegistrationNumber();
            }
            String state = slot.isOccupied() ? "OCCUPIED" : "FREE";
            System.out.println("   | Slot " + slot.getSlotNumber() + "  " + pad(state, 9) + pad(who, 17) + "        |");
            if (!slotList.isEmpty()) {
                slotList = slotList + ",";
            }
            slotList = slotList + slot.getSlotNumber() + ":" + who + ":" + (slot.isOccupied() ? "OCC" : "FREE");
        }
        System.out.println("   | Available: " + getAvailableSlots() + "   Occupied: " + getOccupiedSlots()
                + "                |");
        System.out.println("   +-----------------------------------------------+");
    }

    public void printDailySummary() {
        System.out.println("\n===== DAILY SUMMARY =====");
        int total = 0;
        for (ParkingTicket ticket : service.getTickets()) {
            System.out.println("  " + ticket.getTicketId() + "  " + pad(ticket.getVehicle().getRegistrationNumber(), 12)
                    + " slot " + ticket.getSlotNumber() + "  " + formatTime(ticket.getEntryTime())
                    + " -> " + formatTime(ticket.getExitTime()) + "  Rs." + ticket.getFee());
            total = total + ticket.getFee();
        }
        System.out.println("  Total collected: Rs." + total);
    }

    public static String formatTime(int minutes) {
        int h = minutes / 60;
        int m = minutes % 60;
        return (h < 10 ? "0" : "") + h + ":" + (m < 10 ? "0" : "") + m;
    }

    private static String pad(String text, int width) {
        String result = text;
        while (result.length() < width) {
            result = result + " ";
        }
        return result;
    }
}
