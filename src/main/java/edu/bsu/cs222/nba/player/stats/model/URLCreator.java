package edu.bsu.cs222.nba.player.stats.model;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class URLCreator {
    public URLCreator(){}

    public static URLCreator createEmptyUrl() {
        return new URLCreator();
    }

    public InputStream createPlayerListUrl(Integer year) throws Exception {
        String encodedYear = URLEncoder.encode(year.toString(),"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/players.json";
        URL url = new URL(search);
        return url.openConnection().getInputStream();
    }

    public InputStream createPlayerProfileUrl (URLFactory.URLFactoryBuilder urlFactoryBuilder) throws Exception {
        //URLFactory urlFactory = new URLFactory(new URLFactory.URLFactoryBuilder());
        Integer year = urlFactoryBuilder.getYear();
        String personID = urlFactoryBuilder.getPersonID();
        String encodedYear = URLEncoder.encode(urlFactoryBuilder.setYear(year).toString(),"UTF-8");
        String encodedPersonID = URLEncoder.encode(urlFactoryBuilder.setPersonID(personID),"UTF-8");
        String search = "http://data.nba.net/data/10s/prod/v1/" + encodedYear + "/players/" + encodedPersonID + "_profile.json";
        URL url = new URL(search);
        return url.openConnection().getInputStream();
    }
}
