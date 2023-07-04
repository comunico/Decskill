package com.decskill.service.impl;

import com.decskill.domain.Brand;
import com.decskill.repository.BrandRepository;
import com.decskill.service.BrandService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Brand}.
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    private final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand save(Brand brand) {
        log.debug("Request to save Brand : {}", brand);
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Brand brand) {
        log.debug("Request to update Brand : {}", brand);
        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> partialUpdate(Brand brand) {
        log.debug("Request to partially update Brand : {}", brand);

        return brandRepository
            .findById(brand.getId())
            .map(existingBrand -> {
                if (brand.getName() != null) {
                    existingBrand.setName(brand.getName());
                }

                return existingBrand;
            })
            .map(brandRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Brand> findAll() {
        log.debug("Request to get all Brands");
        return brandRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Brand> findOne(Long id) {
        log.debug("Request to get Brand : {}", id);
        return brandRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Brand : {}", id);
        brandRepository.deleteById(id);
    }
}
