package edu.bsu.cs222.nba.player.stats.model;

import java.util.Arrays;
import java.util.List;

public class PlayerInfo {

    public static PlayerInfoBuilder withName(String name){
        return new PlayerInfoBuilder(name);
    }

    public static final class PlayerInfoBuilder {

        private final String name;
        private int jerseyNumber;
        private String position;
        private int yearsPro;
        private int heightFeet;
        private int heightInches;
        private int weight;

        public PlayerInfoBuilder(String name){
            this.name = name;
        }

        public PlayerInfoBuilder jersey(int jerseyNumber){
            this.jerseyNumber = jerseyNumber;
            return this;
        }

        public PlayerInfoBuilder position(String position){
            this.position = position;
            return this;
        }

        public PlayerInfoBuilder yearsPro(int yearsPro){
            this.yearsPro = yearsPro;
            return this;
        }

        public PlayerInfoBuilder heightFeet(int heightFeet){
            this.heightFeet = heightFeet;
            return this;
        }

        public PlayerInfoBuilder heightInches(int heightInches){
            this.heightInches = heightInches;
            return this;
        }

        public PlayerInfo andWeight(int weight){
            this.weight = weight;
            return newPlayerInfo(this);
        }

    }

    private final String name;
    private final int jerseyNumber;
    private final String position;
    private final int yearsPro;
    private final int heightFeet;
    private final int heightInches;
    private final int weight;

    private PlayerInfo(PlayerInfoBuilder builder){
        this.name = builder.name;
        this.jerseyNumber = builder.jerseyNumber;
        this.position = builder.position;
        this.yearsPro = builder.yearsPro;
        this.heightFeet = builder.heightFeet;
        this.heightInches = builder.heightInches;
        this.weight = builder.weight;
    }

    public static PlayerInfo newPlayerInfo(PlayerInfoBuilder builder){
        return new PlayerInfo(builder);
    }

    public List<Object> getPlayerInfoAsList(){
        return Arrays.asList(getName(),
            getJerseyNumber(),
            getPosition(),
            getYearsPro(),
            getHeightFeet(),
            getHeightInches(),
            getWeight());
    }

    public String getName() {
        return name;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public String getPosition() {
        return position;
    }

    public int getYearsPro() {
        return yearsPro;
    }

    public int getHeightFeet() {
        return heightFeet;
    }

    public int getHeightInches() {
        return heightInches;
    }

    public int getWeight() {
        return weight;
    }
}
