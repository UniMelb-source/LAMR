/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lamr;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

import org.jdom.Document;
import org.jdom.output.XMLOutputter;

import javax.net.ssl.HttpsURLConnection;

import org.jdom.Element;
import org.jdom.xpath.XPath;

/**
 *
 * @author David Cliff
 */
public class XMLSender
{
    private String serviceResponse;
    private ArrayList themisIDs;
    private ArrayList thomsonIDs;

    /**
     * The default constructor for the XMLSender object.
     * 
     * <br><br>
     * 
     * It breaks the input file up into request chunks of 50 (to avoid throttling),
     * then sorts the resulting output into arrays of Themis IDs and Thomson IDs
     *
     * @param xmlFile String
     * @param Login boolean
     * @param username String
     * @param password String
     */
    public XMLSender(String xmlFile, boolean Login, String username, String password)
    {
        PropertyExtractor pE = new PropertyExtractor();

        //importing XML shell for sending data
        String shell = "";
        
        try
        {
            shell = readFileAsString(pE.getOutputShell());
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        if(Login)
        {
            shell = insertLogin(shell, username, password);
        }

        XMLBuilder bX = new XMLBuilder(xmlFile);
        Document doc = bX.returnFile();

        List results = null;

        try
        {
            XPath xpath = XPath.newInstance(pE.getExtractXPath());
            results = xpath.selectNodes(doc);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

        int totalCount = results.size();

        int startNode = 0;
        int loopCount = 1;

        System.out.print("Records being searched: ");

        //------------------------------------------------------------------

        while(startNode < totalCount)
        {

            String loopString = "";

            //for loop
            for (int i=startNode; i < totalCount; i++)
            {
                //System.out.println( results.get(i) );
                Element currentMap = (Element)results.get(i);

                loopString += new XMLOutputter().outputString(currentMap);

                if(loopCount == 50)
                {
                    break;
                }
                loopCount++;
            }

            startNode += loopCount;
            loopCount = 1;

            //clean loopString
            XMLCleaner cX = new XMLCleaner(loopString);
            loopString = cX.returnCleanXML();

            int insertPoint = shell.indexOf(pE.getResultInsertPoint(), 0);

            StringBuilder sB =  new StringBuilder(shell);

            sB.insert(insertPoint, loopString);

            String output = sB.toString();

            Send(output);

            System.out.print(".");
        //------------------------------------------------------------------
        }

        //cleaning up the server response - removing nodes with no results found

        serviceResponse = serviceResponse.replaceFirst("null", "");
        String replaceString = pE.getRegexNoResultFoundPattern();
        serviceResponse = serviceResponse.replaceAll(replaceString, "");

        IDExtractor iE = new IDExtractor(serviceResponse);
        themisIDs = iE.getThemisIDs();
        thomsonIDs = iE.getThomsonIDs();        
    }

    /**
     * This method is used by the constructor to send the information request
     * 
     * <br><br>
     * 
     * It was placed in a separate method so that exception handling could be better
     * packaged, especially in relation to connection throttling.
     *
     * @param output String
     */
    private void Send(String output)
    {
        boolean sent = false;

        while(!sent)
        {
            try
            {
                // Send data
                URL url = new URL("https://ws.isiknowledge.com/cps/xrpc");
                HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(output);
                wr.flush();

                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line;

                while ((line = rd.readLine()) != null)
                {
                    serviceResponse += line;
                }

                wr.close();
                rd.close();

                sent = true;
            }
            catch(ConnectException e)
            {
                //we've hit the throttle, wait, try again
                try
                {
                    Thread.sleep(5000);
                }
                catch(Exception SleepError)
                {
                    System.out.println(SleepError);
                }
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }

    /**
     * This method takes the arrays of output information (Thomson and Themis IDs)
     * and packages them up into a single CSV output file.
     *
     * @param outputFileName String
     */
    public void CreateOutput(String outputFileName)
    {
        PropertyExtractor pE = new PropertyExtractor();

        String outputString = pE.getOutputCSVTitleThemis() + "," + pE.getOutputCSVTitleThomson() + "\n";

        for(int i = 0; i < themisIDs.size(); i++)
        {
            outputString += themisIDs.get(i) + "," + thomsonIDs.get(i) + "\n";
        }

        File outFile = new File(outputFileName);
        try
        {
            FileWriter out = new FileWriter(outFile);
            out.write(outputString);
            out.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

    }

    /**
     * In the event that the user isn't using this program from an authorized IP
     * address, this method inserts their login credentials into the request XML.
     *
     * @param shell String
     * @param username String
     * @param password String
     * @return String
     */
    private String insertLogin(String shell, String username, String password)
    {
        PropertyExtractor pE = new PropertyExtractor();
        int insertPoint = shell.indexOf(pE.getLoginInsertPoint(), 0);
        StringBuilder sB = new StringBuilder(shell);

        sB.insert(insertPoint, pE.getloginInsert());

        String loginShell = sB.toString();

        loginShell = loginShell.replace(pE.getUsernamePlaceHolder(), username);
        loginShell = loginShell.replace(pE.getPasswordPlaceHolder(), password);

        return loginShell;
    }

    /**
     * This method allows the default constructor to read in XML files as a string.
     *
     * @param filePath String
     * @return String
     * @throws java.io.IOException
     */
    private static String readFileAsString(String filePath) throws java.io.IOException
    {
        byte[] buffer = new byte[(int) new File(filePath).length()];
        BufferedInputStream f = null;
        try {
            f = new BufferedInputStream(new FileInputStream(filePath));
            f.read(buffer);
        } finally {
            if (f != null) try { f.close(); } catch (IOException ignored) { }
        }
        return new String(buffer);
    }

}
