package com.kamhoops.services;

import com.kamhoops.data.domain.News;
import com.kamhoops.data.exceptions.EntityNotFoundException;
import com.kamhoops.data.repository.NewsRepository;
import com.kamhoops.exceptions.EntityValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;

@Service
public class NewsService extends AbstractService<NewsRepository, News> {

    public News create(News news) throws EntityValidationException {
        preCreateChecks(news);

        return repository.save(news);
    }

    private void preCreateChecks(News news) throws EntityValidationException {
        Assert.notNull(news, "cannot create null news");

        if (news.getId() != null) {
            throw new EntityValidationException(new FieldError("news", "id", "id is not null, are you sure this is an object ot be created"));
        }

        if (StringUtils.isBlank(news.getTitle())) {
            throw new EntityValidationException(new FieldError("news", "title", "Supplied title of null or blank is invalid"));
        }

        if (StringUtils.isBlank(news.getContent())) {
            throw new EntityValidationException(new FieldError("news", "content", "Supplied content of null or blank is invalid"));
        }

        validateEntity(news);
    }

    public News update(News modifiedNews) throws EntityValidationException, EntityNotFoundException {
        preUpdateChecks(modifiedNews);

        return repository.save(modifiedNews);
    }

    private void preUpdateChecks(News modifiedNews) throws EntityValidationException, EntityNotFoundException {
        Assert.notNull(modifiedNews, "cannot create null news");

        if (modifiedNews.getId() == null) {
            throw new EntityValidationException(new FieldError("news", "id", "id is null, are you sure this is an object ot be updated"));
        }

        //forces a check to make sure the object already exists
        findById(modifiedNews.getId());

        if (StringUtils.isBlank(modifiedNews.getTitle())) {
            throw new EntityValidationException(new FieldError("news", "title", "Supplied title of null or blank is invalid"));
        }

        if (StringUtils.isBlank(modifiedNews.getContent())) {
            throw new EntityValidationException(new FieldError("news", "content", "Supplied content of null or blank is invalid"));
        }

        validateEntity(modifiedNews);
    }

    public Page<News> findNewsPage(int page, int size) {
        PageRequest request = new PageRequest(page, size, Sort.Direction.DESC, "createDate");
        Page<News> news = repository.findAllByActiveTrue(request);
        return repository.findAllByActiveTrue(request);
    }

    public Long count() {
        return repository.count();
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
