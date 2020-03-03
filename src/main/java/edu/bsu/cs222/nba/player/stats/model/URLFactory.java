package edu.bsu.cs222.nba.player.stats.model;

public class URLFactory {

    public static final class URLFactoryBuilder {
        private Integer year;
        private Integer personID;

        public Integer setYear(Integer year){
            this.year = year;
            return this.year;
        }

        public Integer setPersonID(Integer personID) {
            this.personID = personID;
            return this.personID;
        }

        public Integer getYear() {
            return year;
        }

        public Integer getPersonID() {
            return personID;
        }
    }
}
