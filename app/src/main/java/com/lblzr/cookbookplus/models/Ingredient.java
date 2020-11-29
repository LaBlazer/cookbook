package com.lblzr.cookbookplus.models;

import java.util.Locale;

public class Ingredient {
    public enum AmountUnit {
        GRAMS,
        LITRES,
        CUPS,
        PIECES
    }

    private String name;
    private double amount;
    private AmountUnit unit;

    public Ingredient(String name, double amount, AmountUnit unit) {
        this.name = name.toLowerCase();
        this.amount = amount;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    private String formatLitres(double amount) {
        if(amount < 1.) {
            return String.format(Locale.ENGLISH, "%dml", (int)(amount * 1000));
        }

        return String.format(Locale.ENGLISH, "%f.2l", amount);
    }

    private String formatGrams(double amount) {
        if(amount >= 1000.) {
            return String.format(Locale.ENGLISH, "%dkg", (int)(amount / 1000));
        }

        return String.format(Locale.ENGLISH, "%dg", (int)amount);
    }

    public String getAmountString() {
        switch (unit) {
            case CUPS:
                return String.format(Locale.ENGLISH, "%d cups", (int)amount);
            case PIECES:
                return String.format(Locale.ENGLISH, "%d pieces", (int)amount);
            case GRAMS:
                return formatGrams(amount);
            case LITRES:
                return formatLitres(amount);
            default:
                return "unknown";
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%s of %s", getAmountString(), name);
    }
}
