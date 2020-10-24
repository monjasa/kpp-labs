import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.monjasa.RestaurantManager;
import org.monjasa.model.Meal;
import org.monjasa.model.SpecialMeal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantManagerTest {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantManagerTest.class.getSimpleName());

    private List<Meal> meals;

    @BeforeEach
    void setUp() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Meal.class);
        Collection<? extends Meal> unmarshalledMeals = objectMapper.reader().forType(collectionType)
                .readValue(RestaurantManagerTest.class.getResourceAsStream("meals.json"));

        meals = new ArrayList<>(unmarshalledMeals);
    }

    @Test
    void shouldReturnUnsortedMeals() {

        RestaurantManager menu = new RestaurantManager(meals);
        List<Meal> meals = menu.getMealsUnsorted();

        assertThat(meals).isNotNull()
                .hasSize(6);

        logger.info("UNSORTED:");
        meals.stream().map(Meal::toString).forEach(logger::info);
    }

    @Test
    void shouldReturnMealsSortedByPriceInAscendingOrder() {

        RestaurantManager menu = new RestaurantManager(meals);
        List<Meal> meals = menu.getMealsSortedByPrice(false);

        assertThat(meals).isNotNull()
                .hasSize(6)
                .isSortedAccordingTo(Comparator.comparingDouble(Meal::getPrice));

        logger.info("BY PRICE ASC:");
        meals.stream().map(Meal::toString).forEach(logger::info);
    }

    @Test
    void shouldReturnMealsSortedByPriceInDescendingOrder() {

        RestaurantManager menu = new RestaurantManager(meals);
        List<Meal> meals = menu.getMealsSortedByPrice(true);

        assertThat(meals).isNotNull()
                .hasSize(6)
                .isSortedAccordingTo(Comparator.comparingDouble(Meal::getPrice).reversed());

        logger.info("BY PRICE DESC:");
        meals.stream().map(Meal::toString).forEach(logger::info);
    }

    @Test
    void shouldReturnMealsSortedByCaloriesInAscendingOrder() {

        RestaurantManager menu = new RestaurantManager(meals);
        List<Meal> meals = menu.getMealsSortedByCalories(false);

        assertThat(meals).isNotNull()
                .hasSize(6)
                .isSortedAccordingTo(Comparator.comparingDouble(Meal::getCalories));

        logger.info("BY CALORIES ASC:");
        meals.stream().map(Meal::toString).forEach(logger::info);
    }

    @Test
    void shouldReturnMealsSortedByCaloriesInDescendingOrder() {

        RestaurantManager menu = new RestaurantManager(meals);
        List<Meal> meals = menu.getMealsSortedByCalories(true);

        assertThat(meals).isNotNull()
                .hasSize(6)
                .isSortedAccordingTo(Comparator.comparingDouble(Meal::getCalories).reversed());

        logger.info("BY CALORIES DESC:");
        meals.stream().map(Meal::toString).forEach(logger::info);
    }

    @Test
    void shouldReturnMealsSortedByServingSizeInAscendingOrder() {

        RestaurantManager menu = new RestaurantManager(meals);
        List<Meal> meals = menu.getMealsSortedByServingSize(false);

        assertThat(meals).isNotNull()
                .hasSize(6)
                .isSortedAccordingTo(Comparator.comparingInt(Meal::getServingInGrams));

        logger.info("BY SERVING SIZE ASC:");
        meals.stream().map(Meal::toString).forEach(logger::info);
    }

    @Test
    void shouldReturnMealsSortedByServingSizeInDescendingOrder() {

        RestaurantManager menu = new RestaurantManager(meals);
        List<Meal> meals = menu.getMealsSortedByServingSize(true);

        assertThat(meals).isNotNull()
                .hasSize(6)
                .isSortedAccordingTo(Comparator.comparingInt(Meal::getServingInGrams).reversed());

        logger.info("BY SERVING SIZE DESC:");
        meals.stream().map(Meal::toString).forEach(logger::info);
    }

    @Test
    void shouldReturnMealsSortedByNameAlphabetically() {

        RestaurantManager menu = new RestaurantManager(meals);
        List<Meal> meals = menu.getMealsSortedByNameAlphabetically();

        assertThat(meals).isNotNull()
                .hasSize(6)
                .isSortedAccordingTo(Comparator.comparing(Meal::getName));

        logger.info("BY NAME:");
        meals.stream().map(Meal::toString).forEach(logger::info);
    }

    @Test
    void shouldReturnMealsFoundByCharacteristic() {

        RestaurantManager menu = new RestaurantManager(meals);
        String characteristic = "Vegetarian";
        List<Meal> meals = menu.getMealsByCharacteristic(characteristic);

        assertThat(meals).isNotNull()
                .hasSize(2)
                .allMatch(SpecialMeal.class::isInstance)
                .extracting("characteristics", Set.class).contains(Set.of(characteristic));

        logger.info(String.format("%s:", characteristic.toUpperCase()));
        meals.stream().map(Meal::toString).forEach(logger::info);
    }

    @Test
    void shouldReturnMealsFoundByCharacteristics() {

        RestaurantManager menu = new RestaurantManager(meals);
        Set<String> characteristics = Set.of("Spicy", "Vegetarian");
        List<Meal> meals = menu.getMealsByCharacteristics(characteristics);

        assertThat(meals).isNotNull()
                .hasSize(1)
                .allMatch(SpecialMeal.class::isInstance)
                .extracting("characteristics", Set.class).contains(characteristics);

        logger.info(String.format("%s:", characteristics.toString().toUpperCase()));
        meals.stream().map(Meal::toString).forEach(logger::info);
    }
}