package stocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Stocks implements Iterable<StockEntry> {

    private final RandomAccessFile file;
    private final long recordSize = 14;

    Stocks(String path) throws FileNotFoundException {
        this.file = new RandomAccessFile(new File(path),"r");
    }

    public StockEntry get(int i) throws IOException {
        long position = i * recordSize;
        file.seek(position);

        // Reading entry from file into ByteBuffer
        int id = file.readInt();
        int nameLength = file.readShort();
        byte[] nameBytes = new byte[nameLength];
        file.readFully(nameBytes);
        String name = new String(nameBytes, StandardCharsets.UTF_8);
        int timestamp = file.readInt();
        float marketValue = file.readFloat();

        // Creating and returning StockEntry
        ByteBuffer buffer = ByteBuffer.allocate(14 + nameBytes.length);
        buffer.putInt(id);
        buffer.putShort((short) nameLength);
        buffer.put(nameBytes);
        buffer.putInt(timestamp);
        buffer.putFloat(marketValue);
        buffer.flip();

        return new StockEntry(buffer);
    }

    @Override
    public Iterator<StockEntry> iterator() {
        return new StockEntryIterator(file);
    }
}