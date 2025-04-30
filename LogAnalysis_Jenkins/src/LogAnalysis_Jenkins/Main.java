package LogAnalysis_Jenkins;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
    	
        File buildsRoot = new File(("C:\\ProgramData\\Jenkins\\.jenkins\\jobs\\java_work\\builds"));

        File latestBuild = getLatestBuildFolder(buildsRoot);
        if (latestBuild == null) {
            System.err.println(" Folder not found");
            return;
        }

        System.out.println("Folder: " + latestBuild.getAbsolutePath());

        File[] logFiles = latestBuild.listFiles(file -> {
            String name = file.getName();
            return file.isFile() && (name.endsWith(".txt") || name.endsWith(".log") || !name.contains("."));
        });

        if (logFiles == null || logFiles.length == 0) {
            System.out.println(" File not found");
            return;
        }
        boolean summaryExists = Arrays.stream(logFiles).anyMatch(log -> new File(latestBuild, "summary_" + log.getName() + ".txt").exists());
        if (summaryExists) {
        	System.out.println("Summary already exists");
        	return;
        }

        String webhookUrl = "https://discord.com/api/webhooks/1361910822225449070/gqw9y635H_jCcX4RhVyjj8iwMlRnIirqrr8TnHiR5K_Q2rIeJYePzjNv3QuF9EUPYUhl";

        for (File logFile : logFiles) {
            System.out.println(" Analyzing: " + logFile.getName());

            File outputFile = new File(latestBuild, "summary_" + logFile.getName() + ".txt");

            File actualLogFile = logFile;
            boolean isTemp = false;
            if (!logFile.getName().contains(".")) {
            	System.out.println(logFile.getName());
                actualLogFile = new File(logFile.getAbsolutePath() + ".tmp.txt");
                try {
                    Files.copy(logFile.toPath(), actualLogFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    isTemp = true;
                } catch (IOException e) {
                    System.err.println(" File copy failure : " + e.getMessage());
                    continue;
                }
            }

            JenkinsLogAnalyzer analyzer = new JenkinsLogAnalyzer();
            try {
                analyzer.analyzeLog(actualLogFile);
                analyzer.writeSummary(outputFile);
                DiscordWebhookSender.sendFileToDiscord(webhookUrl, outputFile);
                System.out.println(" Send" + outputFile.getName());
            } catch (IOException e) {
                System.err.println(" error : " + e.getMessage());
            }

            if (isTemp) { actualLogFile.delete(); }
        }
        
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(latestBuild);
                System.out.println(" Opened the folder : " + latestBuild.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println(" Could not open the folder: " + e.getMessage());
        }
    }

    public static File getLatestBuildFolder(File buildsDir) {
        File[] subdirs = buildsDir.listFiles(File::isDirectory);
        if (subdirs == null || subdirs.length == 0) return null;

        return Arrays.stream(subdirs)
                .filter(f -> f.getName().matches("\\d+"))
                .map(f -> new Object[]{f, Integer.parseInt(f.getName())})
                .max((a, b) -> Integer.compare((int) a[1], (int) b[1]))
                .map(a -> (File) a[0])
                .orElse(null);
    }
}
