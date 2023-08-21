import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class TestFileDownloader
{
    public static void main(String[] args) throws IOException
    {
        String serverUrl = "http://localhost:9000/spelldb";
        String filePath = "newspelldatabase.db";

        URL url = new URL(serverUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("Received response code: " + responseCode);
        if(responseCode == HttpURLConnection.HTTP_OK)
        {
            InputStream is = connection.getInputStream();
            FileOutputStream fos = new FileOutputStream(filePath);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while((bytesRead = is.read(buffer)) != -1)
            {
                fos.write(buffer, 0, bytesRead);
            }

            is.close();
            fos.close();
            
            System.out.println("File received and saved as " + filePath);
        } else
        {
            System.out.println("Failed to receive the file. HTTP response code: " + responseCode);
        }
        connection.disconnect();

    }
}