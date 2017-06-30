/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logixity.apps.newpopular.db;

import com.logixity.apps.newpopular.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ahmed
 */
public class Users {
    public static final String TABLE = "user";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_USER_UNAME = "user_uname";
    public static final String COL_USER_PWD = "user_pwd";
    public static final String COL_USER_FULLNAME = "user_fullname";
    public static final String COL_TYPE = "type";
    
    
    public static User isValidUser(String uname,String pass) throws SQLException{
        ResultSet rs = GenericDB.query(TABLE, null, COL_USER_UNAME+" = ? AND "+COL_USER_PWD+" = ?", new Object[]{uname,pass}, null);
        User user = null;
        if(rs.next()){
            user = new User();
            user.setUserId(rs.getInt(COL_USER_ID));
            user.setFullName(rs.getString(COL_USER_FULLNAME));
            user.setUsername(rs.getString(COL_USER_UNAME));
            user.setPassword(rs.getString(COL_USER_PWD));
            user.setType(rs.getString(COL_TYPE));
        }
        return user;
    }
}
