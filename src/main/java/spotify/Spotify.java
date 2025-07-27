package spotify;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/** Spotify api interactions. */
public class Spotify implements SpotifyInterface {

    List<String> topTracks = new ArrayList<>();

    List<String> topArtists = new ArrayList<>();

    List<String> topGenres = new ArrayList<>();

    final int port = 8547;

    final String redirectUri = "http://127.0.0.1:" + port + "/callback";

    final String clientId = "ef362d3d003445ccae86ca1eccc1d654";

    final String secret = "871f93f319e847a1ad2ea059a1f2e7e3";

    String code;

    String token;

    String refreshToken;

    /** Initializes spotify class. */
    public Spotify() {
        try {
            String url = getUrl();
            if (Desktop.isDesktopSupported()
                    && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println(url);
            }
            getCode();
            getToken();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
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
            if (responseCode >= 400) {
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                System.out.println(in.readLine());
            }
            JSONObject json = new JSONObject(new JSONTokener(is));
            token = json.getString("access_token");
            refreshToken = json.getString("refresh_token");
            conn.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void getCode() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            InputStream is = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line = in.readLine();
            code = line.split("=", 2)[1].split(" ", 2)[0];
        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void pullTopArtistsAndGenres() {
        try {
            topArtists.clear();
            topGenres.clear();
            URL url = new URL("https://api.spotify.com/v1/me/top/artists?limit=10");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 400) ? conn.getErrorStream() : conn.getInputStream();
            if (responseCode >= 400) {
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                System.out.println(in.readLine());
            }
            JSONObject json = new JSONObject(new JSONTokener(is));
            JSONArray items = json.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject artist = items.getJSONObject(i);
                String name = artist.getString("name");
                topArtists.add(name);
                JSONArray genres = artist.getJSONArray("genres");
                for (int j = 0; j < genres.length(); j++) {
                    String genre = genres.getString(j);
                    topGenres.add(genre);
                }
            }
            conn.disconnect();
            sortGenres();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void sortGenres() {
        Map<String, Integer> map = new HashMap<>();
        for (String genre : topGenres) {
            Integer num = map.get(genre);
            if (num == null) {
                num = 0;
            }
            num = num + 1;
            map.put(genre, num);
        }
        List<String> list = new ArrayList<>();
        List<Integer> nums = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int i = 0;
            while (i < nums.size() && nums.get(i) > entry.getValue()) {
                i += 1;
            }
            nums.add(i, entry.getValue());
            list.add(i, entry.getKey());
        }
        if (list.size() > 5) {
            list = list.subList(0, 5);
        }
        topGenres = list;
    }

    @Override
    public void pullTopTracks() {
        try {
            topTracks.clear();
            URL url = new URL("https://api.spotify.com/v1/me/top/tracks?limit=5");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 400) ? conn.getErrorStream() : conn.getInputStream();
            if (responseCode >= 400) {
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                System.out.println(in.readLine());
            }
            JSONObject json = new JSONObject(new JSONTokener(is));
            JSONArray items = json.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject artist = items.getJSONObject(i);
                String name = artist.getString("name");
                topTracks.add(name);
            }
            conn.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void refreshToken() {
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
                    "grant_type=refresh_token"
                            + "&refresh_token="
                            + refreshToken
                            + "&client_id="
                            + clientId;
            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes());
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 400) ? conn.getErrorStream() : conn.getInputStream();
            if (responseCode >= 400) {
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                System.out.println(in.readLine());
            }
            JSONObject json = new JSONObject(new JSONTokener(is));
            token = json.getString("access_token");
            if (json.has("refresh_token")) {
                refreshToken = json.getString("refresh_token");
            }
            conn.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getTopTracks() {
        return this.topTracks;
    }

    @Override
    public List<String> getTopArtists() {
        return this.topArtists;
    }

    @Override
    public List<String> getTopGenres() {
        return this.topGenres;
    }
}
