package com.springweb.demo.test;

import com.springweb.demo.controller.HomeController;
import com.springweb.demo.controller.SpittleController;
import com.springweb.demo.model.Spittler;
import com.springweb.demo.repository.SpittleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeControllerTest {
    @Test
    public void testHome() {
        HomeController homeController = new HomeController();
        Assert.assertEquals("home", homeController.home());
    }

    @Test
    public void testMockHome() throws Exception {
        HomeController homeController = new HomeController();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.view().name("home"));
    }

    @Test
    public void splittlesTest() throws Exception {
        List<Spittler> spittles = createSplittles(20);
        SpittleRepository spittleRepository = Mockito.mock(SpittleRepository.class);
        Mockito.when(spittleRepository.findSpittles(Long.MAX_VALUE, 20))
                .thenReturn(spittles);

        SpittleController spittleController = new SpittleController(spittleRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(spittleController)
                .setSingleView(new InternalResourceView("/WEB-INF/views/spittle.jsp")).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/spittles"))
                .andExpect(MockMvcResultMatchers.view().name("spittle"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"));

    }

    private List<Spittler> createSplittles(int i) {
        List<Spittler> spittles = new ArrayList<>();
        for (int j = 1; j < i ; j++) {
            Spittler spittle = Spittler.builder()
                    .id((long) j)
                    .message("spittle No:" + j)
                    .time(new Date())
                    .latitude(1112.334D)
                    .longtitude(2123.1235D)
                    .build();
            spittles.add(spittle);
        }
        return spittles;
    }
}
