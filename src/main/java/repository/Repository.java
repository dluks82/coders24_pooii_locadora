package repository;

import java.util.List;

public interface Repository<T> {

    T save(T entity);

    T update(T entity);

    boolean delete(T entity);

    T findById(String id);

    List<T> findAll();
}
