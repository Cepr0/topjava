package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * @author Cepro
 *         23.10.16
 */
public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';
    
    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
               .andExpect(status().isOk())
               .andDo(print())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
               .andExpect(MATCHER.contentMatcher(MEAL1));
    }
    
    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
               .andDo(print())
               .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(MEALS.subList(0, 5), mealService.getAll(START_SEQ));
    }
    
    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
               .andExpect(status().isOk())
               .andDo(print())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Collection<Meal> meals = mealService.getAll(START_SEQ);
        MATCHER.assertCollectionEquals(MEALS, meals);

        List<MealWithExceed> mealsWithExceeds = MealsUtil.getWithExceeded(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        MATCHER_EX.assertCollectionEquals(MEALS_EX, mealsWithExceeds);
    }
    
    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        updated.setDescription("Updated description");
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, START_SEQ));
    }
    
    @Test
    public void testCreate() throws Exception {
        Meal created = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());
        
        Meal returned = MATCHER.fromJsonAction(action);
        created.setId(returned.getId());
        MATCHER.assertEquals(created, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(START_SEQ));
    }
    
    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter").param("startDate","2015-05-31").param("startTime","12:00"))
               .andExpect(status().isOk())
               .andDo(print());

        Collection<Meal> meals = mealService.getBetweenDates(LocalDate.of(2015, 5, 31), TimeUtil.MAX_DATE, START_SEQ);
        List<MealWithExceed> mealsWithExceeds = MealsUtil.getFilteredWithExceeded(meals, LocalTime.of(12, 0), LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        MATCHER_EX.assertCollectionEquals(Arrays.asList(MEAL_EX6, MEAL_EX5), mealsWithExceeds);
    }
}