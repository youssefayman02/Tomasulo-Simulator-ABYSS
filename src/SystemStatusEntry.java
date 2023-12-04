public class SystemStatusEntry {
    private String instruction;
    private int issuedAt;
    private int startedAt;
    private int finishedAt;
    private int writeBackAt;

    public SystemStatusEntry(String instruction, int issuedAt, int startedAt, int finishedAt, int writeBackAt) {
        this.instruction = instruction;
        this.issuedAt = issuedAt;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
