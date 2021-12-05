import com.project_christopher.libraries.srp.Client;
import com.project_christopher.libraries.srp.Components.IVerifierAndSalt;
import com.project_christopher.libraries.srp.Components.M1AndA;
import com.project_christopher.libraries.srp.Exceptions.BadServerCredentials;
import com.project_christopher.libraries.srp.Modules.Parameters;
import com.project_christopher.libraries.srp.Modules.Routines;
import com.project_christopher.libraries.srp.Modules.Utils;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class node {

    public static void main(String[] args) throws Exception {
        new node().testJava();
    }

    @Test
    public void testJava() throws Exception {
        System.out.println("Start of test file.");

        register();
        login();
    }

    private String request(String url, String json) throws IOException {
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

        //add request header
        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpClient.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        httpClient.setDoOutput(true);

        OutputStream os = httpClient.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        osw.write(json);
        osw.flush();
        osw.close();
        os.close();  //don't forget to close the OutputStream

        httpClient.connect();

        BufferedInputStream bis = new BufferedInputStream(httpClient.getInputStream());
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result2 = bis.read();
        while(result2 != -1) {
            buf.write((byte) result2);
            result2 = bis.read();
        }

        return buf.toString();
    }

    public void register() throws IOException {
        // Client
        final String username = "projectChristopher";
        final String password = "password";

        Routines routines = new Routines(new Parameters());
        IVerifierAndSalt verifierAndSalt = Utils.generateVerifierAndSalt(routines, username, password);
        String salt = verifierAndSalt.salt;
        String verifier = verifierAndSalt.verifier;

        /* sendToServer(username, salt, verifier) */
        String json = String.format("{\"username\":\"%s\",\"salt\":\"%s\",\"verifier\":\"%s\"}", username, salt, verifier);
        String result = request("http://localhost:5000/register", json);

        if(!result.equals("ok!"))
            throw new IOException("Error during register response.");
    }

    public void login() throws BadServerCredentials, IOException {
        final String username = "projectChristopher";
        String password = "password";

        Client client = new Client(new Routines(new Parameters()));
        client.step1(username, password);
        password = ""; // No longer needed.

        /* sendToServer(username) */
        String result = request("http://localhost:5000/login", String.format("{\"username\":\"%s\",\"step\":\"1\"}", username));
        if(result.equals("failed!"))
            throw new IOException("Error during login response 1.");

        String salt = result.split("-salt-B-")[0];
        String B = result.split("-salt-B-")[1];

        M1AndA m1AndA = client.step2(salt, B);

        /* sendToServer(A, M1) */
        String result2 = request("http://localhost:5000/login", String.format("{\"A\":\"%s\",\"M1\":\"%s\",\"step\":\"2\"}", m1AndA.A, m1AndA.M1));
        if(result2.equals("failed!"))
            throw new IOException("Error during login response 2.");

        String M2 = result2;

        // Client
        client.step3(M2);

        System.out.println("End of test file.");
        System.exit(0);
    }
}
