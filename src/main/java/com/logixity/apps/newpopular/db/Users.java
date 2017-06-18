/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logixity.apps.newpopular.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ahmed
 */
public class Users {
    public static final String TABLE = "users";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_USER_NAME = "user_name";
    public static final String COL_USER_PASS = "user_pass";
    
    public static boolean isValidUser(String uname,String pass) throws SQLException{
        ResultSet rs = GenericDB.query(TABLE, null, COL_USER_NAME+" = ? AND "+COL_USER_PASS+" = ?", new Object[]{uname,pass}, null);
        return rs.next();
    }
}
