package LogAnalysis_Jenkins;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class JenkinsLogAnalyzer {

    private boolean buildSucceeded = false;
    private List<String> errorLines = new ArrayList<>();

    public void analyzeLog(File logFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("Tundra build success") || line.contains("ExitCode: 0") || line.contains("BUILD SUCCESSFUL")) {
                    buildSucceeded = true;
                }
                if (line.toLowerCase().contains("error") || line.toLowerCase().contains("exception") || line.toLowerCase().contains("failed") || line.toLowerCase().contains("failure")) {
                    errorLines.add(line);
                }
            }
        }
    }

    public void writeSummary(File outputFile) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
            pw.println(" java_work Build Log Summary\n");

            pw.println(buildSucceeded ? " Result: Success\n" : " Result: Failure\n");

            if (errorLines.isEmpty()) {
                pw.println("No errors");
            } else {
                pw.println(" Errors Information : case"+ errorLines.size());
                for (String err : errorLines) {
                    pw.println(" - " + err);
                }
            }
        }
    }
}

