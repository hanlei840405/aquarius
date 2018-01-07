package com.galaxy.framework.aquarius.service.impl;

import com.galaxy.framework.aquarius.service.CrudService;
import com.galaxy.framework.pisces.db.DeleteException;
import com.galaxy.framework.pisces.db.InsertException;
import com.galaxy.framework.pisces.db.UpdateException;
import com.galaxy.framework.pisces.db.VersionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class CrudServiceImpl<T, V extends Serializable> implements CrudService<T, V> {

    @Autowired
    private Mapper<T> mapper;

    @Transactional(readOnly = true)
    @Override
    public T selectOne(V id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public T selectOne(T var) {
        return mapper.selectOne(var);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> select(T var) {
        return mapper.select(var);
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public T insert(T var) {
        try {
            mapper.insert(var);
            return var;
        } catch (Exception e) {
            throw new InsertException();
        }
    }

    @Override
    public T update(T var) {
        try {
            int cnt = mapper.updateByPrimaryKey(var);
            if (cnt == 0) {
                throw new VersionException();
            }
            return var;
        } catch (Exception e) {
            throw new UpdateException();
        }
    }

    @Override
    public int delete(V id) {
        try {
            return mapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            throw new DeleteException();
        }
    }

    @Override
    public int delete(T var) {
        try {
            int cnt = mapper.delete(var);
            if (cnt == 0) {
                throw new VersionException();
            }
            return cnt;
        } catch (Exception e) {
            throw new DeleteException();
        }
    }
}
