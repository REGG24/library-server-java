package com.regg.library.resource;

import com.regg.library.model.Return;
import com.regg.library.model.JsonUtil;
import com.regg.library.service.implementation.ReturnServiceImpl;
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
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ReturnResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReturnServiceImpl returnService;

    @Test
    public void getReturns() throws Exception {
        Return return_ = new Return();
        return_.setId_loan(1L);
        //return_.setDate(new Date());

        Return return_2 = new Return();
        return_2.setId_loan(1L);
        //.setDate(new Date());

        List<Return> allReturns = Arrays.asList(return_, return_2);

        given(returnService.list(5000)).willReturn(allReturns);
        mockMvc.perform(
                        get("/returns/list")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.returns", hasSize(2)))
                .andExpect(jsonPath("$.data.returns[0].id_loan").value(return_.getId_loan()));
    }

    @Test
    public void saveReturn() throws Exception {
        Return return_ = new Return();
        return_.setId_loan(1L);
        return_.setDate(new Date());

        given(returnService.create(return_)).willReturn(return_);
        mockMvc.perform(
                post("/returns/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(return_))
        ).andExpect(status().isOk());
    }

    @Test
    public void getReturn() throws Exception {
        Return return_ = new Return();
        return_.setId_loan(1L);
        return_.setDate(new Date());

        given(returnService.get(1L)).willReturn(return_);
        mockMvc.perform(
                        get("/returns/get/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.return.id_loan").value(return_.getId_loan()));
    }

    @Test
    public void updateReturn() throws Exception {
        Return return_ = new Return();
        return_.setId_loan(1L);
        return_.setDate(new Date());

        given(returnService.update(return_, 1L)).willReturn(return_);
        mockMvc.perform(
                        put("/returns/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(return_))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteReturn() throws Exception {
        Return return_ = new Return();
        return_.setId_loan(1L);
        return_.setDate(new Date());

        given(returnService.delete(1L)).willReturn(true);
        mockMvc.perform(
                        delete("/returns/delete/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(return_))
                )
                .andExpect(status().isOk());
    }

}