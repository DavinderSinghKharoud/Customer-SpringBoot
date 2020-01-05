package kharoud.spring.demo.Controllers;

import kharoud.spring.demo.CustomerApplication;
import kharoud.spring.demo.Model.Customer;
import kharoud.spring.demo.Model.User;
import kharoud.spring.demo.Services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomerApplication.class)
@ActiveProfiles("jpadao")
public class UserServiceJpaDaoTest {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void testUser() throws Exception {
        User user = new User();

        user.setUsername("someusername");
        user.setPassword("myPassword");

        User savedUser = userService.saveOrUpdateCustomer(user);

        assert savedUser.getId() != null;
        assert savedUser.getEncryptedPassword() != null;

        System.out.println("Encrypted Password");
        System.out.println(savedUser.getEncryptedPassword());

    }


    @Test
    public void testUserWithCustomer() throws Exception {

        User user = new User();

        user.setUsername("username");
        user.setPassword("password");

        Customer customer = new Customer();
        customer.setFirstName("Sunny");
        customer.setLastName("Kharoud");

        user.setCustomer(customer);

        User savedUser = userService.saveOrUpdateCustomer(user);

        assert savedUser.getId() != null;
        assert savedUser.getVersion() != null;
        assert savedUser.getCustomer() != null;
        assert savedUser.getCustomer().getId() != null;

    }
}
