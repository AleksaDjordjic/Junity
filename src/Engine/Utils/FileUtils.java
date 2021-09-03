package Engine.Utils;

import java.io.*;

public class FileUtils
{
    public static String LoadAsString(String path)
    {
        StringBuilder results = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(FileUtils.class.getResourceAsStream(path))))
        {
            String line = "";
            while((line = reader.readLine()) != null)
            {
                results.append(line).append("\n");
            }
        }
        catch (Exception exception)
        {
            System.err.print("Error loading file: " + exception.toString());
        }

        return results.toString();
    }
}
