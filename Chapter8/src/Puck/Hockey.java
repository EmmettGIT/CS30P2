package Puck;

public class Hockey {

	public static void main(String[] args) 
	{
		//youth puck
		Puck youthPuck = new Puck(4);
		
		System.out.println(youthPuck.getDivision());
		System.out.println("The youth puck has weight "
						+ youthPuck.getWeight()
						+ " ounces.");
		System.out.println("The youth puck has radius "
						+ youthPuck.getRadius()
						+ " cm.");
		System.out.println("The youth puck has thickness "
						+ youthPuck.getThickness()
						+ " cm.");
						
		
		//standard puck
		Puck standardPuck = new Puck(5.5);
		
		System.out.println(standardPuck.getDivision());
		System.out.println("The standard puck has weight "
						+ standardPuck.getWeight()
						+ " ounces.");
		System.out.println("The standard puck has radius "
						+ standardPuck.getRadius()
						+ " cm.");
		System.out.println("The standard puck has thickness "
						+ standardPuck.getThickness()
						+ " cm.");
		
		//comparison
		if(youthPuck.compareTo(standardPuck) < 0)
		{
			System.out.println("The youth puck is smaller than the standard puck");
		}
		else if(youthPuck.compareTo(standardPuck) == 0)
		{
			System.out.println("The youth puck is the same as the standard puck");
		}
		else
		{
			System.out.println("The youth puck is larger than the standard puck");
		}
	}

}
