package kharoud.spring.demo.Services;

import kharoud.spring.demo.Model.Customer;

import java.util.List;

public interface CustomerService {

     List<Customer> listAllCustomes();

     Customer saveOrUpdateCustomer(Customer customer);

     Customer getCustomerById(Integer id);

     void deleteCustomer(Integer id);
}
