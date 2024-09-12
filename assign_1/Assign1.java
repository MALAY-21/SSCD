import java.io.*;
import java.util.*;

public class Assign1 {
    public static void main(String[] args) {
        String filepath="E://vscode//Assignments//SSCD//assign_1//input.txt";
        ArrayList<String>opcode=new ArrayList<>();
        ArrayList<String>op1=new ArrayList<>();
        ArrayList<String>op2=new ArrayList<>();

        try(BufferedReader br=new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line=br.readLine())!=null) {
                String[] tokens=line.split(" ");
                if(tokens.length>=3){
                    opcode.add(tokens[0]);
                    op1.add(tokens[1]);
                    op2.add(tokens[2]);
                }
                else{
                    System.err.println("Skipping improper format line"+line);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //displaying the spliiting
        System.out.println("Initially splitted\n");
        System.out.println("opcode: "+opcode);
        System.out.println("op1: "+op1);
        System.out.println("op2: "+op2);

        //Hashtable for opcode
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

        //Hashtable for assembler directives
        Hashtable<String, String> ad = new Hashtable<>();
        ad.put("START", "01");
        ad.put("END", "02");

        //hashtable for registers
        Hashtable<String, String> reg = new Hashtable<>();
        reg.put("AREG", "1");
        reg.put("BREG", "2");
        reg.put("CREG", "3");
        reg.put("DREG", "4");

        System.out.println("\nInstruction and Directive Codes:");
        for (String code : opcode) {
            if (is.containsKey(code)) {
                System.out.println(code + " - Instruction Code: " + is.get(code));
            }
            if (ad.containsKey(code)) {
                System.out.println(code + " - Assembler Directive Code: " + ad.get(code));
            }
        }
        for(String code:op1){
            if(reg.containsKey(code)){
                System.out.println(code + " - Register Code: " + reg.get(code));
            }
        }

    }
}