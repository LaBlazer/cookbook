package com.lblzr.cookbookplus.models;

import java.util.ArrayList;

public enum AmountUnit {
    GRAMS("grams"),
    LITRES("litres"),
    CUPS("cups"),
    PIECES("pieces");

    private final String unit;

    AmountUnit(final String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public static AmountUnit fromString(String text) {
        for (AmountUnit b : AmountUnit.values()) {
            if (b.unit.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No unit with text " + text + " found");
    }

    public static ArrayList<String> toArray() {
        ArrayList<String> values = new ArrayList<>();
        for (AmountUnit b : AmountUnit.values()) {
            values.add(b.unit);
        }
        return values;
    }
}
