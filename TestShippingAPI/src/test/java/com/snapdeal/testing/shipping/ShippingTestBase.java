package com.snapdeal.testing.shipping;

import java.util.ArrayList;
import java.util.HashSet;

import org.testng.Assert;

public class ShippingTestBase {
  	
	public void assertEqualList(ArrayList<String> expectedList,
			ArrayList<String> actualList) {

		Assert.assertEquals(expectedList.size(), actualList.size());
		HashSet<String> set = new HashSet<String>();
		for (int i = 0; i < actualList.size(); i++) {
			set.add(actualList.get(i));
		}
		for (int i = 0; i < expectedList.size(); i++) {
			Assert.assertTrue(set.contains(expectedList.get(i)));
		
		}

		
		
	}
	
}
