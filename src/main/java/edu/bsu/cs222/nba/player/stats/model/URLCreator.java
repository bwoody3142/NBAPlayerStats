package edu.bsu.cs222.nba.player.stats.model;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class URLCreator {
    public URLCreator(){}

    public static URLCreator createEmptyUrl() {
        return new URLCreator();
    }

    public URL getPlayerListUrl(Integer year) throws Exception {
        String encodedYear = URLEncoder.encode(year.toString(),"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/players.json";
        return new URL(search);
    }

    public InputStream createPlayerListStream(Integer year) throws Exception {
        return getPlayerListUrl(year).openConnection().getInputStream();
    }

    public URL getPlayerProfileUrl(URLFactory.URLFactoryBuilder urlFactoryBuilder) throws Exception {
        Integer year = urlFactoryBuilder.getYear();
        Integer personID = urlFactoryBuilder.getPersonID();
        String encodedYear = URLEncoder.encode(urlFactoryBuilder.setYear(year).toString(),"UTF-8");
        String encodedPersonID = URLEncoder.encode(urlFactoryBuilder.setPersonID(personID).toString(),"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/players/" + encodedPersonID + "_profile.json";
        return new URL(search);
    }

    public InputStream createPlayerProfileStream(URLFactory.URLFactoryBuilder urlFactoryBuilder) throws Exception {
        return getPlayerProfileUrl(urlFactoryBuilder).openConnection().getInputStream();
    }
}
