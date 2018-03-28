package Step_Definations;

        import com.sun.org.apache.xalan.internal.utils.FeatureManager;
        import cucumber.api.CucumberOptions;
        import cucumber.api.junit.Cucumber;
        import org.junit.runner.RunWith;

/**
 * Created by thinksysuser on 04-04-2017.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/Annotation.feature",format={"json:target/Destination/cucumber.json"}) //format = {"pretty", "html:target/Destination"} )//,tags = {"@SmokeTest"})
//@CucumberOptions(features = "src/test/resources/SignUp.feature")
public class AnnotationsRunner {
}
