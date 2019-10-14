package test.vladfurg.ncjavatest.agreementproduct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Product
{
    @JsonIgnore
    private Object parent = null; // Ignore this field during serialization in order to avoid infinite recursion between parent-child references
    private String name;
    private double price;
    private ArrayList<Product> products = null;

    /**
     * Empty constructor
     * Required for Jackson during serialization
     */
    public Product() { }

    /**
     * Product constructor
     * Allows for the creation of Products
     * @param name  product's name
     * @param price product's price
     */
    public Product(String name, double price)
    {
        this.name   = name;
        this.price  = price;
        this.products = new ArrayList<Product>();
    }

    /**
     * Getters and setters
     */
    public Object getParent() { return parent; }
    public void setParent(Object parent) { this.parent = parent; }

    public ArrayList<Product> getProducts() { return this.products; }
    public void setProducts(ArrayList<Product> products) { this.products = products; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    /**
     * Single product setter
     * Adds one product to the existing collection and sets the current object as its parent
     * @param product Product object
     */
    public void addProduct(Product product)
    {
        this.products.add(product);
        product.setParent(this);
    }
}
