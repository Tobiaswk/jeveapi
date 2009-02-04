/**
 * JEVEAPI 0.1 library
 * by Tobias W. Kjeldsen - tobias at wkjeldsen.dk
 * free usage - I just want the credit :)
 */
package jeveapi;

import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Tobias W. Kjeldsen
 */

/* this class is basicly used just for the parsing of data
 * it can parse just about anything - in this case we parse xml -
 * both from CCP provided data and from my/your own mysql-server */
public abstract class ParseAPIData {

    /**
     * @param xml
     * @param attribute
     * @return Vector with Strings
     * @throws java.lang.Exception 
     */
    public static Vector<String> getValues(String xml, String attribute) throws Exception  {
        String xmlString = new String(xml);
        Vector v = new Vector();
        String beginTagToSearch = "<" + attribute + ">";

        /* this fix(?) ensures the endtag is closed right */
        Scanner scan = new Scanner(attribute);
        String endTagToSearch = "</" + scan.next() + ">";

        // Look for the first occurrence of begin tag
        int index = xmlString.indexOf(beginTagToSearch);
        while (index != -1) {
            // Look for end tag
            // DOES NOT HANDLE <section Blah />
            int lastIndex = xmlString.indexOf(endTagToSearch);

            // Make sure there is no error
            if ((lastIndex == -1) || (lastIndex < index)) {
                throw new Exception("Parse Error");
            }
            // extract the substring
            String subs = xmlString.substring((index + beginTagToSearch.length()), lastIndex);

            // Add it to our list of tag values
            v.addElement(subs);

            // Try it again. Narrow down to the part of string which is not
            // processed yet.
            try {
                xmlString = xmlString.substring(lastIndex + endTagToSearch.length());
            } catch (Exception e) {
                xmlString = "";
            }

            // Start over again by searching the first occurrence of the begin tag
            // to continue the loop.
            index = xmlString.indexOf(beginTagToSearch);
        }
        return v;
    }

    /**
     * @param xml
     * @param attribute
     * @return String
     * @throws java.lang.Exception 
     */
    public static String getValue(String xml, String attribute) throws Exception {
        return getValues(xml, attribute).get(0);
    }
}
