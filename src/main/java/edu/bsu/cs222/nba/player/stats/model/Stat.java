package edu.bsu.cs222.nba.player.stats.model;

import java.util.Arrays;
import java.util.List;

public class Stat {

    public static final Integer PPG = 0;
    public static final Integer APG = 1;
    public static final Integer RPG = 2;
    public static final Integer TOPG = 3;
    public static final Integer SPG = 4;
    public static final Integer BPG = 5;
    public static final Integer FGP = 6;
    public static final Integer FTP = 7;
    public static final Integer TPM = 8;
    public static final String POINTS = "ppg";
    public static final String ASSISTS = "apg";
    public static final String REBOUNDS = "rpg";
    public static final String TURNOVERS = "topg";
    public static final String STEALS = "spg";
    public static final String BLOCKS = "bpg";
    public static final String FIELD_GOAL_PERCENTAGE = "fgp";
    public static final String FREE_THROW_PERCENTAGE = "ftp";
    public static final String THREE_POINTERS = "tpm";

    private float stat;

    private Stat(float stat){
        this.stat = stat;
    }

    public static Stat createStat(float stat){
        return new Stat(stat);
    }

    private Stat(){}

    public static Stat createObject() {
        return new Stat();
    }

    public final List<String> getAllStats(){
        return Arrays.asList(POINTS, ASSISTS, REBOUNDS, TURNOVERS, STEALS, BLOCKS,
                FIELD_GOAL_PERCENTAGE, FREE_THROW_PERCENTAGE, THREE_POINTERS);
    }

    @Override
    public String toString() {
        return String.valueOf(stat);
    }
}
