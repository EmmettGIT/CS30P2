package Mastery;

public class VehicleTest
{
    public static void main(String[] args)
    {
        //create vehicle objects
        Car myCar = new Car("Toyota", "GR Supra", 2022, 2);

        Truck myTruck = new Truck("Mazda", "B2200", 1990, 900);

        Minivan myMinivan = new Minivan("Honda", "Odyssey Absolute", 2006, 8);

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

/* SCREEN DUMP
CAR INFORMATION
Year: 2022, Make: Toyota, Model: GR Supra, Type: Car, Number of Doors: 2

TRUCK INFORMATION
Year: 1990, Make: Mazda, Model: B2200, Type: Truck, Towing Capacity: 900.0 kg

MINIVAN INFORMATION
Year: 2006, Make: Honda, Model: Odyssey Absolute, Type: Minivan, Passenger Capacity: 8
*/