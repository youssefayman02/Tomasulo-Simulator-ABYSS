import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class TomasuloSimulator {

    private ArrayList<Instruction> instructionQueue;
    private ArrayList<ReservationStation> addSubReservationStation;
    private ArrayList<ReservationStation> mulDivReservationStation;
    private ArrayList<RegisterFileEntry> registerFile;
    private ArrayList<RegisterFileEntry> floatingPointRegisterFile;

    private int addSubReservationStationSize;
    private int mulDivReservationStationSize;
    private int programCounter;

    private int addLatency;
    private int subLatency;
    private int mulLatency;
    private int divLatency;
    private int clockCycle;

    public TomasuloSimulator() {
        this.instructionQueue = new ArrayList<>();
        this.addSubReservationStation = new ArrayList<>();
        this.mulDivReservationStation = new ArrayList<>();
        this.registerFile = new ArrayList<>();
        this.floatingPointRegisterFile = new ArrayList<>();
        this.clockCycle = 1;
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

                Instruction instruction = new Instruction(type, rs, rt, rd, immediate, address, label);
                instructionQueue.add(instruction);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAddSubInstruction(Instruction instruction) {
        if (addSubReservationStation.size() > addSubReservationStationSize) {
            System.out.println("==========STALL==========");
            return;
        }
        System.out.println(instruction.toString() + " Has Issued");
        instruction.setIssuedAt(clockCycle);
        double vj = 0;
        double vk = 0;
        String qj = "0";
        String qk = "0";
        int time = 0;

        if (instruction.getType().equals(InstructionType.ADD)) {
            time = this.addLatency;
        }
        else if (instruction.getType().equals(InstructionType.SUB)) {
            time = this.subLatency;
        }

        if (registerFile.get(instruction.getRs()).getQi().equals("0")) {
            vj = Integer.parseInt(registerFile.get(instruction.getRs()).getQi());
        }
        else {
            qj = registerFile.get(instruction.getRs()).getQi();
        }

        if (registerFile.get(instruction.getRt()).getQi().equals("0")) {
            vk = Integer.parseInt(registerFile.get(instruction.getRt()).getQi());
        }
        else {
            qk =  registerFile.get(instruction.getRt()).getQi();
        }

        if (vj != 0 && vk != 0) {
            instruction.setStartedAt(clockCycle + 1);
            instruction.setFinishedAt(instruction.getStartedAt() + time - 1);
            instruction.setWriteBackAt(instruction.getFinishedAt() + 1);
        }
        ReservationStation reservationStation = new ReservationStation(1, instruction.getType(), vj, vk, qj, qk, time);
        addSubReservationStation.add(reservationStation);
    }

    public void printData() {
        /*System.out.println("Clock Cycle: " + clockCycle);
        System.out.println("ADD/SUB Reservation Station: ");
        System.out.println(addSubReservationStation.toString());
        System.out.println("MUL/DIV Reservation Station: ");
        System.out.println(mulDivReservationStation.toString());*/
    }

    public void checkMulDivReservationStations() {
    }

    public void checkAddSubReservationStations() {

    }

    public void fillRegisterFile(int registerNumber) {
        for (int i = 0; i < registerNumber; i++) {
            this.registerFile.add(new RegisterFileEntry(i, "0"));
            this.floatingPointRegisterFile.add(new RegisterFileEntry(i, "0"));
        }
    }

    public boolean allInstructionsDone() {
        boolean finished = true;
        for (Instruction instruction : this.instructionQueue) {
            if (instruction.getWriteBackAt() < this.clockCycle) {
                return false;
            }
        }
        return this.programCounter > this.instructionQueue.size() && finished;
    }

    public void executeInstructions() {
        while (!allInstructionsDone()) {
            for (Instruction instruction : instructionQueue) {
                System.out.println("Clock Cycle: " + this.clockCycle);
                switch (instruction.getType()) {
                    case ADD:
                    case SUB:
                        handleAddSubInstruction(instruction);
                        break;
                    default:
                        break;
                }
                clockCycle += 1;
            }
        }
    }

    public static void main(String[] args) {
        TomasuloSimulator simulator = new TomasuloSimulator();
        Scanner sc = new Scanner(System.in);

        System.out.print("Add/Sub Reservation Station Size: ");
        simulator.setAddSubReservationStationSize(sc.nextInt());
        System.out.println("==============================");
        System.out.print("Mul/Div Reservation Station Size: ");
        simulator.setMulDivReservationStationSize(sc.nextInt());
        System.out.println("==============================");
        System.out.print("Add Latency: ");
        simulator.setAddLatency(sc.nextInt());
        System.out.println("==============================");
        System.out.print("Sub Latency: ");
        simulator.setSubLatency(sc.nextInt());
        System.out.println("==============================");
        System.out.print("Mul Latency: ");
        simulator.setMulLatency(sc.nextInt());
        System.out.println("==============================");
        System.out.print("Div Latency: ");
        simulator.setDivLatency(sc.nextInt());
        System.out.println("==============================");

        simulator.loadInstructions("src/testCases2.txt");
        simulator.fillRegisterFile(32);
        simulator.executeInstructions();
//        simulator.instructionQueue.forEach(instruction -> {
//            System.out.println(instruction);
//        });
    }
}
