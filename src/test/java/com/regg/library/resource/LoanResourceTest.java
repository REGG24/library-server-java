package com.regg.library.resource;

import com.regg.library.model.JsonUtil;
import com.regg.library.model.Loan;
import com.regg.library.service.implementation.LoanServiceImpl;
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
class LoanResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanServiceImpl loanService;

    @Test
    public void getLoans() throws Exception {
        Loan loan = new Loan();
        loan.setId_book(1L);
        loan.setId_employee(1l);
        loan.setId_client(1l);
        loan.setDate(new Date());

        Loan loan2 = new Loan();
        loan2.setId_book(1L);
        loan2.setId_employee(1l);
        loan2.setId_client(1l);
        loan2.setDate(new Date());

        List<Loan> allLoans = Arrays.asList(loan, loan2);

        given(loanService.list(5000)).willReturn(allLoans);
        mockMvc.perform(
                        get("/loan/list")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.loans", hasSize(2)))
                .andExpect(jsonPath("$.data.loans[0].id_book").value(loan.getId_book()))
                .andExpect(jsonPath("$.data.loans[1].id_book").value(loan2.getId_book()));
    }

    @Test
    public void saveLoan() throws Exception {
        Loan loan = new Loan();
        loan.setId_book(1L);
        loan.setId_employee(1l);
        loan.setId_client(1l);
        loan.setDate(new Date());

        given(loanService.create(loan)).willReturn(loan);
        mockMvc.perform(
                post("/loan/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(loan))
        ).andExpect(status().isOk());
    }

    @Test
    public void getLoan() throws Exception {
        Loan loan = new Loan();
        loan.setId_book(1L);
        loan.setId_employee(1l);
        loan.setId_client(1l);
        loan.setDate(new Date());

        given(loanService.get(1L)).willReturn(loan);
        mockMvc.perform(
                        get("/loan/get/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.loan.id_book").value(loan.getId_book()));
    }

    @Test
    public void updateLoan() throws Exception {
        Loan loan = new Loan();
        loan.setId_book(1L);
        loan.setId_employee(1l);
        loan.setId_client(1l);
        loan.setDate(new Date());

        given(loanService.update(loan, 1L)).willReturn(loan);
        mockMvc.perform(
                        put("/loan/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(loan))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteLoan() throws Exception {
        Loan loan = new Loan();
        loan.setId_book(1L);
        loan.setId_employee(1l);
        loan.setId_client(1l);
        loan.setDate(new Date());

        given(loanService.delete(1L)).willReturn(true);
        mockMvc.perform(
                        delete("/loan/delete/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(loan))
                )
                .andExpect(status().isOk());
    }
}