package spotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Base64;
import java.util.List;

/** Spotify api interactions. */
public class Spotify implements SpotifyInterface {

    final List<String> topArtists;

    final int port = 8000;

    final String redirectUri = "http://127.0.0.1:" + port + "/callback";

    final String clientId = "ef362d3d003445ccae86ca1eccc1d654";

    final String secret = "871f93f319e847a1ad2ea059a1f2e7e3";

    String code;

    String token;

    String refreshToken;

    /** Initializes spotify class. */
    public Spotify() {
        topArtists = List.of();
        String url = getUrl();
        System.out.println(url);
        getCode();
        getToken();
    }

    String getUrl() {
        String client = "client_id=" + clientId;
        String response = "response_type=code";
        String redirect = "redirect_uri=" + redirectUri;
        String scopes = "scope=user-top-read";
        String query = client + "&" + response + "&" + redirect + "&" + scopes;
        return "https://accounts.spotify.com/authorize?" + query;
    }

    void getToken() {
        try {
            String credentials = clientId + ":" + secret;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String body =
                    "grant_type=authorization_code"
                            + "&code="
                            + code
                            + "&redirect_uri="
                            + redirectUri;
            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 400) ? conn.getErrorStream() : conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line = in.readLine().split("\":\"", 2)[1];
            in.close();
            conn.disconnect();
            String[] split = line.split("\"", 2);
            token = split[0];
            String split2 = split[1].split("refresh_token\":\"", 2)[1];
            refreshToken = split2.split("\"", 2)[0];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void getCode() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            code = in.readLine().split("=", 2)[1].split(" ", 2)[0];
        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pullTopArtists() {
        try {
            URL url = new URL("https://api.spotify.com/v1/me/top/tracks");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            InputStream is = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line = in.readLine();
            System.out.println(line);
            in.close();
            conn.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getTopArtists() {
        return this.topArtists;
    }
}
