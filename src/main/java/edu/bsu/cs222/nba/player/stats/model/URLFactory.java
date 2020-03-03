package edu.bsu.cs222.nba.player.stats.model;

public class URLFactory {

    public static final class URLFactoryBuilder {
        private Integer year;
        private String personID;

        public Integer setYear(Integer year){
            this.year = year;
            return this.year;
        }

        public String setPersonID(String personID) {
            this.personID = personID;
            return this.personID;
        }

        public Integer getYear() {
            return year;
        }

        public String getPersonID() {
            return personID;
        }
    }
}
