package Beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

@ManagedBean
public class AuthenticationBean {
    private String loginName;
    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirmation;
    
    @Resource(name="jdbc/test")
    private DataSource dataSource;

    public String changePassword() throws SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        
        //1)Checking whether both new password and password confirmation are the same or not
        if (!this.newPassword.equals(this.newPasswordConfirmation))
            context.addMessage(null, new FacesMessage("The new password doesn't match with the new password confirmation! Try again."));
        
        //2)Checking whether the new password is equal to the current one or not
        else if (this.newPassword.equals(this.currentPassword))
            context.addMessage(null, new FacesMessage("The current password and the new one can't be the same! Try again."));
        else {
            Connection connection=null;
            try {
                if(this.dataSource==null) throw new SQLException("Can't get data source");
                
                connection = this.dataSource.getConnection();
                if(connection==null) throw new SQLException("Can't get database connection");
                
                //3)Does the password in the form match with the one in the database?
                if (!this.isPasswordValid(connection))
                    context.addMessage(null, new FacesMessage("The current password is invalid! Try again."));
                else
                {
                    // Password update in the database
                    this.updatePassword(connection);
                    context.addMessage(null, new FacesMessage("Password successfully updated for '" + this.loginName +"'."));
                }
            } finally {
                if (connection!=null) connection.close();
            }
        }
        return "change_password";
    }
        
    private Boolean isPasswordValid(Connection connection) throws SQLException {
        // Querying the user's password in the table 'users'...
        PreparedStatement statement = connection.prepareStatement(
                   "select password from users where login_name = ?"); 
        statement.setString(1, this.loginName);
        ResultSet result =  statement.executeQuery();
        if (result.next()) {
            String dbCryptedPassword = result.getString("password");
            statement.close();
            
            String cryptedCurrentPassword = this.getCryptedPassword(this.currentPassword);
            
            //Logging crypted password values to be compared... 
            Logger.getLogger(AuthenticationBean.class.getName()).log(Level.INFO,
                    "Current password once encrypted for {0}: {1}", new Object[]{this.loginName, cryptedCurrentPassword});
            Logger.getLogger(AuthenticationBean.class.getName()).log(Level.INFO,
                    "Password in DB for {0}: {1}", new Object[]{this.loginName, dbCryptedPassword});

            //Both current and DB passwords are the same?
            if (cryptedCurrentPassword.equals(dbCryptedPassword))
                return true;
            else return false;
        } 
        else {
            Logger.getLogger(AuthenticationBean.class.getName()).log(Level.WARNING,
                    "User not found in the table USERS for the login name ''{0}''!", this.loginName);        
            return false;
        }
    }
    
    private String getCryptedPassword(String notCryptedPassword) {
        MessageDigest md=null;
        try {
            md = MessageDigest.getInstance("SHA256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthenticationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (md == null)
            return notCryptedPassword;
        
        md.update(notCryptedPassword.getBytes());
 
        byte input[] = md.digest();
     
        // Convert the byte variable to hexadecimal format
        StringBuilder hexaString = new StringBuilder();
    	for (int i=0;i<input.length;i++) {
    		String hexaChar=Integer.toHexString(0xff & input[i]);
   	     	if(hexaChar.length()==1) hexaString.append('0');
   	     	hexaString.append(hexaChar);
    	}
        return hexaString.toString();
    }
    
    private void updatePassword(Connection connection) throws SQLException {
        // Updating the user's crypted password in the table 'users'...
        PreparedStatement statement = connection.prepareStatement(
                   "update users set password = ? where login_name = ?"); 
        statement.setString(1, this.getCryptedPassword(this.newPassword));
        statement.setString(2, this.loginName);
        statement.executeUpdate();
        statement.close();
    }
    
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }
    
}
