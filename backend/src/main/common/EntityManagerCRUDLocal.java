package main.common;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import main.common.exceptions.EntityCreateException;
import main.common.exceptions.EntityDeleteException;
import main.common.exceptions.EntityInvalidException;
import main.common.exceptions.EntityNotAllowedException;
import main.common.exceptions.EntityNotFoundException;
import main.common.exceptions.EntityUpdateException;



@Local
public interface EntityManagerCRUDLocal<T> {

    Class<T> getEntityClass();

    String getEntityName();

    String getEntityText();

    List<T> getList();

    List<T> getList(Integer startPosition, Integer maxResult);

    List<T> getList(Integer startPosition, Integer maxResult, String sortField, Boolean sortOrder,
            Map<String, Object> filters);

    List<T> getList(Long fromId, Integer maxResult);

    int getListCount();

    int getListCount(Map<String, Object> filters);

    T getInstance();

    T getProperEntity(T entity);

    void validateEntity(T entity) throws EntityInvalidException;
    
    T get(T entity) throws EntityNotFoundException, EntityNotAllowedException;

    T getById(Long id) throws EntityNotFoundException, EntityNotAllowedException;

    T getById(String id) throws EntityNotFoundException, EntityNotAllowedException;

    T getByName(String name) throws EntityNotFoundException, EntityNotAllowedException;

    T getByLabel(String label) throws EntityNotFoundException, EntityNotAllowedException;
    
    T getByUuid(String uuid) throws EntityNotFoundException, EntityNotAllowedException;

    T getFromEntity(T entity) throws EntityNotFoundException, EntityNotAllowedException;

    T manage(T entity) throws EntityNotAllowedException;

    T create(T entity) throws EntityCreateException, EntityNotAllowedException;

    T createFromEntity(T entity) throws EntityCreateException, EntityNotAllowedException;

    T update(T entity) throws EntityUpdateException, EntityNotAllowedException;

    T updateFromEntity(T entity)
            throws EntityNotFoundException, EntityUpdateException, EntityNotAllowedException;

    T updateFromEntity(T currentEntity, T entity)
            throws EntityUpdateException, EntityNotAllowedException, main.common.exceptions.EntityNotFoundException;

    void delete(T entity) throws EntityDeleteException, EntityNotAllowedException;


}
