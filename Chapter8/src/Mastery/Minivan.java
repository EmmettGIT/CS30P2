package Mastery;

public class Minivan extends Vehicle
{
    private int passengerCapacity;

    //constructor method
    public Minivan(String mk, String mdl, int yr, int passengers)
    {
        super(mk, mdl, yr);
        passengerCapacity = passengers;
    }

    public int getPassengerCapacity()
    {
        return passengerCapacity;
    }

    public String getVehicleType()
    {
        return("Minivan");
    }

    public String toString()
    {
        return(super.toString()
                + ", Passenger Capacity: " + passengerCapacity);
    }
}