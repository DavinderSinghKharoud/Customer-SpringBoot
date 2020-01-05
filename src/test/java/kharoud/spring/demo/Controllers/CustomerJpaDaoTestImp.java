package kharoud.spring.demo.Controllers;

import kharoud.spring.demo.CustomerApplication;
import kharoud.spring.demo.Model.Customer;
import kharoud.spring.demo.Model.User;
import kharoud.spring.demo.Services.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomerApplication.class)
@ActiveProfiles("jpadao")
public class CustomerJpaDaoTestImp {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Test
    public void testList() throws Exception {
        List<Customer> customers = (List<Customer>) customerService.listAllCustomes();

        assert customers.size() == 2;

    }

    @Test
    public void testSaveWithUser() {

        Customer customer = new Customer();
        User user = new User();
        user.setUsername("This is my user name");
        user.setPassword("MyAwesomePassword");
        customer.setUser(user);

        Customer savedCustomer = customerService.saveOrUpdateCustomer(customer);

        assert savedCustomer.getUser().getId() != null;
    }
}
