package stocks;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class StocksTest {

    @Test
    void getTest() {
        try {
            Stocks stocks = new Stocks("stocks.bin");
            StockEntry e1 = stocks.get(0);
            StockEntry e2 = stocks.get(1);
            StockEntry e3 = stocks.get(8);

            assertEquals(e1, new StockEntry(0, "Volvo", 0, 99f));
            assertEquals(e2, new StockEntry(1, "BMW", 0, 99f));
            assertEquals(e3, new StockEntry(8, "Volvo", 2, 98.99010000000001));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void iteratorTest() {
        try {
            Stocks stocks = new Stocks("stocks.bin");
            Iterator<StockEntry> it = stocks.iterator();
            assertTrue(it.hasNext());
            assertEquals(it.next(), new StockEntry(0, "Volvo", 0, 99f));
            assertEquals(it.next(), new StockEntry(1, "BMW", 0, 99f));
            it.next();
            it.next();
            it.next();
            it.next();
            it.next();
            it.next();
            assertEquals(it.next(), new StockEntry(8, "Volvo", 2, 98.99010000000001));
            int count = 9;
            while (it.hasNext()) {
                it.next();
                count++;
            }
            assertEquals(count, 40000);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}