package com.regg.library.resource;

import com.regg.library.model.Client;
import com.regg.library.model.JsonUtil;
import com.regg.library.service.implementation.ClientServiceImpl;
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
class ClientResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientServiceImpl clientService;

    @Test
    public void getClients() throws Exception {
        Client client = new Client();
        client.setName("Norberto");
        client.setPhone(3418787464L);
        client.setAddress("Guzman");

        Client client2 = new Client();
        client.setName("Norberto2");
        client.setPhone(3418787460L);
        client.setAddress("Guzman2");

        List<Client> allClients = Arrays.asList(client, client2);

        given(clientService.list()).willReturn(allClients);
        mockMvc.perform(
                        get("/client/list")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.clients", hasSize(2)))
                .andExpect(jsonPath("$.data.clients[0].name").value(client.getName()))
                .andExpect(jsonPath("$.data.clients[1].name").value(client2.getName()));
    }

    @Test
    public void saveClient() throws Exception {
        Client client = new Client();
        client.setName("Norberto");
        client.setPhone(3418787464L);
        client.setAddress("Guzman");

        given(clientService.create(client)).willReturn(client);
        mockMvc.perform(
                post("/client/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(client))
        ).andExpect(status().isOk());
    }

    @Test
    public void getClient() throws Exception {
        Client client = new Client();
        client.setName("Norberto");
        client.setPhone(3418787464L);
        client.setAddress("Guzman");

        given(clientService.get(1L)).willReturn(client);
        mockMvc.perform(
                        get("/client/get/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.client.name").value(client.getName()));
    }

    @Test
    public void updateClient() throws Exception {
        Client client = new Client();
        client.setName("Norberto");
        client.setPhone(3418787464L);
        client.setAddress("Guzman");

        given(clientService.update(client, 1L)).willReturn(client);
        mockMvc.perform(
                        put("/client/update/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(client))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteClient() throws Exception {
        Client client = new Client();
        client.setName("Norberto");
        client.setPhone(3418787464L);
        client.setAddress("Guzman");

        given(clientService.delete(1L)).willReturn(true);
        mockMvc.perform(
                        delete("/client/delete/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonUtil.toJson(client))
                )
                .andExpect(status().isOk());
    }

}