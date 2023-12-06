public class Instruction {
    private InstructionType type;
    private int rs;
    private int rt;
    private int rd;
    private int immediate;
    private int address;
    private String branchLabel;
    private int issuedAt;
    private int startedAt;
    private int finishedAt;
    private int writeBackAt;

    private int instructionId;

    public Instruction(int instructionId,InstructionType type, int rs, int rt, int rd, int immediate, int address, String branchLabel) {
        this.instructionId = instructionId;
        this.type = type;
        this.rs = rs;
        this.rt = rt;
        this.rd = rd;
        this.immediate = immediate;
        this.address = address;
        this.branchLabel = branchLabel;
        this.issuedAt = -1;
        this.startedAt = -1;
        this.finishedAt = -1;
        this.writeBackAt = -1;
    }

    public int getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(int instructionId) {
        this.instructionId = instructionId;
    }

    public InstructionType getType() {
        return type;
    }

    public void setType(InstructionType type) {
        this.type = type;
    }

    public int getRs() {
        return rs;
    }

    public void setRs(int rs) {
        this.rs = rs;
    }

    public int getRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public int getRd() {
        return rd;
    }

    public void setRd(int rd) {
        this.rd = rd;
    }

    public int getImmediate() {
        return immediate;
    }

    public void setImmediate(int immediate) {
        this.immediate = immediate;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getBranchLabel() {
        return branchLabel;
    }

    public void setBranchLabel(String branchLabel) {
        this.branchLabel = branchLabel;
    }

    public int getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(int issuedAt) {
        this.issuedAt = issuedAt;
    }

    public int getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(int startedAt) {
        this.startedAt = startedAt;
    }

    public int getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(int finishedAt) {
        this.finishedAt = finishedAt;
    }

    public int getWriteBackAt() {
        return writeBackAt;
    }

    public void setWriteBackAt(int writeBackAt) {
        this.writeBackAt = writeBackAt;
    }
}
