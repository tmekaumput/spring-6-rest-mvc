package com.example.springframework.spring6rest.mvc.spring6restmvc.controller;
/*
 * @Author tmekaumput
 * @Date 5/4/24 5:13 pm
 *
 */

import com.example.springframework.spring6rest.mvc.spring6restmvc.model.Customer;
import com.example.springframework.spring6rest.mvc.spring6restmvc.services.CustomerService;
import com.example.springframework.spring6rest.mvc.spring6restmvc.services.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    ArgumentCaptor<Integer> argumentCaptorId;

    @Captor
    ArgumentCaptor<Customer> argumentCaptorCustomer;

    @Test
    void createCustomer() throws Exception {
        Customer customer = Customer.builder()
                .id(1)
                .customerName("New customer")
                .version(1).build();

        given(customerService.newCustomer(customer)).willReturn(customer);

        mockMvc.perform(post("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

        verify(customerService).newCustomer(argumentCaptorCustomer.capture());
        assertThat(customer.getId()).isEqualTo(argumentCaptorCustomer.getValue().getId());
    }

    @Test
    void updateCustomer() throws Exception {
        Customer customer = Customer.builder()
                .id(1)
                .customerName("Updated customer")
                .version(2).build();

        mockMvc.perform(put("/api/v1/customer/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(customer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomer(argumentCaptorId.capture(), argumentCaptorCustomer.capture());
        assertThat(customer.getId()).isEqualTo(argumentCaptorId.getValue());
        assertThat(customer.getCustomerName()).isEqualTo(argumentCaptorCustomer.getValue().getCustomerName());
    }

    @Test
    void patchCustomer() throws Exception {
        Customer customer = Customer.builder()
                .id(1)
                .customerName("Patched customer")
                .version(2).build();

        mockMvc.perform(patch("/api/v1/customer/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(customer)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomer(argumentCaptorId.capture(), argumentCaptorCustomer.capture());
        assertThat(customer.getId()).isEqualTo(argumentCaptorId.getValue());
        assertThat(customer.getCustomerName()).isEqualTo(argumentCaptorCustomer.getValue().getCustomerName());
    }

    @Test
    void getCustomerById() throws Exception{

        Customer customer = Customer.builder()
                .id(1)
                .customerName("New customer")
                .version(1).build();

        given(customerService.getCustomerById(customer.getId())).willReturn(customer);

        mockMvc.perform(get("/api/v1/customer/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {

        given(customerService.getCustomerById(any(Integer.class))).willThrow(NotFoundException.class);

        mockMvc.perform(get("/api/v1/customer/" + 100)).andExpect(status().isNotFound());
    }

    @Test
    void listCustomers() throws Exception{

        List<Customer> customers = new ArrayList<>();

        Customer c1 = Customer.builder()
                .id(1)
                .customerName("C1")
                .version(1).build();

        Customer c2 = Customer.builder()
                .id(1)
                .customerName("C2")
                .version(1).build();

        Customer c3 = Customer.builder()
                .id(1)
                .customerName("C3")
                .version(1).build();

        customers.add(c1);
        customers.add(c2);
        customers.add(c3);

        given(customerService.listCustomers()).willReturn(customers);

        mockMvc.perform(get("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(customers.size())));


    }

    @Test
    void deleteCustomer() throws Exception {
        Customer customer = Customer.builder().id(1).build();

        mockMvc.perform(delete("/api/v1/customer/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomer(argumentCaptorId.capture());

        assertThat(customer.getId()).isEqualTo(argumentCaptorId.getValue());
    }
}
