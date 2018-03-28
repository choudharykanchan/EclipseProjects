package com.dealertire.RightTurnFramework.testing;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.dealertire.RightTurnFramework.Utils;
import com.dealertire.RightTurnFramework.Models.Vehicle;
import com.dealertire.RightTurnFramework.Models.Vehicle.Criteria;

/**
 * Unit tests for the Vehicles class
 * @author bgreen
 *
 */
public class VehicleUnitTests {

	/**
	 * Loads a particular data set with all possible permutations, 
	 * in which trim is the integer row number
	 * and model is the binary representation
	 * @throws IOException if the file cannot be found.
	 */
	@Before
	public void setup() throws IOException {
		ArrayList<String[]> lines = readFromCSV("unitTestingVehicles.csv");
		Vehicle.loadData(lines);
	}
	
	/**
	 * Tests that when using find, you always get a vehicle with the right criteria.
	 * Axes tested: Staggered, Runflat, truck, WT Package, winter tire, Fitment, OEM, Multiple sizes
	 * @throws IOException
	 */
	@Test
	public void findReturnsCorrectAttributes() throws IOException {
		ArrayList<Vehicle> found = Vehicle.findVehicles(Criteria.STAGGERED, true);
		for(Vehicle needle : found) {
			assertTrue(needle.isStaggered);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitSet(rownum, 7);
		}
		
		found = Vehicle.findVehicles(Criteria.RUNFLAT, true);
		for(Vehicle needle : found) {
			assertTrue(needle.isRunflat);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitSet(rownum, 6);
		}
		
		found = Vehicle.findVehicles(Criteria.TRUCK, true);
		for(Vehicle needle : found) {
			assertTrue(needle.isTruck);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitSet(rownum, 5);
		}
		
		found = Vehicle.findVehicles(Criteria.WTPKG, true);
		for(Vehicle needle : found) {
			assertTrue(needle.hasWinterPackage);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitSet(rownum, 4);
		}
		
		found = Vehicle.findVehicles(Criteria.WINTER, true);
		for(Vehicle needle : found) {
			assertTrue(needle.isWinter);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitSet(rownum, 3);
		}
		
		found = Vehicle.findVehicles(Criteria.FITMENT, true);
		for(Vehicle needle : found) {
			assertTrue(needle.hasFitment);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitSet(rownum, 2);
		}
		
		found = Vehicle.findVehicles(Criteria.OEM, true);
		for(Vehicle needle : found) {
			assertTrue(needle.hasOEMTires);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitSet(rownum, 1);
		}
		
		found = Vehicle.findVehicles(Criteria.MULTISIZE, true);
		for(Vehicle needle : found) {
			assertTrue(needle.hasMultipleSizes);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitSet(rownum, 0);
		}	
	}
	
	/**
	 * Test that find returns the correct combinations when given two variables
	 */
	@Test
	public void findReturnsCorrectCombinations_TwoVars() {
		Map<Criteria, Boolean> criteria = new HashMap<Criteria, Boolean>();
		criteria.put(Criteria.STAGGERED, true);
		criteria.put(Criteria.RUNFLAT, true);
		ArrayList<Vehicle> found = Vehicle.findVehicles(criteria);
		
		for(Vehicle needle : found) {
			assertTrue(needle.isStaggered);
			assertTrue(needle.isRunflat);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitSet(rownum, 7);
			assertBitSet(rownum, 6);
		}
		
		criteria = new HashMap<Criteria, Boolean>();
		criteria.put(Criteria.STAGGERED, true);
		criteria.put(Criteria.RUNFLAT, false);
		found = Vehicle.findVehicles(criteria);
		
		for(Vehicle needle : found) {
			assertTrue(needle.isStaggered);
			assertFalse(needle.isRunflat);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitSet(rownum, 7);
			assertBitCleared(rownum, 6);
		}
		
		criteria = new HashMap<Criteria, Boolean>();
		criteria.put(Criteria.STAGGERED, false);
		criteria.put(Criteria.RUNFLAT, false);
		found = Vehicle.findVehicles(criteria);
		
		for(Vehicle needle : found) {
			assertFalse(needle.isStaggered);
			assertFalse(needle.isRunflat);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitCleared(rownum, 7);
			assertBitCleared(rownum, 6);
		}
		
		criteria = new HashMap<Criteria, Boolean>();
		criteria.put(Criteria.STAGGERED, false);
		criteria.put(Criteria.RUNFLAT, true);
		found = Vehicle.findVehicles(criteria);
		
		for(Vehicle needle : found) {
			assertFalse(needle.isStaggered);
			assertTrue(needle.isRunflat);
			Integer rownum = Integer.parseInt(needle.trim);
			assertBitCleared(rownum, 7);
			assertBitSet(rownum, 6);
		}
	}

	private void assertBitCleared(int integer, int bit) {
		assertFalse(integer + " has bit " + bit + "set!",
				(integer & (1<<bit)) != 0);
	}

	private void assertBitSet(int integer, int bit) {
		assertTrue(integer + " does not have bit " + bit + "set!",
				(integer & (1<<bit)) != 0);
	}
	
	private static ArrayList<String[]> readFromCSV(String filename) throws IOException {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		
		InputStream is = Utils.class.getClassLoader().getResourceAsStream(filename);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		
		//Skip line one, it has headers
		line = br.readLine();
		
		while ((line = br.readLine()) != null) {
			String[] variables = line.split(",", -1);
			for (int i = 0; i < variables.length; i++) {
				//clean up CSV formatting
				String token = variables[i];
				if (token.startsWith("\"")) {
					token = token.substring(1, token.length()-1); //chop off quotes
					//quotes are added when a " has to be escaped, so remove escaping
					variables[i] = token.replaceAll("\"\"", "\"");
				}
			}
			lines.add(variables);
		}

		br.close();
		return lines;
	}
}
