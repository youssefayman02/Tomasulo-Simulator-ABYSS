public class ReservationStation {
    private int busy;
    private InstructionType op;
    private double vj;
    private double vk;
    private String qj;
    private String qk;
    private int time;
    private String tag;

    public ReservationStation(int busy, InstructionType op, double vj, double vk, String qj, String qk, int time, String tag) {
        this.busy = busy;
        this.op = op;
        this.vj = vj;
        this.vk = vk;
        this.qj = qj;
        this.qk = qk;
        this.time = time;
        this.tag = tag;
    }

    public int getBusy() {
        return busy;
    }

    public void setBusy(int busy) {
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
}
