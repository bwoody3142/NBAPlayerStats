package edu.bsu.cs222.nba.player.stats.model;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class URLCreator {
    public URLCreator(){}

    public static URLCreator createEmptyUrl() {
        return new URLCreator();
    }

    public URL setupPlayerListUrl(Integer year) throws Exception {
        String encodedYear = URLEncoder.encode(year.toString(),"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/players.json";
        return new URL(search);
    }

    public InputStream createPlayerListUrl(Integer year) throws Exception {
        return setupPlayerListUrl(year).openConnection().getInputStream();
    }

    public URL setupPlayerProfileUrl(URLFactory.URLFactoryBuilder urlFactoryBuilder) throws Exception {
        Integer year = urlFactoryBuilder.getYear();
        String personID = urlFactoryBuilder.getPersonID();
        String encodedYear = URLEncoder.encode(urlFactoryBuilder.setYear(year).toString(),"UTF-8");
        String encodedPersonID = URLEncoder.encode(urlFactoryBuilder.setPersonID(personID),"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/players/" + encodedPersonID + "_profile.json";
        return new URL(search);
    }

    public InputStream createPlayerProfileUrl (URLFactory.URLFactoryBuilder urlFactoryBuilder) throws Exception {
        return setupPlayerProfileUrl(urlFactoryBuilder).openConnection().getInputStream();
    }
}
