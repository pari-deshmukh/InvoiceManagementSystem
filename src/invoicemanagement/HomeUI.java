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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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
                invoice = new Invoice(rs.getInt("id"), rs.getString("customer"), rs.getString("remarks"), rs.getDate("inv_date"), rs.getFloat("subtotal"), rs.getFloat("vat"), rs.getFloat("service_tax"), rs.getFloat("discount"), rs.getFloat("cash"), rs.getFloat("change_amt"));
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
            row[4] = inv_list.get(i).getcash();
            itm.addRow(row);
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
    public void show_products() {
        ArrayList<Product> pdt_list = productList();
        DefaultTableModel ptm = (DefaultTableModel)product_list_tbl.getModel();
        ptm.setRowCount(0);
        Object[] row = new Object[5];
        for (int i=0;i<pdt_list.size();i++) {
            row[0] = pdt_list.get(i).getid();
            row[1] = pdt_list.get(i).getname();
            row[2] = pdt_list.get(i).getdescription();
            row[3] = pdt_list.get(i).getunitprice();
            row[4] = pdt_list.get(i).getdiscount();
            ptm.addRow(row);
        }
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
        this.show_products();
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
        create_invoice_lbl = new javax.swing.JLabel();
        invoice_number_input = new javax.swing.JTextField();
        invoice_number_lbl = new javax.swing.JLabel();
        customer_name_input = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        invoice_date_input = new com.toedter.calendar.JDateChooser();
        invoice_date_lbl = new javax.swing.JLabel();
        product_details_scrl_pane = new javax.swing.JScrollPane();
        product_details_tbl = new javax.swing.JTable();
        product_details_lbl = new javax.swing.JLabel();
        remarks_lbl = new javax.swing.JLabel();
        remarks_scrl_pane = new javax.swing.JScrollPane();
        remarks_input = new javax.swing.JTextPane();
        subtotal_lbl = new javax.swing.JLabel();
        subtotal_input = new javax.swing.JTextField();
        vat_lbl = new javax.swing.JLabel();
        discount_lbl = new javax.swing.JLabel();
        cash_lbl = new javax.swing.JLabel();
        vat_percent_input = new javax.swing.JTextField();
        vat_percent_lbl = new javax.swing.JLabel();
        vat_amount_input = new javax.swing.JTextField();
        service_tax_lbl = new javax.swing.JLabel();
        st_percent_input = new javax.swing.JTextField();
        st_percent_lbl = new javax.swing.JLabel();
        st_amount_input = new javax.swing.JTextField();
        discount_percent_input = new javax.swing.JTextField();
        discount_percent_lbl = new javax.swing.JLabel();
        discount_amount_input = new javax.swing.JTextField();
        cash_input = new javax.swing.JTextField();
        change_lbl = new javax.swing.JLabel();
        change_input = new javax.swing.JTextField();
        product_name_lbl = new javax.swing.JLabel();
        qty_lbl = new javax.swing.JLabel();
        qty_input = new javax.swing.JTextField();
        unit_price_lbl = new javax.swing.JLabel();
        unit_price_input = new javax.swing.JTextField();
        total_amount_lbl = new javax.swing.JLabel();
        unit_price_input1 = new javax.swing.JTextField();
        add_to_cart_btn = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        save_product_btn_lbl1 = new javax.swing.JLabel();
        update_invoice_pnl = new javax.swing.JPanel();
        save_product_btn_lbl2 = new javax.swing.JLabel();
        discount_amount_input2 = new javax.swing.JTextField();
        remarks_lbl2 = new javax.swing.JLabel();
        cash_input2 = new javax.swing.JTextField();
        change_lbl2 = new javax.swing.JLabel();
        remarks_scrl_pane1 = new javax.swing.JScrollPane();
        remarks_input2 = new javax.swing.JTextPane();
        change_input2 = new javax.swing.JTextField();
        update_invoice_lbl = new javax.swing.JLabel();
        subtotal_lbl2 = new javax.swing.JLabel();
        subtotal_input2 = new javax.swing.JTextField();
        vat_lbl2 = new javax.swing.JLabel();
        product_name_lbl2 = new javax.swing.JLabel();
        discount_lbl2 = new javax.swing.JLabel();
        cash_lbl2 = new javax.swing.JLabel();
        invoice_number_input2 = new javax.swing.JTextField();
        vat_percent_input2 = new javax.swing.JTextField();
        qty_lbl2 = new javax.swing.JLabel();
        invoice_number_lbl2 = new javax.swing.JLabel();
        vat_percent_lbl2 = new javax.swing.JLabel();
        qty_input2 = new javax.swing.JTextField();
        customer_name_input2 = new javax.swing.JTextField();
        vat_amount_input2 = new javax.swing.JTextField();
        unit_price_lbl2 = new javax.swing.JLabel();
        update_invoice_product_combo_box = new javax.swing.JComboBox<>();
        update_invoice_customer_name = new javax.swing.JLabel();
        service_tax_lbl2 = new javax.swing.JLabel();
        unit_price_input4 = new javax.swing.JTextField();
        st_percent_input2 = new javax.swing.JTextField();
        total_amount_lbl2 = new javax.swing.JLabel();
        invoice_date_input2 = new com.toedter.calendar.JDateChooser();
        st_percent_lbl2 = new javax.swing.JLabel();
        unit_price_input5 = new javax.swing.JTextField();
        invoice_date_lbl2 = new javax.swing.JLabel();
        st_amount_input2 = new javax.swing.JTextField();
        add_to_cart_btn2 = new javax.swing.JButton();
        discount_percent_input2 = new javax.swing.JTextField();
        product_details_scrl_pane1 = new javax.swing.JScrollPane();
        product_details_tbl2 = new javax.swing.JTable();
        discount_percent_lbl2 = new javax.swing.JLabel();
        product_details_lbl2 = new javax.swing.JLabel();
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
                "ID", "Customer", "Date", "Remarks", "Invoice Amount"
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
        invoice_list_scrl_pane.setViewportView(invoice_list_tbl);

        content.add(invoice_list_scrl_pane, "card3");

        add_invoice_pnl.setBackground(new java.awt.Color(255, 255, 255));

        create_invoice_lbl.setBackground(new java.awt.Color(255, 255, 255));
        create_invoice_lbl.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        create_invoice_lbl.setText("New Invoice");

        invoice_number_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        invoice_number_input.setEnabled(false);

        invoice_number_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        invoice_number_lbl.setText("Invoice Number");

        customer_name_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel1.setText("Customer Name");

        invoice_date_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        invoice_date_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        invoice_date_lbl.setText("Invoice Date");

        product_details_tbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        product_details_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Unit Price", "Qty", "Discount", "Total Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        product_details_scrl_pane.setViewportView(product_details_tbl);
        if (product_details_tbl.getColumnModel().getColumnCount() > 0) {
            product_details_tbl.getColumnModel().getColumn(0).setPreferredWidth(30);
            product_details_tbl.getColumnModel().getColumn(1).setPreferredWidth(130);
            product_details_tbl.getColumnModel().getColumn(2).setPreferredWidth(50);
            product_details_tbl.getColumnModel().getColumn(3).setPreferredWidth(30);
            product_details_tbl.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        product_details_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        product_details_lbl.setText("Product Details");

        remarks_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        remarks_lbl.setText("Remarks");

        remarks_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        remarks_scrl_pane.setViewportView(remarks_input);

        subtotal_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        subtotal_lbl.setText("Subtotal");

        subtotal_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        subtotal_input.setEnabled(false);

        vat_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        vat_lbl.setText("VAT");

        discount_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        discount_lbl.setText("Discount");

        cash_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        cash_lbl.setText("Cash");

        vat_percent_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        vat_percent_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vat_percent_inputActionPerformed(evt);
            }
        });

        vat_percent_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        vat_percent_lbl.setText("%");

        vat_amount_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        vat_amount_input.setEnabled(false);
        vat_amount_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vat_amount_inputActionPerformed(evt);
            }
        });

        service_tax_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        service_tax_lbl.setText("ST");

        st_percent_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        st_percent_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                st_percent_inputActionPerformed(evt);
            }
        });

        st_percent_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        st_percent_lbl.setText("%");

        st_amount_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        st_amount_input.setEnabled(false);
        st_amount_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                st_amount_inputActionPerformed(evt);
            }
        });

        discount_percent_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        discount_percent_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discount_percent_inputActionPerformed(evt);
            }
        });

        discount_percent_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        discount_percent_lbl.setText("%");

        discount_amount_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        discount_amount_input.setEnabled(false);
        discount_amount_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discount_amount_inputActionPerformed(evt);
            }
        });

        cash_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        change_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        change_lbl.setText("Change");

        change_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        change_input.setEnabled(false);

        product_name_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        product_name_lbl.setText("Product Name");

        qty_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        qty_lbl.setText("Qty");

        qty_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        unit_price_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        unit_price_lbl.setText("Unit Price");

        unit_price_input.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        unit_price_input.setEnabled(false);

        total_amount_lbl.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        total_amount_lbl.setText("Total Amount");

        unit_price_input1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        unit_price_input1.setEnabled(false);

        add_to_cart_btn.setBackground(new java.awt.Color(255, 255, 255));
        add_to_cart_btn.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        add_to_cart_btn.setForeground(new java.awt.Color(0, 102, 204));
        add_to_cart_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/invoicemanagement/cart.png"))); // NOI18N
        add_to_cart_btn.setBorder(null);
        add_to_cart_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add_to_cart_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_to_cart_btnActionPerformed(evt);
            }
        });

        jComboBox1.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        save_product_btn_lbl1.setBackground(new java.awt.Color(0, 102, 204));
        save_product_btn_lbl1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        save_product_btn_lbl1.setForeground(new java.awt.Color(255, 255, 255));
        save_product_btn_lbl1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        save_product_btn_lbl1.setText("Save");
        save_product_btn_lbl1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        save_product_btn_lbl1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        save_product_btn_lbl1.setOpaque(true);

        javax.swing.GroupLayout add_invoice_pnlLayout = new javax.swing.GroupLayout(add_invoice_pnl);
        add_invoice_pnl.setLayout(add_invoice_pnlLayout);
        add_invoice_pnlLayout.setHorizontalGroup(
            add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                        .addComponent(save_product_btn_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                        .addComponent(create_invoice_lbl)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                        .addComponent(invoice_number_lbl)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                        .addComponent(invoice_number_input, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, add_invoice_pnlLayout.createSequentialGroup()
                                        .addComponent(customer_name_input, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15))
                                    .addComponent(jLabel1)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, add_invoice_pnlLayout.createSequentialGroup()
                                .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(product_details_lbl, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(product_details_scrl_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                                .addComponent(product_name_lbl)
                                                .addGap(53, 53, 53))
                                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, add_invoice_pnlLayout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(qty_lbl)
                                                .addGap(40, 40, 40)
                                                .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(unit_price_lbl)
                                                    .addComponent(unit_price_input, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(total_amount_lbl)
                                                    .addComponent(unit_price_input1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(12, 12, 12))
                                            .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(qty_input, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(add_to_cart_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(24, 24, 24)))
                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(invoice_date_input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(remarks_scrl_pane)
                                .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                    .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(subtotal_lbl)
                                        .addComponent(vat_lbl))
                                    .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(subtotal_input))
                                        .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                            .addGap(14, 14, 14)
                                            .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                                    .addComponent(st_percent_input, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(2, 2, 2)
                                                    .addComponent(st_percent_lbl)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(st_amount_input, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                                                .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                                    .addComponent(vat_percent_input, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(2, 2, 2)
                                                    .addComponent(vat_percent_lbl)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(vat_amount_input))))))
                                .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                    .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(service_tax_lbl)
                                        .addComponent(discount_lbl)
                                        .addComponent(change_lbl))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cash_input)
                                        .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                            .addComponent(discount_percent_input, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(2, 2, 2)
                                            .addComponent(discount_percent_lbl)
                                            .addGap(18, 18, 18)
                                            .addComponent(discount_amount_input))
                                        .addComponent(change_input)))
                                .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                                    .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(invoice_date_lbl)
                                        .addComponent(remarks_lbl))
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addComponent(cash_lbl))
                        .addGap(39, 39, 39))))
        );
        add_invoice_pnlLayout.setVerticalGroup(
            add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(create_invoice_lbl)
                .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                        .addComponent(invoice_date_lbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(invoice_date_input, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, add_invoice_pnlLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(invoice_number_lbl)
                            .addComponent(jLabel1))
                        .addGap(2, 2, 2)
                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(invoice_number_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customer_name_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                        .addComponent(remarks_lbl)
                        .addGap(0, 0, 0)
                        .addComponent(remarks_scrl_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(subtotal_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subtotal_lbl))
                        .addGap(18, 18, 18)
                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vat_lbl)
                            .addComponent(vat_percent_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vat_percent_lbl)
                            .addComponent(vat_amount_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(service_tax_lbl)
                            .addComponent(st_percent_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(st_percent_lbl)
                            .addComponent(st_amount_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(discount_lbl)
                            .addComponent(discount_percent_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(discount_percent_lbl)
                            .addComponent(discount_amount_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cash_lbl)
                            .addComponent(cash_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(change_lbl)
                            .addComponent(change_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(add_invoice_pnlLayout.createSequentialGroup()
                        .addComponent(product_details_lbl)
                        .addGap(5, 5, 5)
                        .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(product_name_lbl)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, add_invoice_pnlLayout.createSequentialGroup()
                                .addComponent(qty_lbl)
                                .addGap(2, 2, 2)
                                .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(qty_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, add_invoice_pnlLayout.createSequentialGroup()
                                .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(unit_price_lbl)
                                    .addComponent(total_amount_lbl))
                                .addGap(2, 2, 2)
                                .addGroup(add_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(unit_price_input, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(unit_price_input1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(add_to_cart_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(product_details_scrl_pane, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(save_product_btn_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        content.add(add_invoice_pnl, "card3");

        update_invoice_pnl.setBackground(new java.awt.Color(255, 255, 255));

        save_product_btn_lbl2.setBackground(new java.awt.Color(0, 102, 204));
        save_product_btn_lbl2.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        save_product_btn_lbl2.setForeground(new java.awt.Color(255, 255, 255));
        save_product_btn_lbl2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        save_product_btn_lbl2.setText("Update");
        save_product_btn_lbl2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        save_product_btn_lbl2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        save_product_btn_lbl2.setOpaque(true);

        discount_amount_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        discount_amount_input2.setEnabled(false);
        discount_amount_input2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discount_amount_input2ActionPerformed(evt);
            }
        });

        remarks_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        remarks_lbl2.setText("Remarks");

        cash_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        change_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        change_lbl2.setText("Change");

        remarks_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        remarks_scrl_pane1.setViewportView(remarks_input2);

        change_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        change_input2.setEnabled(false);

        update_invoice_lbl.setBackground(new java.awt.Color(255, 255, 255));
        update_invoice_lbl.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        update_invoice_lbl.setText("Update Invoice");

        subtotal_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        subtotal_lbl2.setText("Subtotal");

        subtotal_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        subtotal_input2.setEnabled(false);

        vat_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        vat_lbl2.setText("VAT");

        product_name_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        product_name_lbl2.setText("Product Name");

        discount_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        discount_lbl2.setText("Discount");

        cash_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        cash_lbl2.setText("Cash");

        invoice_number_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        invoice_number_input2.setEnabled(false);

        vat_percent_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        vat_percent_input2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vat_percent_input2ActionPerformed(evt);
            }
        });

        qty_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        qty_lbl2.setText("Qty");

        invoice_number_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        invoice_number_lbl2.setText("Invoice Number");

        vat_percent_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        vat_percent_lbl2.setText("%");

        qty_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        customer_name_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        vat_amount_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        vat_amount_input2.setEnabled(false);
        vat_amount_input2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vat_amount_input2ActionPerformed(evt);
            }
        });

        unit_price_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        unit_price_lbl2.setText("Unit Price");

        update_invoice_product_combo_box.setBackground(new java.awt.Color(255, 255, 255));
        update_invoice_product_combo_box.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_invoice_product_combo_box.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        update_invoice_customer_name.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        update_invoice_customer_name.setText("Customer Name");

        service_tax_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        service_tax_lbl2.setText("ST");

        unit_price_input4.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        unit_price_input4.setEnabled(false);

        st_percent_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        st_percent_input2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                st_percent_input2ActionPerformed(evt);
            }
        });

        total_amount_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        total_amount_lbl2.setText("Total Amount");

        invoice_date_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N

        st_percent_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        st_percent_lbl2.setText("%");

        unit_price_input5.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        unit_price_input5.setEnabled(false);

        invoice_date_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        invoice_date_lbl2.setText("Invoice Date");

        st_amount_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        st_amount_input2.setEnabled(false);
        st_amount_input2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                st_amount_input2ActionPerformed(evt);
            }
        });

        add_to_cart_btn2.setBackground(new java.awt.Color(255, 255, 255));
        add_to_cart_btn2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        add_to_cart_btn2.setForeground(new java.awt.Color(0, 102, 204));
        add_to_cart_btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/invoicemanagement/cart.png"))); // NOI18N
        add_to_cart_btn2.setBorder(null);
        add_to_cart_btn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add_to_cart_btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_to_cart_btn2ActionPerformed(evt);
            }
        });

        discount_percent_input2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        discount_percent_input2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discount_percent_input2ActionPerformed(evt);
            }
        });

        product_details_tbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        product_details_tbl2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Unit Price", "Qty", "Discount", "Total Amount"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        product_details_scrl_pane1.setViewportView(product_details_tbl2);
        if (product_details_tbl2.getColumnModel().getColumnCount() > 0) {
            product_details_tbl2.getColumnModel().getColumn(0).setPreferredWidth(30);
            product_details_tbl2.getColumnModel().getColumn(1).setPreferredWidth(130);
            product_details_tbl2.getColumnModel().getColumn(2).setPreferredWidth(50);
            product_details_tbl2.getColumnModel().getColumn(3).setPreferredWidth(30);
            product_details_tbl2.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        discount_percent_lbl2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        discount_percent_lbl2.setText("%");

        product_details_lbl2.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        product_details_lbl2.setText("Product Details");

        javax.swing.GroupLayout update_invoice_pnlLayout = new javax.swing.GroupLayout(update_invoice_pnl);
        update_invoice_pnl.setLayout(update_invoice_pnlLayout);
        update_invoice_pnlLayout.setHorizontalGroup(
            update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                        .addComponent(save_product_btn_lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                        .addComponent(update_invoice_lbl)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                        .addComponent(invoice_number_lbl2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                        .addComponent(invoice_number_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, update_invoice_pnlLayout.createSequentialGroup()
                                        .addComponent(customer_name_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15))
                                    .addComponent(update_invoice_customer_name)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, update_invoice_pnlLayout.createSequentialGroup()
                                .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(product_details_lbl2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(product_details_scrl_pane1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                                .addComponent(product_name_lbl2)
                                                .addGap(53, 53, 53))
                                            .addComponent(update_invoice_product_combo_box, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, update_invoice_pnlLayout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(qty_lbl2)
                                                .addGap(40, 40, 40)
                                                .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(unit_price_lbl2)
                                                    .addComponent(unit_price_input4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(total_amount_lbl2)
                                                    .addComponent(unit_price_input5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(12, 12, 12))
                                            .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(qty_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(add_to_cart_btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(24, 24, 24)))
                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(invoice_date_input2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(remarks_scrl_pane1)
                                .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                    .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(subtotal_lbl2)
                                        .addComponent(vat_lbl2))
                                    .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(subtotal_input2))
                                        .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                            .addGap(14, 14, 14)
                                            .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                                    .addComponent(st_percent_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(2, 2, 2)
                                                    .addComponent(st_percent_lbl2)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(st_amount_input2, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                                                .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                                    .addComponent(vat_percent_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(2, 2, 2)
                                                    .addComponent(vat_percent_lbl2)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(vat_amount_input2))))))
                                .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                    .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(service_tax_lbl2)
                                        .addComponent(discount_lbl2)
                                        .addComponent(change_lbl2))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cash_input2)
                                        .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                            .addComponent(discount_percent_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(2, 2, 2)
                                            .addComponent(discount_percent_lbl2)
                                            .addGap(18, 18, 18)
                                            .addComponent(discount_amount_input2))
                                        .addComponent(change_input2)))
                                .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                                    .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(invoice_date_lbl2)
                                        .addComponent(remarks_lbl2))
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addComponent(cash_lbl2))
                        .addGap(39, 39, 39))))
        );
        update_invoice_pnlLayout.setVerticalGroup(
            update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(update_invoice_lbl)
                .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                        .addComponent(invoice_date_lbl2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(invoice_date_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, update_invoice_pnlLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(invoice_number_lbl2)
                            .addComponent(update_invoice_customer_name))
                        .addGap(2, 2, 2)
                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(invoice_number_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customer_name_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                        .addComponent(remarks_lbl2)
                        .addGap(0, 0, 0)
                        .addComponent(remarks_scrl_pane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(subtotal_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subtotal_lbl2))
                        .addGap(18, 18, 18)
                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vat_lbl2)
                            .addComponent(vat_percent_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vat_percent_lbl2)
                            .addComponent(vat_amount_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(service_tax_lbl2)
                            .addComponent(st_percent_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(st_percent_lbl2)
                            .addComponent(st_amount_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(discount_lbl2)
                            .addComponent(discount_percent_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(discount_percent_lbl2)
                            .addComponent(discount_amount_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cash_lbl2)
                            .addComponent(cash_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(change_lbl2)
                            .addComponent(change_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(update_invoice_pnlLayout.createSequentialGroup()
                        .addComponent(product_details_lbl2)
                        .addGap(5, 5, 5)
                        .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(product_name_lbl2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, update_invoice_pnlLayout.createSequentialGroup()
                                .addComponent(qty_lbl2)
                                .addGap(2, 2, 2)
                                .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(qty_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(update_invoice_product_combo_box, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, update_invoice_pnlLayout.createSequentialGroup()
                                .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(unit_price_lbl2)
                                    .addComponent(total_amount_lbl2))
                                .addGap(2, 2, 2)
                                .addGroup(update_invoice_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(unit_price_input4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(unit_price_input5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(add_to_cart_btn2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(product_details_scrl_pane1, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(save_product_btn_lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

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
                "ID", "Name", "Description", "Unit Price", "Discount"
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
        pdt_unit_price_lbl.setText("Unit Price");

        pdt_unit_price_input.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        pdt_discount_lbl.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_discount_lbl.setText("Discount");

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
        pdt_unit_price_lbl2.setText("Unit Price");

        update_pdt_unit_price_input.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        pdt_discount_lbl2.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        pdt_discount_lbl2.setText("Discount");

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
                                .addComponent(pdt_desc_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                                .addComponent(pdt_unit_price_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(16, 16, 16)
                        .addGroup(update_product_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(update_pdt_name_input)
                            .addComponent(pdt_id_input)
                            .addComponent(update_pdt_unit_price_input, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(update_pdt_discount_input, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
                            .addComponent(update_pdt_desc_input))))
                .addContainerGap(76, Short.MAX_VALUE))
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

    private void st_amount_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_st_amount_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_st_amount_inputActionPerformed

    private void vat_amount_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vat_amount_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vat_amount_inputActionPerformed

    private void vat_percent_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vat_percent_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vat_percent_inputActionPerformed

    private void discount_amount_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discount_amount_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_discount_amount_inputActionPerformed

    private void add_to_cart_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_to_cart_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_to_cart_btnActionPerformed

    private void discount_percent_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discount_percent_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_discount_percent_inputActionPerformed

    private void st_percent_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_st_percent_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_st_percent_inputActionPerformed

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
        
        this.show_products();
    }//GEN-LAST:event_sidebar_product_list_btn_lblMouseClicked

    private void pdt_desc_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdt_desc_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pdt_desc_inputActionPerformed

    private void discount_amount_input2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discount_amount_input2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_discount_amount_input2ActionPerformed

    private void vat_percent_input2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vat_percent_input2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vat_percent_input2ActionPerformed

    private void vat_amount_input2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vat_amount_input2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vat_amount_input2ActionPerformed

    private void st_percent_input2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_st_percent_input2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_st_percent_input2ActionPerformed

    private void st_amount_input2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_st_amount_input2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_st_amount_input2ActionPerformed

    private void add_to_cart_btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_to_cart_btn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_to_cart_btn2ActionPerformed

    private void discount_percent_input2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discount_percent_input2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_discount_percent_input2ActionPerformed

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
    private javax.swing.JPanel add_invoice_pnl;
    private javax.swing.JPanel add_product_pnl;
    private javax.swing.JButton add_to_cart_btn;
    private javax.swing.JButton add_to_cart_btn2;
    private javax.swing.JTextField cash_input;
    private javax.swing.JTextField cash_input2;
    private javax.swing.JLabel cash_lbl;
    private javax.swing.JLabel cash_lbl2;
    private javax.swing.JTextField change_input;
    private javax.swing.JTextField change_input2;
    private javax.swing.JLabel change_lbl;
    private javax.swing.JLabel change_lbl2;
    private javax.swing.JPanel content;
    private javax.swing.JLabel create_invoice_lbl;
    private javax.swing.JTextField customer_name_input;
    private javax.swing.JTextField customer_name_input2;
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
    private javax.swing.JTextField discount_amount_input;
    private javax.swing.JTextField discount_amount_input2;
    private javax.swing.JLabel discount_lbl;
    private javax.swing.JLabel discount_lbl2;
    private javax.swing.JTextField discount_percent_input;
    private javax.swing.JTextField discount_percent_input2;
    private javax.swing.JLabel discount_percent_lbl;
    private javax.swing.JLabel discount_percent_lbl2;
    private javax.swing.JLabel exit_lbl;
    private javax.swing.JPanel exit_pnl;
    private javax.swing.JPanel header;
    private javax.swing.JPanel home_pnl;
    private com.toedter.calendar.JDateChooser invoice_date_input;
    private com.toedter.calendar.JDateChooser invoice_date_input2;
    private javax.swing.JLabel invoice_date_lbl;
    private javax.swing.JLabel invoice_date_lbl2;
    private javax.swing.JScrollPane invoice_list_scrl_pane;
    private javax.swing.JTable invoice_list_tbl;
    private javax.swing.JTextField invoice_number_input;
    private javax.swing.JTextField invoice_number_input2;
    private javax.swing.JLabel invoice_number_lbl;
    private javax.swing.JLabel invoice_number_lbl2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel product_details_lbl;
    private javax.swing.JLabel product_details_lbl2;
    private javax.swing.JScrollPane product_details_scrl_pane;
    private javax.swing.JScrollPane product_details_scrl_pane1;
    private javax.swing.JTable product_details_tbl;
    private javax.swing.JTable product_details_tbl2;
    private javax.swing.JScrollPane product_list_scrl_pane;
    private javax.swing.JTable product_list_tbl;
    private javax.swing.JLabel product_name_lbl;
    private javax.swing.JLabel product_name_lbl2;
    private javax.swing.JTextField qty_input;
    private javax.swing.JTextField qty_input2;
    private javax.swing.JLabel qty_lbl;
    private javax.swing.JLabel qty_lbl2;
    private javax.swing.JTextPane remarks_input;
    private javax.swing.JTextPane remarks_input2;
    private javax.swing.JLabel remarks_lbl;
    private javax.swing.JLabel remarks_lbl2;
    private javax.swing.JScrollPane remarks_scrl_pane;
    private javax.swing.JScrollPane remarks_scrl_pane1;
    private javax.swing.JLabel save_product_btn_lbl;
    private javax.swing.JLabel save_product_btn_lbl1;
    private javax.swing.JLabel save_product_btn_lbl2;
    private javax.swing.JLabel service_tax_lbl;
    private javax.swing.JLabel service_tax_lbl2;
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
    private javax.swing.JTextField st_amount_input;
    private javax.swing.JTextField st_amount_input2;
    private javax.swing.JTextField st_percent_input;
    private javax.swing.JTextField st_percent_input2;
    private javax.swing.JLabel st_percent_lbl;
    private javax.swing.JLabel st_percent_lbl2;
    private javax.swing.JTextField subtotal_input;
    private javax.swing.JTextField subtotal_input2;
    private javax.swing.JLabel subtotal_lbl;
    private javax.swing.JLabel subtotal_lbl2;
    private javax.swing.JLabel total_amount_lbl;
    private javax.swing.JLabel total_amount_lbl2;
    private javax.swing.JTextField unit_price_input;
    private javax.swing.JTextField unit_price_input1;
    private javax.swing.JTextField unit_price_input4;
    private javax.swing.JTextField unit_price_input5;
    private javax.swing.JLabel unit_price_lbl;
    private javax.swing.JLabel unit_price_lbl2;
    private javax.swing.JLabel update_invoice_customer_name;
    private javax.swing.JLabel update_invoice_lbl;
    private javax.swing.JPanel update_invoice_pnl;
    private javax.swing.JComboBox<String> update_invoice_product_combo_box;
    private javax.swing.JTextField update_pdt_desc_input;
    private javax.swing.JTextField update_pdt_discount_input;
    private javax.swing.JTextField update_pdt_name_input;
    private javax.swing.JTextField update_pdt_unit_price_input;
    private javax.swing.JLabel update_product_btn_lbl;
    private javax.swing.JPanel update_product_pnl;
    private javax.swing.JLabel user_login_heading_lbl;
    private javax.swing.JTextField vat_amount_input;
    private javax.swing.JTextField vat_amount_input2;
    private javax.swing.JLabel vat_lbl;
    private javax.swing.JLabel vat_lbl2;
    private javax.swing.JTextField vat_percent_input;
    private javax.swing.JTextField vat_percent_input2;
    private javax.swing.JLabel vat_percent_lbl;
    private javax.swing.JLabel vat_percent_lbl2;
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
