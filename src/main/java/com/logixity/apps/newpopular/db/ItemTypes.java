/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.logixity.apps.newpopular.db;

import com.logixity.apps.newpopular.models.ItemType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ahmed
 */
public class ItemTypes {

    public static final String TABLE = "item_type";
    public static final String COL_TYPE_ID = "type_id";
    public static final String COL_TYPE_NAME = "type_name";

    public static boolean addNewItemType(ItemType type) throws SQLException {
        return GenericDB.insert(TABLE, getValuesMap(type));
    }

    public static List<ItemType> getAllTypes() throws SQLException {
        ResultSet rs = GenericDB.query(TABLE, null, null, null, null);
        List<ItemType> list = new ArrayList<>();
        while (rs.next()) {
            ItemType type = new ItemType();
            type.setTypeId(rs.getInt(COL_TYPE_ID));
            type.setTypeName(rs.getString(COL_TYPE_NAME));
            list.add(type);
        }
        return list;
    }

    public static boolean updateItemType(ItemType type) throws SQLException {
        return GenericDB.update(TABLE, getValuesMap(type), new String[]{COL_TYPE_ID}, new Object[]{type.getTypeId()});
    }

    public static boolean deleteItemType(int typeId) throws SQLException {
        return GenericDB.delete(TABLE, COL_TYPE_ID, typeId);
    }

    public static ItemType hasSameType(String typeName) throws SQLException {
        ItemType type = null;
        ResultSet rs = GenericDB.query(typeName, null, COL_TYPE_NAME + " = ?", new Object[]{typeName}, null);
        if (rs.next()) {
            type = new ItemType();
            type.setTypeId(rs.getInt(COL_TYPE_ID));
            type.setTypeName(rs.getString(COL_TYPE_NAME));
        }
        return type;
    }

    private static Map<String, Object> getValuesMap(ItemType type) {
        Map<String, Object> values = new HashMap<>();
        values.put(COL_TYPE_ID, type.getTypeId());
        values.put(COL_TYPE_NAME, type.getTypeName());
        return values;
    }
}
