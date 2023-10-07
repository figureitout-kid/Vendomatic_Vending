package com.techelevator.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.techelevator.Items;
import org.junit.Before;
import org.junit.Test;

public class ItemsTest {

    private Items item;

    @Before
    public void setUp() {
        // Create an item with an initial quantity of 5
        item = new Items("A1", "Potato Crisps", 3.05, Items.ItemType.CHIP, 5);
    }


    @Test
    public void quantityCanBeDecreasedFrom5() {
        // Decrease quantity from 5 to 3
        for (int i = 0; i < 2; i++) {
            item.dispense();
        }

        assertEquals(3, item.getQuantity());
    }

    @Test
    public void itemCanBeMarkedAsSoldOut() {
        // Decrease quantity to 0
        for (int i = 0; i < 5; i++) {
            item.dispense();
        }

        assertTrue(item.isSoldOut());
    }
}
