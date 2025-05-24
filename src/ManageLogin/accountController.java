/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageLogin;

/**
 *
 * @author dhiya
 */
import javax.swing.*;
import java.sql.*;
import Project.ConnectionProvider;

public class accountController {
   
    static loginUser login = new loginUser();
    
    public static int userID, adminID;
    private static String userEmail, userPass, userFName, userLName, userPhoneNum, userAddress;
    private static String adminEmail, adminPass, adminFName, adminLName, adminPhoneNum, adminAddress;
    
    public accountController(){}
    
    //returns password of logged in user based on user type
    //Called from updateUser.java interface to display user/admin password
    public static String getPass()
    {
        if (login.type.equals("User"))
            return userPass;
        else
            return adminPass;
    }
    
    //returns phone number of logged in user based on user type
    //Called from updateUser.java interface to display user/admin phone number
    public static String getPhonenum()
    {
        if (login.type.equals("User"))
            return userPhoneNum;
        else
            return adminPhoneNum;
    }
    
    //returns address of logged in user based on user type
    //Called from updateUser.java interface to display user/admin address
    public static String getAddress()
    {
        if (login.type.equals("User"))
            return userAddress;
        else
            return adminAddress;
    }
    
    //initializes this controller's variable to values from the login interface
    //Called from loginUser.java interface
    public static void userLoginInitialize(String email, String pass)
    {
        userEmail=email;
        userPass=pass;
    }
    
    //initializes this controller's variable to values from the login interface
    //Called from loginUser.java interface
    public static void adminLoginInitialize(String email, String pass)
    {
        adminEmail=email;
        adminPass=pass;
    }
    
    //initializes this controller's variable to values from the registration interface
    //Called from registerUser.java interface
    public static void userRegisterInitialize(String email, String pass, String fname, String lname, String phonenum, String address)
    {
        userEmail=email;
        userPass=pass;
        userFName=fname;
        userLName=lname;
        userPhoneNum=phonenum;
        userAddress=address;
    }
    
    //connects to SIS database and checks whether entered login details match any users in the user table.
    //Logs in user if match is found. Returns error and refreshes interface if otherwise.
    //Called from loginUser.java interface
    public static boolean verifyLoginUser()
    {
        try
        {
            Connection con = ConnectionProvider.getCon();
            PreparedStatement ps;

            ps = con.prepareStatement("SELECT `userEmail`, `userPass`, `userID`, `userPhoneNum`, `userAddress` FROM `user` WHERE `userEmail` = ? AND `userPass` = ?");
            ps.setString(1, userEmail);
            ps.setString(2, userPass);
            
            ResultSet result = ps.executeQuery();
            
            if (result.next()){
                JOptionPane.showMessageDialog(null,"Login Successful");
                userID=result.getInt("userID");
                userPhoneNum=result.getString("userPhoneNum");
                userAddress=result.getString("userAddress");
                
                return true;
            }
            else{
                JOptionPane.showMessageDialog(null,"Invalid Email or Password");
                return false;
            }
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
        
        return false;
    }
    
    //connects to SIS database and checks whether entered login details match any admins in the admin table.
    //Logs in admin if match is found. Returns error and refreshes interface if otherwise.
    //Called from loginUser.java interface
    public static boolean verifyLoginAdmin()
    {
        try
        {
            Connection con = ConnectionProvider.getCon();
            PreparedStatement ps;

            ps = con.prepareStatement("SELECT `adminEmail`, `adminPass`, `adminID`, `adminPhoneNum`, `adminAddress` FROM `admin` WHERE `adminEmail` = ? AND `adminPass` = ?");
            ps.setString(1, adminEmail);
            ps.setString(2, adminPass);
            
            ResultSet result = ps.executeQuery();
            
            if (result.next()){
                JOptionPane.showMessageDialog(null,"Login Successful");
                adminID=result.getInt("adminID");
                adminPhoneNum=result.getString("adminPhoneNum");
                adminAddress=result.getString("adminAddress");
                
                //JOptionPane.showMessageDialog(null,"userPhoneNum: "+adminPhoneNum+" userAddress: "+adminAddress);
                return true;
            }
            else{
                JOptionPane.showMessageDialog(null,"Invalid Email or Password");
                return false;
            }
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
        return false;
    }
    
    //connects to SIS database and enters new user details into the user table.
    //Returns the login interface if registration successful. Returns error and refreshes interface if otherwise.
    //Called from registerUser.java interface
    public static boolean verifyRegisterUser()
    {
        try
        {
            Connection con = ConnectionProvider.getCon();
            Statement st2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
            ResultSet rs=st2.executeQuery("SELECT max(userID) FROM user");
            
            if (rs.first())
            {
                int id=rs.getInt(1);
                id=id+1;
                userID=id;
            }
            else
            {
                userID=1;
            }

            Statement st = con.createStatement();
            st.executeUpdate("INSERT INTO user VALUES('"+userID+"','"+userEmail+"','"+userPass+"','"+userFName+"','"+userLName+"','"+userPhoneNum+"','"+userAddress+"')");
            JOptionPane.showMessageDialog(null, "Successfully Registered");
            return true;
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
        
        return false;
    }
    
    //connects to SIS database and updates user/admin details into the user/admin table.
    //Returns the home page if update successful. Returns error and refreshes interface if otherwise.
    //Called from updateUser.java interface
    public static boolean updateAccount(String password, String phonenum, String address)
    {
        if (login.type.equals("User"))
        {
            try
            {
                Connection con = ConnectionProvider.getCon();
                Statement st = con.createStatement();
                st.executeUpdate("UPDATE user SET userPass='"+password+"', userPhoneNum='"+phonenum+"', userAddress='"+address+"' WHERE userID='"+userID+"'");
                JOptionPane.showMessageDialog(null, "Successfully Updated");
                return true;
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,e);
                return false;
            }
        }
        else if (login.type.equals("Admin"))
        {
            try
            {
                Connection con = ConnectionProvider.getCon();
                Statement st = con.createStatement();
                st.executeUpdate("UPDATE admin SET adminPass='"+password+"', adminPhoneNum='"+phonenum+"', adminAddress='"+address+"' WHERE adminID='"+adminID+"'");
                JOptionPane.showMessageDialog(null, "Successfully Updated");
                return true;
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,e);
                return false;
            }
        }
        else
            return false;
        
    }
    
}
