package com.decskill.domain;

import java.io.Serializable;
import java.time.Instant;

import com.decskill.domain.enumeration.Currecny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * A Price.
 */
@Entity
@Table(name = "price")
@NamedQuery(name = "Price.findPriceApply",
query = "select p from Price p where startDate <= ?1 and endDate >= ?1 and brand.id = ?2 and product.id = ?3 "
		+ "ORDER BY p.priority DESC limit 1")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Price implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "price")
    private Float price;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currecny currency;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Brand brand;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Price id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Price startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Price endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public Price priority(Integer priority) {
        this.setPriority(priority);
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Float getPrice() {
        return this.price;
    }

    public Price price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Currecny getCurrency() {
        return this.currency;
    }

    public Price currency(Currecny currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(Currecny currency) {
        this.currency = currency;
    }

    public Brand getBrand() {
        return this.brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Price brand(Brand brand) {
        this.setBrand(brand);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Price product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price)) {
            return false;
        }
        return id != null && id.equals(((Price) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Price{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", priority=" + getPriority() +
            ", price=" + getPrice() +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
