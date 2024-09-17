// import java.io.*;
// import java.util.*;

// public class macro {
//     public static void main(String[] args) {
//         HashMap<Integer, String> mdt = new HashMap<>();
//         LinkedHashMap<String, String> ala = new LinkedHashMap<>();
//         int mdtIndex = 1;
//         int alaIndex = 1;
//         MacroNameTable mnt = new MacroNameTable();
//         try {
//             BufferedReader reader = new BufferedReader(new FileReader("E://vscode//Assignments//SSCD//assign_3//code.txt"));
//             String line = reader.readLine();
//             String fileName = "intermediate.txt";
//             while (line != null) {
//                 if (line.equals("START")) {
//                     try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//                         do {
//                             writer.write(line);
//                             writer.newLine(); 
//                             line = reader.readLine();
//                         } while (line != null);
//                         System.out.println("File written successfully: " + fileName);
//                     } catch (IOException e) {
//                         e.printStackTrace();
//                     }
//                 } else if (line.equals("MACRO")) {
//                     line = reader.readLine();
//                     if (line != null) {
//                         String[] splitStr = line.split("\\s+");
//                         mdt.put(mdtIndex, line);
//                         mnt.add(splitStr[0], mdtIndex);
//                         mdtIndex++;
//                         int a = 1;
//                         while (a < splitStr.length) {
//                             String temp = "#" + alaIndex;
//                             ala.put(temp, splitStr[a]);
//                             a++;
//                             alaIndex++;
//                         }
//                         line = reader.readLine();
//                         while (line != null && !line.equals("MEND")) {
//                             line = replaceDummiesWithIndices(line, ala);
//                             mdt.put(mdtIndex, line);
//                             mdtIndex++;
//                             line = reader.readLine();
//                         }
//                         if (line != null && line.equals("MEND")) {
//                             mdt.put(mdtIndex, line);
//                             mdtIndex++;
//                             line = reader.readLine(); 
//                         }
//                     }
//                 } else {
//                     line = reader.readLine();
//                 }
//             }
//             reader.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         try (BufferedWriter writer = new BufferedWriter(new FileWriter("Tables.txt"))) {
//             writer.write("MDT:");
//             writer.newLine();
//             writer.write("Index\tInstruction");
//             writer.newLine();
//             for (Map.Entry<Integer, String> entry : mdt.entrySet()) {
//                 writer.write(entry.getKey() + "\t" + entry.getValue());
//                 writer.newLine();
//             }
//             writer.write("ALA:");
//             writer.newLine();
//             writer.write("Index\tDummyArgument");
//             writer.newLine();
//             for (Map.Entry<String, String> entry : ala.entrySet()) {
//                 writer.write(entry.getKey() + "\t" + entry.getValue());
//                 writer.newLine();
//             }
//             writer.write("Macro Name Table:");
//             writer.newLine();
//             mnt.displayTable(writer);
//             System.out.println("Tables written successfully: Tables.txt");
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     HashMap<String,String> newala = new HashMap<>();
//     try {
//         BufferedReader intermediateReader = new BufferedReader(new FileReader("intermediate.txt"));
//         BufferedWriter outputWriter = new BufferedWriter(new FileWriter("output.txt"));
//         String line = intermediateReader.readLine();
//         while (!line.contains("END")) {
//             String[] keywords = line.split(" ");
//             if (keywords.length > 0 && mnt.mntNameList.contains(keywords[0])) {
//                 newala = newalaappend(keywords,newala);
//                 int mdt_index = mnt.mdtIndexList.get(mnt.mntNameList.indexOf(keywords[0]))+1;
//                 while (!mdt.get(mdt_index).equals("MEND")) {
//                     StringBuilder argu = new StringBuilder(mdt.get(mdt_index));
//                     while (argu.toString().contains("#")) {
//                         int arguint = argu.toString().indexOf('#');
//                         String key = argu.substring(arguint, arguint + 2);
//                         String replacement = newala.get(key);
//                         if (replacement != null) {
//                             argu.replace(arguint, arguint + 2,replacement);
//                         } else {break;}
//                     }
//                     outputWriter.write(argu.toString());
//                     outputWriter.newLine();
//                     mdt_index++;
//                 }
//             } else {
//                 outputWriter.write(line);
//                 outputWriter.newLine();
//             }
//             line = intermediateReader.readLine();
//         }
//         intermediateReader.close();
//         outputWriter.close();
//     } catch (IOException e) {
//         e.printStackTrace();
//     }   
//     newalawrite(newala);
// }
//     public static void newalawrite(HashMap<String, String> newala){
//         try (BufferedWriter writer = new BufferedWriter(new FileWriter("Tables.txt", true))) {
//             writer.write("New ALA:");
//             writer.newLine();
//             writer.write("Index\tActuall Argument");
//             writer.newLine();
//             for (Map.Entry<String, String> entry : newala.entrySet()) {
//             writer.write(entry.getKey() + "\t" + entry.getValue());
//             writer.newLine();
//             }
//             System.out.println("New ALA written successfully: Tables.txt");
//         } catch (IOException e) {
//             e.printStackTrace();
//         }

//     }
//     public static HashMap<String, String> newalaappend(String[] keywords, HashMap<String, String> newala) {
//         for(int i = 1 ; i < keywords.length;i++){newala.put("#"+(newala.size()+1),keywords[i]);}
//         return newala;
//     }
//     public static String replaceDummiesWithIndices(String line, LinkedHashMap<String, String> ala) {
//         for (Map.Entry<String, String> entry : ala.entrySet()) {
//             String index = entry.getKey();
//             String value = entry.getValue();
//             line = line.replace(value, index);
//         }
//         return line;
//     }

//     public static <K, V> K getKeyByValue(HashMap<K, V> map, V value) {
//         for (Map.Entry<K, V> entry : map.entrySet()) {
//             if (entry.getValue().equals(value)) {
//                 return entry.getKey();
//             }
//         }
//         return null; 
//     }

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

//         public void displayTable(BufferedWriter writer) throws IOException {
//             writer.write(String.format("%-10s %-15s %-10s%n", "MNT Index", "MNT Name", "MDT Index"));
//             for (int i = 0; i < mntIndexList.size(); i++) {
//                 writer.write(String.format("%-10d %-15s %-10d%n", mntIndexList.get(i), mntNameList.get(i), mdtIndexList.get(i)));
//             }
//         }
//     }
// }
import java.io.*;
import java.util.*;

public class MacroProcessor {

    public static void main(String[] args) {
        HashMap<Integer, String> mdt = new HashMap<>();
        LinkedHashMap<String, String> ala = new LinkedHashMap<>();
        int mdtIndex = 1;
        int alaIndex = 1;
        MacroNameTable mnt = new MacroNameTable();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("E://vscode//Assignments//SSCD//assign_3//code.txt"));
            String line = reader.readLine();
            String fileName = "intermediate.txt";

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Tables.txt"))) {
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

        // Second pass: Expand macros
        HashMap<String, String> newAla = new HashMap<>();
        try {
            BufferedReader intermediateReader = new BufferedReader(new FileReader("intermediate.txt"));
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter("output.txt"));
            String line = intermediateReader.readLine();

            while (line != null && !line.contains("END")) {  // Null check added here
                String[] keywords = line.split(" ");
                if (keywords.length > 0 && mnt.mntNameList.contains(keywords[0])) {
                    newAla = appendNewAla(keywords, newAla);
                    int mdtIndexMacro = mnt.mdtIndexList.get(mnt.mntNameList.indexOf(keywords[0])) + 1;
                    while (!mdt.get(mdtIndexMacro).equals("MEND")) {
                        StringBuilder argument = new StringBuilder(mdt.get(mdtIndexMacro));
                        while (argument.toString().contains("#")) {
                            int argPos = argument.toString().indexOf('#');
                            String key = argument.substring(argPos, argPos + 2);
                            String replacement = newAla.get(key);
                            if (replacement != null) {
                                argument.replace(argPos, argPos + 2, replacement);
                            } else {
                                break;
                            }
                        }
                        outputWriter.write(argument.toString());
                        outputWriter.newLine();
                        mdtIndexMacro++;
                    }
                } else {
                    outputWriter.write(line);
                    outputWriter.newLine();
                }
                line = intermediateReader.readLine();  // Read the next line
            }

            intermediateReader.close();
            outputWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeNewAla(newAla);
    }

    // Method to write the new ALA to the file
    public static void writeNewAla(HashMap<String, String> newAla) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Tables.txt", true))) {
            writer.write("New ALA:");
            writer.newLine();
            writer.write("Index\tActual Argument");
            writer.newLine();
            for (Map.Entry<String, String> entry : newAla.entrySet()) {
                writer.write(entry.getKey() + "\t" + entry.getValue());
                writer.newLine();
            }
            System.out.println("New ALA written successfully: Tables.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to append new arguments to the new ALA
    public static HashMap<String, String> appendNewAla(String[] keywords, HashMap<String, String> newAla) {
        for (int i = 1; i < keywords.length; i++) {
            newAla.put("#" + (newAla.size() + 1), keywords[i]);
        }
        return newAla;
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
