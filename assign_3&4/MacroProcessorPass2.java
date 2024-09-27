// import java.io.*;
// import java.util.*;

// public class MacroProcessorPass2 {

//     public static void main(String[] args) {
//         HashMap<Integer, String> mdt = new HashMap<>();
//         HashMap<String, String> newAla = new HashMap<>();
//         MacroNameTable mnt = new MacroNameTable();

//         // Load MDT and MNT from Tables.txt
//         loadTables(mdt, mnt);

//         // Second pass: Expand macros
//         try {
//             BufferedReader intermediateReader = new BufferedReader(new FileReader("E://vscode//Assignments//SSCD//assign_3//intermediate.txt"));
//             BufferedWriter outputWriter = new BufferedWriter(new FileWriter("E://vscode//Assignments//SSCD//assign_3//output.txt"));
//             String line = intermediateReader.readLine();

//             while (line != null && !line.contains("END")) {
//                 String[] keywords = line.split(" ");
//                 if (keywords.length > 0 && mnt.mntNameList.contains(keywords[0])) {
//                     newAla = appendNewAla(keywords, newAla);
//                     int mdtIndexMacro = mnt.mdtIndexList.get(mnt.mntNameList.indexOf(keywords[0])) + 1;
//                     while (!mdt.get(mdtIndexMacro).equals("MEND")) {
//                         StringBuilder argument = new StringBuilder(mdt.get(mdtIndexMacro));
//                         while (argument.toString().contains("#")) {
//                             int argPos = argument.toString().indexOf('#');
//                             String key = argument.substring(argPos, argPos + 2);
//                             String replacement = newAla.get(key);
//                             if (replacement != null) {
//                                 argument.replace(argPos, argPos + 2, replacement);
//                             } else {
//                                 break;
//                             }
//                         }
//                         outputWriter.write(argument.toString());
//                         outputWriter.newLine();
//                         mdtIndexMacro++;
//                     }
//                 } else {
//                     outputWriter.write(line);
//                     outputWriter.newLine();
//                 }
//                 line = intermediateReader.readLine();
//             }

//             intermediateReader.close();
//             outputWriter.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//         writeNewAla(newAla);
//     }

//     // Method to write the new ALA to the file
//     public static void writeNewAla(HashMap<String, String> newAla) {
//         try (BufferedWriter writer = new BufferedWriter(new FileWriter("E://vscode//Assignments//SSCD//assign_3//Tables.txt", true))) {
//             writer.write("New ALA:");
//             writer.newLine();
//             writer.write("Index\tActual Argument");
//             writer.newLine();
//             for (Map.Entry<String, String> entry : newAla.entrySet()) {
//                 writer.write(entry.getKey() + "\t" + entry.getValue());
//                 writer.newLine();
//             }
//             System.out.println("New ALA written successfully: Tables.txt");
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     // Helper method to append new arguments to the new ALA
//     public static HashMap<String, String> appendNewAla(String[] keywords, HashMap<String, String> newAla) {
//         for (int i = 1; i < keywords.length; i++) {
//             newAla.put("#" + (newAla.size() + 1), keywords[i]);
//         }
//         return newAla;
//     }

//     // Method to load MDT and MNT from Tables.txt
//     public static void loadTables(HashMap<Integer, String> mdt, MacroNameTable mnt) {
//         try (BufferedReader reader = new BufferedReader(new FileReader("E://vscode//Assignments//SSCD//assign_3//Tables.txt"))) {
//             String line = reader.readLine();
//             while (line != null) {
//                 if (line.contains("MDT:")) {
//                     reader.readLine(); 
//                     while (!(line = reader.readLine()).contains("ALA:")) {
//                         String[] split = line.split("\t");
//                         mdt.put(Integer.parseInt(split[0]), split[1]);
//                     }
//                 } else if (line.contains("Macro Name Table:")) {
//                     reader.readLine(); 
//                     while ((line = reader.readLine()) != null && !line.contains("New ALA:")) {
//                         String[] split = line.trim().split("\\s+");
//                         mnt.add(split[1], Integer.parseInt(split[2]));
//                     }
//                 }
//                 line = reader.readLine();
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     // Macro Name Table class
//     public static class MacroNameTable {
//         private List<Integer> mntIndexList = new ArrayList<>();
//         private List<String> mntNameList = new ArrayList<>();
//         private List<Integer> mdtIndexList = new ArrayList<>();
//         private int mntIndex = 1;

//         public void add(String name, int mdtIndex) {
//             mntIndexList.add(mntIndex);
//             mntNameList.add(name);
//             mdtIndexList.add(mdtIndex);
//             mntIndex++;
//         }
//     }
// }

import java.io.*;
import java.util.*;

public class MacroProcessorPass2 {

    public static void main(String[] args) {
        HashMap<Integer, String> mdt = new HashMap<>();
        HashMap<String, String> ala = new HashMap<>();
        MacroNameTable mnt = new MacroNameTable();

        // Load MDT and MNT from Tables.txt
        loadTables(mdt, mnt);

        // Expand macros
        processIntermediateFile(mdt, mnt, ala);

        // Write the new ALA to Tables.txt
        writeAlaToFile(ala);
    }

    // Method to process intermediate file and expand macros
    public static void processIntermediateFile(HashMap<Integer, String> mdt, MacroNameTable mnt, HashMap<String, String> ala) {
        try (BufferedReader reader = new BufferedReader(new FileReader("E://vscode//Assignments//SSCD//assign_3//intermediate.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("E://vscode//Assignments//SSCD//assign_3//output.txt"))) {

            String line;
            while ((line = reader.readLine()) != null && !line.contains("END")) {
                String[] parts = line.split(" ");

                if (parts.length > 0 && mnt.mntNameList.contains(parts[0])) {
                    populateAla(parts, ala);
                    expandMacro(mdt, mnt.mdtIndexList.get(mnt.mntNameList.indexOf(parts[0])), ala, writer);
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to expand macros and write to output file
    public static void expandMacro(HashMap<Integer, String> mdt, int mdtIndex, HashMap<String, String> ala, BufferedWriter writer) throws IOException {
        while (!mdt.get(mdtIndex).equals("MEND")) {
            String expandedLine = mdt.get(mdtIndex);
            for (Map.Entry<String, String> entry : ala.entrySet()) {
                expandedLine = expandedLine.replace(entry.getKey(), entry.getValue());
            }
            writer.write(expandedLine);
            writer.newLine();
            mdtIndex++;
        }
    }

    // Method to populate ALA with arguments
    public static void populateAla(String[] parts, HashMap<String, String> ala) {
        ala.clear();
        for (int i = 1; i < parts.length; i++) {
            ala.put("#" + i, parts[i]);
        }
    }

    // Method to write ALA to file
    public static void writeAlaToFile(HashMap<String, String> ala) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("E://vscode//Assignments//SSCD//assign_3//Tables.txt", true))) {
            writer.write("New ALA:");
            writer.newLine();
            writer.write("Index\tActual Argument");
            writer.newLine();
            for (Map.Entry<String, String> entry : ala.entrySet()) {
                writer.write(entry.getKey() + "\t" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load MDT and MNT from Tables.txt
    public static void loadTables(HashMap<Integer, String> mdt, MacroNameTable mnt) {
        try (BufferedReader reader = new BufferedReader(new FileReader("E://vscode//Assignments//SSCD//assign_3//Tables.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("MDT:")) {
                    while (!(line = reader.readLine()).contains("ALA:")) {
                        String[] parts = line.split("\t");
                        mdt.put(Integer.parseInt(parts[0]), parts[1]);
                    }
                } else if (line.contains("Macro Name Table:")) {
                    while ((line = reader.readLine()) != null && !line.contains("New ALA:")) {
                        String[] parts = line.trim().split("\\s+");
                        mnt.add(parts[1], Integer.parseInt(parts[2]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Original Macro Name Table class as requested
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
    }
}

