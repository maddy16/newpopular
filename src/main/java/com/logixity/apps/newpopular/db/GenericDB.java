/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logixity.apps.newpopular.db;

import java.sql.ResultSet;
import java.sql.Statement;
import static com.logixity.apps.newpopular.db.DatabaseHandler.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author ahmed
 */
public class GenericDB {

    static String valQry;
    static String metaQry;
    static String setClause;

    public static boolean hasRecords(String sql) {
        boolean tableExists = false;
        Statement stmt = null;
        try {
            connect();
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            tableExists = rs.next();
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return tableExists;
    }

    static boolean executeUpdate(String sql) throws SQLException {
        boolean updated = false;
        Statement stmt = null;
        try {
            connect();
            stmt = con.createStatement();
            int updateCount = stmt.executeUpdate(sql);
            updated = updateCount > 0;
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                throw ex;
            }

        }
        return updated;
    }

    static boolean insert(String tableName, Map<String, Object> values) throws SQLException {
        return executeUpdate(generateInsertQry(tableName, values));
    }

    static int insertReturningId(String tableName, Map<String, Object> values) throws SQLException {
        int lastInsertId = 0;
        Statement stmt = null;
        try {
            connect();
            stmt = con.createStatement();
            int updateCount = stmt.executeUpdate(generateInsertQry(tableName, values),Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                lastInsertId = rs.getInt(1);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                throw ex;
            }

        }
        return lastInsertId;
    }

    static String generateInsertQry(String tableName, Map<String, Object> values) {
        metaQry = "INSERT INTO " + tableName + "(";
        valQry = " VALUES(";
        values.keySet().forEach(key -> {
            Object value = values.get(key);
            metaQry += key;
            if (value instanceof String) {
                valQry += "'" + (String) value + "'";
            } else {
                valQry += value;
            }
            metaQry += ",";
            valQry += ",";
        });
        metaQry = metaQry.substring(0, metaQry.length() - 1) + ")";
        valQry = valQry.substring(0, valQry.length() - 1) + ")";
        String qry = metaQry + valQry;
        return qry;
    }

    static public boolean update(String table, Map<String, Object> values, String[] pKey, Object[] pKeyValue) throws SQLException {
        return executeUpdate(generateUpdateQry(table, values, pKey, pKeyValue));
    }

    static boolean delete(String tableName, String pKey, Object pKeyValue) throws SQLException {
        return executeUpdate(generateDeleteQry(tableName, pKey, pKeyValue));
    }

    static String generateDeleteQry(String tableName, String pKey, Object pKeyValue) {
        String qry = "DELETE FROM " + tableName + " WHERE " + pKey + " = ";
        if (pKeyValue instanceof String) {
            qry += "'" + (String) pKeyValue + "'";
        } else {
            qry += pKeyValue;
        }
        return qry;
    }

    static String generateUpdateQry(String table, Map<String, Object> values, String[] pKeys, Object[] pKeyValues) {
        String qry = "UPDATE " + table;
        setClause = " SET ";
        String whereClause = " WHERE ";
        values.keySet().forEach(key -> {
            Object value = values.get(key);
            setClause += key + " = ";
            if (value instanceof String) {
                setClause += "'" + (String) value + "'";
            } else {
                setClause += value;
            }
            setClause += ",";
        });
        setClause = setClause.substring(0, setClause.length() - 1);

        for (int i = 0; i < pKeys.length; i++) {
            if (i == 0) {
                whereClause += pKeys[i] + " = ";
            } else {
                whereClause += " AND " + pKeys[i] + " = ";
            }
            if (pKeyValues[i] instanceof String) {
                whereClause += "'" + (String) pKeyValues[i] + "'";
            } else {
                whereClause += pKeyValues[i];
            }
        }

        return qry + setClause + whereClause;
    }

    static ResultSet get(String sql) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connect();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw e;
        }
        return rs;
    }

    static ResultSet getByTableName(String table, String orderBy) throws SQLException {
        String sql = "SELECT * FROM " + table + " ORDER BY " + orderBy;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            connect();
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            throw e;
        }
        return rs;
    }

    static ResultSet query(String tableName, String[] cols, String whereClause, Object[] whereVals, String orderBy) throws SQLException {
        String sql = "SELECT ";
        if (cols != null && cols.length > 0) {
            for (String col : cols) {
                sql += col + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
        } else {
            sql += "*";
        }
        sql += " FROM " + tableName + " ";
        if (whereClause != null) {
            sql += "WHERE " + whereClause;
        }
        if (orderBy != null) {
            sql += " ORDER BY " + orderBy;
        }
        connect();
        PreparedStatement stmt = con.prepareStatement(sql);
        int i = 1;
        if (whereVals != null && whereVals.length > 0) {
            for (Object value : whereVals) {
                if (value instanceof String) {
                    stmt.setString(i++, (String) value);
                } else {
                    stmt.setObject(i++, value);
                }
            }
        }

        return stmt.executeQuery();
    }
}
