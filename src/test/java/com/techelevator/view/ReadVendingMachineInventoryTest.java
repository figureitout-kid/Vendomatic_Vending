package com.techelevator.view;

import com.techelevator.Items;
import com.techelevator.ReadVendingMachineInventory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.*;

public class ReadVendingMachineInventoryTest {

    private static final String TEST_FILE_PATH = "test_vending.csv";

    @Before
    public void setUp() throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
        writer.write("A1|Wonka Bar|1.50|CANDY\n");
        writer.write("A2|Cola|1.25|DRINK\n");
        }
    }


    @Test
    public void testReadItemsFromCSV() throws IOException {
        //Act
        List<Items> items = ReadVendingMachineInventory.readItemsFromCSV(TEST_FILE_PATH);

        //Assert
        assertEquals(2, items.size());

        Items item1 = items.get(0);
        assertEquals("A1", item1.getSlotLocation());
        assertEquals("Wonka Bar", item1.getproductName());
        assertEquals(1.50, item1.getPrice(), 0.01);
        assertEquals(Items.ItemType.CANDY, item1.getType());
        assertEquals(5, item1.getQuantity());

        Items item2 = items.get(1);
        assertEquals("A2", item2.getSlotLocation());
        assertEquals("Cola", item2.getproductName());
        assertEquals(1.25, item2.getPrice(), 0.01);
        assertEquals(Items.ItemType.DRINK, item2.getType());
        assertEquals(5, item2.getQuantity());

    }

    @After
    public void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

}
