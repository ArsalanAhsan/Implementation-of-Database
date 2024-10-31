package stocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class Stocks implements Iterable<StockEntry> {

    private final RandomAccessFile file;

    public Stocks(String path) throws FileNotFoundException {
        this.file = new RandomAccessFile(new File(path), "r");
    }

    public StockEntry get(int i) throws IOException {
        try {
            file.seek(calculatePosition(i));

            long id = file.readLong();
            short nameLength = file.readShort();

            if (nameLength < 0 || file.length() < file.getFilePointer() + nameLength) {
                throw new IOException("Error reading name: Insufficient data for 'name' field. Expected length: " + nameLength);
            }
            byte[] nameBytes = new byte[nameLength];
            file.readFully(nameBytes);
            String name = new String(nameBytes, StandardCharsets.UTF_8);

            long timestamp = file.readLong();
            double marketValue = file.readDouble();

            ByteBuffer buffer = ByteBuffer.allocate(8 + 2 + nameLength + 8 + 8);
            buffer.putLong(id);
            buffer.putShort(nameLength);
            buffer.put(nameBytes);
            buffer.putLong(timestamp);
            buffer.putDouble(marketValue);
            buffer.flip();

            return new StockEntry(buffer);
        } catch (IOException e) {
            throw new IOException("Failed to read record at index " + i + ": " + e.getMessage(), e);
        }
    }

    private long calculatePosition(int recordIndex) throws IOException {
        long position = 0;
        for (int i = 0; i < recordIndex; i++) {
            file.seek(position);
            file.readLong();
            int nameLength = file.readShort();
            position += 8 + 2 + nameLength + 8 + 8;
        }
        return position;
    }

    @Override
    public Iterator<StockEntry> iterator() {
        return new StockEntryIterator(file);
    }
}
