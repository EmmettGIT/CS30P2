package Mastery;

public class Account
{
    private int accountNumber;
    private double balance;

    //constructor method
    public Account(int num, double bal)
    {
        accountNumber = num;
        balance = bal;
    }

    public int getAccountNumber()
    {
        return accountNumber;
    }

    public double getBalance()
    {
        return balance;
    }

    //deposit method
    public void deposit(double amount)
    {
        balance = balance + amount;
    }

    //withdraw method
    public void withdraw(double amount)
    {
        balance = balance - amount;
    }
}