package com.example.myapplication.hypixel;

import androidx.annotation.NonNull;

import java.util.Objects;

public class AuctionsInfo {

    public String name;
    public String start;
    public String end;

    public double lowestBin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuctionsInfo that = (AuctionsInfo) o;
        return Double.compare(that.lowestBin, lowestBin) == 0 && Objects.equals(name, that.name) && Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, start, end, lowestBin);
    }

    @NonNull
    @Override
    public String toString() {
        return "AuctionsInfo{" +
                "name='" + name + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", lowestBin=" + lowestBin +
                '}';
    }
}
