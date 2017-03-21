
//package ru.shapovalov.GetSteamGameList;
//
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GetSteamGameList {
//
//    public static List getSteamGameList() {
//        return steamGameList;
//    }
//
//    private static List steamGameList = null;
//
//    public GetSteamGameList(URL url) {
//        HttpURLConnection connection = null;
//        try {
//            //Create connection
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Content-Type", "application/xml");
//            connection.setRequestProperty("Content-Length", "0");
//
//            //Get Response
//            InputStream is = connection.getInputStream();
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//            String line;
//            StringBuffer response = new StringBuffer();
//            while ((line = rd.readLine()) != null) {
//                response.append(line);
//            }
//            rd.close();
//            steamGameList = jsonToGameList(response.toString());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//    }
//
//    private List<String> jsonToGameList(String json) {
//        List<String> gameList = new ArrayList<>();
//        JSONObject obj = new JSONObject(json);
//        JSONArray gameNames = obj.getJSONObject("applist").getJSONArray("apps");
//        for (int i = 0; i < gameNames.length(); i++) {
//            String gameName = gameNames.getJSONObject(i).getString("name");
//            gameList.add(gameName);
//        }
//        return gameList;
//    }
//}
//
