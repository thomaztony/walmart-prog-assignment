import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequest {

    public static String get(String url){
        int responseCode = 0;
        StringBuffer response = new StringBuffer();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch(MalformedURLException m){
            System.err.println("ERROR: Malformed URL");
            m.printStackTrace();
            System.exit(-1);

        } catch (IOException io) {
            System.err.printf("ERROR: Http Response Code: %d\n", responseCode);
            io.printStackTrace();
            System.exit(-1);
        }

        return response.toString();
    }
}
