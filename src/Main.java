import java.util.Scanner;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    static String AccountNumber;
    static String pin;
    static String name;
    static int balance;

    public static void Creation(Scanner scann) {

        scann.nextLine();

        System.out.print("Enter Your Name: ");
        name = scann.nextLine();

        System.out.print("Enter Your Age: ");
        int age = scann.nextInt();
        scann.nextLine();

        if (age < 18) {
            System.out.println("You are minor");
            return;
        }

        System.out.print("Enter Mobile Number: ");
        String mobile;
        while (true) {
            mobile = scann.nextLine();
            if (mobile.matches("\\d{10}")) break;
            else System.out.print("Invalid! Enter 10 digit mobile: ");
        }

        System.out.print("Enter Email: ");
        String email = scann.nextLine();

        System.out.print("Enter Initial Deposit: ");
        balance = scann.nextInt();
        scann.nextLine();

        // ACCOUNT NUMBER GENERATION
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            builder.append(chars.charAt(random.nextInt(chars.length())));
        }

        AccountNumber = builder.toString();

        System.out.println("Generated Account Number: " + AccountNumber);

        // PIN creation
        while (true) {

            System.out.print("Enter 4-digit PIN: ");
            pin = scann.nextLine();

            if (!pin.matches("\\d{4}")) {
                System.out.println("PIN must be 4 digits!");
                continue;
            }

            System.out.print("Confirm PIN: ");
            String cpin = scann.nextLine();

            if (!pin.equals(cpin)) {
                System.out.println("PINs do not match! Try again.");
                continue;
            }

            break;
        }

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO accounts (acc_no,name,age,mobile,email,pin,balance) VALUES (?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, AccountNumber);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, mobile);
            ps.setString(5, email);
            ps.setString(6, pin);
            ps.setInt(7, balance);

            ps.executeUpdate();

            System.out.println("Account Created Successfully!");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void Login(Scanner scann) {

        System.out.print("Enter Account Number: ");
        String accNo = scann.next();

        System.out.print("Enter PIN: ");
        String password = scann.next();

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM accounts WHERE acc_no=? AND pin=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, accNo);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                AccountNumber = accNo;
                name = rs.getString("name");
                balance = rs.getInt("balance");
                pin = password;

                System.out.println("Login Successful!");
                UserMenu(scann);
            } else {
                System.out.println("Invalid Account Number or PIN");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UserMenu(Scanner scann) {

        while (true) {

            System.out.println("\n1.Account Details");
            System.out.println("2.Check Balance");
            System.out.println("3.Deposit");
            System.out.println("4.Withdraw");
            System.out.println("5.Change PIN");
            System.out.println("6.Logout");

            int choice = scann.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("Name: " + name);
                    System.out.println("Account Number: " + AccountNumber);
                    System.out.println("Account Type: Savings");
                    break;

                case 2:
                    System.out.println("Available Balance: " + balance);
                    break;

                case 3:
                    System.out.print("Enter Deposit Amount: ");
                    int deposit = scann.nextInt();

                    if (deposit <= 0) {
                        System.out.println("Invalid Amount");
                        break;
                    }

                    try {
                        Connection con = DBConnection.getConnection();
                        String q = "UPDATE accounts SET balance = balance + ? WHERE acc_no=?";
                        PreparedStatement ps = con.prepareStatement(q);

                        ps.setInt(1, deposit);
                        ps.setString(2, AccountNumber);
                        ps.executeUpdate();

                        balance += deposit;
                        con.close();

                        System.out.println("Amount Deposited Successfully!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    System.out.print("Enter Withdraw Amount: ");
                    int withdraw = scann.nextInt();

                    if (withdraw > balance) {
                        System.out.println("Insufficient Balance!");
                        break;
                    }

                    try {
                        Connection con = DBConnection.getConnection();
                        String q = "UPDATE accounts SET balance = balance - ? WHERE acc_no=?";
                        PreparedStatement ps = con.prepareStatement(q);

                        ps.setInt(1, withdraw);
                        ps.setString(2, AccountNumber);
                        ps.executeUpdate();

                        balance -= withdraw;
                        con.close();

                        System.out.println("Amount Withdrawn Successfully!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    System.out.print("Enter Old PIN: ");
                    String oldpin = scann.next();

                    if (!oldpin.equals(pin)) {
                        System.out.println("Incorrect Old PIN");
                        break;
                    }

                    System.out.print("Enter New 4-digit PIN: ");
                    String newpin = scann.next();

                    if (!newpin.matches("\\d{4}")) {
                        System.out.println("Invalid PIN Format");
                        break;
                    }

                    try {
                        Connection con = DBConnection.getConnection();
                        String q = "UPDATE accounts SET pin=? WHERE acc_no=?";
                        PreparedStatement ps = con.prepareStatement(q);

                        ps.setString(1, newpin);
                        ps.setString(2, AccountNumber);
                        ps.executeUpdate();

                        pin = newpin;
                        con.close();

                        System.out.println("PIN Changed Successfully!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 6:
                    System.out.println("Logged Out Successfully!");
                    return;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }

    public static void main(String[] args) {

        Scanner scann = new Scanner(System.in);

        while (true) {

            System.out.println("\n1.Account Creation");
            System.out.println("2.Login");
            System.out.println("3.Exit");

            int n = scann.nextInt();

            switch (n) {
                case 1:
                    Creation(scann);
                    break;
                case 2:
                    Login(scann);
                    break;
                case 3:
                    System.out.println("Thank You!");
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}