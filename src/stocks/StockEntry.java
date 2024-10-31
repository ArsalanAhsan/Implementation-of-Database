package stocks;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class StockEntry {
    private final long id;
    private final String name;
    private final long ts;
    private final double value;

    public StockEntry(int id, String name, int timestamp, double market_value) {
        this.id = id;
        this.name = name;
        this.ts = timestamp;
        this.value = market_value;
    }

    public StockEntry(ByteBuffer bb) {
        this.id = bb.getLong();

        // Read name length (2 bytes)
        int nameLength = bb.getShort();

        // Read name (nameLength bytes)
        byte[] nameBytes = new byte[nameLength];
        bb.get(nameBytes);
        this.name = new String(nameBytes, StandardCharsets.UTF_8);

        // Read timestamp (8 bytes)
        this.ts = bb.getLong();

        // Read market value (8 bytes)
        this.value = bb.getDouble();
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public long getTimeStamp() {
        return this.ts;
    }

    public double getMarketValue() {
        return this.value;
    }

    public int getSerializedLength() {
        return 3 * 8 + 2 + name.getBytes().length;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + ts + " " + value;
    }

    public ByteBuffer getBytes() {
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(getSerializedLength());

        // Write ID (8 bytes)
        buffer.putLong(id);

        // Write name length (2 bytes) and name (nameLength bytes)
        buffer.putShort((short) nameBytes.length);
        buffer.put(nameBytes);

        // Write timestamp (8 bytes)
        buffer.putLong(ts);

        // Write market value (8 bytes)
        buffer.putDouble(value);

        buffer.flip(); // Prepare buffer for reading
        return buffer;
    }

    public boolean equals(Object obj) {
        if (obj instanceof StockEntry) {
            StockEntry entry = (StockEntry) obj;
            return id == entry.id && name.equals(entry.name) && ts == entry.ts && value == entry.value;
        }
        return false;
    }
}
