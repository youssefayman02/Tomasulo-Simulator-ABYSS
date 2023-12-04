public class Instruction {
    private InstructionType type;
    private int rs;
    private int rt;
    private int rd;
    private int immediate;
    private int address;

    private String label;

    public Instruction(InstructionType type, int rs, int rt, int rd, int immediate, int address, String label) {
        this.type = type;
        this.rs = rs;
        this.rt = rt;
        this.rd = rd;
        this.immediate = immediate;
        this.address = address;
        this.label = label;
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
