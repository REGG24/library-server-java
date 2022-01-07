package com.regg.library.resource;

import com.regg.library.model.Author;
import com.regg.library.model.JsonUtil;
import com.regg.library.service.implementation.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthorResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorServiceImpl authorService;

    @Test
    public void getAuthors() throws Exception {
        Author author = new Author();
        author.setName("Kafka");
        author.setNationality("Russian");

        Author author2 = new Author();
        author2.setName("Kafka");
        author2.setNationality("Russian");

        List<Author> allAuthors = Arrays.asList(author, author2);

        given(authorService.list(5000)).willReturn(allAuthors);
        mockMvc.perform(
                        get("/author/list")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.authors", hasSize(2)))
                .andExpect(jsonPath("$.data.authors[0].name").value(author.getName()))
                .andExpect(jsonPath("$.data.authors[1].name").value(author2.getName()));
    }

    @Test
    public void saveAuthor() throws Exception {
        Author author = new Author();
        author.setName("Kafka");
        author.setNationality("Russian");

        given(authorService.create(author)).willReturn(author);
        mockMvc.perform(
                post("/client/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(author))
        ).andExpect(status().isOk());
    }

    @Test
    public void getAuthor() throws Exception {
        Author author = new Author();
        author.setName("Kafka");
        author.setNationality("Russian");

        given(authorService.get(1L)).willReturn(author);
        mockMvc.perform(
                        get("/author/get/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.author.name").value(author.getName()));
    }

    @Test
    public void updateAuthor() throws Exception {
        Author author = new Author();
        author.setName("Kafka");
        author.setNationality("Russian");

        given(authorService.update(author, 1L)).willReturn(author);
        mockMvc.perform(
                        put("/author/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(author))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAuthor() throws Exception {
        Author author = new Author();
        author.setName("Kafka");
        author.setNationality("Russian");

        given(authorService.delete(1L)).willReturn(true);
        mockMvc.perform(
                        delete("/author/delete/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(author))
                )
                .andExpect(status().isOk());
    }

   @Test
    public void getAuthorName() throws Exception {

   }

}