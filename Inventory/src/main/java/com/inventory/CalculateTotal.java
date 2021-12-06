package com.inventory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateTotal {
    private static CalculateTotal CalculateTotal = null;
     static int totalAmount;
     static int totalEssential=5;
     static int totalLuxury=3;
     static int totalMisc=6;

    public static int getTotalAmount() {
        return totalAmount;
    }

    public static int getTotalEssential() {
        return totalEssential;
    }

    public static int getTotalLuxury() {
        return totalLuxury;
    }

    public static int getTotalMisc() {
        return totalMisc;
    }

    public static void setTotalAmount(int totalAmount) {
        CalculateTotal.totalAmount -= totalAmount;
    }

    public static void setTotalEssential(int totalEssential) {
        CalculateTotal.totalEssential -= totalEssential;
    }

    public static void setTotalLuxury(int totalLuxury) {
        CalculateTotal.totalLuxury -= totalLuxury;
    }

    public static void setTotalMisc(int totalMisc) {
        CalculateTotal.totalMisc -= totalMisc;
    }

    // denotes singleton class
    public static CalculateTotal getInstance()
    {
        if (CalculateTotal == null)
            CalculateTotal = new CalculateTotal();

        return CalculateTotal;
    }
}
