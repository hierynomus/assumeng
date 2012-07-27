/* License added by: GRADLE-LICENSE-PLUGIN
 *
 *    Copyright 2012 Jeroen van Erp (jeroen@javadude.nl)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package nl.javadude.assumeng;

import java.util.concurrent.atomic.AtomicInteger;
import org.testng.IInvokedMethod;
import org.testng.ITestResult;

public class AssumptionChecker extends BaseTestListener {

    public static final AtomicInteger skippedTests = new AtomicInteger(0);
    public static final AtomicInteger successTests = new AtomicInteger(0);
    public static final AtomicInteger failedTests = new AtomicInteger(0);

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        switch (testResult.getStatus()) {
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
