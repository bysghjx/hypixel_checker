package com.example.myapplication.hypixel;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HypixelBedWarsInfo {
    public String uuid;
    public String name;
    public String Experience;
    public String coins;
    public String wins_bedwars;

    public int final_kills_bedwars;
    public int kills_bedwars;
    public int deaths_bedwars;
    public int final_deaths_bedwars;
    public double K_D;
    public int beds_lost_bedwars;
    public int beds_broken_bedwars;
    public int iron_resources_collected_bedwars;
    public int gold_resources_collected_bedwars;
    public int diamond_resources_collected_bedwars;
    public int emerald_resources_collected_bedwars;

    public String kills_Bedwars;
    public String final_kills_Bedwars;
    public String deaths_Bedwars;
    public String final_deaths_Bedwars;
    public String Bedwars_K_D;
    public String beds_lost_Bedwars;
    public String beds_broken_Bedwars;
    public String iron_resources_collected_Bedwars;
    public String gold_resources_collected_Bedwars;
    public String diamond_resources_collected_Bedwars;
    public String emerald_resources_collected_Bedwars;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HypixelBedWarsInfo that = (HypixelBedWarsInfo) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(name, that.name) && Objects.equals(Experience, that.Experience) && Objects.equals(coins, that.coins) && Objects.equals(wins_bedwars, that.wins_bedwars) && Objects.equals(final_kills_bedwars, that.final_kills_bedwars) && Objects.equals(kills_bedwars, that.kills_bedwars) && Objects.equals(deaths_bedwars, that.deaths_bedwars) && Objects.equals(final_deaths_bedwars, that.final_deaths_bedwars) && Objects.equals(K_D, that.K_D) && Objects.equals(beds_lost_bedwars, that.beds_lost_bedwars) && Objects.equals(beds_broken_bedwars, that.beds_broken_bedwars) && Objects.equals(iron_resources_collected_bedwars, that.iron_resources_collected_bedwars) && Objects.equals(gold_resources_collected_bedwars, that.gold_resources_collected_bedwars) && Objects.equals(diamond_resources_collected_bedwars, that.diamond_resources_collected_bedwars) && Objects.equals(emerald_resources_collected_bedwars, that.emerald_resources_collected_bedwars);
    }

    public void get_kd(){
        K_D = (double) kills_bedwars / (double) deaths_bedwars;
        final_kills_Bedwars = String.valueOf(final_kills_bedwars);
        kills_Bedwars = String.valueOf(kills_bedwars);
        deaths_Bedwars = String.valueOf(deaths_bedwars);
        final_deaths_Bedwars = String.valueOf(final_deaths_bedwars);
        Bedwars_K_D = String.valueOf(K_D);
        beds_lost_Bedwars = String.valueOf(beds_lost_bedwars);
        beds_broken_Bedwars = String.valueOf(beds_broken_bedwars);
        iron_resources_collected_Bedwars = String.valueOf(iron_resources_collected_bedwars);
        gold_resources_collected_Bedwars = String.valueOf(gold_resources_collected_bedwars);
        diamond_resources_collected_Bedwars = String.valueOf(diamond_resources_collected_bedwars);
        emerald_resources_collected_Bedwars = String.valueOf(emerald_resources_collected_bedwars);
    }

    @Override
    public int hashCode() {
            int result = 17;
            result = 31 * result + (uuid == null ? 0 : uuid.hashCode());
            result = 31 * result + (name == null ? 0 : name.hashCode());
            result = 31 * result + (Experience == null ? 0 : Experience.hashCode());
            result = 31 * result + (coins == null ? 0 : coins.hashCode());
            result = 31 * result + (wins_bedwars == null ? 0 : wins_bedwars.hashCode());
            result = 31 * result + (final_kills_Bedwars == null ? 0 : final_kills_Bedwars.hashCode());
            result = 31 * result + (kills_Bedwars == null ? 0 : kills_Bedwars.hashCode());
            result = 31 * result + (deaths_Bedwars == null ? 0 : deaths_Bedwars.hashCode());
            result = 31 * result + (final_deaths_Bedwars == null ? 0 : final_deaths_Bedwars.hashCode());
            result = 31 * result + (Bedwars_K_D == null ? 0 : Bedwars_K_D.hashCode());
            result = 31 * result + (beds_lost_Bedwars == null ? 0 : beds_lost_Bedwars.hashCode());
            result = 31 * result + (beds_broken_Bedwars == null ? 0 : beds_broken_Bedwars.hashCode());
            result = 31 * result + (iron_resources_collected_Bedwars == null ? 0 : iron_resources_collected_Bedwars.hashCode());
            result = 31 * result + (gold_resources_collected_Bedwars == null ? 0 : gold_resources_collected_Bedwars.hashCode());
            result = 31 * result + (diamond_resources_collected_Bedwars == null ? 0 : diamond_resources_collected_Bedwars.hashCode());
            result = 31 * result + (emerald_resources_collected_Bedwars == null ? 0 : emerald_resources_collected_Bedwars.hashCode());
            return result;
        }


    @NonNull
    @Override
    public String toString() {
        return "HypixelBedWarsInfo{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", Experience='" + Experience + '\'' +
                ", coins=" + coins +
                ", wins_bedwars=" + wins_bedwars +
                ", final_kills_bedwars=" + final_kills_bedwars +
                ", kills_bedwars=" + kills_bedwars +
                ", final_deaths_bedwars=" + final_deaths_bedwars +
                ", deaths_bedwars=" + deaths_bedwars +
                ", k_D=" + K_D +
                ", beds_lost_bedwars=" + beds_lost_bedwars +
                ", beds_broken_bedwars=" + beds_broken_bedwars +
                ", iron_resources_collected_bedwars=" + iron_resources_collected_bedwars +
                ", gold_resources_collected_bedwars=" + gold_resources_collected_bedwars +
                ", diamond_resources_collected_bedwars=" + diamond_resources_collected_bedwars +
                ", emerald_resources_collected_bedwars=" + emerald_resources_collected_bedwars +
                '}';
    }
}


