public class StoreBufferEntry {

    private int address;
    private double vj;
    private String qj;
    private int time;
    private int busy;

    public StoreBufferEntry(int address, double vj, String qj, int time, int busy) {
        this.address = address;
        this.vj = vj;
        this.qj = qj;
        this.time = time;
        this.busy = busy;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public double getVj() {
        return vj;
    }

    public void setVj(double vj) {
        this.vj = vj;
    }

    public String getQj() {
        return qj;
    }

    public void setQj(String qj) {
        this.qj = qj;
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

    @Override
    public String toString() {
        return "StoreBufferEntry{" +
                "address=" + address +
                ", vj=" + vj +
                ", qj='" + qj + '\'' +
                ", time=" + time +
                ", busy=" + busy +
                '}';
    }
}