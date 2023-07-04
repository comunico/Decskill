package com.decskill.domain;

public class SearchFilterPrice {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private String dateApply;

	private Long idProduct;
	private Long idBrand;
	
	
	public SearchFilterPrice(String dateApply, Long idProduct, Long idBrand) {
		super();
		this.dateApply = dateApply;
		this.idProduct = idProduct;
		this.idBrand = idBrand;
	}

	public SearchFilterPrice() {
	}

	public String getDateApply() {
		return dateApply;
	}

	public void setDateApply(String dateApply) {
		this.dateApply = dateApply;
	}

	public Long getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}

	public Long getIdBrand() {
		return idBrand;
	}

	public void setIdBrand(Long idBrand) {
		this.idBrand = idBrand;
	}

	@Override
	public String toString() {
		return "SearchFilterTask [dateApply=" + dateApply + 
								", idProduct=" + idProduct + 
								", idBrand=" + idBrand+ "]";
	}

}
