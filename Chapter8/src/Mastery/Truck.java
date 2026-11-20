package Mastery;

public class Truck extends Vehicle
{
    private double towingCapacity;

    //constructor method
    public Truck(String mk, String mdl, int yr, double capacity)
    {
        super(mk, mdl, yr);
        towingCapacity = capacity;
    }

    public double getTowingCapacity()
    {
        return towingCapacity;
    }

    public String getVehicleType()
    {
        return("Truck");
    }

    public String toString()
    {
        return(super.toString()
                + ", Towing Capacity: " + towingCapacity + " kg");
    }
}