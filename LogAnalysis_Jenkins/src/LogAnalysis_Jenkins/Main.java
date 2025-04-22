package LogAnalysis_Jenkins;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
    	
    	if (args.length < 1) {
            System.out.println("❗ ログフォルダのパスを引数で指定してください。");
            System.out.println("例: java -cp src Main C:\\logs");
            return;
        }
        // Jenkinsのbuildsフォルダ
        File buildsRoot = new File(args[0]);
        //("C:\\ProgramData\\Jenkins\\.jenkins\\jobs\\java_work\\builds");

        File latestBuild = getLatestBuildFolder(buildsRoot);
        if (latestBuild == null) {
            System.err.println("最新のビルドフォルダが見つかりません。");
            return;
        }

        System.out.println("最新のビルドフォルダ: " + latestBuild.getAbsolutePath());

        // 拡張子なしファイルも含めて処理対象にする
        File[] logFiles = latestBuild.listFiles(file -> {
            String name = file.getName();
            return file.isFile() && (name.endsWith(".txt") || name.endsWith(".log") || !name.contains("."));
        });

        if (logFiles == null || logFiles.length == 0) {
            System.out.println("ログファイルが見つかりません。");
            return;
        }

        String webhookUrl = "https://discord.com/api/webhooks/1361910822225449070/gqw9y635H_jCcX4RhVyjj8iwMlRnIirqrr8TnHiR5K_Q2rIeJYePzjNv3QuF9EUPYUhl";

        for (File logFile : logFiles) {
            System.out.println("解析中: " + logFile.getName());

            // 拡張子がない場合は一時ファイルにコピー
            File actualLogFile = logFile;
            boolean isTemp = false;
            if (!logFile.getName().contains(".")) {
                actualLogFile = new File(logFile.getAbsolutePath() + ".tmp.txt");
                try {
                    Files.copy(logFile.toPath(), actualLogFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    isTemp = true;
                } catch (IOException e) {
                    System.err.println("拡張子なしファイルのコピーに失敗: " + e.getMessage());
                    continue;
                }
            }

            File outputFile = new File(latestBuild, "summary_" + logFile.getName() + ".txt");

            JenkinsLogAnalyzer analyzer = new JenkinsLogAnalyzer();
            try {
                analyzer.analyzeLog(actualLogFile);
                analyzer.writeSummary(outputFile);
                DiscordWebhookSender.sendFileToDiscord(webhookUrl, outputFile);
                System.out.println("送信完了: " + outputFile.getName());
            } catch (IOException e) {
                System.err.println("エラー: " + e.getMessage());
            }

            // 一時ファイルを削除
            if (isTemp) {
                actualLogFile.delete();
            }
        }

        // フォルダを開く
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(latestBuild);
                System.out.println("フォルダを開きました: " + latestBuild.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("フォルダを開けませんでした: " + e.getMessage());
        }
    }

    // 最新のビルドフォルダを返す
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
