/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageVendor;
import Project.ConnectionProvider;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author suzan
 */

//Controller for ManageVendor Package

public class manageVendorController {
    
    private addVendor addvendor;
    private updateVendor updatevendor;
    private viewVendor viewvendor;
    private makeOrder makeorder;
    
    public float finalTotal = 0;
    
    /*Method used by updateVendor interface 
    to retrieve and display the vendor's information into the interface.*/
    public void showVendorDetail(String vendorID){
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM vendor WHERE vendorID='"+vendorID+"'");
            if(rs.next()){
                getUpdatevendor().jTextField2.setText(rs.getString(2));
                getUpdatevendor().jTextField3.setText(rs.getString(3));
                getUpdatevendor().jTextField4.setText(rs.getString(4));
                getUpdatevendor().jTextField5.setText(rs.getString(5));
                getUpdatevendor().jTextField6.setText(rs.getString(6));
                getUpdatevendor().jTextField1.setEditable(false);
            }
            else {
                JOptionPane.showMessageDialog(null,"vendorID does not exist");
            }
            
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /*Method used by updateVendor interface 
    to update vendor's info using values passed into this method.*/
    public void vendorUpdate(String vendorID,String vendorName,String vendorEmail,String vendorAddress,String vendorPhonenum,String vendorFax){
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE vendor SET vendorName='"+vendorName+"',vendorEmail='"+vendorEmail+"',vendorAddress='"+vendorAddress+"',vendorPhonenum='"+vendorPhonenum+"',vendorFax='"+vendorFax+"' where vendorID='"+vendorID+"'");
            JOptionPane.showMessageDialog(null,"Successfully Updated");
            getUpdatevendor().setVisible(false);
            new updateVendor().setVisible(true);

        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "vendor's ID has already Exist");
            //System.out.println(" Connection fail : "+e);
        }
    }
    
    /*Method used by updateVendor interface 
    to delete a vendor from the database using the vendor's ID passed into this method.*/
    public void vendorDelete(String vendorID){
        int del = JOptionPane.showConfirmDialog(null,"Confirm to Delete?","Select",JOptionPane.YES_NO_OPTION);
        if(del == 0){
            try{
                Connection con = ConnectionProvider.getCon();
                Statement st = con.createStatement();
                st.executeUpdate("DELETE FROM vendor WHERE vendorID = '"+vendorID+"'");
                getUpdatevendor().setVisible(false);
                new updateVendor().setVisible(true);
            }catch(Exception e)
            {
                
            }
        }
    }
    
    /*Method used by viewVendor interface
    to select all the vendors' info from database 
    and display them in table format.*/
    public void vendorView(){
        try{
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM vendor");
            DefaultTableModel model = (DefaultTableModel)getViewvendor().jTable1.getModel();
            while(rs.next()){
                model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)});
            }
            /*jTable1.setModel(DbUtils.resultSetToTableModel(rs));*/
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /*Method used by addVendor interface
    to insert a new vendor into the database
    by using values inserted and passed to this method.*/
    public void vendorAdd(String vendorID,String vendorName,String vendorEmail,String vendorAddress,String vendorPhonenum,String vendorFax){
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO vendor VALUES('"+vendorID+"','"+vendorName+"','"+vendorEmail+"','"+vendorAddress+"','"+vendorPhonenum+"','"+vendorFax+"')");
            JOptionPane.showMessageDialog(null,"Successfully added");
            getAddvendor().setVisible(false);
            new addVendor().setVisible(true);
            
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Vendor's ID already exist");
            //System.out.println(" Connection fail : "+e);
        }
    }
    
    
    /*Method used by makeOrder interface
    to retrieve the latest order ID from database
    and display the newest order ID waiting to be added to the database, on the interface.*/
    public void printorderID(){
        try{
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = st.executeQuery("Select max(orderID) FROM ordering");
            if(rs.first()){
                int id = rs.getInt(1);
                id=id+1;
                String str = String.valueOf(id);
                getMakeorder().jLabel3.setText(str);
            }
            else {
                getMakeorder().jLabel3.setText("1");
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    /*Method used by makeOrder interface
    to retrieve vendor's info when vendor's ID is inserted
    and display them on the interface.*/
    public void searchVendor(String vendorID){
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT vendorID, vendorName, vendorPhonenum, vendorEmail FROM vendor WHERE vendorID='"+vendorID+"'");
            if(rs.next()){
                getMakeorder().jTextField1.setText(rs.getString(1));
                getMakeorder().jTextField2.setText(rs.getString(2));
                getMakeorder().jTextField3.setText(rs.getString(3));
                getMakeorder().jTextField4.setText(rs.getString(4));
            }else {
                getMakeorder().jTextField2.setText("");
                getMakeorder().jTextField3.setText("");
                getMakeorder().jTextField4.setText("");
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /*Method used by makeOrder interface
    to retrieve vendor's info when vendor's name is inserted
    and display them on the interface.*/
    public void searchVendorName(String vendorName){
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT vendorID, vendorName, vendorPhonenum, vendorEmail FROM vendor WHERE vendorName LIKE '%"+vendorName+"%'");
            if(rs.next()){
                getMakeorder().jTextField1.setText(rs.getString(1));
                getMakeorder().jTextField2.setText(rs.getString(2));
                getMakeorder().jTextField3.setText(rs.getString(3));
                getMakeorder().jTextField4.setText(rs.getString(4));
            }else {
                getMakeorder().jTextField1.setText("");
                getMakeorder().jTextField3.setText("");
                getMakeorder().jTextField4.setText("");
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /*Method used by makeOrder interface
    to retrieve an inventory's info when vendor's ID is inserted
    and display them on the interface.*/
    public void searchInv(String invID){
        try{
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT invID,invName,invPrice FROM inventory WHERE invID = '"+invID+"'");
            if(rs.next()){
                getMakeorder().jTextField5.setText(rs.getString(1));
                getMakeorder().jTextField6.setText(rs.getString(2));
                getMakeorder().jTextField7.setText("1");
                getMakeorder().jTextField8.setText(rs.getString(3)); 
            }else {
                getMakeorder().jTextField6.setText("");
                getMakeorder().jTextField7.setText("");
                getMakeorder().jTextField8.setText("");
            }
        }catch(Exception e){
            
        }
    }
    
    /*Method used by makeOrder interface
    to insert the inventory into order list for further order.*/
    public void addToOrder(float orderUnitPrice, int orderInvQuantity){
        float total = orderUnitPrice * orderInvQuantity;
        DefaultTableModel model = (DefaultTableModel)getMakeorder().jTable1.getModel();
        model.addRow(new Object[]{getMakeorder().jTextField5.getText(),getMakeorder().jTextField6.getText(),orderInvQuantity,total});
        finalTotal = finalTotal + total;
        String finalTotal1 = String.valueOf(finalTotal);
        getMakeorder().jTextField9.setText(finalTotal1);
    }
    
    /*Method used by makeOrder interface
    to insert the new order details into the database
    and generate an order report.*/
    public void orderMake(String vendorID,String vendorName,String vendorPhonenum,String vendorEmail,String orderID,String adminID,String orderTotalPrice,String path){
        com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
        
         try{
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO ordering VALUES('"+orderID+"','"+vendorID+"','"+adminID+"','"+getMakeorder().jLabel2.getText()+"','"+orderTotalPrice+"')");
                    
            PdfWriter.getInstance(doc, new FileOutputStream(path+""+orderID+" "+vendorName+" "+getMakeorder().jLabel2.getText()+".pdf"));
            doc.open();
            Paragraph para1 = new Paragraph("                                                    Stationery Inventory System\n");
            doc.add(para1);
            Paragraph para4 = new Paragraph("                                                              Order Form\n");
            doc.add(para4);
            Paragraph para2 = new Paragraph("Date: "+getMakeorder().jLabel2.getText()+"\nAdmin ID: "+adminID+"\nOrder ID: "+orderID+"\n\nVENDOR DETAILS: \n\nVendor ID: "+vendorID+"\nName: "+vendorName+"\nPhone No: "+vendorPhonenum+"\nEmail: "+vendorEmail+"\n\n\n");
            doc.add(para2);
            PdfPTable table1 = new PdfPTable(4);
            table1.addCell("Inventory ID");
            table1.addCell("Name");
            table1.addCell("Quantity");
            table1.addCell("Total"); 

            for(int i=0; i<getMakeorder().jTable1.getRowCount();i++){
                String id =getMakeorder().jTable1.getValueAt(i,0).toString();
                String name =getMakeorder().jTable1.getValueAt(i,1).toString();
                String quan =getMakeorder().jTable1.getValueAt(i,2).toString();
                String tot =getMakeorder().jTable1.getValueAt(i,3).toString();
                table1.addCell(id);
                table1.addCell(name);
                table1.addCell(quan);
                table1.addCell(tot);
                st.executeUpdate("INSERT INTO orderdetail(orderID,invID,orderInvName,orderInvQuantity,orderPrice) VALUES('"+orderID+"','"+id+"','"+name+"','"+quan+"','"+tot+"')");
                
            }
            doc.add(table1);
            Paragraph para3 = new Paragraph("\n\nTotal Amount: RM"+getMakeorder().jTextField9.getText()+"\n\n");
            doc.add(para3);
            JOptionPane.showMessageDialog(null,"Order Generated");
            getMakeorder().setVisible(false);
            new makeOrder().setVisible(true);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        doc.close();
    }

    /**
     * @return the addvendor
     */
    public addVendor getAddvendor() {
        return addvendor;
    }

    /**
     * @param addvendor the addvendor to set
     */
    public void setAddvendor(addVendor addvendor) {
        this.addvendor = addvendor;
    }

    /**
     * @return the updatevendor
     */
    public updateVendor getUpdatevendor() {
        return updatevendor;
    }

    /**
     * @param updatevendor the updatevendor to set
     */
    public void setUpdatevendor(updateVendor updatevendor) {
        this.updatevendor = updatevendor;
    }

    /**
     * @return the viewvendor
     */
    public viewVendor getViewvendor() {
        return viewvendor;
    }

    /**
     * @param viewvendor the viewvendor to set
     */
    public void setViewvendor(viewVendor viewvendor) {
        this.viewvendor = viewvendor;
    }

    /**
     * @return the makeorder
     */
    public makeOrder getMakeorder() {
        return makeorder;
    }

    /**
     * @param makeorder the makeorder to set
     */
    public void setMakeorder(makeOrder makeorder) {
        this.makeorder = makeorder;
    }
    
}
