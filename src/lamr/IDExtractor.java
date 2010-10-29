/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lamr;

/**
 *
 * @author sandy
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;

/**
 *
 * @author David Cliff
 */
public class IDExtractor
{
    String xmlString;

    /**
     * The default constructor of the IDExtractor object
     * 
     * <br><br>
     * 
     * Takes the input parameter and sets an internal variable to equal it
     *
     * @param xmlInputString String
     */
    public IDExtractor(String xmlInputString)
    {
        xmlString = xmlInputString;
    }

    /**
     * Returns an array of Themis IDs extracted from the xmlString variable
     *
     * @return ArrayList
     */
    public ArrayList getThemisIDs()
    {
        PropertyExtractor pE = new PropertyExtractor();

        String matchingString = "";
        ArrayList themisIDs = new ArrayList();

        Pattern pattern = Pattern.compile(pE.getRegexThemisPattern());

        final Matcher m = pattern.matcher(xmlString);
        while ( m.find() )
        {
            final int gc = m.groupCount();
            // group 0 is the whole pattern matched,
            // loops runs from from 0 to gc, not 0 to gc-1 as is traditional.
            for ( int i = 0; i <= gc; i++ )
            {
                //System.out.println( i + " : " + m.group( i ) );
                matchingString = m.group(0);
                matchingString = matchingString.replaceAll("\\D", "");

                themisIDs.add(matchingString);
            }
        }

        return themisIDs;
    }

    /**
     * Returns an array of Thomson IDs extracted from the xmlString variable
     *
     * @return ArrayList
     */
    public ArrayList getThomsonIDs()
    {

        PropertyExtractor pE = new PropertyExtractor();

        String matchingString = "";
        ArrayList thomsonIDs = new ArrayList();

        Pattern pattern = Pattern.compile(pE.getRegexThomsonPattern());

        final Matcher m = pattern.matcher(xmlString);
        while ( m.find() )
        {
            final int gc = m.groupCount();
            // group 0 is the whole pattern matched,
            // loops runs from from 0 to gc, not 0 to gc-1 as is traditional.
            for ( int i = 0; i <= gc; i++ )
            {
                //System.out.println( i + " : " + m.group( i ) );
                matchingString = m.group(0);
                matchingString = matchingString.replaceAll("\\D", "");
                
                thomsonIDs.add(matchingString);
            }
        }

        return thomsonIDs;
    }
}
