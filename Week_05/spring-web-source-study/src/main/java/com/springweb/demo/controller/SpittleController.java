package com.springweb.demo.controller;

import com.springweb.demo.model.Spittler;
import com.springweb.demo.repository.SpittleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

    private SpittleRepository spittleRepository;
    @Autowired
    public SpittleController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    //视图类型推断，Map
    public String spittle(Model model) {
        Spittler spittler = spittleRepository.findSpittles(Long.MAX_VALUE, 20).get(0);
        model.addAttribute(spittler);
        return "spittle";
    }
}
