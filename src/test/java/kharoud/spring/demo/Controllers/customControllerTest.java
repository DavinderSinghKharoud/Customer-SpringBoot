package kharoud.spring.demo.Controllers;

import kharoud.spring.demo.Model.Customer;
import kharoud.spring.demo.Services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.internal.configuration.MockAnnotationProcessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    public void testEdit() throws Exception{

        Integer id = 1;
        //should not call service before
        when(customerService.getCustomerById(id)).thenReturn(new Customer());

        mockMvc.perform(get("/customers/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerForm"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }


    @Test
    public void testSaveOrUpdate() throws Exception{
        Integer id = 1;
        String firstName = "sunny";
        String lastName = "kharoud";
        String email = "test.com";
        String phoneNumber = "5147777867";
        String address = "Montreal";
        String city = "Montrea";
        String state = "Quebec";
        String zipCode = "H9B2L4";

        Customer returnCustomer = new Customer();
        returnCustomer.setId(id);
        returnCustomer.setFirstName(firstName);
        returnCustomer.setLastName(lastName);
        returnCustomer.setEmail(email);
        returnCustomer.setPhoneNumber(phoneNumber);
        returnCustomer.setAddress(address);
        returnCustomer.setCity(city);
        returnCustomer.setState(state);
        returnCustomer.setState(zipCode);

        when(customerService.saveOrUpdateCustomer(ArgumentMatchers.<Customer>any(Customer.class))).thenReturn(returnCustomer);

        mockMvc.perform(post("/customers")
                .param("id", "1")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("email", email)
                .param("phoneNumber", phoneNumber)
                .param("address", address)
                .param("city", city)
                .param("state", state)
                .param("zipCode", zipCode))

                .andExpect(status().is3xxRedirection());

        ArgumentCaptor<Customer> boundProduct = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).saveOrUpdateCustomer(boundProduct.capture());

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(firstName, boundProduct.getValue().getFirstName());
        assertEquals(lastName, boundProduct.getValue().getLastName());
        assertEquals(email, boundProduct.getValue().getEmail());
        assertEquals(phoneNumber, boundProduct.getValue().getPhoneNumber());
        assertEquals(address, boundProduct.getValue().getAddress());
        assertEquals(city, boundProduct.getValue().getCity());
        assertEquals(state, boundProduct.getValue().getState());
        assertEquals(zipCode, boundProduct.getValue().getZipCode());

    }

}
