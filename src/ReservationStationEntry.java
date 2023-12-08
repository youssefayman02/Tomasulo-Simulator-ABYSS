public class ReservationStationEntry {
    private int time;
    private String tag;
    boolean busy;
    private InstructionType op;
    private double vj;
    private double vk;
    private String qj;
    private String qk;
    private int address;

    public ReservationStationEntry(String tag) {
        this.time = 0;
        this.tag = tag;
        this.busy = false;
        this.op = null;
        this.vj = 0;
        this.vk = 0;
        this.qj = "";
        this.qk = "";
        this.address = 0;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public InstructionType getOp() {
        return op;
    }

    public void setOp(InstructionType op) {
        this.op = op;
    }

    public double getVj() {
        return vj;
    }

    public void setVj(double vj) {
        this.vj = vj;
    }

    public double getVk() {
        return vk;
    }

    public void setVk(double vk) {
        this.vk = vk;
    }

    public String getQj() {
        return qj;
    }

    public void setQj(String qj) {
        this.qj = qj;
    }

    public String getQk() {
        return qk;
    }

    public void setQk(String qk) {
        this.qk = qk;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ReservationStationEntry{" +
                "time=" + time +
                ", tag='" + tag + '\'' +
                ", busy=" + busy +
                ", op=" + op +
                ", vj=" + vj +
                ", vk=" + vk +
                ", qj='" + qj + '\'' +
                ", qk='" + qk + '\'' +
                ", address=" + address +
                '}';
    }
}
