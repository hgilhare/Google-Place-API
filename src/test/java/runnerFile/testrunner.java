package runnerFile;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/featurefile",glue={"stepdefination"},tags="@Reg",plugin={"pretty","html:target/htmlReports/test1.html","json:target/jsonReports/test2.json"})
public class testrunner{}
