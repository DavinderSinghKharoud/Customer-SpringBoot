package kharoud.spring.demo.Controllers;

import kharoud.spring.demo.Model.Customer;
import kharoud.spring.demo.Services.CustomerService;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class customerController {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService){
         this.customerService = customerService;
    }


    @RequestMapping("/customers/list")
    public String listProducts(Model model){
        model.addAttribute("customers", customerService.listAllCustomes());
        return "customers";
    }

    @RequestMapping("/customers/new")
    public String newCustomer(Model model){
        model.addAttribute("customer", new Customer());
        return "customerForm";
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public String saveOrUpdateCustomer(Customer customer){

        Customer savedCustomer = customerService.saveOrUpdateCustomer(customer);
        return "redirect:/customers/list";
    }

    @RequestMapping("/customers/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        model.addAttribute("customer", customerService.getCustomerById(id));
        return "customerForm";
    }

    @RequestMapping("/customers/delete/{id}")
    public String delete(@PathVariable Integer id){
        customerService.deleteCustomer(id);
        return "redirect:/customers/list";
    }
}
