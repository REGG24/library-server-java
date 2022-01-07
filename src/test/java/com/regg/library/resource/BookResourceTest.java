package com.regg.library.resource;

import com.regg.library.model.Book;
import com.regg.library.model.JsonUtil;
import com.regg.library.service.implementation.BookServiceImpl;
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
class BookResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;

    @Test
    public void getBooks() throws Exception {
        Book book = new Book();
        book.setName("The art of love");
        book.setDescription("You must love you first before love anyone else");
        book.setPrice(155.00);
        book.setStock(5);
        book.setId_author(1L);

        Book book2 = new Book();
        book2.setName("The art of love2");
        book2.setDescription("You must love you first before love anyone else2");
        book2.setPrice(155.00);
        book2.setStock(5);
        book2.setId_author(1L);

        List<Book> allBooks = Arrays.asList(book, book2);

        given(bookService.list(5000)).willReturn(allBooks);
        mockMvc.perform(
                        get("/book/list")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.books", hasSize(2)))
                .andExpect(jsonPath("$.data.books[0].name").value(book.getName()))
                .andExpect(jsonPath("$.data.books[1].name").value(book2.getName()));
    }

    @Test
    public void saveBook() throws Exception {
        Book book = new Book();
        book.setName("The art of love");
        book.setDescription("You must love you first before love anyone else");
        book.setPrice(155.00);
        book.setStock(5);
        book.setId_author(1L);

        given(bookService.create(book)).willReturn(book);
        mockMvc.perform(
                post("/client/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(book))
        ).andExpect(status().isOk());
    }

    @Test
    public void getBook() throws Exception {
        Book book = new Book();
        book.setName("The art of love");
        book.setDescription("You must love you first before love anyone else");
        book.setPrice(155.00);
        book.setStock(5);
        book.setId_author(1L);

        given(bookService.get(1L)).willReturn(book);
        mockMvc.perform(
                        get("/book/get/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.book.name").value(book.getName()));
    }

    @Test
    public void updateBook() throws Exception {
        Book book = new Book();
        book.setName("The art of love");
        book.setDescription("You must love you first before love anyone else");
        book.setPrice(155.00);
        book.setStock(5);
        book.setId_author(1L);

        given(bookService.update(book, 1L)).willReturn(book);
        mockMvc.perform(
                        put("/book/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(book))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBook() throws Exception {
        Book book = new Book();
        book.setName("The art of love");
        book.setDescription("You must love you first before love anyone else");
        book.setPrice(155.00);
        book.setStock(5);
        book.setId_author(1L);

        given(bookService.delete(1L)).willReturn(true);
        mockMvc.perform(
                        delete("/book/delete/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(book))
                )
                .andExpect(status().isOk());
    }
}