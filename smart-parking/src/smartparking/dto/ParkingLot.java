package smartparking.dto;

import java.util.ArrayList;

public class ParkingLot {

  private String name;
  private ArrayList<ParkingSlot> slots = new ArrayList<>();

  public ParkingLot(String name, int numberOfSlots) {
    this.name = name;
    for (int number = 1; number <= numberOfSlots; number++) {
     
      slots.add(new ParkingSlot(number));
    
    }
  }

  public String getName() {
    return name;
  }

  public ArrayList<ParkingSlot> getSlots() {
    return slots;
  }

  public int getTotalSlots() {
    return slots.size();
  }

  public ParkingSlot getSlot(int slotNumber) {
    if (slotNumber < 0 || slotNumber >= slots.size()) {
      return null;
    }
    // 9 - fixed the index
    return slots.get(slotNumber-1);
  }
}
