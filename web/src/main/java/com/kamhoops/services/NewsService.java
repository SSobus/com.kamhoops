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

    public News create(News news) throws EntityValidationException, EntityNotFoundException {
        preCreateChecks(news);

        news = repository.save(news);

        return findById(news.getId());
    }

    private void preCreateChecks(News news) throws EntityValidationException {
        Assert.notNull(news, "cannot create null news");
        Assert.isNull(news.getId(), "cannot create object with an id");

        validateEntity(news);
    }

    public News update(News news) throws EntityValidationException, EntityNotFoundException {
        news = preUpdateChecksAndMerges(news);

        news = repository.save(news);

        return findById(news.getId());
    }

    private News preUpdateChecksAndMerges(News news) throws EntityValidationException, EntityNotFoundException {
        Assert.notNull(news, "cannot create null news");
        Assert.notNull(news.getId(), "cannot update object without an id");

        validateEntity(news);

        //forces a check to make sure the object already exists
        News existingNews = findById(news.getId());

        existingNews.setTitle(news.getTitle());
        existingNews.setContent(news.getContent());

        validateEntity(existingNews);

        return existingNews;
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
