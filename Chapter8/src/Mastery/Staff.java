package Mastery;

public class Staff extends UEmployee
{
    private String jobTitle;

    //constructor method
    public Staff(String name, double S, String title)
    {
        super(name, S);
        jobTitle = title;
    }

    
    public String getaJobTitle()
    {
        return jobTitle;
    }
}