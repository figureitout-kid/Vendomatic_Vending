package com.techelevator;

import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


import static com.techelevator.Items.ItemType.CHIP;
import static java.nio.file.Files.readAllBytes;
import static org.junit.Assert.assertTrue;
public class TransactionLoggerTest {
    private static final String TEST_LOG_PATH = "Testlog.txt";
    private Items testItem;
    private String testProductName;
    private String testSlotLocation;
    private double testPrice;
    int testQuantity;


//everything is on pause in this class until we get logchange working properly in transactionLogger and CLI

    @Before
    public void setupClass(){
        TransactionLogger.setLogFilePath(TEST_LOG_PATH);
        // make sure file is clean before testing
        File testFile = new File(TEST_LOG_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    //initialize a test item with parameters from items class
    @Before
    public void setTestItem() {
        testItem = new Items("A1", "Chips", 1.50, CHIP, 5);
    }


    @Test
    public void testLogDepositToLogFile() throws IOException {
        double amount = 5.00;
        double newBalance = 10.00;
        TransactionLogger.logDeposit(amount, newBalance);

        //test if file exists
        assertTrue(Files.exists(Paths.get(TEST_LOG_PATH)));

        //read the file to make sure deposit is logged
        String logElements = new String(readAllBytes(Paths.get(TEST_LOG_PATH)));
        Assert.assertTrue(logElements.contains("FEED MONEY: "));
        Assert.assertTrue(logElements.contains("$5.00 $10.00"));
    }

    @Test
    public void testLogPurchaseToLogFile() throws IOException {
        TransactionLogger.logPurchase(testItem, 5.00);

        //read the file to make sure purchase is logged
        String logElements = new String (readAllBytes(Paths.get(TEST_LOG_PATH)));
        assertTrue(logElements.contains(testItem.getproductName() + ": $1.50 $5.00"));

    }

    @Test
    public void testLogGiveChange() throws IOException {
        TransactionLogger.setLogFilePath(TEST_LOG_PATH);
        File testFile = new File(TEST_LOG_PATH);

        double changeAmount = 5.00;
        double newBalance = 0.00;
        TransactionLogger.logGiveChange(changeAmount, newBalance);

        String logElements = new String(Files.readAllBytes(Paths.get(TEST_LOG_PATH)));

        assertTrue(logElements.contains("$5.00 $0.00"));
    }

    // keep the test file clean for next use
@AfterClass
    public static void cleanUp() {
        File testFile = new File(TEST_LOG_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
//changing line 48 to logFilePath changes the logger functionality-- variables at the top of transaction
//logger are commented out to attempt to resolve and now the test classes wont work without it.
//hard to test with everything writing to log.txt