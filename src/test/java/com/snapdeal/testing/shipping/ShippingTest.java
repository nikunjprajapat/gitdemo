package com.snapdeal.testing.shipping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.snapdeal.base.model.common.CatalogType;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.shipping.core.model.AddBannedZipPatternForProductRequest;
import com.snapdeal.shipping.core.model.AddBannedZipPatternForProductResponse;
import com.snapdeal.shipping.core.model.CancelShipmentRequest;
import com.snapdeal.shipping.core.model.CancelShipmentResponse;
import com.snapdeal.shipping.core.model.CatalogNonShippableReason;
import com.snapdeal.shipping.core.model.CheckIfCancellableRequest;
import com.snapdeal.shipping.core.model.CheckIfCancellableResponse;
import com.snapdeal.shipping.core.model.FulfillOrderRequest;
import com.snapdeal.shipping.core.model.GetActiveShippingProvidersRequest;
import com.snapdeal.shipping.core.model.GetActiveShippingProvidersResponse;
import com.snapdeal.shipping.core.model.GetAllShippingGroupsForCatalogRequest;
import com.snapdeal.shipping.core.model.GetAllShippingGroupsForCatalogResponse;
import com.snapdeal.shipping.core.model.GetAllShippingGroupsRequest;
import com.snapdeal.shipping.core.model.GetAllShippingGroupsResponse;
import com.snapdeal.shipping.core.model.GetAllShippingHistoryRequest;
import com.snapdeal.shipping.core.model.GetAllShippingHistoryResponse;
import com.snapdeal.shipping.core.model.GetApplicableShippingMethodsRequest;
import com.snapdeal.shipping.core.model.GetApplicableShippingMethodsResponse;
import com.snapdeal.shipping.core.model.GetDeliveredPackagesRequest;
import com.snapdeal.shipping.core.model.GetDeliveredPackagesResponse;
import com.snapdeal.shipping.core.model.GetPackageByReferenceCodeRequest;
import com.snapdeal.shipping.core.model.GetPackageByReferenceCodeResponse;
import com.snapdeal.shipping.core.model.GetShippingGroupByCodeRequest;
import com.snapdeal.shipping.core.model.GetShippingGroupByCodeResponse;
import com.snapdeal.shipping.core.model.GetShippingMethodByCodeRequest;
import com.snapdeal.shipping.core.model.GetShippingMethodByCodeResponse;
import com.snapdeal.shipping.core.model.GetShippingMethodChargesRequest;
import com.snapdeal.shipping.core.model.GetShippingMethodChargesResponse;
import com.snapdeal.shipping.core.model.IsCatalogShippableRequest;
import com.snapdeal.shipping.core.model.IsCatalogShippableResponse;
import com.snapdeal.shipping.core.model.IsFulfilledRequest;
import com.snapdeal.shipping.core.model.IsFulfilledResponse;
import com.snapdeal.shipping.core.model.IsShippingValidRequest;
import com.snapdeal.shipping.core.model.IsShippingValidResponse;
import com.snapdeal.shipping.core.model.OverrideBannedZipPatternForVendorProductsRequest;
import com.snapdeal.shipping.core.model.OverrideBannedZipPatternForVendorProductsResponse;
import com.snapdeal.shipping.core.model.SwitchHoldOnShipmentBySuborderRequest;
import com.snapdeal.shipping.core.model.SwitchHoldOnShipmentBySuborderResponse;
import com.snapdeal.shipping.core.model.UpdatePackageRequest;
import com.snapdeal.shipping.core.model.UpdatePackageResponse;
import com.snapdeal.shipping.core.model.UpdatePackageStatusRequest;
import com.snapdeal.shipping.core.model.UpdatePackageStatusResponse;
import com.snapdeal.shipping.sro.ShippingCartSRO;
import com.snapdeal.shipping.sro.ShippingCatalogSRO;
import com.snapdeal.shipping.sro.ShippingGroupSRO;
import com.snapdeal.shipping.sro.ShippingMethodSRO;
import com.snapdeal.shipping.sro.ShippingPackageSRO;
import com.snapdeal.shipping.sro.ShippingProductCategorySRO;
import com.snapdeal.shipping.sro.ShippingProductSRO;
import com.snapdeal.shipping.sro.ShippingProviderSRO;
import com.snapdeal.shipping.sro.VendorProductBannedZipsSRO;
import com.snapdeal.testing.helper.ApiHelper;
import com.snapdeal.testing.helper.DatabaseHelper;

public class ShippingTest extends ShippingTestBase {
	System.out.println("Starting of the program"); 
        System.out.println("Starting of the program second time");
	// This method will provide data to any test method that declares that its
	// Data Provider
	// is named "createShippingGroupCode"
	@DataProvider(name = "createShippingGroupCode")
	public Object[][] createShippingGroupCode() {

		return new Object[][] { new Object[] { "STD" },
				new Object[] { "COD-VOUCHERS" },
				new Object[] { "COD-VOUCHERS" },
				new Object[] { "EMAIL-MOBILE" } };

	};

	// This method will provide data to any test method that declares that its
	// Data Provider
	// is named "createShippingMethodCode"
	@DataProvider(name = "createShippingMethodCode")
	public Object[][] createShippingMethodCode() {

		return new Object[][] { new Object[] { 1, "STD" },
				new Object[] { 2, "COD" }, new Object[] { 3, "EMAIL" } };

		// {String.class, new String[] {"1", "2"}},

	};

	@DataProvider(name = "createNegativeInput")
	public Object[][] createNegativeGroupCode() {

		return new Object[][] { new Object[] { null },
				new Object[] { "%$^&*" }, new Object[] { "COD" },
				new Object[] { "999999999999999999999" } };

	};

	@DataProvider(name = "createCatalogType")
	public Object[][] createCatalogType() {

		return new Object[][] { new Object[] { "PRODUCT" },
				new Object[] { "DEAL" } };

	};

	@DataProvider(name = "createStatusCode")
	public Object[][] createStatusCode() {

		return new Object[][] {// new Object[] { "OUT_OF_STOCK","PWM","IOS",null
								// },
		// new Object[] { "PACKSLIP_RECEIVED","IOS","PWM",null },
		new Object[] { "VENDOR_RETURN_CONFIRMED", "PKG", "RTN", "50" }

		};

	};

	@DataProvider(name = "createsSwitchHoldOnShipmentBySuborder")
	public Object[][] createsSwitchHoldOnShipmentBySuborder() {

		return new Object[][] {// new Object[] { "IOS","13","0cac8d",true },
				new Object[] { "PWM","10","0cac8d",false,true },
				new Object[] { "PWM","13","0cac8d",false,false },
				new Object[] { "PWM","10","0cac8d",true,true },
				new Object[] { "PWM","13","0cac8d",true,false },
				};
	};
	@DataProvider(name = "createsSwitchHoldOnShipmentBySuborderNegative")
	public Object[][] createSwitchHoldOnShipmentBySuborderNegative() {

		return new Object[][] {// new Object[] { "IOS","13","0cac8d",true },
				new Object[] { "FFD","10","vendor_invalid",false,false },
				//new Object[] { "PWM","13","0cac8d",false,false },
				//new Object[] { "PWM","10","0cac8d",true,true },
				//new Object[] { "PWM","13","0cac8d",true,false },
				};
	};
	
	@DataProvider(name="createSwitchHoldOnNegativeParams")
	public Object[][] createSwitchHoldOnNegativeParams() {
		return new Object[][] {
				new Object[] {null,null},
				new Object[] {"9999999999999",false},
				new Object[] {"@#$$%%&^*&^",true},
				
		};
	}
	
	
	@DataProvider(name="createBannedZip")
	public Object[][] createBannedZip() {
		return new Object[][] {
				new Object[] {"248*"},
				new Object[] {null},
				new Object[] {"!11*,22*"},
				
		};
	}
	
	@DataProvider(name="createBannedZipNegative")
	public Object[][] createBannedZipNegative() {
		return new Object[][] {
				new Object[] {"*************************"},
				new Object[] {"!!!!!!!!!!!!!!!!!!!!!"},
				new Object[] {"00000000"},
				
		};
	}
	

	// The shipping APIs are being called from uniware or snapdeal.com but not
	// from vendor panel.
	// Vendor panel calls the methods internally which are all listed in the
	// vendor controller.Those APIs are not exposed.
	/**
	 * Test case for the method getActiveShippingProvider() Fetches the active
	 * shipping providers and assert if the list fetched in the response is same
	 * as expected
	 * 
	 * @throws Exception
	 */
	// @Parameters({ "environment" })
	@Test
	// (groups = { "Sniff"})
	public void getActiveShippingProvider() throws Exception {// String
																// environment)
		// System.out.println("environment ......"+environment);
		GetActiveShippingProvidersRequest request = new GetActiveShippingProvidersRequest();
		GetActiveShippingProvidersResponse response = ApiHelper
				.getActiveShippingProviders(request);
		AssertJUnit.assertTrue(response.isSuccessful());
		ArrayList<String> expectedShippingProviders = DatabaseHelper
				.getActiveShippingProvider();
		ArrayList<String> actualShippingProviders = new ArrayList<String>();
		for (ShippingProviderSRO sro : response.getProviders()) {
			actualShippingProviders.add(sro.getName());
		}
		assertEqualList(expectedShippingProviders, actualShippingProviders);
	}

	@Test
	public void getAllShippingGroups() throws Exception {
		GetAllShippingGroupsRequest request = new GetAllShippingGroupsRequest();
		GetAllShippingGroupsResponse response = ApiHelper
				.getAllShippingGroups(request);
		AssertJUnit.assertTrue(response.isSuccessful());
		ArrayList<ArrayList<String>> expectedShippingGroups = DatabaseHelper
				.getShippingGroups();
		AssertJUnit.assertEquals(expectedShippingGroups.size(), response
				.getShippingGroupSROs().size());
		for (ShippingGroupSRO sro : response.getShippingGroupSROs()) {
			int index = locateItem(expectedShippingGroups, sro.getCode(), 0);
			AssertJUnit.assertTrue(index >= 0);
			AssertJUnit.assertEquals(sro.getName(),
					expectedShippingGroups.get(index).get(1));
			Set<String> actualShippingMethods = sro.getShippingMethods();
			ArrayList<String> expectedShippingMethods = DatabaseHelper
					.getShippingMethodCodeByShippingGroup(sro.getCode());
			AssertJUnit.assertTrue(actualShippingMethods
					.containsAll(expectedShippingMethods));
		}
	}

	@Test(dataProvider = "createShippingGroupCode")
	public void getApplicableShippingMethods(String shippingGroup)
			throws Exception {
		GetApplicableShippingMethodsRequest request = new GetApplicableShippingMethodsRequest();
		request.setShippingGroupCode(shippingGroup);
		GetApplicableShippingMethodsResponse response = ApiHelper
				.getApplicableShippingMethods(request);
		AssertJUnit.assertTrue(response.isSuccessful());
		ArrayList<ArrayList<String>> expectedShippingMethods = DatabaseHelper
				.getShippingMethods(shippingGroup);
		for (ShippingMethodSRO sro : response.getShippingMethods()) {
			int index = locateItem(expectedShippingMethods, sro.getId() + "", 0);
			AssertJUnit.assertTrue(index >= 0);
			AssertJUnit.assertEquals(sro.getCode(), expectedShippingMethods
					.get(index).get(1));
			AssertJUnit.assertEquals(sro.getName(), expectedShippingMethods
					.get(index).get(2));
			// Assert.assertEquals(sro.getCharges(),expectedShippingMethods.get(index).get(4));
			AssertJUnit.assertEquals(sro.getChargesType() + "",
					expectedShippingMethods.get(index).get(5));
		}
	}

	/**
	 * @param shippingMethodCode
	 * @throws Exception
	 */
	@Test(dataProvider = "createShippingMethodCode")
	public void getShippingMethodByCode(int i, String shippingMethodCode)
			throws Exception {
		GetShippingMethodByCodeRequest request = new GetShippingMethodByCodeRequest();
		request.setCode(shippingMethodCode);
		GetShippingMethodByCodeResponse response = ApiHelper
				.getShippingMethodByCode(request);
		AssertJUnit.assertTrue(response.isSuccessful());
		ArrayList<ArrayList<String>> expectedShippingMethod = DatabaseHelper
				.getShippingMethodByCode(shippingMethodCode);
		AssertJUnit.assertEquals(expectedShippingMethod.get(0).get(0), response
				.getShippingMethodSRO().getId() + "");
		AssertJUnit.assertEquals(expectedShippingMethod.get(0).get(1), response
				.getShippingMethodSRO().getCode() + "");
		AssertJUnit.assertEquals(expectedShippingMethod.get(0).get(2), response
				.getShippingMethodSRO().getName() + "");
		// assertEquals(expectedShippingMethod.get(0).get(3),
		// response.getShippingMethodSRO().getPriority()+"");
		AssertJUnit.assertEquals(expectedShippingMethod.get(0).get(4), response
				.getShippingMethodSRO().getCharges() + "");
		AssertJUnit.assertEquals(expectedShippingMethod.get(0).get(5), response
				.getShippingMethodSRO().getChargesType() + "");

	}

	// the charges for all shipping methods are set to 0 in DB ->select
	// charges from shipping_method returns 0 for all shipping methods
	// because snapdeal does not charge for shipping products as of now

	@Test(dataProvider = "createShippingMethodCode")
	public void getShippingMethodCharges(int i, String shippingMethodCode) {
		GetShippingMethodChargesRequest request = new GetShippingMethodChargesRequest();
		List<ShippingCartSRO> cartSROList = new ArrayList<ShippingCartSRO>();
		ShippingCartSRO shippingCartSRO = new ShippingCartSRO();
		shippingCartSRO.setCatalogType(CatalogType.PRODUCT.type());
		shippingCartSRO.setQuantity(2);
		request.setCartSROs(cartSROList);
		request.setShippingMethod(shippingMethodCode);
		GetShippingMethodChargesResponse response = ApiHelper
				.getShippingMethodCharges(request);
		AssertJUnit.assertTrue(response.isSuccessful());
		AssertJUnit.assertEquals(response.getCharges().intValue(), 0);
	}

	/**
	 * Negative test case. Improper error message set for response when an
	 * incorrect value is passed as parameter. Expected: No Shipping Group found
	 * for code null/invalid param Actual: Error occured while calling Shipping
	 * jira LOGGED
	 */
	@Test(dataProvider = "createNegativeInput")
	public void getShippingGroupByCodeNegative(String shippingGroupCode) {
		GetShippingGroupByCodeRequest request = new GetShippingGroupByCodeRequest(
				shippingGroupCode);
		GetShippingGroupByCodeResponse response = ApiHelper
				.getShippingGroupByCode(request);
		System.out.println("sro" + response.getShippingGroupSRO().getName());
		AssertJUnit.assertFalse(response.isSuccessful());
	}

	// @Test(dataProvider="createShippingGroupCode")
	public void getShippingGroupByCodePositive(String shippingGroupCode) {
		GetShippingGroupByCodeRequest request = new GetShippingGroupByCodeRequest(
				shippingGroupCode);
		GetShippingGroupByCodeResponse response = ApiHelper
				.getShippingGroupByCode(request);
		AssertJUnit.assertTrue(response.isSuccessful());
		try {
			ArrayList<ArrayList<String>> expectedShippingGroup = DatabaseHelper
					.getShippingGroupByCode(shippingGroupCode);
			AssertJUnit.assertEquals(expectedShippingGroup.get(0).get(0),
					response.getShippingGroupSRO().getCode() + "");
			AssertJUnit.assertEquals(expectedShippingGroup.get(0).get(1),
					response.getShippingGroupSRO().getName() + "");

		} catch (Exception e) {
			AssertJUnit.fail(e.getMessage());
		}
	}

	// As discussed with amit dalal this method will be removed from the API in
	// sometime as the
	// same purpose is solved by the method isCatalogShippable
	// @Test(dataProvider="createShippingMethodCode")
	public void isShippingValid(String shippingMethodCode) {
		IsShippingValidRequest request = new IsShippingValidRequest();
		request.setShippingMethodCode(shippingMethodCode);
		request.setPinCode("248001");
		IsShippingValidResponse response = ApiHelper.isShippingValid(request);
		AssertJUnit.assertTrue(response.isSuccessful());
		AssertJUnit.assertTrue(response.isValid());
	}

	// @Test(dataProvider="createCatalogType")
	public void getAllShippingGroupsForCatalogPositive(String catalogType) {
		GetAllShippingGroupsForCatalogRequest request = new GetAllShippingGroupsForCatalogRequest();
		request.setCatalogType(catalogType);
		GetAllShippingGroupsForCatalogResponse response = ApiHelper
				.getAllShippingGroupsForCatalog(request);
		AssertJUnit.assertTrue(response.isSuccessful());
		try {
			ArrayList<ArrayList<String>> expectedShippingGroupsForCatalog = DatabaseHelper
					.getAllShippingGroupsForCatalog(catalogType);
			AssertJUnit.assertEquals(expectedShippingGroupsForCatalog.size(),
					response.getShippingGroupSROs().size());
			for (ShippingGroupSRO sro : response.getShippingGroupSROs()) {
				int index = locateItem(expectedShippingGroupsForCatalog,
						sro.getCode(), 0);
				AssertJUnit.assertTrue(index >= 0);
				AssertJUnit.assertEquals(sro.getName(),
						expectedShippingGroupsForCatalog.get(index).get(1));
				Set<String> actualShippingMethods = sro.getShippingMethods();
				ArrayList<String> expectedShippingMethods = DatabaseHelper
						.getShippingMethodCodeByShippingGroup(sro.getCode());
				AssertJUnit.assertTrue(actualShippingMethods
						.containsAll(expectedShippingMethods));
			}
		} catch (Exception e) {
			AssertJUnit
					.fail("Exception raised in DatabaseHelper.getAllShippingGroupsForCatalog");
		}
	}

	@Test(dataProvider = "createNegativeInput")
	public void getAllShippingGroupsForCatalogNegative(String catalogType) {
		GetAllShippingGroupsForCatalogRequest request = new GetAllShippingGroupsForCatalogRequest();
		request.setCatalogType(catalogType);
		GetAllShippingGroupsForCatalogResponse response;
		try {
			response = ApiHelper.getAllShippingGroupsForCatalog(request);
			AssertJUnit.assertTrue(response.isSuccessful());
		} catch (Exception e) {
			AssertJUnit.fail(e.getMessage());
		}
	}

	@Test(dataProvider = "createShippingMethodCode")
	public void getDeliveredPackages(Integer id, String shippingMethodCode) {
		GetDeliveredPackagesRequest request = new GetDeliveredPackagesRequest();
		DateRange range = new DateRange();
		Date start = new Date();
		start.setMonth(7);
		Date end = new Date();
		end.setMonth(8);
		range.setEnd(end);
		range.setStart(start);
		request.setRange(range);
		request.setShippingMethodCode(shippingMethodCode);
		GetDeliveredPackagesResponse response = ApiHelper
				.getDeliveredPackages(request);
		List<ShippingPackageSRO> sros = response.getShippingPackageSROs();
		try {
			ArrayList<ArrayList<String>> expectedDeliveredSROs = DatabaseHelper
					.getDeliveredPackages(range.getStart(), range.getEnd(),
							id.intValue());
			AssertJUnit.assertTrue(response.isSuccessful());
			AssertJUnit.assertEquals(expectedDeliveredSROs.size(), sros.size());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			AssertJUnit.fail("DatabaseHelper.getDeliveredPackages() failed");
		}

	}

	@Test(dataProvider = "createNegativeInput")
	public void getDeliveredPackagesNegative(String shippingMethodCode) {
		GetDeliveredPackagesRequest request = new GetDeliveredPackagesRequest();
		DateRange range = new DateRange();
		Date start = new Date();
		start.setMonth(0);
		Date end = new Date();
		end.setMonth(0);
		range.setEnd(end);
		range.setStart(start);
		request.setRange(range);
		request.setShippingMethodCode(shippingMethodCode);
		try {
			GetDeliveredPackagesResponse response = ApiHelper
					.getDeliveredPackages(request);
			AssertJUnit
					.assertTrue(response.getShippingPackageSROs().size() == 0);
			AssertJUnit.assertFalse(response.isSuccessful());
		} catch (Exception e) {
			AssertJUnit.fail(e.getMessage());
		}
	}

	// Throws Nullpointer exception for reason.getMesage().Logged JIRA for the
	// same.
	// JIRA 6461,6447 logged
	@Test
	public void isCatalogShippable() {
		String bannedZipPattern = "11*";
		String pinCode = "9999999999999";
		String pageUrl = "computers-laptops";
		String supc = "113665599999999999";
		IsCatalogShippableRequest request = new IsCatalogShippableRequest();
		ShippingCatalogSRO catalogSRO = new ShippingCatalogSRO();
		List<ShippingProductCategorySRO> categorySROs = new ArrayList<ShippingProductCategorySRO>();
		ShippingProductCategorySRO shippingProductCategorySRO = new ShippingProductCategorySRO();
		shippingProductCategorySRO.setBannedZipPattern(bannedZipPattern);
		shippingProductCategorySRO.setPageUrl(pageUrl);
		List<ShippingProductSRO> productSRO = new ArrayList<ShippingProductSRO>();
		ShippingProductSRO shippingProductSRO = new ShippingProductSRO();
		shippingProductSRO.setSupc(supc);
		shippingProductSRO.setProductCategorySROs(categorySROs);
		catalogSRO.setBannedZipPattern(bannedZipPattern);
		catalogSRO.setShippingProductSROs(productSRO);
		request.setCatalogSRO(catalogSRO);
		request.setPinCode(pinCode);
		request.setShippingMethodCode("COD");
		IsCatalogShippableResponse response = ApiHelper
				.isCatalogShippable(request);
		// Assert.assertTrue(response.isSuccessful());
		System.out.println("response.isShippable()=" + response.isShippable());
		CatalogNonShippableReason reason = response.getReason();
		System.out.println("response.getreason=" + reason.getMessage());
		/*
		 * try { ArrayList<ArrayList<String>> expectedShippingGroup =
		 * DatabaseHelper .getShippingGroupByCode(shippingGroupCode);
		 * Assert.assertEquals(expectedShippingGroup.get(0).get(0), response
		 * .getShippingGroupSRO().getCode() + "");
		 * Assert.assertEquals(expectedShippingGroup.get(0).get(1), response
		 * .getShippingGroupSRO().getName() + "");
		 * 
		 * } catch (Exception e) { Assert.fail(e.getMessage()); }
		 */
	}

	@Test(dataProvider = "createNegativeInput")
	public void checkIfCancellableNegative(String code) {
		CheckIfCancellableRequest request = new CheckIfCancellableRequest();
		request.setSuborderCode(code);
		CheckIfCancellableResponse response = ApiHelper
				.checkIfCancellable(request);
		AssertJUnit.assertFalse(response.isCancellable());
		AssertJUnit.assertFalse(response.isSuccessful());
		AssertJUnit.assertEquals(response.getMessage(),
				"No Shipping Order Item found for given suborder code. Sorry!");

	}

	/*
	 * the query "select distinct soi FROM ShippingOrderItem soi " +
	 * " left join fetch soi.shippingMethod " +
	 * " left join fetch soi.shippingDetail " 102 +
	 * " left join fetch soi.shippingPackage " +
	 * " left join fetch soi.shippingOrderItemStatus sois" +
	 * " where soi.suborderCode=:suborderCode" 103 +
	 * " and sois.code <> :status ") in ShippingDAOImpl
	 */

	/*
	 * select * from shipping.shipping_order_item soi left outer join
	 * shipping.shipping_method on soi.shipping_method_id =
	 * shipping.shipping_method.id left outer join shipping.shipping_detail sd
	 * on soi.shipping_detail_id=sd.id left outer join shipping.shipping_package
	 * sp on soi.shipping_package_id=sp.id left outer join
	 * shipping.shipping_order_item_status sois on soi.soi_status_code
	 * =sois.code where sois.code!=�VCH� and soi.suborder_code=�10020014� Uses
	 * memcache and also calls external unicommerce wsdl..need to test
	 */
	@Test
	public void checkIfCancellableUnPackagedPositive() {
		String suborder_id = null;
		CheckIfCancellableRequest request = new CheckIfCancellableRequest();
		try {
			// Testing if an unpacked soi is cancellable
			ArrayList<ArrayList<String>> soi = DatabaseHelper.getUnPackedSOI();
			suborder_id = soi.get(0).get(4);
			System.out.println("suborder id " + suborder_id);
			System.out.println("sizeeee" + soi.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setSuborderCode(suborder_id);
		CheckIfCancellableResponse response = ApiHelper
				.checkIfCancellable(request);
		AssertJUnit.assertTrue(response.isCancellable());
		AssertJUnit.assertTrue(response.isSuccessful());
		AssertJUnit.assertEquals(response.getMessage(), "SUCCESS");

	}

	// Testing if a packed soi is cancellable, package type is voucher and
	// electronic shipment(EMAIL)
	@Test
	public void checkIfCancellablePackagedPositive() {
		String suborder_id = null;
		CheckIfCancellableRequest request = new CheckIfCancellableRequest();
		try {
			ArrayList<ArrayList<String>> soi = DatabaseHelper.getPackedSOI();
			if (soi.size() == 1) {
				// Fetching the suborder_id
				suborder_id = soi.get(0).get(4);
				request.setSuborderCode(suborder_id);
				CheckIfCancellableResponse response = ApiHelper
						.checkIfCancellable(request);
				AssertJUnit.assertTrue(response.isCancellable());
				AssertJUnit.assertTrue(response.isSuccessful());
				AssertJUnit.assertEquals(response.getMessage(), "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// JIRA 6515,6521
	@Test
	public void getAllShippingPackageHistory() {
		GetAllShippingHistoryRequest request = new GetAllShippingHistoryRequest();
		request.setSuborderCode("10020014");
		GetAllShippingHistoryResponse response = ApiHelper
				.getAllShippingPackageHistory(request);
		System.out.println("response.getMessage" + response.getMessage());
		System.out.println("response.issuccesful" + response.isSuccessful());
		System.out.println("size of list ----"
				+ response.getHistoryList().size());

	}

	@Test
	public void getFulfillOrder() {
		FulfillOrderRequest request = new FulfillOrderRequest();
		// AddressDetailSRO addressDetailSRO = new AddressDetailSRO();

		// request.setAddressDetail(addressDetailSRO);
		// ApiHelper.
	}

	// @Test
	public void cancelShipmentPackagedSOI() {
		String suborder_id = null;
		CancelShipmentRequest request = new CancelShipmentRequest();
		try {
			ArrayList<ArrayList<String>> soi = DatabaseHelper.getPackedSOI();
			if (soi.size() == 1) {
				// Fetching the suborder_id
				suborder_id = soi.get(0).get(5);
				request.setSuborderCode(suborder_id);
				CancelShipmentResponse response = ApiHelper
						.cancelShipment(request);
				AssertJUnit.assertTrue(response.isCancelled());
				AssertJUnit.assertTrue(response.isSuccessful());
				AssertJUnit.assertEquals(response.getMessage(), "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void cancelShipmentUnPackagedSOI() {
		String suborder_id = null;
		CancelShipmentRequest request = new CancelShipmentRequest();
		try {
			ArrayList<ArrayList<String>> soi = DatabaseHelper.getUnPackedSOI();
			if (soi.size() == 1) {
				// Fetching the suborder_id
				suborder_id = soi.get(0).get(5);
				request.setSuborderCode(suborder_id);
				CancelShipmentResponse response = ApiHelper
						.cancelShipment(request);
				AssertJUnit.assertTrue(response.isCancelled());
				AssertJUnit.assertTrue(response.isSuccessful());
				AssertJUnit.assertEquals(response.getMessage(), "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// JIRA - 6600
	@Test
	public void updatePackagePositive() {
		String referenceCode = "PI1171883810";
		String statusCode = "70";
		UpdatePackageRequest request = new UpdatePackageRequest();
		UpdatePackageResponse response = ApiHelper.updatePackage(request);
		// request.setPackageId("1111");
		request.setReferenceCode(referenceCode);
		request.setStatusCode(statusCode);
		// request.setComment("updating reference code and status code");
		System.out.println("response.getmessage===" + response.getMessage());
		AssertJUnit.assertTrue(response.isSuccessful());
		AssertJUnit.assertEquals(response.getMessage(), "SUCCESS");

	}

	// JIRA 6763 logged for hard coding
	// JIRA LOGGED - 6798,6799 for junk code and also the invalid response
	// package_id=soi.suborder_code
	// updatePackage is called by uniware and it updates the status in soi as
	// per the hard coded status sent
	// by uniware in the request. So, uniware calls our API from an endpoint to
	// set the status of SOI
	// TO ASK----updateSOIStatus is set to false even if updateSuccessful and
	// getmessage are true
	// TO ASK.... COURIER_READY_FOR_PICKUP and OUT_OF_STOCK_PO_GENERATED has all
	// code commneted?
	// Uniware sends status=PACKSLIP_RECEIVED when an item in IOS is now
	// available, so it is marked to PWM again
	// TO ASK --when is the setByuseris used and others in response.set
	// Fails for PKG TO RTN when the package is in 70 state..need to ask
	@Test(dataProvider = "createStatusCode")
	public void updatePackageStatus(String requestedStatusCode,
			String prevCode, String postCode, String shipping_status_id) {
		String suborderCode = null;
		String manifest_id = "99";
		UpdatePackageStatusRequest request = new UpdatePackageStatusRequest();

		// Fetching the suborder_id
		try {
			ArrayList<ArrayList<String>> soi = DatabaseHelper
					.getSOIByShipping_id(shipping_status_id, prevCode);
			// System.out.println("size ...."+soi.size());
			System.out.println("soi.get(0)..." + soi.get(0));
			suborderCode = soi.get(0).get(4);
			// ShippingOrderItem sro=soi.get(0);
			System.out.println("subordercode..." + suborderCode);
			request.setPackageId(suborderCode);
			request.setStatusCode(requestedStatusCode);
			request.setManifestId(manifest_id);
			// request.setByUserId(byUserId);
			// request.setReferenceCodes(refs);
			UpdatePackageStatusResponse response = ApiHelper
					.updatePackageStatus(request);
			System.out.println("the response message==="
					+ response.getMessage());
			System.out.println("response.isUpdateSuccessful()=="
					+ response.isUpdateSuccessful());
			AssertJUnit.assertTrue(response.isSuccessful());
			AssertJUnit.assertEquals(response.getMessage(), "Updated");
			AssertJUnit
					.assertEquals(
							DatabaseHelper.getSOIBySuborderCode(suborderCode),
							postCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// soi is created in the table then isFulfilled should be true
	@Test
	public void isFulfilledPositive() {
		String suborder_id = null;
		IsFulfilledRequest request = new IsFulfilledRequest();
		try {
			ArrayList<ArrayList<String>> soi = DatabaseHelper.getPackedSOI();
			if (soi.size() == 1) {
				// Fetching the suborder_id
				suborder_id = soi.get(0).get(5);
				System.out.println("id....." + suborder_id);
				request.setSuborderCode(suborder_id);
				IsFulfilledResponse response = ApiHelper.isFulfilled(request);
				System.out.println("reponse message" + response.getMessage());
				AssertJUnit.assertTrue(response.isFulfilled());
				AssertJUnit.assertTrue(response.isSuccessful());
				AssertJUnit.assertEquals(response.getMessage(), "SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(dataProvider = "createNegativeInput")
	public void isFulfilledNegative(String suboder_id) {
		IsFulfilledRequest request = new IsFulfilledRequest();
		request.setSuborderCode(suboder_id);
		IsFulfilledResponse response = ApiHelper.isFulfilled(request);
		AssertJUnit.assertFalse(response.isFulfilled());
		AssertJUnit.assertFalse(response.isSuccessful());
		AssertJUnit.assertNotSame(response.getMessage(), "SUCCESS");
	}

	@Test(dataProvider = "createNegativeInput")
	public void getPackageByReferenceCodeNegative(String referenceCode) {
		GetPackageByReferenceCodeRequest request = new GetPackageByReferenceCodeRequest();
		request.setReferenceCode(referenceCode);
		GetPackageByReferenceCodeResponse response = ApiHelper
				.getPackageByReferenceCode(request);
		ShippingPackageSRO shippingPackageSRO = response
				.getShippingPackageSRO();
		System.out.println("error message" + response.getMessage());
		AssertJUnit.assertNull(shippingPackageSRO);
	}

	@Test
	public void getPackageByReferenceCodePositive() {
		String referenceCode = null;
		GetPackageByReferenceCodeRequest request = new GetPackageByReferenceCodeRequest();
		try {
			ArrayList<ArrayList<String>> sp = DatabaseHelper.getPackage();
			if (sp.size() > 0) {
				// Fetching the suborder_id
				referenceCode = sp.get(0).get(0);
				System.out.println("Reference code" + referenceCode);
				request.setReferenceCode(referenceCode);
				GetPackageByReferenceCodeResponse response = ApiHelper
						.getPackageByReferenceCode(request);
				ShippingPackageSRO shippingPackageSRO = response
						.getShippingPackageSRO();
				AssertJUnit.assertNotNull(shippingPackageSRO);
				AssertJUnit.assertEquals(shippingPackageSRO.getReferenceCode(),
						referenceCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(dataProvider = "createsSwitchHoldOnShipmentBySuborder")
	// select * from shipping_property where name like '%raymed%' to find the
	// categories that can be pushed to spinel
	// For above the code is List<String> dispatchCategoriesToMoveToWMS =
	// ConfigUtils.getStringList(Property.PUSH_TO_WAREHOUSE_CATEGORIES, ";");
	// The value of these properties is first looked in shippingconfig.xml if
	// not found then in shipping_property DB and then in property.java
	// Check for WMS enabled SOI - if in spinel and out of
	// stock/pwm,shippingPackage ==
	// null,VENDOR_CODE=0cac8d,soiDispatchCategoryId="PERFUMES"
	// Works for PWM but not for IOS???
	public void switchHoldOnShipmentBySuborder(String status,
			String dispatch_category, String vendor_code, Boolean doHold) {// new
																			// Object[]
																			// {
																			// "PWM","13","0cac8d",false
																			// }
		SwitchHoldOnShipmentBySuborderRequest request = new SwitchHoldOnShipmentBySuborderRequest();
		// Boolean doHold=true;
		String suborderCode = null;
		try {
			suborderCode = DatabaseHelper
					.getSOIForswitchHoldOnShipmentBySuborder(vendor_code,
							status, dispatch_category,doHold);
			System.out.println("subordercode==" + suborderCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AssertJUnit.fail();
		}
		request.setSuborderCode("12064452");
		request.setDoHold(doHold);
		SwitchHoldOnShipmentBySuborderResponse response = ApiHelper
				.switchHoldOnShipmentBySuborder(request);
		System.out.println("response.message===" + response.getMessage());
		System.out.println("response.issuccess=" + response.isSuccessful());
		AssertJUnit.assertEquals(response.getMessage(), "SUCCESS");
		AssertJUnit.assertEquals(response.isSuccessful(), true);
	}
	
	@Test(dataProvider="createSwitchHoldOnNegativeParams")//Suborder is null
	public void switchHoldOnShipmentBySuborderNegativeNull(String subOrder, Boolean onHold)
	{
		SwitchHoldOnShipmentBySuborderRequest request= new SwitchHoldOnShipmentBySuborderRequest();
		request.setSuborderCode(null);
		request.setDoHold(true);
		SwitchHoldOnShipmentBySuborderResponse response=ApiHelper.switchHoldOnShipmentBySuborder(request);
		System.out.println("response.message==="+response.getMessage());
		System.out.println("response.issuccess="+response.isSuccessful());
		Assert.assertEquals(response.getMessage(), "Suborder can't be null");
		Assert.assertEquals(response.isSuccessful(),false);
	}

	@Test(dataProvider="createSwitchHoldOnShipmentBySuborderNegative")
	public void switchHoldOnShipmentBySuborderNegative(String status,String dispatch_category,String vendor_code,Boolean doHold,Boolean withPackage)
	{
		SwitchHoldOnShipmentBySuborderRequest request= new SwitchHoldOnShipmentBySuborderRequest();
		String suborderCode=null;
		try {
			suborderCode = DatabaseHelper.getSOIForswitchHoldOnShipmentBySuborder(vendor_code,status,dispatch_category,withPackage);
			System.out.println("subordercode=="+suborderCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		request.setSuborderCode(suborderCode);
		request.setDoHold(true);
		SwitchHoldOnShipmentBySuborderResponse response=ApiHelper.switchHoldOnShipmentBySuborder(request);
		System.out.println("response.message==="+response.getMessage());
		System.out.println("response.issuccess="+response.isSuccessful());
		Assert.assertEquals(response.getMessage(), "Suborder can't be null");
		Assert.assertEquals(response.isSuccessful(),false);
	}
	//Why is a new row being inserted in product_banned_zip_pattern_mapping for every add? why isnt the existing supc being updated?
	//What should be the behaviour in case multiple zips are to be added preeceeding !
	//on what basis is isenabled being set?
	//Only *248 is being added ..y not the others though a success is returned for all 3 inputs
	//how does it handle null and comma seperated banned patterns?
	@Test(dataProvider="createBannedZip")
	public void addBannedZipForProduct(String bannedZip)
	{
		AddBannedZipPatternForProductRequest request = new AddBannedZipPatternForProductRequest();
		String supc=null;
		try
		{
			supc=DatabaseHelper.getSupcForAddBannedZipForProduct();
			System.out.println("the supc is /...."+supc);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		request.setBannedZip(bannedZip);
		request.setSupc(supc);
		AddBannedZipPatternForProductResponse response=ApiHelper.addBannedZipForProduct(request);
		System.out.println("response"+response.getMessage());
		System.out.println("response.is=="+response.isSuccessful());
		//add code to fetch again and verify from DB
		Assert.assertTrue(response.isSuccessful());
		Assert.assertEquals(response.getMessage(), "SUCCESS");
		
	}
	
	//Its updating any invalid banned zip?
	@Test(dataProvider="createBannedZipNegative")
	public void addBannedZipForProductNegative(String bannedZip)
	{
		AddBannedZipPatternForProductRequest request = new AddBannedZipPatternForProductRequest();
		String supc=null;
		try
		{
			supc=DatabaseHelper.getSupcForAddBannedZipForProduct();
			System.out.println("the supc is /...."+supc);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		request.setBannedZip(bannedZip);
		request.setSupc(supc);
		AddBannedZipPatternForProductResponse response=ApiHelper.addBannedZipForProduct(request);
		System.out.println("response"+response.getMessage());
		System.out.println("response.is=="+response.isSuccessful());
		Assert.assertFalse(response.isSuccessful());
	}
	
	//Log JIRA for " no check provided for null supc and vendor code.response is true even if null is passed
	@Test
	public void overrideBannedZipForVendorProducts()
	{
		OverrideBannedZipPatternForVendorProductsRequest request = new OverrideBannedZipPatternForVendorProductsRequest();
		VendorProductBannedZipsSRO vendorProductBannedZipsSRO= new VendorProductBannedZipsSRO();
		String supc=null,vendor=null;
		List<String> zips=new ArrayList<String>();
		zips.add("!24*");
		zips.add("!11*");
		vendorProductBannedZipsSRO.setBannedZip(zips);
		try {
		ArrayList<ArrayList<String>> supc_Vendor = DatabaseHelper.getSupcAndVendorForOverrideBannedZipForVendor();
		System.out.println("list is ..."+supc_Vendor);
		ArrayList<String> record = supc_Vendor.get(0);
			System.out.println("supc=="+record.get(5));
			supc=record.get(5);
			vendorProductBannedZipsSRO.setSupc(supc);
			vendor=record.get(6);
			System.out.println("vendor code =="+vendor);
			vendorProductBannedZipsSRO.setVendorCode(vendor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<VendorProductBannedZipsSRO> vendorProductBannedZips= new ArrayList<VendorProductBannedZipsSRO>();
		vendorProductBannedZips.add(vendorProductBannedZipsSRO);
		request.setVendorProductBannedZips(vendorProductBannedZips);
		OverrideBannedZipPatternForVendorProductsResponse response = ApiHelper.overrideBannedZipForVendorProducts(request);
		System.out.println("response==="+response.isSuccessful());
		System.out.println("response message"+ response.getMessage());
		Assert.assertTrue(response.isSuccessful());
		Assert.assertEquals(response.getMessage(), "SUCCESS");
		try {
			ArrayList<String> updatedRecord=DatabaseHelper.getProductVendorBannedZipPaternMapping(supc,vendor);
			System.out.println("the list is =="+updatedRecord);
			System.out.println("zips is ==="+zips);
			Assert.assertTrue(updatedRecord.containsAll(zips));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Log JIRA for incorrect response message sent when request params are null
	@Test
	public void overrideBannedZipForVendorProductsNegative()
	{
		OverrideBannedZipPatternForVendorProductsRequest request = new OverrideBannedZipPatternForVendorProductsRequest();
		OverrideBannedZipPatternForVendorProductsResponse response = ApiHelper.overrideBannedZipForVendorProducts(request);
		System.out.println("response==="+response.isSuccessful());
		System.out.println("response message"+ response.getMessage());
		Assert.assertFalse(response.isSuccessful());
		Assert.assertEquals(response.getMessage(), "Error occured while calling Shipping");
	}


	private int locateItem(ArrayList<ArrayList<String>> list,
			String searchValue, int itemNumber) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get(itemNumber).trim().equals(searchValue.trim())) {
				return i;
			}
		}

		return -1;
	}
}
