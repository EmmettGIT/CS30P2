package Mastery;

public abstract class Vehicle
{
    private String make;
    private String model;
    private int year;

    //constructor method
    public Vehicle(String mk, String mdl, int yr)
    {
        make = mk;
        model = mdl;
        year = yr;
    }

    
    public String getMake()
    {
        return make;
    }

    public String getModel()
    {
        return model;
    }

    public int getYear()
    {
        return year;
    }

    public abstract String getVehicleType();

    public String toString()
    {
        return("Year: " + year
                + ", Make: " + make
                + ", Model: " + model
                + ", Type: " + getVehicleType());
    }
}