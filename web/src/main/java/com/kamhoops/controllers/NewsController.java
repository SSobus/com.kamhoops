package com.kamhoops.controllers;

import com.kamhoops.data.domain.News;
import com.kamhoops.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "list.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<News> getFrontNewsPage() {
        return newsService.findNewsPage(0, 3);
    }

}
