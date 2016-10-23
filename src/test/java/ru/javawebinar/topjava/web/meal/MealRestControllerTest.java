package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;

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
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        ;
        MATCHER.assertCollectionEquals(MEALS, mealService.getAll(START_SEQ));
    }
    
    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        updated.setDescription("Updated description");
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))).andExpect(status().isOk());
        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, START_SEQ));
    }
    
    @Test
    public void testCreate() throws Exception {
        Meal expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());
        
        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());
        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(START_SEQ));
    }
    
    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDate=2015-05-31&startTime=12:00"))
               .andExpect(status().isOk())
               .andDo(print());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5),
                mealService.getBetweenDateTimes(
                        LocalDateTime.of(2015, 5, 31, 12, 0),
                        LocalDateTime.of(2015, 5, 31, 23, 59),
                        START_SEQ));
    }
    
}