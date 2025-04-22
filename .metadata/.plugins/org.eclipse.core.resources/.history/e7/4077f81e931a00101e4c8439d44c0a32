package LogAnalysis_Jenkins;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class DiscordWebhookSender {

    public static void sendFileToDiscord(String webhookUrl, File file) throws IOException {
        String boundary = Long.toHexString(System.currentTimeMillis());
        String LINE_FEED = "\r\n";

        HttpURLConnection connection = (HttpURLConnection) new URL(webhookUrl).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (
            OutputStream output = connection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, "UTF-8"), true)
        ) {
            // ファイル部分
            writer.append("--").append(boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"")
                    .append(file.getName()).append("\"").append(LINE_FEED);
            writer.append("Content-Type: text/plain").append(LINE_FEED);
            writer.append(LINE_FEED).flush();

            Files.copy(file.toPath(), output);
            output.flush();

            writer.append(LINE_FEED).flush();
            writer.append("--").append(boundary).append("--").append(LINE_FEED).flush();
        }

        int responseCode = connection.getResponseCode();
        System.out.println("📨 Discord Webhook 送信完了。レスポンスコード: " + responseCode);
    }
}
