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
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * 生成增删改
 *
 * @author xurong
 */
public class GenCRUD {

    public static void start(String[] tables) throws Exception {
        new File("target/test/").mkdir();
        generateCRUD(tables, "loess", "");
    }

    private static void generateCRUD(final String[] tables, String module, String action)
            throws Exception {
        List<TableInfo> tiList = (List<TableInfo>) JdbcConnectionUtil.createJdbcTemplate().execute(new ConnectionCallback() {
            public Object doInConnection(Connection con) throws SQLException {
                TableMetaHelper metaHelper = new TableMetaHelper(con);
                return metaHelper.getTableInfo(tables, new String[]{"TABLE"});
            }
        });

        for (TableInfo info : tiList) {
            info.setTableName(info.getTableName());
            genCrud(info, module, action);
        }
    }

    public static void genCrud(TableInfo tableInfo, String module, String action)
            throws Exception {
        List<Map<String, String>> props = new ArrayList<Map<String, String>>();
        for (ColumnInfo columnInfo : tableInfo.getColumnInfos()) {
            Map<String, String> p = new HashMap<String, String>();
            p.put("label", StringUtils.defaultIfEmpty(columnInfo.getComment(), PropertyNameUtil
                    .getPropertyName(columnInfo.getColumnName())));
            p.put("name", PropertyNameUtil.getPropertyName(columnInfo.getColumnName()));
            props.add(p);
        }
        //
        Velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                "org.apache.velocity.runtime.log.Log4JLogChute");
        Velocity.setProperty("runtime.log.logsystem.log4j.logger", "root");
        Properties p = new Properties();
        p.load(GenCRUD.class.getResourceAsStream("/velocity.properties"));
        Velocity.init(p);
        VelocityContext context = new VelocityContext();
        context.put("title", StringUtils.defaultIfEmpty(tableInfo.getComment(), PropertyNameUtil
                .getPropertyName(tableInfo.getTableName())));
        context.put("modelName", PropertyNameUtil.getJavaPropertyName(tableInfo.getTableName()));
        context.put("className", PropertyNameUtil.getClassName(tableInfo.getTableName()));
        context.put("properties", props);
        context.put("dollar", "$");
        context.put("action", action);
        context.put("module", module);
        context.put("package_pref", Parameters.TARGET_PACKAGE);
        context.put("package_model", PathUtil.getModelPath());
        context.put("package_dao", PathUtil.getDaoPath());
        context.put("package_daoImpl", PathUtil.getDaoImplPath());
        context.put("nameUtils", new PropertyNameUtil());
        Template templateDao = Velocity.getTemplate("template/crud/dao.vm");
        Template templateDaoImpl = Velocity.getTemplate("template/crud/daoImpl.vm");

        Writer writerDao = new FileWriter(new File(PathUtil.getPackagePath(Parameters.DAO_TARGET_PACKAGE),
                PropertyNameUtil.getClassName(tableInfo.getTableName()) + "Dao.java"));
        Writer writerDaoImpl = new FileWriter(new File(PathUtil.getPackagePath(Parameters.DAO_IMPL_TARGET_PACKAGE),
                PropertyNameUtil.getClassName(tableInfo.getTableName()) + "DaoImpl.java"));
        templateDao.merge(context, writerDao);
        writerDao.close();
        templateDaoImpl.merge(context, writerDaoImpl);
        writerDaoImpl.close();
    }


    @Deprecated
    public void old(TableInfo tableInfo, String module, String action, boolean order, String prefix, String suffix) throws Exception {
        VelocityContext context = new VelocityContext();
        Template templateEdit = Velocity.getTemplate("template/crud/edit.vm");
        Template templateIndex = Velocity.getTemplate("template/crud/index.vm");
        Template templateController = Velocity.getTemplate("template/crud/controller.vm");
        Template templateService = Velocity.getTemplate("template/crud/service.vm");
        Template templateServiceImpl = Velocity.getTemplate("template/crud/serviceImpl.vm");
        Template templateManager = Velocity.getTemplate("template/crud/manager.vm");
        Template templateManagerImpl = Velocity.getTemplate("template/crud/managerImpl.vm");
        Template templateDao = Velocity.getTemplate("template/crud/dao.vm");
        Template templateDaoImpl = Velocity.getTemplate("template/crud/daoImpl.vm");
        Template templateDaoImplTest = Velocity.getTemplate("template/crud/daoImplTest.vm");
        ////////////
        Writer writerEdit = new FileWriter(new File("target/ftl/edit.ftl"));
        Writer writerIndex = new FileWriter(new File("target/ftl/index.ftl"));
        Writer writerController = new FileWriter(new File("target/java/"
                + PropertyNameUtil.getClassName(tableInfo.getTableName()) + "Action.java"));
        Writer writerService = new FileWriter(new File("target/java/"
                + PropertyNameUtil.getClassName(tableInfo.getTableName()) + "Service.java"));
        Writer writerServiceImpl = new FileWriter(new File("target/java/impl/"
                + PropertyNameUtil.getClassName(tableInfo.getTableName()) + "ServiceImpl.java"));
        Writer writerManager = new FileWriter(new File("target/java/"
                + PropertyNameUtil.getClassName(tableInfo.getTableName()) + "Repository.java"));
        Writer writerManagerImpl = new FileWriter(new File("target/java/impl/"
                + PropertyNameUtil.getClassName(tableInfo.getTableName()) + "RepositoryImpl.java"));
        Writer writerDao = new FileWriter(new File("target/java/"
                + PropertyNameUtil.getClassName(tableInfo.getTableName()) + "Dao.java"));
        Writer writerDaoImpl = new FileWriter(new File("target/java/impl/"
                + PropertyNameUtil.getClassName(tableInfo.getTableName()) + "DaoImpl.java"));
        Writer writerDaoImplTest = new FileWriter(new File("target/java/impl/"
                + PropertyNameUtil.getClassName(tableInfo.getTableName()) + "DaoImplTest.java"));
        templateEdit.merge(context, writerEdit);
        writerEdit.close();
        templateIndex.merge(context, writerIndex);
        writerIndex.close();
        templateController.merge(context, writerController);
        writerController.close();
        templateService.merge(context, writerService);
        writerService.close();
        templateServiceImpl.merge(context, writerServiceImpl);
        writerServiceImpl.close();
        templateManager.merge(context, writerManager);
        writerManager.close();
        templateManagerImpl.merge(context, writerManagerImpl);
        writerManagerImpl.close();
        templateDao.merge(context, writerDao);
        writerDao.close();
        templateDaoImpl.merge(context, writerDaoImpl);
        writerDaoImpl.close();
        templateDaoImplTest.merge(context, writerDaoImplTest);
        writerDaoImplTest.close();
        FileUtils.copyFileToDirectory(new File("src/start/resources/template/crud/sample/TestModelDataInitUtils.java"), new File("target/java/impl"));
    }
}
