package com.loess.tools.gen;

import com.loess.tools.config.Parameters;
import com.loess.tools.util.JdbcConnectionUtil;
import com.loess.tools.util.PathUtil;
import com.loess.tools.util.PropertyNameUtil;
import com.raddle.jdbc.callback.ConnectionCallback;
import com.raddle.jdbc.meta.table.TableInfo;
import com.raddle.jdbc.meta.table.TableMetaHelper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 生成model
 *
 * @author xurong
 */
public class GenAdv {

    public static void start(final String[] tables) {
        try {
            Velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                    "org.apache.velocity.runtime.log.Log4JLogChute");
            Velocity.setProperty("runtime.log.logsystem.log4j.logger", "root");
            Properties p = new Properties();
            p.load(GenAdv.class.getResourceAsStream("/velocity.properties"));
            Velocity.init(p);

            List<TableInfo> tiList = (List<TableInfo>) JdbcConnectionUtil.createJdbcTemplate().execute(new ConnectionCallback() {

                public Object doInConnection(Connection con) throws SQLException {
                    TableMetaHelper metaHelper = new TableMetaHelper(con);
                    return metaHelper.getTableInfo(tables, "loess", new String[]{"TABLE"});
                }
            });
            for (TableInfo tableInfo : tiList) {
                tableInfo.setTableName(tableInfo.getTableName());
                VelocityContext context = new VelocityContext();
                context.put("package", PathUtil.getModelPath()); //com.woyo.business.enterprisercenter.domain.domain
                context.put("qoPath", PathUtil.getAdvModelPath()); //com.woyo.business.enterprisercenter.domain.qo
                context.put("tableInfo", tableInfo);
                context.put("package_model", PathUtil.getModelPath());
                context.put("nameUtils", new PropertyNameUtil());
                Template template = null;
                template = Velocity.getTemplate("template/domain/adv.vm");
                Writer writer = new OutputStreamWriter(new FileOutputStream(new File(PathUtil.getPackagePath(Parameters.ADV_MODEL_TARGET_PACKAGE), PropertyNameUtil
                        .getClassName(tableInfo.getTableName())
                        + "Adv.java")), "utf-8");
                template.merge(context, writer);
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
