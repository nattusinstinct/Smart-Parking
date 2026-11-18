package smartparking.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import smartparking.dao.ParkingDAO;
import smartparking.dto.ParkingLot;
import smartparking.dto.Vehicle;
import smartparking.service.ParkingService;
import smartparking.view.ParkingReport;

public class ParkingController {

    private ParkingService service;
    private ParkingReport report;
    private ParkingLot parkingLot;
    private ParkingDAO dao;

    public ParkingController(ParkingService service, ParkingReport report, ParkingLot parkingLot, ParkingDAO dao) {
        this.service = service;
        this.report = report;
        this.parkingLot = parkingLot;
        this.dao = dao;
    }

    public void runMenu() {
        Scanner input = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n1) Park a vehicle   \n2) Exit a vehicle   \n3) Show status   \n4) Daily summary   \n0) Quit");
            System.out.print("Choose: ");
             int choice ; 
            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input type !");
                break;
            }
           
            input.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Registration number: ");
                    String reg = input.nextLine().trim();
                    System.out.print("Type (CAR/BIKE): ");
                    String type = input.nextLine().trim();
                    loop:while (true) { 
                    System.out.print("Arrival time (HH:MM): ");
                    String time = input.nextLine().trim();
                    boolean ans = isValidTime(time,"HH:mm");
                    if(ans==true){
                         service.parkVehicle(new Vehicle(reg, type), toMinutes(time));
                         break loop;
                    }
                    }
                   
                    report.printStatus();
                    dao.saveTickets(service.getTickets());
                    break;
                case 2:
                    System.out.print("Registration number: ");
                    String exitReg = input.nextLine().trim();
                    loop:while (true) { 
                    System.out.print("Exit time (HH:MM): ");
                    String exitTime = input.nextLine().trim();
                    boolean res = isValidTime(exitTime,"HH:mm");
                    if(res==true){
                        service.exitVehicle(exitReg, toMinutes(exitTime));
                        break loop;
                    }
                }
                    report.printStatus();
                    dao.saveTickets(service.getTickets());
                    break;
                case 3:
                    report.printStatus();
                    break;
                case 4:
                    report.printDailySummary();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option: " + choice);
            }
        }
    }

    public static int toMinutes(String time) {
        String[] hm = time.split(":");
        //changed into 100 to 60
        return Integer.parseInt(hm[0]) * 60 + Integer.parseInt(hm[1]);
    }

   public static boolean isValidTime(String time, String format) {
    try {
        LocalTime.parse(time, DateTimeFormatter.ofPattern(format));
        return true;
    } catch (Exception e) {
        System.out.println("Invalid Time Input formate ");
        return false;
    }
}
}
