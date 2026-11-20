package CrestMart;

public class Manager extends Employee
{
	private double yearlySalary;
	
	//contructor method
	public Manager(String fn, String ln, double salary)
	{
		super(fn, ln);
		yearlySalary = salary;
	}
	
	public double pay(double weeks)
	{
		double payEarned;
		payEarned = yearlySalary/52 * weeks;
		return payEarned;
	}
	
	public String toString()
	{
		return(super.toString()
				+ ", Manager"
				+ "Salary: "
				+ yearlySalary);
				
	}
}
