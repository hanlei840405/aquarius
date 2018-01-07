package com.galaxy.framework.aquarius.service;

import java.io.Serializable;
import java.util.List;

public interface CrudService<T, V extends Serializable> {

    T selectOne(V id);

    T selectOne(T var);

    List<T> select(T var);

    List<T> selectAll();

    T insert(T var);

    void insert(List<T> vars);

    T update(T var);

    void update(List<T> vars);

    int delete(V id);

    int delete(T var);

    void delete(List<V> vars);
}
