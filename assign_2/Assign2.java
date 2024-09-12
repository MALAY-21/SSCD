import java.io.*;
import java.util.*;

public class Assign2 {
    public static void main(String[] args) {
        String filepath = "E:\\vscode\\Assignments\\SSCD\\assign_2\\input.txt";
        ArrayList<String> opcode = new ArrayList<>();
        ArrayList<String> op1 = new ArrayList<>();
        ArrayList<String> op2 = new ArrayList<>();
        LinkedHashMap<String, Integer> littab = new LinkedHashMap<>(); 
        Integer loccounter = 0;
        int literalIndex = 1; 

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens.length >= 3) {
                    opcode.add(tokens[0]);
                    op1.add(tokens[1]);
                    op2.add(tokens[2]);
                    if (tokens[2].startsWith("=")) {
                        littab.put(tokens[2], loccounter);
                    }
                    loccounter++;
                } else if (tokens.length == 2 && tokens[0].equals("START")) {
                    loccounter = Integer.parseInt(tokens[1]);
                } else {
                    System.err.println("Skipping improperly formatted line: " + line);
                    loccounter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Displaying the splitting
        System.out.println("Initially split\n");
        System.out.println("Opcode: " + opcode);
        System.out.println("Op1: " + op1);
        System.out.println("Op2: " + op2);

        // Hashtable for opcode
        Hashtable<String, String> is = new Hashtable<>();
        is.put("STOP", "00");
        is.put("ADD", "01");
        is.put("SUB", "02");
        is.put("MULT", "03");
        is.put("MOVER", "04");
        is.put("MOVEM", "05");
        is.put("COMP", "06");
        is.put("BC", "07");
        is.put("DIV", "08");
        is.put("READ", "09");
        is.put("PRINT", "10");

        // Hashtable for assembler directives
        Hashtable<String, String> ad = new Hashtable<>();
        ad.put("START", "01");
        ad.put("END", "02");

        // Hashtable for registers
        Hashtable<String, String> reg = new Hashtable<>();
        reg.put("AREG", "1");
        reg.put("BREG", "2");
        reg.put("CREG", "3");
        reg.put("DREG", "4");

        // Intermediate Code and Machine Code
        System.out.println("\nInstruction\tIntermediate Code\tMachine Code");
        for (int i = 0; i < opcode.size(); i++) {
            String code = opcode.get(i);
            String operand1 = op1.get(i);
            String operand2 = op2.get(i);

            StringBuilder intermediateCode = new StringBuilder();
            StringBuilder machineCode = new StringBuilder();

            if (is.containsKey(code)) {
                intermediateCode.append("(IS,").append(is.get(code)).append(")");
                machineCode.append(is.get(code));
            } else if (ad.containsKey(code)) {
                intermediateCode.append("(AD,").append(ad.get(code)).append(")");
                machineCode.append(ad.get(code));
            }

            if (reg.containsKey(operand1)) {
                intermediateCode.append(" (RG,").append(reg.get(operand1)).append(")");
                machineCode.append(" ").append(reg.get(operand1));
            }

            if (operand2.startsWith("=")) {
                intermediateCode.append(" (L,").append(littab.get(operand2)).append(")");
                machineCode.append(" ").append(littab.get(operand2));
            }

            System.out.println(code + "\t\t" + intermediateCode + "\t\t" + machineCode);
        }

        // Display the register table
        System.out.println("\nRegister Table:");
        System.out.println("Register\tCode");
        for (Map.Entry<String, String> entry : reg.entrySet()) {
            System.out.println(entry.getKey() + "\t\t" + entry.getValue());
        }

        // Display the literal table with index
        System.out.println("\nLiteral Table:");
        System.out.println("Index\tLiteral\t\tAddress");
        for (Map.Entry<String, Integer> entry : littab.entrySet()) {
            System.out.println(literalIndex + "\t" + entry.getKey() + "\t\t" + entry.getValue());
            literalIndex++;
        }
    }
}



