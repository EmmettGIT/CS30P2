package CrestMart;

public class Associate extends Employee
{
	private double hourlyPayRate;
	
	//constructor method
	public Associate(String fn, String ln, double rate)
	{
		super(fn, ln);
		hourlyPayRate = rate;
	}
	
	public double pay(double hours)
	{
		double payEarned;
		
		if(hours > 40)
		{
			payEarned = ((hourlyPayRate * 40)
					+ (hourlyPayRate * 1.5)
					* (hours - 40));
		}
		else
		{
			payEarned = hours * hourlyPayRate;
		}
		
		return payEarned;
	}
	
	public String toString()
	{
		return(super.toString()
				+ ", Associate"
				+ "Pay Rate: " + hourlyPayRate);
	}
}
