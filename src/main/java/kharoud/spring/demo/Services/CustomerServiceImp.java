package kharoud.spring.demo.Services;

import kharoud.spring.demo.Model.Customer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImp implements CustomerService {

    private Map<Integer,Customer> customers;

    @Override
    public List<Customer> listAllCustomes() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public Customer saveOrUpdateCustomer(Customer customer) {
        if(customer!=null){
           if(customer.getId() == null){
               customer.setId(getNextKey());
           }

           customers.put(customer.getId(), customer);
           return customer;
        }else{
              throw new RuntimeException("Customer can't be null");
        }
    }

    private Integer getNextKey(){
        return Collections.max(customers.keySet()) + 1;
    }

    public CustomerServiceImp(){
        loadCustomers();
    }

    private void loadCustomers() {
        customers = new HashMap<>();

        Customer customer1 = new Customer();
        customer1.setId(1);
        customer1.setFirstName("Sunny");
        customer1.setLastName("Kharoud");
        customer1.setEmail("Dskharoud2@gmail.com");
        customer1.setPhoneNumber("514-777-7867");
        customer1.setAddress("India");
        customer1.setCity("Patiala");
        customer1.setState("Punjab");
        customer1.setZipCode("147001");

        customers.put(1, customer1);
    }




}
