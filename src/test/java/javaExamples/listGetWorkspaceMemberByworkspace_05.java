package javaExamples;

import java.util.Arrays;
import java.util.List;

public class listGetWorkspaceMemberByworkspace_05 {

    //We can use Arrays class to get the view of array as list.
    // However we won’t be able to do any structural modification to
    // the list, it will throw java.lang.UnsupportedOperationException.
    // So the best way is to use for loop for creating list by iterating over the array
    public static void main(String... args){
        String [] responseBody = { "1617935448409","","Wkt3tHgB6T29TqnSuTha", "1617935448409", "Default", "1kt3tHgB6T29TqnSCje3"};

        //Use List
        List<String> response = Arrays.asList(responseBody);
        System.out.println(response);
        System.out.println(response.get(2));



    }
}
