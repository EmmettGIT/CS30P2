package Mastery;

public class Faculty extends UEmployee
{
	private String departmentName;

    //constructor method
    public Faculty(String name, double S, String dept)
    {
    	super(name, S);
    	departmentName = dept;
	}
    public String getDepartmentName()
    {
    	return departmentName;
	}
}