package com.caudron.amusementpark.models.db.repositories.interfaces;

import java.util.List;

public interface IRepository<T> {
    int insert(T item);
    int update(T item);
    int delete(T item);
    T getById(int id);
    List<T> getAll();

}
