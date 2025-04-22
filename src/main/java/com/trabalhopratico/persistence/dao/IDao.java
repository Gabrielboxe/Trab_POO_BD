package com.trabalhopratico.persistence.dao;

import java.util.List;

public interface IDao<T> {
    public List<T> selectAll();
    public T selectById(int id);
    public void create(T t);
    public void update(T t);
    public void delete(int id);
}
