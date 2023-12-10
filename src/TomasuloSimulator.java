import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class TomasuloSimulator {

    public static int instructionId = 0;
    private ArrayList<Instruction> instructionQueue;
    private ReservationStation addSubReservationStation;
    private int addSubReservationStationSize = 3;

    private ReservationStation mulDivReservationStation;
    private int mulDivReservationStationSize = 3;

    private ReservationStation loadReservationStation;
    private int loadReservationStationSize = 3;

    private ReservationStation storeReservationStation;
    private int storeReservationStationSize = 3;
    private ArrayList<FloatingPointRegisterFileEntry> floatingPointRegisterFile;
    private int floatingPointRegisterFileSize = 64;

    private ArrayList<IntegerRegisterFileEntry> integerRegisterFile;
    private int integerRegisterFileSize = 32;

    private CommonDataBus cdb;

    private double[] cache = new double[50];
    private int addLatency = 2;
    private int subLatency = 2;
    private int mulLatency = 10;
    private int divLatency = 40;
    private int storeLatency = 2;
    private int loadLatency = 2;
    private int addILatency = 1;
    private int subILatency = 1;
    private int bnezLatency = 1;
    private int clockCycle;
    private int programCounter;

    public TomasuloSimulator() {
        this.instructionQueue = new ArrayList<>();
        this.addSubReservationStation = new ReservationStation(addSubReservationStationSize, "A");
        this.mulDivReservationStation = new ReservationStation(mulDivReservationStationSize, "M");
        this.loadReservationStation = new ReservationStation(loadReservationStationSize, "L");
        this.storeReservationStation = new ReservationStation(storeReservationStationSize, "S");
        this.floatingPointRegisterFile = new ArrayList<>();
        this.integerRegisterFile = new ArrayList<>();
        this.cdb = new CommonDataBus();
        this.clockCycle = 1;
        this.programCounter = 0;
        this.fillCache();
    }

    public void setAddSubReservationStationSize(int addSubReservationStationSize) {
        this.addSubReservationStationSize = addSubReservationStationSize;
    }

    public void setMulDivReservationStationSize(int mulDivReservationStationSize) {
        this.mulDivReservationStationSize = mulDivReservationStationSize;
    }

    public void incrementClockCycle() {
        this.clockCycle += 1;
    }

    public void setAddLatency(int addLatency) {
        this.addLatency = addLatency;
    }

    public void setSubLatency(int subLatency) {
        this.subLatency = subLatency;
    }

    public void setMulLatency(int mulLatency) {
        this.mulLatency = mulLatency;
    }

    public void setDivLatency(int divLatency) {
        this.divLatency = divLatency;
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
                int immediate = -1;
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
                        immediate = Integer.parseInt(tokens[i + 3]);
                        break;
                    case "BNEZ":
                        type = InstructionType.valueOf(tokens[i]);
                        rt = Integer.parseInt(tokens[i + 1].substring(1));
                        branchLabel = tokens[i + 2];
                        break;
                    default:
                        break;



//                switch (type) {
//                    case L :
//                    case S:
//                        rt = Integer.parseInt(tokens[1].split("")[1]);
//                        address = Integer.parseInt(tokens[2]);
//                        break;
//                    case ADD:
//                    case DADD:
//                    case DSUB:
//                    case SUB:
//                    case DIV:
//                    case MUL:
//                        rd = Integer.parseInt(tokens[1].split("")[1]);
//                        rs = Integer.parseInt(tokens[2].split("")[1]);
//                        rt = Integer.parseInt(tokens[3].split("")[1]);
//                        break;
//                    case SUBI:
//                    case ADDI:
//                        rd = Integer.parseInt(tokens[1].split("")[1]);
//                        rs = Integer.parseInt(tokens[2].split("")[1]);
//                        immediate = Integer.parseInt(tokens[3]);
//                        break;
//                    case BNEZ:
//                        rt = Integer.parseInt(tokens[1].split("")[1]);
//                        address = 0;
//                        break;
//                    default:
//                        break;
//                }
//
                }
                Instruction instruction = new Instruction(instructionId, type, rs, rt, rd, immediate, address, branchLabel, instructionLabel);
                instructionId++;
                instructionQueue.add(instruction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillRegisterFile(int registerNumber) {
        for (int i = 0; i < registerNumber; i++) {
            this.integerRegisterFile.add(new IntegerRegisterFileEntry((long) i, "0"));
            this.floatingPointRegisterFile.add(new FloatingPointRegisterFileEntry(i, "0"));
        }
    }

    // fill cache with random int values
    public void fillCache () {
        Random random = new Random();
        for (int i = 0; i < this.cache.length; i++) {
            cache[i] = random.nextInt(100);
        }
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
            case DADD:
                return addLatency;
            case SUB:
            case DSUB:
                return subLatency;
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
                        int addiResult = (int) entry.getVj() + (int) entry.getVk();
                        cdb.setTag(entry.getTag());
                        cdb.setValue(addiResult);
                        clearReservationStationEntry(entry);
                        return;
                    case SUBI:
                        System.out.println(instruction + " writes on the common data bus");
                        int subiResult = (int) entry.getVj() - (int) entry.getVk();
                        cdb.setTag(entry.getTag());
                        cdb.setValue(subiResult);
                        clearReservationStationEntry(entry);
                        return;
//                    case BNEZ:
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

        this.cdb.clearCommonDataBus();

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

                incrementClockCycle();

                if (tag.equals("STALL")) {
                    continue;
                }

                programCounter++;
            }
            else {
                execute("");
                writeBack();
                updateReservationStations();
                incrementClockCycle();
            }

            this.print();

        }

        System.out.println("============================= Clock Cycle: " + this.clockCycle + " ===============================");
        this.print();
    }

    public void print() {
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
        System.out.println("******************** Floating Point Register File ******************** ");
        for (int i = 0; i < floatingPointRegisterFile.size(); i++) {
            System.out.println("F" + i + ": " +floatingPointRegisterFile.get(i).toString());
        }
        System.out.println("******************** Integer Register File ******************** ");
        for (int i = 0; i < integerRegisterFile.size(); i++) {
            System.out.println("R" + i + ": " + integerRegisterFile.get(i).toString());
        }
        System.out.println("******************** Common Data Bus ******************** ");
        System.out.println(cdb.toString());
        System.out.println("******************** Cache ******************** ");
        for (int i = 0; i < cache.length; i++) {
            System.out.println("M" + i + ": " + cache[i]);
        }
    }

    public static void main(String[] args) {
        TomasuloSimulator simulator = new TomasuloSimulator();
        Scanner sc = new Scanner(System.in);

//        System.out.print("Add/Sub Reservation Station Size: ");
//        simulator.setAddSubReservationStationSize(sc.nextInt());
//        System.out.println("==============================");
//        System.out.print("Mul/Div Reservation Station Size: ");
//        simulator.setMulDivReservationStationSize(sc.nextInt());
//        System.out.println("==============================");
//        System.out.print("Add Latency: ");
//        simulator.setAddLatency(sc.nextInt());
//        System.out.println("==============================");
//        System.out.print("Sub Latency: ");
//        simulator.setSubLatency(sc.nextInt());
//        System.out.println("==============================");
//        System.out.print("Mul Latency: ");
//        simulator.setMulLatency(sc.nextInt());
//        System.out.println("==============================");
//        System.out.print("Div Latency: ");
//        simulator.setDivLatency(sc.nextInt());
//        System.out.println("==============================");

        simulator.loadInstructions("src/testCases2.txt");
        simulator.fillRegisterFile(32);
//        simulator.executeInstructions();
//        simulator.instructionQueue.forEach(instruction -> {
//            System.out.println(instruction);
//        });
        System.out.println(simulator.instructionQueue.toString());
        System.out.println("==============================");
        simulator.simulate();
    }
}
