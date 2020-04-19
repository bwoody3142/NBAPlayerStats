package edu.bsu.cs222.nba.player.stats.model;

public enum PlayerInfoData {
    FIRST_NAME(0, "firstName"),
    LAST_NAME(1, "lastName"),
    JERSEY(2, "jersey"),
    POSITION(3, "pos"),
    HEIGHT_FEET(4, "heightFeet"),
    HEIGHT_INCHES(5, "heightInches"),
    WEIGHT(6, "weightPounds"),
    YEARS_PRO(7, "yearsPro");

    private final int index;
    private final String dataSourceKey;

    PlayerInfoData(int index, String dataSourceKey){
        this.index = index;
        this.dataSourceKey = dataSourceKey;
    }

    public int getIndex() {
        return index;
    }

    public String getDataSourceKey() {
        return dataSourceKey;
    }
}
