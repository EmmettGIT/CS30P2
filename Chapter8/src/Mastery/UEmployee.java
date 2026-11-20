package Mastery;

public class UEmployee
{
    private String employeeName;
    private double salary;

    //constructor method
    public UEmployee(String name, double S)
    {
        employeeName = name;
        salary = S;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public double getSalary()
    {
        return salary;
    }
}