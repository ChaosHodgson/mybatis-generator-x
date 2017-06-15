package com.loess.tools.gen;

import com.loess.tools.config.Parameters;
import com.loess.tools.util.JdbcConnectionUtil;
import com.loess.tools.util.PathUtil;
import com.loess.tools.util.PropertyNameUtil;
import com.raddle.jdbc.callback.ConnectionCallback;
import com.raddle.jdbc.meta.table.ColumnInfo;
import com.raddle.jdbc.meta.table.TableInfo;
import com.raddle.jdbc.meta.table.TableMetaHelper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 * @author xurong
 */
public class GenMapper {


    public static void start(final String[] tables) throws Exception {

        new File(Parameters.MAPPER_TARGET_PATH).mkdirs();
        FileUtils.cleanDirectory(new File(Parameters.MAPPER_TARGET_PATH));
        new File(Parameters.MAPPER_EXT_TARGET_PATH).mkdirs();
        FileUtils.cleanDirectory(new File(Parameters.MAPPER_EXT_TARGET_PATH));

        List<TableInfo> tiList = (List<TableInfo>) JdbcConnectionUtil.createJdbcTemplate().execute(new ConnectionCallback() {
            public Object doInConnection(java.sql.Connection con) throws SQLException {
                TableMetaHelper metaHelper = new TableMetaHelper(con);
                return metaHelper.getTableInfo(tables, new String[]
                        {
                                "TABLE"
                        });
            }
        });

        for (TableInfo info : tiList) {
            info.setTableName(info.getTableName());
            genOne(info, "loess", "");
        }
    }

    public static void genOne(TableInfo tableInfo, String module, String action) throws Exception {
        Map<Object, Object> root = new HashMap<>();
        // 类属性和列名
        List<Map<Object, Object>> props = new ArrayList<>();
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            Map<Object, Object> m = new HashMap<Object, Object>();
            m.put("name", PropertyNameUtil.getPropertyName(columnInfo.getColumnName(), columnInfo.getColumnTypeName()));
            m.put("propertyTypeName", PropertyNameUtil.getJavaType(columnInfo));
            m.put("columnName", columnInfo.getColumnName());
            m.put("columnTypeName", "INT".equals(columnInfo.getColumnTypeName()) ? "INTEGER" : columnInfo.getColumnTypeName());

            props.add(m);
        }
        root.put("properties", props);
        // 表名
        root.put("tableName", tableInfo.getTableName());
        root.put("className", PathUtil.getModelPath() + "." + PropertyNameUtil.getClassName(tableInfo.getTableName()));
        root.put("simpleClassName", PropertyNameUtil.getClassName(tableInfo.getTableName()));
        root.put("beanName", StringUtils.uncapitalize(PropertyNameUtil.getClassName(tableInfo.getTableName())));
        root.put("id", "id");
        root.put("nameUtils", new PropertyNameUtil());
        root.put("fields", "fields");
        root.put("tables", "tables");
        root.put("conditions", "conditions");

        VelocityContext context = new VelocityContext();
        for (Object key : root.keySet()) {
            context.put(key.toString(), root.get(key));
        }
        org.apache.velocity.Template template = Velocity.getTemplate("template/mybatis/Mapper.vm");
        File file = new File("target/mybatis/mapper",
                PropertyNameUtil.getClassName(tableInfo.getTableName()) + "Mapper" + ".xml");
        FileWriter writer = new FileWriter(file);
        template.merge(context, writer);
        writer.flush();

        template = Velocity.getTemplate("template/mybatis/MapperExt.vm");
        file = new File("target/mybatis/mapper/extend",
                PropertyNameUtil.getClassName(tableInfo.getTableName()) + "MapperExt" + ".xml");
        writer = new FileWriter(file);
        template.merge(context, writer);
        writer.flush();

       /* template = Velocity.getTemplate("template/mybatis/MapperNoAdv.vm");
        file = new File("target/mybatis/mapper/",
                PropertyNameUtil.getClassName(tableInfo.getTableName()) + "MapperNoAdv" + ".xml");
        writer = new FileWriter(file);
        template.merge(context, writer);
        writer.flush();*/
        writer.close();
    }

    private static String getColumnName(String propName) {
        char[] chars = propName.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c) && i > 0) {
                sb.append('_').append(c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString().toLowerCase(); //.toUpperCase();
    }

    private static String getSqlMapName(String simpleClassName) {
        char[] chars = simpleClassName.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c) && i > 0) {
                sb.append('-').append(c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString().toLowerCase();
    }
}
