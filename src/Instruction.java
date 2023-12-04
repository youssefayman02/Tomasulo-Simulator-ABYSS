public class Instruction {
    private InstructionType type;
    private int rs;
    private int rt;
    private int rd;
    private int immediate;
    private int address;

    private String label;

    private int issuedAt;
    private int startedAt;
    private int finishedAt;
    private int writeBackAt;

    public Instruction(InstructionType type, int rs, int rt, int rd, int immediate, int address, String label) {
        this.type = type;
        this.rs = rs;
        this.rt = rt;
        this.rd = rd;
        this.immediate = immediate;
        this.address = address;
        this.label = label;
        this.issuedAt = -1;
        this.startedAt = -1;
        this.writeBackAt = -1;
        this.finishedAt = -1;
    }

    public void setIssuedAt(int issuedAt) {
        this.issuedAt = issuedAt;
    }

    public void setStartedAt(int startedAt) {
        this.startedAt = startedAt;
    }

    public void setFinishedAt(int finishedAt) {
        this.finishedAt = finishedAt;
    }

    public void setWriteBackAt(int writeBackAt) {
        this.writeBackAt = writeBackAt;
    }

    public int getIssuedAt() {
        return issuedAt;
    }

    public int getStartedAt() {
        return startedAt;
    }

    public int getFinishedAt() {
        return finishedAt;
    }

    public int getWriteBackAt() {
        return writeBackAt;
    }

    public InstructionType getType() {
        return type;
    }

    public int getRs() {
        return rs;
    }

    public int getRt() {
        return rt;
    }

    public int getRd() {
        return rd;
    }

    public int getImmediate() {
        return immediate;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "type=" + type +
                ", rs=" + rs +
                ", rt=" + rt +
                ", rd=" + rd +
                ", immediate=" + immediate +
                ", address=" + address +
                ", label='" + label + '\'' +
                '}';
    }
}
