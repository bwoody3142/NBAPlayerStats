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
        private final InputStream stream;
        private String fullName;

        public TeamParserBuilder(InputStream stream){
            this.stream = stream;
        }

        public TeamParser andFullTeamName(String fullName) {
            this.fullName = fullName;
            return new TeamParser(this);
        }
    }

    private final String fullName;
    private final Object document;

    public TeamParser(TeamParserBuilder builder) {
        InputStream stream = builder.stream;
        this.fullName = builder.fullName;
        document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
    }

    public Team parse(){
        JSONArray abbreviation = makeQuery("tricode");
        JSONArray urlName = makeQuery("urlName");
        return Team.withUrlName(getArrayAsString(urlName)).andAbbreviation(getArrayAsString(abbreviation));
    }

    private JSONArray makeQuery(String query){
        return JsonPath.read(document, "$..standard[?(@.fullName==" + fullName + ")]." + query);
    }

    private String getArrayAsString(JSONArray array){
        return array.get(0).toString();
    }
}
