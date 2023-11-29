public class ReservationStation {
    private int busy;
    private InstructionType op;
    private double vj;
    private double vk;
    private String qj;
    private String qk;
    private int time;

    public ReservationStation(int busy, InstructionType op, double vj, double vk, String qj, String qk, int time) {
        this.busy = busy;
        this.op = op;
        this.vj = vj;
        this.vk = vk;
        this.qj = qj;
        this.qk = qk;
        this.time = time;
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

    @Override
    public String toString() {
        return "ReservationStation{" +
                "busy=" + busy +
                ", op=" + op +
                ", vj=" + vj +
                ", vk=" + vk +
                ", qj='" + qj + '\'' +
                ", qk='" + qk + '\'' +
                ", time=" + time +
                '}';
    }
}
