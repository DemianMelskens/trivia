package nl.demian.trivia.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.demian.trivia.domain.dtos.AnswerDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TriviaResourceIT {

    private static final ObjectMapper mapper = createObjectMapper();

    @Autowired
    private WebApplicationContext context;
    private MockMvc restMockMvc;

    private static ObjectMapper createObjectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @BeforeEach
    public void setup() {
        this.restMockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    void getQuestions() throws Exception {
        this.restMockMvc.perform(get("/trivia/questions"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$.[*].category").exists())
                .andExpect(jsonPath("$.[*].difficulty").exists())
                .andExpect(jsonPath("$.[*].question").exists())
                .andExpect(jsonPath("$.[*].options").isArray());
    }

    @Test
    void checkAnswer() throws Exception {
        MvcResult response = this.restMockMvc.perform(get("/trivia/questions"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$.[*].category").exists())
                .andExpect(jsonPath("$.[*].difficulty").exists())
                .andExpect(jsonPath("$.[*].question").exists())
                .andExpect(jsonPath("$.[*].options").isArray())
                .andReturn();

        final JSONArray responseJson = new JSONArray(response.getResponse().getContentAsString());
        final JSONObject json = responseJson.getJSONObject(0);

        final String question = json.getString("question");
        final String answer = json.getJSONArray("options").getString(0);

        final MvcResult result = this.restMockMvc.perform(
                post("/trivia/checkanswer")
                        .content(mapper.writeValueAsBytes(new AnswerDTO(question, answer)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final String content = result.getResponse().getContentAsString();

        assertThat(content.equals("true") || content.equals("false")).isTrue();
    }
}
