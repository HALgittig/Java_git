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
                // ビルド成功判定
                if (line.contains("Tundra build success") || line.contains("ExitCode: 0")) {
                    buildSucceeded = true;
                }

                // エラー検出
                if (line.toLowerCase().contains("error")
                        || line.toLowerCase().contains("exception")
                        || line.toLowerCase().contains("failed")) {
                    errorLines.add(line);
                }
            }
        }
    }

    public void writeSummary(File outputFile) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
            pw.println("📦 Jenkins Unity Build Log Summary\n");

            pw.println(buildSucceeded ? "✅ ビルド結果: 成功\n" : "❌ ビルド結果: 失敗\n");

            if (errorLines.isEmpty()) {
                pw.println("❗ エラーなし");
            } else {
                pw.println("❌ エラー情報（" + errorLines.size() + "件）");
                for (String err : errorLines) {
                    pw.println("- " + err);
                }
            }
        }
    }
}

