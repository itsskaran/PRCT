package com.company;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//import net.miginfocom.swing.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
/*
 * Created by JFormDesigner on Sat Jul 31 22:34:33 PDT 2021
 */



/**
 * @author CJ Clem
 */
class Category extends JFrame {

    Connection1 con  = new Connection1();
    Connection conobj = con.connect();


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Category form1 = new Category();

        form1.updateTable();

        form1.setVisible(true);

    }


    public void updateTable() throws SQLException {

        String quer1 = "Select * from category";
        PreparedStatement query = conobj.prepareStatement(quer1);

        ResultSet rs = query.executeQuery();

        ResultSetMetaData Res = rs.getMetaData();



        int c = Res.getColumnCount();
        DefaultTableModel df = (DefaultTableModel) jtdata.getModel();

        df.setRowCount(0);

        rs.last();

        int z = rs.getRow();

        rs.beforeFirst();

        String[][] array = new String[0][];
        if(z>0) {
            array= new String[z][2];
        }

        int j=0;
        while(rs.next()) {
            array[j][0] = rs.getString("catcode");
            array[j][1] = rs.getString("catdesc");
            ++j;

        }

        String[] cols = {"Category Code", "Category Description"};

        DefaultTableModel model = new DefaultTableModel(array,cols);
        jtdata.setModel(model);

        jtdata.setDefaultEditor(Object.class, null);


    }


    public Category() throws SQLException, ClassNotFoundException {


        initComponents();




    }

    private void btnaddActionPerformed(ActionEvent e) throws SQLException {



        String catcode, catdesc;



        catcode = txtcatcode.getText();
        catdesc = txtcatdesk.getText();

        String quer1 = "Select * from category where catcode=?";
        PreparedStatement query = conobj.prepareStatement(quer1);
        query.setString(1, catcode);

        ResultSet rs = query.executeQuery();




        if(rs.isBeforeFirst()) {          //res.isBeforeFirst() is true if the cursor

            JOptionPane.showMessageDialog(null, "The catcode you are trying to enter already exists ");

            txtcatcode.setText("");
            txtcatdesk.setText("");
            txtcatcode.requestFocus();

            return;



        }


        String quer2 = "INSERT INTO Category VALUES ( ?, ? )";
        query = conobj.prepareStatement(quer2);


        query.setString(1, catcode);
        query.setString(2, catdesc);

        query.executeUpdate();

        JOptionPane.showMessageDialog(null, "One record added ");



        updateTable();








    }

    private void jtdataMouseClicked(MouseEvent e) {

        DefaultTableModel df = (DefaultTableModel) jtdata.getModel();

        int index1 = jtdata.getSelectedRow();

        txtcatcode.setText(df.getValueAt(index1,0).toString());
        txtcatdesk.setText(df.getValueAt(index1,1).toString());







    }

    private void btneditActionPerformed(ActionEvent e) throws SQLException {

        //get old value

        DefaultTableModel df = (DefaultTableModel) jtdata.getModel();

        int index1 = jtdata.getSelectedRow();

        String catcode, catdesc;



        catcode = txtcatcode.getText();
        catdesc = txtcatdesk.getText();

        String oldvalue=df.getValueAt(index1,0).toString();

        PreparedStatement query;
        query = conobj.prepareStatement("Update category set catcode=?, catdesc=? where catcode = ?");
        query.setString(1, catcode);
        query.setString(2, catdesc);
        query.setString(3, oldvalue);

        query.executeUpdate();

        JOptionPane.showMessageDialog(null, "One record edited ");

        updateTable();






    }

    private void btndeleteActionPerformed(ActionEvent e) throws SQLException {
        String catcode, catdesc;



        catcode = txtcatcode.getText();
        catdesc = txtcatdesk.getText();



        PreparedStatement query;
        query = conobj.prepareStatement("Delete from category where catcode = ?");
        query.setString(1, catcode);

        query.executeUpdate();

        JOptionPane.showMessageDialog(null, "One record deleted ");

        updateTable();


    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner non-commercial license
        label1 = new JLabel();
        txtcatcode = new JTextField();
        label2 = new JLabel();
        txtcatdesk = new JTextField();
        scrollPane1 = new JScrollPane();
        jtdata = new JTable();
        btnadd = new JButton();
        btnedit = new JButton();
        btndelete = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[fill]" +
                        "[fill]",
                // rows
                "[]" +
                        "[]" +
                        "[]" +
                        "[]"));

        //---- label1 ----
        label1.setText("Enter the category code");
        contentPane.add(label1, "cell 0 0");

        //---- txtcatcode ----
        txtcatcode.setColumns(17);
        contentPane.add(txtcatcode, "cell 1 0");

        //---- label2 ----
        label2.setText("Enter the category description");
        contentPane.add(label2, "cell 0 1");
        contentPane.add(txtcatdesk, "cell 1 1");

        //======== scrollPane1 ========
        {

            //---- jtdata ----
            jtdata.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    jtdataMouseClicked(e);
                }
            });
            scrollPane1.setViewportView(jtdata);
        }
        contentPane.add(scrollPane1, "cell 1 2");

        //---- btnadd ----
        btnadd.setText("Add");
        btnadd.addActionListener(e -> {
            try {
                btnaddActionPerformed(e);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        contentPane.add(btnadd, "cell 1 3");

        //---- btnedit ----
        btnedit.setText("Edit");
        btnedit.addActionListener(e -> {
            try {
                btneditActionPerformed(e);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        contentPane.add(btnedit, "cell 1 3");

        //---- btndelete ----
        btndelete.setText("Delete");
        btndelete.addActionListener(e -> {
            try {
                btndeleteActionPerformed(e);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        contentPane.add(btndelete, "cell 1 3");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner non-commercial license
    private JLabel label1;
    private JTextField txtcatcode;
    private JLabel label2;
    private JTextField txtcatdesk;
    private JScrollPane scrollPane1;
    private JTable jtdata;
    private JButton btnadd;
    private JButton btnedit;
    private JButton btndelete;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}



        import java.sql.DriverManager;
        import java.sql.SQLException;

public class Connection1 {

    public java.sql.Connection connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");



        java.sql.Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");




        return con1;

    }



}

