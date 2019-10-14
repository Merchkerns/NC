package test.vladfurg.ncjavatest;

import test.vladfurg.ncjavatest.agreementapi.AgreementReader;
import test.vladfurg.ncjavatest.agreementapi.AgreementWriter;
import test.vladfurg.ncjavatest.agreementproduct.Agreement;
import test.vladfurg.ncjavatest.agreementproduct.Product;

import java.io.IOException;
import java.util.ArrayList;

public class Main
{
    public static void main( String[] args ) throws IOException {
        // Create a new Agreement object
        Agreement testAgreement = new Agreement("Vladislavs Furgins");

        // Test products - parent level (go directly under the Agreement object)
        ArrayList<Product> parentProducts = new ArrayList<Product>();
        parentProducts.add(new Product("Parent Product 1", 1.99d));
        parentProducts.add(new Product("Parent Product 2", 1.99d));
        parentProducts.add(new Product("Parent Product 3", 1.99d));
        parentProducts.add(new Product("Parent Product 4", 1.99d));
        parentProducts.add(new Product("Parent Product 5", 1.99d));
        // Erroneous case: parent-level Product with another Product as its parent
        parentProducts.get(4).setParent(parentProducts.get(0));

        // Test products - child level (not allowed to be added to Agreement object)
        ArrayList<Product> childProducts = new ArrayList<Product>();
        childProducts.add(new Product("Child Product 1", 10.99d));
        childProducts.add(new Product("Child Product 2", 10.99d));
        childProducts.add(new Product("Child Product 3", 10.99d));

        // Test product - second child level
        childProducts.get(2).addProduct(new Product("Grandchild Product 1", 100.99d));

        // Add all of the child products to each parent product
        for(Product testParent : parentProducts)
        {
            for(Product testChild : childProducts)
                testParent.addProduct(testChild);

            testAgreement.addProduct(testParent);
        }

        // Target directory for the output file
        String filePath = System.getProperty("user.dir");

        // Write object to file
        AgreementWriter writer = new AgreementWriter(testAgreement, filePath);
        writer.writeJSON();

        // Read object from the newly-created file
        AgreementReader reader = new AgreementReader(writer.directoryPath + writer.fileName);
        Agreement readAgreement = reader.readJSON();

        // Only continue execution if an Agreement object was successfully created
        if(readAgreement != null)
        {
            // Change some of the agreement's fields for testing purposes
            readAgreement.setName("Read Agreement");
            readAgreement.setSignedBy("Furgins Vladislavs");

            // Add a new top-level Product
            readAgreement.addProduct(new Product("Newly-added Parent Product", 9.99));

            // Fetch the Agreement's products
            ArrayList<Product> readProducts = readAgreement.getProducts();

            // Make sure we actually fetched something
            if(!readProducts.isEmpty())
            {
                // Add a new child Product to the newly-added parent Product
                readProducts.get(readProducts.size() - 1).addProduct(new Product("Newly-added Child Product", 99.99));
            }

            // Write the edited Agreement to a new (since the Agreement's name was changed) JSON file
            writer = new AgreementWriter(readAgreement, filePath);
            writer.writeJSON();
        }
    }
}
