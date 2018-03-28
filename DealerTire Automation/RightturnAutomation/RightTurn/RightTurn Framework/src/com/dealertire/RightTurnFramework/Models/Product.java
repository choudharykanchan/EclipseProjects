package com.dealertire.RightTurnFramework.Models;

import java.util.Comparator;

import com.dealertire.RightTurnFramework.Pages.ComparePage.ProductCompareView;
import com.dealertire.RightTurnFramework.Pages.ProductPage;

/**
 * A model to represent a product.
 * @author bgreen
 *
 */
public class Product implements Comparable<Product> {
	//Properties
	private String listID;
	private String name;
	private Price price;
	private String brand;
	private Integer displayOrder;
	private Integer warranty;

	private Seasonality seasonality;
	private boolean isOEM;
	private boolean isPromo;
	private boolean isRunflat;

	private int numReviews;
	private ProductPage.ProductView listView;
	private ProductCompareView compareView;

	
	/**
	 * Get the number of reviews displayed
	 * @return The number of reviews
	 */
	public int getNumReviews() {
		return numReviews;
	}

	/**
	 * Set the number of reviews.
	 * @param numReviews The number of reviews
	 */
	public void setNumReviews(int numReviews) {
		this.numReviews = numReviews;
	}
	
	/** 
	 * Set display order, used only on product list page
	 * @param displayOrder
	 */
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	/**
	 * Basic constructor
	 * @param id This ought to be the unique identifier that the ProductList uses to display the Product, so a ProductView can be created
	 * @param name The name of the product
	 * @param price The price the product is being sold at
	 * @param brand The brand of the product
	 * @param seasonality Seasonality of the product
	 * @param isOEM True if this is a factory fitment tire
	 * @param isPromo True if this tire has a promotion
	 * @deprecated in favor of {@link Product#Product(String, String, Price, String, boolean, boolean, boolean, boolean)}
	 */
	public Product(String id, String name, Price price, String brand,
			Seasonality seasonality, boolean isOEM,
			boolean isPromo) {
		super();
		this.listID = id;
		this.name = name;
		this.price = price;
		this.brand = brand;
		this.displayOrder = 0;
		this.seasonality = seasonality;
		this.isOEM = isOEM;
		this.isPromo = isPromo;
		
		this.numReviews = 0;
		this.setQuantity(0);
	}
	
	/**
	 * Basic constructor
	 * @param id This ought to be the unique identifier that the ProductList uses to display the Product, so a ProductView can be created
	 * @param name The name of the product
	 * @param price The price the product is being sold at
	 * @param brand The brand of the product
	 * @param seasonality Seasonality of the product
	 * @param isOEM True if this is a factory fitment tire
	 * @param isPromo True if this tire has a promotion
	 * @param isRunflat True if this tire runs flat
	 */
	public Product(String id, String name, Price price, String brand,
			Seasonality seasonality, boolean isOEM,
			boolean isPromo, boolean isRunflat) {
		super();
		this.listID = id;
		this.name = name;
		this.price = price;
		this.brand = brand;
		this.displayOrder = 0;
		this.seasonality = seasonality;
		this.isOEM = isOEM;
		this.isPromo = isPromo;
		this.isRunflat = isRunflat;
		
		this.numReviews = 0;
		this.setQuantity(0);
	}

	/**
	 * Whether the product matches a given criteria, used by search methods.
	 * @param c The criteria to match
	 * @return True if it matches, false if not.
	 */
	public Boolean matchesCriteria(Criteria c){
		switch(c) {
			case WINTER:
				return seasonality == Seasonality.WINTER;
			case THREE_SEASON:
				return seasonality == Seasonality.THREE_SEASON;
			case ALL_SEASON:
				return seasonality == Seasonality.ALL_SEASON;
			case OEM:
				return isOEM;
			case PROMO:
				return isPromo;
			case HASREVIEWS:
				return numReviews > 0;
			case RUNFLAT:
				return isRunflat;
			case STAGGERED:
				return false;
			default:
				return false;
		}
	}
	
	@Override
	 public int compareTo(Product p) {
	        return displayOrder.compareTo(p.displayOrder);
	 }

	/**
	 * Gets the name of the product. Can be used safely from any page, as it is cached.
	 * @return The name of the product, as it was displayed when the Product was created.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the brand of the product. Can be used safely from any page, as it is cached.
	 * @return The brand of the product, as it was displayed when the Product was created.
	 */
	public String getBrand() {
		return brand;
	}
	
	/**
	 * Gets the price of the product. Can be used safely from any page, as it is cached.
	 * @return The price of the product, as it was displayed when the Product was created.
	 */
	public Price getPrice() {
		return price;
	}

	/**
	 * Get the ID used in the Product List, so you can figure out which one this represents and build a view around it.
	 * @return The ID
	 */
	public String getListID() {
		return listID;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Product) {
			Product p = (Product) obj;
			return this.name.equalsIgnoreCase(p.name) &&
					this.brand.equalsIgnoreCase(p.brand);
		}
		return false; //not even the same class means not equal
	}
	
	
	@Override
	public String toString() {
		return "Product [name=" + name + ", brand=" + brand + "]";
	}

	/**
	 * Set the quantity. Should be done when the UI changes
	 * @param quantity the quantity
	 */
	public void setQuantity(int quantity) {
	}

	/**
	 * Get the view from the productPage 
	 * @return the view
	 */
	public ProductPage.ProductView getListView() {
		return listView;
	}

	/**
	 * Set the view from the productPage 
	 * @param listView The view
	 */
	public void setListView(ProductPage.ProductView listView) {
		this.listView = listView;
	}

	/**
	 * Set the view from the Compare page
	 * @param productCompareView The view
	 */
	public void setCompareView(ProductCompareView productCompareView) {
		this.compareView = productCompareView;		
	}
	
	/**
	 * Get the view from the Compare page 
	 * @return the view
	 */
	public ProductCompareView getCompareView() {
		return this.compareView;
	}

	/**
	 * Get the length of the treadlife warranty. Should be expressed in thousands of miles: 35 for 35K. No warranty should be 0.
	 * @return The warranty, in thousands of miles.
	 */
	public Integer getWarranty() {
		return warranty;
	}
	

	/**
	 * Set the treadlife warranty. 
	 * @param warranty The warranty to set. Should be expressed in thousands of miles: 35 for 35K. No warranty should be 0.
	 */
	public void setWarranty(Integer warranty) {
		this.warranty = warranty;
	}
	
	/**
	 * Allows setting the runflat property after creation.
	 * @param isRunflat Whether the tire runs flat or not
	 */
	public void setRunflat(boolean isRunflat) {
		this.isRunflat = isRunflat;
	}
	
	/**
	 * Get the seasonality of the product
	 * @return Winter, Three-season, All-season, or Unknown
	 */
	public Seasonality getSeasonality() {
		return seasonality;
	}

	/**
	 * A comparator for sorting by treadlife warranty, high to low
	 * @author bgreen
	 */
	public static class ComparatorTreadLifeWarranty implements Comparator<Product> {

		@Override
		public int compare(Product o1, Product o2) {
			//Intentionally reversed, as Integers want to be ordered low->high
			return o2.warranty.compareTo(o1.warranty);
		}

	}
	
	/**
	 * Criteria which Products can meet.
	 */
	public enum Criteria {
		
		/** Winter tire */
		WINTER,
		
		/** All-season tire**/
		ALL_SEASON,
		
		/** Non-winter tire **/
		THREE_SEASON,
		
		/** Factory fitment */
		OEM, 
		
		/** Product with a promotion on it */
		PROMO,
		
		/**Product with reviews */
		HASREVIEWS,
		
		/**Product that is runflat*/
		RUNFLAT,
		
		/**Product is in a staggered set*/
		STAGGERED
	}
	
	/**
	 * The seasonality of the product displayed
	 * @author bgreen
	 */
	public enum Seasonality {
		/** Winter tire **/
		WINTER,
		
		/** All-season tire**/
		ALL_SEASON,
		
		/** Non-winter tire **/
		THREE_SEASON,
		
		/** Default **/
		UNKNOWN
	}


}

