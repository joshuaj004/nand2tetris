import java.io.*;
import java.util.*;
public class assembler {
    
    public static void main(String[] args) throws FileNotFoundException {
        System.out.print("Enter the name of your file (without.asm): ");
        Scanner k = new Scanner(System.in);
        String filename = k.nextLine();
        Map<String, String> symbolMap = new TreeMap<String, String>();
        initialMap(symbolMap);
        File file = new File(filename + ".asm");
        Scanner fileScanner = new Scanner(file);
        PrintStream output = new PrintStream(new File(filename +".hack"));
        labelMaker(fileScanner, symbolMap, 16);
        fileScanner.close();
        System.out.println(symbolMap.toString());
        Scanner fileScanner1 = new Scanner(file);
        secondPass(fileScanner1, symbolMap, output);
    }
    
    public static void initialMap(Map symbolMap) {
        symbolMap.put("Null", "000");
        symbolMap.put("M", "001");
        symbolMap.put("D", "010");
        symbolMap.put("MD", "011");
        symbolMap.put("A", "100");
        symbolMap.put("AM", "101");
        symbolMap.put("AD", "110");
        symbolMap.put("AMD", "111");
        
        symbolMap.put("JGT", "001");
        symbolMap.put("JEQ", "010");
        symbolMap.put("JGE", "011");
        symbolMap.put("JLT", "100");
        symbolMap.put("JNE", "101");
        symbolMap.put("JLE", "110");
        symbolMap.put("JMP", "111");
        
        symbolMap.put("0",      "101010");
        symbolMap.put("1",      "111111");
        symbolMap.put("-1",     "111010");
        symbolMap.put("DReg",   "001100");
        symbolMap.put("AReg",   "110000");
        symbolMap.put("NotD",   "001101");
        symbolMap.put("NotA",   "110001");
        symbolMap.put("-D",     "001111");
        symbolMap.put("-A",     "110011");
        symbolMap.put("D++",    "011111");
        symbolMap.put("A++",    "110111");
        symbolMap.put("D--",    "001110");
        symbolMap.put("A--",    "110010");
        symbolMap.put("D+A",    "000010");
        symbolMap.put("D-A",    "010011");
        symbolMap.put("A-D",    "000111");
        symbolMap.put("D&A",    "000000");
        symbolMap.put("D|A",    "010101");
    }
    
    public static void labelMaker(Scanner fs, Map symbolMap, int number) {
        while (fs.hasNextLine()) {
            Scanner line = new Scanner(fs.nextLine());
            if (line.hasNextLine()) {
                String temp = line.next();
                if (!temp.equals("//")) {
                    System.out.println(temp);
                    if (!symbolMap.containsKey(temp) && (temp.charAt(0) == '@')) {
                        if (!temp.matches("@R[0-9]+")){
                            System.out.println(temp);
                            if (temp.matches("@[0-9]+")) {
                                symbolMap.put(temp, "" + temp.substring(1));
                            } else {
                                if (!symbolMap.containsKey(temp)) {
                                    symbolMap.put(temp, "" + number);
                                    number++;
                                    ;
                                }
                            }
                        } else {
                            symbolMap.put(temp, "" + temp.substring(2));
                        }
                    }
                }                
            }
        }
    }
    
    public static void secondPass(Scanner fs, Map symbolMap, PrintStream output) {
        String a="0"; String dest="000"; String comp="000000"; String jump="000";
        while (fs.hasNextLine()) {
            Scanner line = new Scanner(fs.nextLine());
            if (line.hasNextLine()) {
                String temp = line.next();
                if (!temp.equals("//")) {
                    if (temp.charAt(0) == '@' ) {
                        String numberString;
                        if (temp.equals("@SCREEN")) {
                            numberString = "0100000000000000";
                        } else if (temp.equals("KBD")) {
                            numberString = "0110000000000000";
                        } else {
                            numberString = "000000000000000" + Integer.toBinaryString(Integer.valueOf("" + symbolMap.get(temp)));
                        }
                        while (numberString.length() != 16) {
                            numberString = numberString.substring(1);
                        }
                        output.println(numberString);
                        
                    } else if (temp.charAt(0) != '(') {
                        String base = "111";
                        //String a; String dest; String comp; String jump;
                        if (!temp.contains("=")) {
                            dest = "000";
                        } else {
                            dest = "" + symbolMap.get(temp.substring(0, temp.indexOf("=")));
                            temp = temp.substring(temp.indexOf("=") + 1);
                        }
                        
                        if (!temp.contains(";")) {
                            jump = "000";
                        } else {
                            jump = "" + symbolMap.get(temp.substring(temp.indexOf(";")+1));
                            temp = temp.substring(0, temp.indexOf(";"));
                        }
                        
                        switch (temp) {
                            case "0":   a = "" + 0; comp = "" + symbolMap.get("0"); break;
                            case "1":   a = "" + 0; comp = "" + symbolMap.get("1"); break;
                            case "-1":  a = "" + 0; comp = "" + symbolMap.get("-1"); break;
                            case "D":   a = "" + 0; comp = "" + symbolMap.get("DReg"); break;
                            case "A":   a = "" + 0; comp = "" + symbolMap.get("AReg"); break;
                            case "M":   a = "" + 1; comp = "" + symbolMap.get("AReg"); break;
                            case "!D":  a = "" + 0; comp = "" + symbolMap.get("NotD"); break;
                            case "!A":  a = "" + 0; comp = "" + symbolMap.get("NotA"); break;
                            case "!M":  a = "" + 1; comp = "" + symbolMap.get("NotA"); break;
                            case "-D":  a = "" + 0; comp = "" + symbolMap.get("-D"); break;
                            case "-A":  a = "" + 0; comp = "" + symbolMap.get("-A"); break;
                            case "-M":  a = "" + 1; comp = "" + symbolMap.get("-A"); break;
                            case "D+1": a = "" + 0; comp = "" + symbolMap.get("D++"); break;
                            case "A+1": a = "" + 0; comp = "" + symbolMap.get("A++"); break;
                            case "M+1": a = "" + 1; comp = "" + symbolMap.get("A++"); break;
                            case "D-1": a = "" + 0; comp = "" + symbolMap.get("D--"); break;
                            case "A-1": a = "" + 0; comp = "" + symbolMap.get("A--"); break;
                            case "M-1": a = "" + 1; comp = "" + symbolMap.get("A--"); break;
                            case "D+A": a = "" + 0; comp = "" + symbolMap.get("D+A"); break;
                            case "D+M": a = "" + 1; comp = "" + symbolMap.get("D+A"); break;
                            case "D-A": a = "" + 0; comp = "" + symbolMap.get("D-A"); break;
                            case "D-M": a = "" + 1; comp = "" + symbolMap.get("D-A"); break;
                            case "A-D": a = "" + 0; comp = "" + symbolMap.get("A-D"); break;
                            case "M-D": a = "" + 1; comp = "" + symbolMap.get("A-D"); break;
                            case "D&A": a = "" + 0; comp = "" + symbolMap.get("D&A"); break;
                            case "D&M": a = "" + 1; comp = "" + symbolMap.get("D&A"); break;
                            case "D|A": a = "" + 0; comp = "" + symbolMap.get("D|A"); break;
                            case "D|M": a = "" + 1; comp = "" + symbolMap.get("D|A"); break;
                            default: a = "" + 0; comp = "000000"; break;
                        }
                        
                        output.println(base + a + comp + dest + jump);
                    }
                }
            }    
        }
    }
}