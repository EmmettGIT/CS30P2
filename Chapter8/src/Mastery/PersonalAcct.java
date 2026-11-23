package Mastery;

public class PersonalAcct extends Account
{
    //constructor method
    public PersonalAcct(int num, double bal)
    {
        super(num, bal);
    }

    //check minimum balance
    public void checkBalance()
    {
        if(getBalance() < 100)
        {
            withdraw(2.00);
        }
    }
}