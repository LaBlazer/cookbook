package com.lblzr.cookbookplus.helpers;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.lblzr.cookbookplus.models.AmountUnit;
import com.lblzr.cookbookplus.models.Ingredient;
import com.lblzr.cookbookplus.models.Recipe;
import com.lblzr.cookbookplus.models.Step;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class RecipeSerializer {
    private static final String extension = ".cbpx";

    public static void Save(Context c, Recipe r) {
        String filename = cleanString(r.getName()) + extension;
        Log.d("CBP", "Saving recipe to file: " + filename);
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "recipe");
            serializer.attribute("", "name", r.getName());
            serializer.attribute("", "duration", String.valueOf(r.getDuration()));
            serializer.attribute("", "image", r.getImage());

            for (Ingredient i : r.getIngredients()) {
                serializer.startTag("", "ingredient");
                serializer.attribute("", "name", i.getName());
                serializer.attribute("", "amount", String.valueOf(i.getAmount()));
                serializer.attribute("", "unit", i.getUnit().getUnit());
                serializer.attribute("", "optional", String.valueOf(i.isOptional()));
                serializer.endTag("", "ingredient");
            }

            for (Step s : r.getSteps()) {
                serializer.startTag("", "step");
                serializer.attribute("", "name", s.getName());
                serializer.attribute("", "description", s.getDescription());
                serializer.attribute("", "image", s.getImage());
                serializer.endTag("", "step");
            }

            serializer.endTag("", "recipe");
            serializer.endDocument();
            FileHelper.writeString(c, filename, writer.toString());
        } catch (Exception ex) {
            Log.e("CBP", "Exception while serializing recipe: " + ex.toString());
        }
    }

    public static ArrayList<Recipe> LoadAll(Context c) {
        ArrayList<Recipe> recipes = new ArrayList<>();

        for(File file : FileHelper.listFiles(c, extension)) {
            recipes.add(RecipeSerializer.Load(file));
        }

        return recipes;
    }

    public static Recipe Load(File file) {
        Log.d("CBP", "Loading recipe from file: " + file.getAbsolutePath());

        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(FileHelper.getReader(file));
            Recipe r = readRecipe(parser);
            r.setFile(file);
            return r;

        } catch (Exception ex) {
            Log.e("CBP", "Exception while parsing recipe: " + ex.toString());
        }
        return null;
    }

    private static Recipe readRecipe(XmlPullParser parser) throws XmlPullParserException, IOException {
        String name = "";
        int duration = 0;
        String image = "";
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ArrayList<Step> steps = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            String tag = parser.getName();
            if (tag.equals("recipe")) {
                for(int i = 0; i < parser.getAttributeCount(); i++) {
                    if(parser.getAttributeName(i).equals("name")) {
                        name = parser.getAttributeValue(i);
                    } else if(parser.getAttributeName(i).equals("duration")) {
                        duration = Integer.parseInt(parser.getAttributeValue(i));
                    } else if(parser.getAttributeName(i).equals("image")) {
                        image = parser.getAttributeValue(i);
                    }
                }
            } else if (tag.equals("ingredient")) {
                ingredients.add(readIngredient(parser));
            } else if (tag.equals("step")) {
                steps.add(readStep(parser));
            }
        }

        return new Recipe(name, ingredients, steps, image, duration);
    }

    private static Step readStep(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "step");
        String name = "";
        String description = "";
        String image = "";

        do {
            for(int i = 0; i < parser.getAttributeCount(); i++) {
                if (parser.getAttributeName(i).equals("name")) {
                    name = parser.getAttributeValue(i);
                } else if (parser.getAttributeName(i).equals("description")) {
                    description = parser.getAttributeValue(i);
                } else if (parser.getAttributeName(i).equals("image")) {
                    image = parser.getAttributeValue(i);
                }
            }
        } while (parser.next() != XmlPullParser.END_TAG);

        parser.require(XmlPullParser.END_TAG, null, "step");
        return new Step(name, description, image);
    }

    private static Ingredient readIngredient(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "ingredient");
        String name = "";
        double amount = 0;
        AmountUnit unit = AmountUnit.PIECES;
        boolean optional = false;

        do {
            for(int i = 0; i < parser.getAttributeCount(); i++) {
                if(parser.getAttributeName(i).equals("name")) {
                    name = parser.getAttributeValue(i);
                } else if(parser.getAttributeName(i).equals("amount")) {
                    amount = Double.parseDouble(parser.getAttributeValue(i));
                } else if(parser.getAttributeName(i).equals("unit")) {
                    unit = AmountUnit.fromString(parser.getAttributeValue(i));
                } else if(parser.getAttributeName(i).equals("optional")) {
                    optional = Boolean.parseBoolean(parser.getAttributeValue(i));
                }
            }
        } while (parser.next() != XmlPullParser.END_TAG);

        parser.require(XmlPullParser.END_TAG, null, "ingredient");
        return new Ingredient(name, amount, unit, optional);
    }

    private static String cleanString(String text)
    {
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");

        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");

        // replace space with underscore
        text = text.replace(' ', '_');

        return text.trim().toLowerCase();
    }
}
