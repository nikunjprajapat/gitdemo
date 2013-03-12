package com.snapdeal.testing.shipping;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ShippingTestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(result);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		System.out.println(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		System.out.println(context);
	}

}
