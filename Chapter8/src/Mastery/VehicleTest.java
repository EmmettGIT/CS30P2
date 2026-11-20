package Mastery;

public class VehicleTest
{
    public static void main(String[] args)
    {
        //create vehicle objects
        Car myCar = new Car("Toyota", "Camry", 2024, 4);

        Truck myTruck = new Truck("Ford", "F-150", 2023, 5000);

        Minivan myMinivan = new Minivan("Honda", "Odyssey", 2025, 8);

        //car information
        System.out.println("CAR INFORMATION");
        System.out.println(myCar);

        System.out.println();

        //truck information
        System.out.println("TRUCK INFORMATION");
        System.out.println(myTruck);
        
        System.out.println();

        //minivan information
        System.out.println("MINIVAN INFORMATION");
        System.out.println(myMinivan);
    }
}