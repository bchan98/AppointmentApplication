package sample.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userQuery {

    /** This method verifies login credentials in the database. Username and passwords are compared to values found in database, with matching results returning a boolean true.
     *
     * @param username The username to be checked.
     * @param password The password to be checked.
     * @return Returns true or false depending on successful login.
     * @throws SQLException
     */
    public static boolean verifyCredentials(String username, String password) throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        boolean flag = false;
        while(rs.next() && flag == false) {
            String usernameAuth = rs.getString("User_Name");
            String passwordAuth = rs.getString("Password");
            if(usernameAuth.equals(username) && passwordAuth.equals(password)){
                flag = true;
            }
        }
        return flag;
    }
}
