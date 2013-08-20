package com.kamhoops.service;


import com.kamhoops.data.domain.News;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.NewsRepository;
import com.kamhoops.exceptions.EntityValidationException;
import com.kamhoops.services.NewsService;
import com.kamhoops.support.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import static org.junit.Assert.assertTrue;

public class NewsServiceTests extends BaseTest {

    @Autowired
    private NewsService newsService;

    private NewsRepository newsRepository;

    @Before
    public void init() {
        dataGenerator.deleteAll();
        newsRepository = newsService.getRepository();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingWithANullObject() throws EntityValidationException, EntityNotFoundException {
        newsService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenCreatingWithAnObjectWithAnId() throws EntityValidationException, EntityNotFoundException {
        News news = new News();
        news.setId(99L);

        newsService.create(news);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenCreatingWithANullTitle() throws EntityValidationException, EntityNotFoundException {
        News news = new News();
        news.setTitle(null);
        news.setContent("Content");

        newsService.create(news);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenCreatingWithABlankTitle() throws EntityValidationException, EntityNotFoundException {
        News news = new News();
        news.setTitle(" ");
        news.setContent("Content");

        newsService.create(news);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenCreatingWithNullContent() throws EntityValidationException, EntityNotFoundException {
        News news = new News();
        news.setTitle("Title");
        news.setContent(null);

        newsService.create(news);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenCreatingWithBlankContent() throws EntityValidationException, EntityNotFoundException {
        News news = new News();
        news.setTitle("Title");
        news.setContent(" ");

        newsService.create(news);
    }

    @Test
    public void shouldCreateNews() throws EntityValidationException, EntityNotFoundException {
        News news = new News();

        news.setTitle("Title");
        news.setContent("Content");

        newsService.create(news);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingANullObject() throws EntityValidationException, EntityNotFoundException {
        newsService.update(null);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityValidationExceptionWhenUpdatingAnObjectThatDoesNotExist() throws EntityValidationException, EntityNotFoundException {
        News news = dataGenerator.getTestNews();
        news.setId(99L);
        newsService.update(news);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenUpdatingWithANullTitle() throws EntityValidationException, EntityNotFoundException {
        News news = dataGenerator.createTestNews();

        news.setTitle(null);

        newsService.update(news);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenUpdatingWithABlankTitle() throws EntityValidationException, EntityNotFoundException {
        News news = dataGenerator.createTestNews();
        news.setTitle(" ");

        newsService.update(news);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenUpdatingWithNullContent() throws EntityValidationException, EntityNotFoundException {
        News news = dataGenerator.createTestNews();
        news.setContent(null);

        newsService.update(news);
    }

    @Test(expected = EntityValidationException.class)
    public void shouldThrowEntityValidationExceptionWhenUpdatingWithBlankContent() throws EntityValidationException, EntityNotFoundException {
        News news = dataGenerator.createTestNews();
        news.setContent(" ");

        newsService.update(news);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenUpdatingWithNullId() throws EntityValidationException, EntityNotFoundException {
        News news = dataGenerator.createTestNews();
        news.setId(null);

        newsService.update(news);
    }

    @Test
    public void shouldUpdateNews() throws EntityValidationException, EntityNotFoundException {
        News news = dataGenerator.createTestNews();
        news.setTitle("New Title");
        news.setContent("New Content");

        newsService.update(news);

    }

    @Test
    public void shouldReturnACount() {
        dataGenerator.generateRandomNews(5);
        assertTrue(newsService.count() == 5);
    }

    @Test
    public void shouldReturnAPageOfNews() {
        dataGenerator.generateRandomNews(10);

        Page<News> newsPage = newsService.findNewsPage(0, 3);

        assertTrue(newsPage.getTotalElements() == 10);
        assertTrue(newsPage.getSize() == 3);
    }

    @Test
    public void repositoryShouldHaveAnEntityType() {
        assertTrue(newsService.getEntityTypeClass() == News.class);
    }
}
