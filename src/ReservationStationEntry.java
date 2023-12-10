public class ReservationStationEntry {
    private int time;
    private String tag;
    boolean busy;
    private InstructionType op;
    private Object vj;
    private Object vk;
    private String qj;
    private String qk;
    private int address;
    private Instruction instruction;

    public ReservationStationEntry(String tag) {
        this.time = -1;
        this.tag = tag;
        this.busy = false;
        this.op = null;
        this.vj = 0;
        this.vk = 0;
        this.qj = "0";
        this.qk = "0";
        this.address = 0;
        this.instruction = null;
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

    public Object getVj() {
        return vj;
    }

    public void setVj(Object vj) {
        this.vj = vj;
    }

    public Object getVk() {
        return vk;
    }

    public void setVk(Object vk) {
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

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
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
                ", instruction=" + instruction +
                '}';
    }
}
