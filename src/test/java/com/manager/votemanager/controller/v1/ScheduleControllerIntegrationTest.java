package com.manager.votemanager.controller.v1;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ScheduleControllerIntegrationTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    ScheduleService scheduleService;
//
//    @Test
//    void shouldGetScheduleWhenIHaveAValidName() throws Exception {
//        final String name = "Test schedule";
//        final String response = getJsonAsString("schedule.json");
//
//        doReturn(mockScheduleResult()).when(scheduleService).getByName(name);
//
//        final MvcResult result = mockMvc.perform(get("/v1/schedule")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .param("name", name))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        final String actualResponse = result.getResponse().getContentAsString();
//
//        JSONAssert.assertEquals(response, actualResponse, false);
//    }
//
//    @Test
//    void getScheduleWhenIHaveAValidId() throws Exception {
//        final Long id = 1L;
//
//        doReturn(mockScheduleResult()).when(scheduleService).getById(id);
//
//        this.mockMvc.perform(get("/v1/schedule/{id}", 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void deleteScheduleWhenIHaveAValidId() throws Exception {
//
//        doReturn(mockScheduleResult()).when(scheduleService).deleteWithId(1L);
//
//        mockMvc.perform(delete("/v1/schedule/{id}", "1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void shouldStatus201WhenCreateScheduleValid() throws Exception {
//        final String request = getJsonAsString("schedule.json");
//
//        Mockito.when(scheduleService.createSchedule(Mockito.any())).thenReturn(mockScheduleResult());
//
//        mockMvc.perform(post("/v1/schedule/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(request))
//                .andExpect(status().isCreated());
//    }
//
//    private static Schedule mockScheduleResult() {
//
//        return new Schedule(1L, "Test schedule", "test if schedulo method post works",
//                StatusEnum.OPEN, "DRAW", 0, 0, 0, 0.00,0.00);
//    }
}
