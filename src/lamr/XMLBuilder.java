/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lamr;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.JDOMException;

import java.io.*;

/**
 *
 * @author David Cliff
 */
public class XMLBuilder
{

    Document xmlFile;

    /**
     * The default constructor for the XMLBuilder object
     * 
     * <br><br>
     * 
     * Creates a Document object from a local XML file
     *
     * @param xmlFileName String
     */
    public XMLBuilder(String xmlFileName)
    {
        Document doc = null;

        SAXBuilder sb = new SAXBuilder();

        try
        {
            doc = sb.build(new File(xmlFileName));
        }
        catch (JDOMException e)
        {
            System.out.println(e);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        xmlFile = doc;
    }

    /**
     * Returns the built XML Document object
     *
     * @return Document
     */
    public Document returnFile()
    {
        return xmlFile;
    }

}
