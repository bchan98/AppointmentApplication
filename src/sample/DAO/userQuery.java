package sample.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userQuery {

    public static boolean verifyCredentials(String username, String password) throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        boolean flag = false;
        while(rs.next() && flag == false) {
            String usernameAuth = rs.getString("User_Name");
            String passwordAuth = rs.getString("Password");
            if(usernameAuth == username && passwordAuth == password){
                flag = true;
            }
        }
        return flag;
    }
}
