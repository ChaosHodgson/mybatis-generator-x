package com.loess.common;

import java.io.Serializable;

/**
 * 系统领域对象的基类，实现了Serializable接口。
 * DomainObject还覆写了hashCode()方法和equals()方法。这两个方法都将基于代理主键进行操作。
 * 只有代理主键相等，领域对象才相等。如果创建的对象的entityId均未赋值，则直接比较物理地址。
 */
public abstract class DomainObject implements Serializable {


    /**
     * primary key.
     */
    private Long id;

    /**
     * constructor
     */
    public DomainObject() {
    }

    /**
     * constructor
     *
     * @param id
     */
    public DomainObject(Long id) {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = Id;
    }

    /**
     * override hashcode
     *
     * @return
     */
    @Override
    public int hashCode() {
        return (id != null ? id.hashCode() : 0);
    }

    /**
     * override equals
     */
    @Override
    public boolean equals(Object target) {
        if (target == null)
            return false;

        if (target == this)
            return true;

        if (target.getClass() != this.getClass())
            return false;

        DomainObject t = (DomainObject) target;

        //如果领域对象的Id为空，直接判断物理地址
        if (id == null || t.getId() == null)
            return (t == this);

        return id.equals(t.getId());
    }
}
