public class LoadBufferEntry {

    private int address;
    private double value;
    private int time;
    private int busy;

    public LoadBufferEntry(int address, double value, int time, int busy) {
        this.address = address;
        this.value = value;
        this.time = time;
        this.busy = busy;
    }

    public int getAddress() {
        return address;
    }

    public double getValue() {
        return value;
    }

    public int getTime() {
        return time;
    }

    public int getBusy() {
        return busy;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setBusy(int busy) {
        this.busy = busy;
    }

    public String toString() {
        return "Address: " + address + ", Value: " + value + ", Time: " + time + ", Busy: " + busy;
    }
}
