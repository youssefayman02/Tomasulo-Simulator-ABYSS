import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class TomasuloSimulator {

    public static int instructionId = 0;
    private ArrayList<Instruction> instructionQueue;
    private ReservationStation addSubReservationStation;
    private int addSubReservationStationSize;

    private ReservationStation mulDivReservationStation;
    private int mulDivReservationStationSize;

    private ReservationStation loadReservationStation;
    private int loadReservationStationSize;

    private ReservationStation storeReservationStation;
    private int storeReservationStationSize;
    private ArrayList<FloatingPointRegisterFileEntry> floatingPointRegisterFile;
    private int floatingPointRegisterFileSize = 32;

    private ArrayList<IntegerRegisterFileEntry> integerRegisterFile;
    private int integerRegisterFileSize = 32;

    private CommonDataBus cdb;

    private double[] cache = new double[124];
    private int addLatency;
    private int subLatency;
    private int daddLatency;
    private int dsubLatency;
    private int mulLatency;
    private int divLatency;
    private int storeLatency;
    private int loadLatency;
    private int addILatency = 1;
    private int subILatency = 1;
    private int bnezLatency = 1;
    private int clockCycle;
    private int programCounter;
    private boolean branchStall;

    public TomasuloSimulator() {
    }

    public static int getInstructionId() {
        return instructionId;
    }

    public static void setInstructionId(int instructionId) {
        TomasuloSimulator.instructionId = instructionId;
    }

    public ArrayList<Instruction> getInstructionQueue() {
        return instructionQueue;
    }

    public void setInstructionQueue(ArrayList<Instruction> instructionQueue) {
        this.instructionQueue = instructionQueue;
    }

    public ReservationStation getAddSubReservationStation() {
        return addSubReservationStation;
    }

    public void setAddSubReservationStation(ReservationStation addSubReservationStation) {
        this.addSubReservationStation = addSubReservationStation;
    }

    public int getAddSubReservationStationSize() {
        return addSubReservationStationSize;
    }

    public void setAddSubReservationStationSize(int addSubReservationStationSize) {
        this.addSubReservationStationSize = addSubReservationStationSize;
    }

    public ReservationStation getMulDivReservationStation() {
        return mulDivReservationStation;
    }

    public void setMulDivReservationStation(ReservationStation mulDivReservationStation) {
        this.mulDivReservationStation = mulDivReservationStation;
    }

    public int getMulDivReservationStationSize() {
        return mulDivReservationStationSize;
    }

    public void setMulDivReservationStationSize(int mulDivReservationStationSize) {
        this.mulDivReservationStationSize = mulDivReservationStationSize;
    }

    public ReservationStation getLoadReservationStation() {
        return loadReservationStation;
    }

    public void setLoadReservationStation(ReservationStation loadReservationStation) {
        this.loadReservationStation = loadReservationStation;
    }

    public int getLoadReservationStationSize() {
        return loadReservationStationSize;
    }

    public void setLoadReservationStationSize(int loadReservationStationSize) {
        this.loadReservationStationSize = loadReservationStationSize;
    }

    public ReservationStation getStoreReservationStation() {
        return storeReservationStation;
    }

    public void setStoreReservationStation(ReservationStation storeReservationStation) {
        this.storeReservationStation = storeReservationStation;
    }

    public int getStoreReservationStationSize() {
        return storeReservationStationSize;
    }

    public void setStoreReservationStationSize(int storeReservationStationSize) {
        this.storeReservationStationSize = storeReservationStationSize;
    }

    public ArrayList<FloatingPointRegisterFileEntry> getFloatingPointRegisterFile() {
        return floatingPointRegisterFile;
    }

    public void setFloatingPointRegisterFile(ArrayList<FloatingPointRegisterFileEntry> floatingPointRegisterFile) {
        this.floatingPointRegisterFile = floatingPointRegisterFile;
    }

    public int getFloatingPointRegisterFileSize() {
        return floatingPointRegisterFileSize;
    }

    public void setFloatingPointRegisterFileSize(int floatingPointRegisterFileSize) {
        this.floatingPointRegisterFileSize = floatingPointRegisterFileSize;
    }

    public ArrayList<IntegerRegisterFileEntry> getIntegerRegisterFile() {
        return integerRegisterFile;
    }

    public void setIntegerRegisterFile(ArrayList<IntegerRegisterFileEntry> integerRegisterFile) {
        this.integerRegisterFile = integerRegisterFile;
    }

    public int getIntegerRegisterFileSize() {
        return integerRegisterFileSize;
    }

    public void setIntegerRegisterFileSize(int integerRegisterFileSize) {
        this.integerRegisterFileSize = integerRegisterFileSize;
    }

    public CommonDataBus getCdb() {
        return cdb;
    }

    public void setCdb(CommonDataBus cdb) {
        this.cdb = cdb;
    }

    public double[] getCache() {
        return cache;
    }

    public void setCache(double[] cache) {
        this.cache = cache;
    }

    public int getAddLatency() {
        return addLatency;
    }

    public void setAddLatency(int addLatency) {
        this.addLatency = addLatency;
    }

    public int getSubLatency() {
        return subLatency;
    }

    public void setSubLatency(int subLatency) {
        this.subLatency = subLatency;
    }

    public int getDaddLatency() {
        return daddLatency;
    }

    public void setDaddLatency(int daddLatency) {
        this.daddLatency = daddLatency;
    }

    public int getDsubLatency() {
        return dsubLatency;
    }

    public void setDsubLatency(int dsubLatency) {
        this.dsubLatency = dsubLatency;
    }

    public int getMulLatency() {
        return mulLatency;
    }

    public void setMulLatency(int mulLatency) {
        this.mulLatency = mulLatency;
    }

    public int getDivLatency() {
        return divLatency;
    }

    public void setDivLatency(int divLatency) {
        this.divLatency = divLatency;
    }

    public int getStoreLatency() {
        return storeLatency;
    }

    public void setStoreLatency(int storeLatency) {
        this.storeLatency = storeLatency;
    }

    public int getLoadLatency() {
        return loadLatency;
    }

    public void setLoadLatency(int loadLatency) {
        this.loadLatency = loadLatency;
    }

    public int getAddILatency() {
        return addILatency;
    }

    public void setAddILatency(int addILatency) {
        this.addILatency = addILatency;
    }

    public int getSubILatency() {
        return subILatency;
    }

    public void setSubILatency(int subILatency) {
        this.subILatency = subILatency;
    }

    public int getBnezLatency() {
        return bnezLatency;
    }

    public void setBnezLatency(int bnezLatency) {
        this.bnezLatency = bnezLatency;
    }

    public int getClockCycle() {
        return clockCycle;
    }

    public void setClockCycle(int clockCycle) {
        this.clockCycle = clockCycle;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public boolean isBranchStall() {
        return branchStall;
    }

    public void setBranchStall(boolean branchStall) {
        this.branchStall = branchStall;
    }

    public void loadInstructions(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                InstructionType type = null;
                int rs = -1;
                int rt = -1;
                int rd = -1;
                long immediate = -1;
                int address = -1;
                String branchLabel = "";
                String instructionLabel = "";
                int i = 0;
                //I need to check if the instruction contains branch label or not
                if (tokens[0].contains(":")) {
                    instructionLabel = tokens[0].split(":")[0];
                    i = 1;
                }

                switch (tokens[i].toUpperCase()) {
                    case "L.D":
                    case "S.D":
                        type = InstructionType.valueOf(tokens[i].split("\\.")[0]);
                        rt = Integer.parseInt(tokens[i + 1].substring(1));
                        address = Integer.parseInt(tokens[i + 2]);
                        break;
                    case "ADD.D":
                    case "SUB.D":
                    case "MUL.D":
                    case "DIV.D":
                        type = InstructionType.valueOf(tokens[i].split("\\.")[0]);
                        rd = Integer.parseInt(tokens[i + 1].substring(1));
                        rs = Integer.parseInt(tokens[i + 2].substring(1));
                        rt = Integer.parseInt(tokens[i + 3].substring(1));
                        break;
                    case "DADD":
                    case "DSUB":
                        type = InstructionType.valueOf(tokens[i]);
                        rd = Integer.parseInt(tokens[i + 1].substring(1));
                        rs = Integer.parseInt(tokens[i + 2].substring(1));
                        rt = Integer.parseInt(tokens[i + 3].substring(1));
                        break;
                    case "ADDI":
                    case "SUBI":
                        type = InstructionType.valueOf(tokens[i]);
                        rd = Integer.parseInt(tokens[i + 1].substring(1));
                        rs = Integer.parseInt(tokens[i + 2].substring(1));
                        immediate = Long.parseLong(tokens[i + 3]);
                        break;
                    case "BNEZ":
                        type = InstructionType.valueOf(tokens[i]);
                        rt = Integer.parseInt(tokens[i + 1].substring(1));
                        branchLabel = tokens[i + 2];
                        break;
                    default:
                        break;
                }
                Instruction instruction = new Instruction(instructionId, type, rs, rt, rd, immediate, address, branchLabel, instructionLabel);
                instructionId++;
                instructionQueue.add(instruction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // compute branch addresses
        for (int i = 0; i < instructionQueue.size(); i++) {
            Instruction instruction = instructionQueue.get(i);
            if (instruction.getType().equals(InstructionType.BNEZ)) {
                for (int j = 0; j < instructionQueue.size(); j++) {
                    Instruction instruction1 = instructionQueue.get(j);
                    if (instruction.getBranchLabel().equals(instruction1.getInstructionLabel())) {
                        instruction.setAddress(j);
                    }
                }
            }
        }
    }


    public void fillRegisterFile() {
        for (int i = 0; i < this.integerRegisterFileSize; i++) {
            this.integerRegisterFile.add(new IntegerRegisterFileEntry((long) i, "0"));
        }

        for (int i = 0; i < this.floatingPointRegisterFileSize; i++) {
            this.floatingPointRegisterFile.add(new FloatingPointRegisterFileEntry((double) i, "0"));
        }
        integerRegisterFile.get(1).setValue(2L);
        floatingPointRegisterFile.get(2).setValue(2.0);

    }

    // fill cache with random int values
    public void fillCache () {
        Random random = new Random();
        for (int i = 0; i < this.cache.length; i++) {
            cache[i] = random.nextInt(100);
        }
        cache[0] = 2;
        cache[1] = 2;
    }

    public boolean allInstructionsDone() {
        /*
            Tomasulo will stop execution when all reservation stations are empty
        */
        return addSubReservationStation.isEmpty() && mulDivReservationStation.isEmpty() && loadReservationStation.isEmpty() && storeReservationStation.isEmpty();
    }

    /*
        1) add the instruction to the reservation station
        2) check if the source registers are available
        3) if they are available, update the reservation station with the values
        4) if they are not available, update the reservation station with the tags
        5) update the register file with the tag
    */
    public String issueAddSubReservationStation(Instruction instruction) {
        if (this.branchStall) {
            System.out.println("========== STALL, THERE IS A BRANCH STALL ==========");
            return "BRANCH STALL";
        }

        if (addSubReservationStation.isFull()) {
            System.out.println("========== STALL, The ADD/SUB RESERVATION STATION IS FULL ==========");
            return "STALL";
        }

        InstructionType op = instruction.getType();
        Object vj = 0;
        Object vk = 0;
        String qj = "0";
        String qk = "0";
        int time = -1;
        String tag = "";
        Instruction instruction1 = new Instruction(instruction.getInstructionId(), instruction.getType(), instruction.getRs(), instruction.getRt(), instruction.getRd(), instruction.getImmediate(), instruction.getAddress(), instruction.getBranchLabel(), instruction.getInstructionLabel());
        instruction1.setIssuedAt(clockCycle);
        System.out.println(instruction1.toString() + " Has Issued");

        // Check if the source registers are available
        if (op.equals(InstructionType.ADD) || op.equals(InstructionType.SUB)) {
            if (floatingPointRegisterFile.get(instruction.getRs()).getQi().equals("0")) {
                vj = floatingPointRegisterFile.get(instruction.getRs()).getValue();
            }
            else {
                qj = floatingPointRegisterFile.get(instruction.getRs()).getQi();
            }

            if (floatingPointRegisterFile.get(instruction.getRt()).getQi().equals("0")) {
                vk = floatingPointRegisterFile.get(instruction.getRt()).getValue();
            }
            else {
                qk = floatingPointRegisterFile.get(instruction.getRt()).getQi();
            }

            tag = addSubReservationStation.issueInstruction(time, true, op, vj, vk, qj, qk, -1, instruction1);
            // update the register file with the tag
            floatingPointRegisterFile.get(instruction.getRd()).setQi(tag);
            return tag;
        }

        if (op.equals(InstructionType.DADD) || op.equals(InstructionType.DSUB)) {
            if (integerRegisterFile.get(instruction.getRs()).getQi().equals("0")) {
                vj = integerRegisterFile.get(instruction.getRs()).getValue();
            }
            else {
                qj = integerRegisterFile.get(instruction.getRs()).getQi();
            }

            if (integerRegisterFile.get(instruction.getRt()).getQi().equals("0")) {
                vk = integerRegisterFile.get(instruction.getRt()).getValue();
            }
            else {
                qk = integerRegisterFile.get(instruction.getRt()).getQi();
            }

            tag = addSubReservationStation.issueInstruction(time, true, op, vj, vk, qj, qk, -1, instruction1);
            // update the register file with the tag
            integerRegisterFile.get(instruction.getRd()).setQi(tag);
            return tag;
        }

        if (op.equals(InstructionType.ADDI) || op.equals(InstructionType.SUBI)) {
            if (integerRegisterFile.get(instruction.getRs()).getQi().equals("0")) {
                vj = integerRegisterFile.get(instruction.getRs()).getValue();
            }
            else {
                qj = integerRegisterFile.get(instruction.getRs()).getQi();
            }

            vk = instruction.getImmediate();

            tag = addSubReservationStation.issueInstruction(time, true, op, vj, vk, qj, qk, -1, instruction1);
            // update the register file with the tag
            integerRegisterFile.get(instruction.getRd()).setQi(tag);
            return tag;
        }

        if (op.equals(InstructionType.BNEZ)) {
            if (integerRegisterFile.get(instruction.getRt()).getQi().equals("0")) {
                vj = integerRegisterFile.get(instruction.getRt()).getValue();
            }
            else {
                qj = integerRegisterFile.get(instruction.getRt()).getQi();
            }

            tag = addSubReservationStation.issueInstruction(time, true, op, vj, vk, qj, qk, instruction.getAddress(), instruction1);
            this.branchStall = true;
            return tag;
        }
         return tag;
    }

    public String issueMulDivReservationStation(Instruction instruction) {
        if (mulDivReservationStation.isFull()) {
            System.out.println("========== STALL, The MUL/DIV RESERVATION STATION IS FULL ==========");
            return "STALL";
        }

        boolean busy = true;
        String tag = "";
        InstructionType op = instruction.getType();
        Object vj = 0;
        Object vk = 0;
        String qj = "0";
        String qk = "0";
        int time = -1;
        Instruction instruction1 = new Instruction(instruction.getInstructionId(), instruction.getType(), instruction.getRs(), instruction.getRt(), instruction.getRd(), instruction.getImmediate(), instruction.getAddress(), instruction.getBranchLabel(), instruction.getInstructionLabel());
        instruction1.setIssuedAt(clockCycle);
        System.out.println(instruction1.toString() + " Has Issued");

        // Check if the source registers are available
        if (op.equals(InstructionType.MUL) || op.equals(InstructionType.DIV)) {
            if (floatingPointRegisterFile.get(instruction.getRs()).getQi().equals("0")) {
                vj = floatingPointRegisterFile.get(instruction.getRs()).getValue();
            }
            else {
                qj = floatingPointRegisterFile.get(instruction.getRs()).getQi();
            }

            if (floatingPointRegisterFile.get(instruction.getRt()).getQi().equals("0")) {
                vk = floatingPointRegisterFile.get(instruction.getRt()).getValue();
            }
            else {
                qk = floatingPointRegisterFile.get(instruction.getRt()).getQi();
            }
        }

        tag = mulDivReservationStation.issueInstruction(time, busy, op, vj, vk, qj, qk, -1, instruction1);
        // update the register file with the tag
        floatingPointRegisterFile.get(instruction.getRd()).setQi(tag);

        return tag;

    }


    public String issueLoadReservationStation(Instruction instruction) {
        if (loadReservationStation.isFull()) {
            System.out.println("========== STALL, The LOAD RESERVATION STATION IS FULL ==========");
            return "STALL";
        }

        int time = -1;
        String tag = "";
        boolean busy = true;
        Object vj = 0;
        Object vk = 0;
        String qj = "0";
        String qk = "0";
        int address = instruction.getAddress();
        Instruction instruction1 = new Instruction(instruction.getInstructionId(), instruction.getType(), instruction.getRs(), instruction.getRt(), instruction.getRd(), instruction.getImmediate(), instruction.getAddress(), instruction.getBranchLabel(), instruction.getInstructionLabel());
        instruction1.setIssuedAt(clockCycle);
        System.out.println(instruction1.toString() + " Has Issued");

        tag = loadReservationStation.issueInstruction(time, busy, instruction.getType(), vj, vk, qj, qk, address, instruction1);

        // update the register file with the tag
        floatingPointRegisterFile.get(instruction.getRt()).setQi(tag);

        return tag;
    }

    public String issueStoreReservationStation(Instruction instruction) {
        if (storeReservationStation.isFull()) {
            System.out.println("========== STALL, The STORE RESERVATION STATION IS FULL ==========");
            return "STALL";
        }

        int time = -1;
        String tag = "";
        boolean busy = true;
        Object vj = 0;
        Object vk = 0;
        String qj = "0";
        String qk = "0";
        int address = instruction.getAddress();
        Instruction instruction1 = new Instruction(instruction.getInstructionId(), instruction.getType(), instruction.getRs(), instruction.getRt(), instruction.getRd(), instruction.getImmediate(), instruction.getAddress(), instruction.getBranchLabel(), instruction.getInstructionLabel());
        instruction1.setIssuedAt(clockCycle);
        System.out.println(instruction1.toString() + " Has Issued");

        if (floatingPointRegisterFile.get(instruction.getRt()).getQi().equals("0")) {
            vj = floatingPointRegisterFile.get(instruction.getRt()).getValue();
        }
        else {
            qj = floatingPointRegisterFile.get(instruction.getRt()).getQi();
        }

        tag = storeReservationStation.issueInstruction(time, busy, instruction.getType(), vj, vk, qj, qk, address, instruction1);
        return tag;
    }

    public String issue (Instruction instruction) {

        switch (instruction.getType()) {
            case ADD:
            case SUB:
            case DADD:
            case DSUB:
            case ADDI:
            case SUBI:
            case BNEZ:
                return issueAddSubReservationStation(instruction);
            case MUL:
            case DIV:
                return issueMulDivReservationStation(instruction);
            case L:
                return issueLoadReservationStation(instruction);
            case S:
                return issueStoreReservationStation(instruction);
            default:
                break;
        }
        return "";
    }

    public int getLatency(InstructionType type) {
        switch (type) {
            case ADD:
                return addLatency;
            case DADD:
                return daddLatency;
            case SUB:
                return subLatency;
            case DSUB:
                return dsubLatency;
            case MUL:
                return mulLatency;
            case DIV:
                return divLatency;
            case ADDI:
                return addILatency;
            case SUBI:
                return subILatency;
            case BNEZ:
                return bnezLatency;
            case L:
                return loadLatency;
            case S:
                return storeLatency;
            default:
                return 0;
        }
    }

    /*
        Check for instructions in the reservation station that can execute
        The tag is used in order to skip the instruction that has been issued in the same clock cycle
    */
    public void execute(String tag) {
        // check the add/sub reservation station
        for (int i = 0; i < addSubReservationStation.getSize(); i++) {
            ReservationStationEntry entry = addSubReservationStation.getStations()[i];
            if (entry.isBusy() && !entry.getTag().equals(tag) && entry.getTime() > 0) {
                    if (entry.getQj().equals("0") && entry.getQk().equals("0")) {
                        if (entry.getTime() == getLatency(entry.getOp())) {
                            System.out.println(entry.getInstruction().toString() + " Has Started");
                        }
                            entry.setTime(entry.getTime() - 1);
                        if (entry.getTime() == 0) {
                            System.out.println(entry.getInstruction().toString() + " Has Finished");
                        }
                }
            }
        }

        // check the mul/div reservation station
        for (int i = 0; i < mulDivReservationStation.getSize(); i++) {
            ReservationStationEntry entry = mulDivReservationStation.getStations()[i];
            if (entry.isBusy() && !entry.getTag().equals(tag) && entry.getTime() > 0) {
                if (entry.getQj().equals("0") && entry.getQk().equals("0")) {
                    if (entry.getTime() == getLatency(entry.getOp())) {
                        System.out.println(entry.getInstruction().toString() + " Has Started");
                    }
                    entry.setTime(entry.getTime() - 1);
                    if (entry.getTime() == 0) {
                        System.out.println(entry.getInstruction().toString() + " Has Finished");
                    }
                }
            }
        }

        // check the load reservation station
        for (int i = 0; i < loadReservationStation.getSize(); i++) {
            ReservationStationEntry entry = loadReservationStation.getStations()[i];
            if (entry.isBusy() && !entry.getTag().equals(tag) && entry.getTime() > 0) {
                if (entry.getQj().equals("0") && entry.getQk().equals("0")) {
                    if (entry.getTime() == getLatency(entry.getOp())) {
                        System.out.println(entry.getInstruction().toString() + " Has Started");
                    }
                    entry.setTime(entry.getTime() - 1);
                    if (entry.getTime() == 0) {
                        System.out.println(entry.getInstruction().toString() + " Has Finished");
                    }
                }
            }
        }

        // check the store reservation station
        for (int i = 0; i < storeReservationStation.getSize(); i++) {
            ReservationStationEntry entry = storeReservationStation.getStations()[i];
            if (entry.isBusy() && !entry.getTag().equals(tag) && entry.getTime() > 0) {
                if (entry.getQj().equals("0") && entry.getQk().equals("0")) {
                    if (entry.getTime() == getLatency(entry.getOp())) {
                        System.out.println(entry.getInstruction().toString() + " Has Started");
                    }
                    entry.setTime(entry.getTime() - 1);
                    if (entry.getTime() == 0) {
                        System.out.println(entry.getInstruction().toString() + " Has Finished");
                    }
                }
            }
        }
    }

    public void writeBack() {

        for (int i = 0; i < storeReservationStation.getSize(); i++) {
            ReservationStationEntry entry = storeReservationStation.getStations()[i];
            Instruction instruction = entry.getInstruction();
            if (entry.isBusy() && this.clockCycle >= instruction.getWriteBackAt() && instruction.getWriteBackAt() != -1) {
                cache[entry.getAddress()] = (double) entry.getVj();
                clearReservationStationEntry(entry);
            }
        }

        for (int i = 0; i < addSubReservationStation.getSize(); i++) {
            ReservationStationEntry entry = addSubReservationStation.getStations()[i];
            Instruction instruction = entry.getInstruction();
            if (entry.isBusy() && this.clockCycle >= instruction.getWriteBackAt() && instruction.getWriteBackAt() != -1) {
                if (entry.getOp().equals(InstructionType.BNEZ)) {
                    branchStall = false;
                    if ((long) entry.getVj() != 0) {
                        System.out.println(instruction + " Branch is taken");
                        programCounter = instruction.getAddress();
                    } else {
                        System.out.println(instruction + " Branch is not taken");
                        this.programCounter++;
                    }
                    clearReservationStationEntry(entry);
                    continue;
                }
                switch (entry.getOp()) {
                    case ADD:
                        System.out.println(instruction + " writes on the common data bus");
                        double addResult = (double) entry.getVj() + (double) entry.getVk();
                        cdb.setTag(entry.getTag());
                        cdb.setValue(addResult);
                        clearReservationStationEntry(entry);
                        return;
                    case SUB:
                        System.out.println(instruction + " writes on the common data bus");
                        double subResult = (double) entry.getVj() - (double) entry.getVk();
                        cdb.setTag(entry.getTag());
                        cdb.setValue(subResult);
                        clearReservationStationEntry(entry);
                        return;
                    case DADD:
                        System.out.println(instruction + " writes on the common data bus");
                        long daddResult = (long) entry.getVj() + (long) entry.getVk();
                        cdb.setTag(entry.getTag());
                        cdb.setValue(daddResult);
                        clearReservationStationEntry(entry);
                        return;
                    case DSUB:
                        System.out.println(instruction + " writes on the common data bus");
                        long dsubResult = (long) entry.getVj() - (long) entry.getVk();
                        cdb.setTag(entry.getTag());
                        cdb.setValue(dsubResult);
                        clearReservationStationEntry(entry);
                        return;
                    case ADDI:
                        System.out.println(instruction + " writes on the common data bus");
                        long addiResult = (long) entry.getVj() + (long) entry.getVk();
                        cdb.setTag(entry.getTag());
                        cdb.setValue(addiResult);
                        clearReservationStationEntry(entry);
                        return;
                    case SUBI:
                        System.out.println(instruction + " writes on the common data bus");
                        long subiResult = (long) entry.getVj() - (long) entry.getVk();
                        cdb.setTag(entry.getTag());
                        cdb.setValue(subiResult);
                        clearReservationStationEntry(entry);
                        return;
                }
            }
        }

        for (int i = 0; i < mulDivReservationStation.getSize(); i++) {
            ReservationStationEntry entry = mulDivReservationStation.getStations()[i];
            Instruction instruction = entry.getInstruction();
            if (entry.isBusy() && this.clockCycle >= instruction.getWriteBackAt() && instruction.getWriteBackAt() != -1) {
                switch (entry.getOp()) {
                    case MUL:
                        System.out.println(instruction + " writes on the common data bus");
                        double mulResult = (double) entry.getVj() * (double) entry.getVk();
                        cdb.setTag(entry.getTag());
                        cdb.setValue(mulResult);
                        clearReservationStationEntry(entry);
                        return;
                    case DIV:
                        System.out.println(instruction + " writes on the common data bus");
                        double divResult =  (double) entry.getVj() / (double) entry.getVk();
                        cdb.setTag(entry.getTag());
                        cdb.setValue(divResult);
                        clearReservationStationEntry(entry);
                        return;
                }
            }
        }

        for (int i = 0; i < loadReservationStation.getSize(); i++) {
            ReservationStationEntry entry = loadReservationStation.getStations()[i];
            Instruction instruction = entry.getInstruction();
            if (entry.isBusy() && this.clockCycle >= instruction.getWriteBackAt() && instruction.getWriteBackAt() != -1) {
                System.out.println(instruction + " writes on the common data bus");
                double loadResult = cache[entry.getAddress()];
                cdb.setTag(entry.getTag());
                cdb.setValue(loadResult);
                clearReservationStationEntry(entry);
                return;
            }
        }
    }

    public static void clearReservationStationEntry(ReservationStationEntry entry) {
        entry.setTime(-1);
        entry.setBusy(false);
        entry.setOp(null);
        entry.setVj(0);
        entry.setVk(0);
        entry.setQj("0");
        entry.setQk("0");
        entry.setAddress(0);
        entry.setInstruction(null);
    }

    /*
        Update the reservation stations with the values from the CDB
    */
    public void updateReservationStations() {
        String key = cdb.getTag();
        Object value = cdb.getValue();
        for (int i = 0; i < addSubReservationStation.getSize(); i++) {
            ReservationStationEntry entry = addSubReservationStation.getStations()[i];
            Instruction instruction = entry.getInstruction();
            if (entry.getQj().equals(key)) {
                entry.setVj(value);
                entry.setQj("0");
            }
            if (entry.getQk().equals(key)) {
                entry.setVk(value);
                entry.setQk("0");
            }

            if (entry.isBusy() && entry.getQj().equals("0") && entry.getQk().equals("0") && entry.getTime() == -1) {
                entry.setTime(getLatency(entry.getOp()));
                instruction.setStartedAt(this.clockCycle + 1);
                instruction.setFinishedAt(this.clockCycle + getLatency(entry.getOp()));
                instruction.setWriteBackAt(this.clockCycle + getLatency(entry.getOp()) + 1);
            }
        }

        for (int i = 0; i < mulDivReservationStation.getSize(); i++) {
            ReservationStationEntry entry = mulDivReservationStation.getStations()[i];
            Instruction instruction = entry.getInstruction();
            if (entry.getQj().equals(key)) {
                entry.setVj(value);
                entry.setQj("0");
            }
            if (entry.getQk().equals(key)) {
                entry.setVk(value);
                entry.setQk("0");
            }

            if (entry.isBusy() && entry.getQj().equals("0") && entry.getQk().equals("0") && entry.getTime() == -1) {
                entry.setTime(getLatency(entry.getOp()));
                instruction.setStartedAt(this.clockCycle + 1);
                instruction.setFinishedAt(this.clockCycle + getLatency(entry.getOp()));
                instruction.setWriteBackAt(this.clockCycle + getLatency(entry.getOp()) + 1);
            }
        }

        for (int i = 0; i < loadReservationStation.getSize(); i++) {
            ReservationStationEntry entry = loadReservationStation.getStations()[i];
            Instruction instruction = entry.getInstruction();
            if (entry.getQj().equals(key)) {
                entry.setVj(value);
                entry.setQj("0");
            }
            if (entry.getQk().equals(key)) {
                entry.setVk(value);
                entry.setQk("0");
            }

            if (entry.isBusy() && entry.getQj().equals("0") && entry.getQk().equals("0") && entry.getTime() == -1) {
                entry.setTime(getLatency(entry.getOp()));
                instruction.setStartedAt(this.clockCycle + 1);
                instruction.setFinishedAt(this.clockCycle + getLatency(entry.getOp()));
                instruction.setWriteBackAt(this.clockCycle + getLatency(entry.getOp()) + 1);
            }
        }

        for (int i = 0; i < storeReservationStation.getSize(); i++) {
            ReservationStationEntry entry = storeReservationStation.getStations()[i];
            Instruction instruction = entry.getInstruction();
            if (entry.getQj().equals(key)) {
                entry.setVj(value);
                entry.setQj("0");
            }
            if (entry.getQk().equals(key)) {
                entry.setVk(value);
                entry.setQk("0");
            }

            if (entry.isBusy() && entry.getQj().equals("0") && entry.getQk().equals("0") && entry.getTime() == -1) {
                entry.setTime(getLatency(entry.getOp()));
                instruction.setStartedAt(this.clockCycle + 1);
                instruction.setFinishedAt(this.clockCycle + getLatency(entry.getOp()));
                instruction.setWriteBackAt(this.clockCycle + getLatency(entry.getOp()) + 1);
            }
        }

        for (int i = 0; i < floatingPointRegisterFile.size(); i++) {
            FloatingPointRegisterFileEntry entry = floatingPointRegisterFile.get(i);
            if (entry.getQi().equals(key)) {
                entry.setValue((double) value);
                entry.setQi("0");
            }
        }

        for (int i = 0; i < integerRegisterFile.size(); i++) {
            IntegerRegisterFileEntry entry = integerRegisterFile.get(i);
            if (entry.getQi().equals(key)) {
                entry.setValue((long) value);
                entry.setQi("0");
            }
        }
    }


    public void simulate () {
        while ((!allInstructionsDone()) || clockCycle == 1 || programCounter < instructionQueue.size()){
            System.out.println("============================= Clock Cycle: " + this.clockCycle + " ===============================");
            if (programCounter < instructionQueue.size()) {
                Instruction instruction = instructionQueue.get(programCounter);

                // we use this tag in order to skip the issued instruction as we don't want to issue and execute the same instruction in the same clock cycle
                String tag = issue(instruction);

                execute(tag);

                writeBack();

                updateReservationStations();

                setClockCycle(getClockCycle() + 1);

                if (branchStall || tag.equals("STALL") || tag.equals("BRANCH STALL")) {
                    this.print();
                    this.cdb.clearCommonDataBus();
                    continue;
                }


                programCounter++;

            }
            else {
                execute("");
                writeBack();
                updateReservationStations();
                setClockCycle(getClockCycle() + 1);
            }

            this.print();
            this.cdb.clearCommonDataBus();

        }

        System.out.println("============================= Clock Cycle: " + this.clockCycle + " ===============================");
        this.print();
    }

    public void print() {
        System.out.println("******************** PROGRAM COUNTER " + programCounter + " ********************");
        System.out.println("******************** INSTRUCTION QUEUE ********************");
        for (int i = 0; i < instructionQueue.size(); i++) {
            System.out.println(instructionQueue.get(i).toString());
        }
        System.out.println("******************** ADD/SUB Reservation Station ********************");
        for (int i = 0; i < addSubReservationStation.getSize(); i++) {
            System.out.println(addSubReservationStation.getStations()[i].toString());
        }
        System.out.println("******************** MUL/DIV Reservation Station ********************");
        for (int i = 0; i < mulDivReservationStation.getSize(); i++) {
            System.out.println(mulDivReservationStation.getStations()[i].toString());
        }
        System.out.println("******************** LOAD BUFFER ******************** ");
        for (int i = 0; i < loadReservationStation.getSize(); i++) {
            System.out.println(loadReservationStation.getStations()[i].toString());
        }
        System.out.println("******************** STORE BUFFER ******************** ");
        for (int i = 0; i < storeReservationStation.getSize(); i++) {
            System.out.println(storeReservationStation.getStations()[i].toString());
        }

        System.out.println("******************** Common Data Bus ******************** ");
        System.out.println(cdb.toString());

        System.out.println("******************** Floating Point Register File ******************** ");
        for (int i = 0; i < floatingPointRegisterFile.size(); i++) {
            System.out.println("F" + i + ": " +floatingPointRegisterFile.get(i).toString());
        }
        System.out.println("******************** Integer Register File ******************** ");
        for (int i = 0; i < integerRegisterFile.size(); i++) {
            System.out.println("R" + i + ": " + integerRegisterFile.get(i).toString());
        }
        System.out.println("******************** Cache ******************** ");
        for (int i = 0; i < cache.length; i++) {
            System.out.println("M" + i + ": " + cache[i]);
        }
    }

    public void takeInputsFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Add/Sub Reservation Station Size: ");
        this.setAddSubReservationStationSize(sc.nextInt());
        System.out.println("==============================");
        System.out.print("Mul/Div Reservation Station Size: ");
        this.setMulDivReservationStationSize(sc.nextInt());
        System.out.println("==============================");
        System.out.print("Load Buffer Size: ");
        this.setLoadReservationStationSize(sc.nextInt());
        System.out.println("==============================");
        System.out.print("Store Buffer Size: ");
        this.setStoreReservationStationSize(sc.nextInt());
        System.out.println("==============================");
        System.out.print("ADD.D Latency: ");
        this.setAddLatency(sc.nextInt());
        System.out.println("==============================");
        System.out.print("SUB.D Latency: ");
        this.setSubLatency(sc.nextInt());
        System.out.println("==============================");
        System.out.print("DADD Latency: ");
        this.setDaddLatency(sc.nextInt());
        System.out.println("==============================");
        System.out.print("DSUB Latency: ");
        this.setDsubLatency(sc.nextInt());
        System.out.println("==============================");
        System.out.print("MUL.D Latency: ");
        this.setMulLatency(sc.nextInt());
        System.out.println("==============================");
        System.out.print("DIV.D Latency: ");
        this.setDivLatency(sc.nextInt());
        System.out.println("==============================");
        System.out.print("L.D Latency: ");
        this.setLoadLatency(sc.nextInt());
        System.out.println("==============================");
        System.out.print("S.D Latency: ");
        this.setStoreLatency(sc.nextInt());
        System.out.println("==============================");
    }

    public void init() {
        this.addSubReservationStation = new ReservationStation(this.addSubReservationStationSize, "A");
        this.mulDivReservationStation = new ReservationStation(this.mulDivReservationStationSize, "D");
        this.loadReservationStation = new ReservationStation(this.loadReservationStationSize, "L");
        this.storeReservationStation = new ReservationStation(this.storeReservationStationSize, "S");
        this.integerRegisterFile = new ArrayList<>();
        this.floatingPointRegisterFile = new ArrayList<>();
        this.instructionQueue = new ArrayList<>();
        this.cdb = new CommonDataBus();
        this.clockCycle = 1;
        this.programCounter = 0;
        this.branchStall = false;
        this.fillCache();
        this.fillRegisterFile();
    }

    public static void main(String[] args) {
        TomasuloSimulator simulator = new TomasuloSimulator();
        simulator.takeInputsFromUser();
        simulator.init();
        simulator.loadInstructions("src/testCases3.txt");
        simulator.print();
        System.out.println("==============================");
        simulator.simulate();
    }
}
