package meal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IntoleranceFreeMeal extends Meal {

    private List<String> excludedSubstances;

    public IntoleranceFreeMeal() {
        super();
        this.excludedSubstances = new ArrayList<>();
    }

    public IntoleranceFreeMeal(
            String name,
            int servingInGrams,
            double calories,
            double price,
            List<String> ingredients,
            List<String> excludedSubstances
    ) {
        super(name, servingInGrams, calories, price, ingredients);
        this.excludedSubstances = excludedSubstances;
    }

    public static IntoleranceFreeMealBuilder builder() {
        return new IntoleranceFreeMealBuilder();
    }

    public List<String> getExcludedSubstances() {
        return excludedSubstances;
    }

    public void setExcludedSubstances(List<String> excludedSubstances) {
        this.excludedSubstances = excludedSubstances;
    }

    @Override
    public String toString() {
        return "IntoleranceFreeMeal{" +
                "excludedSubstances=" + excludedSubstances +
                ", name='" + name + '\'' +
                ", servingInGrams=" + servingInGrams +
                ", calories=" + calories +
                ", price=" + price +
                ", ingredients=" + ingredients +
                '}';
    }

    public static class IntoleranceFreeMealBuilder {

        private final IntoleranceFreeMeal meal;

        public IntoleranceFreeMealBuilder() {
            meal = new IntoleranceFreeMeal();
        }

        public IntoleranceFreeMealBuilder name(String name) {
            meal.name = name;
            return this;
        }

        public IntoleranceFreeMealBuilder servingInGrams(int servingInGrams) {
            meal.servingInGrams = servingInGrams;
            return this;
        }

        public IntoleranceFreeMealBuilder calories(double calories) {
            meal.calories = calories;
            return this;
        }

        public IntoleranceFreeMealBuilder price(double price) {
            meal.price = price;
            return this;
        }

        public IntoleranceFreeMealBuilder ingredient(String ingredient) {
            meal.ingredients.add(ingredient);
            return this;
        }

        public IntoleranceFreeMealBuilder ingredients(Collection<? extends String> ingredients) {
            meal.ingredients.addAll(ingredients);
            return this;
        }

        public IntoleranceFreeMealBuilder clearIngredients() {
            meal.ingredients.clear();
            return this;
        }

        public IntoleranceFreeMealBuilder excludedSubstance(String excludedSubstance) {
            meal.excludedSubstances.add(excludedSubstance);
            return this;
        }

        public IntoleranceFreeMealBuilder excludedSubstances(Collection<? extends String> excludedSubstances) {
            meal.excludedSubstances.addAll(excludedSubstances);
            return this;
        }

        public IntoleranceFreeMealBuilder clearExcludedSubstances() {
            meal.excludedSubstances.clear();
            return this;
        }

        public IntoleranceFreeMeal build() {
            return meal;
        }

        @Override
        public String toString() {
            return "IntoleranceFreeMealBuilder{" +
                    "meal=" + meal +
                    '}';
        }
    }
}
