package Mastery;

public class University
{
    public static void main(String[] args)
    {
        //create employee objects
        Faculty myFaculty = new Faculty("Sam Palmer", 75000, "Computer Science");

        Staff myStaff = new Staff("Leif Martin", 55000, "Administrator");

        //display faculty information
        System.out.println("FACULTY INFORMATION");
        System.out.println("Name: " + myFaculty.getEmployeeName());
        System.out.println("Salary: $" + myFaculty.getSalary());
        System.out.println("Department: " + myFaculty.getDepartmentName());

        System.out.println();

        //display staff information
        System.out.println("STAFF INFORMATION");
        System.out.println("Name: " + myStaff.getEmployeeName());
        System.out.println("Salary: $" + myStaff.getSalary());
        System.out.println("Job Title: " + myStaff.getaJobTitle());
    }
}

/* SCREEN DUMP
FACULTY INFORMATION
Name: Sam Palmer
Salary: $75000.0
Department: Computer Science

STAFF INFORMATION
Name: Leif Martin
Salary: $55000.0
Job Title: Administrator
*/