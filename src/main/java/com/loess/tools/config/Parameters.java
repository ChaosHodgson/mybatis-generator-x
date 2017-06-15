package com.loess.tools.config;

/**
 * Do
 *
 * @author ChaosHodgson
 * @date 2017/6/14
 * @since 1.0
 */
public class Parameters {
    /*target package root in your project */
    public static final String TARGET_PACKAGE = "zwfw.express.loess";
    /*target package domain after root package */
    public static final String MODEL_TARGET_PACKAGE = "domain";
    /*target package domain adv after root package*/
    public static final String ADV_MODEL_TARGET_PACKAGE = "domain.adv";
    /*target package dao after root package */
    public static final String DAO_TARGET_PACKAGE = "dao";
    /*target package dao impl after root package */
    public static final String DAO_IMPL_TARGET_PACKAGE = "dao.impl";

    /*mapper path*/
    public static final String MAPPER_TARGET_PATH = "target/mybatis/mapper";
    /*adv path*/
    public static final String MAPPER_EXT_TARGET_PATH = "target/mybatis/mapper/extend";

    /*domain path*/
    public static final String MODEL_TARGET_PATH = "target/mybatis/domain/domain";

    /*mysql config*/
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://xxx/xxx?characterEncoding=utf8&useUnicode=true";
    public static final String USERNAME = "xxx";
    public static final String PASSWORD = "xxx";


}
