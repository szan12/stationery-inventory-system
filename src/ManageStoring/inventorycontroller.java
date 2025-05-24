/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageStoring;

import java.awt.Color;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import ManageStoring.addInventoryForm;
import ManageStoring.returnInventoryForm;
import ManageStoring.updateInventory;
import ManageStoring.viewInventory;
import Project.ConnectionProvider;
import net.proteanit.sql.DbUtils;
import java.util.Date;
import java.util.Calendar;

/**
 *
 * @author huixu
 */
public class inventorycontroller {

    /**
     * @return the add
     */
    public static addInventoryForm getAdd() {
        return add;
    }

    /**
     * @param aAdd the add to set
     */
    public static void setAdd(addInventoryForm aAdd) {
        add = aAdd;
    }

    /**
     * @return the update
     */
    public static updateInventory getUpdate() {
        return update;
    }

    /**
     * @param aUpdate the update to set
     */
    public static void setUpdate(updateInventory aUpdate) {
        update = aUpdate;
    }

    /**
     * @return the delete
     */
    public static returnInventoryForm getDelete() {
        return delete;
    }

    /**
     * @param aDelete the delete to set
     */
    public static void setDelete(returnInventoryForm aDelete) {
        delete = aDelete;
    }

    /**
     * @return the view
     */
    public static viewInventory getView() {
        return view;
    }

    /**
     * @param aView the view to set
     */
    public static void setView(viewInventory aView) {
        view = aView;
    }
    
    public static addInventoryForm add;
    public static updateInventory update;
    public static returnInventoryForm delete;
    public static viewInventory view;
    public static String invid;
    public static String invname;
    public static String invquantity;
    public static String invstatus;
    public static String invprice;
    public static String invlocation;
    public static String invarrivedate;
    public static String itemid;
    public static String catid;
    public static String catname;
    public static String vendorid;
    public static String returndescription;
    
    public inventorycontroller(){}
    
    //Declare variables//
    public static void insert(String invID, String invName, String invQuantity, String invStatus, String invPrice, String invLocation, String invArrivedate, String itemID, String catID, String catName, String vendorID, String returnDescription)
    {
        invid = invID;
        invname = invName;
        invquantity = invQuantity;
        invstatus = invStatus;
        invprice = invPrice;
        invlocation = invLocation;
        invarrivedate = invArrivedate;
        itemid = itemID;
        catid = catID;
        catname = catName;
        vendorid = vendorID;
        returndescription = returnDescription;
    } 
    
    //This method is use to retrive to addInvemtoryForm interface. This query is use to insert new inventory details.//
    public static void save(String invID,String invName,String invQuantity,String invStatus,String invPrice,String invLocation, String invArrivedate,String itemID,String catID,String catName,String vendorID,String returnDescription)
    {
      try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st=con.createStatement();
            st.executeUpdate("Insert into inventory values ('"+invID+"','"+invName+"','"+invQuantity+"','"+invStatus+"','"+invPrice+"','"+invLocation+"','"+invArrivedate+"','"+itemID+"','"+catID+"','"+catName+"','"+vendorID+"','"+returnDescription+"')");
            JOptionPane.showMessageDialog(null,"Successfully Save");
            add.setVisible(false);
            new addInventoryForm().setVisible(true);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    //This method is use to retrive to addInventoryForm. This is use to auto increment the inventory ID//
    public static void increment(String invID)
    {
         try
        {
            Connection con= ConnectionProvider.getCon();
            Statement st=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=st.executeQuery("Select max(invID) from inventory");
            if (rs.first())
            {
                int id=rs.getInt(1);
                id=id+1;
                String str=String.valueOf(id);
                getAdd().jLabel4.setText(str);
            }
            else
            {
                getAdd().jLabel4.setText("1");
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //This method is use to retrive to updateInvemtory interface. This query is use to update existing inventory details.//
    public static void update(String invID,String invName,String invQuantity,String invStatus,String invPrice,String invLocation,String invArrivedate,String itemID,String catID,String catName,String vendorID,String returnDescription)
    {
       String Connection ="jdbc:mysql://localhost:3306/sis";
       
         try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st=con.createStatement();
            st.executeUpdate("UPDATE inventory SET invName='"+invName+"',invQuantity='"+invQuantity+"',invStatus='"+invStatus+"',invPrice='"+invPrice+"',invLocation='"+invLocation+"',invArrivedate='"+invArrivedate+"',itemID='"+itemID+"',catID='"+catID+"',catName='"+catName+"',vendorID='"+vendorID+"',returnDescription='"+returnDescription+"'where invID='"+invID+"'");
            JOptionPane.showMessageDialog(null,"Successfilly updated");
            update.setVisible(false);
            new updateInventory().setVisible(true);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
    }  
    
    //This method is use to retrive to updateInvemtory interface. This query is use to search existing inventory details by using inventory Id in order to update the existing details.//
    public static void search(String invID)
    {
         invID=update.jTextField1.getText();
        try
        {
            Connection con=ConnectionProvider.getCon();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM inventory where invID='"+invID+"'");
             if(rs.next())
             {
                   update.jTextField1.setText(rs.getString(1));
                   update.jTextField2.setText(rs.getString(2));
                   update.jTextField3.setText(rs.getString(3));
                   update.jTextField4.setText(rs.getString(4));
                   update.jTextField5.setText(rs.getString(5));
                   update.jTextField6.setText(rs.getString(6));
                   update.jTextField7.setText(rs.getString(7));
                   update.jTextField8.setText(rs.getString(8));
                   update.jTextField9.setText(rs.getString(9));
                   update.jTextField10.setText(rs.getString(10));
                   update.jTextField11.setText(rs.getString(11));
                   update.jTextField1.setEditable(false);
             }
             else
             {
                 JOptionPane.showMessageDialog(null,"Inventory ID does not exist");
             }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //This method is use to retrive to returnInvemtoryForm interface. This query is use to search existing inventory details by using inventory Id.//
     public static void search2(String invID)
    {
        invID=delete.jTextField1.getText();
        try
        {
            Connection con=ConnectionProvider.getCon();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM inventory where invID='"+invID+"'");
            if(rs.next())
             {
                   delete.jTextField1.setText(rs.getString(1));
                   delete.jTextField2.setText(rs.getString(2));
                   delete.jTextField3.setText(rs.getString(3));
                   delete.jTextField4.setText(rs.getString(4));
                   delete.jTextField5.setText(rs.getString(5));
                   delete.jTextField6.setText(rs.getString(6));
                   delete.jTextField7.setText(rs.getString(7));
                   delete.jTextField8.setText(rs.getString(8));
                   delete.jTextField9.setText(rs.getString(9));
                   delete.jTextField10.setText(rs.getString(10));
                   delete.jTextField11.setText(rs.getString(11));
                   delete.jTextField1.setEditable(false);
             }
             else
             {
                 JOptionPane.showMessageDialog(null,"Inventory ID does not exist");
             }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String returnDescription=delete.jTextField12.getText();
            st.executeUpdate("UPDATE inventory SET returnDescription='"+returnDescription+"' where invID='"+invID+"'");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }  
    
     //This method is use to retrive to returnInvemtoryForm interface. This query is use to delete all of the selected existing inventory details.//
     public static void delete(String invID,String invName,String invQuantity,String invStatus,String invPrice,String invLocation,String invArrivedate,String itemID,String catID,String catName,String vendorID,String returnDescription)
     {
         String Connection ="jdbc:mysql://localhost:3306/sis";
       invID=delete.jTextField1.getText();
        int a=JOptionPane.showConfirmDialog(null,"Do you want to Delete?","Select",JOptionPane.YES_NO_OPTION);
        if(a==0)
        {
            try
            {
                Connection con=ConnectionProvider.getCon();
                Statement st=con.createStatement();
                st.executeUpdate("Delete from inventory where invID='"+invID+"'");
                delete.setVisible(false);
                new returnInventoryForm().setVisible(true);        
        }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
        }
     }
     
     //This method is use to retrive to viewInvemtory interface. This query is use to display all existing inventory details.//
     public static void view(String invID,String invName,String invQuantity,String invStatus,String invPrice,String invLocation,String invArrivedate,String itemID,String catID,String catName,String vendorID,String returnDescription)
     {
          try
        {
            Connection con=ConnectionProvider.getCon();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("Select * from inventory");
            view.jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
     }

    
}
