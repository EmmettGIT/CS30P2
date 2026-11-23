package Mastery;

public class BusinessAcct extends Account
{
    //constructor method
    public BusinessAcct(int num, double bal)
    {
        super(num, bal);
    }

    //check minimum balance
    public void checkBalance()
    {
        if(getBalance() < 500)
        {
            withdraw(10.00);
        }
    }
}