package kharoud.spring.demo.Controllers;

import kharoud.spring.demo.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class customerController {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService){
         this.customerService = customerService;
    }


    @RequestMapping("/customers")
    public String listProducts(Model model){
        model.addAttribute("customers", customerService.listAllCustomes());
        return "customers";
    }
}
