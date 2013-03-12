package com.snapdeal.testing.helper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.snapdeal.base.utils.DateUtils.DateRange;
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
import com.snapdeal.shipping.service.IShippingClientService;
import com.snapdeal.shipping.sro.ShippingGroupSRO;
import com.snapdeal.shipping.sro.ShippingMethodSRO;
import com.snapdeal.shipping.sro.ShippingProviderSRO;
import com.snapdeal.testing.config.ShippingConfig;
import com.snapdeal.vendor.core.model.GetAllActiveVendorContactsRequest;



@Component
public class ApiHelper 
{
	@Autowired
	IShippingClientService shippingService;
	static ApiHelper apiHelper;
	static ShippingConfig shippingConfig;
	static String serviceEndpoint;
	static {
		
		try {
			shippingConfig = ShippingConfig.getInstance();
			serviceEndpoint=shippingConfig.getConfig("Shipping.Endpoint");
			System.out.println("Service endpoint==="+serviceEndpoint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		ApplicationContext aop = new ClassPathXmlApplicationContext("application-context.xml");
		apiHelper = aop.getBean(ApiHelper.class);
		apiHelper.shippingService.setShippingWebServiceURL(serviceEndpoint);
		
		
	}
	
	

	public static GetActiveShippingProvidersResponse getActiveShippingProviders(GetActiveShippingProvidersRequest request) {
		return apiHelper.shippingService.getActiveShippingProviders(request);
	}

	public static GetAllShippingGroupsResponse getAllShippingGroups(GetAllShippingGroupsRequest request) {
		return apiHelper.shippingService.getAllShippingGroups(request);
	}
	
	public static GetShippingMethodChargesResponse getShippingMethodCharges(GetShippingMethodChargesRequest request){
		return apiHelper.shippingService.getShippingMethodCharges(request);
	}
	
	public static CheckIfCancellableResponse checkIfCancellable(CheckIfCancellableRequest request) {
		return apiHelper.shippingService.checkIfCancellable(request);
	}
	
	public static CancelShipmentResponse cancelShipment(CancelShipmentRequest request) {
		return apiHelper.shippingService.cancelShipment(request);
	}
	
	public static IsCatalogShippableResponse isCatalogShippable(IsCatalogShippableRequest request) {
		return apiHelper.shippingService.isCatalogShippable(request);
	}
	
	public static FulfillOrderResponse fulfillOrder(FulfillOrderRequest request) {
		return apiHelper.shippingService.fulfillOrder(request);
	}

	public static GetAllShippingGroupsForCatalogResponse getAllShippingGroupsForCatalog(GetAllShippingGroupsForCatalogRequest request) {
		return apiHelper.shippingService.getAllShippingGroupsForCatalog(request);
	}

	public static GetApplicableShippingMethodsResponse getApplicableShippingMethods(GetApplicableShippingMethodsRequest request) {
		return apiHelper.shippingService.getApplicableShippingMethods(request);
	}

	public static GetCategoryShippingProviderMappingsResponse getCategoryShippingProviderMappings(GetCategoryShippingProviderMappingsRequest request) {
		return apiHelper.shippingService.getCategoryShippingProviderMappings(request);
	}

	
	public static AddCategoryShippingProviderMappingResponse addCategoryShippingProviderMappings(AddCategoryShippingProviderMappingRequest request) {
		return apiHelper.shippingService.addCategoryShippingProviderMappings(request);
	}
//
	
	public static AddProductShippingProviderMappingResponse addProductShippingProviderMapping(AddProductShippingProviderMappingRequest request) {
		return apiHelper.shippingService.addProductShippingProviderMapping(request);
	}
	
	public static GetPackageByReferenceCodeResponse getPackageByReferenceCode(GetPackageByReferenceCodeRequest request) {
		return apiHelper.shippingService.getPackageByReferenceCode(request);
	}

	public static GetPackageBySuborderResponse getPackageBySuborder(GetPackageBySuborderRequest request) {
		return apiHelper.shippingService.getPackageBySuborder(request);
	}


	public static GetProductShippingProviderMappingsResponse getProductShippingProviderMappings(GetProductShippingProviderMappingsRequest request) {
		return apiHelper.shippingService.getProductShippingProviderMappings(request);
	}

	public static GetVendorShippingProviderMappingsResponse getVendorShippingProviderMappings(GetVendorShippingProviderMappingsRequest request) {
		return apiHelper.shippingService.getVendorShippingProviderMappings(request);
	}

	public static GetShippingMethodByCodeResponse getShippingMethodByCode(GetShippingMethodByCodeRequest request) {
		return apiHelper.shippingService.getShippingMethodByCode(request);
	}

	public static GetShippingGroupByCodeResponse getShippingGroupByCode(GetShippingGroupByCodeRequest request) {
		return apiHelper.shippingService.getShippingGroupByCode(request);
	}
	

	public static IsShippingValidResponse isShippingValid(IsShippingValidRequest request) {
		return apiHelper.shippingService.isShippingValid(request);
	}
	
	public static GetDeliveredPackagesResponse getDeliveredPackages(GetDeliveredPackagesRequest request) {
		return apiHelper.shippingService.getDeliveredPackages(request);
	}
	
	public static GetAllShippingHistoryResponse getAllShippingPackageHistory(GetAllShippingHistoryRequest request) {
		return apiHelper.shippingService.getAllShippingPackageHistory(request);
	}
	
	public static UpdatePackageResponse updatePackage(UpdatePackageRequest request) {
		return apiHelper.shippingService.updatePackage(request);
	}
	
	public static UpdatePackageStatusResponse updatePackageStatus(UpdatePackageStatusRequest request) {
		return apiHelper.shippingService.updatePackageStatus(request);
	}
	
	public static IsFulfilledResponse isFulfilled(IsFulfilledRequest request) {
		return apiHelper.shippingService.isFulfilled(request);
	}
	public static SwitchHoldOnShipmentBySuborderResponse switchHoldOnShipmentBySuborder(SwitchHoldOnShipmentBySuborderRequest request)
	{
		return apiHelper.shippingService.switchHoldOnShipmentBySuborder(request);
	}
	
	public static AddBannedZipPatternForProductResponse addBannedZipForProduct(AddBannedZipPatternForProductRequest request)
	{
		return apiHelper.shippingService.addBannedZipForProduct(request);
	}
	
	public static OverrideBannedZipPatternForVendorProductsResponse overrideBannedZipForVendorProducts(OverrideBannedZipPatternForVendorProductsRequest request)
	{
		return apiHelper.shippingService.overrideBannedZipForVendorProducts(request);
	}
	
	public static UpdateShippingUserResponse updateShippingUser(UpdateShippingUserRequest request)
	{
		return apiHelper.shippingService.updateShippingUser(request);
	}
	public static GetPossibleShippingMethodsForCatalogShipmentResponse getPossibleShippingMethodsForCatalogShipment(GetPossibleShippingMethodsForCatalogShipmentRequest request)
	{
		return apiHelper.shippingService.getPossibleShipppingMethodsForCatalogShipment(request);
	}
	
	public static GetCancelledPackagesResponse getCancelledPackages(GetCancelledPackagesRequest request)
	{
		return apiHelper.shippingService.getCancelledPackages(request);
	}
	
	public static GetBannedZipPatternForProductResponse getBannedZipForProduct(GetBannedZipPatternForProductRequest request)
	{
		return apiHelper.shippingService.getBannedZipForProduct(request);
	}
	
	public static GetBannedZipPatternForProductAndVendorResponse getBannedZipForProductAndVendor(GetBannedZipPatternForProductAndVendorRequest request)
	{
		return apiHelper.shippingService.getBannedZipForProductAndVendor(request);
	}
	
	public static GetShippingChargesResponse getShippingCharges(GetShippingChargesRequest request)
	{
		return apiHelper.shippingService.getShippingCharges(request);
	}
	//
	public static GetSubordersShippedByVendorResponse getSubordersShippedByVendor(GetSubordersShippedByVendorRequest request)
	{
		return apiHelper.shippingService.getSubordersShippedByVendor(request);
	}
	
	public static AddVendorShippingProviderMappingResponse addVendorShippingProviderMappings(AddVendorShippingProviderMappingRequest request)
	{
		return apiHelper.shippingService.addVendorShippingProviderMappings(request);
	}
	
	public static MarkPendingCancellationOnSuborderResponse markPendingCancellationOnSuborder(MarkPendingCancellationOnSuborderRequest request)
	{
		return apiHelper.shippingService.markPendingCancellationOnSuborder(request);
	}
	
	public static RestorePackageForSuborderResponse restorePackageForSuborder(RestorePackageForSuborderRequest request)
	{
		return apiHelper.shippingService.restorePackageForSuborder(request);
	}
	
	public static GetShippingInfoForSuborderResponse getShippingInfoForSuborder(GetShippingInfoForSuborderRequest request)
	{
		return apiHelper.shippingService.getShippingInfoForSuborder(request);
	}
	
	
	public static void main(String[] args) {
		
		System.out.println("hello");
        ApplicationContext aop = new ClassPathXmlApplicationContext("application-context.xml");
        ApiHelper t = aop.getBean(ApiHelper.class);
        //
        GetActiveShippingProvidersRequest request = new GetActiveShippingProvidersRequest();
        t.shippingService.setShippingWebServiceURL("http://ec2-46-137-193-102.ap-southeast-1.compute.amazonaws.com:8081/");
        GetActiveShippingProvidersResponse response = t.shippingService.getActiveShippingProviders(request);
        for (ShippingProviderSRO sro : response.getProviders()) {
            System.out.println("sro.getName()==="+sro.getName());
        }
        GetAllShippingGroupsRequest request1 = new GetAllShippingGroupsRequest();
        GetAllShippingGroupsResponse response1 = t.shippingService.getAllShippingGroups(request1);
        for (ShippingGroupSRO sro : response1.getShippingGroupSROs()) {
            System.out.println(sro.getName());
        }
        
        
        
        GetDeliveredPackagesRequest gdReq= new GetDeliveredPackagesRequest();
        
        DateRange range= new DateRange();
        Date start= new Date();
        start.setMonth(7);
        Date end = new Date();
        range.setEnd(end);
        
        range.setStart(start);
		gdReq.setRange(range);
        
		gdReq.setShippingMethodCode("STD");
		GetDeliveredPackagesResponse deliveredPackagesResponse = t.shippingService.getDeliveredPackages(gdReq);
        
		
		System.out.println(deliveredPackagesResponse.getMessage());
//		System.out.println(deliveredPackagesResponse.getShippingPackageSROs().get(2).getOrderCode());
//		System.out.println(deliveredPackagesResponse.getShippingPackageSROs().get(0).getPackageType());

		
		GetApplicableShippingMethodsRequest request2= new GetApplicableShippingMethodsRequest();
		
		request2.setShippingGroupCode("STD");
		
		GetApplicableShippingMethodsResponse response2= t.shippingService.getApplicableShippingMethods(request2);
		
		System.out.println(response2.getMessage());
		for(ShippingMethodSRO sro: response2.getShippingMethods()) {
			
			System.out.println(sro.getCode()+"  "+sro.getId()+"  "+sro.getName());
		}
		
		System.out.println("hello End");
		
		
		GetShippingMethodByCodeRequest shippingMethodByCodeRequest = new GetShippingMethodByCodeRequest();
		shippingMethodByCodeRequest.setCode("STD");
		
		GetShippingMethodByCodeResponse response3= t.shippingService.getShippingMethodByCode(shippingMethodByCodeRequest);
		
		

	}

}
