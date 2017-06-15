package com.loess.tools.gen;

import com.loess.tools.config.Parameters;
import com.loess.tools.util.JdbcConnectionUtil;
import com.loess.tools.util.PropertyNameUtil;
import com.raddle.jdbc.callback.ConnectionCallback;
import com.raddle.jdbc.meta.table.TableInfo;
import com.raddle.jdbc.meta.table.TableMetaHelper;

import java.sql.SQLException;
import java.util.List;


/**
 * Hello world!
 *
 * @author xurong
 */
public class Logger {

    public static void print(final String[] tables) {

        List<TableInfo> tiList = (List<TableInfo>) JdbcConnectionUtil.createJdbcTemplate().execute(new ConnectionCallback() {

            public Object doInConnection(java.sql.Connection con) throws SQLException {
                TableMetaHelper metaHelper = new TableMetaHelper(con);
                return metaHelper.getTableInfo(tables, new String[]{"TABLE"});
            }
        });

        System.out.println();
        System.out.println("----------------generate success-----------------");
        System.out.println();
        for (TableInfo info : tiList) {
            info.setTableName(info.getTableName());
            System.out.println(Parameters.MAPPER_TARGET_PATH + "/" + PropertyNameUtil.getClassName(info.getTableName()) + "Mapper.xml");
            System.out.println(Parameters.MAPPER_EXT_TARGET_PATH + "/" + PropertyNameUtil.getClassName(info.getTableName()) + "MapperExt.xml");
        }

    }
}
