package meal;

import java.util.Collection;
import java.util.List;

public class DefaultMeal extends Meal {

    public DefaultMeal() {
        super();
    }

    public DefaultMeal(String name, int portionInGrams, int calories, double price, List<String> ingredients) {
        super(name, portionInGrams, calories, price, ingredients);
    }

    public static DefaultMealBuilder builder() {
        return new DefaultMealBuilder();
    }

    @Override
    public String toString() {
        return "DefaultMeal{" +
                "name='" + name + '\'' +
                ", servingInGrams=" + servingInGrams +
                ", calories=" + calories +
                ", price=" + price +
                ", ingredients=" + ingredients +
                '}';
    }

    public static class DefaultMealBuilder {

        private final DefaultMeal meal;

        public DefaultMealBuilder() {
            meal = new DefaultMeal();
        }

        public DefaultMealBuilder name(String name) {
            meal.name = name;
            return this;
        }

        public DefaultMealBuilder servingInGrams(int servingInGrams) {
            meal.servingInGrams = servingInGrams;
            return this;
        }

        public DefaultMealBuilder calories(double calories) {
            meal.calories = calories;
            return this;
        }

        public DefaultMealBuilder price(double price) {
            meal.price = price;
            return this;
        }

        public DefaultMealBuilder ingredient(String ingredient) {
            meal.ingredients.add(ingredient);
            return this;
        }

        public DefaultMealBuilder ingredients(Collection<? extends String> ingredients) {
            meal.ingredients.addAll(ingredients);
            return this;
        }

        public DefaultMealBuilder clearIngredients() {
            meal.ingredients.clear();
            return this;
        }

        public DefaultMeal build() {
            return meal;
        }

        @Override
        public String toString() {
            return "ClassicMealBuilder{" +
                    "meal=" + meal +
                    '}';
        }
    }
}
