package edu.bsu.cs222.nba.player.stats.model;


public enum Stat {
    PPG(0, "ppg"),
    APG(1, "apg"),
    RPG(2, "rpg"),
    TOPG(3, "topg"),
    SPG(4, "spg"),
    BPG(5, "bpg"),
    FGP(6, "fgp"),
    FTP(7, "ftp"),
    TPM(8, "tpm");

    public final int index;
    public final String dataSourceKey;

    Stat(int index, String dataSourceKey) {
        this.index = index;
        this.dataSourceKey = dataSourceKey;
    }

    int getIndex(){
        return index;
    }

    String getDataSourceKey(){
        return dataSourceKey;
    }
}

