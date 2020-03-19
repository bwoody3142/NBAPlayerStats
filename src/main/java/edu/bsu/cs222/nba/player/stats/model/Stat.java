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
    public static final Integer TPP = 8;

    private Stat(){}

    public static Stat create(){
        return new Stat();
    }

    public final List<String> getAllStats(){
        return Arrays.asList("ppg", "apg", "rpg", "topg", "spg", "bpg", "fgp", "ftp", "tpp");
    }

}
