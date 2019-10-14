package test.vladfurg.ncjavatest.agreementproduct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Agreement
{
    private String name, signedBy;
    ArrayList<Product> products;

    /**
     * Empty constructor
     * Required for Jackson during serialization
     */
    public Agreement() { }

    /**
     * Agreement constructor
     * Creates an empty (no child products) Agreement object and sets its field values
     * @param signedBy name of the person who signed the agreement
     */
    public Agreement(String signedBy)
    {
        this.name       = "Agreement " + new SimpleDateFormat("dd/MM/yy").format(new Date());
        this.signedBy   = signedBy;
        this.products   = new ArrayList<Product>();
    }

    /**
     * Filled-out Agreement constructor
     * Creates an Agreement object with child products
     * Allows child-products for testing purposes (validation is implemented in the AgreementWriter class)
     * @param signedBy  name of the person who signed the agreement
     * @param products collection of products belonging to this agreement
     */
    public Agreement(String signedBy, ArrayList<Product> products)
    {
        this(signedBy);
        this.products = products;
    }

    /**
     * Getters and setters
     */
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public String getSignedBy() { return this.signedBy; }
    public void setSignedBy(String signedBy) { this.signedBy = signedBy; }


    public ArrayList<Product> getProducts() { return this.products; }
    public void setProducts(ArrayList<Product> products) { this.products = products; }

    /**
     * Allows for the addition of individual Product objects
     * Only adds Products that don't have a reference to a Product object in their parent field
     * @param product Product object to be added to the Agreement
     */
    public void addProduct(Product product)
    {
        if(!(product.getParent() instanceof Product))
        {
            product.setParent(this);
            this.products.add(product);
        }
        else
            System.out.println(product.getName() + ": Products with parents are not allowed directly under Agreement");
    }

}
