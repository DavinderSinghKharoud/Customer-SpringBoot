package kharoud.spring.demo.Controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class indexControllerTest {

    private MockMvc mockMvc;

    private  IndexController indexController;

    @Before
    public void Setup(){
        indexController = new IndexController();
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    public void testIndex() throws Exception{
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}
