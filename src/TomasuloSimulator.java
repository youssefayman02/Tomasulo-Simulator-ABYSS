import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;

public class TomasuloSimulator {

    static Queue<Instruction> instructionQueue;
    static int programCounter;


    public static void loadInstructions(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                InstructionType type = InstructionType.valueOf(tokens[0]);
                int rs = Integer.parseInt(tokens[1]);
                int rt = Integer.parseInt(tokens[2]);
                int rd = Integer.parseInt(tokens[3]);
                int immediate = Integer.parseInt(tokens[4]);
                int address = Integer.parseInt(tokens[5]);
                Instruction instruction = new Instruction(type, rs, rt, rd, immediate, address);
                instructionQueue.add(instruction);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
    }
}
