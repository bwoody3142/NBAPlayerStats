package edu.bsu.cs222.nba.player.stats.model;


import java.util.Arrays;
import java.util.List;

public enum Statistic {
    PPG(0, "ppg"),
    APG(1, "apg"),
    RPG(2, "rpg"),
    TURNOVERS(3, "turnovers"),
    SPG(4, "spg"),
    BPG(5, "bpg"),
    FGP(6, "fgp"),
    FTP(7, "ftp"),
    TPM(8, "tpm"),
    GAMES_PLAYED(9, "gamesPlayed");

    public final int index;
    public final String dataSourceKey;

    Statistic(int index, String dataSourceKey) {
        this.index = index;
        this.dataSourceKey = dataSourceKey;
    }

    int getIndex(){
        return index;
    }

    String getDataSourceKey(){
        return dataSourceKey;
    }

    public List<Statistic> getListOfUsedStatistics(){
        return Arrays.asList(PPG,
                APG,
                RPG,
                TURNOVERS,
                SPG,
                BPG,
                FGP,
                FTP,
                TPM);
    }
}

