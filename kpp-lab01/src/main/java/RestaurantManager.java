import meal.DefaultMeal;
import meal.Meal;
import meal.Meal.MealComparatorByPrice;
import meal.SpecialMeal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RestaurantManager {

    private List<Meal> meals;

    public RestaurantManager(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMealsUnsorted() {
        return new ArrayList<>(meals);
    }

    public List<Meal> getMealsSortedByCalories(boolean inDescendingOrder) {

        Meal proxyMeal = new DefaultMeal();

        var comparator = inDescendingOrder ?
                proxyMeal.new MealComparatorByCalories().reversed() :
                proxyMeal.new MealComparatorByCalories();

        return meals.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Meal> getMealsSortedByPrice(boolean inDescendingOrder) {

        var comparator = inDescendingOrder ?
                new MealComparatorByPrice().reversed() :
                new MealComparatorByPrice();

        return meals.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Meal> getMealsSortedByServingSize(boolean inDescendingOrder) {
        return meals.stream()
                .sorted(new Comparator<Meal>() {
                    @Override
                    public int compare(Meal o1, Meal o2) {
                        if (inDescendingOrder) return Integer.compare(o2.getServingInGrams(), o1.getServingInGrams());
                        else return Integer.compare(o1.getServingInGrams(), o2.getServingInGrams());
                    }
                })
                .collect(Collectors.toList());
    }

    public List<Meal> getMealsSortedByNameAlphabetically() {
        return meals.stream()
                .sorted(Comparator.comparing(Meal::getName))
                .collect(Collectors.toList());
    }

    public List<Meal> getMealsByCharacteristic(String characteristic) {
        return meals.stream()
                .filter(SpecialMeal.class::isInstance)
                .map(SpecialMeal.class::cast)
                .filter(meal -> meal.getCharacteristics().contains(characteristic))
                .collect(Collectors.toList());
    }

    public List<Meal> getMealsByCharacteristics(Set<String> characteristic) {
        return meals.stream()
                .filter(SpecialMeal.class::isInstance)
                .map(SpecialMeal.class::cast)
                .filter(meal -> meal.getCharacteristics().containsAll(characteristic))
                .collect(Collectors.toList());
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
