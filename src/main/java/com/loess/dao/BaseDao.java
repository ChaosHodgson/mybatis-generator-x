package com.loess.dao;

import com.loess.common.Page;

import java.util.List;
import java.util.Map;

/**
 * Do
 *
 * @author ChaosHodgson
 * @date 2017/6/8
 * @since 1.0
 */
public interface BaseDao<T> {

    /**
     * 统计数量
     * 根据不为null和""的属性来组合where
     *
     * @param target
     * @return
     */
    int count(T target);

    /**
     * 根据id获得对象
     *
     * @param id
     * @return
     */
    T query(Long id);

    /**
     * 获得单个对象
     * 根据不为null和""的属性来组合where
     *
     * @param target
     * @return
     */
    T queryByProperty(T target);

    /**
     * 获得对象
     * 根据不为null和""的属性来组合where
     *
     * @param target
     * @return
     */
    List<T> query(T target);

    /**
     * 获得对象
     * 根据不为null和""的属性来组合where
     *
     * @param target
     * @param orderBy
     * @return
     */
    List<T> query(T target, String orderBy);


    /**
     * 分页查询
     *
     * @param target
     * @param page
     * @return ArrayList
     */
    List<T> query(T target, Page page);

    /**
     * 分页查询
     *
     * @param target
     * @param page
     * @return ArrayList
     */
    List<T> query(T target, Page page, String orderBy);

    /**
     * 分页查询
     *
     * @param target
     * @param page
     * @return
     */
    Page<T> queryPage(T target, Page page, String orderBy);

    /**
     * 自定义查询
     *
     * @param sqlConstituents 自定义查询语句成分
     * @return
     */
    T query(Map sqlConstituents);

    /**
     * 自定义查询
     *
     * @param sqlConstituents 自定义查询语句成分
     * @param orderBy
     * @return
     */
    List<T> query(Map sqlConstituents, String orderBy);

    /**
     * @param sqlConstituents
     * @param page
     * @param orderBy
     * @return
     */
    List<T> query(Map sqlConstituents, Page page, String orderBy);

    /**
     * create new
     *
     * @param target
     * @return Long
     * @see
     */
    Long insert(T target);

    /**
     * 根据id更新对象，只更新不为null和""的属性
     *
     * @param target
     * @return
     */
    int update(T target);

    /**
     * 批量更新
     *
     * @param query 查询对像 根据不为null和""的属性
     * @param value 设值， 只更新不为null和""的属性
     * @return
     */
    int update(T query, T value);

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int delete(Long id);

}
