Assume NG -- Assumptions for TestNG
===================================

Using Assume NG it becomes possible to add [JUnit](http://www.junit.org)-like [assumptions](http://kentbeck.github.com/junit/javadoc/latest/org/junit/Assume.html) to [TestNG](http://www.testng.org).

Note
----

# Please ensure that Assume NG is on the classpath *before* TestNG. This is because we need to classpath override the TestNG <code>org.testng.internal.Invoker</code> class to hook in our behavior.
# Assume NG currently works with TestNG 5.14.10 (due to the adaptation above).

Usage
-----
Assume NG works using the @Assumption annotation and the AssumptionListener.

The <code>@Assumption</code> annotation is defined on a <code>@Test</code> method, and calls out to one or more assumption methods which decide whether or not the <code>@Test</code> annotated method needs to run in the current context. Only if all of the assumption methods pass, the <code>@Test</code> method will actually be invoked, otherwise it will be marked as
<code>skipped</code>.

The <code>AssumptionListener</code> scans for <code>@Assumption</code> annotations and handles the actual invocation of the assumption method.

The assumption method should have the following signature

    public boolean checkWhetherXHolds() { ... }

A full example could be:

	@Listeners(AssumptionListener.class)
    public class WeatherTest {
        @Test
        @Assumption(methods = "assumeWeatherIsNice")
        public void shouldNotRunWhenItsRaining() {
            // Do some stuff, like going to the beach...
        }

        public boolean assumeWeatherIsNice() {
        	// check the weather bulletin...
        }
    }
