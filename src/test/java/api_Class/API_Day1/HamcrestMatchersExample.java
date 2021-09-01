package api_Class.API_Day1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

/*
Hamcrest library provides matchers which are simply assertion methods, that's why Rest-Assured uses
hamcrest's assertions in tests. Hamcrest works with both JUnit and TestNG.
 */
public class HamcrestMatchersExample {
// any hamcrest assertions that is not correct will fail.
    @Test
    public void test() {

        // comparing numbers
        assertThat(200, equalTo(200));
        assertThat(7, greaterThan(5));
        assertThat(100, lessThan(101));
        assertThat(200, lessThanOrEqualTo(201));

        // comparing String values
        String a = "tla";
        String b = "tla";
        String c = "     tla  ";
        assertThat(a, is("tla"));
        assertThat(a, equalTo(b)); // equalTo() works same as 'is()'
        assertThat(a, equalToCompressingWhiteSpace(c)); // ignores space before and after
        assertThat(a, notNullValue()); // verify a doesn't have a null value

        // verify first argument is NOT equal to the second one
        assertThat(a, is(not("techlead")));

        // compare ignoring case
        assertThat(a, equalToIgnoringCase("TLA"));

        List<Integer> nums = Arrays.asList(20,25,30,40,50);
        assertThat(nums, everyItem(greaterThan(10)));
        assertThat(nums, hasItem(40));

        List<String> strs = Arrays.asList("tech", "lead", "academy");
        assertThat(strs, hasSize(3));
        assertThat(strs, hasItem("academy"));
        assertThat(strs, containsInAnyOrder("lead", "academy", "tech"));

    }
}