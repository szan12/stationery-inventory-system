
 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageBooking;
import java.sql.*;
import Project.ConnectionProvider;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author huixu
 */
public class manageBookingController {
    private  viewBooking viewBook;
    private  reduceInventory reduceInventory;
    private updateBookingStatus updateBookingStatus;
    
    private  String userid,bookinvname,bookdate,bookstatus,invid;
    private int bookingid,bookinvquantity,invquantity,cartid;
    private float bookinvprice,booktotalprice;
    
    public  manageBookingController(String userID,int cartID,String bookInvName,String bookDate,String bookStatus,String invID,int bookingID,int bookInvQuantity, float bookInvPrice,float bookTotalPrice, int invQuantity){
    
   this.userid=userID;
   this.cartid= cartID;
    this.bookinvname=bookInvName;
    this.bookdate=bookDate;
    this.bookstatus=bookStatus;
    this.bookingid=bookingID;
    this.bookinvquantity=bookInvQuantity;
    this.bookinvprice=bookInvPrice;
    this.booktotalprice=bookTotalPrice;
    this.invid=invID;
    this.invquantity=invQuantity;
    }

    manageBookingController() {
       
    }
     //This method is to  get data from database and pass it to 'viewBooking' interface to view.
     public void viewBooking(){
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            
            ResultSet rs = st.executeQuery("Select d.bookInvName as 'Inventory Name', d.bookInvQuantity as Quantity, d.bookInvPrice as 'Unit Price', d.bookTotalPrice as 'Total Price', d.bookingID as bookingID, c.cartID as cartID, c.userID as userID, b.bookdate as 'booking date', b.bookstatus as status FROM bookingdetail as d INNER JOIN booking as b ON b.bookingID=d.bookingID INNER JOIN cart as c ON b.cartID=c.cartID");
            getViewBook().jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
            System.out.println(e);
        }
    }
    //This method is to get the variables (invID, reduce quantity) form reduceInventory interface then reduce and update the quantity .
     public  void reduceInventory(String invid, int reduce){
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT invQuantity FROM inventory where invID='"+invid+"'");
            if(rs.next())
            {
                invquantity = rs.getInt("invQuantity");

                if(invquantity >= reduce)
                {   
                    int newinvquantity = invquantity - reduce;
                    Statement qr = con.createStatement();
                    qr.executeUpdate("UPDATE inventory SET invQuantity="+newinvquantity+" where invID='"+invid+"'");
                    JOptionPane.showMessageDialog(null, "Reduce successfully");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Error : Lack of Inventory ");
                }
           
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error : Recheck Inventory ID");
            }
          
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
     }   
     //This method is to get the variables (bookingID, bookStatus) from updateBookingStatus interface then insert booking ID and update the booking status .
    public  void updateBookingStatus(int bookingid,String bookstatus){
        try
        {
            Connection con = ConnectionProvider.getCon();
          
            Statement qr = con.createStatement();
            ResultSet ab = qr.executeQuery("SELECT bookingID FROM booking  where bookingID='"+bookingid+"' ");
            if(ab.next())
            {
                bookingid = ab.getInt("bookingID");
                Statement rq = con.createStatement();
                rq.executeUpdate("UPDATE booking SET bookStatus='"+bookstatus+"' where bookingID='"+bookingid+"' ");
                JOptionPane.showMessageDialog(null, "Update successfully"); 
                getUpdateBookingStatus().setVisible(false);
                new updateBookingStatus().setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error : Wrong Booking ID !");
            }
         
        }
        catch(Exception e)
        {
      
            JOptionPane.showMessageDialog(null, e);
        }
        
    }

    /**
     * @return the viewBook
     */
    public viewBooking getViewBook() {
        return viewBook;
    }

    /**
     * @param viewBook the viewBook to set
     */
    public void setViewBook(viewBooking viewBook) {
        this.viewBook = viewBook;
    }

    /**
     * @return the reduceInventory
     */
    public reduceInventory getReduceInventory() {
        return reduceInventory;
    }

    /**
     * @param reduceInventory the reduceInventory to set
     */
    public void setReduceInventory(reduceInventory reduceInventory) {
        this.reduceInventory = reduceInventory;
    }

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return the cartid
     */
    public int getCartid() {
        return cartid;
    }

     public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    /**
     * @return the bookinvname
     */
    public String getBookinvname() {
        return bookinvname;
    }

    /**
     * @param bookinvname the bookinvname to set
     */
    public void setBookinvname(String bookinvname) {
        this.bookinvname = bookinvname;
    }

    /**
     * @param bookdate the bookdate to set
     */
    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    /**
     * @return the bookstatus
     */
    public String getBookstatus() {
        return bookstatus;
    }

    /**
     * @param bookstatus the bookstatus to set
     */
    public void setBookstatus(String bookstatus) {
        this.bookstatus = bookstatus;
    }

    /**
     * @return the invid
     */
    public String getInvid() {
        return invid;
    }

    /**
     * @param invid the invid to set
     */
    public void setInvid(String invid) {
        this.invid = invid;
    }

    /**
     * @return the bookingid
     */
    public int getBookingid() {
        return bookingid;
    }

    /**
     * @param bookingid the bookingid to set
     */
    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    /**
     * @return the bookinvquantity
     */
    public int getBookinvquantity() {
        return bookinvquantity;
    }

    /**
     * @param bookinvquantity the bookinvquantity to set
     */
    public void setBookinvquantity(int bookinvquantity) {
        this.bookinvquantity = bookinvquantity;
    }

    /**
     * @return the invquantity
     */
    public int getInvquantity() {
        return invquantity;
    }

    /**
     * @param invquantity the invquantity to set
     */
    public void setInvquantity(int invquantity) {
        this.invquantity = invquantity;
    }

    /**
     * @return the bookinvprice
     */
    public float getBookinvprice() {
        return bookinvprice;
    }

    /**
     * @param bookinvprice the bookinvprice to set
     */
    public void setBookinvprice(float bookinvprice) {
        this.bookinvprice = bookinvprice;
    }

    /**
     * @return the booktotalprice
     */
    public float getBooktotalprice() {
        return booktotalprice;
    }

    /**
     * @param booktotalprice the booktotalprice to set
     */
    public void setBooktotalprice(float booktotalprice) {
        this.booktotalprice = booktotalprice;
    }

    /**
     * @return the bookdate
     */
    public String getBookdate() {
        return bookdate;
    }

    void setviewBook() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the updateBookingStatus
     */
    public updateBookingStatus getUpdateBookingStatus() {
        return updateBookingStatus;
    }

    /**
     * @param updateBookingStatus the updateBookingStatus to set
     */
    public void setUpdateBookingStatus(updateBookingStatus updateBookingStatus) {
        this.updateBookingStatus = updateBookingStatus;
    }

    

    

   
  } 
    
   
    

    
