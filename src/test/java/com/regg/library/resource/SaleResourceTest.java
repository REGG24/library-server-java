package com.regg.library.resource;

import com.regg.library.model.JsonUtil;
import com.regg.library.model.Sale;
import com.regg.library.service.implementation.SaleServiceImpl;
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
class SaleResourceTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private SaleServiceImpl saleService;

    @Test
    public void getSales() throws Exception {
        Sale sale = new Sale();
        sale.setId_book(1L);
        sale.setId_employee(1l);
        sale.setId_client(1l);
        sale.setDate(new Date());

        Sale sale2 = new Sale();
        sale2.setId_book(1L);
        sale2.setId_employee(1l);
        sale2.setId_client(1l);
        sale2.setDate(new Date());

        List<Sale> allSales = Arrays.asList(sale, sale2);

        given(saleService.list(5000)).willReturn(allSales);
        mockMvc.perform(
                        get("/sale/list")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.sales", hasSize(2)))
                .andExpect(jsonPath("$.data.sales[0].id_book").value(sale.getId_book()))
                .andExpect(jsonPath("$.data.sales[1].id_book").value(sale2.getId_book()));
    }

    @Test
    public void saveSale() throws Exception {
        Sale sale = new Sale();
        sale.setId_book(1L);
        sale.setId_employee(1l);
        sale.setId_client(1l);
        sale.setDate(new Date());

        given(saleService.create(sale)).willReturn(sale);
        mockMvc.perform(
                post("/sale/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(sale))
        ).andExpect(status().isOk());
    }

    @Test
    public void getSale() throws Exception {
        Sale sale = new Sale();
        sale.setId_book(1L);
        sale.setId_employee(1l);
        sale.setId_client(1l);
        sale.setDate(new Date());

        given(saleService.get(1L)).willReturn(sale);
        mockMvc.perform(
                        get("/sale/get/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.sale.id_book").value(sale.getId_book()));
    }

    @Test
    public void updateSale() throws Exception {
        Sale sale = new Sale();
        sale.setId_book(1L);
        sale.setId_employee(1l);
        sale.setId_client(1l);
        sale.setDate(new Date());

        given(saleService.update(sale, 1L)).willReturn(sale);
        mockMvc.perform(
                        put("/sale/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(sale))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteSale() throws Exception {
        Sale sale = new Sale();
        sale.setId_book(1L);
        sale.setId_employee(1l);
        sale.setId_client(1l);
        sale.setDate(new Date());

        given(saleService.delete(1L)).willReturn(true);
        mockMvc.perform(
                        delete("/sale/delete/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(sale))
                )
                .andExpect(status().isOk());
    }

}