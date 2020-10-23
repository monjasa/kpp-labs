package org.monjasa.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@JsonTypeInfo(use = Id.NAME,
        property = "type")
@JsonSubTypes({
        @Type(value = DefaultMeal.class, name = "Default"),
        @Type(value = SpecialMeal.class, name = "Special"),
        @Type(value = IntoleranceFreeMeal.class, name = "Intolerance-Free")
})
public abstract class Meal {

    protected String name;
    protected int servingInGrams;
    protected double calories;
    protected double price;

    protected List<String> ingredients;

    public Meal() {
        this.ingredients = new ArrayList<>();
    }

    public Meal(String name, int servingInGrams, double calories, double price, List<String> ingredients) {
        this.name = name;
        this.servingInGrams = servingInGrams;
        this.calories = calories;
        this.price = price;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServingInGrams() {
        return servingInGrams;
    }

    public void setServingInGrams(int servingInGrams) {
        this.servingInGrams = servingInGrams;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static class MealComparatorByPrice implements Comparator<Meal> {
        @Override
        public int compare(Meal o1, Meal o2) {
            return Double.compare(o1.price, o2.price);
        }
    }

    public class MealComparatorByCalories implements Comparator<Meal> {
        @Override
        public int compare(Meal o1, Meal o2) {
            return Double.compare(o1.calories, o2.calories);
        }
    }
}
