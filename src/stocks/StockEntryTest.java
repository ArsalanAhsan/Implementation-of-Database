package stocks;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StockEntryTest {
    @Test
    void getBytesTest() {
        StockEntry e = new StockEntry(1, "dummy", 2, 0.0);
        ByteBuffer bb = e.getBytes();
        byte[] arr = new byte[bb.remaining()];
        bb.get(arr);
        assertArrayEquals(arr, new byte[]{
                0, 0, 0, 0, 0, 0, 0, 1,
                0, 5, 'd', 'u', 'm', 'm', 'y',
                0, 0, 0, 0, 0, 0, 0, 2,
                0, 0, 0, 0, 0, 0, 0, 0
        });
    }

    @Test
    void constructorTest() {
        byte[] arr = new byte[]{
                0, 0, 0, 0, 0, 0, 0, 1,
                0, 5, 'd', 'u', 'm', 'm', 'y',
                0, 0, 0, 0, 0, 0, 0, 2,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        ByteBuffer bb = ByteBuffer.wrap(arr);
        StockEntry e = new StockEntry(bb);
        assertEquals(e, new StockEntry(1, "dummy", 2, 0.0));
    }
}