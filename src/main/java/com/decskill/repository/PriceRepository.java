package com.decskill.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.decskill.domain.Price;

/**
 * Spring Data JPA repository for the Price entity.
 */
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
	public List<Price> findPriceApply(Instant dateApply, Long brandId, Long productId);

}
