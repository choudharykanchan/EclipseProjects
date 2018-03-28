package com.dealertire.RightTurnFramework.Models;

/**
 * A staggered set. Has more fields than a normal Product.
 * @author bgreen
 */
public class StaggeredProduct extends Product {

	private Price frontPrice;
	private Price rearPrice;
	
	private Size frontSize;
	private Size rearSize;
	
	/**
	 * Basic constructor
	 * @param id This ought to be the unique identifier that the ProductList uses to display the Product, so a ProductView can be created
	 * @param name The name of the product
	 * @param frontPrice The price of the front product
	 * @param rearPrice The price of the rear product
	 * @param price The price the product is being sold at
	 * @param brand The brand of the product
	 * @param seasonality Whether the tire is winter, all-season, et cetera
	 * @param isOEM True if this is a factory fitment tire
	 * @param isPromo True if this tire has a promotion
	 * @param isRunflat True if this tire runs flat
	 */
	public StaggeredProduct(String id, String name, Price frontPrice, Price rearPrice, String brand,
			Seasonality seasonality, boolean isOEM, boolean isPromo, boolean isRunflat) {
		super(id, name, null, brand, seasonality, isOEM, isPromo, isRunflat);
		
		this.setFrontPrice(frontPrice);
		this.setRearPrice(rearPrice);
	}

	
	@Override
	public Boolean matchesCriteria(Criteria c) {
		if (c == Criteria.STAGGERED) return true;
		return super.matchesCriteria(c);
	}


	/**
	 * @return the rearSize
	 */
	public Size getRearSize() {
		return rearSize;
	}


	/**
	 * @param rearSize the rearSize to set
	 */
	public void setRearSize(Size rearSize) {
		this.rearSize = rearSize;
	}


	/**
	 * @return the frontSize
	 */
	public Size getFrontSize() {
		return frontSize;
	}


	/**
	 * @param frontSize the frontSize to set
	 */
	public void setFrontSize(Size frontSize) {
		this.frontSize = frontSize;
	}


	/**
	 * @return the frontPrice
	 */
	public Price getFrontPrice() {
		return frontPrice;
	}


	/**
	 * @param frontPrice the frontPrice to set
	 */
	public void setFrontPrice(Price frontPrice) {
		this.frontPrice = frontPrice;
	}


	/**
	 * @return the rearPrice
	 */
	public Price getRearPrice() {
		return rearPrice;
	}


	/**
	 * @param rearPrice the rearPrice to set
	 */
	public void setRearPrice(Price rearPrice) {
		this.rearPrice = rearPrice;
	}
	
	

}
