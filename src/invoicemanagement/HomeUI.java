/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invoicemanagement;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author pdeshmukh
 */
public class HomeUI extends javax.swing.JFrame {

    private int xx;
    private int xy;
    
    public ArrayList<Invoice> invoiceList() {
        ArrayList<Invoice> invoiceList = new ArrayList<>();
        
        PreparedStatement ps;
        ResultSet rs;
        Invoice invoice;
        String queryInvoices = "Select * FROM `inv_invoices`";

        try {

            ps = DbConnection.getConnection().prepareStatement(queryInvoices);

            rs = ps.executeQuery();
            
            while(rs.next()) {
                invoice = new Invoice(rs.getInt("id"), rs.getString("customer"), rs.getString("customer_contact_number"), rs.getString("remarks"), rs.getDate("inv_date"), rs.getFloat("subtotal"), rs.getFloat("vat"), rs.getFloat("service_tax"), rs.getFloat("inv_amt"));
                invoiceList.add(invoice);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        return invoiceList;
    }
    
    public void show_invoice() {
        ArrayList<Invoice> inv_list = invoiceList();
        DefaultTableModel itm = (DefaultTableModel)invoice_list_tbl.getModel();
        itm.setRowCount(0);
        Object[] row = new Object[5];
        for (int i=0;i<inv_list.size();i++) {
            row[0] = inv_list.get(i).getid();
            row[1] = inv_list.get(i).getcustomer();
            row[2] = inv_list.get(i).getinvdate();
            row[3] = inv_list.get(i).getremarks();
            row[4] = inv_list.get(i).getinv_amt();
            itm.addRow(row);
        }
    }
    
    private int addInvoice(String cust_name, String cust_contact, String remarks, Date inv_date, float subtotal, float vat, float st, float inv_amt) {
        PreparedStatement ps;
        ResultSet rs;
        String updateInvoice = "INSERT INTO `inv_invoices` (customer, customer_contact_number, remarks, inv_date, subtotal, vat, service_tax, inv_amt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int inv_id = -1;
        try {
            ps = DbConnection.getConnection().prepareStatement(updateInvoice);
            
            ps.setString(1, cust_name);
            ps.setString(2, cust_contact);
            ps.setString(3, remarks);
            ps.setObject(4, inv_date);
            ps.setFloat(5, subtotal);
            ps.setFloat(6, vat);
            ps.setFloat(7, st);
            ps.setFloat(8, inv_amt);
            
            ps.executeUpdate();
            rs = ps.executeQuery("SELECT LAST_INSERT_ID()");
            if(rs.next()) {
                inv_id = rs.getInt(1);
            } else {
                JOptionPane.showMessageDialog(this, "Invoice not Found!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        return inv_id;
    }

    private void updateInvoice(int inv_id, String cust_name, String cust_contact, String remarks, Date inv_date, float subtotal, float vat, float st, float inv_amt) {
        PreparedStatement ps;
        int rs;
        String updateInvoice = "UPDATE `inv_invoices` SET customer = ?, customer_contact_number = ?, remarks = ?, inv_date = ?, subtotal = ?, vat = ?, service_tax = ?, inv_amt = ? where id = ?";
        
        try {
            ps = DbConnection.getConnection().prepareStatement(updateInvoice);
            
            ps.setString(1, cust_name);
            ps.setString(2, cust_contact);
            ps.setString(3, remarks);
            ps.setObject(4, inv_date);
            ps.setFloat(5, subtotal);
            ps.setFloat(6, vat);
            ps.setFloat(7, st);
            ps.setFloat(8, inv_amt);
            ps.setFloat(9, inv_id);
            
            rs = ps.executeUpdate();
            if(rs > 0) {
                JOptionPane.showMessageDialog(this, "Invoice Updated!");
            } else {
                JOptionPane.showMessageDialog(this, "Invoice not Found!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
    
    private void updateInvProduct(int inv_id, int pdt_id, int qty) {
        PreparedStatement ps;
        int rs;
        
        String updateInvoice = "INSERT INTO `inv_invoice_products` (qty,product_id,invoice_id) VALUES (?, ?, ?)";
        
        try {
            ps = DbConnection.getConnection().prepareStatement(updateInvoice);
            
            ps.setInt(1, qty);
            ps.setInt(2, pdt_id);
            ps.setInt(3, inv_id);
            
            rs = ps.executeUpdate();
            if(rs > 0) {
                JOptionPane.showMessageDialog(this, "Invoice Products Updated!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
    
    private void clearInvProduct(int inv_id) {
        PreparedStatement ps;
        String clearInvProduct = "DELETE FROM `inv_invoice_products` WHERE invoice_id = ?";
        try {
            ps = DbConnection.getConnection().prepareStatement(clearInvProduct);

            ps.setInt(1, inv_id);

            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
    
    public void delete_invoice(int inv_id) {
        PreparedStatement ps;
        int rs;
        String deleteInvoice = "DELETE FROM `inv_invoices` WHERE id = ?";
        
        try {
            ps = DbConnection.getConnection().prepareStatement(deleteInvoice);
            
            ps.setInt(1, inv_id);
            
            rs = ps.executeUpdate();
            if(rs > 0) {
                JOptionPane.showMessageDialog(this, "Invoice Deleted!");
            } else {
                JOptionPane.showMessageDialog(this, "Invoice not Found!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
    
    public void delete_product(int pdt_id) {
        PreparedStatement ps;
        int rs;
        String deleteProduct = "DELETE FROM `inv_products` WHERE id = ?";
        
        try {
            ps = DbConnection.getConnection().prepareStatement(deleteProduct);
            
            ps.setInt(1, pdt_id);
            
            rs = ps.executeUpdate();
            if(rs > 0) {
                JOptionPane.showMessageDialog(this, "Product Deleted!");
            } else {
                JOptionPane.showMessageDialog(this, "Product not Found!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
    
    public ArrayList<Product> productList() {
        ArrayList<Product> productList = new ArrayList<>();
        
        PreparedStatement ps;
        ResultSet rs;
        Product product;
        String queryInvoices = "Select * FROM `inv_products`";

        try {

            ps = DbConnection.getConnection().prepareStatement(queryInvoices);

            rs = ps.executeQuery();
            
            while(rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getFloat("discount"), rs.getFloat("unit_price"));
                productList.add(product);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        return productList;
    }
    
    public ArrayList<Product> invProductList(int id) {
        ArrayList<Product> invProductList = new ArrayList<>();
        
        PreparedStatement ps;
        ResultSet rs;
        Product product;
        String queryInvProducts = "select inv_invoices.*, inv_invoice_products.*, inv_products.*  from inv_invoices inner join inv_invoice_products on inv_invoices.id = inv_invoice_products.invoice_id inner join inv_products on inv_products.id = inv_invoice_products.product_id where inv_invoices.id = ?";

        try {

            ps = DbConnection.getConnection().prepareStatement(queryInvProducts);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()) {
                product = new Product(rs.getInt("product_id"), rs.getString("name"), rs.getString("description"), rs.getFloat("discount"), rs.getFloat("unit_price"));
                product.qty = rs.getInt("qty");
                invProductList.add(product);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        return invProductList;
    }
    public void get_products() {
        ArrayList<Product> pdt_list = productList();
        DefaultTableModel ptm = (DefaultTableModel)product_list_tbl.getModel();
        ptm.setRowCount(0);
        add_invoice_product_combo_box.removeAllItems();
        update_invoice_product_combo_box.removeAllItems();
        Object[] row = new Object[5];
        for (int i=0;i<pdt_list.size();i++) {
            row[0] = pdt_list.get(i).getid();
            row[1] = pdt_list.get(i).getname();
            row[2] = pdt_list.get(i).getdescription();
            row[3] = pdt_list.get(i).getunitprice();
            row[4] = pdt_list.get(i).getdiscount();
            ptm.addRow(row);
            add_invoice_product_combo_box.addItem(pdt_list.get(i).getname());
            update_invoice_product_combo_box.addItem(pdt_list.get(i).getname());
        }
    }
    public Product get_product(int id) {
        PreparedStatement ps;
        ResultSet rs;
        Product product = new Product(0,"","",0,0);
        String queryProduct = "select * from inv_products where inv_products.id = ?";
        
        try {
            ps = DbConnection.getConnection().prepareStatement(queryProduct);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            
            while(rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getFloat("discount"), rs.getFloat("unit_price"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        return product;
    }

    private Product getProductByName(String name) {
        PreparedStatement ps;
        ResultSet rs;
        Product product = new Product(0,"","",0,0);
        String queryProduct = "select * from inv_products where inv_products.name = ?";
        
        try {
            ps = DbConnection.getConnection().prepareStatement(queryProduct);
            ps.setString(1, name);

            rs = ps.executeQuery();
            
            while(rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getFloat("discount"), rs.getFloat("unit_price"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
        return product;
    }
    
    public void get_inv_products(int id) {
        ArrayList<Product> inv_pdt_list = invProductList(id);
        DefaultTableModel iptm = (DefaultTableModel)update_product_details_tbl.getModel();
        iptm.setRowCount(0);
        Object[] row = new Object[6];
        for (int i=0;i<inv_pdt_list.size();i++) {
            row[0] = inv_pdt_list.get(i).getid();
            row[1] = inv_pdt_list.get(i).getname();
            row[2] = inv_pdt_list.get(i).getunitprice();
            row[3] = inv_pdt_list.get(i).getqty();
            row[4] = inv_pdt_list.get(i).getdiscount();
            row[5] = (inv_pdt_list.get(i).getunitprice() - inv_pdt_list.get(i).getdiscount()) * inv_pdt_list.get(i).getqty();
            iptm.addRow(row);
        }
    }

    private void add_inv_amounts() {
        DefaultTableModel iptm = (DefaultTableModel)add_product_details_tbl.getModel();
        float subtotal = 0;
        float vat = 0;
        float st = 0;
        float inv_amt = 0;
        for (int i = 0; i < iptm.getRowCount(); i++) {
            subtotal += Float.parseFloat(iptm.getValueAt(i, 5).toString());
        }
        vat = (Float.parseFloat(add_vat_percent_input.getText())/100) * subtotal;
        st = (Float.parseFloat(add_st_percent_input.getText())/100) * subtotal;
        inv_amt = subtotal + vat + st;
        add_subtotal_input.setText(String.format("%.2f",subtotal));
        add_vat_amount_input.setText(String.format("%.2f",vat));
        add_st_amount_input.setText(String.format("%.2f",st));
        add_inv_amt_input.setText(String.format("%.2f",inv_amt));
        
    }

    private void update_inv_amounts() {
        DefaultTableModel iptm = (DefaultTableModel)update_product_details_tbl.getModel();
        float subtotal = 0;
        float vat = 0;
        float st = 0;
        float inv_amt = 0;
        for (int i = 0; i < iptm.getRowCount(); i++) {
            subtotal += Float.parseFloat(iptm.getValueAt(i, 5).toString());
        }
        vat = (Float.parseFloat(update_vat_percent_input.getText())/100) * subtotal;
        st = (Float.parseFloat(update_st_percent_input.getText())/100) * subtotal;
        inv_amt = subtotal + vat + st;
        update_subtotal_input.setText(String.format("%.2f",subtotal));
        update_vat_amount_input.setText(String.format("%.2f",vat));
        update_st_amount_input.setText(String.format("%.2f",st));
        update_inv_amt_input.setText(String.format("%.2f",inv_amt));
        
    }
    
    /**
     * Creates new form Home
     */
    public HomeUI() {
        initComponents();
        invoice_list_scrl_pane.getViewport().setBackground(Color.WHITE);
        invoice_list_tbl.getTableHeader().setDefaultRenderer(new TblHeaderColor());
        invoice_list_tbl.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        product_list_scrl_pane.getViewport().setBackground(Color.WHITE);
        product_list_tbl.getTableHeader().setDefaultRenderer(new TblHeaderColor());
        product_list_tbl.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.LEFT);
        invoice_list_tbl.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        product_list_tbl.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        product_list_tbl.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        this.show_invoice();
        this.get_products();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        home_pnl = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        logout = new javax.swing.JLabel();
        user_login_heading_lbl = new javax.swing.JLabel();
        exit_pnl = new javax.swing.JPanel();
        exit_lbl = new javax.swing.JLabel();
        sidebar = new javax.swing.JPanel();
        sidebar_menu = new javax.swing.JPanel();
        sidebar_invoice_lbl = new javax.swing.JLabel();
        sidebar_invoice_list_btn_lbl = new javax.swing.JLabel();
        sidebar_add_invoice_btn_lbl = new javax.swing.JLabel();
        sidebar_update_invoice_btn_lbl = new javax.swing.JLabel();
        sidebar_delete_invoice_btn_lbl = new javax.swing.JLabel();
        sidebar_product_lbl = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        sidebar_product_list_btn_lbl = new javax.swing.JLabel();
        sidebar_add_product_btn_lbl = new javax.swing.JLabel();
        sidebar_update_product_btn_lbl = new javax.swing.JLabel();
        sidebar_delete_product_btn_lbl = new javax.swing.JLabel();
        content = new javax.swing.JPanel();
        invoice_list_scrl_pane = new javax.swing.JScrollPane();
        invoice_list_tbl = new javax.swing.JTable();
        add_invoice_pnl = new javax.swing.JPanel();
        add_invoice_btn_lbl = new javax.swing.JLabel();
        add_remarks_lbl = new javax.swing.JLabel();
        add_inv_amt_input = new javax.swing.JTextField();
        remarks_scrl_pane2 = new javax.swing.JScrollPane();
        add_remarks_input = new javax.swing.JTextPane();
        update_invoice_lbl1 = new javax.swing.JLabel();
        add_subtotal_lbl = new javax.swing.JLabel();
        add_subtotal_input = new javax.swing.JTextField();
        add_vat_lbl = new javax.swing.JLabel();
        add_product_name_lbl = new javax.swing.JLabel();
        add_cash_lbl = new javax.swing.JLabel();
        add_invoice_number_input = new javax.swing.JTextField();
        add_vat_percent_input = new javax.swing.JTextField();
        add_qty_lbl = new javax.swing.JLabel();
        add_invoice_number_lbl = new javax.swing.JLabel();
        add_vat_percent_lbl = new javax.swing.JLabel();
        add_qty_input = new javax.swing.JTextField();
        add_customer_name_input = new javax.swing.JTextField();
        add_vat_amount_input = new javax.swing.JTextField();
        add_unit_price_lbl = new javax.swing.JLabel();
        add_invoice_product_combo_box = new javax.swing.JComboBox<>();
        add_invoice_customer_name = new javax.swing.JLabel();
        add_service_tax_lbl = new javax.swing.JLabel();
        add_unit_price_input = new javax.swing.JTextField();
        add_st_percent_input = new javax.swing.JTextField();
        add_total_amount_lbl = new javax.swing.JLabel();
        add_invoice_date_input = new com.toedter.calendar.JDateChooser();
        add_st_percent_lbl = new javax.swing.JLabel();
        add_invoice_pdt_total_amount_input = new javax.swing.JTextField();
        add_invoice_date_lbl = new javax.swing.JLabel();
        add_st_amount_input = new javax.swing.JTextField();
        add_delete_from_cart_btn = new javax.swing.JButton();
        product_details_scrl_pane2 = new javax.swing.JScrollPane();
        add_product_details_tbl = new javax.swing.JTable();
        add_add_to_cart_btn = new javax.swing.JButton();
        add_product_details_lbl = new javax.swing.JLabel();
        add_invoice_customer_phone = new javax.swing.JLabel();
        add_customer_contact_number_input = new javax.swing.JTextField();
        add_vat_percent_lbl1 = new javax.swing.JLabel();
        add_st_percent_lbl1 = new javax.swing.JLabel();
        update_invoice_pnl = new javax.swing.JPanel();
        update_invoice_btn_lbl = new javax.swing.JLabel();
        update_remarks_lbl = new javax.swing.JLabel();
        update_inv_amt_input = new javax.swing.JTextField();
        remarks_scrl_pane1 = new javax.swing.JScrollPane();
        update_remarks_input = new javax.swing.JTextPane();
        update_invoice_lbl = new javax.swing.JLabel();
        update_subtotal_lbl = new javax.swing.JLabel();
        update_subtotal_input = new javax.swing.JTextField();
        update_vat_lbl = new javax.swing.JLabel();
        update_product_name_lbl = new javax.swing.JLabel();
        update_cash_lbl = new javax.swing.JLabel();
        update_invoice_number_input = new javax.swing.JTextField();
        update_vat_percent_input = new javax.swing.JTextField();
        update_qty_lbl = new javax.swing.JLabel();
        update_invoice_number_lbl = new javax.swing.JLabel();
        update_vat_percent_lbl = new javax.swing.JLabel();
        update_qty_input = new javax.swing.JTextField();
        update_customer_name_input = new javax.swing.JTextField();
        update_vat_amount_input = new javax.swing.JTextField();
        update_unit_price_lbl = new javax.swing.JLabel();
        update_invoice_product_combo_box = new javax.swing.JComboBox<>();
        update_invoice_customer_name = new javax.swing.JLabel();
        update_service_tax_lbl = new javax.swing.JLabel();
        update_unit_price_input = new javax.swing.JTextField();
        update_st_percent_input = new javax.swing.JTextField();
        update_total_amount_lbl = new javax.swing.JLabel();
        update_invoice_date_input = new com.toedter.calendar.JDateChooser();
        update_st_percent_lbl = new javax.swing.JLabel();
        update_invoice_pdt_total_amount_input = new javax.swing.JTextField();
        update_invoice_date_lbl = new javax.swing.JLabel();
        update_st_amount_input = new javax.swing.JTextField();
        update_delete_from_cart_btn = new javax.swing.JButton();
        product_details_scrl_pane1 = new javax.swing.JScrollPane();
        update_product_details_tbl = new javax.swing.JTable();
        update_add_to_cart_btn = new javax.swing.JButton();
        update_product_details_lbl1 = new javax.swing.JLabel();
        update_invoice_customer_phone = new javax.swing.JLabel();
        update_customer_contact_number_input = new javax.swing.JTextField();
        update_vat_percent_lbl1 = new javax.swing.JLabel();
        update_vat_percent_lbl2 = new javax.swing.JLabel();
        delete_invoice_pnl = new javax.swing.JPanel();
        delete_invoice_id_lbl = new javax.swing.JLabel();
        delete_invoice_lbl = new javax.swing.JLabel();
        delete_invoice_id_input = new javax.swing.JTextField();
        delete_invoice_btn_lbl = new javax.swing.JLabel();
        product_list_scrl_pane = new javax.swing.JScrollPane();
        product_list_tbl = new javax.swing.JTable();
        add_product_pnl = new javax.swing.JPanel();
        new_pdt_lbl = new javax.swing.JLabel();
        pdt_name_input = new javax.swing.JTextField();
        pdt_name_lbl = new javax.swing.JLabel();
        pdt_desc_lbl = new javax.swing.JLabel();
        pdt_desc_input = new javax.swing.JTextField();
        pdt_unit_price_lbl = new javax.swing.JLabel();
        pdt_unit_price_input = new javax.swing.JTextField();
        pdt_discount_lbl = new javax.swing.JLabel();
        pdt_discount_input = new javax.swing.JTextField();
        save_product_btn_lbl = new javax.swing.JLabel();
        update_product_pnl = new javax.swing.JPanel();
        update_pdt_desc_input = new javax.swing.JTextField();
        pdt_unit_price_lbl2 = new javax.swing.JLabel();
        update_pdt_unit_price_input = new javax.swing.JTextField();
        pdt_discount_lbl2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        update_pdt_discount_input = new javax.swing.JTextField();
        pdt_id_input = new javax.swing.JTextField();
        update_product_btn_lbl = new javax.swing.JLabel();
        pdt_name_lbl3 = new javax.swing.JLabel();
        pdt_desc_lbl2 = new javax.swing.JLabel();
        pdt_id_lbl = new javax.swing.JLabel();
        update_pdt_name_input = new javax.swing.JTextField();
        delete_product_pnl = new javax.swing.JPanel();
        delete_product_lbl = new javax.swing.JLabel();
        delete_product_id_input = new javax.swing.JTextField();
        delete_product_id_lbl = new javax.swing.JLabel();
        delete_product_btn_lbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);

        home_pnl.setBackground(new java.awt.Color(255, 255, 255));
        home_pnl.setLayout(null);

        header.setOpaque(false);
        header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerMouseDragged(evt);
            }
        });
        header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                headerMouseReleased(evt);
            }
        });

        logout.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        logout.setForeground(new java.awt.Color(255, 255, 255));
        logout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/invoicemanagement/logout.png"))); // NOI18N
        logout.setToolTipText("Log Out");
        logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });

        user_login_heading_lbl.setFont(new java.awt.Font("Calibri", 0, 36)); // NOI18N
        user_login_heading_lbl.setForeground(new java.awt.Color(102, 102, 102));
        user_login_heading_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        user_login_heading_lbl.setText("Invoice Management");
        user_login_heading_lbl.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        exit_pnl.setBackground(new java.awt.Color(0, 102, 204));
        exit_pnl.setPreferredSize(new java.awt.Dimension(40, 40));

        exit_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        exit_lbl.setForeground(new java.awt.Color(255, 255, 255));
        exit_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        exit_lbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/invoicemanagement/close.png"))); // NOI18N
        exit_lbl.setToolTipText("Exit");
        exit_lbl.setAlignmentY(0.0F);
        exit_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exit_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        exit_lbl.setPreferredSize(new java.awt.Dimension(40, 40));
        exit_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exit_lblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout exit_pnlLayout = new javax.swing.GroupLayout(exit_pnl);
        exit_pnl.setLayout(exit_pnlLayout);
        exit_pnlLayout.setHorizontalGroup(
            exit_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exit_pnlLayout.createSequentialGroup()
                .addComponent(exit_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        exit_pnlLayout.setVerticalGroup(
            exit_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exit_pnlLayout.createSequentialGroup()
                .addComponent(exit_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 287, Short.MAX_VALUE)
                .addComponent(user_login_heading_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit_pnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addComponent(exit_pnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(user_login_heading_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        home_pnl.add(header);
        header.setBounds(0, 0, 1041, 64);

        sidebar.setBackground(new java.awt.Color(0, 102, 204));

        sidebar_menu.setOpaque(false);

        sidebar_invoice_lbl.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        sidebar_invoice_lbl.setForeground(new java.awt.Color(255, 255, 255));
        sidebar_invoice_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidebar_invoice_lbl.setText("INVOICE");

        sidebar_invoice_list_btn_lbl.setBackground(new java.awt.Color(0, 51, 153));
        sidebar_invoice_list_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        sidebar_invoice_list_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        sidebar_invoice_list_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidebar_invoice_list_btn_lbl.setText("Invoice List");
        sidebar_invoice_list_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sidebar_invoice_list_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sidebar_invoice_list_btn_lbl.setOpaque(true);
        sidebar_invoice_list_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sidebar_invoice_list_btn_lblMouseClicked(evt);
            }
        });

        sidebar_add_invoice_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        sidebar_add_invoice_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        sidebar_add_invoice_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        sidebar_add_invoice_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidebar_add_invoice_btn_lbl.setText("Add Invoice");
        sidebar_add_invoice_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sidebar_add_invoice_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sidebar_add_invoice_btn_lbl.setOpaque(true);
        sidebar_add_invoice_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sidebar_add_invoice_btn_lblMouseClicked(evt);
            }
        });

        sidebar_update_invoice_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        sidebar_update_invoice_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        sidebar_update_invoice_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        sidebar_update_invoice_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidebar_update_invoice_btn_lbl.setText("Update Invoice");
        sidebar_update_invoice_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sidebar_update_invoice_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sidebar_update_invoice_btn_lbl.setOpaque(true);
        sidebar_update_invoice_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sidebar_update_invoice_btn_lblMouseClicked(evt);
            }
        });

        sidebar_delete_invoice_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        sidebar_delete_invoice_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        sidebar_delete_invoice_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        sidebar_delete_invoice_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidebar_delete_invoice_btn_lbl.setText("Delete Invoice");
        sidebar_delete_invoice_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sidebar_delete_invoice_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sidebar_delete_invoice_btn_lbl.setOpaque(true);
        sidebar_delete_invoice_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sidebar_delete_invoice_btn_lblMouseClicked(evt);
            }
        });

        sidebar_product_lbl.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        sidebar_product_lbl.setForeground(new java.awt.Color(255, 255, 255));
        sidebar_product_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidebar_product_lbl.setText("PRODUCT");

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator1.setPreferredSize(new java.awt.Dimension(50, 2));

        sidebar_product_list_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        sidebar_product_list_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        sidebar_product_list_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        sidebar_product_list_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidebar_product_list_btn_lbl.setText("Product List");
        sidebar_product_list_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sidebar_product_list_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sidebar_product_list_btn_lbl.setOpaque(true);
        sidebar_product_list_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sidebar_product_list_btn_lblMouseClicked(evt);
            }
        });

        sidebar_add_product_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        sidebar_add_product_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        sidebar_add_product_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        sidebar_add_product_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidebar_add_product_btn_lbl.setText("Add Product");
        sidebar_add_product_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sidebar_add_product_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sidebar_add_product_btn_lbl.setOpaque(true);
        sidebar_add_product_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sidebar_add_product_btn_lblMouseClicked(evt);
            }
        });

        sidebar_update_product_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        sidebar_update_product_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        sidebar_update_product_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        sidebar_update_product_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidebar_update_product_btn_lbl.setText("Update Product");
        sidebar_update_product_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sidebar_update_product_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sidebar_update_product_btn_lbl.setOpaque(true);
        sidebar_update_product_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sidebar_update_product_btn_lblMouseClicked(evt);
            }
        });

        sidebar_delete_product_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        sidebar_delete_product_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 20)); // NOI18N
        sidebar_delete_product_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        sidebar_delete_product_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sidebar_delete_product_btn_lbl.setText("Delete Product");
        sidebar_delete_product_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sidebar_delete_product_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sidebar_delete_product_btn_lbl.setOpaque(true);
        sidebar_delete_product_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sidebar_delete_product_btn_lblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout sidebar_menuLayout = new javax.swing.GroupLayout(sidebar_menu);
        sidebar_menu.setLayout(sidebar_menuLayout);
        sidebar_menuLayout.setHorizontalGroup(
            sidebar_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidebar_invoice_list_btn_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sidebar_add_invoice_btn_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sidebar_update_invoice_btn_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
            .addComponent(sidebar_delete_product_btn_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
            .addComponent(sidebar_update_product_btn_lbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sidebar_product_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sidebar_invoice_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sidebar_add_product_btn_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sidebar_delete_invoice_btn_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
            .addComponent(sidebar_product_list_btn_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sidebar_menuLayout.setVerticalGroup(
            sidebar_menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebar_menuLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(sidebar_invoice_lbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sidebar_invoice_list_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sidebar_add_invoice_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sidebar_update_invoice_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sidebar_delete_invoice_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sidebar_product_lbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sidebar_product_list_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sidebar_add_product_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sidebar_update_product_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sidebar_delete_product_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidebar_menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidebar_menu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        home_pnl.add(sidebar);
        sidebar.setBounds(0, 0, 260, 587);

        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setLayout(new java.awt.CardLayout());

        invoice_list_scrl_pane.setBackground(new java.awt.Color(255, 255, 255));
        invoice_list_scrl_pane.setBorder(null);
        invoice_list_scrl_pane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        invoice_list_scrl_pane.setMinimumSize(new java.awt.Dimension(21, 25));
        invoice_list_scrl_pane.setPreferredSize(new java.awt.Dimension(450, 400));

        invoice_list_tbl.setAutoCreateRowSorter(true);
        invoice_list_tbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        invoice_list_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Customer", "Date", "Remarks", "Invoice Amt. (£)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        invoice_list_tbl.setGridColor(new java.awt.Color(255, 255, 255));
        invoice_list_tbl.setRowHeight(20);
        invoice_list_tbl.setSelectionBackground(new java.awt.Color(0, 102, 204));
        invoice_list_tbl.setSelectionForeground(new java.awt.Color(255, 255, 255));
        invoice_list_tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                invoice_list_tblMouseClicked(evt);
            }
        });
        invoice_list_scrl_pane.setViewportView(invoice_list_tbl);

        content.add(invoice_list_scrl_pane, "card3");

        add_invoice_pnl.setBackground(new java.awt.Color(255, 255, 255));
        add_invoice_pnl.setLayout(null);

        add_invoice_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        add_invoice_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        add_invoice_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        add_invoice_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add_invoice_btn_lbl.setText("Submit");
        add_invoice_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add_invoice_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add_invoice_btn_lbl.setOpaque(true);
        add_invoice_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_invoice_btn_lblMouseClicked(evt);
            }
        });
        add_invoice_pnl.add(add_invoice_btn_lbl);
        add_invoice_btn_lbl.setBounds(27, 462, 167, 31);

        add_remarks_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_remarks_lbl.setText("Remarks");
        add_invoice_pnl.add(add_remarks_lbl);
        add_remarks_lbl.setBounds(550, 180, 49, 18);

        add_inv_amt_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_invoice_pnl.add(add_inv_amt_input);
        add_inv_amt_input.setBounds(550, 426, 210, 30);

        add_remarks_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        remarks_scrl_pane2.setViewportView(add_remarks_input);

        add_invoice_pnl.add(remarks_scrl_pane2);
        remarks_scrl_pane2.setBounds(550, 200, 210, 80);

        update_invoice_lbl1.setBackground(new java.awt.Color(255, 255, 255));
        update_invoice_lbl1.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        update_invoice_lbl1.setText("New Invoice");
        add_invoice_pnl.add(update_invoice_lbl1);
        update_invoice_lbl1.setBounds(27, 12, 121, 30);

        add_subtotal_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_subtotal_lbl.setText("Subtotal (£)");
        add_invoice_pnl.add(add_subtotal_lbl);
        add_subtotal_lbl.setBounds(550, 300, 70, 18);

        add_subtotal_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_subtotal_input.setEnabled(false);
        add_invoice_pnl.add(add_subtotal_input);
        add_subtotal_input.setBounds(618, 290, 140, 30);

        add_vat_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_vat_lbl.setText("VAT");
        add_invoice_pnl.add(add_vat_lbl);
        add_vat_lbl.setBounds(550, 340, 23, 18);

        add_product_name_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_product_name_lbl.setText("Product Name");
        add_invoice_pnl.add(add_product_name_lbl);
        add_product_name_lbl.setBounds(27, 125, 81, 18);

        add_cash_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_cash_lbl.setText("Invoice Amount (£)");
        add_invoice_pnl.add(add_cash_lbl);
        add_cash_lbl.setBounds(550, 410, 120, 18);

        add_invoice_number_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_invoice_number_input.setEnabled(false);
        add_invoice_pnl.add(add_invoice_number_input);
        add_invoice_number_input.setBounds(27, 68, 164, 30);

        add_vat_percent_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_vat_percent_input.setText("20");
        add_vat_percent_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_vat_percent_inputActionPerformed(evt);
            }
        });
        add_invoice_pnl.add(add_vat_percent_input);
        add_vat_percent_input.setBounds(620, 330, 40, 30);

        add_qty_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_qty_lbl.setText("Qty");
        add_invoice_pnl.add(add_qty_lbl);
        add_qty_lbl.setBounds(211, 125, 20, 18);

        add_invoice_number_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_invoice_number_lbl.setText("Invoice Number");
        add_invoice_pnl.add(add_invoice_number_lbl);
        add_invoice_number_lbl.setBounds(27, 52, 89, 18);

        add_vat_percent_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_vat_percent_lbl.setText("%");
        add_invoice_pnl.add(add_vat_percent_lbl);
        add_vat_percent_lbl.setBounds(660, 340, 10, 18);

        add_qty_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_qty_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                add_qty_inputKeyReleased(evt);
            }
        });
        add_invoice_pnl.add(add_qty_input);
        add_qty_input.setBounds(209, 140, 50, 30);

        add_customer_name_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_customer_name_input.setAlignmentX(2.0F);
        add_customer_name_input.setAlignmentY(2.0F);
        add_customer_name_input.setPreferredSize(null);
        add_invoice_pnl.add(add_customer_name_input);
        add_customer_name_input.setBounds(214, 68, 310, 30);

        add_vat_amount_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_vat_amount_input.setEnabled(false);
        add_vat_amount_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_vat_amount_inputActionPerformed(evt);
            }
        });
        add_invoice_pnl.add(add_vat_amount_input);
        add_vat_amount_input.setBounds(690, 330, 50, 30);

        add_unit_price_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_unit_price_lbl.setText("Unit Price (£)");
        add_invoice_pnl.add(add_unit_price_lbl);
        add_unit_price_lbl.setBounds(271, 125, 80, 18);

        add_invoice_product_combo_box.setBackground(new java.awt.Color(255, 255, 255));
        add_invoice_product_combo_box.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_invoice_product_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_invoice_product_combo_boxActionPerformed(evt);
            }
        });
        add_invoice_pnl.add(add_invoice_product_combo_box);
        add_invoice_product_combo_box.setBounds(27, 140, 164, 30);

        add_invoice_customer_name.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_invoice_customer_name.setText("Customer Name");
        add_invoice_pnl.add(add_invoice_customer_name);
        add_invoice_customer_name.setBounds(214, 52, 91, 18);

        add_service_tax_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_service_tax_lbl.setText("ST");
        add_invoice_pnl.add(add_service_tax_lbl);
        add_service_tax_lbl.setBounds(550, 380, 13, 18);

        add_unit_price_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_unit_price_input.setEnabled(false);
        add_invoice_pnl.add(add_unit_price_input);
        add_unit_price_input.setBounds(271, 140, 81, 30);

        add_st_percent_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_st_percent_input.setText("4");
        add_st_percent_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_st_percent_inputActionPerformed(evt);
            }
        });
        add_invoice_pnl.add(add_st_percent_input);
        add_st_percent_input.setBounds(620, 370, 40, 30);

        add_total_amount_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_total_amount_lbl.setText("Total Amt. (£)");
        add_invoice_pnl.add(add_total_amount_lbl);
        add_total_amount_lbl.setBounds(364, 125, 80, 18);

        add_invoice_date_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_invoice_pnl.add(add_invoice_date_input);
        add_invoice_date_input.setBounds(550, 140, 207, 30);

        add_st_percent_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_st_percent_lbl.setText("%");
        add_invoice_pnl.add(add_st_percent_lbl);
        add_st_percent_lbl.setBounds(660, 380, 10, 18);

        add_invoice_pdt_total_amount_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_invoice_pdt_total_amount_input.setEnabled(false);
        add_invoice_pnl.add(add_invoice_pdt_total_amount_input);
        add_invoice_pdt_total_amount_input.setBounds(364, 140, 80, 30);

        add_invoice_date_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_invoice_date_lbl.setText("Invoice Date");
        add_invoice_pnl.add(add_invoice_date_lbl);
        add_invoice_date_lbl.setBounds(550, 120, 71, 18);

        add_st_amount_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_st_amount_input.setEnabled(false);
        add_st_amount_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_st_amount_inputActionPerformed(evt);
            }
        });
        add_invoice_pnl.add(add_st_amount_input);
        add_st_amount_input.setBounds(690, 370, 50, 30);

        add_delete_from_cart_btn.setBackground(new java.awt.Color(255, 255, 255));
        add_delete_from_cart_btn.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        add_delete_from_cart_btn.setForeground(new java.awt.Color(0, 102, 204));
        add_delete_from_cart_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/invoicemanagement/delete.png"))); // NOI18N
        add_delete_from_cart_btn.setBorder(null);
        add_delete_from_cart_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add_delete_from_cart_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_delete_from_cart_btnActionPerformed(evt);
            }
        });
        add_invoice_pnl.add(add_delete_from_cart_btn);
        add_delete_from_cart_btn.setBounds(450, 140, 30, 30);

        add_product_details_tbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_product_details_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Unit Price (£)", "Qty", "Discount (£)", "Total Amount (£)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        add_product_details_tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_product_details_tblMouseClicked(evt);
            }
        });
        product_details_scrl_pane2.setViewportView(add_product_details_tbl);
        if (add_product_details_tbl.getColumnModel().getColumnCount() > 0) {
            add_product_details_tbl.getColumnModel().getColumn(0).setPreferredWidth(30);
            add_product_details_tbl.getColumnModel().getColumn(1).setPreferredWidth(100);
            add_product_details_tbl.getColumnModel().getColumn(2).setPreferredWidth(50);
            add_product_details_tbl.getColumnModel().getColumn(3).setPreferredWidth(30);
            add_product_details_tbl.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        add_invoice_pnl.add(product_details_scrl_pane2);
        product_details_scrl_pane2.setBounds(27, 177, 488, 279);

        add_add_to_cart_btn.setBackground(new java.awt.Color(255, 255, 255));
        add_add_to_cart_btn.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        add_add_to_cart_btn.setForeground(new java.awt.Color(0, 102, 204));
        add_add_to_cart_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/invoicemanagement/cart.png"))); // NOI18N
        add_add_to_cart_btn.setBorder(null);
        add_add_to_cart_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add_add_to_cart_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_add_to_cart_btnActionPerformed(evt);
            }
        });
        add_invoice_pnl.add(add_add_to_cart_btn);
        add_add_to_cart_btn.setBounds(485, 140, 30, 30);

        add_product_details_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        add_product_details_lbl.setText("Product Details");
        add_invoice_pnl.add(add_product_details_lbl);
        add_product_details_lbl.setBounds(27, 100, 111, 23);

        add_invoice_customer_phone.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_invoice_customer_phone.setText("Customer Contact Number");
        add_invoice_pnl.add(add_invoice_customer_phone);
        add_invoice_customer_phone.setBounds(550, 52, 160, 18);

        add_customer_contact_number_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_customer_contact_number_input.setAlignmentX(2.0F);
        add_customer_contact_number_input.setAlignmentY(2.0F);
        add_customer_contact_number_input.setPreferredSize(null);
        add_invoice_pnl.add(add_customer_contact_number_input);
        add_customer_contact_number_input.setBounds(550, 68, 210, 30);

        add_vat_percent_lbl1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_vat_percent_lbl1.setText("£");
        add_invoice_pnl.add(add_vat_percent_lbl1);
        add_vat_percent_lbl1.setBounds(742, 340, 7, 18);

        add_st_percent_lbl1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        add_st_percent_lbl1.setText("£");
        add_invoice_pnl.add(add_st_percent_lbl1);
        add_st_percent_lbl1.setBounds(742, 380, 7, 18);

        content.add(add_invoice_pnl, "card3");

        update_invoice_pnl.setBackground(new java.awt.Color(255, 255, 255));
        update_invoice_pnl.setLayout(null);

        update_invoice_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        update_invoice_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        update_invoice_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        update_invoice_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        update_invoice_btn_lbl.setText("Update");
        update_invoice_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        update_invoice_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        update_invoice_btn_lbl.setOpaque(true);
        update_invoice_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                update_invoice_btn_lblMouseClicked(evt);
            }
        });
        update_invoice_pnl.add(update_invoice_btn_lbl);
        update_invoice_btn_lbl.setBounds(27, 462, 167, 31);

        update_remarks_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_remarks_lbl.setText("Remarks");
        update_invoice_pnl.add(update_remarks_lbl);
        update_remarks_lbl.setBounds(550, 180, 49, 18);

        update_inv_amt_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_invoice_pnl.add(update_inv_amt_input);
        update_inv_amt_input.setBounds(550, 426, 210, 30);

        update_remarks_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        remarks_scrl_pane1.setViewportView(update_remarks_input);

        update_invoice_pnl.add(remarks_scrl_pane1);
        remarks_scrl_pane1.setBounds(550, 200, 210, 80);

        update_invoice_lbl.setBackground(new java.awt.Color(255, 255, 255));
        update_invoice_lbl.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        update_invoice_lbl.setText("Update Invoice");
        update_invoice_pnl.add(update_invoice_lbl);
        update_invoice_lbl.setBounds(27, 12, 149, 30);

        update_subtotal_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_subtotal_lbl.setText("Subtotal (£)");
        update_invoice_pnl.add(update_subtotal_lbl);
        update_subtotal_lbl.setBounds(550, 300, 80, 18);

        update_subtotal_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_subtotal_input.setEnabled(false);
        update_invoice_pnl.add(update_subtotal_input);
        update_subtotal_input.setBounds(618, 290, 140, 30);

        update_vat_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_vat_lbl.setText("VAT");
        update_invoice_pnl.add(update_vat_lbl);
        update_vat_lbl.setBounds(550, 340, 23, 18);

        update_product_name_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_product_name_lbl.setText("Product Name");
        update_invoice_pnl.add(update_product_name_lbl);
        update_product_name_lbl.setBounds(27, 125, 81, 18);

        update_cash_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_cash_lbl.setText("Invoice Amount (£)");
        update_invoice_pnl.add(update_cash_lbl);
        update_cash_lbl.setBounds(550, 410, 120, 18);

        update_invoice_number_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_invoice_number_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                update_invoice_number_inputKeyReleased(evt);
            }
        });
        update_invoice_pnl.add(update_invoice_number_input);
        update_invoice_number_input.setBounds(27, 68, 164, 30);

        update_vat_percent_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_vat_percent_input.setText("20");
        update_vat_percent_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_vat_percent_inputActionPerformed(evt);
            }
        });
        update_invoice_pnl.add(update_vat_percent_input);
        update_vat_percent_input.setBounds(618, 330, 50, 30);

        update_qty_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_qty_lbl.setText("Qty");
        update_invoice_pnl.add(update_qty_lbl);
        update_qty_lbl.setBounds(211, 125, 20, 18);

        update_invoice_number_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_invoice_number_lbl.setText("Invoice Number");
        update_invoice_pnl.add(update_invoice_number_lbl);
        update_invoice_number_lbl.setBounds(27, 52, 89, 18);

        update_vat_percent_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_vat_percent_lbl.setText("£");
        update_invoice_pnl.add(update_vat_percent_lbl);
        update_vat_percent_lbl.setBounds(742, 340, 7, 18);

        update_qty_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_qty_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                update_qty_inputKeyReleased(evt);
            }
        });
        update_invoice_pnl.add(update_qty_input);
        update_qty_input.setBounds(209, 140, 50, 30);

        update_customer_name_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_customer_name_input.setAlignmentX(2.0F);
        update_customer_name_input.setAlignmentY(2.0F);
        update_customer_name_input.setPreferredSize(null);
        update_invoice_pnl.add(update_customer_name_input);
        update_customer_name_input.setBounds(214, 68, 310, 30);

        update_vat_amount_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_vat_amount_input.setEnabled(false);
        update_vat_amount_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_vat_amount_inputActionPerformed(evt);
            }
        });
        update_invoice_pnl.add(update_vat_amount_input);
        update_vat_amount_input.setBounds(690, 330, 50, 30);

        update_unit_price_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_unit_price_lbl.setText("Unit Price (£)");
        update_invoice_pnl.add(update_unit_price_lbl);
        update_unit_price_lbl.setBounds(271, 125, 80, 18);

        update_invoice_product_combo_box.setBackground(new java.awt.Color(255, 255, 255));
        update_invoice_product_combo_box.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_invoice_product_combo_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_invoice_product_combo_boxActionPerformed(evt);
            }
        });
        update_invoice_pnl.add(update_invoice_product_combo_box);
        update_invoice_product_combo_box.setBounds(27, 140, 164, 30);

        update_invoice_customer_name.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_invoice_customer_name.setText("Customer Name");
        update_invoice_pnl.add(update_invoice_customer_name);
        update_invoice_customer_name.setBounds(214, 52, 91, 18);

        update_service_tax_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_service_tax_lbl.setText("ST");
        update_invoice_pnl.add(update_service_tax_lbl);
        update_service_tax_lbl.setBounds(550, 380, 13, 18);

        update_unit_price_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_unit_price_input.setEnabled(false);
        update_invoice_pnl.add(update_unit_price_input);
        update_unit_price_input.setBounds(271, 140, 81, 30);

        update_st_percent_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_st_percent_input.setText("4");
        update_st_percent_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_st_percent_inputActionPerformed(evt);
            }
        });
        update_invoice_pnl.add(update_st_percent_input);
        update_st_percent_input.setBounds(618, 370, 50, 30);

        update_total_amount_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_total_amount_lbl.setText("Total Amt. (£)");
        update_invoice_pnl.add(update_total_amount_lbl);
        update_total_amount_lbl.setBounds(364, 125, 100, 18);

        update_invoice_date_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_invoice_pnl.add(update_invoice_date_input);
        update_invoice_date_input.setBounds(550, 140, 207, 30);

        update_st_percent_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_st_percent_lbl.setText("%");
        update_invoice_pnl.add(update_st_percent_lbl);
        update_st_percent_lbl.setBounds(670, 380, 10, 18);

        update_invoice_pdt_total_amount_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_invoice_pdt_total_amount_input.setEnabled(false);
        update_invoice_pnl.add(update_invoice_pdt_total_amount_input);
        update_invoice_pdt_total_amount_input.setBounds(364, 140, 80, 30);

        update_invoice_date_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_invoice_date_lbl.setText("Invoice Date");
        update_invoice_pnl.add(update_invoice_date_lbl);
        update_invoice_date_lbl.setBounds(550, 120, 71, 18);

        update_st_amount_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_st_amount_input.setEnabled(false);
        update_st_amount_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_st_amount_inputActionPerformed(evt);
            }
        });
        update_invoice_pnl.add(update_st_amount_input);
        update_st_amount_input.setBounds(690, 370, 50, 30);

        update_delete_from_cart_btn.setBackground(new java.awt.Color(255, 255, 255));
        update_delete_from_cart_btn.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        update_delete_from_cart_btn.setForeground(new java.awt.Color(0, 102, 204));
        update_delete_from_cart_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/invoicemanagement/delete.png"))); // NOI18N
        update_delete_from_cart_btn.setBorder(null);
        update_delete_from_cart_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        update_delete_from_cart_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_delete_from_cart_btnActionPerformed(evt);
            }
        });
        update_invoice_pnl.add(update_delete_from_cart_btn);
        update_delete_from_cart_btn.setBounds(450, 140, 30, 30);

        update_product_details_tbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_product_details_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Unit Price (£)", "Qty", "Discount (£)", "Total Amt. (£)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        update_product_details_tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                update_product_details_tblMouseClicked(evt);
            }
        });
        product_details_scrl_pane1.setViewportView(update_product_details_tbl);
        if (update_product_details_tbl.getColumnModel().getColumnCount() > 0) {
            update_product_details_tbl.getColumnModel().getColumn(0).setPreferredWidth(30);
            update_product_details_tbl.getColumnModel().getColumn(1).setPreferredWidth(100);
            update_product_details_tbl.getColumnModel().getColumn(2).setPreferredWidth(50);
            update_product_details_tbl.getColumnModel().getColumn(3).setPreferredWidth(30);
            update_product_details_tbl.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        update_invoice_pnl.add(product_details_scrl_pane1);
        product_details_scrl_pane1.setBounds(27, 177, 488, 279);

        update_add_to_cart_btn.setBackground(new java.awt.Color(255, 255, 255));
        update_add_to_cart_btn.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        update_add_to_cart_btn.setForeground(new java.awt.Color(0, 102, 204));
        update_add_to_cart_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/invoicemanagement/cart.png"))); // NOI18N
        update_add_to_cart_btn.setBorder(null);
        update_add_to_cart_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        update_add_to_cart_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_add_to_cart_btnActionPerformed(evt);
            }
        });
        update_invoice_pnl.add(update_add_to_cart_btn);
        update_add_to_cart_btn.setBounds(485, 140, 30, 30);

        update_product_details_lbl1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        update_product_details_lbl1.setText("Product Details");
        update_invoice_pnl.add(update_product_details_lbl1);
        update_product_details_lbl1.setBounds(27, 100, 111, 23);

        update_invoice_customer_phone.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_invoice_customer_phone.setText("Customer Contact Number");
        update_invoice_pnl.add(update_invoice_customer_phone);
        update_invoice_customer_phone.setBounds(550, 52, 160, 18);

        update_customer_contact_number_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_customer_contact_number_input.setAlignmentX(2.0F);
        update_customer_contact_number_input.setAlignmentY(2.0F);
        update_customer_contact_number_input.setPreferredSize(null);
        update_invoice_pnl.add(update_customer_contact_number_input);
        update_customer_contact_number_input.setBounds(550, 68, 210, 30);

        update_vat_percent_lbl1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_vat_percent_lbl1.setText("%");
        update_invoice_pnl.add(update_vat_percent_lbl1);
        update_vat_percent_lbl1.setBounds(670, 340, 10, 18);

        update_vat_percent_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_vat_percent_lbl2.setText("£");
        update_invoice_pnl.add(update_vat_percent_lbl2);
        update_vat_percent_lbl2.setBounds(742, 380, 7, 18);

        content.add(update_invoice_pnl, "card3");

        delete_invoice_pnl.setBackground(new java.awt.Color(255, 255, 255));

        delete_invoice_id_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        delete_invoice_id_lbl.setText("Invoice ID");

        delete_invoice_lbl.setBackground(new java.awt.Color(255, 255, 255));
        delete_invoice_lbl.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        delete_invoice_lbl.setText("Delete Invoice");

        delete_invoice_id_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_invoice_id_inputActionPerformed(evt);
            }
        });

        delete_invoice_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        delete_invoice_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        delete_invoice_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        delete_invoice_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        delete_invoice_btn_lbl.setText("Delete");
        delete_invoice_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        delete_invoice_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        delete_invoice_btn_lbl.setOpaque(true);
        delete_invoice_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delete_invoice_btn_lblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout delete_invoice_pnlLayout = new javax.swing.GroupLayout(delete_invoice_pnl);
        delete_invoice_pnl.setLayout(delete_invoice_pnlLayout);
        delete_invoice_pnlLayout.setHorizontalGroup(
            delete_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, delete_invoice_pnlLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(delete_invoice_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(278, 278, 278))
            .addGroup(delete_invoice_pnlLayout.createSequentialGroup()
                .addGroup(delete_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(delete_invoice_pnlLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(delete_invoice_id_lbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete_invoice_id_input, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(delete_invoice_pnlLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(delete_invoice_lbl)))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        delete_invoice_pnlLayout.setVerticalGroup(
            delete_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(delete_invoice_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(delete_invoice_lbl)
                .addGap(176, 176, 176)
                .addGroup(delete_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete_invoice_id_input, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete_invoice_id_lbl))
                .addGap(34, 34, 34)
                .addComponent(delete_invoice_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(193, Short.MAX_VALUE))
        );

        content.add(delete_invoice_pnl, "card5");

        product_list_scrl_pane.setBorder(null);

        product_list_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Description", "Unit Price (£)", "Discount (£)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        product_list_scrl_pane.setViewportView(product_list_tbl);

        content.add(product_list_scrl_pane, "card7");

        add_product_pnl.setBackground(new java.awt.Color(255, 255, 255));

        new_pdt_lbl.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        new_pdt_lbl.setText("New Product");

        pdt_name_input.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        pdt_name_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_name_lbl.setText("Name");

        pdt_desc_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_desc_lbl.setText("Description");
        pdt_desc_lbl.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pdt_desc_lbl.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        pdt_desc_input.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_desc_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdt_desc_inputActionPerformed(evt);
            }
        });

        pdt_unit_price_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_unit_price_lbl.setText("Unit Price (£)");

        pdt_unit_price_input.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        pdt_discount_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_discount_lbl.setText("Discount (£)");

        pdt_discount_input.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        save_product_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        save_product_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        save_product_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        save_product_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        save_product_btn_lbl.setText("Save");
        save_product_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        save_product_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        save_product_btn_lbl.setOpaque(true);
        save_product_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                save_product_btn_lblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout add_product_pnlLayout = new javax.swing.GroupLayout(add_product_pnl);
        add_product_pnl.setLayout(add_product_pnlLayout);
        add_product_pnlLayout.setHorizontalGroup(
            add_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(add_product_pnlLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(add_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(new_pdt_lbl)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, add_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(save_product_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(add_product_pnlLayout.createSequentialGroup()
                            .addGroup(add_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(pdt_desc_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pdt_unit_price_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(add_product_pnlLayout.createSequentialGroup()
                                    .addGroup(add_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pdt_name_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(pdt_discount_lbl))
                                    .addGap(22, 22, 22)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(add_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(pdt_name_input)
                                .addComponent(pdt_desc_input)
                                .addComponent(pdt_unit_price_input, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(pdt_discount_input, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(50, 50, 50))
        );
        add_product_pnlLayout.setVerticalGroup(
            add_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(add_product_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(new_pdt_lbl)
                .addGap(18, 18, 18)
                .addGroup(add_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pdt_name_input, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pdt_name_lbl))
                .addGap(18, 18, 18)
                .addGroup(add_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pdt_desc_lbl)
                    .addComponent(pdt_desc_input, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(add_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pdt_unit_price_input, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pdt_unit_price_lbl))
                .addGap(20, 20, 20)
                .addGroup(add_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pdt_discount_input, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pdt_discount_lbl))
                .addGap(18, 18, 18)
                .addComponent(save_product_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        content.add(add_product_pnl, "card6");

        update_product_pnl.setBackground(new java.awt.Color(255, 255, 255));

        update_pdt_desc_input.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        update_pdt_desc_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_pdt_desc_inputActionPerformed(evt);
            }
        });

        pdt_unit_price_lbl2.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_unit_price_lbl2.setText("Unit Price (£)");

        update_pdt_unit_price_input.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        pdt_discount_lbl2.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_discount_lbl2.setText("Discount (£)");

        jLabel7.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel7.setText("Update Product");

        update_pdt_discount_input.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        pdt_id_input.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_id_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pdt_id_inputKeyReleased(evt);
            }
        });

        update_product_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        update_product_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        update_product_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        update_product_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        update_product_btn_lbl.setText("Update");
        update_product_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        update_product_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        update_product_btn_lbl.setOpaque(true);
        update_product_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                update_product_btn_lblMouseClicked(evt);
            }
        });

        pdt_name_lbl3.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_name_lbl3.setText("Name");

        pdt_desc_lbl2.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_desc_lbl2.setText("Description");
        pdt_desc_lbl2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        pdt_desc_lbl2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        pdt_id_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_id_lbl.setText("ID");

        update_pdt_name_input.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        javax.swing.GroupLayout update_product_pnlLayout = new javax.swing.GroupLayout(update_product_pnl);
        update_product_pnl.setLayout(update_product_pnlLayout);
        update_product_pnlLayout.setHorizontalGroup(
            update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(update_product_pnlLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(update_product_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(update_product_pnlLayout.createSequentialGroup()
                        .addGroup(update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pdt_id_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pdt_name_lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pdt_discount_lbl2)
                            .addGroup(update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(pdt_desc_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pdt_unit_price_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(16, 16, 16)
                        .addGroup(update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(update_pdt_name_input)
                            .addComponent(pdt_id_input)
                            .addComponent(update_pdt_unit_price_input, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(update_pdt_discount_input, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
                            .addComponent(update_pdt_desc_input))))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        update_product_pnlLayout.setVerticalGroup(
            update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(update_product_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pdt_id_input, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pdt_id_lbl))
                .addGap(18, 18, 18)
                .addGroup(update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(update_pdt_name_input, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pdt_name_lbl3))
                .addGap(18, 18, 18)
                .addGroup(update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pdt_desc_lbl2)
                    .addComponent(update_pdt_desc_input, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(update_pdt_unit_price_input, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pdt_unit_price_lbl2))
                .addGap(20, 20, 20)
                .addGroup(update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(update_pdt_discount_input, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pdt_discount_lbl2))
                .addGap(18, 18, 18)
                .addComponent(update_product_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        content.add(update_product_pnl, "card6");

        delete_product_pnl.setBackground(new java.awt.Color(255, 255, 255));

        delete_product_lbl.setBackground(new java.awt.Color(255, 255, 255));
        delete_product_lbl.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        delete_product_lbl.setText("Delete Product");

        delete_product_id_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_product_id_inputActionPerformed(evt);
            }
        });

        delete_product_id_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        delete_product_id_lbl.setText("Product ID");

        delete_product_btn_lbl.setBackground(new java.awt.Color(0, 102, 204));
        delete_product_btn_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        delete_product_btn_lbl.setForeground(new java.awt.Color(255, 255, 255));
        delete_product_btn_lbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        delete_product_btn_lbl.setText("Delete");
        delete_product_btn_lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        delete_product_btn_lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        delete_product_btn_lbl.setOpaque(true);
        delete_product_btn_lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delete_product_btn_lblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout delete_product_pnlLayout = new javax.swing.GroupLayout(delete_product_pnl);
        delete_product_pnl.setLayout(delete_product_pnlLayout);
        delete_product_pnlLayout.setHorizontalGroup(
            delete_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, delete_product_pnlLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(delete_product_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(278, 278, 278))
            .addGroup(delete_product_pnlLayout.createSequentialGroup()
                .addGroup(delete_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(delete_product_pnlLayout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(delete_product_id_lbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete_product_id_input, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(delete_product_pnlLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(delete_product_lbl)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        delete_product_pnlLayout.setVerticalGroup(
            delete_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(delete_product_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(delete_product_lbl)
                .addGap(176, 176, 176)
                .addGroup(delete_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete_product_id_input, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete_product_id_lbl))
                .addGap(34, 34, 34)
                .addComponent(delete_product_btn_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(193, Short.MAX_VALUE))
        );

        content.add(delete_product_pnl, "card6");

        home_pnl.add(content);
        content.setBounds(260, 60, 785, 510);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(home_pnl, javax.swing.GroupLayout.DEFAULT_SIZE, 1041, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(home_pnl, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1041, 566));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exit_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exit_lblMouseClicked
        System.exit(0);
    }//GEN-LAST:event_exit_lblMouseClicked

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        this.dispose();
        new LoginUI().setVisible(true);
    }//GEN-LAST:event_logoutMouseClicked

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_headerMouseDragged

    private void headerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMousePressed
        setOpacity((float)0.8);
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_headerMousePressed

    private void headerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMouseReleased
        setOpacity((float)1.0);
    }//GEN-LAST:event_headerMouseReleased

    private void sidebar_invoice_list_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidebar_invoice_list_btn_lblMouseClicked
        setLblColor(sidebar_invoice_list_btn_lbl);
        resetLblColor(sidebar_add_invoice_btn_lbl);
        resetLblColor(sidebar_update_invoice_btn_lbl);
        resetLblColor(sidebar_delete_invoice_btn_lbl);
        resetLblColor(sidebar_product_list_btn_lbl);
        resetLblColor(sidebar_add_product_btn_lbl);
        resetLblColor(sidebar_update_product_btn_lbl);
        resetLblColor(sidebar_delete_product_btn_lbl);
        
        add_invoice_pnl.setVisible(false);
        update_invoice_pnl.setVisible(false);
        invoice_list_scrl_pane.setVisible(true);
        delete_invoice_pnl.setVisible(false);
        
        add_product_pnl.setVisible(false);
        update_product_pnl.setVisible(false);
        product_list_scrl_pane.setVisible(false);
        delete_product_pnl.setVisible(false);
        this.show_invoice();
    }//GEN-LAST:event_sidebar_invoice_list_btn_lblMouseClicked

    private void sidebar_add_invoice_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidebar_add_invoice_btn_lblMouseClicked
        resetLblColor(sidebar_invoice_list_btn_lbl);
        setLblColor(sidebar_add_invoice_btn_lbl);
        resetLblColor(sidebar_update_invoice_btn_lbl);
        resetLblColor(sidebar_delete_invoice_btn_lbl);
        resetLblColor(sidebar_product_list_btn_lbl);
        resetLblColor(sidebar_add_product_btn_lbl);
        resetLblColor(sidebar_update_product_btn_lbl);
        resetLblColor(sidebar_delete_product_btn_lbl);
        
        add_invoice_pnl.setVisible(true);
        update_invoice_pnl.setVisible(false);
        invoice_list_scrl_pane.setVisible(false);
        delete_invoice_pnl.setVisible(false);
        
        add_product_pnl.setVisible(false);
        update_product_pnl.setVisible(false);
        product_list_scrl_pane.setVisible(false);
        delete_product_pnl.setVisible(false);
        
        this.get_products();
        add_invoice_date_input.setDate(new Date());
    }//GEN-LAST:event_sidebar_add_invoice_btn_lblMouseClicked

    private void sidebar_update_invoice_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidebar_update_invoice_btn_lblMouseClicked
        resetLblColor(sidebar_invoice_list_btn_lbl);
        resetLblColor(sidebar_add_invoice_btn_lbl);
        setLblColor(sidebar_update_invoice_btn_lbl);
        resetLblColor(sidebar_delete_invoice_btn_lbl);
        resetLblColor(sidebar_product_list_btn_lbl);
        resetLblColor(sidebar_add_product_btn_lbl);
        resetLblColor(sidebar_update_product_btn_lbl);
        resetLblColor(sidebar_delete_product_btn_lbl);
        
        add_invoice_pnl.setVisible(false);
        update_invoice_pnl.setVisible(true);
        invoice_list_scrl_pane.setVisible(false);
        delete_invoice_pnl.setVisible(false);
        
        add_product_pnl.setVisible(false);
        update_product_pnl.setVisible(false);
        product_list_scrl_pane.setVisible(false);
        delete_product_pnl.setVisible(false);
        
        this.get_products();
    }//GEN-LAST:event_sidebar_update_invoice_btn_lblMouseClicked

    private void sidebar_delete_product_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidebar_delete_product_btn_lblMouseClicked
        resetLblColor(sidebar_invoice_list_btn_lbl);
        resetLblColor(sidebar_add_invoice_btn_lbl);
        resetLblColor(sidebar_update_invoice_btn_lbl);
        resetLblColor(sidebar_delete_invoice_btn_lbl);
        resetLblColor(sidebar_product_list_btn_lbl);
        resetLblColor(sidebar_add_product_btn_lbl);
        resetLblColor(sidebar_update_product_btn_lbl);
        setLblColor(sidebar_delete_product_btn_lbl);
        
        add_invoice_pnl.setVisible(false);
        update_invoice_pnl.setVisible(false);
        invoice_list_scrl_pane.setVisible(false);
        delete_invoice_pnl.setVisible(false);
        
        add_product_pnl.setVisible(false);
        update_product_pnl.setVisible(false);
        product_list_scrl_pane.setVisible(false);
        delete_product_pnl.setVisible(true);
    }//GEN-LAST:event_sidebar_delete_product_btn_lblMouseClicked

    private void sidebar_update_product_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidebar_update_product_btn_lblMouseClicked
        resetLblColor(sidebar_invoice_list_btn_lbl);
        resetLblColor(sidebar_add_invoice_btn_lbl);
        resetLblColor(sidebar_update_invoice_btn_lbl);
        resetLblColor(sidebar_delete_invoice_btn_lbl);
        resetLblColor(sidebar_product_list_btn_lbl);
        resetLblColor(sidebar_add_product_btn_lbl);
        setLblColor(sidebar_update_product_btn_lbl);
        resetLblColor(sidebar_delete_product_btn_lbl);
        
        add_invoice_pnl.setVisible(false);
        update_invoice_pnl.setVisible(false);
        invoice_list_scrl_pane.setVisible(false);
        delete_invoice_pnl.setVisible(false);
        
        add_product_pnl.setVisible(false);
        update_product_pnl.setVisible(true);
        product_list_scrl_pane.setVisible(false);
        delete_product_pnl.setVisible(false);
    }//GEN-LAST:event_sidebar_update_product_btn_lblMouseClicked

    private void sidebar_add_product_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidebar_add_product_btn_lblMouseClicked
        resetLblColor(sidebar_invoice_list_btn_lbl);
        resetLblColor(sidebar_add_invoice_btn_lbl);
        resetLblColor(sidebar_update_invoice_btn_lbl);
        resetLblColor(sidebar_delete_invoice_btn_lbl);
        resetLblColor(sidebar_product_list_btn_lbl);
        setLblColor(sidebar_add_product_btn_lbl);
        resetLblColor(sidebar_update_product_btn_lbl);
        resetLblColor(sidebar_delete_product_btn_lbl);
        
        add_invoice_pnl.setVisible(false);
        update_invoice_pnl.setVisible(false);
        invoice_list_scrl_pane.setVisible(false);
        delete_invoice_pnl.setVisible(false);
        
        add_product_pnl.setVisible(true);
        update_product_pnl.setVisible(false);
        product_list_scrl_pane.setVisible(false);
        delete_product_pnl.setVisible(false);
    }//GEN-LAST:event_sidebar_add_product_btn_lblMouseClicked

    private void sidebar_delete_invoice_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidebar_delete_invoice_btn_lblMouseClicked
        resetLblColor(sidebar_invoice_list_btn_lbl);
        resetLblColor(sidebar_add_invoice_btn_lbl);
        resetLblColor(sidebar_update_invoice_btn_lbl);
        setLblColor(sidebar_delete_invoice_btn_lbl);
        resetLblColor(sidebar_product_list_btn_lbl);
        resetLblColor(sidebar_add_product_btn_lbl);
        resetLblColor(sidebar_update_product_btn_lbl);
        resetLblColor(sidebar_delete_product_btn_lbl);
        
        add_invoice_pnl.setVisible(false);
        update_invoice_pnl.setVisible(false);
        invoice_list_scrl_pane.setVisible(false);
        delete_invoice_pnl.setVisible(true);
        
        add_product_pnl.setVisible(false);
        update_product_pnl.setVisible(false);
        product_list_scrl_pane.setVisible(false);
        delete_product_pnl.setVisible(false);
    }//GEN-LAST:event_sidebar_delete_invoice_btn_lblMouseClicked

    private void sidebar_product_list_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sidebar_product_list_btn_lblMouseClicked
        resetLblColor(sidebar_invoice_list_btn_lbl);
        resetLblColor(sidebar_add_invoice_btn_lbl);
        resetLblColor(sidebar_update_invoice_btn_lbl);
        resetLblColor(sidebar_delete_invoice_btn_lbl);
        setLblColor(sidebar_product_list_btn_lbl);
        resetLblColor(sidebar_add_product_btn_lbl);
        resetLblColor(sidebar_update_product_btn_lbl);
        resetLblColor(sidebar_delete_product_btn_lbl);
        
        add_invoice_pnl.setVisible(false);
        update_invoice_pnl.setVisible(false);
        invoice_list_scrl_pane.setVisible(false);
        delete_invoice_pnl.setVisible(false);
        
        add_product_pnl.setVisible(false);
        update_product_pnl.setVisible(false);
        product_list_scrl_pane.setVisible(true);
        delete_product_pnl.setVisible(false);
        
        this.get_products();
    }//GEN-LAST:event_sidebar_product_list_btn_lblMouseClicked

    private void pdt_desc_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdt_desc_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pdt_desc_inputActionPerformed

    private void update_vat_percent_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_vat_percent_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_vat_percent_inputActionPerformed

    private void update_vat_amount_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_vat_amount_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_vat_amount_inputActionPerformed

    private void update_st_percent_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_st_percent_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_st_percent_inputActionPerformed

    private void update_st_amount_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_st_amount_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_st_amount_inputActionPerformed

    private void update_delete_from_cart_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_delete_from_cart_btnActionPerformed
        DefaultTableModel ptm = (DefaultTableModel)update_product_details_tbl.getModel();
        int[] rows = update_product_details_tbl.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            ptm.removeRow(rows[i] - i);
        }
        this.update_inv_amounts();
        update_qty_input.setText("");
        update_unit_price_input.setText("");
        update_invoice_pdt_total_amount_input.setText("");
    }//GEN-LAST:event_update_delete_from_cart_btnActionPerformed

    private void update_pdt_desc_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_pdt_desc_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_pdt_desc_inputActionPerformed

    private void delete_invoice_id_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_invoice_id_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_delete_invoice_id_inputActionPerformed

    private void delete_product_id_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_product_id_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_delete_product_id_inputActionPerformed

    private void delete_invoice_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_invoice_btn_lblMouseClicked
        int inv_id = Integer.parseInt(delete_invoice_id_input.getText());
        this.delete_invoice(inv_id);
        this.sidebar_invoice_list_btn_lblMouseClicked(evt);
    }//GEN-LAST:event_delete_invoice_btn_lblMouseClicked

    private void delete_product_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_product_btn_lblMouseClicked
        int pdt_id = Integer.parseInt(delete_product_id_input.getText());
        this.delete_product(pdt_id);
        this.sidebar_product_list_btn_lblMouseClicked(evt);
    }//GEN-LAST:event_delete_product_btn_lblMouseClicked

    private void save_product_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_save_product_btn_lblMouseClicked
        String name = pdt_name_input.getText();
        String description = pdt_desc_input.getText();
        Float unit_price = Float.parseFloat(pdt_unit_price_input.getText());
        Float discount = Float.parseFloat(pdt_discount_input.getText());
        
        PreparedStatement ps;
        int rs;
        String queryInvoices = "INSERT INTO `inv_products` (name, description, unit_price, discount) VALUES (?, ?, ?, ?)";

        try {

            ps = DbConnection.getConnection().prepareStatement(queryInvoices);

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setFloat(3, unit_price);
            ps.setFloat(4, discount);
            
            rs = ps.executeUpdate();
            if(rs > 0) {
                JOptionPane.showMessageDialog(this, "Product added!");
                this.sidebar_product_list_btn_lblMouseClicked(evt);
            } else {
                JOptionPane.showMessageDialog(this, "Product could not be added!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_save_product_btn_lblMouseClicked

    private void pdt_id_inputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pdt_id_inputKeyReleased
        if(pdt_id_input.getText() == null || pdt_id_input.getText().equals("")) {
        } else {
            int id = Integer.parseInt(pdt_id_input.getText());
            PreparedStatement ps;
            ResultSet rs;
            String queryInvoices = "SELECT * FROM `inv_products` WHERE `id` = ?";

            try {

                ps = DbConnection.getConnection().prepareStatement(queryInvoices);

                ps.setInt(1, id);

                rs = ps.executeQuery();

                while(rs.next()) {
                    Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getFloat("discount"), rs.getFloat("unit_price"));
                    update_pdt_name_input.setText(product.getname());
                    update_pdt_desc_input.setText(product.getdescription());
                    update_pdt_unit_price_input.setText(Float.toString(product.getunitprice()));
                    update_pdt_discount_input.setText(Float.toString(product.getdiscount()));
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }//GEN-LAST:event_pdt_id_inputKeyReleased

    private void update_product_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_update_product_btn_lblMouseClicked
        int id = Integer.parseInt(pdt_id_input.getText());
        String name = update_pdt_name_input.getText();
        String description = update_pdt_desc_input.getText();
        Float unit_price = Float.parseFloat(update_pdt_unit_price_input.getText());
        Float discount = Float.parseFloat(update_pdt_discount_input.getText());
        
        PreparedStatement ps;
        int rs;
        String queryInvoices = "UPDATE `inv_products` SET `name` = ?, `description` = ?, `unit_price` = ?, `discount` = ? WHERE `id` = ?";

        try {

            ps = DbConnection.getConnection().prepareStatement(queryInvoices);

            ps.setString(1, name);
            ps.setString(2, description);
            ps.setFloat(3, unit_price);
            ps.setFloat(4, discount);
            ps.setFloat(5, id);
            
            rs = ps.executeUpdate();
            if(rs > 0) {
                JOptionPane.showMessageDialog(this, "Product updated!");
                this.sidebar_product_list_btn_lblMouseClicked(evt);
            } else {
                JOptionPane.showMessageDialog(this, "Product could not be updated!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_update_product_btn_lblMouseClicked

    private void update_invoice_product_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_invoice_product_combo_boxActionPerformed
    }//GEN-LAST:event_update_invoice_product_combo_boxActionPerformed

    private void update_invoice_number_inputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_invoice_number_inputKeyReleased
        if(update_invoice_number_input.getText() == null || update_invoice_number_input.getText().equals("")) {
        } else {
            int id = Integer.parseInt(update_invoice_number_input.getText());
            PreparedStatement ps;
            ResultSet rs;
            String queryInvoices = "SELECT * FROM `inv_invoices` WHERE `id` = ?";

            try {

                ps = DbConnection.getConnection().prepareStatement(queryInvoices);

                ps.setInt(1, id);

                rs = ps.executeQuery();

                while(rs.next()) {
                    Invoice invoice = new Invoice(rs.getInt("id"), rs.getString("customer"), rs.getString("customer_contact_number"), rs.getString("remarks"), rs.getDate("inv_date"), rs.getFloat("subtotal"), rs.getFloat("vat"), rs.getFloat("service_tax"), rs.getFloat("inv_amt"));
                    update_customer_name_input.setText(invoice.getcustomer());
                    update_customer_contact_number_input.setText(invoice.getcustomercontactnumber());
                    update_invoice_date_input.setDate(invoice.getinvdate());
                    update_remarks_input.setText(invoice.getremarks());
                    update_subtotal_input.setText(Float.toString(invoice.getsubtotal()));
                    update_vat_amount_input.setText(Float.toString(invoice.getvat()));
                    update_st_amount_input.setText(Float.toString(invoice.getservicetax()));
                    update_inv_amt_input.setText(Float.toString(invoice.getinv_amt()));
                    this.get_inv_products(id);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }//GEN-LAST:event_update_invoice_number_inputKeyReleased

    private void invoice_list_tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_invoice_list_tblMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_invoice_list_tblMouseClicked

    private void update_product_details_tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_update_product_details_tblMouseClicked
        int row = update_product_details_tbl.getSelectedRow();
        TableModel updt = update_product_details_tbl.getModel();
        Product product = this.get_product(Integer.parseInt(updt.getValueAt(row, 0).toString()));
        update_qty_input.setText(updt.getValueAt(row, 3).toString());
        update_unit_price_input.setText(Float.toString(product.getunitprice()));
        int qty = Integer.parseInt(updt.getValueAt(row, 3).toString());
        float total_amt = (product.getunitprice() - product.getdiscount()) * qty;
        update_invoice_pdt_total_amount_input.setText(Float.toString(total_amt));
        update_invoice_product_combo_box.setSelectedItem(product.getname());
    }//GEN-LAST:event_update_product_details_tblMouseClicked

    private void update_add_to_cart_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_add_to_cart_btnActionPerformed
        Product pdt = this.getProductByName(update_invoice_product_combo_box.getSelectedItem().toString());
        int qty = Integer.parseInt(update_qty_input.getText());
        float uprice = Float.parseFloat(update_unit_price_input.getText());
        float tamt = Float.parseFloat(update_invoice_pdt_total_amount_input.getText());
        DefaultTableModel ptm = (DefaultTableModel)update_product_details_tbl.getModel();
        Object[] row = new Object[6];
        row[0] = pdt.getid();
        row[1] = pdt.getname();
        row[2] = uprice;
        row[3] = qty;
        row[4] = pdt.getdiscount();
        row[5] = tamt;
        ptm.addRow(row);
        this.update_inv_amounts();
    }//GEN-LAST:event_update_add_to_cart_btnActionPerformed

    private void update_qty_inputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_qty_inputKeyReleased
        Product pdt = this.getProductByName(update_invoice_product_combo_box.getSelectedItem().toString());
        if(!update_qty_input.getText().equals("")) {
            int qty = Integer.parseInt(update_qty_input.getText());
            update_unit_price_input.setText(Float.toString(pdt.getunitprice()));
            float total_amt = (pdt.getunitprice() - pdt.getdiscount()) * qty;
            update_invoice_pdt_total_amount_input.setText(Float.toString(total_amt));
        }
    }//GEN-LAST:event_update_qty_inputKeyReleased

    private void update_invoice_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_update_invoice_btn_lblMouseClicked
        int inv_id = Integer.parseInt(update_invoice_number_input.getText());
        String cust_name = update_customer_name_input.getText();
        String cust_contact = update_customer_contact_number_input.getText();
        String remarks = update_remarks_input.getText();
        Date inv_date = update_invoice_date_input.getDate();
        float subtotal = Float.parseFloat(update_subtotal_input.getText());
        float vat = Float.parseFloat(update_vat_amount_input.getText());
        float st = Float.parseFloat(update_st_amount_input.getText());
        float inv_amt = Float.parseFloat(update_inv_amt_input.getText());
        
        this.updateInvoice(inv_id, cust_name, cust_contact, remarks, inv_date, subtotal, vat, st, inv_amt);
        int rows = update_product_details_tbl.getRowCount();
        int pdt_id, qty;
        this.clearInvProduct(inv_id);
        for (int i = 0; i < rows; i++) {
            pdt_id = Integer.parseInt(update_product_details_tbl.getValueAt(i, 0).toString());
            qty = Integer.parseInt(update_product_details_tbl.getValueAt(i, 3).toString());
            this.updateInvProduct(inv_id, pdt_id, qty);
        }
    }//GEN-LAST:event_update_invoice_btn_lblMouseClicked

    private void add_invoice_btn_lblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_invoice_btn_lblMouseClicked
        String cust_name = add_customer_name_input.getText();
        String cust_contact = add_customer_contact_number_input.getText();
        String remarks = add_remarks_input.getText();
        Date inv_date = add_invoice_date_input.getDate();
        float subtotal = Float.parseFloat(add_subtotal_input.getText());
        float vat = Float.parseFloat(add_vat_amount_input.getText());
        float st = Float.parseFloat(add_st_amount_input.getText());
        float inv_amt = Float.parseFloat(add_inv_amt_input.getText());
        
        int inv_id = this.addInvoice(cust_name, cust_contact, remarks, inv_date, subtotal, vat, st, inv_amt);
        int rows = add_product_details_tbl.getRowCount();
        int pdt_id, qty;
        for (int i = 0; i < rows; i++) {
            pdt_id = Integer.parseInt(add_product_details_tbl.getValueAt(i, 0).toString());
            qty = Integer.parseInt(add_product_details_tbl.getValueAt(i, 3).toString());
            this.updateInvProduct(inv_id, pdt_id, qty);
        }
    }//GEN-LAST:event_add_invoice_btn_lblMouseClicked

    private void add_vat_percent_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_vat_percent_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_vat_percent_inputActionPerformed

    private void add_qty_inputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_qty_inputKeyReleased
        Product pdt = this.getProductByName(add_invoice_product_combo_box.getSelectedItem().toString());
        if(!add_qty_input.getText().equals("")) {
            int qty = Integer.parseInt(add_qty_input.getText());
            add_unit_price_input.setText(Float.toString(pdt.getunitprice()));
            float total_amt = (pdt.getunitprice() - pdt.getdiscount()) * qty;
            add_invoice_pdt_total_amount_input.setText(Float.toString(total_amt));
        }
    }//GEN-LAST:event_add_qty_inputKeyReleased

    private void add_vat_amount_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_vat_amount_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_vat_amount_inputActionPerformed

    private void add_invoice_product_combo_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_invoice_product_combo_boxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_invoice_product_combo_boxActionPerformed

    private void add_st_percent_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_st_percent_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_st_percent_inputActionPerformed

    private void add_st_amount_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_st_amount_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_st_amount_inputActionPerformed

    private void add_delete_from_cart_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_delete_from_cart_btnActionPerformed
        DefaultTableModel ptm = (DefaultTableModel)add_product_details_tbl.getModel();
        int[] rows = add_product_details_tbl.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            ptm.removeRow(rows[i] - i);
        }
        this.add_inv_amounts();
        add_qty_input.setText("");
        add_unit_price_input.setText("");
        add_invoice_pdt_total_amount_input.setText("");
    }//GEN-LAST:event_add_delete_from_cart_btnActionPerformed

    private void add_product_details_tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_product_details_tblMouseClicked
        int row = add_product_details_tbl.getSelectedRow();
        TableModel updt = add_product_details_tbl.getModel();
        Product product = this.get_product(Integer.parseInt(updt.getValueAt(row, 0).toString()));
        add_qty_input.setText(updt.getValueAt(row, 3).toString());
        add_unit_price_input.setText(Float.toString(product.getunitprice()));
        int qty = Integer.parseInt(updt.getValueAt(row, 3).toString());
        float total_amt = (product.getunitprice() - product.getdiscount()) * qty;
        add_invoice_pdt_total_amount_input.setText(Float.toString(total_amt));
        add_invoice_product_combo_box.setSelectedItem(product.getname());
    }//GEN-LAST:event_add_product_details_tblMouseClicked

    private void add_add_to_cart_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_add_to_cart_btnActionPerformed
        Product pdt = this.getProductByName(add_invoice_product_combo_box.getSelectedItem().toString());
        int qty = Integer.parseInt(add_qty_input.getText());
        float uprice = Float.parseFloat(add_unit_price_input.getText());
        float tamt = Float.parseFloat(add_invoice_pdt_total_amount_input.getText());
        DefaultTableModel ptm = (DefaultTableModel)add_product_details_tbl.getModel();
        Object[] row = new Object[6];
        row[0] = pdt.getid();
        row[1] = pdt.getname();
        row[2] = uprice;
        row[3] = qty;
        row[4] = pdt.getdiscount();
        row[5] = tamt;
        ptm.addRow(row);
        this.add_inv_amounts();
    }//GEN-LAST:event_add_add_to_cart_btnActionPerformed
 
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_add_to_cart_btn;
    private javax.swing.JLabel add_cash_lbl;
    private javax.swing.JTextField add_customer_contact_number_input;
    private javax.swing.JTextField add_customer_name_input;
    private javax.swing.JButton add_delete_from_cart_btn;
    private javax.swing.JTextField add_inv_amt_input;
    private javax.swing.JLabel add_invoice_btn_lbl;
    private javax.swing.JLabel add_invoice_customer_name;
    private javax.swing.JLabel add_invoice_customer_phone;
    private com.toedter.calendar.JDateChooser add_invoice_date_input;
    private javax.swing.JLabel add_invoice_date_lbl;
    private javax.swing.JTextField add_invoice_number_input;
    private javax.swing.JLabel add_invoice_number_lbl;
    private javax.swing.JTextField add_invoice_pdt_total_amount_input;
    private javax.swing.JPanel add_invoice_pnl;
    private javax.swing.JComboBox<String> add_invoice_product_combo_box;
    private javax.swing.JLabel add_product_details_lbl;
    private javax.swing.JTable add_product_details_tbl;
    private javax.swing.JLabel add_product_name_lbl;
    private javax.swing.JPanel add_product_pnl;
    private javax.swing.JTextField add_qty_input;
    private javax.swing.JLabel add_qty_lbl;
    private javax.swing.JTextPane add_remarks_input;
    private javax.swing.JLabel add_remarks_lbl;
    private javax.swing.JLabel add_service_tax_lbl;
    private javax.swing.JTextField add_st_amount_input;
    private javax.swing.JTextField add_st_percent_input;
    private javax.swing.JLabel add_st_percent_lbl;
    private javax.swing.JLabel add_st_percent_lbl1;
    private javax.swing.JTextField add_subtotal_input;
    private javax.swing.JLabel add_subtotal_lbl;
    private javax.swing.JLabel add_total_amount_lbl;
    private javax.swing.JTextField add_unit_price_input;
    private javax.swing.JLabel add_unit_price_lbl;
    private javax.swing.JTextField add_vat_amount_input;
    private javax.swing.JLabel add_vat_lbl;
    private javax.swing.JTextField add_vat_percent_input;
    private javax.swing.JLabel add_vat_percent_lbl;
    private javax.swing.JLabel add_vat_percent_lbl1;
    private javax.swing.JPanel content;
    private javax.swing.JLabel delete_invoice_btn_lbl;
    private javax.swing.JTextField delete_invoice_id_input;
    private javax.swing.JLabel delete_invoice_id_lbl;
    private javax.swing.JLabel delete_invoice_lbl;
    private javax.swing.JPanel delete_invoice_pnl;
    private javax.swing.JLabel delete_product_btn_lbl;
    private javax.swing.JTextField delete_product_id_input;
    private javax.swing.JLabel delete_product_id_lbl;
    private javax.swing.JLabel delete_product_lbl;
    private javax.swing.JPanel delete_product_pnl;
    private javax.swing.JLabel exit_lbl;
    private javax.swing.JPanel exit_pnl;
    private javax.swing.JPanel header;
    private javax.swing.JPanel home_pnl;
    private javax.swing.JScrollPane invoice_list_scrl_pane;
    private javax.swing.JTable invoice_list_tbl;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel logout;
    private javax.swing.JLabel new_pdt_lbl;
    private javax.swing.JTextField pdt_desc_input;
    private javax.swing.JLabel pdt_desc_lbl;
    private javax.swing.JLabel pdt_desc_lbl2;
    private javax.swing.JTextField pdt_discount_input;
    private javax.swing.JLabel pdt_discount_lbl;
    private javax.swing.JLabel pdt_discount_lbl2;
    private javax.swing.JTextField pdt_id_input;
    private javax.swing.JLabel pdt_id_lbl;
    private javax.swing.JTextField pdt_name_input;
    private javax.swing.JLabel pdt_name_lbl;
    private javax.swing.JLabel pdt_name_lbl3;
    private javax.swing.JTextField pdt_unit_price_input;
    private javax.swing.JLabel pdt_unit_price_lbl;
    private javax.swing.JLabel pdt_unit_price_lbl2;
    private javax.swing.JScrollPane product_details_scrl_pane1;
    private javax.swing.JScrollPane product_details_scrl_pane2;
    private javax.swing.JScrollPane product_list_scrl_pane;
    private javax.swing.JTable product_list_tbl;
    private javax.swing.JScrollPane remarks_scrl_pane1;
    private javax.swing.JScrollPane remarks_scrl_pane2;
    private javax.swing.JLabel save_product_btn_lbl;
    private javax.swing.JPanel sidebar;
    private javax.swing.JLabel sidebar_add_invoice_btn_lbl;
    private javax.swing.JLabel sidebar_add_product_btn_lbl;
    private javax.swing.JLabel sidebar_delete_invoice_btn_lbl;
    private javax.swing.JLabel sidebar_delete_product_btn_lbl;
    private javax.swing.JLabel sidebar_invoice_lbl;
    private javax.swing.JLabel sidebar_invoice_list_btn_lbl;
    private javax.swing.JPanel sidebar_menu;
    private javax.swing.JLabel sidebar_product_lbl;
    private javax.swing.JLabel sidebar_product_list_btn_lbl;
    private javax.swing.JLabel sidebar_update_invoice_btn_lbl;
    private javax.swing.JLabel sidebar_update_product_btn_lbl;
    private javax.swing.JButton update_add_to_cart_btn;
    private javax.swing.JLabel update_cash_lbl;
    private javax.swing.JTextField update_customer_contact_number_input;
    private javax.swing.JTextField update_customer_name_input;
    private javax.swing.JButton update_delete_from_cart_btn;
    private javax.swing.JTextField update_inv_amt_input;
    private javax.swing.JLabel update_invoice_btn_lbl;
    private javax.swing.JLabel update_invoice_customer_name;
    private javax.swing.JLabel update_invoice_customer_phone;
    private com.toedter.calendar.JDateChooser update_invoice_date_input;
    private javax.swing.JLabel update_invoice_date_lbl;
    private javax.swing.JLabel update_invoice_lbl;
    private javax.swing.JLabel update_invoice_lbl1;
    private javax.swing.JTextField update_invoice_number_input;
    private javax.swing.JLabel update_invoice_number_lbl;
    private javax.swing.JTextField update_invoice_pdt_total_amount_input;
    private javax.swing.JPanel update_invoice_pnl;
    private javax.swing.JComboBox<String> update_invoice_product_combo_box;
    private javax.swing.JTextField update_pdt_desc_input;
    private javax.swing.JTextField update_pdt_discount_input;
    private javax.swing.JTextField update_pdt_name_input;
    private javax.swing.JTextField update_pdt_unit_price_input;
    private javax.swing.JLabel update_product_btn_lbl;
    private javax.swing.JLabel update_product_details_lbl1;
    private javax.swing.JTable update_product_details_tbl;
    private javax.swing.JLabel update_product_name_lbl;
    private javax.swing.JPanel update_product_pnl;
    private javax.swing.JTextField update_qty_input;
    private javax.swing.JLabel update_qty_lbl;
    private javax.swing.JTextPane update_remarks_input;
    private javax.swing.JLabel update_remarks_lbl;
    private javax.swing.JLabel update_service_tax_lbl;
    private javax.swing.JTextField update_st_amount_input;
    private javax.swing.JTextField update_st_percent_input;
    private javax.swing.JLabel update_st_percent_lbl;
    private javax.swing.JTextField update_subtotal_input;
    private javax.swing.JLabel update_subtotal_lbl;
    private javax.swing.JLabel update_total_amount_lbl;
    private javax.swing.JTextField update_unit_price_input;
    private javax.swing.JLabel update_unit_price_lbl;
    private javax.swing.JTextField update_vat_amount_input;
    private javax.swing.JLabel update_vat_lbl;
    private javax.swing.JTextField update_vat_percent_input;
    private javax.swing.JLabel update_vat_percent_lbl;
    private javax.swing.JLabel update_vat_percent_lbl1;
    private javax.swing.JLabel update_vat_percent_lbl2;
    private javax.swing.JLabel user_login_heading_lbl;
    // End of variables declaration//GEN-END:variables

    private void setLblColor(JLabel lbl) {
        lbl.setBackground(new Color(0,51,153));
    }

    private void resetLblColor(JLabel lbl) {
        lbl.setBackground(new Color(0,102,204));
    }
    
    static public class TblHeaderColor extends DefaultTableCellRenderer {
        public TblHeaderColor() {
            setOpaque(true);
        }
        public Component getTableCellRendererComponent(JTable mytable, Object value, boolean selected, boolean focusses, int row, int column) {
            super.getTableCellRendererComponent(mytable, value, selected, selected, row, column);
            setBackground(new Color(0,102,204));
            setForeground(Color.WHITE);
            setFont(new java.awt.Font("Calibri", 0, 18));
            setPreferredSize(new Dimension(110,32));
            return this;
        }
    }
}
