Assume NG -- Assumptions for TestNG
===================================

Using Assume NG it becomes possible to add [JUnit](http://www.junit.org)-like [assumptions](http://kentbeck.github.com/junit/javadoc/latest/org/junit/Assume.html) to [TestNG](http://www.testng.org).

Usage
-----
Assume NG works using the @Assumption annotation and the AssumptionListener.

The <code>@Assumption</code> annotation is defined on a <code>@Test</code> method, and calls out to one or more assumption methods which decide whether or not the <code>@Test</code> annotated method needs to run in the current context. Only if all of the assumption methods pass, the <code>@Test</code> method will actually be invoked, otherwise it will be marked as
<code>skipped</code>.

The <code>AssumptionListener</code> scans for <code>@Assumption</code> annotations and handles the actual invocation of the assumption method.

The assumption method should have the following signature

    public boolean checkWhetherXHolds() { ... }

A simple usage example is:

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

"Real world" use case
-------------------

    public class HostFactoryItest {
        @Factory
        public Object[] createTestInstances() {
            Object[] instances = new Object[6];
            object[0] = new HostItest("localhost", "local");
            object[1] = new HostItest("unix-host", "sftp");
            object[2] = new HostItest("unix-host", "ssh");
            object[3] = new HostItest("windows-host", "sftp");
            object[4] = new HostItest("windows-host", "cifs-telnet");
            object[5] = new HostItest("windows-host", "cifs-winrm");
        }
    }

    @Listeners(AssumptionListener.class)
    public class HostItest() {
        private String host;
        private String protocol;

        public HostItest(String host, String protocol) {
            ...
        }

        @Test
        @Assumption(methods = "notLocal")
        public void shouldNotConnectUsingWrongPassword() {
            ...
        }

        @Test
        public void shouldGetFileFromHost() {
            ...
        }

        ...more tests...

        public boolean notLocal() {
            return !this.host.equals("localhost");
        }
    }
