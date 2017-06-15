package com.loess.tools.util;

import com.raddle.jdbc.meta.table.ColumnInfo;
import com.raddle.jdbc.meta.table.TableInfo;

import java.io.File;

import static com.loess.tools.config.Parameters.*;

/**
 * Do
 *
 * @author ChaosHodgson
 * @date 2017/6/14
 * @since 1.0
 */
public class PathUtil {

    public static String getModelPath() {
        return TARGET_PACKAGE + "." + MODEL_TARGET_PACKAGE;
    }

    public static String getAdvModelPath() {
        return TARGET_PACKAGE + "." + ADV_MODEL_TARGET_PACKAGE;
    }

    public static String getDaoPath() {
        return TARGET_PACKAGE + "." + DAO_TARGET_PACKAGE;
    }

    public static String getDaoImplPath() {
        return TARGET_PACKAGE + "." + DAO_IMPL_TARGET_PACKAGE;
    }

    public static boolean enableJavaField(TableInfo tableInfo, ColumnInfo columnInfo) {
        return !BizUtil.in(columnInfo.getColumnName(), "id");
    }

    public static File getPackagePath(String packageStr) {
        File dir = new File("target/" + packageStr.replaceAll("\\.", "/"));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}
