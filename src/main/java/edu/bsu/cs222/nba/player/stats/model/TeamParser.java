package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.InputStream;

public class TeamParser {

    public static TeamParserBuilder withStream(InputStream stream) {
        return new TeamParserBuilder(stream);
    }

    public static final class TeamParserBuilder{
        private InputStream stream;
        private String fullName;

        public TeamParserBuilder(InputStream stream){
            this.stream = stream;
        }

        public TeamParser andFullName(String fullName) {
            this.fullName = fullName;
            return new TeamParser(this);
        }
    }

    private InputStream stream;
    private String fullName;

    public TeamParser(TeamParserBuilder builder) {
        this.stream = builder.stream;
        this.fullName = builder.fullName;
    }

    public Team parse(){
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        JSONArray abbreviationArray = JsonPath.read(document, "$..standard[?(@.fullName==" + fullName + ")].tricode");
        JSONArray urlNameArray = JsonPath.read(document, "$..standard[?(@.fullName==" + fullName + ")].urlName");
        return Team.create(urlNameArray.get(0).toString(), abbreviationArray.get(0).toString());
    }
}
