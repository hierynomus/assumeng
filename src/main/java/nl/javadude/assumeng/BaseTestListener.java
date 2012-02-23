package nl.javadude.assumeng;

import org.testng.*;

abstract class BaseTestListener implements IInvokedMethodListener {
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
	}
}
