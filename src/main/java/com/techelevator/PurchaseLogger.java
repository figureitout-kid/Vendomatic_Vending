package com.techelevator;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseLogger {

    private static final String LOG_FILE_PATH = "Log.txt";

    public static void logDeposit(double amount, double newBalance) {
        String logMessage;
    }

    public static void logPurchase(Items item, double newBalance) {
        String logMessage;
    }

    public static void logGiveChange(double changeAmount, double newBalance) {
        String logMessage;
    }

    private static String getFormattedLogMessage(String action, double amount, double newBalance) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        String timestamp = dateFormat.format(new Date());
        return String.format("%s %s: $%.2f $%.2f", timestamp, action, amount, newBalance);
    }

    private static String getFormattedLogMessage(Items item, double newBalance) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        String timestamp = dateFormat.format(new Date());
        return String.format("%s %s: $%.2f $%.2f", timestamp, item.getproductName(), item.getPrice(), newBalance);
    }

    private static void logToFile(String logMessage) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.println(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
