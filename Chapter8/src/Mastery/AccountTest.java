package Mastery;

public class AccountTest
{
    public static void main(String[] args)
    {
        //create account objects
        PersonalAcct myPersonal = new PersonalAcct(1001, 90);

        BusinessAcct myBusiness = new BusinessAcct(2001, 450);

        //display personal account information
        System.out.println("PERSONAL ACCOUNT");
        System.out.println("Account Number: " + myPersonal.getAccountNumber());
        System.out.println("Starting Balance: $" + myPersonal.getBalance());

        myPersonal.checkBalance();

        System.out.println("Final Balance: $" + myPersonal.getBalance());

        System.out.println();

        //display business account information
        System.out.println("BUSINESS ACCOUNT");
        System.out.println("Account Number: " + myBusiness.getAccountNumber());
        System.out.println("Starting Balance: $" + myBusiness.getBalance());

        myBusiness.checkBalance();

        System.out.println("Final Balance: $" + myBusiness.getBalance());
    }
}

/*
PERSONAL ACCOUNT
Account Number: 1001
Starting Balance: $90.0
Final Balance: $88.0

BUSINESS ACCOUNT
Account Number: 2001
Starting Balance: $450.0
Final Balance: $440.0
*/