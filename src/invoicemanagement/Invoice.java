/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoicemanagement;

import java.sql.Date;

/**
 *
 * @author pdeshmukh
 */
class Invoice {
    private int id;
    private String customer, remarks;
    private Date inv_date;
    private float subtotal, vat, service_tax, discount, cash, change_amt;
    
    public Invoice(int id, String customer, String remarks, Date inv_date, float subtotal, float vat, float service_tax, float discount, float cash, float change_amt) {
        this.id = id;
        this.customer = customer;
        this.remarks = remarks;
        this.inv_date = inv_date;
        this.subtotal = subtotal;
        this.vat = vat;
        this.service_tax = service_tax;
        this.discount = discount;
        this.cash = cash;
        this.change_amt = change_amt;
    }
    
    public int getid() {
        return id;
    }
    public String getcustomer() {
        return customer;
    }
    public String getremarks() {
        return remarks;
    }
    public Date getinvdate() {
        return inv_date;
    }
    public float getsubtotal() {
        return subtotal;
    }
    public float getvat() {
        return vat;
    }
    public float getservicetax() {
        return service_tax;
    }
    public float getdiscount() {
        return discount;
    }
    public float getcash() {
        return cash;
    }
    public float getchange_amt() {
        return change_amt;
    }
}
