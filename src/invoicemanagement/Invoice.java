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
    private String customer, customer_contact_number, remarks;
    private Date inv_date;
    private float subtotal, vat, service_tax, inv_amt;
    
    public Invoice(int id, String customer, String customer_contact_number, String remarks, Date inv_date, float subtotal, float vat, float service_tax, float inv_amt) {
        this.id = id;
        this.customer = customer;
        this.customer_contact_number = customer_contact_number;
        this.remarks = remarks;
        this.inv_date = inv_date;
        this.subtotal = subtotal;
        this.vat = vat;
        this.service_tax = service_tax;
        this.inv_amt = inv_amt;
    }
    
    public int getid() {
        return id;
    }
    public String getcustomer() {
        return customer;
    }
    public String getcustomercontactnumber() {
        return customer_contact_number;
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
    public float getinv_amt() {
        return inv_amt;
    }
}
