package kharoud.spring.demo.Controllers;

import kharoud.spring.demo.CustomerApplication;
import kharoud.spring.demo.Model.*;
import kharoud.spring.demo.Services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void testAddAndRemoveCartToUserWithCartDetails() throws Exception {
        User user = new User();

        user.setUsername("someusername");
        user.setPassword("myPassword");

        user.setCart(new Cart());

        Product product1 = new Product();
        Product product2 = new Product();

        List<Product> storedProducts = new ArrayList<>();
        storedProducts.add(product1);
        storedProducts.add(product2);

        CartDetail cartItemOne = new CartDetail();
        cartItemOne.setProduct(storedProducts.get(0));
        user.getCart().addCartDetail(cartItemOne);

        CartDetail cartItemTwo = new CartDetail();
        cartItemTwo.setProduct(storedProducts.get(1));
        user.getCart().addCartDetail(cartItemTwo);

        User savedUser = userService.saveOrUpdateCustomer(user);

        assert savedUser.getCart().getCartDetails().size() == 2;

        savedUser.getCart().removeCartDetail(savedUser.getCart().getCartDetails().get(0));

        userService.saveOrUpdateCustomer(savedUser);

        assert savedUser.getCart().getCartDetails().size() == 1;
    }
}
