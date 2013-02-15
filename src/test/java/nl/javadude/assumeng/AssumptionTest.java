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

import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static nl.javadude.assumeng.Assumes.assumeThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.fail;

class InverseLogic {
    public static boolean alwaysFalse() {
        return true;
    }

    public static boolean alwaysTrue() {
        return false;
    }
}

@Listeners(value = {AssumptionListener.class, AssumptionChecker.class})
public class AssumptionTest {

    @AfterClass
    public void assumptionsShouldBeCorrect() {
        assertThat(AssumptionChecker.getSkippedTests(), equalTo(4));
        assertThat(AssumptionChecker.getFailedTests(), equalTo(0));
        assertThat(AssumptionChecker.getSuccessTests(), equalTo(3));
    }

    @Test
    @Assumption(methods = "alwaysFalse")
    public void shouldSkip() {
        fail("Should not run");
    }

    @Test
    @Assumption(methods = "alwaysTrue")
    public void shouldSucceed() {
        assertThat(alwaysFalse(), equalTo(false));
    }

    @Test
    @Assumption(methods = {"alwaysTrue", "alwaysFalse"})
    public void shouldAlsoSkip() {
        fail("Should not run");
    }

    @Test
    @Assumption(methods = "alwaysTrue", methodClass = InverseLogic.class)
    public void shouldSkipInverse() {
        fail("Should not run");
    }

    @Test
    @Assumption(methods = "alwaysFalse", methodClass = InverseLogic.class)
    public void shouldSucceedInverse() {
        assertThat(InverseLogic.alwaysFalse(), equalTo(true));
    }

    @Test
    public void shouldUseAssumeThatToSkip() {
        assumeThat(alwaysTrue(), equalTo(false));
        fail("Should not reach here...");
    }

    @Test
    public void shouldUseAssumeThatAndContinue() {
        assumeThat(alwaysTrue(), equalTo(true));
        // Ok!
    }

    public boolean alwaysFalse() {
        return false;
    }

    public boolean alwaysTrue() {
        return true;
    }
}
