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
        if (bb.remaining() < 8) {
            throw new IllegalArgumentException("Error parsing StockEntry: Insufficient data for 'id'. Required: 8 bytes, Remaining: " + bb.remaining());
        }
        this.id = bb.getLong();

        if (bb.remaining() < 2) {
            throw new IllegalArgumentException("Error parsing StockEntry: Insufficient data for 'name length'. Required: 2 bytes, Remaining: " + bb.remaining());
        }
        short nameLength = bb.getShort();

        if (bb.remaining() < nameLength) {
            throw new IllegalArgumentException("Error parsing StockEntry: Insufficient data for 'name'. Required: " + nameLength + " bytes, Remaining: " + bb.remaining());
        }
        byte[] nameBytes = new byte[nameLength];
        bb.get(nameBytes);
        this.name = new String(nameBytes, StandardCharsets.UTF_8);

        if (bb.remaining() < 8) {
            throw new IllegalArgumentException("Error parsing StockEntry: Insufficient data for 'timestamp'. Required: 8 bytes, Remaining: " + bb.remaining());
        }
        this.ts = bb.getLong();

        if (bb.remaining() < 8) {
            throw new IllegalArgumentException("Error parsing StockEntry: Insufficient data for 'market value'. Required: 8 bytes, Remaining: " + bb.remaining());
        }
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
        return 8 + 2 + name.getBytes(StandardCharsets.UTF_8).length + 8 + 8;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + ts + " " + value;
    }

    public ByteBuffer getBytes() {
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(getSerializedLength());

        buffer.putLong(id);
        buffer.putShort((short) nameBytes.length);
        buffer.put(nameBytes);
        buffer.putLong(ts);
        buffer.putDouble(value);

        buffer.flip();
        return buffer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof StockEntry) {
            StockEntry entry = (StockEntry) obj;
            return id == entry.id && name.equals(entry.name) && ts == entry.ts && value == entry.value;
        }
        return false;
    }
}
