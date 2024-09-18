import java.io.*;
import java.util.*;

public class MacroProcessorPass1 {

    public static void main(String[] args) {
        HashMap<Integer, String> mdt = new HashMap<>();
        LinkedHashMap<String, String> ala = new LinkedHashMap<>();
        int mdtIndex = 1;
        int alaIndex = 1;
        MacroNameTable mnt = new MacroNameTable();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("E://vscode//Assignments//SSCD//assign_3//code.txt"));
            String line = reader.readLine();
            String fileName = "E://vscode//Assignments//SSCD//assign_3//intermediate.txt";

            while (line != null) {
                if (line.equals("START")) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                        do {
                            writer.write(line);
                            writer.newLine();
                            line = reader.readLine();
                        } while (line != null);
                        System.out.println("File written successfully: " + fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (line.equals("MACRO")) {
                    line = reader.readLine();
                    if (line != null) {
                        String[] splitStr = line.split("\\s+");
                        mdt.put(mdtIndex, line);
                        mnt.add(splitStr[0], mdtIndex);
                        mdtIndex++;
                        int a = 1;
                        while (a < splitStr.length) {
                            String temp = "#" + alaIndex;
                            ala.put(temp, splitStr[a]);
                            a++;
                            alaIndex++;
                        }
                        line = reader.readLine();
                        while (line != null && !line.equals("MEND")) {
                            line = replaceDummiesWithIndices(line, ala);
                            mdt.put(mdtIndex, line);
                            mdtIndex++;
                            line = reader.readLine();
                        }
                        if (line != null && line.equals("MEND")) {
                            mdt.put(mdtIndex, line);
                            mdtIndex++;
                            line = reader.readLine();
                        }
                    }
                } else {
                    line = reader.readLine();
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write MDT, ALA, and MNT tables to Tables.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("E://vscode//Assignments//SSCD//assign_3//Tables.txt"))) {
            writer.write("MDT:");
            writer.newLine();
            writer.write("Index\tInstruction");
            writer.newLine();
            for (Map.Entry<Integer, String> entry : mdt.entrySet()) {
                writer.write(entry.getKey() + "\t" + entry.getValue());
                writer.newLine();
            }

            writer.write("ALA:");
            writer.newLine();
            writer.write("Index\tDummyArgument");
            writer.newLine();
            for (Map.Entry<String, String> entry : ala.entrySet()) {
                writer.write(entry.getKey() + "\t" + entry.getValue());
                writer.newLine();
            }

            writer.write("Macro Name Table:");
            writer.newLine();
            mnt.displayTable(writer);
            System.out.println("Tables written successfully: Tables.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to replace dummy arguments with ALA indices
    public static String replaceDummiesWithIndices(String line, LinkedHashMap<String, String> ala) {
        for (Map.Entry<String, String> entry : ala.entrySet()) {
            String index = entry.getKey();
            String value = entry.getValue();
            line = line.replace(value, index);
        }
        return line;
    }

    // Macro Name Table class
    public static class MacroNameTable {
        private List<Integer> mntIndexList = new ArrayList<>();
        private List<String> mntNameList = new ArrayList<>();
        private List<Integer> mdtIndexList = new ArrayList<>();
        private int mntIndex = 1;

        public void add(String name, int mdtIndex) {
            mntIndexList.add(mntIndex);
            mntNameList.add(name);
            mdtIndexList.add(mdtIndex);
            mntIndex++;
        }

        public void displayTable(BufferedWriter writer) throws IOException {
            writer.write(String.format("%-10s %-15s %-10s%n", "MNT Index", "MNT Name", "MDT Index"));
            for (int i = 0; i < mntIndexList.size(); i++) {
                writer.write(String.format("%-10d %-15s %-10d%n", mntIndexList.get(i), mntNameList.get(i), mdtIndexList.get(i)));
            }
        }
    }
}
