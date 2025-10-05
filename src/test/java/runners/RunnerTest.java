package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions(
        features = "src/test/resources/features/UI-Test.feature",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/cucumber-pretty.html",
                "json:test-output/cucumber-reports/CucumberTestReport.json",
                "rerun:test-output/cucumber-reports/rerun.txt",
                "testng:test-output/cucumber-reports/testng.xml"
        })

public class RunnerTest extends AbstractTestNGCucumberTests {

    private TestNGCucumberRunner testNGCucumberRunner;

//     @BeforeClass(alwaysRun = true)
//     public void setUpClass() {
//         testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
//     }

    // @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    // public void feature(CucumberFeatureWrapper cucumberFeature) {
    //     testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    // }

    // @DataProvider
    // public Object[][] features() {
    //     return testNGCucumberRunner.provideFeatures();
    // }

    // @AfterClass(alwaysRun = true)
    // public void tearDownClass() {
    //     testNGCucumberRunner.finish();
    // }


}
