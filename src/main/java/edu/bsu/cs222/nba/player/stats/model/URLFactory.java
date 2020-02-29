package edu.bsu.cs222.nba.player.stats.model;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class URLFactory {

    public InputStream createPlayerListUrl(String year) throws Exception {
        String encodedSearch = URLEncoder.encode(year,"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedSearch + "/players.json";
        URL url = new URL(search);
        URLConnection connection = url.openConnection();
        return connection.getInputStream();
    }

    public InputStream createPlayerProfileUrl (String year, String personID) throws Exception {
        String encodedYearSearch = URLEncoder.encode(year,"UTF-8");
        String encodedIdSearch = URLEncoder.encode(personID,"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYearSearch + "/players/" + encodedIdSearch + "_profile.json";
        URL url = new URL(search);
        URLConnection connection = url.openConnection();
        return connection.getInputStream();
    }

    public static URLFactory createEmptyURLFactory(){
        return new URLFactory();
    }
}
