package stocks;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class StockEntryIterator implements Iterator<StockEntry> {

    private long pos;
    private final RandomAccessFile file;

    public StockEntryIterator(RandomAccessFile file) {
        this.file = file;
        this.pos = 0;
    }

    public boolean hasNext() {
        try {
            // Check if there is more data in the file by comparing the current position to the file length
            return pos < file.length();
        } catch (IOException e) {
            throw new RuntimeException("Error checking file length", e);
        }
    }

    public StockEntry next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more entries in the file.");
        }

        try {
            // Move the file pointer to the current position
            file.seek(pos);

            // Read ID (8 bytes)
            long id = file.readLong();

            // Read name length (2 bytes)
            short nameLength = file.readShort();

            // Read name (nameLength bytes)
            byte[] nameBytes = new byte[nameLength];
            file.readFully(nameBytes);
            String name = new String(nameBytes, StandardCharsets.UTF_8);

            // Read timestamp (8 bytes)
            int timestamp = (int) file.readLong();

            // Read market value (8 bytes)
            double marketValue = file.readDouble();

            // Update the position for the next entry
            pos = file.getFilePointer();

            // Create and return the StockEntry object
            return new StockEntry((int) id, name, timestamp, marketValue);

        } catch (IOException e) {
            throw new RuntimeException("Error reading next StockEntry", e);
        }
    }
}
