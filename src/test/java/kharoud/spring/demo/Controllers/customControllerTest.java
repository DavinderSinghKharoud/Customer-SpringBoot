package kharoud.spring.demo.Controllers;

import kharoud.spring.demo.Model.Customer;
import kharoud.spring.demo.Services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.MockAnnotationProcessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class customControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private customerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testCustomerList() throws Exception{

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        customers.add(new Customer());
        customers.add(new Customer());

        //specific Mockito interaction, tell stub to return list of products
        when(customerService.listAllCustomes()).thenReturn((List) customers);

        mockMvc.perform(get("/customers/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers"))
                .andExpect(model().attribute("customers", hasSize(3)));
    }

    @Test
    public void testNewCustomer() throws Exception{

        //should not call service before
        verifyZeroInteractions(customerService);

        mockMvc.perform(get("/customers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerForm"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }


    @Test
    public void deleteTest() throws Exception{

        mockMvc.perform(get("/customers/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/list"));

        verify(customerService, times(1)).deleteCustomer(1);
    }
}
