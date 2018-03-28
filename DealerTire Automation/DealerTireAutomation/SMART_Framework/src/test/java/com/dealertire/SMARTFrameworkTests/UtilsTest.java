package com.dealertire.SMARTFrameworkTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.xpath.operations.Bool;
import org.junit.Test;

import com.dealertire.SMARTFramework.Utils;

/**
 * Tests for utility functions
 * @author bgreen
 */
public class UtilsTest {

	/**
	 * Tests to ensure that the mergeDatasets_cartesian function actually does a Cartesian join, since this shit is complex.
	 */
	@Test
	public void testMergeDatasheets_cartesian() {
		String[] a1 = {"1","2","3"};
		String[] a2 = {"4", "5","6"};
		String[] a3 = {"7", "8", "9"};
		
		String[] b1 = {"a","b"};
		String[] b2 = {"c", "d"};
		String[] b3 = {"e", "f"};
		
		String[] expected1 = {"1", "2", "3", "a", "b"};
		String[] expected2 = {"1", "2", "3", "c", "d"};
		String[] expected3 = {"1", "2", "3", "e", "f"};
		
		String[] expected4 = {"4", "5", "6", "a", "b"};
		String[] expected5 = {"4", "5", "6", "c", "d"};
		String[] expected6 = {"4", "5", "6", "e", "f"};
		
		String[] expected7 = {"7", "8", "9", "a", "b"};
		String[] expected8 = {"7", "8", "9", "c", "d"};
		String[] expected9 = {"7", "8", "9", "e", "f"};
		
		ArrayList<String[]> a = new ArrayList<String[]>();
		a.add(a1);
		a.add(a2);
		a.add(a3);
		
		ArrayList<String[]> b = new ArrayList<String[]>();
		b.add(b1);
		b.add(b2);
		b.add(b3);
		
		ArrayList<String[]> expected = new ArrayList<String[]>();
		expected.add(expected1);
		expected.add(expected2);
		expected.add(expected3);
		expected.add(expected4);
		expected.add(expected5);
		expected.add(expected6);
		expected.add(expected7);
		expected.add(expected8);
		expected.add(expected9);
		
		ArrayList<String[]> actual = Utils.mergeDatasheets_cartesian(a, b);
		
		assertEquals(expected.size(), actual.size());
		for (String[] line : expected) {
			boolean found = false;
			for (String[] actualLine : actual) {
				if (Arrays.deepEquals(line, actualLine)) {
					found = true;
					break;
				}
			}
			assertTrue(found);
		}
	}

}
