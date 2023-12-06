public class LoadBufferEntry {

    private int address;
    private double value;
    private int time;
    private int busy;
    private String tag;

    private static int nextTag = 0;

    public LoadBufferEntry(int address, double value, int time, int busy) {
        this.address = address;
        this.value = value;
        this.time = time;
        this.busy = busy;
        this.tag = "L" + (++nextTag);
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getBusy() {
        return busy;
    }

    public void setBusy(int busy) {
        this.busy = busy;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static int getNextTag() {
        return nextTag;
    }

    public static void setNextTag(int nextTag) {
        LoadBufferEntry.nextTag = nextTag;
    }
}
