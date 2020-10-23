package org.monjasa.model;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpecialMeal extends Meal {

    private Set<String> characteristics;

    public SpecialMeal() {
        super();
        characteristics = new HashSet<>();
    }

    public SpecialMeal(
            String name,
            int servingInGrams,
            double calories,
            double price,
            List<String> ingredients,
            Set<String> characteristics
    ) {
        super(name, servingInGrams, calories, price, ingredients);
        this.characteristics = characteristics;
    }

    public static SpecialMealBuilder builder() {
        return new SpecialMealBuilder();
    }

    public Set<String> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Set<String> characteristics) {
        this.characteristics = characteristics;
    }

    @Override
    public String toString() {
        return "SpecialMeal{" +
                "characteristics=" + characteristics +
                ", name='" + name + '\'' +
                ", servingInGrams=" + servingInGrams +
                ", calories=" + calories +
                ", price=" + price +
                ", ingredients=" + ingredients +
                '}';
    }

    public static class SpecialMealBuilder {

        private final SpecialMeal meal;

        public SpecialMealBuilder() {
            meal = new SpecialMeal();
        }

        public SpecialMealBuilder name(String name) {
            meal.name = name;
            return this;
        }

        public SpecialMealBuilder servingInGrams(int servingInGrams) {
            meal.servingInGrams = servingInGrams;
            return this;
        }

        public SpecialMealBuilder calories(double calories) {
            meal.calories = calories;
            return this;
        }

        public SpecialMealBuilder price(double price) {
            meal.price = price;
            return this;
        }

        public SpecialMealBuilder ingredient(String ingredient) {
            meal.ingredients.add(ingredient);
            return this;
        }

        public SpecialMealBuilder ingredients(Collection<? extends String> ingredients) {
            meal.ingredients.addAll(ingredients);
            return this;
        }

        public SpecialMealBuilder clearIngredients() {
            meal.ingredients.clear();
            return this;
        }

        public SpecialMealBuilder characteristic(String characteristic) {
            meal.characteristics.add(characteristic);
            return this;
        }

        public SpecialMealBuilder characteristics(Collection<? extends String> characteristics) {
            meal.characteristics.addAll(characteristics);
            return this;
        }

        public SpecialMealBuilder clearCharacteristics() {
            meal.characteristics.clear();
            return this;
        }

        public SpecialMeal build() {
            return meal;
        }

        @Override
        public String toString() {
            return "SpecialMealBuilder{" +
                    "meal=" + meal +
                    '}';
        }
    }
}
