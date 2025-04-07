package app.persistence.daos;

import java.util.List;
import java.util.Optional;

public interface IDAO<T> {
    T save(T dto);
    Optional<T> findById(int id);
    List<T> findAll();
    Optional<T> update(T dto);
    void delete(int id);
}