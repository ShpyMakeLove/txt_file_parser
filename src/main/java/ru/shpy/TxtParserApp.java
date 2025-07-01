package ru.shpy;

import org.fusesource.jansi.Ansi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class TxtParserApp {
    public static void main(String[] args) {
        List<String> argsList = Arrays.asList(args);

        String outputPath = null;
        String customBaseName = null;
        boolean changeAppend = false;
        boolean showStats = false;
        boolean showFullStats = false;

        List<String> inputFileNames = new ArrayList<>();

        for (int i = 0; i < argsList.size(); i++) {

            String arg = argsList.get(i);

            if (!arg.startsWith("-")) {
                inputFileNames.add(arg);
            }

            switch (arg) {
                case "-o":
                    if (i + 1 < argsList.size()) {
                        outputPath = argsList.get(i + 1);
                    }
                    break;
                case "-p":
                    if (i + 1 < argsList.size()) {
                        customBaseName = argsList.get(i + 1);
                    }
                    break;
                case "-a":
                    changeAppend = true;
                    break;
                case "-s":
                    showStats = true;
                    break;
                case "-f":
                    showFullStats = true;
                    break;
                default:
                    break;
            }
        }

        if (inputFileNames.isEmpty()) {
            System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("FILES NOT PASSED IN ARGUMENTS"));
            System.exit(1);
        } else {

            File[] inputFiles = inputFileNames.stream()
                    .map(File::new)
                    .toArray(File[]::new);

            Map<String, Object> parsedData = readFiles(inputFiles);

            String prefix = (customBaseName != null) ? customBaseName : "";

            String fileStr = prefix + "strings.txt";
            String fileDec = prefix + "floats.txt";
            String fileInt = prefix + "integers.txt";

            writeToFile((List<?>) parsedData.get("strings"), outputPath, fileStr, changeAppend);
            writeToFile((List<?>) parsedData.get("bigDecimals"), outputPath, fileDec, changeAppend);
            writeToFile((List<?>) parsedData.get("bigIntegers"), outputPath, fileInt, changeAppend);

            if (showStats) {
                System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("ALL INTEGERS COUNT (" + ((List<?>) parsedData.get("bigIntegers")).size() + ")"));
                System.out.println(Ansi.ansi().fg(Ansi.Color.CYAN).a("ALL FLOATS COUNT (" + ((List<?>) parsedData.get("bigDecimals")).size() + ")"));
                System.out.println(Ansi.ansi().fg(Ansi.Color.MAGENTA).a("ALL STRINGS COUNT (" + ((List<?>) parsedData.get("strings")).size() + ")"));
            }

            if (showFullStats) {

                System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a("ALL INTEGERS COUNT (" + ((List<?>) parsedData.get("bigIntegers")).size() + ")"));
                System.out.println("MIN INTEGER: " + parsedData.get("minBigInt"));
                System.out.println("MAX INTEGER: " + parsedData.get("maxBigInt"));
                System.out.println("AVG INTEGER: " + parsedData.get("avgBigInt"));
                System.out.println("SUM INTEGER: " + parsedData.get("sumBigInt"));

                System.out.println(Ansi.ansi().fg(Ansi.Color.CYAN).a("ALL FLOATS COUNT (" + ((List<?>) parsedData.get("bigDecimals")).size() + ")"));
                System.out.println("MIN FLOAT: " + parsedData.get("minBigDec"));
                System.out.println("MAX FLOAT: " + parsedData.get("maxBigDec"));
                System.out.println("AVG FLOAT: " + parsedData.get("avgBigDec"));
                System.out.println("SUM FLOAT: " + parsedData.get("sumBigDec"));

                System.out.println(Ansi.ansi().fg(Ansi.Color.MAGENTA).a("ALL STRINGS COUNT (" + ((List<?>) parsedData.get("strings")).size() + ")"));
                System.out.println("THE SHORTEST STRING: " + parsedData.get("shortestStr"));
                System.out.println("THE LONGEST STRING: " + parsedData.get("longestStr"));
            }
        }
    }

    public static Map<String, Object> readFiles(File... files) {

        List<String> strings = new ArrayList<>();
        List<BigInteger> bigIntegers = new ArrayList<>();
        List<BigDecimal> bigDecimals = new ArrayList<>();

        BigInteger minBigInt = null;
        BigInteger maxBigInt = null;
        BigInteger avgBigInt = null;
        BigInteger sumBigInt = BigInteger.ZERO;

        BigDecimal minBigDec = null;
        BigDecimal maxBigDec = null;
        BigDecimal avgBigDec = null;
        BigDecimal sumBigDec = BigDecimal.ZERO;

        String shortestStr = null;
        String longestStr = null;

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).a("BEGIN TO READ FILE: " + file.getName()));
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    try {
                        BigInteger bigInt = new BigInteger(line);
                        bigIntegers.add(bigInt);
                        if (minBigInt == null || bigInt.compareTo(minBigInt) < 0)
                            minBigInt = bigInt;

                        if (maxBigInt == null || bigInt.compareTo(maxBigInt) > 0)
                            maxBigInt = bigInt;

                        sumBigInt = sumBigInt.add(bigInt);
                        continue;
                    } catch (NumberFormatException ignored) {}

                    if (!bigIntegers.isEmpty()) {
                        avgBigInt = sumBigInt.divide(BigInteger.valueOf(bigIntegers.size()));
                    }


                    try {
                        BigDecimal bigDec = new BigDecimal(line);
                        bigDecimals.add(bigDec);
                        if (minBigDec == null || bigDec.compareTo(minBigDec) < 0)
                            minBigDec = bigDec;

                        if (maxBigDec == null || bigDec.compareTo(maxBigDec) > 0)
                            maxBigDec = bigDec;

                        sumBigDec = sumBigDec.add(bigDec);
                        continue;
                    } catch (NumberFormatException ignored) {}

                    if (!bigDecimals.isEmpty()) {
                        avgBigDec = sumBigDec.divide(BigDecimal.valueOf(bigDecimals.size()), 50, BigDecimal.ROUND_HALF_UP);
                    }



                    strings.add(line);
                    if (shortestStr == null || line.length() < shortestStr.length())
                        shortestStr = line;

                    if (longestStr == null || line.length() > longestStr.length())
                        longestStr = line;
                }

                System.out.println(Ansi.ansi().fg(Ansi.Color.WHITE).a("END OF FILE READ: " + file.getName()));
            } catch (IOException e) {
                System.out.println(Ansi.ansi().fg(Ansi.Color.RED).a("ERROR READING FILE " + file.getName() + ": " + e.getMessage()));
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("strings", strings);
        result.put("bigIntegers", bigIntegers);
        result.put("bigDecimals", bigDecimals);
        result.put("minBigInt", minBigInt);
        result.put("maxBigInt", maxBigInt);
        result.put("minBigDec", minBigDec);
        result.put("maxBigDec", maxBigDec);
        result.put("avgBigDec", avgBigDec);
        result.put("avgBigInt", avgBigInt);
        result.put("sumBigDec", sumBigDec);
        result.put("sumBigInt", sumBigInt);
        result.put("shortestStr", shortestStr);
        result.put("longestStr", longestStr);
        return result;
    }

    public static <T> void writeToFile(List<T> list, String outputPath, String fileName, boolean append) {
        if (list == null || list.isEmpty()) {
            System.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).a("LIST IS EMPTY - FILE " + fileName + " WILL NOT BE CREATED."));
            return;
        }

        try {
            File dir = new File(outputPath);
            if (!dir.exists()) dir.mkdirs();

            List<String> lines = list.stream()
                    .map(Object::toString)
                    .toList();

            Path filePath = Path.of(outputPath, fileName);

            if (append) {
                Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } else {
                Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }

            System.out.println(Ansi.ansi().fg(Ansi.Color.GREEN).a(fileName + " SUCCESSFULLY CREATED AT " + outputPath));
        } catch (IOException e) {
            System.err.println(Ansi.ansi().fg(Ansi.Color.RED).a("ERROR WRITING TO FILE " + fileName + ": " + e.getMessage()));
        }

    }
}