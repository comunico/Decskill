package com.decskill.service.dto;

import java.io.Serializable;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class PriceFilterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long brandId;

    private Long productId;

    private Float price;

    public PriceFilterDTO() {
        // Empty constructor needed for Jackson.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PriceFilterDTO(Long id, Long brandId, Long productId, Float price) {
		super();
		this.id = id;
		this.brandId = brandId;
		this.productId = productId;
		this.price = price;
	}

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	// prettier-ignore
    @Override
    public String toString() {
        return "PriceFilterDTO{" +
            "id='" + id + '\'' +
            ", brandId='" + brandId + '\'' +
            ", productId='" + productId + '\'' +
            ", price='" + price + '\'' +
            "}";
    }
}
