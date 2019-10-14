package test.vladfurg.ncjavatest.agreementapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import test.vladfurg.ncjavatest.agreementproduct.Agreement;

import java.io.File;
import java.io.IOException;

public class AgreementReader
{
    private String filePath;

    /**
     * Constructor
     * @param filePath JSON file to read
     */
    public AgreementReader(String filePath)
    {
        this.filePath = filePath;
    }

    /**
     * Reads passed JSON file and deserializes it into an Agreement object
     * Checks if the specified file exists before attempting to read it
     * @return newly-created Agreement object or null on failure
     * @throws IOException as it works with the Java File class
     */
    public Agreement readJSON() throws IOException
    {
        if(fileExists())
        {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(filePath), Agreement.class);
        }
        else
        {
            System.out.println("Specified file does not exist: " + this.filePath);
            return null;
        }
    }

    /**
     * Checks if the specified file exists
     * @return true if file exists, otherwise false
     */
    private boolean fileExists()
    {
        File inputFile = new File(this.filePath);
        return inputFile.exists();
    }
}