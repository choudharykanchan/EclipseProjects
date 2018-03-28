package com.dealertire.RightTurnFramework.Models;

/**
 * A utility class to avoid floating point issues when storing prices. Straight out of the textbook except for the constructor being useful.
 * @author bgreen
 */
public class Price implements Comparable<Price> {
	/** The number of dollars in this price. May be used to represent pounds, euros, or any other whole portion of a unit of currency*/
	public Integer dollars;
	/** The number of cents in this price. May be used to represent pence or any other fractional portion of a unit of currency.*/
	public Integer cents;
	
	/**
	 * Parses a price string into a Price object. 
	 * @param price The string to parse. Optionally may start with '$'. Must contain exactly one '.'. Ought to contain no more than two digits after that, but we don't enforce that.
	 */
	public Price(String price) {
		if (price.startsWith("$")) {
			price = price.substring(1);
		}
		String[] parts = price.split("\\.");
		if (parts.length != 2) throw new IllegalArgumentException("String '" + price + "' is not formatted like a price.");
		
		dollars = Integer.parseInt(parts[0]);
		cents = Integer.parseInt(parts[1]);
	}
	
	/**
	 * Creates a Price the old fashioned way.
	 * @param dollars The dollar component
	 * @param cents The cents component
	 */
	public Price(Integer dollars, Integer cents) {
		this.dollars = dollars;
		this.cents = cents;
		
		//Normalization
		while (this.cents >= 100) {
			this.dollars++;
			this.cents -= 100;
		}
	}

	@Override
	public int compareTo(Price p) {
		if (this.dollars == p.dollars)
			return cents.compareTo(p.cents);
		else 
			return dollars.compareTo(p.dollars);
	}

	/**
	 * Returns a Price that equals the given price times a constant
	 * @param i The constant to multiply by
	 * @return A new Price object
	 */
	public Price times(int i) {
		return new Price(this.dollars*i, this.cents*i);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cents == null) ? 0 : cents.hashCode());
		result = prime * result + ((dollars == null) ? 0 : dollars.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Price other = (Price) obj;
		return this.dollars.intValue() == other.dollars.intValue() && this.cents.intValue() == other.cents.intValue();
	}

	@Override
	public String toString() {
		return "$" + dollars + "." + cents;
	}
	
	/**
	 * Get the dollar value from the price
	 * @return The dollar value
	 */
	public int getDollars() {
		return this.dollars;
	}
	
	/**
	 * Get the cents value from the price
	 * @return The cents value as a whole integer
	 */
	public int getCents() {
		return this.cents;
	}
}