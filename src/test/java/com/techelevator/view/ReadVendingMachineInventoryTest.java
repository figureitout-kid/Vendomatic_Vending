import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.techelevator.Chip;
import com.techelevator.Items;
import com.techelevator.ReadVendingMachineInventory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReadVendingMachineInventoryTest {

    private static final String TEST_FILE_PATH = "test_vending.csv";

    @Before
    public void setUp() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
            writer.write("A1|Potato Crisps|3.05|Chip\n");
            writer.write("A2|Doritos|2.50|Chip\n");
        }
    }

    @Test
    public void testReadItemsFromCSV() throws IOException {
        // Act
        List<Items> items = ReadVendingMachineInventory.readItemsFromCSV(TEST_FILE_PATH);

        // Assert
        assertEquals(2, items.size());

        Chip chipItem1 = (Chip) items.get(0);
        assertEquals("A1", chipItem1.getSlotLocation());
        assertEquals("Potato Crisps", chipItem1.getProductName());
        assertEquals(3.05, chipItem1.getPrice(), 0.01);
        assertEquals(5, chipItem1.getQuantity());

        Chip chipItem2 = (Chip) items.get(1);
        assertEquals("A2", chipItem2.getSlotLocation());
        assertEquals("Doritos", chipItem2.getProductName());
        assertEquals(2.50, chipItem2.getPrice(), 0.01);
        assertEquals(5, chipItem2.getQuantity());
    }

    @After
    public void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
