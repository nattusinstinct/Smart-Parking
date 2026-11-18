package smartparking.controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
  Scanner input = new Scanner(System.in);

  public ParkingController(
      ParkingService service, ParkingReport report, ParkingLot parkingLot, ParkingDAO dao) {

    this.service = service;
    this.report = report;
    this.parkingLot = parkingLot;
    this.dao = dao;
  }

  public void runMenu() {

    boolean running = true;

    while (running) {
      System.out.println(
          "\n1) Park a vehicle   2) Exit a vehicle   3) Show status   4) Daily summary   0) Quit");
      System.out.print("Choose: ");
     
      int choice;
      try {
        choice = input.nextInt();
      } catch (Exception e) {
        System.out.println("Invalid input,enter integer..");
        input.nextLine();
        continue;
      }

      // 1 . scanner next-line error

      input.nextLine();
      switch (choice) {
        case 1:
          System.out.print("Registration number: ");
          String reg = input.nextLine().trim();
          String type;

          vehicleloop: 
        while(true){
           System.out.print("Vehicle Type\n 1 - CAR\n 2 - BIKE  \n");
           System.out.print("Enter the vehicle type : ");
           String vehicle = input.nextLine();
   
     
          switch(vehicle){
            case "1"->{
              type = "car";
              break vehicleloop;
            }
            case "2"->{
              type = "bike";
                break vehicleloop;
            }
            default->{
              System.out.println("invalid vehicle type");
            }
          }
        }
         

          String time;
          while (true) {
            System.out.print("Arrival time (HH:MM): ");
            time = input.nextLine().trim();

            if (isFormatValid(time)) {
              break;
            }

            
          }

          service.parkVehicle(new Vehicle(reg, type), toMinutes(time));
          report.printStatus();
          dao.saveTickets(service.getTickets());
          System.out.println(reg + " " + type + " " + time);
          break;

        case 2:
          System.out.print("Registration number: ");
          String exitReg = input.nextLine().trim();

            String exitTime;
          while (true) {
            System.out.print("Arrival time (HH:MM): ");
            exitTime = input.nextLine().trim();

            if (isFormatValid(exitTime)) {
              break;
            }

          }

          service.exitVehicle(exitReg, toMinutes(exitTime));
          System.out.println("test : " + exitReg + " " + exitTime);
          report.printStatus();
          dao.saveTickets(service.getTickets());

          break;

        case 3:
          report.printStatus();
          // 6 . fixed the status view
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
    return Integer.parseInt(hm[0]) * 60 + Integer.parseInt(hm[1]);
  }

  // added time format validator

  public boolean isFormatValid(String time) {

    try {

      LocalTime.parse(time, formatter);

      return true;

    } catch (Exception e) {
      System.out.println("Invalid format..please enter the time in format(HH:MM)");
      return false;
    }
  }
}
