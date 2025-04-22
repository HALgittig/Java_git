package LogAnalysis_Jenkins;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File logFile = new File("#23.txt");
        File outputFile = new File("summary.txt");

        String webhookUrl = "https://discord.com/api/webhooks/https://discord.com/api/webhooks/1361910822225449070/gqw9y635H_jCcX4RhVyjj8iwMlRnIirqrr8TnHiR5K_Q2rIeJYePzjNv3QuF9EUPYUhl";

        JenkinsLogAnalyzer analyzer = new JenkinsLogAnalyzer();
        try {
            analyzer.analyzeLog(logFile);
            analyzer.writeSummary(outputFile);
            DiscordWebhookSender.sendFileToDiscord(webhookUrl, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ エラーが発生しました: " + e.getMessage());
        }
    }
}
