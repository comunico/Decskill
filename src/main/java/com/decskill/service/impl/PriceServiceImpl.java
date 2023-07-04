package com.decskill.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.decskill.domain.Price;
import com.decskill.domain.SearchFilterPrice;
import com.decskill.repository.PriceRepository;
import com.decskill.service.PriceService;
import com.decskill.service.dto.PriceFilterDTO;

/**
 * Service Implementation for managing {@link Price}.
 */
@Service
@Transactional
public class PriceServiceImpl implements PriceService {

    private final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);

	private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price save(Price price) {
        log.debug("Request to save Price : {}", price);
        return priceRepository.save(price);
    }

    @Override
    public Price update(Price price) {
        log.debug("Request to update Price : {}", price);
        return priceRepository.save(price);
    }

    @Override
    public Optional<Price> partialUpdate(Price price) {
        log.debug("Request to partially update Price : {}", price);

        return priceRepository
            .findById(price.getId())
            .map(existingPrice -> {
                if (price.getStartDate() != null) {
                    existingPrice.setStartDate(price.getStartDate());
                }
                if (price.getEndDate() != null) {
                    existingPrice.setEndDate(price.getEndDate());
                }
                if (price.getPriority() != null) {
                    existingPrice.setPriority(price.getPriority());
                }
                if (price.getPrice() != null) {
                    existingPrice.setPrice(price.getPrice());
                }
                if (price.getCurrency() != null) {
                    existingPrice.setCurrency(price.getCurrency());
                }

                return existingPrice;
            })
            .map(priceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Price> findAll() {
        log.debug("Request to get all Prices");
        return priceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Price> findOne(Long id) {
        log.debug("Request to get Price : {}", id);
        return priceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Price : {}", id);
        priceRepository.deleteById(id);
    }

	@Override
	@Transactional(readOnly = true)
	public List<PriceFilterDTO> filterPrices(SearchFilterPrice searchFilterPrice) {
        log.debug("Request to get Price to Apply: {}", searchFilterPrice);

	    Instant instantDateApply = Instant.parse(searchFilterPrice.getDateApply());
		List<Price> result = priceRepository.findPriceApply(instantDateApply, 
															searchFilterPrice.getIdBrand(), 
															searchFilterPrice.getIdProduct());
        log.debug("Recover the Price to Apply: {}", result);
		List<PriceFilterDTO> prices = new ArrayList<PriceFilterDTO>();
		result.forEach(price -> {
			prices.add(
					new PriceFilterDTO(price.getId(), 
										price.getBrand().getId(), 
										price.getProduct().getId(), 
										price.getPrice()));
		});
		return prices;

	}

//	@Override
//	@Transactional(readOnly = true)
//	public List<PriceFilterDTO> filterPrices(SearchFilterPrice searchFilterPrice) {
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		CriteriaQuery<Price> criteriaQuery = criteriaBuilder.createQuery(Price.class);
//		Root<Price> taskRoot = criteriaQuery.from(Price.class);
//		List<Predicate> predicates = getPredicates(searchFilterPrice, criteriaBuilder, taskRoot);
//		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
//
//		TypedQuery<Price> query = entityManager.createQuery(criteriaQuery);
//		List<PriceFilterDTO> prices= executeQuery(query);
//		return prices;
//	}
//
//	private List<Predicate> getPredicates(SearchFilterPrice searchFilterPrice,
//			CriteriaBuilder criteriaBuilder, Root<Price> priceRoot) {
//		List<Predicate> predicates = new ArrayList<Predicate>();
//
//		if (searchFilterPrice.getDateApply() != null) {
//			Instant instantDateApply = LocalDate.parse(searchFilterPrice.getDateApply())
//					.atStartOfDay(ZoneId.systemDefault()).toInstant();
//			predicates.add(criteriaBuilder.greaterThanOrEqualTo(priceRoot.get("startDate"), instantDateApply));
//			predicates.add(criteriaBuilder.lessThanOrEqualTo(priceRoot.get("endDate"), instantDateApply));
//		}
//
//		if (searchFilterPrice.getIdBrand() != null) {
//			predicates.add(criteriaBuilder.equal(priceRoot.get("idBrand"), searchFilterPrice.getIdBrand()));
//		}
//		
//		if (searchFilterPrice.getIdProduct() != null) {
//			predicates.add(criteriaBuilder.equal(priceRoot.get("idProduct"), searchFilterPrice.getIdProduct()));
//		}
//		
//		return predicates;
//	}
//	
//	private List<PriceFilterDTO> executeQuery(TypedQuery<Price> query) {
//		List<Price> result = query.getResultList();
//        log.debug("Recover the Price to Apply: {}", result);
//		List<PriceFilterDTO> prices = new ArrayList<PriceFilterDTO>();
//		result.forEach(price -> {
//			prices.add(
//					new PriceFilterDTO(price.getId(), 
//										price.getBrand().getId(), 
//										price.getProduct().getId(), 
//										price.getPrice()));
//		});
//		return prices;
//	}

}
