import smartparking.controller.ParkingController;
import smartparking.dao.ParkingDAO;
import smartparking.dto.ParkingLot;
import smartparking.service.ParkingService;
import smartparking.view.ParkingReport;

public class Main {

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot("SMART PARKING", 5);
        ParkingDAO dao = new ParkingDAO();
        
        ParkingService service = new ParkingService(lot, dao.loadTickets());
        ParkingReport report = new ParkingReport(lot, service);
        ParkingController controller = new ParkingController(service, report, lot, dao);
        //bug 1
        System.out.println("=== "+lot.getName()+"===  (" + lot.getTotalSlots() + " slots)");
        controller.runMenu();
    }
}
