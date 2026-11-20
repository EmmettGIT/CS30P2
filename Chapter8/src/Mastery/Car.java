package Mastery;

public class Car extends Vehicle
{
    private int numberOfDoors;

    //constructor method
    public Car(String mk, String mdl, int yr, int doors)
    {
        super(mk, mdl, yr);
        numberOfDoors = doors;
    }

    public int getNumberOfDoors()
    {
        return numberOfDoors;
    }

    public String getVehicleType()
    {
        return("Car");
    }

    public String toString()
    {
        return(super.toString()
                + ", Number of Doors: " 
        		+ numberOfDoors);
    }
}