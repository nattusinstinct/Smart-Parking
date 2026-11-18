package smartparking.dto;

public class Vehicle {

    private String registrationNumber;
    private String type;

    public Vehicle(String registrationNumber, String type) {
        this.registrationNumber = registrationNumber.toUpperCase();
        this.type = type.toUpperCase();
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getType() {
        return type;
    }

    public int getHourlyRate() {
        if (type.equals("BIKE")) {
            return 20;
        }
        else if (type.equals("CAR")){
        return 40;
        }
        else{
            return 0;
        }
    }

    public String toString() {
        return registrationNumber + " (" + type + ")";
    }
}
