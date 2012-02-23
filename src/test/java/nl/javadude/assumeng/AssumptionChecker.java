package nl.javadude.assumeng;

import org.testng.IInvokedMethod;
import org.testng.ITestResult;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AssumptionChecker extends BaseTestListener {

	public static final AtomicInteger skippedTests = new AtomicInteger(0); 
	public static final AtomicInteger successTests = new AtomicInteger(0); 
	public static final AtomicInteger failedTests = new AtomicInteger(0); 
	
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		switch(testResult.getStatus()) {
			case ITestResult.FAILURE:
			case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
				failedTests.incrementAndGet();
				break;
			case ITestResult.SKIP:
				skippedTests.incrementAndGet();
				break;
			case ITestResult.SUCCESS:
				successTests.incrementAndGet();
		}
	}

	public static int getSkippedTests() {
		return skippedTests.intValue();
	}

	public static int getSuccessTests() {
		return successTests.intValue();
	}

	public static int getFailedTests() {
		return failedTests.intValue();
	}
}
