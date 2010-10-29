/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lamr;

import java.io.*;

/**
 *
 * @author sandy
 */
public class PropertyExtractor
{

    private String extractXPath;
    private String outputCSVTitleThomson;
    private String outputCSVTitleThemis;
    private String regexThomsonPattern;
    private String regexThemisPattern;
    private String regexNoResultFoundPattern;
    private String outputShell;
    private String resultInsertPoint;
    private String usernamePlaceHolder;
    private String passwordPlaceHolder;
    private String loginInsertPoint;
    private String loginInsert;

    /**
     * The default constructor of the PropertyExtractor object
     * 
     * <br><br>
     * 
     * This class was created to facilitate the use of predetermined variables
     * that affect this projects functionality. The benefit of which allows the
     * operator to modify the programs behavior extensively without resorting
     * to changing the source code.
     *
     * <br><br>
     *
     * The aim of this design is to try and future-proof the implementation against
     * changes in the environment, such as different input files or changes to the
     * web service etc.
     *
     */
    public PropertyExtractor()
    {
        //retrieving properties from external file
        try
        {
            FileInputStream fstream = new FileInputStream("LAMR.settings");

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null)
            {
                StringBuilder sB = new StringBuilder(strLine);

                //Retrieve setting
                if(strLine.indexOf("extractXPath:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    extractXPath = s.trim();
                }
                else if(strLine.indexOf("outputCSVTitleThomson:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    outputCSVTitleThomson = s.trim();
                }
                else if(strLine.indexOf("outputCSVTitleThemis:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    outputCSVTitleThemis = s.trim();
                }
                else if(strLine.indexOf("regexThomsonPattern:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    regexThomsonPattern = s.trim();
                }
                else if(strLine.indexOf("regexThemisPattern:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    regexThemisPattern = s.trim();
                }
                else if(strLine.indexOf("regexNoResultFoundPattern:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    regexNoResultFoundPattern = s.trim();
                }
                else if(strLine.indexOf("outputShell:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    outputShell = s.trim();
                }
                else if(strLine.indexOf("resultInsertPoint:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    resultInsertPoint = s.trim();
                }
                else if(strLine.indexOf("usernamePlaceHolder:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    usernamePlaceHolder = s.trim();
                }
                else if(strLine.indexOf("passwordPlaceHolder:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    passwordPlaceHolder = s.trim();
                }
                else if(strLine.indexOf("loginInsertPoint:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    loginInsertPoint = s.trim();
                }
                else if(strLine.indexOf("loginInsert:") != -1)
                {
                    int startChar = strLine.indexOf(" ");
                    sB.delete(0, startChar);
                    String s = sB.toString();
                    loginInsert = s.trim();
                }
            }

            //Close the input stream
            in.close();
        }
        catch (Exception e)
        {
            //Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     *
     * @return String
     */
    public String getExtractXPath()
    {
        return extractXPath;
    }

    /**
     *
     * @return String
     */
    public String getOutputCSVTitleThomson()
    {
        return outputCSVTitleThomson;
    }

    /**
     *
     * @return String
     */
    public String getOutputCSVTitleThemis()
    {
        return outputCSVTitleThemis;
    }

    /**
     *
     * @return String
     */
    public String getRegexThomsonPattern()
    {
        return regexThomsonPattern;
    }

    /**
     *
     * @return String
     */
    public String getRegexThemisPattern()
    {
        return regexThemisPattern;
    }

    /**
     *
     * @return String
     */
    public String getRegexNoResultFoundPattern()
    {
        return regexNoResultFoundPattern;
    }

    /**
     *
     * @return String
     */
    public String getOutputShell()
    {
        return outputShell;
    }

    /**
     *
     * @return String
     */
    public String getResultInsertPoint()
    {
        return resultInsertPoint;
    }

    /**
     *
     * @return String
     */
    public String getUsernamePlaceHolder()
    {
        return usernamePlaceHolder;
    }

    /**
     *
     * @return String
     */
    public String getPasswordPlaceHolder()
    {
        return passwordPlaceHolder;
    }

    /**
     * 
     * @return String
     */
    public String getLoginInsertPoint()
    {
        return loginInsertPoint;
    }

    /**
     *
     * @return String
     */
    public String getloginInsert()
    {
        return loginInsert;
    }
}
