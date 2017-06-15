package com.loess.dao;

import com.loess.common.DomainObject;
import com.loess.common.Page;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Map;

/**
 * Do
 *
 * @author ChaosHodgson
 * @date 2017/6/9
 * @since 1.0
 */
public class BaseDaoImpl<T extends DomainObject> implements BaseDao<T> {

    private SqlSessionFactory sessionFactory;

    private SqlSession readSqlSession = sessionFactory.openSession();

    private SqlSession writeSqlSession = sessionFactory.openSession();

    /* 当前操作 命名空间*/
    private String NAME_SPACE;

    /*constructor*/
    public BaseDaoImpl(Class<T> targetClass) {
        this.NAME_SPACE = targetClass.getName();
    }

    /**
     * 统计数量
     * 根据不为null和""的属性来组合where
     *
     * @param target
     * @return
     */
    @Override
    public int count(T target) {
        return readSqlSession.selectOne(NAME_SPACE + ".count", target);
    }

    /**
     * 根据id获得对象
     *
     * @param id
     * @return
     */
    @Override
    public T query(Long id) {
        return readSqlSession.selectOne(NAME_SPACE + ".queryById", id);
    }

    /**
     * 获得单个对象
     * 根据不为null和""的属性来组合where
     *
     * @param target
     * @return
     */
    @Override
    public T queryByProperty(T target) {
        return readSqlSession.selectOne(NAME_SPACE + ".queryByProperty", target);
    }

    /**
     * 获得对象
     * 根据不为null和""的属性来组合where
     *
     * @param target
     * @return
     */
    @Override
    public List query(T target) {
        return null;
    }

    /**
     * 获得对象
     * 根据不为null和""的属性来组合where
     *
     * @param target
     * @param orderBy
     * @return
     */
    @Override
    public List query(T target, String orderBy) {
        return null;
    }

    /**
     * 分页查询
     *
     * @param target
     * @param page
     * @return ArrayList
     */
    @Override
    public List query(T target, Page page) {
        return null;
    }

    /**
     * 分页查询
     *
     * @param target
     * @param page
     * @param orderBy
     * @return ArrayList
     */
    @Override
    public List query(T target, Page page, String orderBy) {
        return null;
    }

    /**
     * 分页查询
     *
     * @param target
     * @param page
     * @param orderBy
     * @return
     */
    @Override
    public Page queryPage(T target, Page page, String orderBy) {
        return null;
    }

    /**
     * 自定义查询
     *
     * @param sqlConstituents 自定义查询语句成分
     * @return
     */
    @Override
    public T query(Map sqlConstituents) {
        return null;
    }

    /**
     * 自定义查询
     *
     * @param sqlConstituents 自定义查询语句成分
     * @param orderBy
     * @return
     */
    @Override
    public List<T> query(Map sqlConstituents, String orderBy) {
        return null;
    }

    /**
     * @param sqlConstituents
     * @param page
     * @param orderBy
     * @return
     */
    @Override
    public List<T> query(Map sqlConstituents, Page page, String orderBy) {
        return null;
    }


    /**
     * create new
     *
     * @param target
     * @return Long
     * @see
     */
    @Override
    public Long insert(T target) {
        writeSqlSession.update(NAME_SPACE + ".insert", target);
        return target.getId();
    }

    /**
     * 根据id更新对象，只更新不为null和""的属性
     *
     * @param target id can not be null
     * @return
     */
    @Override
    public int update(T target) {
        return writeSqlSession.update(NAME_SPACE + ".updateById", target);
    }

    /**
     * 批量更新
     *
     * @param query 查询对像 根据不为null和""的属性
     * @param value 设值， 只更新不为null和""的属性
     * @return
     */
    @Override
    public int update(T query, T value) {
        return 0;
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @Override
    public int delete(Long id) {
        return 0;
    }


}
