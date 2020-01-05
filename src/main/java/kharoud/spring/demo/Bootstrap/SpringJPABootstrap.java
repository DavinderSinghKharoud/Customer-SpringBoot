package kharoud.spring.demo.Bootstrap;

import kharoud.spring.demo.Model.Customer;
import kharoud.spring.demo.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CustomerService customerService;



    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        loadCustomers();

    }

    public void loadCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Micheal");
        customer1.setLastName("Weston");
        customer1.setAddress("1 Main St");
        customer1.setCity("Miami");
        customer1.setState("Florida");
        customer1.setZipCode("33101");
        customer1.setEmail("micheal@burnnotice.com");
        customer1.setPhoneNumber("305.333.0101");
        customerService.saveOrUpdateCustomer(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Fiona");
        customer2.setLastName("Glenanne");
        customer2.setAddress("1 Key Biscane Ave");
        customer2.setCity("Miami");
        customer2.setState("Florida");
        customer2.setZipCode("33101");
        customer2.setEmail("fiona@burnnotice.com");
        customer2.setPhoneNumber("305.323.0233");
        customerService.saveOrUpdateCustomer(customer2);

    }
}