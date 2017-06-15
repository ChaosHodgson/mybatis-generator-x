package com.loess.tools.util;

import com.raddle.jdbc.meta.table.ColumnInfo;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyNameUtil {
    public static String getPropertyName(String columnName) {
        columnName = dbToJava(columnName);
        char[] chars = columnName.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_' && i < chars.length - 1) {
                sb.append(Character.toUpperCase(chars[i + 1]));
                i++;//
            } else if (c != '_') {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    public static String getPropertyName(String columnName, String columnType) {
        String javaType = getJavaType(columnType);

        if (" java.lang.Boolean".equals(javaType) && columnName.startsWith("is_")) {
            columnName = columnName.substring(columnName.indexOf("is_"));
        }

        return getPropertyName(columnName);
    }

    public static String getMethodNameSuffixFromColumnName(String columnName) {
        return getMethodNameSuffix(getPropertyName(columnName));
    }

    public static String getMethodNameSuffix(String propName) {
        if (Character.isUpperCase(propName.charAt(0))) {
            if (propName.length() == 1 || Character.isLowerCase(propName.charAt(1))) {
                throw new IllegalArgumentException("非法属性名[" + propName + "]");
            }
            return propName;
        } else {
            if (propName.length() > 1 && Character.isUpperCase(propName.charAt(1))) {
                return propName;
            } else {
                return StringUtils.capitalize(propName);
            }
        }
    }

    public static String getJavaPropertyName(String tableName) {
//    	tableName = tableName.substring(tableName.indexOf('_') + 1);
//    	tableName = tableName.substring(tableName.indexOf('_') + 1);
        return getPropertyName(tableName);
    }

    public static String getClassName(String tableName) {
        // 最笨的切两个'_'的办法 ^_^
//    	tableName = tableName.substring(tableName.indexOf('_') + 1);
//    	tableName = tableName.substring(tableName.indexOf('_') + 1);
        //去除'_',将后面的字符大写。
//    	String[] names=tableName.split("_");
//    	tableName=names[0];
//    	for (int i = 1; i < names.length; i++) {
//			tableName+=StringUtils.capitalize(names[i]);
//			
//		}
        return myClassNameFilter(StringUtils.capitalize(getPropertyName(tableName)));
    }

    //    public static String getClassName(String tableName, String prefix, String suffix) {
//        return  StringUtils.defaultString(prefix) + myClassNameFilter(StringUtils.capitalize(getPropertyName(tableName)))
//                + StringUtils.defaultString(suffix) ;
//    }
    protected static String myClassNameFilter(String name) {
//        if (name.equals("Orders")) {
//        	return "Order";
//        }
        return name;
    }

    public static String getTableName(String className) {
//        if (className.equals("order")) {
//        	return "orders";
//        }
        return className;
    }

    public static boolean isTypeIsString(String columnType) {
//    	System.out.print("String ");
        return BizUtil.in(columnType, "CHAR", "VARCHAR", "TEXT", "MEDIUMTEXT");
    }

    public static boolean hasField(List<Map<Object, Object>> props, String columnName) {
        for (Map<Object, Object> prop : props) {
            if (columnName.equals(prop.get("name"))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPrice(ColumnInfo colunmInfo) {
        return colunmInfo.getColumnName().endsWith("_price") && BizUtil.in(colunmInfo.getColumnTypeName(), "BIGINT");
    }

    public static boolean isTypeIsDate(String columnType) {
//    	System.out.print("Date ");
        return BizUtil.in(columnType, "DATETIME", "DATE", "TIMESTAMP");
    }

    public static boolean isTypeIsInteger(String columnType) {
//    	System.out.print("Int ");
        return BizUtil.in(columnType, "INT", "BIT", "SMALLINT", "TINYINT", "BIGINT", "BIGINT UNSIGNED", "INT UNSIGNED", "TINYINT UNSIGNED");
    }

    public static boolean isTypeIsNumber(String columnType) {
//    	System.out.print("Number ");
        return BizUtil.in(columnType, "INT", "SMALLINT", "TINYINT", "TINYINT UNSIGNED", /*"BIGINT",*/ "DECIMAL", "DOUBLE", "BIGINT UNSIGNED", "INT UNSIGNED");
    }
    // WOYO

    public static String getJavaType(String columnName) {
        if (BizUtil.in(columnName, "CHAR", "VARCHAR", "TEXT", "MEDIUMTEXT")) {
            return "java.lang.String";
        }
        if (BizUtil.in(columnName, "INT", "SMALLINT", "TINYINT", "TINYINT UNSIGNED")) {
            return "java.lang.Integer";
        }
        if (BizUtil.in(columnName, "DATETIME", "DATE", "TIMESTAMP")) {
            return "java.util.Date";
        }
        if (BizUtil.in(columnName, "BIGINT", "BIGINT UNSIGNED", "INT UNSIGNED")) {
            return "java.lang.Long";
        }
        if (BizUtil.in(columnName, "DOUBLE")) {
            return "java.lang.Double";
        }
        if (BizUtil.in(columnName, "FLOAT")) {
            return "java.lang.Float";
        }
        if (BizUtil.in(columnName, "BIT")) {
            return "java.lang.Integer";
        }
        if (BizUtil.in(columnName, "DECIMAL")) {
            return "java.math.BigDecimal";
        }

        return "Unknown[" + columnName + "]";
    }

    public static String getJavaType(ColumnInfo colunmInfo) {
        return getJavaType(colunmInfo.getColumnTypeName());
    }

    public static String getJavaType_Mysql(ColumnInfo colunmInfo) {
        String columnName = colunmInfo.getColumnTypeName();
        if (BizUtil.in(columnName, "CHAR", "VARCHAR", "TEXT", "MEDIUMTEXT")) {
            return "String";
        }
        if (BizUtil.in(columnName, "BIGINT", "BIGINT UNSIGNED", "INT UNSIGNED")) {
            return "Long";
        }
        if (BizUtil.in(columnName, "INT")) {
            return "Integer";
        }
        if (BizUtil.in(columnName, "DATETIME", "DATE", "TIMESTAMP")) {
            return "java.util.Date";
        }
        if (BizUtil.in(columnName, "SMALLINT", "TINYINT", "TINYINT UNSIGNED")) {
            return "Integer";
        }
        if (BizUtil.in(columnName, "DECIMAL")) {
            return "java.math.BigDecimal";
        }
        return "Unknown[" + columnName + "]";
    }
    // ORACLE

    public static String getJavaType_ORACLE(ColumnInfo colunmInfo) {
        if ("CHAR".equals(colunmInfo.getColumnTypeName()) || "VARCHAR".equals(colunmInfo.getColumnTypeName())
                || "VARCHAR2".equals(colunmInfo.getColumnTypeName())) {
            return "String";
        } else if ("DECIMAL".equals(colunmInfo.getColumnTypeName()) || "NUMBER".equals(colunmInfo.getColumnTypeName())
                || "INT".equals(colunmInfo.getColumnTypeName())) {
            if (colunmInfo.getScale() > 0) {
                return "java.math.BigDecimal";
            } else if (colunmInfo.getLength() < 10) {
                return "Integer";
            } else {
                return "Long";
            }
        } else if ("DATE".equals(colunmInfo.getColumnTypeName())) {
            return "java.util.Date";
        } else {
            return "Unknown[" + colunmInfo.getColumnTypeName() + "]";
        }
    }

    public static String dbToJava(String name) {
        String[] orgn = name.split("_");
        String[] res = new String[orgn.length];
        for (int i = 0; i < orgn.length; i++) {
            res[i] = dbToJavaRuleFunc(orgn[i]);
        }
        return StringUtils.join(res, "_");
    }

    private static String dbToJavaRuleFunc(String n) {
        String v = dbToJavaRule.get(n);
        return v != null ? v : n;
    }

    static Map<String, String> dbToJavaRule = new HashMap<String, String>();

    static {
        dbToJavaRule.put("ord", "order");
        dbToJavaRule.put("usr", "user");
        dbToJavaRule.put("aud", "audit");
        dbToJavaRule.put("conf", "configure");
        dbToJavaRule.put("stat", "status");
        dbToJavaRule.put("info", "info");
        dbToJavaRule.put("org", "org");
        dbToJavaRule.put("addr", "address");
        dbToJavaRule.put("msg", "msg");
        dbToJavaRule.put("his", "history");
        dbToJavaRule.put("da", "daily");
        dbToJavaRule.put("pic", "pic");
        dbToJavaRule.put("bat", "bat");
        dbToJavaRule.put("err", "err");
        dbToJavaRule.put("bak", "bak");
        dbToJavaRule.put("del", "delete");
        dbToJavaRule.put("upd", "update");
        dbToJavaRule.put("int", "insert");
        dbToJavaRule.put("mdfy", "modify");
        dbToJavaRule.put("cred", "creditcard");
        dbToJavaRule.put("mem", "member");
        dbToJavaRule.put("pwd", "pwd");
        dbToJavaRule.put("agt", "agent");
        //
        dbToJavaRule.put("desc", "desc");
        dbToJavaRule.put("cd", "code");
        dbToJavaRule.put("typ", "type");
//    	dbToJavaRule.put("", "");
//    	dbToJavaRule.put("", "");
//    	dbToJavaRule.put("", "");
//    	dbToJavaRule.put("", "");
//    	dbToJavaRule.put("", "");
//    	dbToJavaRule.put("", "");
//    	dbToJavaRule.put("", "");
//    	dbToJavaRule.put("", "");
//    	dbToJavaRule.put("", "");
//    	dbToJavaRule.put("", "");
//    	dbToJavaRule.put("", "");
    }
}
