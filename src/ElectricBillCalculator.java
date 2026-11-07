/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;


/*
@Jaylen Barnes
*/

public class ElectricBillCalculator {

    public static void main(String[] args) {

        DecimalFormat money = new DecimalFormat("0.00");

        try {

            File inputFile = new File("Customer_Data.txt");

            if (!inputFile.exists()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: Customer_Data.txt is missing."
                );
                return;
            }

            Scanner fileReader = new Scanner(inputFile);

            String customerID = fileReader.next();
            String firstName = fileReader.next();
            String lastName = fileReader.next();
            double usage = fileReader.nextDouble();
            String paymentStatus = fileReader.next().toUpperCase();

            fileReader.close();

            // Validation

            if (customerID.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: Customer ID is missing."
                );
                return;
            }

            if (firstName.trim().isEmpty() ||
                    lastName.trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        null,
                        "Error: Customer name is missing."
                );
                return;
            }

            if (usage < 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: Electricity usage cannot be negative."
                );
                return;
            }

            if (!paymentStatus.equals("Y") &&
                    !paymentStatus.equals("N")) {

                JOptionPane.showMessageDialog(
                        null,
                        "Error: Payment status must be Y or N."
                );
                return;
            }

            double electricCharge;

            // Progressive Pricing

            if (usage <= 100) {

                electricCharge = usage * 0.10;

            } else if (usage <= 300) {

                electricCharge =
                        (100 * 0.10)
                                + ((usage - 100) * 0.15);

            } else {

                electricCharge =
                        (100 * 0.10)
                                + (200 * 0.15)
                                + ((usage - 300) * 0.20);
            }

            double lateFee = 0.00;

            if (paymentStatus.equals("Y")) {
                lateFee = 15.00;
            }

            double totalDue = electricCharge + lateFee;

            String paymentMessage;

            if (paymentStatus.equals("Y")) {
                paymentMessage = "Previous bill overdue";
            } else {
                paymentMessage = "No overdue balance";
            }

            String report =
                    "MONTHLY UTILITY BILL\n" +
                            "================================\n" +
                            "Customer ID: " + customerID + "\n" +
                            "Customer Name: " + firstName + " " + lastName + "\n" +
                            "Electricity Usage: " + usage + " kWh\n" +
                            "Electricity Charge: $" +
                            money.format(electricCharge) + "\n" +
                            "Late-Payment Fee: $" +
                            money.format(lateFee) + "\n" +
                            "TOTAL AMOUNT DUE: $" +
                            money.format(totalDue) + "\n" +
                            "Payment Status: " +
                            paymentMessage;

            JOptionPane.showMessageDialog(
                    null,
                    report
            );

            FileWriter writer =
                    new FileWriter("UtilityBillReport.txt");

            writer.write(report);
            writer.close();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error reading file: " +
                            e.getMessage()
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Invalid data format in file."
            );
        }
    }
}