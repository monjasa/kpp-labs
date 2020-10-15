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

    public static ClassicMealBuilder builder() {
        return new ClassicMealBuilder();
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

    public static class ClassicMealBuilder {

        private final DefaultMeal meal;

        public ClassicMealBuilder() {
            meal = new DefaultMeal();
        }

        public ClassicMealBuilder name(String name) {
            meal.name = name;
            return this;
        }

        public ClassicMealBuilder servingInGrams(int servingInGrams) {
            meal.servingInGrams = servingInGrams;
            return this;
        }

        public ClassicMealBuilder calories(double calories) {
            meal.calories = calories;
            return this;
        }

        public ClassicMealBuilder price(double price) {
            meal.price = price;
            return this;
        }

        public ClassicMealBuilder ingredient(String ingredient) {
            meal.ingredients.add(ingredient);
            return this;
        }

        public ClassicMealBuilder ingredients(Collection<? extends String> ingredients) {
            meal.ingredients.addAll(ingredients);
            return this;
        }

        public ClassicMealBuilder clearIngredients() {
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
