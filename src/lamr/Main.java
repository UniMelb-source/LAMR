/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lamr;

/**
 *
 * @author David Cliff
 *
 * <br><br>
 *
 * This program contacts the LAMR service and searches for the values found in
 * the input file, guided by the properties in the LAMR.settings document.
 *
 * <br><br>
 *
 * When all the results have been collated, they are then formed into an output
 * file. If no output filename has been chosen at the command line, the default
 * output.csv is used.
 */
public class Main
{

    /**
     * The main class drives the program through the use of the other classes
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        String filename = "";
        String outputFilename = "output.csv";

        String username = "";
        String password = "";
        boolean login = false;

        try
        {
            for (int i = 0; i < args.length; i++)
            {
                if(args[i].equalsIgnoreCase("-inputFile"))
                {
                    filename = args[(i + 1)];
                }
                else if(args[i].equalsIgnoreCase("-login"))
                {
                    login = true;
                    username = args[(i + 1)];
                    password = args[(i + 2)];
                }
                else if(args[i].equalsIgnoreCase("-outputFile"))
                {                    
                    outputFilename = args[(i + 1)];
                }
                else if(args[i].equalsIgnoreCase("-h"))
                {
                    consoleHelp();
                }
            }
        }
        catch(Exception e)
        {
            consoleHelp();
        }

        if(filename.equals(""))
        {
            System.out.println("Input file name is required\n");
            consoleHelp();
        }

        XMLSender sX = new XMLSender(filename, login, username, password);
        sX.CreateOutput(outputFilename);

    }

    /**
     * The menu for the command-line usage of the program
     *
     */
    private static void consoleHelp()
    {
        System.out.print("Correct usage of this application: ");
        System.out.println("java -jar WebOfScience.jar <flags>\n");
        System.out.println("If an output filename isn't chosen, the default records.csv will be used");
        System.out.println("\n\tFlags:");
        System.out.println("\n\t-inputFile filename");
        System.out.println("\t-login username password");
        System.out.println("\t-output filename");
        System.out.println("\t-h - Display this help menu\n");
        System.exit(0);
    }

}
