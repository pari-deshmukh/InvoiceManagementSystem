/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoicemanagement;

/**
 *
 * @author pdeshmukh
 */
class Product {
    private int id;
    private String name, description;
    private float discount, unit_price;
    
    public Product(int id, String name, String description, float discount, float unit_price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.unit_price = unit_price;
    }
    
    public int getid() {
        return id;
    }
    public String getname() {
        return name;
    }
    public String getdescription() {
        return description;
    }
    public float getdiscount() {
        return discount;
    }
    public float getunitprice() {
        return unit_price;
    }
    
}
