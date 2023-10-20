package com.techelevator.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.techelevator.Chip;
import com.techelevator.Items;
import org.junit.Before;
import org.junit.Test;

public class ItemsTest {

    private Items item;

    @Before
    public void setUp() {
        // Create an item with an initial quantity of 5 and an item type of "Chip"
        item = new Chip("A1", "Potato Crisps", 3.05, 5);
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

    @Test
    public void canBeRestockedToMax() {
        // dispense four items and restock to max quantity
        item.dispense();
        item.dispense();
        item.dispense();
        item.dispense();
        item.restock();
        assertEquals(5, item.getQuantity());
    }
}