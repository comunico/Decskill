package com.decskill.service;

import java.util.List;
import java.util.Optional;

import com.decskill.domain.Price;
import com.decskill.domain.SearchFilterPrice;
import com.decskill.service.dto.PriceFilterDTO;

/**
 * Service Interface for managing {@link Price}.
 */
public interface PriceService {
    /**
     * Save a price.
     *
     * @param price the entity to save.
     * @return the persisted entity.
     */
    Price save(Price price);

    /**
     * Updates a price.
     *
     * @param price the entity to update.
     * @return the persisted entity.
     */
    Price update(Price price);

    /**
     * Partially updates a price.
     *
     * @param price the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Price> partialUpdate(Price price);

    /**
     * Get all the prices.
     *
     * @return the list of entities.
     */
    List<Price> findAll();

    /**
     * Get the "id" price.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Price> findOne(Long id);

    /**
     * Delete the "id" price.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
	/**
	 * Filter tasks Pageable.
	 *
	 * @return the list of entities.
	 */
	List<PriceFilterDTO> filterPrices(SearchFilterPrice searchFilterPrice);

}
