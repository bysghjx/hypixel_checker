package com.example.myapplication.hypixel;

import androidx.annotation.NonNull;

import java.util.Objects;

public class BazaarInfo {
    public String name;
    public double buyPrice;
    public double sellPrice;
    public int buyVolume;
    public int sellVolume;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BazaarInfo that = (BazaarInfo) o;
        return Double.compare(that.buyPrice, buyPrice) == 0 && Double.compare(that.sellPrice, sellPrice) == 0 && buyVolume == that.buyVolume && sellVolume == that.sellVolume && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buyPrice, sellPrice, buyVolume, sellVolume);
    }

    @NonNull
    @Override
    public String toString() {
        return "BazaarInfo{" +
                "name='" + name + '\'' +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                ", buyVolume=" + buyVolume +
                ", sellVolume=" + sellVolume +
                '}';
    }
}
