package com.kamhoops.services;

import com.kamhoops.data.domain.News;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.NewsRepository;
import com.kamhoops.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class NewsService extends AbstractService<NewsRepository, News> {

    public News create(News news) throws EntityValidationException {
        preCreateChecks(news);

        return repository.save(news);
    }

    private void preCreateChecks(News news) throws EntityValidationException {
        Assert.notNull(news, "cannot create null news");
        Assert.isNull(news.getId(), "cannot create object with an id");
        Assert.hasText(news.getTitle(), "cannot create with a blank title");
        Assert.hasText(news.getContent(), "cannot create with blank content");

        validateEntity(news);
    }

    public News update(News modifiedNews) throws EntityValidationException, EntityNotFoundException {
        preUpdateChecks(modifiedNews);

        return repository.save(modifiedNews);
    }

    private void preUpdateChecks(News modifiedNews) throws EntityValidationException, EntityNotFoundException {
        Assert.notNull(modifiedNews, "cannot create null news");
        Assert.notNull(modifiedNews.getId(), "cannot update object without an id");
        Assert.hasText(modifiedNews.getTitle(), "cannot update with a blank title");
        Assert.hasText(modifiedNews.getContent(), "cannot update with blank content");

        //forces a check to make sure the object already exists
        findById(modifiedNews.getId());

        validateEntity(modifiedNews);
    }

    public Page<News> findNewsPage(int page, int size) {
        PageRequest request = new PageRequest(page, size, Sort.Direction.DESC, "createDate");
        return repository.findAllByActiveTrue(request);
    }

    @Override
    public NewsRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(NewsRepository newsRepository) {
        repository = newsRepository;
    }

    @Override
    public Class<News> getEntityTypeClass() {
        return News.class;
    }
}
