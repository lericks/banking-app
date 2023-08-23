/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author G50
 */
public class BankingApp {

    /**
     * @param args the command line arguments
     */
    //create a global array to act as the database, (rows=2000 columns = 3)
    static String accounts[][] = new String[2000][3];
    static String[][] logs = new String[5000][4];
    static String[] transName = {"Deposited", "Withdrew", "Created Account", "Changed name", "Changed password", "Changed name and password", "Deleted Account"};
    static String intel;
    static int transID, logCount = 0;

    //declare and initialize global variables
    static int accCount = 0, theAccount = -20;

    //accCount keeps track of number of accounts present
    //theAccount stores the row index of the currently logged in account
    public static void main(String[] args) {
        //Call a method to fetch accounts data from a text file
        readFromText();
      readLog();
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to leRICKS Bank");
        //label to be used when an invalid input is given
        lover:
        //start of endless loop, ensures your program runs continuous till exit
        while (true) {
            System.out.println("Enter\n1. To create Account\n2. To log in\n3. To exit program");
            String testIn = in.nextLine();
            int choice = 0;
            try {
                //check users selection if it is an integer
                choice = Integer.parseInt(testIn);
            } catch (Exception e) {
                //alert user of wrong input and call label
                System.out.println("Ooups ! Invalid selection. Try Again");
                continue lover;
            }
            if (choice == 1) {
                createAcc();
            } else if (choice == 2) {
                login();
            } else if (choice == 3) {
                //copy data from array to text file
                updateText();
                System.exit(0);
            } else if (choice == 555) {
                //log in as admin
                admin();
            } else {
                System.out.println("Ooups ! Invalid selection. Try Again");
            }

        }
    }

    static void createAcc() {
        Scanner in2 = new Scanner(System.in);
        //  check if number of accounts exceed number of rows in array
        if (accCount < accounts.length) {
            accounts[accCount][2] = "0";
            System.out.println("Enter the account name");
            //check if account name exists and continuously ask for the name 
            lericks:
            while (true) {
                String theName;
                theName = in2.nextLine();
                int xvc = 0;

                if (accCount > 0) {
                    while (xvc < accCount) {
                        if (accounts[xvc][0].equalsIgnoreCase(theName)) {
                            System.out.println("Sorry ! Account name already in use, Try another name");
                            continue lericks;
                        }
                        xvc += 1;
                    }
                }
                if (theName.equalsIgnoreCase("")) {
                    System.out.println("Sorry ! Invalid account name. Try again");
                    continue lericks;
                }
                accounts[accCount][0] = theName;
                System.out.println("Enter password");
                String thePass;
                thePass = in2.nextLine();
                if (theName.equalsIgnoreCase("")) {
                    System.out.println("Sorry ! Invalid passWord. Try again");
                    continue lericks;
                }
                accounts[accCount][1] = thePass;
                System.out.println("Account created sucessfully,\nAccount Name :\t" + accounts[accCount][0] + "\nPassword :\t\t" + accounts[accCount][1]);
                accCount += 1;
                updateText();
                intel=theName;
                transID=2;
                createLog();
                
                break;
            }
        } else {
            System.out.println("Accounts Database is full, Consult the Admin ");
        }
    }

    static void login() {
        Scanner in3 = new Scanner(System.in);
        int kaka = 2, x = 0;;
        //if there is no account present in array cancel log in
        if (!(accounts[0][0] == null)) {

            lericks:
            while (true) {
                if (kaka == 2) {
                    System.out.println("Enter Account Name");
                    String theName = in3.nextLine();
                    //check if account name exists
                    for (int v = 0; v < accounts.length; v++) {
                        if (theName.equalsIgnoreCase(accounts[v][0])) {
                            //login the user with the row index of the account
                            theAccount = v;
                            x = 8;
                            kaka = 4;
                            break;
                        }
                    }
                }
                if (x == 8) {
                    ///ask for password
                    System.out.println("Enter password");
                    String thePass = in3.nextLine();
                    //check if password equals password in the array
                    if (thePass.equalsIgnoreCase(accounts[theAccount][1])) {
                        break;
                    } else {
                        System.out.println("Oups ! Wrong Password, Please Try Again");
                        continue lericks;
                    }
                } else {
                    System.out.println("Ooups ! Account Name does not exist");
                    continue lericks;
                }
            }
            System.out.println("Login succesful");
            //log the user in and print menu
            menu();
        } else {
            System.out.println("Accounts database empty, create account first");
        }
    }

    static void deposit() {
        Scanner in4 = new Scanner(System.in);
        int depAmount = 0;
        //get current balance 
        String xn = accounts[theAccount][2];
        System.out.println("Enter Amount");
        //take input and validate it
        hey:
        while (true) {
            String vbnTest = in4.nextLine();
            try {
                depAmount = Integer.parseInt(vbnTest);
                if (depAmount > 0) {
                    break;
                } else {
                    System.out.println("Ooups ! Invalid input. Try Again");
                    continue hey;
                }
            } catch (Exception e) {
                System.out.println("Ooups ! Invalid input. Try Again");
                continue hey;
            }
        }
        int balance = Integer.parseInt(xn);
        int v = depAmount + balance;
        String b = "" + v;
        accounts[theAccount][2] = b;
        System.out.println("Transaction completed succesfully, Your new balance is : " + accounts[theAccount][2] + " /=");
        updateText();
         intel=""+depAmount;
                transID=0;
                createLog();
    }

    static void withdraw() {
        Scanner in5 = new Scanner(System.in);
        System.out.println("Enter Amount");
        int theAmount = 0;
        heyP:
        while (true) {
            String vMnTest = in5.nextLine();
            try {
                theAmount = Integer.parseInt(vMnTest);
                break;
            } catch (Exception e) {
                System.out.println("Ooups ! Invalid input. Try Again");
                continue heyP;
            }
        }
        int bal = Integer.parseInt(accounts[theAccount][2]);
        if (bal > theAmount && theAmount > 0) {
            int v = bal - theAmount;
            String b = "" + v;
            accounts[theAccount][2] = b;
            System.out.println("Transaction sucessful. Balance is : " + accounts[theAccount][2] + " /=");
            updateText();
             intel=""+theAmount;
                transID=1;
                createLog();
        } else {
            System.out.println("oups ! Withdrawal failed. Your current balance is : " + accounts[theAccount][2] + " /=");
        }
    }

    static void showBalance() {
        //get current time and display while showing balance
        Date tod = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy hh:mm a");
        System.out.println("Your balance as at " + sdf.format(tod) + " is : " + accounts[theAccount][2] + " /=");
    }

    static void menu() {
        Scanner in6 = new Scanner(System.in);
        int vbs = 10;
        girl:
        while (true) {
            System.out.println("Hello " + accounts[theAccount][0] + "\nWhat would you like to do ? \n1. Deposit Cash\n2. Withdraw cash\n3. Check balance\n4. Change details\n5. Log Out");
            int sel = 0;
            String xbn = in6.nextLine();
            try {
                sel = Integer.parseInt(xbn);
            } catch (Exception e) {
                System.out.println("Ooups ! Invalid selection. Try Again");
                continue girl;
            }
            switch (sel) {
                case 1:
                    deposit();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    showBalance();
                    break;
                case 4:
                    changeDetails();
                    break;
                case 5:
                    theAccount = -20;
                    vbs = 20;
                    break;
                default:
                     System.out.println("Ooups ! Invalid selection. Try Again");
                    break;
            }
            if (vbs == 20) {
                break;
            } else {
            }
        }
    }

    static void changeDetails() {
        Scanner c74 = new Scanner(System.in);
        String accN = accounts[theAccount][0], passW = accounts[theAccount][1];
        int bcv = 2;
        System.out.println("Your current details are:\nAccount Name :\t" + accounts[theAccount][0] + "\nPassword :\t\t" + accounts[theAccount][1]);
        System.out.println("Enter\n1. To change Account Name\n2. To change password\n3. To change Account Name and Password\n0. To go back");
        int theC = 33;
        djRock:
        while (true) {
            String hyg = c74.nextLine();
            try {
                theC = Integer.parseInt(hyg);
                break;
            } catch (Exception e) {
                System.out.println("Ooups ! Invalid selection. Try Again");
                System.out.println("Enter\n1. To change Account Name\n2. To change password\n3. To change Account Name and Password\n0. To go back");
                continue djRock;
            }
        }
        switch (theC) {
            case 1:
                System.out.println("Enter Your new Account Name");
                accN = c74.nextLine();
                bcv = 4;
                 intel=accN;
                transID=3;
                break;
            case 2:
                System.out.println("Enter Your new Password");
                passW = c74.nextLine();
                bcv = 4;
                 intel=passW;
                transID=4;
                break;
            case 3:
                System.out.println("Enter Your new Account Name");
                accN = c74.nextLine();
                System.out.println("Enter Your new Password");
                passW = c74.nextLine();
                bcv = 4;
                 intel=""+accN+" "+passW;
                transID=5;
                break;
            default:
                break;
        }
        if (bcv != 2) {
            System.out.println("Are you sure you want to change details. Yes (y) or No(n)");
            String dfg = c74.next();
            if (dfg.equalsIgnoreCase("yes") || dfg.equalsIgnoreCase("y")) {
                accounts[theAccount][0] = accN;
                accounts[theAccount][1] = passW;
                System.out.println("Operation completed successfully");
                updateText();
                createLog();
            } else {
            }
        }
    }

    static void readFromText() {
        java.io.File file = new java.io.File("database.txt");
        //read data from text file and store in array
        try {
            Scanner input = new Scanner(file);
            int x = 0;
            //loop till text file out of content
            while (input.hasNextLine()) {
                accounts[x] = input.nextLine().split("\t");
                accCount += 1;
                x += 1;
            }
            input.close();
        } catch (Exception e) {
        }
    }

    static void updateText() {
        //take data from array and store in text file
        java.io.File file = new java.io.File("database.txt");
        try {
            java.io.PrintWriter output = new java.io.PrintWriter(file);
            int bbp = 0;
            while (bbp < accCount) {
                output.println(accounts[bbp][0] + "\t" + accounts[bbp][1] + "\t" + accounts[bbp][2]);
                bbp += 1;
            }
            output.close();
        } catch (Exception e) {
        }
    }

    static void admin() {
        Scanner crazy = new Scanner(System.in);
        System.out.println("You are about to logIn as administrator");
        System.out.println("Enter username and password, press enter after each input");
        String hisPut = crazy.nextLine();
        String his = crazy.nextLine();
        if (hisPut.equalsIgnoreCase("admin") && his.equalsIgnoreCase("root")) {
            hungry:
            while (true) {
                int bbg = 0;
                System.out.println("System Acounts\nAcc No.\tAcc Name\t\tPassword\t\tBalance");

                while (bbg < accCount) {
                    System.out.println(bbg + "\t" + accounts[bbg][0] + "\t\t" + accounts[bbg][1] + "\t\t" + accounts[bbg][2]);
                    bbg += 1;
                }
                System.out.println("Enter \n1: To delete accounts\n2: To view logs \n0: To log out");
                int x = crazy.nextInt();
                if (x == 1) {
                    System.out.println("Enter the Acc No. of the account to delete");
                    int del = crazy.nextInt();
                    if ((del < accCount) && (del >= 0)) {
                        System.out.println("Are you sure you want to delete\n" + accounts[del][0] + "\nReply with yes (y) or no (n)");
                        String che = crazy.next();
                        if (che.equalsIgnoreCase("yes") || che.equalsIgnoreCase("y")) {
                                intel=accounts[del][0];
                            for (int bb = del; del < accCount; del++) {
                                accounts[del][0] = accounts[del + 1][0];
                                accounts[del][1] = accounts[del + 1][1];
                                accounts[del][2] = accounts[del + 1][2];
                            }
                            accounts[accCount - 1][0] = null;
                            accounts[accCount - 1][1] = null;
                            accounts[accCount - 1][2] = null;
                            accCount -= 1;
                            updateText();
                            System.out.println("Account deleted succesfully");
                            
                transID=6;
                createLog();
                        } else {
                            System.out.println("Account deletion cancelled");
                        }
                    } else {
                        System.out.println("Invalid input, Try again");
                        continue hungry;
                    }

                } else if (x == 0) {
                    break;
                }else if(x==2){
                     int bbx = 0;
                System.out.println("System Logs\nLog No.\tTransaction Name\tDetails\tTime and Date\tPerfomed by");
                while (bbx < logCount) {
                    
                    System.out.println(bbx + "\t" + logs[bbx][0] + "\t" + logs[bbx][1] + "\t" + logs[bbx][2]+ "\t" + logs[bbx][3]);
                    bbx += 1;
                }
                    
                    
                    
                    
                } else {
                    System.out.println("Invalid input, Try again");
                }

            }
        } else {

        }

    }

    static void createLog() {
        Date xy = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a dd/MMM/yyyy");
        String time = sdf.format(xy);
        if(transID==6){
           logs[logCount][3] = "admin";
        }else if(transID==2){
           logs[logCount][3] =intel;
        }else{
        logs[logCount][3] = accounts[theAccount][0];
        }
        
        logs[logCount][0] = transName[transID];
        logs[logCount][1] = intel;
        logs[logCount][2] = time;
     
        logCount+=1;
        java.io.File file = new java.io.File("logs.txt");
        try {
            java.io.PrintWriter outputx = new java.io.PrintWriter(file);
            int bbp = 0;
            while (bbp < logCount) {
                outputx.println(logs[bbp][0] + "\t" + logs[bbp][1] + "\t" + logs[bbp][2] + "\t" + logs[bbp][3]);
                bbp += 1;
            }
            outputx.close();
        } catch (Exception e) {
        }

    }

    static void readLog() {

java.io.File theFile = new java.io.File("logs.txt");
        //read data from text file and store in array
        try {
            Scanner theInput = new Scanner(theFile);
            int mk = 0;
            //loop till text file out of content
            while (theInput.hasNextLine()) {
                //read text file and store in array row splitting where you meet a tab key
                logs[mk] = theInput.nextLine().split("\t");
                logCount += 1;
                mk += 1;
            }
            theInput.close();
        } catch (Exception e) {
        }

    }
}
