public class Instruction {
    private InstructionType type;
    private int rs;
    private int rt;
    private int rd;
    private int immediate;
    private int address;

    public Instruction(InstructionType type, int rs, int rt, int rd, int immediate, int address) {
        this.type = type;
        this.rs = rs;
        this.rt = rt;
        this.rd = rd;
        this.immediate = immediate;
        this.address = address;
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

    public String toString() {
        String s = type.toString() + " ";
        switch (type) {
            case ADD:
            case SUB:
            case MUL:
            case DIV:
                s += "R" + rd + ", R" + rs + ", R" + rt;
                break;
            case LD:
            case SD:
                s += "R" + rt + ", " + immediate + "(R" + rs + ")";
                break;
            case ADDI:
            case SUBI:
                s += "R" + rt + ", R" + rs + ", #" + immediate;
                break;
            case BNEZ:
                s += "R" + rs + ", #" + immediate;
                break;
            default:
                break;
        }
        return s;
    }


}
