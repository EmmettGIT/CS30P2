package Mastery;

public class VehicleTest
{
    public static void main(String[] args)
    {
        // Create vehicle objects
        Car myCar = new Car("Toyota", "GR Supra", 2022, 3.9);
        Truck myTruck = new Truck("Mazda", "B2200", 1990, 900);
        Minivan myMinivan = new Minivan("Honda", "Odyssey Absolute", 2006, 8);

        System.out.println("CAR INFORMATION");
        System.out.println(myCar);
        System.out.println();

        System.out.println("TRUCK INFORMATION");
        System.out.println(myTruck);
        System.out.println();

        System.out.println("MINIVAN INFORMATION");
        System.out.println(myMinivan);
    }
}

/* SCREEN DUMP
CAR INFORMATION
Year: 2022, Make: Toyota, Model: GR Supra, Type: Car, 0 to 60 time: 3.9 seconds

TRUCK INFORMATION
Year: 1990, Make: Mazda, Model: B2200, Type: Truck, Towing Capacity: 900.0 kg

MINIVAN INFORMATION
Year: 2006, Make: Honda, Model: Odyssey Absolute, Type: Minivan, Passenger Capacity: 8
*/