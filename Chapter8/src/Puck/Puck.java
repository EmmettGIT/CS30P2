package Puck;

public class Puck extends Disk
{
	private double MIN_STD_WEIGHT = 5;
	private double MAX_STD_WEIGHT = 5.5;
	private double MIN_YTH_WEIGHT = 4;
	private double MAX_YTH_WEIGHT = 4.5;
	
	//instance variable
	private double weight;
	private boolean standard, youth;
	
	//overload constructor method
	public Puck(double w)
	{
		super(1.5, 1);
		weight = w;
		
		if(weight >= MIN_STD_WEIGHT && weight <= MAX_STD_WEIGHT )
		{
			standard = true;
			youth = false;
		}
		else 
		{
			standard = false;
			youth = true;
		}
	}
	
	//accessor method
	public double getWeight()
	{
		return weight;
	}
	
	//modifier method
	public void setWeight(double r)
	{
		weight = r;
	}
	
	public String getDivision()
	{
		String div;
		
		if(standard)
		{
			div = "Puck is standard.";
		}
		else
		{
			div = "Puck is youth.";
		}
		
		return div;
	}
	
	public boolean equals(Object p)
	{
		Puck pObj = (Puck)p;
		
		if(pObj.getDivision() == getDivision())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public int compareTo(Object p)
	{
		Puck pObj = (Puck)p;
		
		if(weight < pObj.getWeight())
		{
			return -1;
		}
		else if(weight == pObj.getWeight())
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	public String toString()
	{
		String puckString;
		
		puckString = "The puck has weight"
				+ getWeight()
				+" and division" + getDivision()
				+".";
		return puckString;
	}
	
}
