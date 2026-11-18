package smartparking.controller;

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
            System.out.println("\n1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit");
            System.out.print("Choose: ");
            int choice = input.nextInt();
            //bug 2
            input.nextLine();
            switch (choice) {
                case 1:
                    //.matches("[A-Z]{2} [0-9]{2} [A-Z]{2} [0-9]{4}")
                    String reg;
                    do {
                        System.out.print("Registration number: ");
                        reg = input.nextLine().trim().toUpperCase();
                        if (!reg.matches("[A-Z]{2} [0-9]{2} [A-Z]{2} [0-9]{4}")) {
                            System.out.println("Invalid registration number format. Use TN 81 AZ 2345.");
                        }
                    } while (!reg.matches("[A-Z]{2} [0-9]{2} [A-Z]{2} [0-9]{4}"));
                    
                    //this is not bug i will just replace it 
                    int choose;
                    String type;
                    do {
                        System.out.print("Type\n\t1 -> CAR\n\t2 -> BIKE\nChoose: ");
                        choose = input.nextInt();
                        input.nextLine();
                switch (choose) {
                    case 1 -> type = "CAR";
                    case 2 -> type = "BIKE";
                    default -> {
                        type = "";
                        System.out.println("Unknown vehicle type: " + choose);
                    }
                }
                    } while (choose != 1 && choose != 2);
                    //this is not a bug i will just change it 
                    String time;
                    do {
                        System.out.print("Arrival time (HH:MM): ");
                        time = input.nextLine().trim();
                    } while (!isValidTime(time));
                    

                    service.parkVehicle(new Vehicle(reg, type), toMinutes(time));
                    report.printStatus();
                    dao.saveTickets(service.getTickets());
                    break;

                case 2:
                    System.out.print("Registration number: ");
                    String exitReg = input.nextLine().trim();
                    System.out.print("Exit time (HH:MM): ");
                    String exitTime = input.nextLine().trim();
                    service.exitVehicle(exitReg, toMinutes(exitTime));
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

    private static boolean isValidTime(String time) {
        if (time == null || !time.matches("\\d{2}:\\d{2}")) {
            System.out.println("Invalid time format. Use HH:MM.");
            return false;
        }

        String[] hm = time.split(":");
        int hours = Integer.parseInt(hm[0]);
        int minutes = Integer.parseInt(hm[1]);
        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
            System.out.println("Invalid time. Use a value from 00:00 to 23:59.");
            return false;
        }
        return true;
    }

    public static int toMinutes(String time) {
        String[] hm = time.split(":");
        return Integer.parseInt(hm[0]) * 60 + Integer.parseInt(hm[1]);
    }
}
