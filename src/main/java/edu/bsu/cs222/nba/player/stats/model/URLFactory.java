package edu.bsu.cs222.nba.player.stats.model;

public class URLFactory {

    public static URLFactoryBuilder withYear(Integer year){
        return new URLFactoryBuilder(year);
    }

    public static final class URLFactoryBuilder {
        private final Integer year;
        private Integer personID;

        public URLFactoryBuilder(Integer year){
            this.year = year;
        }

        public URLFactory andPersonID(Integer personID) {
            this.personID = personID;
            return create(this);
        }
    }

    private final Integer year;
    private final Integer personID;

    private URLFactory(URLFactoryBuilder builder){
        this.year = builder.year;
        this.personID = builder.personID;
    }

    public static URLFactory create(URLFactoryBuilder builder){
        return new URLFactory(builder);
    }

    public Integer getYear() {
        return year;
    }

    public Integer getPersonID() {
        return personID;
    }

}
