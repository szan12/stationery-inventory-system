/*
This is a controller control between addToCart, editCart, makeBooking and bookingReport interface.
 */
package MakeBooking;
import Project.ConnectionProvider;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import ManageLogin.accountController;

public class makeBookingController {
/** Declare variables and interface**/
    
    private addToCart addtocart;
    private editCart editcart;
    private makeBooking makebooking;
    private bookingReport bookingreport;
    
    private String itemID,userID,itemName,bookStatus, vendorName, vendorAddress;
    private int quantity,bookingID, cartID;
    private float unitPrice,totalPrice,itemPrice;
    LocalDate date = java.time.LocalDate.now();
    
    public makeBookingController(String itemID, String itemName, String booksStatus, int quantity, float unitprice, String vendorName, String vendorAddress, int bookingID, int cartID, float totalPrice, float itemPrice, LocalDate date){
        this.itemID=itemID;
        this.itemName=itemName;
        this.bookStatus=bookStatus;
        this.cartID=cartID;
        this.bookingID=bookingID;
        this.totalPrice=totalPrice;
        this.itemPrice=itemPrice;
        this.date=date;
        this.quantity=quantity;
        this.unitPrice=unitprice;
        this.vendorName=vendorName;
        this.vendorAddress=vendorAddress;
    }
    
    makeBookingController(){
        
    }
    

 /** This method retrieve data from database and show in the table in addToCart interface**/   
 public void showProductDetail(){

        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select i.invID as 'INV ID', i.invName as Name, i.invQuantity as Quantity, i.invPrice as 'Unit Price', v.vendorName as 'Vendor', v.vendorAddress as 'Vendor Address' FROM inventory as i INNER JOIN vendor as v WHERE i.vendorID=v.vendorID ");
            getAddtocart().jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
}

  /** This method insert data to cart table in addToCart interface**/ 
 public void addCart(String itemID, int quantity, float unitPrice){
     try
        {
            totalPrice = quantity * unitPrice;
            String userID = String.valueOf(accountController.userID);

            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            st.executeUpdate("Insert Into cart(invID, userID, itemQuantity, totalPrice) VALUES('"+itemID+"','"+userID+"','"+quantity+"','"+totalPrice+"')");
            JOptionPane.showMessageDialog(null, "Item inserted succesfully");
            getAddtocart().setVisible(false);
            new addToCart().setVisible(true);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
 
  /** This method retrieve data from database and show in the textbox in addToCart interface**/ 
 public void showItemDetail(String itemID){
        try
        {
            
            
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select i.invID, i.invName, i.invPrice, i.invQuantity, v.vendorName, v.vendorAddress, v.vendorPhonenum FROM inventory as i INNER JOIN vendor as v ON i.vendorID=v.vendorID  WHERE invID='"+itemID+"'");
            if(rs.next())
            {
                getAddtocart().jTextField2.setText(rs.getString(1));
                getAddtocart().jTextField3.setText(rs.getString(2));
                getAddtocart().jTextField4.setText(rs.getString(3));
                getAddtocart().jTextField5.setText(rs.getString(4));
                getAddtocart().jTextField6.setText(rs.getString(5));
                getAddtocart().jTextField7.setText(rs.getString(6));
                getAddtocart().jTextField8.setText(rs.getString(7));
                getAddtocart().jTextField1.setEditable(false);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Item ID does not exist");

            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    
    } 
 
  /** This method update data in cart table in editCart interface**/ 
    public void UpdateCart(String itemID, float unitPrice, int quantity){

        try
        {
            String userID = String.valueOf(accountController.userID);
            totalPrice = quantity * unitPrice;
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE cart SET itemQuantity='"+quantity+"', totalPrice='"+totalPrice+"' WHERE userID='"+userID+"' AND invID='"+itemID+"' ");
                JOptionPane.showMessageDialog(null, "Item updated succesfully"); 
                editcart.setVisible(false);
                new editCart().setVisible(true);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
   
     /** This method delete data from cart table in editCart interface**/ 
    public void deleteCart(String itemID){
        String userID = String.valueOf(accountController.userID);
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            st.executeUpdate("DELETE FROM cart WHERE userID='"+userID+"' AND invID='"+itemID+"' ");
                JOptionPane.showMessageDialog(null, "Item deleted succesfully");
                editcart.setVisible(false);
                new editCart().setVisible(true);
        }
        
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /** This method retrieve data from cart and inventory table into textbox in editCart interface**/
    public void searchCart(String itemID){
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select i.invID, i.invName, i.invPrice, i.invQuantity FROM inventory as i INNER JOIN cart as c ON i.invID=c.invID WHERE c.invID='"+itemID+"'");
            if(rs.next())
            {
                editcart.jTextField2.setText(rs.getString(1));
                editcart.jTextField3.setText(rs.getString(2));
                editcart.jTextField4.setText(rs.getString(3));
                editcart.jTextField5.setText(rs.getString(4));
                editcart.jTextField1.setEditable(false);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Item ID does not exist");
                
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /** This method retrieve data from cart, inventory and vendor table in makeBooking interface**/
    public void showCartDetails(){
        try
        {
            String userID = String.valueOf(accountController.userID);
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select i.invID as INVID, i.invName as Name, c.itemQuantity as 'Order Quantity', c.totalPrice as 'Total Price', v.vendorName as Vendor, v.vendorAddress as 'Vendor Adress' from cart as c INNER JOIN inventory as i ON c.invID=i.invID INNER JOIN vendor as v ON i.vendorID=v.vendorID where c.userID='"+userID+"' ");
            getMakebooking().jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
  /** This method retrieve, insert and update data from/to cart, inventory, booking and bookingdetail table in makeBooking interface**/ 
    public void makeBooking(String itemID, int cartID, int quantity, int bookingID, float totalPrice, String itemName, float itemPrice, String bookStatus){
        String userID = String.valueOf(accountController.userID);
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select cartID from cart WHERE userID='"+userID+"' AND invID='"+itemID+"' ");
            
            while(rs.next()){
            cartID = rs.getInt("cartID");
            }
            makebooking.setVisible(false);
            new makeBooking().setVisible(true);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            
            st.executeUpdate("INSERT INTO booking(bookDate, bookStatus, userID, adminID, cartID) VALUES('"+date+"','"+bookStatus+"','"+userID+"','','"+cartID+"')");
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            
            ResultSet rs = st.executeQuery("SELECT bookingID FROM booking where cartID='"+cartID+"' ");
            
            while(rs.next()){
            bookingID = rs.getInt("bookingID");
            }            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO bookingdetail(bookingID, invID, bookInvName, bookInvPrice, bookInvQuantity, bookTotalPrice) VALUES  ('"+bookingID+"','"+itemID+"','','0','0','0')");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT c.itemQuantity, c.totalPrice FROM cart as c INNER jOIN booking as b ON c.cartID=b.cartID INNER JOIN bookingdetail as d ON b.bookingID=d.bookingID WHERE c.cartID='"+cartID+"' ");
            
            while(rs.next()){
            quantity = rs.getInt("c.itemQuantity");
            totalPrice = rs.getFloat("c.totalPrice");
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }      
        
         
         try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT i.invName,i.invPrice FROM inventory as i INNER JOIN bookingdetail as d ON i.invID=d.invID WHERE i.invID='"+itemID+"'");
            
            while(rs.next()){
            itemName = rs.getString("i.invName");
            itemPrice = rs.getFloat("i.invPrice");
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
         
         
         try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE bookingdetail SET bookInvQuantity='"+quantity+"', bookTotalPrice='"+totalPrice+"', bookInvName='"+itemName+"', bookInvPrice='"+itemPrice+"' WHERE bookingID='"+bookingID+"'");
            JOptionPane.showMessageDialog(null, "Item booked successfully");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /** This method retrieve data from cart adn inventory table into textbox in makeBooking interface**/
    public void searchCart2(String itemID){
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select i.invID, i.invName, i.invPrice, c.itemQuantity, c.totalPrice FROM cart as c INNER JOIN inventory as i ON c.invID=i.invID WHERE c.invID='"+itemID+"' ");
            if(rs.next())
            {
                getMakebooking().jTextField2.setText(rs.getString(1));
                getMakebooking().jTextField3.setText(rs.getString(2));
                getMakebooking().jTextField4.setText(rs.getString(3));
                getMakebooking().jTextField5.setText(rs.getString(4));
                getMakebooking().jTextField6.setText(rs.getString(5));
                getMakebooking().jTextField1.setEditable(false);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Item ID does not exist in cart");
                
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    /** This method retrieve data from booking, bookingdetail and inventory table in editCart interface**/
    public void showReport(){
        String userID = String.valueOf(accountController.userID);
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select d.invID as INVID, d.bookInvName as Name, d.bookInvQuantity as 'Ordered Quantity', d.bookTotalPrice as 'Total Price', v.vendorName as Vendor, v.vendorAddress as 'Vendor Address', v.vendorPhonenum as 'Vendor Phone Number', b.bookDate as 'booking date', b.bookStatus as Status FROM bookingdetail as d INNER JOIN booking as b ON b.bookingID=d.bookingID INNER JOIN inventory as i ON d.invID=i.invID INNER JOIN vendor as v ON i.vendorID=v.vendorID where b.userID='"+userID+"' AND b.bookStatus='Success' ");
            bookingreport.jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    
    
            /**
     * @return the addtocart
     */
    public addToCart getAddtocart() {
        return addtocart;
    }

    /**
     * @param addtocart the addtocart to set
     */
    public void setAddtocart(addToCart addtocart) {
        this.addtocart = addtocart;
    }

    /**
     * @return the editcart
     */
    public editCart getEditcart() {
        return editcart;
    }

    /**
     * @param editcart the editcart to set
     */
    public void setEditcart(editCart editcart) {
        this.editcart = editcart;
    }

    /**
     * @return the makebooking
     */
    public makeBooking getMakebooking() {
        return makebooking;
    }

    /**
     * @param makebooking the makebooking to set
     */
    public void setMakebooking(makeBooking makebooking) {
        this.makebooking = makebooking;
    }

    /**
     * @return the bookingreport
     */
    public bookingReport getBookingreport() {
        return bookingreport;
    }

    /**
     * @param bookingreport the bookingreport to set
     */
    public void setBookingreport(bookingReport bookingreport) {
        this.bookingreport = bookingreport;
    }
   
    }



