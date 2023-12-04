import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class TomasuloSimulator {

    private ArrayList<Instruction> instructionQueue;
    private int programCounter;

    public TomasuloSimulator() {
        this.instructionQueue = new ArrayList<>();
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

                if (tokens[0].split(":").length > 0) {
                    label = tokens[0].split(":")[0];
                    type = InstructionType.valueOf(tokens[1].split("\\.")[0]);
                    tokens = Arrays.copyOfRange(tokens, 1, tokens.length);
                }

                else {
                    type = InstructionType.valueOf(tokens[0].split("\\.")[0]);
                }

                switch (type) {
                    case L :
                        rt = Integer.parseInt(tokens[1].split("")[1]);
                        address = Integer.parseInt(tokens[2]);
                        break;
                    case S:
                        rt = Integer.parseInt(tokens[1].split("")[1]);
                        address = Integer.parseInt(tokens[2]);
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
                        rt = Integer.parseInt(tokens[3]);
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

    public static void main(String[] args) {
        TomasuloSimulator simulator = new TomasuloSimulator();
        simulator.loadInstructions("src/testCases.txt");
        simulator.instructionQueue.forEach(instruction -> {
            System.out.println(instruction);
        });
    }
}
