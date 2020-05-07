package edu.bsu.cs222.nba.player.stats.model;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

public class CurrentSeasonGenerator {

    public int generateCurrentSeason(){
        int year = Year.now().getValue();
        Month month = YearMonth.now().getMonth();
        String monthAsString = String.valueOf(month);
        if(!(monthAsString.equals("OCTOBER") || monthAsString.equals("NOVEMBER") || monthAsString.equals("DECEMBER"))){
            year -= 1;
        }
        return year;
    }
}