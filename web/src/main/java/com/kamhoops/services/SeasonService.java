package com.kamhoops.services;

import com.kamhoops.data.domain.Season;
import com.kamhoops.data.repository.SeasonRepository;
import com.kamhoops.exceptions.EntityValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeasonService extends AbstractService<SeasonRepository, Season> {

    @Autowired
    SeasonRepository seasonRepository;

    /**
     * Save a Season
     *
     * @param season to save
     * @return saved season
     * @throws EntityValidationException
     */
    public Season save(Season season) throws EntityValidationException {
        validateEntity(season);

        return seasonRepository.save(season);
    }

    @Override
    public SeasonRepository getRepository() {
        return repository;
    }

    @Autowired
    public void setRepository(SeasonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Class<Season> getEntityTypeClass() {
        return Season.class;
    }
}
