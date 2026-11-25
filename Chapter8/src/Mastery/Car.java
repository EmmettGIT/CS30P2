package Mastery;

public class Car extends Vehicle
{
    private double zeroToSixty;

    // Constructor method
    public Car(String mk, String mdl, int yr, double time)
    {
        super(mk, mdl, yr);
        zeroToSixty = time;
    }

    public double get0to60()
    {
        return zeroToSixty;
    }

    public String getVehicleType()
    {
        return "Car";
    }

    public String toString()
    {
        return super.toString()
                + ", 0 to 60 time: "
                + zeroToSixty + " seconds";
    }
}
