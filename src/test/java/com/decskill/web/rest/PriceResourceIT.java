package com.decskill.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.decskill.IntegrationTest;
import com.decskill.domain.Price;
import com.decskill.domain.enumeration.Currecny;
import com.decskill.repository.PriceRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PriceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PriceResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final Currecny DEFAULT_CURRENCY = Currecny.EUR;
    private static final Currecny UPDATED_CURRENCY = Currecny.USD;

    private static final String ENTITY_API_URL = "/api/prices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPriceMockMvc;

    private Price price;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Price createEntity(EntityManager em) {
        Price price = new Price()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .priority(DEFAULT_PRIORITY)
            .price(DEFAULT_PRICE)
            .currency(DEFAULT_CURRENCY);
        return price;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Price createUpdatedEntity(EntityManager em) {
        Price price = new Price()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .priority(UPDATED_PRIORITY)
            .price(UPDATED_PRICE)
            .currency(UPDATED_CURRENCY);
        return price;
    }

    @BeforeEach
    public void initTest() {
        price = createEntity(em);
    }

    @Test
    @Transactional
    void createPrice() throws Exception {
        int databaseSizeBeforeCreate = priceRepository.findAll().size();
        // Create the Price
        restPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(price)))
            .andExpect(status().isCreated());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeCreate + 1);
        Price testPrice = priceList.get(priceList.size() - 1);
        assertThat(testPrice.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPrice.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testPrice.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPrice.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void createPriceWithExistingId() throws Exception {
        // Create the Price with an existing ID
        price.setId(1L);

        int databaseSizeBeforeCreate = priceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(price)))
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrices() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get all the priceList
        restPriceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(price.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())));
    }

    @Test
    @Transactional
    void getPrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        // Get the price
        restPriceMockMvc
            .perform(get(ENTITY_API_URL_ID, price.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(price.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPrice() throws Exception {
        // Get the price
        restPriceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        int databaseSizeBeforeUpdate = priceRepository.findAll().size();

        // Update the price
        Price updatedPrice = priceRepository.findById(price.getId()).get();
        // Disconnect from session so that the updates on updatedPrice are not directly saved in db
        em.detach(updatedPrice);
        updatedPrice
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .priority(UPDATED_PRIORITY)
            .price(UPDATED_PRICE)
            .currency(UPDATED_CURRENCY);

        restPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPrice.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPrice))
            )
            .andExpect(status().isOk());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
        Price testPrice = priceList.get(priceList.size() - 1);
        assertThat(testPrice.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPrice.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPrice.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPrice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPrice.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void putNonExistingPrice() throws Exception {
        int databaseSizeBeforeUpdate = priceRepository.findAll().size();
        price.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, price.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(price))
            )
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrice() throws Exception {
        int databaseSizeBeforeUpdate = priceRepository.findAll().size();
        price.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(price))
            )
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrice() throws Exception {
        int databaseSizeBeforeUpdate = priceRepository.findAll().size();
        price.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(price)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePriceWithPatch() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        int databaseSizeBeforeUpdate = priceRepository.findAll().size();

        // Update the price using partial update
        Price partialUpdatedPrice = new Price();
        partialUpdatedPrice.setId(price.getId());

        partialUpdatedPrice.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).priority(UPDATED_PRIORITY).price(UPDATED_PRICE);

        restPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrice))
            )
            .andExpect(status().isOk());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
        Price testPrice = priceList.get(priceList.size() - 1);
        assertThat(testPrice.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPrice.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPrice.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPrice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPrice.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    }

    @Test
    @Transactional
    void fullUpdatePriceWithPatch() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        int databaseSizeBeforeUpdate = priceRepository.findAll().size();

        // Update the price using partial update
        Price partialUpdatedPrice = new Price();
        partialUpdatedPrice.setId(price.getId());

        partialUpdatedPrice
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .priority(UPDATED_PRIORITY)
            .price(UPDATED_PRICE)
            .currency(UPDATED_CURRENCY);

        restPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPrice))
            )
            .andExpect(status().isOk());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
        Price testPrice = priceList.get(priceList.size() - 1);
        assertThat(testPrice.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPrice.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testPrice.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testPrice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPrice.getCurrency()).isEqualTo(UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void patchNonExistingPrice() throws Exception {
        int databaseSizeBeforeUpdate = priceRepository.findAll().size();
        price.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, price.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(price))
            )
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrice() throws Exception {
        int databaseSizeBeforeUpdate = priceRepository.findAll().size();
        price.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(price))
            )
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrice() throws Exception {
        int databaseSizeBeforeUpdate = priceRepository.findAll().size();
        price.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(price)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Price in the database
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrice() throws Exception {
        // Initialize the database
        priceRepository.saveAndFlush(price);

        int databaseSizeBeforeDelete = priceRepository.findAll().size();

        // Delete the price
        restPriceMockMvc
            .perform(delete(ENTITY_API_URL_ID, price.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Price> priceList = priceRepository.findAll();
        assertThat(priceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
