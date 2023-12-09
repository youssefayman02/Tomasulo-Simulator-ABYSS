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

    private Hashtable<String, Integer> cdb;

    private double[] cache = new double[1024];
    private int addLatency = 5;
    private int subLatency = 6;
    private int mulLatency = 7;
    private int divLatency = 8;
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
        this.cdb = new Hashtable<>();
        this.clockCycle = 0;
        this.programCounter = 0;
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
                String label = "";
                InstructionType type = null;
                int rs = 0;
                int rt = 0;
                int rd = 0;
                int immediate = 0;
                int address = 0;

                if (line.split(":").length > 1) {
                    label = tokens[0].split(":")[0];
                    type = InstructionType.valueOf(tokens[1].split("\\.")[0]);
                    tokens = Arrays.copyOfRange(tokens, 1, tokens.length);
                }

                else {
                    type = InstructionType.valueOf(tokens[0].split("\\.")[0]);
                }

                switch (type) {
                    case L :
                    case S:
                        rt = Integer.parseInt(tokens[1].split("")[1]);
                        address = Integer.parseInt(tokens[2]);
                        break;
                    case ADD:
                    case DADD:
                    case DSUB:
                    case SUB:
                    case DIV:
                    case MUL:
                        rd = Integer.parseInt(tokens[1].split("")[1]);
                        rs = Integer.parseInt(tokens[2].split("")[1]);
                        rt = Integer.parseInt(tokens[3].split("")[1]);
                        break;
                    case SUBI:
                    case ADDI:
                        rd = Integer.parseInt(tokens[1].split("")[1]);
                        rs = Integer.parseInt(tokens[2].split("")[1]);
                        immediate = Integer.parseInt(tokens[3]);
                        break;
                    case BNEZ:
                        rt = Integer.parseInt(tokens[1].split("")[1]);
                        address = 0;
                        break;
                    default:
                        break;
                }

                Instruction instruction = new Instruction(instructionId, type, rs, rt, rd, immediate, address, label);
                instructionId++;
                instructionQueue.add(instruction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void handleAddSubInstruction(Instruction instruction) {
//        if (addSubReservationStation.size() > addSubReservationStationSize) {
//            System.out.println("==========STALL==========");
//            return;
//        }
//        System.out.println(instruction.toString() + " Has Issued");
//        instruction.setIssuedAt(clockCycle);
//        double vj = 0;
//        double vk = 0;
//        String qj = "0";
//        String qk = "0";
//        int time = 0;
//
//        if (instruction.getType().equals(InstructionType.ADD)) {
//            time = this.addLatency;
//        }
//        else if (instruction.getType().equals(InstructionType.SUB)) {
//            time = this.subLatency;
//        }
//
//        if (floatingPointRegisterFile.get(instruction.getRs()).getQi().equals("0")) {
//            vj = Integer.parseInt(floatingPointRegisterFile.get(instruction.getRs()).getQi());
//        }
//        else {
//            qj = floatingPointRegisterFile.get(instruction.getRs()).getQi();
//        }
//
//        if (floatingPointRegisterFile.get(instruction.getRt()).getQi().equals("0")) {
//            vk = Integer.parseInt(floatingPointRegisterFile.get(instruction.getRt()).getQi());
//        }
//        else {
//            qk =  floatingPointRegisterFile.get(instruction.getRt()).getQi();
//        }
//
//        if (vj != 0 && vk != 0) {
//            instruction.setStartedAt(clockCycle + 1);
//            instruction.setFinishedAt(instruction.getStartedAt() + time - 1);
//            instruction.setWriteBackAt(instruction.getFinishedAt() + 1);
//        }
//        ReservationStationEntry reservationStation = new ReservationStationEntry(1, instruction.getType(), vj, vk, qj, qk, time, "A" + (++addSubReservationStationCounter));
//        addSubReservationStation.add(reservationStation);
//    }
//
//    public void printData() {
//        /*System.out.println("Clock Cycle: " + clockCycle);
//        System.out.println("ADD/SUB Reservation Station: ");
//        System.out.println(addSubReservationStation.toString());
//        System.out.println("MUL/DIV Reservation Station: ");
//        System.out.println(mulDivReservationStation.toString());*/
//    }
//
//    public void checkMulDivReservationStations() {
//    }
//
//    public void checkAddSubReservationStations() {
//        for (ReservationStationEntry addSubReservationEntry : this.addSubReservationStation) {
//            // check if an instruction in the reservation station has finished excecuting
//        }
//    }
//
    public void fillRegisterFile(int registerNumber) {
        for (int i = 0; i < registerNumber; i++) {
            this.integerRegisterFile.add(new IntegerRegisterFileEntry((long) i, "0"));
            this.floatingPointRegisterFile.add(new FloatingPointRegisterFileEntry(i, "0"));
        }
    }
//
//
//    public void executeInstructions() {
//        while (!allInstructionsDone()) {
//            for (Instruction instruction : instructionQueue) {
//                System.out.println("Clock Cycle: " + this.clockCycle);
//                switch (instruction.getType()) {
//                    case ADD:
//                    case SUB:
//                        handleAddSubInstruction(instruction);
//                        break;
//                    default:
//                        break;
//                }
//                // check all reservation stations for instructions that can excecute
//                checkAddSubReservationStations();
//                checkMulDivReservationStations();
//                clockCycle += 1;
//            }
//        }
//    }
//

    public boolean allInstructionsDone() {
        /*
            Tomasulo will stop execution when two conditions are satisfied:
            1) All reservation stations are empty
            2) The program counter hits the end of the instruction queue
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
            return "";
        }

        System.out.println(instruction.toString() + " Has Issued");
        instruction.setIssuedAt(clockCycle);

        InstructionType op = instruction.getType();
        double vj = 0;
        double vk = 0;
        String qj = "0";
        String qk = "0";
        int time = 0;
        String tag = "";

        switch (op) {
            case ADD:
            case DADD:
                time = this.addLatency;
                break;
            case SUB:
            case DSUB:
                time = this.subLatency;
                break;
            case ADDI:
                time = this.addILatency;
                break;
            case SUBI:
                time = this.subILatency;
                break;
            case BNEZ:
                time = this.bnezLatency;
                break;
            default:
                break;
        }



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

            tag = addSubReservationStation.issueInstruction(time, true, op, vj, vk, qj, qk, -1);
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

            tag = addSubReservationStation.issueInstruction(time, true, op, vj, vk, qj, qk, -1);
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

            tag = addSubReservationStation.issueInstruction(time, true, op, vj, vk, qj, qk, -1);
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

            tag = addSubReservationStation.issueInstruction(time, true, op, vj, vk, qj, qk, instruction.getAddress());
            return tag;
        }
         return tag;
    }

    public String issueMulDivReservationStation(Instruction instruction) {
        if (mulDivReservationStation.isFull()) {
            System.out.println("========== STALL, The MUL/DIV RESERVATION STATION IS FULL ==========");
            return "";
        }

        System.out.println(instruction.toString() + " Has Issued");
        instruction.setIssuedAt(clockCycle);

        boolean busy = true;
        String tag = "";
        InstructionType op = instruction.getType();
        double vj = 0;
        double vk = 0;
        String qj = "0";
        String qk = "0";
        int time = 0;

        switch (op) {
            case MUL:
                time = this.mulLatency;
                break;
            case DIV:
                time = this.divLatency;
                break;
            default:
                break;
        }

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

        tag = mulDivReservationStation.issueInstruction(time, busy, op, vj, vk, qj, qk, -1);
        // update the register file with the tag
        floatingPointRegisterFile.get(instruction.getRd()).setQi(tag);

        return tag;

    }


    public String issueLoadReservationStation(Instruction instruction) {
        if (loadReservationStation.isFull()) {
            System.out.println("========== STALL, The LOAD RESERVATION STATION IS FULL ==========");
            return "";
        }

        System.out.println(instruction.toString() + " Has Issued");
        instruction.setIssuedAt(clockCycle);

        int time = this.loadLatency;
        String tag = "";
        boolean busy = true;
        double vj = 0;
        double vk = 0;
        String qj = "0";
        String qk = "0";
        int address = instruction.getAddress();

        tag = loadReservationStation.issueInstruction(time, busy, instruction.getType(), vj, vk, qj, qk, address);

        // update the register file with the tag
        floatingPointRegisterFile.get(instruction.getRt()).setQi(tag);

        return tag;
    }

    public String issueStoreReservationStation(Instruction instruction) {
        if (storeReservationStation.isFull()) {
            System.out.println("========== STALL, The STORE RESERVATION STATION IS FULL ==========");
            return "";
        }

        System.out.println(instruction.toString() + " Has Issued");
        instruction.setIssuedAt(clockCycle);

        int time = this.storeLatency;
        String tag = "";
        boolean busy = true;
        double vj = 0;
        double vk = 0;
        String qj = "0";
        String qk = "0";
        int address = instruction.getAddress();

        if (floatingPointRegisterFile.get(instruction.getRt()).getQi().equals("0")) {
            vj = floatingPointRegisterFile.get(instruction.getRt()).getValue();
        }
        else {
            qj = floatingPointRegisterFile.get(instruction.getRt()).getQi();
        }

        tag = storeReservationStation.issueInstruction(time, busy, instruction.getType(), vj, vk, qj, qk, address);
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

    /*
        Check for instructions in the reservation station that can execute
        The tag is used in order to skip the instruction that has been issued in the same clock cycle
    */
    public void execute(String tag) {
        // check the add/sub reservation station
        for (int i = 0; i < addSubReservationStation.getSize(); i++) {
            ReservationStationEntry entry = addSubReservationStation.getStations()[i];
            if (entry.isBusy() && !entry.getTag().equals(tag) && entry.getTime() != 0) {

                if (entry.getQj().equals("0") && entry.getQk().equals("0")) {
                    entry.setTime(entry.getTime() - 1);
                    if (entry.getTime() == 0) {
                        entry.setBusy(false);
                        entry.setQj("0");
                        entry.setQk("0");
                        entry.setVj(0);
                        entry.setVk(0);
                        entry.setOp(null);
                        entry.setTag("");
                    }
                }
            }
        }
    }

    public void writeBack() {}


    public void simulate () {
        System.out.println(allInstructionsDone());
        while ((!allInstructionsDone()) || clockCycle == 0){
            if (programCounter >= instructionQueue.size()) {
                return;
            }
            System.out.println(programCounter);
            Instruction instruction = instructionQueue.get(programCounter);

            // we use this tag in order to skip the issued instruction as we don't want to issue and execute the same instruction in the same clock cycle
            String tag = issue(instruction);

            execute(tag);

            writeBack();

            programCounter++;

        }
    }

    public void print() {
        System.out.println("Clock Cycle: " + clockCycle);
        System.out.println("ADD/SUB Reservation Station: ");
        System.out.println(addSubReservationStation.toString());
        System.out.println("MUL/DIV Reservation Station: ");
        System.out.println(mulDivReservationStation.toString());
        System.out.println("LOAD Reservation Station: ");
        System.out.println(loadReservationStation.toString());
        System.out.println("STORE Reservation Station: ");
        System.out.println(storeReservationStation.toString());
        System.out.println("Floating Point Register File: ");
        System.out.println(floatingPointRegisterFile.toString());
        System.out.println("Integer Register File: ");
        System.out.println(integerRegisterFile.toString());
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

        simulator.loadInstructions("src/testCases.txt");
        simulator.fillRegisterFile(32);
//        simulator.executeInstructions();
//        simulator.instructionQueue.forEach(instruction -> {
//            System.out.println(instruction);
//        });
        System.out.println(simulator.instructionQueue.size());
        System.out.println("==============================");
        simulator.simulate();
        simulator.print();
    }
}
