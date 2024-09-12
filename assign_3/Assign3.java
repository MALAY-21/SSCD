//MACRO pass-1 assignment
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Assign3 {

    // Macro Name Table (MNT)
    static ArrayList<String> MNT = new ArrayList<>();

    // Macro Definition Table (MDT)
    static ArrayList<String> MDT = new ArrayList<>();

    // Argument List Array (ALA)
    static HashMap<String, Integer> ALA = new HashMap<>();

    // Method to define a macro
    public static void defineMacro(ArrayList<String> macroDefinition) {
        if (macroDefinition.isEmpty()) {
            System.out.println("No macro definition found.");
            return;
        }
        if (!macroDefinition.get(0).trim().equals("MACRO") || !macroDefinition.get(macroDefinition.size() - 1).trim().equals("MEND")) {
            System.out.println("Invalid macro format. Macro should start with 'MACRO' and end with 'MEND'.");
            return;
        }
        String[] macroLine = macroDefinition.get(1).trim().split("\\s+");
        String macroName = macroLine[0]; 

        // Add macro name to MNT
        MNT.add(macroName);

        // Parse arguments 
        if (macroLine.length > 1) {
            String[] arguments = macroLine[1].split(",");
            for (int i = 0; i < arguments.length; i++) {
                String arg = arguments[i].trim();
                if (!arg.startsWith("&")) {
                    arg = "&" + arg; 
                }
                ALA.put(arg, i + 1); 
            }
        }

        // Store only the macro body (excluding "MACRO" and "MEND") in MDT
        for (int i = 2; i < macroDefinition.size() - 1; i++) {
            MDT.add(macroDefinition.get(i).trim());
        }
    }

    // Method to display the Macro Name Table (MNT)
    public static void displayMNT() {
        System.out.println("\nMacro Name Table (MNT):");
        for (int i = 0; i < MNT.size(); i++) {
            System.out.println((i + 1) + "\t" + MNT.get(i));
        }
    }

    // Method to display the Macro Definition Table (MDT)
    public static void displayMDT() {
        System.out.println("\nMacro Definition Table (MDT):");
        for (int i = 0; i < MDT.size(); i++) {
            System.out.println((i + 1) + "\t" + MDT.get(i));
        }
    }

    // Method to display the Argument List Array (ALA)
    public static void displayALA() {
        System.out.println("\nArgument List Array (ALA):");
        ALA.forEach((key, value) -> System.out.println(key + " -> #" + value));
    }

    // Method to read a file, extract macro, and write remaining code to output.txt
    public static void readFileAndProcess(String fileName, String outputFileName) {
        ArrayList<String> macroDefinition = new ArrayList<>();
        ArrayList<String> nonMacroCode = new ArrayList<>();
        boolean isMacro = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                // Start of macro
                if (line.equals("MACRO")) {
                    isMacro = true;
                    macroDefinition.add(line);
                }
                // End of macro
                else if (line.equals("MEND")) {
                    macroDefinition.add(line);
                    defineMacro(macroDefinition); // Define the macro after reaching MEND
                    isMacro = false;
                    macroDefinition.clear(); // Reset for next macro (if any)
                }
                // Inside macro
                else if (isMacro) {
                    macroDefinition.add(line);
                }
                // Non-macro part
                else {
                    nonMacroCode.add(line); 
                }
            }

            //output.txt
            for (String codeLine : nonMacroCode) {
                bw.write(codeLine);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error reading or writing files: " + e.getMessage());
        }
    }

    // Main method
    public static void main(String[] args) {
        String inputFileName = "E:\\vscode\\Assignments\\SSCD\\assign_3\\input.txt";  
        String outputFileName = "E:\\vscode\\Assignments\\SSCD\\assign_3\\output.txt"; 

        // Process file and store non-macro code in output.txt
        readFileAndProcess(inputFileName, outputFileName);

        // Display MNT, MDT, and ALA
        displayMNT();
        displayMDT();
        displayALA();

        System.out.println("\nNon-macro code has been written to " + outputFileName);
    }
}
