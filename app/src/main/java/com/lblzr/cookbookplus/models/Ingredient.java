package com.lblzr.cookbookplus.models;

import java.io.Serializable;
import java.util.Locale;

public class Ingredient implements Serializable {
    public enum AmountUnit {
        GRAMS,
        LITRES,
        CUPS,
        PIECES
    }

    private String name;
    private double amount;
    private AmountUnit unit;
    private boolean optional;

    public Ingredient(String name, double amount, AmountUnit unit, boolean optional) {
        this.name = name.toLowerCase();
        this.amount = amount;
        this.unit = unit;
        this.optional = optional;
    }

    public Ingredient(String name, double amount, AmountUnit unit) {
        this(name, amount, unit, false);
    }


    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    private static String formatDouble(double d)
    {
        if(d == (long) d)
            return String.format(Locale.ENGLISH, "%d", (long)d);
        else
            return String.format(Locale.ENGLISH, "%.3f", d);
    }

    private String formatLitres(double amount) {
        if(amount < 1.) {
            return String.format(Locale.ENGLISH, "%dml", (long)(amount * 1000));
        }

        return formatDouble(amount) + "l";
    }

    private String formatGrams(double amount) {
        if(amount >= 1000.) {
            return String.format(Locale.ENGLISH, "%dkg", (long)(amount / 1000));
        }

        return formatDouble(amount) + "g";
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
