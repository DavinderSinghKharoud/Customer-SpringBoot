package kharoud.spring.demo.Services;

import kharoud.spring.demo.Model.User;

import java.util.List;

public interface UserService {
    List<?> listAllCustomes();

    User getCustomerById(Integer id);

    User saveOrUpdateCustomer(User domainObject);

    void deleteCustomer(Integer id);
}
