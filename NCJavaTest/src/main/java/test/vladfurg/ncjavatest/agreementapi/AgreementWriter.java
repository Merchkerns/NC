package test.vladfurg.ncjavatest.agreementapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import test.vladfurg.ncjavatest.agreementproduct.Agreement;
import test.vladfurg.ncjavatest.agreementproduct.Product;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AgreementWriter
{

    public String directoryPath, fileName;
    private Agreement agreement;

    /**
     * Constructor
     * @param agreement Agreement object reference
     * @param directoryPath path to output file
     */
    public AgreementWriter(Agreement agreement, String directoryPath)
    {
        this.agreement = agreement;
        // Add trailing directory separator if such is missing from the passed path
        this.directoryPath = directoryPath.endsWith(File.separator) ? directoryPath : directoryPath + File.separator;
        // Replace forward slashes (/) with underscores (_) for the output file name to bypass file naming restrictions
        this.fileName = this.agreement.getName().replace("/", "_") + ".json";
    }

    /**
     * Writes passed Agreement object to file
     * Performs validation on the passed target path (validatePath() method)
     * Performs validation on the passed Agreement object (validateObject() method)
     * @throws IOException as it works with the Java File class
     */
    public void writeJSON() throws IOException
    {
        if(validatePath() && validateObject())
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(this.directoryPath + this.fileName), agreement);
        }
        else
        {
            System.out.println("Exiting");
            System.exit(0);
        }
    }

    /**
     * Performs the following checks to determine whether to proceed with output file creation:
     * >If the passed directory exists (prompts directory creation if it doesn't)
     * >If a file for the passed agreement exists (prompts file overwrite if it does)
     * @return true if all checks are passed, false if any of them fail or the user prompts are rejected
     * @throws IOException as it works with the Java File class
     */
    public boolean validatePath() throws IOException {
       if(directoryExists())
       {
           if(fileExists())
           {
               System.out.println("File " + this.fileName + " already exists");
               return confirmFileOverwrite();
           }
           else
               return true;
       }
       else
       {
           // If the directory doesn't exist, no need to check for the file's existence
           System.out.println("Directory " + this.directoryPath + " doesn't exist");
           return confirmDirectoryCreation();
       }
    }

    /**
     * Performs the following checks on the passed Agreement object:
     * >If any of the Agreement's child Products have a reference to parent Product (incorrect)
     * @return true if all criteria are met, false otherwise
     */
    private boolean validateObject()
    {
        for(Product childProduct: this.agreement.getProducts())
        {
            if(childProduct.getParent() instanceof Product)
            {
                System.out.println("Error: Products with a parent of the type Product are not allowed: " + childProduct.getName());
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for the existence of the passed directory
     * @return true if the passed path exists and points to a directory
     */
    private boolean directoryExists()
    {
        File path = new File(this.directoryPath);
        return path.exists() && path.isDirectory();
    }

    /**
     * Checks for the existence of an Agreement file in the passed path
     * @return true if a file with the Agreement's name already exists
     */
    private boolean fileExists()
    {
        File file = new File(this.directoryPath + this.fileName);
        return file.exists();
    }

    /**
     * Prompts user to confirm directory creation and invokes the createDirectory() method upon confirmation
     * @return value returned from the createDirectory() method or false upon user rejection
     * @throws IOException as it works with the Java File class
     */
    private boolean confirmDirectoryCreation() throws IOException {
        boolean confirm;
        String response;
        Scanner input = new Scanner(System.in);

        while(true)
        {
            System.out.println("Create directory? (y/n)");
            response = input.nextLine().toLowerCase().trim();

            if(response.equals("y"))
            {
                confirm = true;
                break;
            }
            else if(response.equals("n"))
            {
                confirm = false;
                break;
            }
            else
                System.out.println("Invalid response: " + response);
        }

        if(confirm)
            return createDirectory();
        else return false;
    }

    /**
     * Prompts user to confirm file overwrite
     * @return true upon user confirmation or false upon rejection
     */
    private boolean confirmFileOverwrite()
    {
        boolean confirm;
        String response;
        Scanner input = new Scanner(System.in);

        while(true)
        {
            System.out.println("Overwrite file? (y/n)");
            response = input.nextLine().toLowerCase().trim();

            if(response.equals("y"))
            {
                confirm = true;
                break;
            }
            else if(response.equals("n"))
            {
                confirm = false;
                break;
            }
            else
                System.out.println("Invalid response: " + response);
        }

        return confirm;
    }

    /**
     * Creates the specified directory
     * @return true if a directory is successfully created, false otherwise
     * @throws IOException as it works with the Java File class
     */
    private boolean createDirectory() throws IOException
    {
        File targetDirectory = new File(this.directoryPath);
        return targetDirectory.mkdir();
    }
}
