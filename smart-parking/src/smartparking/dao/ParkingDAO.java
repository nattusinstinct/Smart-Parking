package smartparking.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import smartparking.dto.ParkingTicket;
import smartparking.dto.Vehicle;

public class ParkingDAO {

    private static final String FILE_PATH = "Github/Smart-Parking/smart-parking/src/smartparking/data/tickets.json";

    public ArrayList<ParkingTicket> loadTickets() {
        ArrayList<ParkingTicket> tickets = new ArrayList<>();
        try {
            Scanner file = new Scanner(new File(FILE_PATH));
            while (file.hasNextLine()) {
                String line = file.nextLine().trim();
                if (!line.isEmpty()) {
                    tickets.add(parseTicket(line));
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
        }
        return tickets;
    }

    public void saveTickets(ArrayList<ParkingTicket> tickets) {
        try {
            //the logic for getting the bracket
            FileWriter writer = new FileWriter(FILE_PATH);
             writer.write("[");
            int count = 0 ;
            for (ParkingTicket ticket : tickets) {
                writer.write(toJson(ticket));
                if(tickets.size()-1!=count){
                    writer.write("\n,");
                }
                count++;
            }
            writer.write("]");
            writer.close();
        } catch (IOException e) {
            System.out.println("Could not save tickets: " + e.getMessage());
        }
    }

    private String toJson(ParkingTicket ticket) {
        return "{\"id\":\"" + ticket.getTicketId()
                + "\",\"reg\":\"" + ticket.getVehicle().getRegistrationNumber()
                + "\",\"type\":\"" + ticket.getVehicle().getType()
                + "\",\"slot\":" + ticket.getSlotNumber()
                + ",\"entry\":" + ticket.getEntryTime()
                + ",\"exit\":" + ticket.getExitTime()
                + ",\"fee\":" + ticket.getFee()
                + ",\"closed\":" + ticket.isClosed() + "}";
    }

    private ParkingTicket parseTicket(String line) {
        String id = jsonText(line, "id");
        String reg = jsonText(line, "reg");
        String type = jsonText(line, "type");
        int slot = jsonNumber(line, "slot");
        int entry = jsonNumber(line, "entry");
        int exit = jsonNumber(line, "exit");
        int fee = jsonNumber(line, "fee");
        boolean closed = jsonText(line, "closed").equals("true");
        return new ParkingTicket(id, new Vehicle(reg, type), slot, entry, exit, fee, closed);
    }

    private String jsonText(String line, String key) {
        String marker = "\"" + key + "\":";
        int start = line.indexOf(marker) + marker.length();
        if (line.charAt(start) == '"') {
            int end = line.indexOf('"', start + 1);
            return line.substring(start + 1, end);
        }
        // System.out.println("start : "+ start);
          
        int end = start;
        // System.out.println("end : "+ end);
        while (end < line.length() && line.charAt(end) != ',' && line.charAt(end) != '}') {
            end++;
        }
        return line.substring(start, end);
    }

    private int jsonNumber(String line, String key) {
        return Integer.parseInt(jsonText(line, key));
    }
}
