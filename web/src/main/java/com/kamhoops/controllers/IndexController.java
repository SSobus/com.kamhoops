package com.kamhoops.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getIndex(ModelAndView mv) {
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping(value = "homepage", method = RequestMethod.GET)
    public ModelAndView getHomepage(ModelAndView mv) {
        mv.setViewName("homepage");
        return mv;
    }

    @RequestMapping(value = "schedule", method = RequestMethod.GET)
    public ModelAndView getSchedule(ModelAndView mv) {
        mv.setViewName("schedule");
        return mv;
    }

}
