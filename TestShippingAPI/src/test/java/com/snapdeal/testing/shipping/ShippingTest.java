package com.snapdeal.testing.shipping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.snapdeal.base.model.common.CatalogType;
import com.snapdeal.base.transport.service.ITransportService.Protocol;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.base.validation.ValidationError;
import com.snapdeal.shipping.core.model.AddBannedZipPatternForProductRequest;
import com.snapdeal.shipping.core.model.AddBannedZipPatternForProductResponse;
import com.snapdeal.shipping.core.model.AddCategoryShippingProviderMappingRequest;
import com.snapdeal.shipping.core.model.AddCategoryShippingProviderMappingResponse;
import com.snapdeal.shipping.core.model.AddProductShippingProviderMappingRequest;
import com.snapdeal.shipping.core.model.AddProductShippingProviderMappingResponse;
import com.snapdeal.shipping.core.model.AddVendorShippingProviderMappingRequest;
import com.snapdeal.shipping.core.model.AddVendorShippingProviderMappingResponse;
import com.snapdeal.shipping.core.model.CancelShipmentRequest;
import com.snapdeal.shipping.core.model.CancelShipmentResponse;
import com.snapdeal.shipping.core.model.CatalogNonShippableReason;
import com.snapdeal.shipping.core.model.CheckIfCancellableRequest;
import com.snapdeal.shipping.core.model.CheckIfCancellableResponse;
import com.snapdeal.shipping.core.model.FulfillOrderRequest;
import com.snapdeal.shipping.core.model.FulfillOrderResponse;
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
import com.snapdeal.shipping.core.model.GetBannedZipPatternForProductAndVendorRequest;
import com.snapdeal.shipping.core.model.GetBannedZipPatternForProductAndVendorResponse;
import com.snapdeal.shipping.core.model.GetBannedZipPatternForProductRequest;
import com.snapdeal.shipping.core.model.GetBannedZipPatternForProductResponse;
import com.snapdeal.shipping.core.model.GetCancelledPackagesRequest;
import com.snapdeal.shipping.core.model.GetCancelledPackagesResponse;
import com.snapdeal.shipping.core.model.GetCategoryShippingProviderMappingsRequest;
import com.snapdeal.shipping.core.model.GetCategoryShippingProviderMappingsResponse;
import com.snapdeal.shipping.core.model.GetDeliveredPackagesRequest;
import com.snapdeal.shipping.core.model.GetDeliveredPackagesResponse;
import com.snapdeal.shipping.core.model.GetPackageByReferenceCodeRequest;
import com.snapdeal.shipping.core.model.GetPackageByReferenceCodeResponse;
import com.snapdeal.shipping.core.model.GetPackageBySuborderRequest;
import com.snapdeal.shipping.core.model.GetPackageBySuborderResponse;
import com.snapdeal.shipping.core.model.GetPossibleShippingMethodsForCatalogShipmentRequest;
import com.snapdeal.shipping.core.model.GetPossibleShippingMethodsForCatalogShipmentResponse;
import com.snapdeal.shipping.core.model.GetProductShippingProviderMappingsRequest;
import com.snapdeal.shipping.core.model.GetProductShippingProviderMappingsResponse;
import com.snapdeal.shipping.core.model.GetShippingChargesRequest;
import com.snapdeal.shipping.core.model.GetShippingChargesResponse;
import com.snapdeal.shipping.core.model.GetShippingGroupByCodeRequest;
import com.snapdeal.shipping.core.model.GetShippingGroupByCodeResponse;
import com.snapdeal.shipping.core.model.GetShippingInfoForSuborderRequest;
import com.snapdeal.shipping.core.model.GetShippingInfoForSuborderResponse;
import com.snapdeal.shipping.core.model.GetShippingMethodByCodeRequest;
import com.snapdeal.shipping.core.model.GetShippingMethodByCodeResponse;
import com.snapdeal.shipping.core.model.GetShippingMethodChargesRequest;
import com.snapdeal.shipping.core.model.GetShippingMethodChargesResponse;
import com.snapdeal.shipping.core.model.GetSubordersShippedByVendorRequest;
import com.snapdeal.shipping.core.model.GetSubordersShippedByVendorResponse;
import com.snapdeal.shipping.core.model.GetVendorShippingProviderMappingsRequest;
import com.snapdeal.shipping.core.model.GetVendorShippingProviderMappingsResponse;
import com.snapdeal.shipping.core.model.IsCatalogShippableRequest;
import com.snapdeal.shipping.core.model.IsCatalogShippableResponse;
import com.snapdeal.shipping.core.model.IsFulfilledRequest;
import com.snapdeal.shipping.core.model.IsFulfilledResponse;
import com.snapdeal.shipping.core.model.IsShippingValidRequest;
import com.snapdeal.shipping.core.model.IsShippingValidResponse;
import com.snapdeal.shipping.core.model.MarkPendingCancellationOnSuborderRequest;
import com.snapdeal.shipping.core.model.MarkPendingCancellationOnSuborderResponse;
import com.snapdeal.shipping.core.model.OverrideBannedZipPatternForVendorProductsRequest;
import com.snapdeal.shipping.core.model.OverrideBannedZipPatternForVendorProductsResponse;
import com.snapdeal.shipping.core.model.RestorePackageForSuborderRequest;
import com.snapdeal.shipping.core.model.RestorePackageForSuborderResponse;
import com.snapdeal.shipping.core.model.SwitchHoldOnShipmentBySuborderRequest;
import com.snapdeal.shipping.core.model.SwitchHoldOnShipmentBySuborderResponse;
import com.snapdeal.shipping.core.model.UpdatePackageRequest;
import com.snapdeal.shipping.core.model.UpdatePackageResponse;
import com.snapdeal.shipping.core.model.UpdatePackageStatusRequest;
import com.snapdeal.shipping.core.model.UpdatePackageStatusResponse;
import com.snapdeal.shipping.core.model.UpdateShippingUserRequest;
import com.snapdeal.shipping.core.model.UpdateShippingUserResponse;
import com.snapdeal.shipping.sro.AddressDetailSRO;
import com.snapdeal.shipping.sro.ShippingCartSRO;
import com.snapdeal.shipping.sro.ShippingCatalogSRO;
import com.snapdeal.shipping.sro.ShippingGroupSRO;
import com.snapdeal.shipping.sro.ShippingMethodSRO;
import com.snapdeal.shipping.sro.ShippingPackageSRO;
import com.snapdeal.shipping.sro.ShippingProductCategorySRO;
import com.snapdeal.shipping.sro.ShippingProductSRO;
import com.snapdeal.shipping.sro.ShippingProviderSRO;
import com.snapdeal.shipping.sro.ShippingUserSRO;
import com.snapdeal.shipping.sro.SuborderFullSRO;
import com.snapdeal.shipping.sro.VendorProductBannedZipsSRO;
import com.snapdeal.testing.helper.ApiHelper;
import com.snapdeal.testing.helper.DatabaseHelper;
import com.snapdeal.vendor.core.model.GetAllActiveVendorContactsRequest;

@Listeners(ShippingTestListener.class)
public class ShippingTest extends ShippingTestBase{
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

		return new Object[][] {new Object[] {1, "STD" },
				new Object[] {2, "COD"},new Object[] {3, "EMAIL"}
				};
		
		 // {String.class, new String[] {"1", "2"}},

	};

	@DataProvider(name = "pinCodes_shippingMethods")
	public Object[][] isValidShipping() {

		return new Object[][] {new Object[] {"248001", "STD","1"},
				new Object[] {"110005", "COD","2"}
				};
		
		 // {String.class, new String[] {"1", "2"}},

	};
	
	@DataProvider(name = "createNegativeInput")
	public Object[][] createNegativeGroupCode() {

		return new Object[][] { new Object[] { null },
				new Object[] { "%$^&*" },new Object[] {"999999999999999999999"} };

	};

	@DataProvider(name = "createCatalogType")
	public Object[][] createCatalogType() {

		return new Object[][] { new Object[] { "PRODUCT" },
				new Object[] { "DEAL" } };

	};

	@DataProvider(name = "createStatusCode")
	public Object[][] createStatusCode() {

		return new Object[][] { new Object[] { "OUT_OF_STOCK","PWM","IOS",null },
				new Object[] { "PACKSLIP_RECEIVED","IOS","PWM",null },
				new Object[] { "VENDOR_RETURN_CONFIRMED","PKG","RTN","50" },
				new Object[] { "VENDOR_RETURN_CONFIRMED","FFD","RTN","50" },
				
		
				};

	};
	
	@DataProvider(name = "createStatusCodeNegative")
	public Object[][] createStatusCodeNegative() {

		return new Object[][] { new Object[] { "OUT_OF_STOCK","PKG","PWM",null },
				//new Object[] { "OUT_OF_STOCK","IOS","PKG",null },
				new Object[] { "PACKSLIP_RECEIVED",null,"RTA",null },
				//new Object[] { "VENDOR_RETURN_CONFIRMED","PKG","RSP","50" } 
		
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
	
	
	@DataProvider(name="createUser")
	public Object[][] createUser() {
		return new Object[][] {
				new Object[] {"chhavi.suri@jasperindia.com", "123456"},
				new Object[] {"testtoday@yahoo.co.in","p@ssw0rd"},
		};
	}
	
	
	@DataProvider(name="createUserNegative")
	public Object[][] createUserNegative() {
		return new Object[][] {
				new Object[] {"automation@gmail", "password"},
				new Object[] {null,"p@ssw0rd"},
		};
	}
	
	@DataProvider(name="getPossibleShippingMethodsForCatalogShipment")
	public Object[][] getPossibleShippingMethodsForCatalogShipment() {
		return new Object[][] {
				new Object[] {"1002397", "248001", "110005"},
				//new Object[] {null,"p@ssw0rd"},
		};
	}
	
	
//The shipping APIs are being called from uniware or snapdeal.com but not from vendor panel.
//Vendor panel calls the methods internally which are all listed in the vendor controller.Those APIs are not exposed.
/**
 * Test case for the method getActiveShippingProvider()
 * Fetches the active shipping providers and assert if the list fetched in the response is same as expected
 * @throws Exception
 */
//@Parameters({ "env" })
//@Test
  public void getActiveShippingProvider() throws Exception {//String env 
GetActiveShippingProvidersRequest request= new GetActiveShippingProvidersRequest();
GetActiveShippingProvidersResponse response=ApiHelper.getActiveShippingProviders(request);
Assert.assertTrue(response.isSuccessful());
 ArrayList<String> expectedShippingProviders=DatabaseHelper.getActiveShippingProvider(); 
 ArrayList<String> actualShippingProviders = new ArrayList<String>(); 
 for(ShippingProviderSRO sro : response.getProviders()) {
actualShippingProviders.add(sro.getName());
 } 
 assertEqualList(expectedShippingProviders,actualShippingProviders);
}

//@Test
public void getAllShippingGroups() throws Exception {
GetAllShippingGroupsRequest request= new GetAllShippingGroupsRequest();
GetAllShippingGroupsResponse response= ApiHelper.getAllShippingGroups(request);
Assert.assertTrue(response.isSuccessful());
ArrayList<ArrayList<String>> expectedShippingGroups = DatabaseHelper.getShippingGroups();
Assert.assertEquals(expectedShippingGroups.size(),response.getShippingGroupSROs().size());
for (ShippingGroupSRO sro: response.getShippingGroupSROs()) 
{ int index= locateItem(expectedShippingGroups,sro.getCode(),0);
Assert.assertTrue(index>=0);
Assert.assertEquals(sro.getName(),expectedShippingGroups.get(index).get(1));
Set<String> actualShippingMethods = sro.getShippingMethods(); 
ArrayList<String> expectedShippingMethods=DatabaseHelper.getShippingMethodCodeByShippingGroup(sro.getCode());
Assert.assertTrue(actualShippingMethods.containsAll(expectedShippingMethods));
} }

//@Test(dataProvider = "createShippingGroupCode") 
	public void getApplicableShippingMethods(String shippingGroup) throws Exception {
GetApplicableShippingMethodsRequest request = new GetApplicableShippingMethodsRequest();
request.setShippingGroupCode(shippingGroup);
GetApplicableShippingMethodsResponse response = ApiHelper.getApplicableShippingMethods(request);
Assert.assertTrue(response.isSuccessful());
ArrayList<ArrayList<String>> expectedShippingMethods =DatabaseHelper.getShippingMethods(shippingGroup);
for(ShippingMethodSRO sro : response.getShippingMethods()) 
{ 
	  int index = locateItem(expectedShippingMethods, sro.getId() + "", 0); 
Assert.assertTrue(index >= 0);
Assert.assertEquals(sro.getCode(), expectedShippingMethods.get(index).get(1));
Assert.assertEquals(sro.getName(),
expectedShippingMethods.get(index).get(2));
//Assert.assertEquals(sro.getCharges(),expectedShippingMethods.get(index).get(4));
Assert.assertEquals(sro.getChargesType() + "",expectedShippingMethods.get(index).get(5)); 
}
}

	/**
	 * @param shippingMethodCode
	 * @throws Exception
	 */
	//@Test(dataProvider = "createShippingMethodCode")
	public void getShippingMethodByCode(int i,String shippingMethodCode)
			throws Exception {
		GetShippingMethodByCodeRequest request = new GetShippingMethodByCodeRequest();
		request.setCode(shippingMethodCode);
		GetShippingMethodByCodeResponse response = ApiHelper
				.getShippingMethodByCode(request);
		Assert.assertTrue(response.isSuccessful());
		ArrayList<ArrayList<String>> expectedShippingMethod = DatabaseHelper
				.getShippingMethodByCode(shippingMethodCode);
		Assert.assertEquals(expectedShippingMethod.get(0).get(0), response
				.getShippingMethodSRO().getId() + "");
		Assert.assertEquals(expectedShippingMethod.get(0).get(1), response
				.getShippingMethodSRO().getCode() + "");
		Assert.assertEquals(expectedShippingMethod.get(0).get(2), response
				.getShippingMethodSRO().getName() + "");
		// assertEquals(expectedShippingMethod.get(0).get(3),
		// response.getShippingMethodSRO().getPriority()+"");
		Assert.assertEquals(expectedShippingMethod.get(0).get(4), response
				.getShippingMethodSRO().getCharges() + "");
		Assert.assertEquals(expectedShippingMethod.get(0).get(5), response
				.getShippingMethodSRO().getChargesType() + "");

	}

	
	//charges are 0?  if (productAmt < ConfigUtils.getIntegerScalar(Property.PRODUCT_PRICE_THRESHOLD_FOR_SHIPPING_CHARGE) && productAmt != 0) {
	//     shippingCharges = ConfigUtils.getIntegerScalar(Property.SHIPPING_CHARGES);
	// PRODUCT_PRICE_THRESHOLD_FOR_SHIPPING_CHARGE             ("threshold.for.shipping.charge", "150"), and charges are 30
	// SHIPPING_CONFIG_RELATIVE_PATH     ("shipping.config.file.path", "/configuration/shippingconfig.xml"), in less /opt/configuration/shippingconfig.xml
	//which is overriden in the table shipping_property
	
	//@Test
	public void getShippingChargesPositive()
	{
		GetShippingChargesRequest request = new GetShippingChargesRequest();
		ShippingCartSRO cartSRO = new ShippingCartSRO();
		cartSRO.setCatalogId(1);
		cartSRO.setCatalogType("product");
		cartSRO.setQuantity(2);
		cartSRO.setSellingPrice(25);
		List<ShippingCartSRO> cartSROs = new ArrayList<ShippingCartSRO>();
		cartSROs.add(cartSRO);
		request.setCartSROs(cartSROs);
		request.setRequestProtocol(Protocol.PROTOCOL_PROTOSTUFF);
		Gson gson=new Gson();
		String json=gson.toJson(request);
		System.out.println("the json object is ==="+json);
		GetShippingChargesResponse response=ApiHelper.getShippingCharges(request);
		System.out.println("response=="+response.getCharges());
		System.out.println("getmesage=="+response.getMessage());
		Assert.assertTrue(response.isSuccessful());
		Assert.assertEquals(response.getMessage(), "SUCCESS");
		Assert.assertEquals(response.getCharges(),Integer.valueOf(50));
		
	}
	
	//response message is incorrect - catalog type should only be product
	//@Test
	public void getShippingChargesNegative()
	{
		GetShippingChargesRequest request = new GetShippingChargesRequest();
		ShippingCartSRO cartSRO = new ShippingCartSRO();
		cartSRO.setCatalogId(1);
		cartSRO.setCatalogType("deal");
		cartSRO.setQuantity(2);
		cartSRO.setSellingPrice(0);
		List<ShippingCartSRO> cartSROs = new ArrayList<ShippingCartSRO>();
		request.setCartSROs(cartSROs);
		GetShippingChargesResponse response=ApiHelper.getShippingCharges(request);
		System.out.println("response==="+response.isSuccessful());
		System.out.println("message ===="+response.getMessage());
		System.out.println("charges==="+response.getCharges());
		Assert.assertFalse(response.isSuccessful());
		Assert.assertEquals(response.getMessage(), "Catalog type is not product");
		Assert.assertEquals(response.getCharges(),Integer.valueOf(0));
		
	}
	
	  //the charges for all shipping methods are set to 0 in DB ->select
	 // charges from shipping_method returns 0 for all shipping methods 
	//because snapdeal does not charge for shipping products as of now
	 
	  //@Test(dataProvider="createShippingMethodCode") 
	  public void getShippingMethodCharges(int i,String shippingMethodCode) {
	  GetShippingMethodChargesRequest request = new GetShippingMethodChargesRequest(); 
	  List<ShippingCartSRO> cartSROList =new ArrayList<ShippingCartSRO>(); 
	  ShippingCartSRO shippingCartSRO = new ShippingCartSRO();
	  shippingCartSRO.setCatalogType(CatalogType.PRODUCT.type());
	  shippingCartSRO.setQuantity(2); request.setCartSROs(cartSROList);
	  request.setShippingMethod(shippingMethodCode);
	  GetShippingMethodChargesResponse response= ApiHelper.getShippingMethodCharges(request);
	  Assert.assertTrue(response.isSuccessful());
	  Assert.assertEquals(response.getCharges().intValue(),0);
	  }
	 

	/**
	 * Negative test case. Improper error message set for response when an
	 * incorrect value is passed as parameter. Expected: No Shipping Group found
	 * for code null/invalid param Actual: Error occured while calling Shipping jira LOGGED
	 */
	//@Test(dataProvider="createNegativeInput")
	public void getShippingGroupByCodeNegative(String shippingGroupCode) {
		GetShippingGroupByCodeRequest request = new GetShippingGroupByCodeRequest(
				shippingGroupCode);
		GetShippingGroupByCodeResponse response = ApiHelper
				.getShippingGroupByCode(request);
		System.out.println("sro"+response.getShippingGroupSRO().getName());
		Assert.assertFalse(response.isSuccessful());
	}

	 //@Test(dataProvider="createShippingGroupCode")
	public void getShippingGroupByCodePositive(String shippingGroupCode) {
		GetShippingGroupByCodeRequest request = new GetShippingGroupByCodeRequest(
				shippingGroupCode);
		GetShippingGroupByCodeResponse response = ApiHelper
				.getShippingGroupByCode(request);
		Assert.assertTrue(response.isSuccessful());
		try {
			ArrayList<ArrayList<String>> expectedShippingGroup = DatabaseHelper
					.getShippingGroupByCode(shippingGroupCode);
			Assert.assertEquals(expectedShippingGroup.get(0).get(0), response
					.getShippingGroupSRO().getCode() + "");
			Assert.assertEquals(expectedShippingGroup.get(0).get(1), response
					.getShippingGroupSRO().getName() + "");

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	//Correction - Saw this method being used in getPossibleShippingMethodsForCatalogShipment in 
	//shippingServiceImpl so it is being used and should be tested,nned to inform Amit
//As discussed with amit dalal this method will be removed from the API in sometime as the 
	//same purpose is solved by the method isCatalogShippable
	//@Test(dataProvider="pinCodes_shippingMethods")
	public void isShippingValidPositive(String pincode ,String shippingMethodCode,String methodId) {
		IsShippingValidRequest request = new IsShippingValidRequest();
		request.setShippingMethodCode(shippingMethodCode);
		request.setPinCode(pincode);
		IsShippingValidResponse response = ApiHelper.isShippingValid(request);
		Assert.assertTrue(response.isSuccessful());
		Assert.assertTrue(response.isValid());
		try {
			String id=DatabaseHelper.isShippingValid(pincode,methodId);
			//Verified pincode+method id combination exists in the DB
			Assert.assertEquals(id,pincode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}
	}

	//response.getmessage is success even though the shipping isValid is false.
	//@Test
	public void isShippingValidNegative() {
		String pincode="110005";
	    String method="EMAIL";
		String methodId="3";
		IsShippingValidRequest request = new IsShippingValidRequest();
		request.setShippingMethodCode(method);
		request.setPinCode(pincode);
		IsShippingValidResponse response = ApiHelper.isShippingValid(request);
		//Assert.assertFalse(response.isSuccessful());
		//Assert.assertEquals(response.getMessage(), expected);
		System.out.println("response=="+response.getMessage());
		Assert.assertFalse(response.isValid());
		try {
			String id=DatabaseHelper.isShippingValid(pincode,methodId);
			//Verified pincode+method id combination exists in the DB
			Assert.assertNull(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}
	}
	
	
	 //@Test(dataProvider="createCatalogType")
	public void getAllShippingGroupsForCatalogPositive(String catalogType) {
		GetAllShippingGroupsForCatalogRequest request = new GetAllShippingGroupsForCatalogRequest();
		request.setCatalogType(catalogType);
		GetAllShippingGroupsForCatalogResponse response = ApiHelper
				.getAllShippingGroupsForCatalog(request);
		Assert.assertTrue(response.isSuccessful());
		try {
			ArrayList<ArrayList<String>> expectedShippingGroupsForCatalog = DatabaseHelper
					.getAllShippingGroupsForCatalog(catalogType);
			Assert.assertEquals(expectedShippingGroupsForCatalog.size(),
					response.getShippingGroupSROs().size());
			for (ShippingGroupSRO sro : response.getShippingGroupSROs()) {
				int index = locateItem(expectedShippingGroupsForCatalog,
						sro.getCode(), 0);
				Assert.assertTrue(index >= 0);
				Assert.assertEquals(sro.getName(),
						expectedShippingGroupsForCatalog.get(index).get(1));
				Set<String> actualShippingMethods = sro.getShippingMethods();
				ArrayList<String> expectedShippingMethods = DatabaseHelper
						.getShippingMethodCodeByShippingGroup(sro.getCode());
				Assert.assertTrue(actualShippingMethods
						.containsAll(expectedShippingMethods));
			}
		} catch (Exception e) {
			Assert.fail("Exception raised in DatabaseHelper.getAllShippingGroupsForCatalog");
		}
	}

	 //@Test(dataProvider="createNegativeInput")
	public void getAllShippingGroupsForCatalogNegative(String catalogType) {
		GetAllShippingGroupsForCatalogRequest request = new GetAllShippingGroupsForCatalogRequest();
		request.setCatalogType(catalogType);
		GetAllShippingGroupsForCatalogResponse response;
		try {
			response = ApiHelper.getAllShippingGroupsForCatalog(request);
			Assert.assertTrue(response.isSuccessful());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	//@Test(dataProvider = "createShippingMethodCode")
	public void getDeliveredPackages(Integer id,String shippingMethodCode) {
		GetDeliveredPackagesRequest request = new GetDeliveredPackagesRequest();
		Date start =DateUtils.createDate(2010, 1, 10);
		Date end = DateUtils.createDate(2013,11, 10);
		DateRange range = new DateRange();
		range.setEnd(end);
		range.setStart(start);
		request.setRange(range);
		System.out.println("start==="+range.getStart());
		System.out.println("end=="+range.getEnd());
		request.setShippingMethodCode(shippingMethodCode);
		GetDeliveredPackagesResponse response = ApiHelper.getDeliveredPackages(request);
		List<ShippingPackageSRO> sros = response.getShippingPackageSROs();
		System.out.println("sros actual=="+sros);
		try {
			ArrayList<ArrayList<String>> expectedDeliveredSROs = DatabaseHelper
					.getDeliveredPackages(range.getStart(), range.getEnd(),id.intValue());
			System.out.println("expected sros=="+expectedDeliveredSROs);
			Assert.assertTrue(response.isSuccessful());
			Assert.assertEquals(expectedDeliveredSROs.size(),sros.size());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.fail("DatabaseHelper.getDeliveredPackages() failed");
		}
			
	}
	
	//@Test(dataProvider = "createNegativeInput")
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
		try
		{
			GetDeliveredPackagesResponse response = ApiHelper.getDeliveredPackages(request);
			Assert.assertTrue(response.getShippingPackageSROs().size()==0);
			Assert.assertFalse(response.isSuccessful());
		}catch(Exception e)
		{
			Assert.fail("Exception thrown by getDeliveredPackages");
		}
	}

	//Throws Nullpointer exception for reason.getMesage().Logged JIRA for the same.
	//JIRA 6461,6447 logged
	 //@Test
	public void isCatalogShippable() {
		 String bannedZipPattern="11*";
		 String pinCode="9999999999999";
		 String pageUrl="computers-laptops";
		 String supc="113665599999999999";
		IsCatalogShippableRequest request = new IsCatalogShippableRequest();
		ShippingCatalogSRO catalogSRO = new ShippingCatalogSRO();
		List<ShippingProductCategorySRO> categorySROs = new ArrayList<ShippingProductCategorySRO>();
		ShippingProductCategorySRO shippingProductCategorySRO=new ShippingProductCategorySRO();
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
		IsCatalogShippableResponse response = ApiHelper.isCatalogShippable(request);
		//Assert.assertTrue(response.isSuccessful());
		System.out.println("response.isShippable()="+response.isShippable());
		CatalogNonShippableReason reason=response.getReason();
		System.out.println("response.getreason="+reason.getMessage());
		/*try {
			ArrayList<ArrayList<String>> expectedShippingGroup = DatabaseHelper
					.getShippingGroupByCode(shippingGroupCode);			Assert.assertEquals(expectedShippingGroup.get(0).get(0), response
					.getShippingGroupSRO().getCode() + "");
			Assert.assertEquals(expectedShippingGroup.get(0).get(1), response
					.getShippingGroupSRO().getName() + "");

		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}*/
	}
	 
	 //@Test(dataProvider="createNegativeInput")
	 public void checkIfCancellableNegative(String code)
	 {
		 CheckIfCancellableRequest request= new CheckIfCancellableRequest();
		 request.setSuborderCode(code);
		 CheckIfCancellableResponse response = ApiHelper.checkIfCancellable(request);
		 Assert.assertFalse(response.isCancellable());
		 Assert.assertFalse(response.isSuccessful());
		 Assert.assertEquals(response.getMessage(),"No Shipping Order Item found for given suborder code. Sorry!");
		 
	 }
	/* the query "select distinct soi FROM ShippingOrderItem soi " + " left join fetch soi.shippingMethod " + " left join fetch soi.shippingDetail "
102                        + " left join fetch soi.shippingPackage " + " left join fetch soi.shippingOrderItemStatus sois" + " where soi.suborderCode=:suborderCode"
103                        + " and sois.code <> :status ") in ShippingDAOImpl

	 */
	 
	 /*select * from shipping.shipping_order_item soi left outer join shipping.shipping_method on soi.shipping_method_id = shipping.shipping_method.id left outer join shipping.shipping_detail sd on soi.shipping_detail_id=sd.id left outer join shipping.shipping_package sp on soi.shipping_package_id=sp.id left outer join shipping.shipping_order_item_status sois on soi.soi_status_code
=sois.code where sois.code!=”VCH” and soi.suborder_code=”10020014”
Uses memcache and also calls external unicommerce wsdl..need to test
*/
	//@Test
	 public void checkIfCancellableUnPackagedPositive()
	 {
		 String suborder_id = null;
		 CheckIfCancellableRequest request= new CheckIfCancellableRequest();
		 try
		 {
			 //Testing if an unpacked soi is cancellable
		 ArrayList<ArrayList<String>> soi = DatabaseHelper.getUnPackedSOI();
		 suborder_id=soi.get(0).get(4);
		 System.out.println("suborder id "+suborder_id);
	     System.out.println("sizeeee"+soi.size());
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 request.setSuborderCode(suborder_id);
		 CheckIfCancellableResponse response = ApiHelper.checkIfCancellable(request);
		  Assert.assertTrue(response.isCancellable());
		  Assert.assertTrue(response.isSuccessful());
		 Assert.assertEquals(response.getMessage(),"SUCCESS");
		 
		
	 }
	 
	 //Testing if a packed soi is cancellable, package type is voucher and electronic shipment(EMAIL)
	//@Test
	 public void checkIfCancellablePackagedPositive()
	 {
		 String suborder_id = null;
		 CheckIfCancellableRequest request= new CheckIfCancellableRequest();
		 try
		 {
		 ArrayList<ArrayList<String>> soi = DatabaseHelper.getPackedSOI();
		 if(soi.size()==1)
		 {
			 //Fetching the suborder_id
		 suborder_id=soi.get(0).get(4);
		 request.setSuborderCode(suborder_id);
		 CheckIfCancellableResponse response = ApiHelper.checkIfCancellable(request);
		  Assert.assertTrue(response.isCancellable());
		  Assert.assertTrue(response.isSuccessful());
		 Assert.assertEquals(response.getMessage(),"SUCCESS");
		 }
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
	 }
	 
	 
	 //JIRA 6515,6521
	 //@Test
	 public void getAllShippingPackageHistory()
	 {
		 GetAllShippingHistoryRequest request = new GetAllShippingHistoryRequest();
		 request.setSuborderCode("10020014");
		 GetAllShippingHistoryResponse response = ApiHelper.getAllShippingPackageHistory(request);
		 System.out.println("response.getMessage"+response.getMessage());
		 System.out.println("response.issuccesful"+response.isSuccessful()); 
		 System.out.println("size of list ----"+response.getHistoryList().size());
		 
	 }
	 
	 //@Test
	 public void fulfillOrder()
	 {
		 FulfillOrderRequest request = new FulfillOrderRequest();
		 AddressDetailSRO addressDetailSRO = new AddressDetailSRO();
		 addressDetailSRO.setAddressLine1("automation_test_addressLine1");
		 addressDetailSRO.setCity("automation_test_city");
		 addressDetailSRO.setPincode("110005");
		 request.setAddressDetail(addressDetailSRO);
		 request.setOrderCode("18001");
		 //saleorder table in oms
		 SuborderFullSRO sro= new SuborderFullSRO();
		 sro.setInventoryTxnCode("1000263");
		 sro.setSuborderCode("21534");
		 Set<SuborderFullSRO> suborders=new HashSet<SuborderFullSRO>();
		 suborders.add(sro);
		 request.setSuborders(suborders);
		 FulfillOrderResponse response = ApiHelper.fulfillOrder(request);
		 System.out.println("response==="+response.isSuccessful());
		 System.out.println("message===+"+response.getMessage());
		 Assert.assertTrue(response.isSuccessful());
	 }
	 //@Test
	 public void cancelShipmentPackagedSOI()
	 {
		 String suborder_id = null;
		 CancelShipmentRequest  request = new CancelShipmentRequest();
		try
		 {
		 ArrayList<ArrayList<String>> soi = DatabaseHelper.getPackedSOI();
		 if(soi.size()==1)
		 {
			 //Fetching the suborder_id
		 suborder_id=soi.get(0).get(5);
		 request.setSuborderCode(suborder_id);
		 CancelShipmentResponse response = ApiHelper.cancelShipment(request);
		  Assert.assertTrue(response.isCancelled());
		  Assert.assertTrue(response.isSuccessful());
		 Assert.assertEquals(response.getMessage(),"SUCCESS");
		 }
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }
	 
	 //@Test
	 public void cancelShipmentUnPackagedSOI()
	 {
		 String suborder_id = null;
		 CancelShipmentRequest  request = new CancelShipmentRequest();
		try
		 {
		 ArrayList<ArrayList<String>> soi = DatabaseHelper.getUnPackedSOI();
		 if(soi.size()==1)
		 {
			 //Fetching the suborder_id
		 suborder_id=soi.get(0).get(5);
		 request.setSuborderCode(suborder_id);
		 CancelShipmentResponse response = ApiHelper.cancelShipment(request);
		  Assert.assertTrue(response.isCancelled());
		  Assert.assertTrue(response.isSuccessful());
		 Assert.assertEquals(response.getMessage(),"SUCCESS");
		 }
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }
	 
	 
	 //Log JIRA - status for a shipping package can not be updated to CREATED..works fine for all other status
	 //JIRA - 6600
	//@Test
	 public void updatePackagePositive()
	 {
		 String referenceCode=null;
		 String statusCode=null;//="CREATED";
		 List<String> statusCodes=null;
		try {
			referenceCode = DatabaseHelper.getReferenceCode();
			System.out.println("the reference_code is ==="+referenceCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			 statusCodes=DatabaseHelper.getStatusCode();
			System.out.println("size of list is =="+statusCodes.size());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<String> it=statusCodes.iterator();
		while(it.hasNext())
		{
		 UpdatePackageRequest request = new UpdatePackageRequest();
		 statusCode=it.next();
		 if(statusCode.equals("CREATED")){
		 System.out.println("status code =="+statusCode);
		 request.setReferenceCode(referenceCode);
		 System.out.println("reference code =="+referenceCode);
		 request.setStatusCode(statusCode);
		 request.setComment("updating reference code and status code");
		 UpdatePackageResponse response= ApiHelper.updatePackage(request);
		 System.out.println("response.getmessage==="+response.getMessage());
		 List<ValidationError> errors=response.getValidationErrors();
		 System.out.println("validation error=="+errors);
		 Assert.assertTrue(response.isSuccessful());
		 Assert.assertEquals(response.getMessage(),"SUCCESS");
		 }
		
		}
	 }
	 
	//Log JIRA response.getValidation Errors is null and response.message is "error occured while calling shipping" which should be status code is invalid
	//@Test
	public void updatePackageNegative()
	{
		 String referenceCode=null;
		 String statusCode="INVALID";
		try {
			referenceCode = DatabaseHelper.getReferenceCode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 UpdatePackageRequest request = new UpdatePackageRequest();
			 request.setReferenceCode(referenceCode);
		 System.out.println("reference code =="+referenceCode);
		 request.setStatusCode(statusCode);
		 request.setComment("updating reference code and status code");
		 UpdatePackageResponse response= ApiHelper.updatePackage(request);
		 System.out.println("response.getmessage==="+response.getMessage());
		 List<ValidationError> errors=response.getValidationErrors();
		 System.out.println("validation error=="+errors);
		 Assert.assertTrue(errors.size()>0);
		 Assert.assertFalse(response.isSuccessful());
		 Assert.assertEquals(response.getMessage(),"Error occured while calling Shipping");
		 
	}
	 
	 //JIRA 6763 logged for hard coding
	 //JIRA LOGGED - 6798,6799 for junk code and also the invalid response(updatesoistatus is always false)
	//package_id=soi.suborder_code
	 //updatePackage is called by uniware and it updates the status in soi as per the hard coded status sent
	 //by uniware in the request. So, uniware calls our API from an endpoint to set the status of SOI
	 //TO ASK----updateSOIStatus is set to false even if updateSuccessful and getmessage are true
	 //TO ASK.... COURIER_READY_FOR_PICKUP and OUT_OF_STOCK_PO_GENERATED has all code commented?
	 //Uniware sends status=PACKSLIP_RECEIVED when an item in IOS is now available, so it is marked to PWM again
	//TO ASK --when is the setByuseris used and others in response.set
	 //@Test(dataProvider="createStatusCode")
	 public void updatePackageStatusPositive(String requestedStatusCode,String prevCode,String postCode,String shipping_status_id)
	 {
		 String suborderCode=null;
		 String manifest_id="99";
		 UpdatePackageStatusRequest request = new UpdatePackageStatusRequest();
		 
			 //Fetching the suborder_id
		 try
		 {
			ArrayList<ArrayList<String>> soi = DatabaseHelper.getSOIByShipping_id(shipping_status_id,prevCode);
			// System.out.println("size ...."+soi.size());
			System.out.println("soi.get(0)..."+soi.get(0));
		     suborderCode = soi.get(0).get(4);
			 System.out.println("subordercode..."+suborderCode);
		 request.setPackageId(suborderCode);
		 request.setStatusCode(requestedStatusCode);
		 request.setManifestId(manifest_id);
		 //request.setByUserId(byUserId);
		 //request.setReferenceCodes(refs);
		 UpdatePackageStatusResponse response = ApiHelper.updatePackageStatus(request);
		 System.out.println("the response message==="+response.getMessage());
		 System.out.println("response.isUpdateSuccessful()=="+response.isUpdateSuccessful());
		   Assert.assertTrue(response.isSuccessful());
		 Assert.assertEquals(response.getMessage(),"Updated");
		 Assert.assertEquals(DatabaseHelper.getSOIStatusBySuborderCode(suborderCode), postCode);
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		
	 }
	 
	 //@Test(dataProvider="createStatusCodeNegative")
	 public void updatePackageStatusNegative(String requestedStatusCode,String prevCode,String postCode,String shipping_status_id)
	 {
		 String suborderCode=null;
		 String manifest_id="99";
		 UpdatePackageStatusRequest request = new UpdatePackageStatusRequest();
			 //Fetching the suborder_id
		 try
		 {
			 if(prevCode!=null)
			 {
			ArrayList<ArrayList<String>> soi = DatabaseHelper.getSOIByShipping_id(shipping_status_id,prevCode);
			// System.out.println("size ...."+soi.size());
			System.out.println("soi.get(0)..."+soi.get(0));
		     suborderCode = soi.get(0).get(4);
			 System.out.println("subordercode..."+suborderCode);
		 request.setPackageId(suborderCode);
		 request.setStatusCode(requestedStatusCode);
		 request.setManifestId(manifest_id);
			 }
		 //request.setByUserId(byUserId);
		 //request.setReferenceCodes(refs);
		 UpdatePackageStatusResponse response = ApiHelper.updatePackageStatus(request);
		 System.out.println("response.isSuccessful()=="+response.isSuccessful());
		 System.out.println("the response message==="+response.getMessage());
		 System.out.println("response.isUpdateSuccessful()=="+response.isUpdateSuccessful());
		   Assert.assertFalse(response.isSuccessful());
		   if(prevCode!=null)
		 Assert.assertEquals(response.getMessage(),"No shipping transition allowed for the given requested suborder :"+suborderCode);
		   else
			  Assert.assertEquals(response.getMessage(),"SubOrder Code not valid.");
		 Assert.assertEquals(DatabaseHelper.getSOIStatusBySuborderCode(suborderCode), prevCode);
			 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		
	 }
	 
	 
	 //soi is created in the table then isFulfilled should be true
	//@Test
	 public void isFulfilledPositive()
	 {
		String suborder_id=null;
		IsFulfilledRequest request = new IsFulfilledRequest();	
		try
		 {
		 ArrayList<ArrayList<String>> soi = DatabaseHelper.getPackedSOI();
		 if(soi.size()==1)
		 {
			 //Fetching the suborder_id
		 suborder_id=soi.get(0).get(4);
		 System.out.println("id....."+suborder_id);
		 request.setSuborderCode(suborder_id);
		 IsFulfilledResponse response = ApiHelper.isFulfilled(request);
		 System.out.println("reponse message"+response.getMessage());
		  Assert.assertTrue(response.isFulfilled());
		  Assert.assertTrue(response.isSuccessful());
		 Assert.assertEquals(response.getMessage(),"SUCCESS");
		 }
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		
	 }
	
	//@Test(dataProvider="createNegativeInput")
	public void isFulfilledNegative(String suboder_id)
	{
		IsFulfilledRequest request = new IsFulfilledRequest();	
		request.setSuborderCode(suboder_id);
		 IsFulfilledResponse response = ApiHelper.isFulfilled(request);
		  Assert.assertFalse(response.isFulfilled());
		  Assert.assertFalse(response.isSuccessful());
		 Assert.assertNotSame(response.getMessage(),"SUCCESS");
	}
	
	//@Test(dataProvider="createNegativeInput")
	public void getPackageByReferenceCodeNegative(String referenceCode)
	{
		GetPackageByReferenceCodeRequest request = new GetPackageByReferenceCodeRequest();
		request.setReferenceCode(referenceCode);
		GetPackageByReferenceCodeResponse response =ApiHelper.getPackageByReferenceCode(request);
		ShippingPackageSRO shippingPackageSRO =response.getShippingPackageSRO();
		System.out.println("error message"+response.getMessage());
		Assert.assertNull(shippingPackageSRO);
	}
	
	//@Test
	public void getPackageByReferenceCodePositive()
	{
		String referenceCode=null;
		GetPackageByReferenceCodeRequest request = new GetPackageByReferenceCodeRequest();	
		try
		 {
		 ArrayList<ArrayList<String>> sp = DatabaseHelper.getPackage();
		 if(sp.size()>0)
		 {
			 //Fetching the suborder_id
			 referenceCode=sp.get(0).get(0);
			 System.out.println("Reference code"+referenceCode);
		request.setReferenceCode(referenceCode);
		GetPackageByReferenceCodeResponse response =ApiHelper.getPackageByReferenceCode(request);
		ShippingPackageSRO shippingPackageSRO =response.getShippingPackageSRO();
		Assert.assertNotNull(shippingPackageSRO);
		Assert.assertEquals(shippingPackageSRO.getReferenceCode(), referenceCode);
	}}catch(Exception e)
	{
		e.printStackTrace();
	}
}
	
	//@Test(dataProvider="createsSwitchHoldOnShipmentBySuborder")
	//select * from shipping_property where name like '%raymed%' to find the categories that can be pushed to spinel
	//For above the code is  List<String> dispatchCategoriesToMoveToWMS = ConfigUtils.getStringList(Property.PUSH_TO_WAREHOUSE_CATEGORIES, ";");
	//The value of these properties is first looked in shippingconfig.xml if not found then in shipping_property DB and then in property.java
//Check for WMS enabled SOI - if in spinel and out of stock/pwm,shippingPackage == null,VENDOR_CODE=0cac8d,soiDispatchCategoryId="PERFUMES"
	//Works for PWM but not for IOS???
	// { "PWM","10","0cac8d",false,true },
	public void switchHoldOnShipmentBySuborder(String status,String dispatch_category,String vendor_code,Boolean doHold,Boolean withPackage)
	{
		SwitchHoldOnShipmentBySuborderRequest request= new SwitchHoldOnShipmentBySuborderRequest();
		String suborderCode=null;
		/*try {
			suborderCode = DatabaseHelper.getSOIForswitchHoldOnShipmentBySuborder(vendor_code,status,dispatch_category,withPackage);
			System.out.println("subordercode=="+suborderCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}*/
		request.setSuborderCode(suborderCode);
		request.setDoHold(doHold);
		SwitchHoldOnShipmentBySuborderResponse response=ApiHelper.switchHoldOnShipmentBySuborder(request);
		System.out.println("response.message==="+response.getMessage());
		System.out.println("response.issuccess="+response.isSuccessful());
		Assert.assertEquals(response.getMessage(), "SUCCESS");
		Assert.assertEquals(response.isSuccessful(),true);
	}
	
	//@Test(dataProvider="createSwitchHoldOnNegativeParams")//Suborder is null
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

	//@Test(dataProvider="createSwitchHoldOnShipmentBySuborderNegative")
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
	//@Test(dataProvider="createBannedZip")
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
	
	//@Test
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
	//JIRA 6683
	//@Test
	public void overrideBannedZipForVendorProductsNegative()
	{
		OverrideBannedZipPatternForVendorProductsRequest request = new OverrideBannedZipPatternForVendorProductsRequest();
		OverrideBannedZipPatternForVendorProductsResponse response = ApiHelper.overrideBannedZipForVendorProducts(request);
		System.out.println("response==="+response.isSuccessful());
		System.out.println("response message"+ response.getMessage());
		Assert.assertFalse(response.isSuccessful());
		Assert.assertEquals(response.getMessage(), "Null Request params not accepted");
	}

	
	/*@DataProvider(name="createUser")
	public Object[][] createUser() {
		return new Object[][] {
				new Object[] {"automation@gmail.com", "password"},
				new Object[] {"test@yahoo.co.in","p@ssw0rd"},
		};
	}*/
	
	
	//JIRA - 7081(unable to login using the user created
	//log jira response.getmessage is null
	//It creates a new user but does not update it the next time,no error thrown but seen in table password does not get updated???
	//also, unable to login as that user in admin panel or snapdeal .com
	//@Test(dataProvider="createUser")
	public void updateShippingUserPositive(String email,String password)
	{
		UpdateShippingUserRequest request = new UpdateShippingUserRequest();
		ShippingUserSRO shippingUserSRO = new ShippingUserSRO();
		shippingUserSRO.setEmail(email);
		shippingUserSRO.setPassword(password);
		request.setUserSRO(shippingUserSRO);
		UpdateShippingUserResponse response = ApiHelper.updateShippingUser(request);
		Assert.assertTrue(response.isSuccessful());
		Assert.assertEquals(response.getMessage(), "SUCCESS");
		try {
			String user_email= DatabaseHelper.getUser(email);
			Assert.assertEquals(user_email, email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	// JIRA user with invalid email is added successfully(automation@gmail is added)
	//jira 7078
	//@Test(dataProvider="createUserNegative")
	public void updateShippingUserNegative(String email,String password)
	{
		UpdateShippingUserRequest request = new UpdateShippingUserRequest();
		ShippingUserSRO shippingUserSRO = new ShippingUserSRO();
		shippingUserSRO.setEmail(email);
		shippingUserSRO.setPassword(password);
		request.setUserSRO(shippingUserSRO);
		UpdateShippingUserResponse response = ApiHelper.updateShippingUser(request);
		System.out.println("response=="+response.getMessage());
		Assert.assertFalse(response.isSuccessful());
	}
//new Object[] {"1002397", "248001", "110005"}
	//gets the pincode,method for it from shipping_location and then it fetches the vendors from ipms(seperate DB) who can ship that product(supc)
	//to the pincode with that method(cod,std)
	//@Test(dataProvider="getPossibleShippingMethodsForCatalogShipment")
	public void getPossibleShippingMethodsForCatalogShipmentPositive(String supc,String pincode,String bannedZipPattern)
	{
		 GetPossibleShippingMethodsForCatalogShipmentRequest request=new GetPossibleShippingMethodsForCatalogShipmentRequest();
		 List<ShippingProductCategorySRO> categorySROs= new ArrayList<ShippingProductCategorySRO>();
		 ShippingProductCategorySRO productCategorySRO = new ShippingProductCategorySRO();
		 productCategorySRO.setBannedZipPattern(pincode);
		 categorySROs.add(productCategorySRO);
		 //productCategorySRO.setPageUrl(pageUrl);
		 List<ShippingProductSRO> productSROs= new ArrayList<ShippingProductSRO>();
		 ShippingProductSRO productSRO = new ShippingProductSRO();
		 productSRO.setProductCategorySROs(categorySROs);
		 productSRO.setSupc(supc);
		 productSROs.add(productSRO);
		 ShippingCatalogSRO catalogSRO = new ShippingCatalogSRO();
		 catalogSRO.setBannedZipPattern(bannedZipPattern);
		 catalogSRO.setShippingProductSROs(productSROs);
		 request.setPinCode(pincode);
		 request.setCatalogSRO(catalogSRO);
		 GetPossibleShippingMethodsForCatalogShipmentResponse response=ApiHelper.getPossibleShippingMethodsForCatalogShipment(request);
		 System.out.println("response.getmessage=="+response.getMessage());
		 System.out.println("response.issuccessful=="+response.isSuccessful());
		 Assert.assertTrue(response.isSuccessful());
		 List<String> shippingMethods=response.getPossibleShippingMethods();
		 System.out.println("list is =="+shippingMethods);
	}

	//response is successful although the supc is incorrect, getmessage is success(though supc is wrong,should display the proper message)
	//JIRA -6683
	//@Test
	public void getPossibleShippingMethodsForCatalogShipmentNegative()
	{
		 GetPossibleShippingMethodsForCatalogShipmentRequest request=new GetPossibleShippingMethodsForCatalogShipmentRequest();
		 List<ShippingProductCategorySRO> categorySROs= new ArrayList<ShippingProductCategorySRO>();
		 ShippingProductCategorySRO productCategorySRO = new ShippingProductCategorySRO();
		 productCategorySRO.setBannedZipPattern("248001");
		 categorySROs.add(productCategorySRO);
		 //productCategorySRO.setPageUrl(pageUrl);
		 List<ShippingProductSRO> productSROs= new ArrayList<ShippingProductSRO>();
		 ShippingProductSRO productSRO = new ShippingProductSRO();
		 productSRO.setProductCategorySROs(categorySROs);
		 productSRO.setSupc("11");
		 productSROs.add(productSRO);
		 ShippingCatalogSRO catalogSRO = new ShippingCatalogSRO();
		 catalogSRO.setBannedZipPattern("248001");
		 catalogSRO.setShippingProductSROs(productSROs);
		 request.setPinCode("248001");
		 request.setCatalogSRO(catalogSRO);
		 GetPossibleShippingMethodsForCatalogShipmentResponse response=ApiHelper.getPossibleShippingMethodsForCatalogShipment(request);
		 System.out.println("response.getmessage=="+response.getMessage());
		 System.out.println("response.issuccessful=="+response.isSuccessful());
		 Assert.assertFalse(response.isSuccessful());
		 List<String> shippingMethods=response.getPossibleShippingMethods();
		 System.out.println("list is =="+shippingMethods);
		 Assert.assertEquals(shippingMethods.size(), 0);
	}
	
	//@Test
	public void getPackageBySuborderPositive()
	{
		GetPackageBySuborderRequest request = new GetPackageBySuborderRequest();
		String suborderCode=null;
		try {
			suborderCode = DatabaseHelper.getSuborderCode();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String suborderCode=DatabaseHelper.getSuborderCode();
		request.setSuborderCode(suborderCode);
		GetPackageBySuborderResponse response = ApiHelper.getPackageBySuborder(request);
		Assert.assertNotNull(response.getShippingPackageSRO());
		ShippingPackageSRO shippingPackageSRO=response.getShippingPackageSRO();
		try {
			ArrayList<ArrayList<String>> expected = DatabaseHelper.getPackageBySuborder(suborderCode);
			ArrayList<String> sro=expected.get(0);
			Assert.assertEquals(shippingPackageSRO.getOrderCode(),sro.get(0));
			Assert.assertEquals(shippingPackageSRO.getSubOrderCodes().get(0),sro.get(1));
			Assert.assertEquals(String.valueOf(shippingPackageSRO.getPackageType()),sro.get(2));
			Assert.assertEquals(shippingPackageSRO.getShippingMethodCode(),sro.get(3));
			Assert.assertEquals(shippingPackageSRO.getShippingProviderName(),sro.get(4));
			Assert.assertEquals(shippingPackageSRO.getStatusCode(),sro.get(5));
			Assert.assertEquals(shippingPackageSRO.getStatusDescription(),sro.get(6));
			Assert.assertEquals(String.valueOf(shippingPackageSRO.getStatusId()),sro.get(7));
			Assert.assertEquals(shippingPackageSRO.getShippedDate(),sro.get(8));
			Assert.assertEquals(shippingPackageSRO.getTrackingNumber(),sro.get(9));
			Assert.assertEquals(shippingPackageSRO.getReferenceCode(),sro.get(10));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Log jira message should be - invalid subordercode
	//@Test
	public void getPackageBySuborderNegative()
	{
		GetPackageBySuborderRequest request = new GetPackageBySuborderRequest();
		String suborderCode="@#@#$$%$%^";
		//String suborderCode=DatabaseHelper.getSuborderCode();
		request.setSuborderCode(suborderCode);
		GetPackageBySuborderResponse response = ApiHelper.getPackageBySuborder(request);
		Assert.assertNull(response.getShippingPackageSRO());
		//ShippingPackageSRO shippingPackageSRO=response.getShippingPackageSRO();
		//Assert.assertTrue(response.isSuccessful());
		System.out.println("response=="+response.getMessage());
	}
	
	
	//16:33:51,590#-586064875523716 WARN  - http-bio-5050-exec-233 - [DispatcherServlet] - 
	//No mapping found for HTTP request with URI [/service/shipping/addVendorShippingProviderMappings] in DispatcherServlet with name 'spring'
   // JIRA 7082
	//@Test
	public void addVendorShippingProviderMappings()
	{
		AddVendorShippingProviderMappingRequest request = new AddVendorShippingProviderMappingRequest();
		List<ShippingProviderSRO> providerSROs= new ArrayList<ShippingProviderSRO>();
		ShippingProviderSRO providerSRO= new ShippingProviderSRO();
		//providerSRO.setCode("BLUEDART");
		providerSRO.setId(4);
		//providerSRO.setName("sp_name");
		//providerSRO.setTrackingLink("trackingLink");
		providerSROs.add(providerSRO);
		List<ShippingMethodSRO> methodSROs= new ArrayList<ShippingMethodSRO>();
		ShippingMethodSRO methodSRO = new ShippingMethodSRO();
		methodSRO.setCode("STD");
		methodSROs.add(methodSRO);
		providerSRO.setMethodSROs(methodSROs);
		request.setProviderSROs(providerSROs);
		request.setVendorCode("b82f06");
		AddVendorShippingProviderMappingResponse response= ApiHelper.addVendorShippingProviderMappings(request);
		System.out.println("response =="+response.getMessage());
		
	}
	
	//@Test
	public void getCategoryShippingProviderMappings()
	{
		
	 String categoryURL=null;
		GetCategoryShippingProviderMappingsRequest request = new GetCategoryShippingProviderMappingsRequest();
		try {
			ArrayList<String> urls = DatabaseHelper.getCategoryURL();
		Iterator<String> it=urls.iterator();
		while(it.hasNext())
		{
			categoryURL=it.next();
		request.setCategoryURL(categoryURL);
		GetCategoryShippingProviderMappingsResponse response=ApiHelper.getCategoryShippingProviderMappings(request);
		ArrayList<ArrayList<String>>expected=DatabaseHelper.getCategoryShippingProviderMappings(categoryURL);
		System.out.println("resposne=="+response.getMessage());
		List<ShippingProviderSRO> sros=response.getProviderSROs();
		System.out.println("ACTUAL=="+sros.size());
		System.out.println("expected=="+expected.size());
		Assert.assertEquals(sros.size(), expected.size());
		Iterator<ShippingProviderSRO> itr=sros.iterator();
		while(itr.hasNext())
		{
			ShippingProviderSRO sro=itr.next();
			System.out.println(sro.getCode());
			System.out.println(sro.getId());
			System.out.println(sro.getMethodSROs());
			System.out.println(sro.getName());
			System.out.println(sro.getTrackingLink());
		}
		//System.out.println(response.getProviderSROs().get(0).getName());
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//jira 7044
	//@Test
	public void addCategoryShippingProviderMappings()
	{
		String category_url="lifestyle-handbags-wallets";
		String code="COD";
		int provider=4;
		 AddCategoryShippingProviderMappingRequest request = new AddCategoryShippingProviderMappingRequest();
		 List<ShippingProviderSRO> providerSROs=new ArrayList<ShippingProviderSRO>();
		 ShippingProviderSRO sro= new ShippingProviderSRO();
		 sro.setId(provider);
		 List<ShippingMethodSRO> methodSROs = new ArrayList<ShippingMethodSRO>();
		 ShippingMethodSRO methodsro= new ShippingMethodSRO();
		 methodsro.setCode(code);
		 methodsro.setEnabled(true);
		 methodSROs.add(methodsro);
		 sro.setMethodSROs(methodSROs);
		 providerSROs.add(sro);
		 request.setProviderSROs(providerSROs);
		 request.setCategoryURL(category_url);
		 /*try {
			ArrayList<ArrayList<String>> beforeAdding =DatabaseHelper.getCategoryShippingProviderMappings("lifestyle-handbags-wallets");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		 AddCategoryShippingProviderMappingResponse response=ApiHelper.addCategoryShippingProviderMappings(request);
		 Assert.assertEquals(response.getMessage(),"SUCCESS");
		 Assert.assertTrue(response.isSuccessful());
		 try {
			
			ArrayList<ArrayList<String>> afterAdding =DatabaseHelper.getCategoryShippingProviderMappingsIds(category_url);
           Iterator<ArrayList<String>> it = afterAdding.iterator();
           Boolean valid=false;
           while(it.hasNext())
           {
        	   
        	   ArrayList<String> record = it.next();
        	  if(record.get(1).equals(category_url) && record.get(4).contains(code) && record.get(2).contains(String.valueOf(provider)))
        	  {
        	   valid=true;
        	   break;
        	  }
        	   System.out.println("resposne url=="+record.get(1));
        	   System.out.println("resposne 4=="+record.get(2));
        	   System.out.println("resposne cod=="+record.get(4));
           }
    	   Assert.assertTrue(valid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DatabaseHelper.deleteCategoryShippingProviderMappings(category_url,code,String.valueOf(provider));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//record not getting added
	//@Test
	public void addProductShippingProviderMapping()
	{
		AddProductShippingProviderMappingRequest request= new AddProductShippingProviderMappingRequest();
		 List<ShippingProviderSRO> providerSROs=new ArrayList<ShippingProviderSRO>();
		 ShippingProviderSRO sro= new ShippingProviderSRO();
		 sro.setId(7);
		 //sro.se
		 List<ShippingMethodSRO> methodSROs = new ArrayList<ShippingMethodSRO>();
		 ShippingMethodSRO methodsro= new ShippingMethodSRO();
		 methodsro.setCode("COD");
		 methodsro.setEnabled(true);
		 methodsro.setId(2);
		 methodsro.setShippingAddressRequired(false);
		 methodsro.setVerificationRequired(false);
		 methodSROs.add(methodsro);
		 sro.setMethodSROs(methodSROs);
		 providerSROs.add(sro);
		 request.setProviderSROs(providerSROs);
		 request.setSupc("1056732");
		 AddProductShippingProviderMappingResponse response = ApiHelper.addProductShippingProviderMapping(request);
		 System.out.println(response.isSuccessful());
		 System.out.println(response.getMessage());
	}
	
//@Test
    void markPendingCancellationOnSuborderPositive()
	{
		MarkPendingCancellationOnSuborderRequest request = new MarkPendingCancellationOnSuborderRequest();
		String comment = "Test markPendingCancellationOnSuborder automation";
		try {
			String suborderCode=DatabaseHelper.getSuborderCode();
			List<String> suborderCodes = new ArrayList<String>();
			suborderCodes.add(suborderCode);
			request.setSuborderCodes(suborderCodes);
			request.setComment(comment);
			MarkPendingCancellationOnSuborderResponse response = ApiHelper.markPendingCancellationOnSuborder(request);
			String statusAfterUpdate=DatabaseHelper.getSOIStatusBySuborderCode(suborderCode);
			Assert.assertEquals(statusAfterUpdate,"CRQ");
			String shipping_package_id=DatabaseHelper.getShippingPackageStatusBySuborderCode(suborderCode);
			Assert.assertEquals(shipping_package_id, "95");
			String remark=DatabaseHelper.getShippingPackageHistoryBySuborderCode(suborderCode);
			Assert.assertEquals(remark, comment);
			Assert.assertEquals(response.getMessage(),"SUCCESS");
			Assert.assertTrue(response.isSuccessful());
			/*System.out.println("message==="+response.getMessage());
			System.out.println("issuccess==="+response.isSuccessful());
			System.out.println(response.getProtocol());
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.fail(e.getMessage());
		}
	}

    //JIRA 7075
//@Test
public void markPendingCancellationOnSuborderNegative()
{
	MarkPendingCancellationOnSuborderRequest request = new MarkPendingCancellationOnSuborderRequest();
	String comment = "!@#$%^&56547567~`/?><,..+_&*()invalid test string";
	try {
		String suborderCode=null;
		List<String> suborderCodes = new ArrayList<String>();
		suborderCodes.add(suborderCode);
		request.setSuborderCodes(suborderCodes);
		request.setComment(comment);
		String statusBeforeUpdate=DatabaseHelper.getSOIStatusBySuborderCode(suborderCode);
		String shipping_package_id_before=DatabaseHelper.getShippingPackageStatusBySuborderCode(suborderCode);
		MarkPendingCancellationOnSuborderResponse response = ApiHelper.markPendingCancellationOnSuborder(request);
		String statusAfterUpdate=DatabaseHelper.getSOIStatusBySuborderCode(suborderCode);
		Assert.assertEquals(statusAfterUpdate,statusBeforeUpdate);
		String shipping_package_id_after=DatabaseHelper.getShippingPackageStatusBySuborderCode(suborderCode);
		Assert.assertEquals(shipping_package_id_before, shipping_package_id_after);
		System.out.println("message==="+response.getMessage());
		System.out.println("issuccess==="+response.isSuccessful());
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
    //@Test
	public void restorePackageForSuborder()
	{
    	markPendingCancellationOnSuborderPositive();
    	RestorePackageForSuborderRequest request= new RestorePackageForSuborderRequest();
		String suborderCode=null;
		try {
			 suborderCode=DatabaseHelper.getSuborderCodeForCrq();
			  System.out.println("subordercode"+suborderCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(suborderCode!=null)
		{
		request.setComment("restoring the soi to previous state after crq");
		request.setStatusCode("PKG");
		request.setSuborderCode(suborderCode);
		RestorePackageForSuborderResponse response=ApiHelper.restorePackageForSuborder(request);
		Assert.assertEquals(response.getMessage(), "SUCCESS","Validating the response message");
		Assert.assertTrue(response.isSuccessful(),"Validating response.isSuccessful()");
		//System.out.println("response"+response.getMessage());
	}
		else
		{
			//System.out.println("skipping tests");
			 throw new SkipException("No records found having status as CRQ");
		}
	}
    
    //@Test
	public void restorePackageForSuborderNullNegative()
	{
    	RestorePackageForSuborderRequest request= new RestorePackageForSuborderRequest();
		String suborderCode=null;
		request.setComment("restoring the soi to previous state after crq");
		request.setStatusCode("PKG");
		request.setSuborderCode(suborderCode);
		RestorePackageForSuborderResponse response=ApiHelper.restorePackageForSuborder(request);
		Assert.assertEquals(response.getMessage(), "Suborder code cannot be null","Validating the response message");
		Assert.assertFalse(response.isSuccessful(),"Validating response.isSuccessful()");
	}
    
    //@Test
	public void restorePackageForSuborderStatusCodeInvalidNegative()
	{
    	markPendingCancellationOnSuborderPositive();
    	RestorePackageForSuborderRequest request= new RestorePackageForSuborderRequest();
		String suborderCode=null;
		try {
			 suborderCode=DatabaseHelper.getSuborderCodeForCrq();
			  System.out.println("subordercode"+suborderCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setComment("restoring the soi to previous state after crq");
		request.setStatusCode("INVALID");
		request.setSuborderCode(suborderCode);
		RestorePackageForSuborderResponse response=ApiHelper.restorePackageForSuborder(request);
		Assert.assertEquals(response.getMessage(), "Status code is invalid","Validating the response message");
		Assert.assertFalse(response.isSuccessful(),"Validating response.isSuccessful()");
	}

   // //@Test
	public void restorePackageForSuborderInvalidNegative()
	{
    	markPendingCancellationOnSuborderPositive();
    	RestorePackageForSuborderRequest request= new RestorePackageForSuborderRequest();
		String suborderCode="999999";
		request.setComment("restoring the soi to previous state after crq");
		request.setStatusCode("INVALID");
		request.setSuborderCode(suborderCode);
		RestorePackageForSuborderResponse response=ApiHelper.restorePackageForSuborder(request);
		Assert.assertEquals(response.getMessage(), "No ShippingOrderItem found for suborder ","Validating the response message");
		Assert.assertFalse(response.isSuccessful(),"Validating response.isSuccessful()");
	}
    
   // //@Test
    public void getShippingInfoForSuborder()
    {
    	GetShippingInfoForSuborderRequest request = new GetShippingInfoForSuborderRequest();
    	String suborderCode=null;
		try {
			suborderCode = DatabaseHelper.getSuborderCode();
			request.setSubOrderCode(suborderCode);
			System.out.println("suborder code "+suborderCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	GetShippingInfoForSuborderResponse response=ApiHelper.getShippingInfoForSuborder(request);
    	try {
			ArrayList<ArrayList<String>> expected=DatabaseHelper.getShippingInfoForSuborder(suborderCode);
			ArrayList<String> list=expected.get(0);
			Iterator<String> it =list.iterator();
			while(it.hasNext())
				System.out.println(it.next());
			Assert.assertEquals(response.getSubOrderCode(), list.get(0));
			Assert.assertEquals(response.getItemType(), list.get(1));
			Assert.assertEquals(response.getShippingMethodCode(), list.get(2));
			Assert.assertEquals(response.getVendorCode(), list.get(3));
			Assert.assertEquals(response.getVendorName(), list.get(4));
			Assert.assertEquals(response.getStatusCode(), "NOTAVAILABLE");
			Assert.assertEquals(response.getAdminStatusDescription(), "Not Available");
			Assert.assertEquals(response.getMessage(), "SUCCESS");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    //@Test
    public void getShippingInfoForSuborderNegative()
    {
    	GetShippingInfoForSuborderRequest request = new GetShippingInfoForSuborderRequest();
    	request.setSubOrderCode("'1'='1' or '10020014';;DROP TABLE user");
    	GetShippingInfoForSuborderResponse response=ApiHelper.getShippingInfoForSuborder(request);
    	System.out.println("response.getmessage===="+response.getShippingMethodCode());
    	
    }
    
 //@Test
    public void getShippingInfoForSuborderExternal()
    {
    	GetShippingInfoForSuborderRequest request = new GetShippingInfoForSuborderRequest();
    	String suborderCode=null;
		try {
			suborderCode = DatabaseHelper.getSuborderCode();
			request.setSubOrderCode(suborderCode);
			System.out.println("suborder code "+suborderCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	GetShippingInfoForSuborderResponse response=ApiHelper.getShippingInfoForSuborder(request);
    	try {
			ArrayList<ArrayList<String>> expected=DatabaseHelper.getShippingInfoForSuborder(suborderCode);
			ArrayList<String> list=expected.get(0);
			Iterator<String> it =list.iterator();
			while(it.hasNext())
				System.out.println(it.next());
			Assert.assertEquals(response.getSubOrderCode(), list.get(0));
			Assert.assertEquals(response.getItemType(), list.get(1));
			Assert.assertEquals(response.getShippingMethodCode(), list.get(2));
			Assert.assertEquals(response.getVendorCode(), list.get(3));
			Assert.assertEquals(response.getVendorName(), list.get(4));
			//Assert.assertEquals(response.getStatusCode(), "NOTAVAILABLE");
			//Assert.assertEquals(response.getAdminStatusDescription(), "Not Available");
			//Assert.assertEquals(response.getMessage(), "SUCCESS");
			ShippingPackageSRO sro =response.getDispatchedShipment();
			Assert.assertEquals(sro.getTrackingNumberForExternal(),"N/A");
			//Assert.assertEquals(sro.getShippingProviderName(), "MANUAL");
			Assert.assertEquals(sro.getTrackingLink(), "N/A");
			System.out.println(sro.getOrderCode());
			System.out.println(sro.getPackageType());
			System.out.println(sro.getReconciliationDateExp());
			System.out.println(sro.getReferenceCode());
			System.out.println(sro.getShippingMethodCode());
			System.out.println(sro.getStatusCode());
			System.out.println(sro.getStatusDescription());
			System.out.println(sro.getStatusForExternal());
			System.out.println(sro.getStatusId());
			System.out.println(sro.getTrackingLink());
			System.out.println(sro.getEstimatedDeliveryDate());
			System.out.println(sro.getEstimatedShippingDate());
			System.out.println(sro.getShippedDate());
			System.out.println(sro.getSubOrderCodes());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Assert.fail(e.getMessage());
			//e.printStackTrace();
		}
    }
    
 //@Test
    public void getCancelledPackagesPositive()
    {
    	
    	  GetCancelledPackagesRequest request = new GetCancelledPackagesRequest();
    	  Date start =DateUtils.createDate(2010, 1, 10);
    		Date end = DateUtils.createDate(2013,11, 10);
    		DateRange range = new DateRange();
    		range.setEnd(end);
    		range.setStart(start);
    		request.setRange(range);
      		request.setShippingMethodCode("COD");
  		GetCancelledPackagesResponse response= ApiHelper.getCancelledPackages(request);
  		List<ShippingPackageSRO> sros = response.getShippingPackageSROs();
  		try {
			ArrayList<ArrayList<String>> expectedCancelledSROs =DatabaseHelper.getCancelledPackages(range.getStart(), range.getEnd(), 2);
  			Assert.assertTrue(response.isSuccessful());
  			Assert.assertEquals(expectedCancelledSROs.size(),sros.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     }
    
    //JIRA 7263
//@Test
	public void getCancelledPackagesNegative() {
		GetCancelledPackagesRequest request = new GetCancelledPackagesRequest();
		DateRange range = new DateRange();
		Date start = new Date();
		start.setMonth(0);
		Date end = new Date();
		end.setMonth(0);
		range.setEnd(end);
		range.setStart(start);
		request.setRange(range);
		request.setShippingMethodCode("-11");
		try
		{
			GetCancelledPackagesResponse response = ApiHelper.getCancelledPackages(request);
			System.out.println("size of list"+response.getShippingPackageSROs().size());
			Assert.assertTrue(response.getShippingPackageSROs().size()==0);
			Assert.assertFalse(response.isSuccessful());
		}catch(Exception e)
		{
			Assert.fail("Exception thrown by getDeliveredPackages with invalid request params");
}
	}

//@Test
public void getBannedZipForProductPositive()
{
	GetBannedZipPatternForProductRequest request = new GetBannedZipPatternForProductRequest();
	String supc=null;
	try {
		supc = DatabaseHelper.getSupc();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		Assert.fail(e.getMessage());
	}
	request.setSupc(supc);
	GetBannedZipPatternForProductResponse response=ApiHelper.getBannedZipForProduct(request);
	ArrayList<String> expected=null;
	try {
		expected = DatabaseHelper.getBannedZipForProduct(supc);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		Assert.fail(e.getMessage());
	}
    Assert.assertEquals(response.getBannedZipString().size(), expected.size());
	Assert.assertTrue(response.getBannedZipString().size()>0);
}

//@Test
public void getBannedZipForProductNegative()
{
	GetBannedZipPatternForProductRequest request = new GetBannedZipPatternForProductRequest();
	String supc=null;
	request.setSupc(supc);
	GetBannedZipPatternForProductResponse response=ApiHelper.getBannedZipForProduct(request);
	Assert.assertTrue(response.getBannedZipString().size()==0);
}
//JIRA 7291
//@Test
public void getSubordersShippedByVendor()
{
	GetSubordersShippedByVendorRequest request = new GetSubordersShippedByVendorRequest();
	Date start =DateUtils.createDate(2010, 10, 13);
	Date end = DateUtils.createDate(2010,10, 14);
	request.setEndDate(end);
	request.setStartDate(start);
	List<String> vendorCodes = new ArrayList<String>();
	vendorCodes.add("2a725e");
	request.setVendorCodes(vendorCodes);
	GetSubordersShippedByVendorResponse response=ApiHelper.getSubordersShippedByVendor(request);
	System.out.println("response===="+response.getMessage());
	//Assert.assertTrue(response.isSuccessful());
	System.out.println("size==="+response.getVendorCodeToSuborderListMap());
}

//@Test
public void getBannedZipForProductAndVendorPositive()
{
	 GetBannedZipPatternForProductAndVendorRequest request = new  GetBannedZipPatternForProductAndVendorRequest();
	 String supc=null;
	 String vendor_code=null;
	 ArrayList<String> expected=null;
	 try {
		 supc=DatabaseHelper.getSupcForProductVendor();
		 vendor_code=DatabaseHelper.getVendorCode();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		Assert.fail(e.getMessage());
	}
	 request.setSupc(supc);
	 request.setVendorCode(vendor_code);
	 GetBannedZipPatternForProductAndVendorResponse response = ApiHelper.getBannedZipForProductAndVendor(request);
	 try {
		 expected=DatabaseHelper.getBannedZipForProductAndVendor(supc,vendor_code);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		Assert.fail(e.getMessage());
	}
	 Assert.assertEquals(response.getBannedZipString().size(),expected.size());
	 Assert.assertEquals(response.getSupc(), supc);
	 Assert.assertEquals(response.getVendorCode(), vendor_code);
}


//response.isSuccessful() should be false and message is also invalid
//@Test
public void getBannedZipForProductAndVendorNegative()
{
	 GetBannedZipPatternForProductAndVendorRequest request = new  GetBannedZipPatternForProductAndVendorRequest();
	 request.setSupc("");
	 request.setVendorCode("!@#$%^&*()");
	 GetBannedZipPatternForProductAndVendorResponse response = ApiHelper.getBannedZipForProductAndVendor(request);
	 Assert.assertFalse(response.isSuccessful());
	 //System.out.println("the message is =="+response.getMessage());
}

//@Test
public void getProductShippingProviderMappings()
{
	GetProductShippingProviderMappingsRequest request =new GetProductShippingProviderMappingsRequest();
	String supc=null;
	ArrayList<ArrayList<String>> expected=null;
	try {
		supc = DatabaseHelper.getSupcForPSPM();
		System.out.println("supc is =="+supc);
	} catch (Exception e) {
		Assert.fail(e.getMessage());
	}
	request.setSupc(supc);
	GetProductShippingProviderMappingsResponse response =ApiHelper.getProductShippingProviderMappings(request);
	Assert.assertEquals(response.getMessage(),"SUCCESS");
	Assert.assertTrue(response.isSuccessful());
	System.out.println("response=="+response.isSuccessful());
	System.out.println("response====="+response.getMessage());
	System.out.println("size=="+response.getProviderSROs().size());
	try {
		 expected=DatabaseHelper.getProductShippingProviderMappings(supc);
		 System.out.println("expected size==="+expected.size());
	} catch (Exception e) {
		Assert.fail(e.getMessage());
	}
	Assert.assertEquals(expected.size(),response.getProviderSROs().size());
	System.out.println("expected.get(0)==="+expected.get(0));
	Iterator exp=expected.iterator();
	List<String> expectedNames=new ArrayList<String>();
	while(exp.hasNext())
	{
	    List<String> expRecord=(List<String>) exp.next();
	    expectedNames.add(expRecord.get(1));
	}
	System.out.println("the list of names =="+expectedNames);
	List<ShippingProviderSRO> sros = response.getProviderSROs();
	Iterator<ShippingProviderSRO> it = sros.iterator();
	List<String> actualNames=new ArrayList<String>();
	while(it.hasNext())
	{
		ShippingProviderSRO sro = it.next();
		actualNames.add(sro.getName());
		System.out.println("name==="+sro.getName());
		System.out.println("code==="+sro.getCode());
	}
	Assert.assertTrue(actualNames.containsAll(expectedNames));
}

//@Test
public void getVendorShippingProviderMappings()
{
	GetVendorShippingProviderMappingsRequest request = new GetVendorShippingProviderMappingsRequest();
	String vendorCode = null;
	ArrayList<ArrayList<String>> expected=null;
	try {
		vendorCode = DatabaseHelper.getVendorCodeForVSPM();
		System.out.println("vendor code ==="+vendorCode);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		Assert.fail(e.getMessage());
	}
	request.setVendorCode(vendorCode);
	GetVendorShippingProviderMappingsResponse response=ApiHelper.getVendorShippingProviderMappings(request);
	Assert.assertEquals(response.getMessage(),"SUCCESS");
	Assert.assertTrue(response.isSuccessful());
	System.out.println("response=="+response.isSuccessful());
	System.out.println("response====="+response.getMessage());
	System.out.println("size=="+response.getProviderSROs().size());
	try {
		 expected=DatabaseHelper.getVendorShippingProviderMappings(vendorCode);
		 System.out.println("expected size==="+expected.size());
	} catch (Exception e) {
		Assert.fail(e.getMessage());
	}
	Assert.assertEquals(expected.size(),response.getProviderSROs().size());
	System.out.println("expected.get(0)==="+expected.get(0));
	Iterator<ArrayList<String>> exp=expected.iterator();
	List<String> expectedNames=new ArrayList<String>();
	while(exp.hasNext())
	{
	    List<String> expRecord=(List<String>) exp.next();
	    expectedNames.add(expRecord.get(1));
	}
	System.out.println("the list of names =="+expectedNames);
	List<ShippingProviderSRO> sros = response.getProviderSROs();
	Iterator<ShippingProviderSRO> it = sros.iterator();
	List<String> actualNames=new ArrayList<String>();
	while(it.hasNext())
	{
		ShippingProviderSRO sro = it.next();
		actualNames.add(sro.getName());
		System.out.println("name==="+sro.getName());
		System.out.println("code==="+sro.getCode());
	}
	Assert.assertTrue(actualNames.containsAll(expectedNames));
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
//getProductShippingProviderMappings
//getVendorShippingProviderMappings
//fulfillorder