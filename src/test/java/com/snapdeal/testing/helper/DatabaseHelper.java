package com.snapdeal.testing.helper;


import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper {

	public static ArrayList<String> getActiveShippingProvider()
			throws Exception {

		String sql = "select  name  from shipping_provider where enabled=1";
		return DatabaseUtility.getList(sql, "dev");
	}

	public static ArrayList<ArrayList<String>> getShippingGroups()
			throws Exception {

		String sql = "select code,name from shipping_group where enabled=1 order by id";
		return DatabaseUtility.getRecords(sql, "dev");
	}

	public static ArrayList<ArrayList<String>> getShippingMethods(
			String shippingGroupCode) throws Exception {
		String sql = " select id,code,name,priority,charges,charges_type from shipping_method where enabled=1 and id in (select shipping_method_id from"
				+ " shipping_group_methods where shipping_group_id=(select id from shipping_group where code='"
				+ shippingGroupCode + "'))";
		System.out.println("Running query " + sql);
		return DatabaseUtility.getRecords(sql, "dev");
	}

	public static ArrayList<String> getShippingMethodCodeByShippingGroup(
			String shippingGroupCode) throws Exception {

		String sql = "select  sm.code  from shipping_group_methods sg , shipping_method sm where sg.shipping_method_id=sm.id  "
				+ "and  shipping_group_id="
				+ "(select id from shipping_group where code='"
				+ shippingGroupCode + "')";
		return DatabaseUtility.getList(sql, "dev");
	}

	public static ArrayList<ArrayList<String>> getShippingMethodByCode(
			String code) throws Exception {

		String sql = "select id,code,name,priority,charges,charges_type from shipping_method where enabled=1  and  code='"
				+ code + "'";

		return DatabaseUtility.getRecords(sql, "dev");
	}

	public static ArrayList<ArrayList<String>> getShippingGroupByCode(
			String code) throws Exception {

		String sql = "select code,name from shipping_group where enabled=1  and  code='"
				+ code + "'";

		return DatabaseUtility.getRecords(sql, "dev");
	}

	public static ArrayList<ArrayList<String>> getAllShippingGroupsForCatalog(
			String catalogType) throws Exception {

		String sql = "select code,name from shipping_group where enabled=1  and  catalog_type='"
				+ catalogType + "'";

		return DatabaseUtility.getRecords(sql, "dev");
	}

	public static ArrayList<ArrayList<String>> getDeliveredPackages(
			Date startDate, Date endDate, int shippingMethodId)
			throws Exception {
		System.out.println("startdate=="+startDate);
		System.out.println("enddate==="+endDate);
		String sql = "select sp.* from shipping.shipping_package sp, shipping.shipping_order_item i, shipping.shipping_package_history sph where i.shipping_package_id=sp.id  and sp.id=sph.package_id and sp.shipping_status_id=70 and sph.shipping_status_id=70 and sph.created >='2010-01-10 00:00:00' and sph.created <'2013-11-10 00:00:00' and sp.on_hold=0 and sp.shipping_method_id='"+shippingMethodId+"'";
		return DatabaseUtility.getRecords(sql, "dev");
	}
	
	public static ArrayList<ArrayList<String>> getUnPackedSOI()throws Exception{
		String sql="select * from shipping_order_item where soi_status_code!='VCH' and shipping_package_id is NULL limit 1";
		return DatabaseUtility.getRecords(sql,"dev");
	}
	//Get a soi for which package_type is vouchers and shipping_method is email
	public static ArrayList<ArrayList<String>> getPackedSOI()throws Exception{
		String sql="select * from shipping_order_item soi, shipping_package sp where soi.shipping_package_id= sp.id and soi_status_code!='VCH' and shipping_package_id is NOT NULL and sp.package_type=1 and sp.shipping_method_id =3 limit 1";
		//String sql="select * from shipping_order_item soi, shipping_package sp where soi.shipping_package_id= sp.id and soi_status_code!='VCH' and shipping_package_id is NOT NULL and sp.shipping_method_id =1 limit 1";
		return DatabaseUtility.getRecords(sql,"dev");
	}
	
	public static ArrayList<ArrayList<String>> getPackage()throws Exception{
		//String sql="select * from shipping_package sp,shipping_order_item soi where soi. limit 1";
		String sql="select sp.reference_code from shipping_order_item soi,shipping_package sp where sp.id=soi.shipping_package_id and soi.suborder_code is not null limit 1";
		return DatabaseUtility.getRecords(sql,"dev");
	}
	//String soi_statusCode,
	public static ArrayList<ArrayList<String>> getSOIByShipping_id(String shipping_status_id,String prev_code)throws Exception{
		String sql=null;
		if(shipping_status_id!=null)
		{
		 sql="select id from shipping_package where shipping_status_id='"+shipping_status_id+"'";
		//System.out.println("sql is ..."+sql);
		String shipping_package_id=DatabaseUtility.executeQuery(sql);
		System.out.println("the shipping_id "+shipping_package_id);
	  sql="select * from shipping_order_item soi where shipping_package_id='"+shipping_package_id+"' and soi_status_code='"+prev_code+"' limit 1";
		System.out.println("SQL in dbhelper=="+sql);
		return DatabaseUtility.getRecords(sql,"dev");
		}
		else
		{
			 sql="select * from shipping_order_item where soi_status_code='"+prev_code+"' limit 1";
			return DatabaseUtility.getRecords(sql, "dev");
		}
	}
	
	
	/*public static String getSOIBySOI_Status_code(String statusCode)throws Exception{
//		String sql="select id from shipping_status where code='"+statusCode+"'";
//		System.out.println("sql is ..."+sql);
//		String status_code=DatabaseUtility.executeQuery(sql);
//		System.out.println("the status code is "+status_code);
		String sql="select soi.suborder_code from shipping_order_item soi where soi_status_code='"+statusCode+"' limit 1";
		System.out.println("SQL in dbhelper=="+sql);
		return DatabaseUtility.executeQuery(sql,"dev");
	}*/
	public static String getSOIBySuborderCode(String subOrderCode)throws Exception{
		String sql="select soi.soi_status_code from shipping_order_item soi where suborder_code='"+subOrderCode+"'";
		return DatabaseUtility.executeQuery(sql,"dev");
	}
	public static String getSOIForswitchHoldOnShipmentBySuborder(String vendor_Code,String status,String dispatchCategory,Boolean withPackage)throws Exception
	{
		String sql=null;
		//String sql="select suborder_code from shipping_order_item where shipping_package_id is null and vendor_code='"+vendor_Code+"' and soi_status_code='"+status+"' and dispatch_category_id='"+dispatchCategory+"' order by created limit 1";
		if(withPackage ==true)
		{
			//If shipping_package is in status VENDOR_SELF_NO_COURIER_INFO= 34; VENDOR_READY= 40;PUSHED_TO_WMS= 41;OUT_OF_STOCK= 42; then it can be put on hold
		 sql="select suborder_code from shipping_order_item soi,shipping_package sp where soi.shipping_package_id=sp.id and shipping_status_id in ('34','40','41','42') limit 1";
		}
		else
		{
			sql="select suborder_code from shipping_order_item where shipping_package_id is null and vendor_code='"+vendor_Code+"' and soi_status_code='"+status+"' and dispatch_category_id='"+dispatchCategory+"' order by created limit 1";		
		}
		System.out.println("the sql is ==="+sql);
		return DatabaseUtility.executeQuery(sql, "dev");
	}
	public static ArrayList<ArrayList<String>> getSupcAndVendorForOverrideBannedZipForVendor()throws Exception
	{
		String sql = "select * from  product_vendor_banned_zip_pattern_mapping limit 1";
		return DatabaseUtility.getRecords(sql,"dev");
	}

	
	public static String getSupcForAddBannedZipForProduct()throws Exception
	{
		String sql = "select supc from product_banned_zip_pattern_mapping limit 1";
		return DatabaseUtility.executeQuery(sql);
	}

	public static ArrayList<String> getProductVendorBannedZipPaternMapping(String supc,String vendor)throws Exception
	{
		String sql = "select banned_zip_pattern from product_vendor_banned_zip_pattern_mapping where supc='"+supc+"' and vendor_code='"+vendor+"'";
		return DatabaseUtility.getList(sql,"dev");
	}

	public static String getUser(String email)throws Exception
	{
		String sql = "select email from user where email='"+email+"'";
		return DatabaseUtility.executeQuery(sql);
	}
	
	
	
	public static String isShippingValid(String pincode,String methodId)throws Exception
	{
		String sql = "select pincode from shipping_location where pincode='"+pincode+"' and shipping_method_id='"+methodId+"'";
		return DatabaseUtility.executeQuery(sql);
	}
	
	public static String getReferenceCode()throws Exception
	{
		String sql = "select reference_code from shipping_package limit 1";
		return DatabaseUtility.executeQuery(sql);
	}
	
	
	public static ArrayList<String> getStatusCode()throws Exception
	{
		String sql = "select code from shipping_status";
		return DatabaseUtility.getList(sql, "dev");
	}
	
	
	public static ArrayList<ArrayList<String>> getPackageBySuborder(String suborderCode)throws Exception
	{
		String sql = "select order_code,suborder_code,package_type,sm.code,spr.name,ss.code,ss.description,ss.id,shipped_date,tracking_number,reference_code from shipping_status ss,shipping_method sm,shipping_order_item soi,shipping_package sp,shipping_provider spr where suborder_code='"+suborderCode+"' and soi.shipping_package_id=sp.id and spr.id=sp.shipping_provider_id and sp.shipping_method_id=sm.id and ss.id=sp.shipping_status_id";
		return DatabaseUtility.getRecords(sql, "dev");
	}
	
	public static String getSuborderCode()throws Exception
	{
		String sql = "select suborder_code from shipping_order_item limit 1";
		return DatabaseUtility.executeQuery(sql);
	}
	
	
	public static ArrayList<String> getCategoryURL()throws Exception
	{
		String sql = "select category_url from  product_category_shipping_provider_mapping";
		return DatabaseUtility.getList(sql, "dev");
	}
	public static ArrayList<ArrayList<String>> getCategoryShippingProviderMappings(String url)throws Exception
	{
		String sql = "select sp.id,name,code,ship_method_code,tracking_link from shipping_provider sp,product_category_shipping_provider_mapping map where sp.id=map.shipping_provider_id and category_url='"+url+"'"; 
		return DatabaseUtility.getRecords(sql, "dev");
	}
	
	
	 /* public static void main(String[] args) throws Exception{
	 * 
	 * 
	 * 
	 * }
	 */

}
