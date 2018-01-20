package main.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import main.common.exceptions.EntityCreateException;
import main.common.exceptions.EntityDeleteException;
import main.common.exceptions.EntityInvalidException;
import main.common.exceptions.EntityNotAllowedException;
import main.common.exceptions.EntityNotFoundException;
import main.common.exceptions.EntityUpdateException;
import main.common.model.GenericEntity;
import main.common.utils.CRUDUtil;


public class EntityManagerCRUDBean<T> implements EntityManagerCRUDLocal<T> {

	    private Log logger = LogFactory.getLog(EntityManagerCRUDBean.class);
	    private final static String PERSISTENCE_UNIT_NAME = "FoodCalculator";
	    private static EntityManagerFactory factory = 
	    	    Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	    @PersistenceContext(unitName = "FoodCalculator")
	    protected EntityManager em;

	   
	    private String entityPackage;

	    public EntityManagerCRUDBean() {
	        entityPackage = EntityManagerCRUDBean.class.getPackage().getName();
	        entityPackage = entityPackage.substring(0, entityPackage.length() - ".common.session".length());
	        em=factory.createEntityManager();
	    }

	    protected EntityManager getEntityManager() {
	        return em;
	    }

	    protected void assertAccess(T entity) throws EntityNotAllowedException {
	    }

	    protected List<T> filterByAccess(List<T> list) {
	        return list;
	    }

	    private Class<T> entityClass;

	    @SuppressWarnings("unchecked")
	    @Override
	    public Class<T> getEntityClass() {
	        if (entityClass == null) {
	            entityClass = CRUDUtil.getEntityClass(this.getClass());
	        }
	        return entityClass;
	    }

	    private String entityName = null;

	    @Override
	    public String getEntityName() {
	        if (entityName == null) {
	            entityName = CRUDUtil.getEntityName(this.getClass());
	        }
	        return entityName;
	    }

	    private String entityText = null;

	    @Override
	    public String getEntityText() {
	        if (entityText == null) {
	            entityText = CRUDUtil.getEntityText(this.getClass());
	        }
	        return entityText;
	    }

	    protected String getIdentifier(Object entity) throws NoSuchMethodException {
	        String id = null;
	        try {
	            id = (String) getProperty(entity, "Uuid");
	            if (StringUtils.isEmpty(id)) {
	                throw new NoSuchMethodException("Empty UUID so continue with next");
	            }
	        } catch (NoSuchMethodException e) {
	            try {
	                id = (String) getProperty(entity, "Name");
	            } catch (NoSuchMethodException e2) {
	                try {
	                    id = (String) getProperty(entity, "Uid");
	                } catch (NoSuchMethodException e3) {
	                    try {
	                        id = (String) getProperty(entity, "Title");
	                    } catch (NoSuchMethodException e4) {
	                        id = (String) getProperty(entity, "Text");
	                    }
	                }
	            }
	        }
	        return id;
	    }

	    protected String getLabel(Object entity) {
	        String label = null;
	        try {
	            label = (String) getProperty(entity, "label");
	        } catch (NoSuchMethodException e) {
	            try {
	                label = (String) getProperty(entity, "name");
	            } catch (NoSuchMethodException e2) {
	                label = "";
	            }
	        }
	        return label;
	    }

	    protected Object getProperty(Object entity, String property) throws NoSuchMethodException {
	        try {
	            Class<?>[] parameterTypes = null;
	            Object[] args = null;
	            Object testEntity = entity;
	            for (String propertyElement : property.split("\\.")) {
	                Method m = getter(testEntity.getClass(), propertyElement, parameterTypes);
	                testEntity = m.invoke(entity, args);
	            }
	            return testEntity;
	        } catch (Exception e) {
	            throw new NoSuchMethodException(e.getMessage());
	        }
	    }

	    protected Boolean hasProperty(Object entity, String property) {
	        try {
	            Class<?>[] parameterTypes = null;
	            Object[] args = null;
	            Object testEntity = entity;
	            for (String propertyElement : property.split("\\.")) {
	                Method m = getter(testEntity.getClass(), propertyElement, parameterTypes);
	                testEntity = m.invoke(entity, args);
	            }
	            return true;
	        } catch (Exception e) {
	            // Nothing to do here
	        }
	        return false;
	    }

	    protected Boolean hasProperty(Class<T> clazz, String property) {
	        try {
	            Class<?>[] parameterTypes = null;
	            Class<?> testClazz = clazz;
	            for (String propertyElement : property.split("\\.")) {
	                Method m = getter(testClazz, propertyElement, parameterTypes);
	                testClazz = m.getReturnType();
	            }
	            return true;
	        } catch (Exception e) {
	            // Nothing to do here
	        }
	        return false;
	    }

	    protected void setProperty(T entity, String property, Object value) throws NoSuchMethodException {
	        try {
	            Class<?>[] parameterTypes = null;
	            Object[] args = null;
	            Object testEntity = entity;
	            String propertyElements[] = property.split("\\.");
	            for (int p = 0; p < propertyElements.length; p++) {
	                String propertyElement = propertyElements[p];
	                if (p < (propertyElements.length - 1)) {
	                    Method m = getter(testEntity.getClass(), propertyElement, parameterTypes);
	                    testEntity = m.invoke(entity, args);
	                } else {
	                    Method m = setter(testEntity.getClass(), propertyElement, new Class<?>[] { value.getClass() });
	                    testEntity = m.invoke(entity, new Object[] { value });
	                }
	            }
	        } catch (Exception e) {
	            throw new NoSuchMethodException(e.getMessage());
	        }
	    }

	    protected Class<?> getPropertyClass(Class<T> clazz, String property) throws NoSuchMethodException {
	        try {
	            Class<?> propertyClass = null;
	            Class<?>[] parameterTypes = null;
	            Class<?> testClass = clazz;
	            for (String propertyElement : property.split("\\.")) {
	                Method m = getter(testClass, propertyElement, parameterTypes);
	                testClass = propertyClass = m.getReturnType();
	            }
	            return propertyClass;
	        } catch (Exception e) {
	            throw new NoSuchMethodException(e.getMessage());
	        }
	    }

	    @SuppressWarnings("rawtypes")
	    private Object getRawProperty(Object entity, String property) throws NoSuchMethodException {
	        Object s = null;
	        try {
	            Class[] cargs = null;
	            Method m = getter(entity.getClass(), property, cargs);
	            Object[] iargs = null;
	            s = m.invoke(entity, iargs);
	        } catch (SecurityException e) {
	            logger.error("Error", e);
	        } catch (IllegalArgumentException e) {
	            logger.error("Error", e);
	        } catch (IllegalAccessException e) {
	            logger.error("Error", e);
	        } catch (InvocationTargetException e) {
	            logger.error("Error", e);
	        }
	        return s;
	    }

	    protected Method getter(Class<?> clazz, String property, Class<?>[] parameterTypes)
	            throws NoSuchMethodException, SecurityException {
	        try {
	            return clazz.getMethod("get" + property.substring(0, 1).toUpperCase() + property.substring(1), parameterTypes);
	        } catch (NoSuchMethodException e) {
	            return clazz.getMethod("is" + property.substring(0, 1).toUpperCase() + property.substring(1), parameterTypes);
	        }
	    }

	    protected Method setter(Class<?> clazz, String property, Class<?>[] parameterTypes)
	            throws NoSuchMethodException, SecurityException {
	        return clazz.getMethod("set" + property.substring(0, 1).toUpperCase() + property.substring(1), parameterTypes);
	    }


	    @Override
	    public List<T> getList() {
	        return getList(null, null, null, null, null);
	    }

	    @Override
	    public List<T> getList(Integer startPosition, Integer maxResult) {
	        return getList(startPosition, maxResult, null, null, null);
	    }

	    @Override
	    @SuppressWarnings("unchecked")
	    public List<T> getList(Integer startPosition, Integer maxResult, String sortField, Boolean sortOrder,
	            Map<String, Object> filters) {
	        List<T> list = null;
	        String filter = null;
	        resetListFilter();
	        if (filters != null && !filters.isEmpty()) {
	            filter = "";
	            for (String field : filters.keySet()) {
	                String fieldFilter = getListFilter(filters, field);
	                if (fieldFilter != null && !fieldFilter.isEmpty()) {
	                    filter += ((!filter.isEmpty() ? " AND " : " ") + fieldFilter);
	                }
	            }
	            if (!filter.isEmpty()) {
	                filter = " WHERE" + filter;
	            }
	        }

	        String order = getListOrder(sortField, sortOrder != null ? sortOrder : true);
	        if (order != null)
	            order = " ORDER BY " + order;

	        String join = getListFilterJoin();

	        String q = "SELECT e FROM " + getEntityName() + " e" + (join != null ? " LEFT JOIN " + join : "")
	                + (filter != null ? filter : "") + (order != null ? order : "");

	        if (logger.isTraceEnabled()) {
	            logger.trace("lazyQuery : " + q);
	        }
	        Query query = getEntityManager().createQuery(q);

	        if (startPosition != null && startPosition >= 0) {
	            query.setFirstResult(startPosition);
	        }
	        if (maxResult != null && maxResult > 0) {
	            query.setMaxResults(maxResult);
	        }

	        Map<String, Object> filterParams = getListFilterParams(filters);
	        if (filterParams != null) {
	            for (Entry<String, Object> entry : filterParams.entrySet()) {
	                String paramField = entry.getKey().replaceAll("\\.", "_");
	                if (logger.isTraceEnabled()) {
	                    logger.trace(
	                            "lazyQuery : " + paramField + " = " + entry.getValue() + " (" + entry.getValue().getClass() + ")");
	                }
	                query.setParameter(paramField, entry.getValue());
	            }
	        }

	        list = query.getResultList();
	        list = filterByAccess(list);

	        return list;
	    }

	    @Override
	    public int getListCount() {
	        return getListCount(null);
	    }

	    @SuppressWarnings("unchecked")
		@Override
	    public int getListCount(Map<String, Object> filters) {
	        int count = 0;
	        String filter = null;
	        if (filters != null && !filters.isEmpty()) {
	            filter = "";
	            for (String field : filters.keySet()) {
	                String fieldFilter = getListFilter(filters, field);
	                if (fieldFilter != null && !fieldFilter.isEmpty()) {
	                    filter += ((!filter.isEmpty() ? " AND " : " ") + fieldFilter);
	                }
	            }
	            if (!filter.isEmpty()) {
	                filter = " WHERE" + filter;
	            }
	        }

	        String join = getListFilterJoin();

	        String q = "SELECT COUNT(DISTINCT e) FROM " + getEntityName() + " e" + (join != null ? " LEFT JOIN " + join : "")
	                + (filter != null ? filter : "");

	        if (logger.isTraceEnabled()) {
	            logger.trace("lazyCountQuery : " + q);
	        }
	        Query query = getEntityManager().createNativeQuery(q, Long.class);

	        Map<String, Object> filterParams = getListFilterParams(filters);
	        if (filterParams != null) {
	            for (Entry<String, Object> entry : filterParams.entrySet()) {
	                String paramField = entry.getKey().replaceAll("\\.", "_");
	                query.setParameter(paramField, entry.getValue());
	            }
	        }

	        List<Long> results = query.getResultList();
	        if (!results.isEmpty()) {
	            count = results.get(0).intValue();
	        }
	        return count;
	    }

	    @Override
	    @SuppressWarnings("unchecked")
	    public List<T> getList(Long fromId, Integer maxResult) {
	        List<T> list = null;
	        String join = getListFilterJoin();
	        String q = "SELECT distinct e FROM " + getEntityName() + " e"
	                + (fromId != null && fromId >= 0 ? " WHERE e.id > :id" : "") + (join != null ? " LEFT JOIN " + join : "")
	                + " ORDER BY e.id";
	        Query query = getEntityManager().createQuery(q);
	        if (fromId != null && fromId >= 0) {
	            query.setParameter("id", fromId);
	        }
	        if (maxResult != null && maxResult > 0) {
	            query.setMaxResults(maxResult);
	        }
	        list = query.getResultList();
	        return list;
	    }

	    protected String getListFilterJoin() {
	        return null;
	    }

	    @SuppressWarnings({ "unchecked", "rawtypes" })
	    protected String getListFilter(Map<String, Object> filters, String property) {
	        Class<T> clazz = CRUDUtil.getEntityClass(getClass());
	        try {
	            Class propertyClass = getPropertyClass(clazz, property);
	            List values;
	            if (filters.get(property) instanceof List && !propertyClass.equals(String.class)) {
	                values = (List) filters.get(property);
	            } else {
	                values = new ArrayList();
	                values.add(filters.get(property));
	            }
	            StringBuffer sb = new StringBuffer();
	            if (values.size() > 1) {
	                sb.append("(");
	            }
	            for (int valueCount = 0; valueCount < values.size(); valueCount++) {
	                String paramField = property.replaceAll("\\.", "_") + (valueCount > 0 ? valueCount : "");
	                Object value = values.get(valueCount);
	                if (valueCount > 0) {
	                    sb.append(" OR ");
	                }
	                boolean isNot = isNot(String.valueOf(value));
	                if (withoutNot(String.valueOf(value)).equals("null")) {
	                    sb.append((isNot ? "NOT " : "") + "e." + property + " IS NULL");
	                } else {
	                    if (propertyClass.equals(String.class)) {
	                        if (value instanceof List) {
	                            sb.append((isNot ? "NOT " : "") + "e." + property + " IN :" + paramField + "");
	                        } else {
	                            if (hasWildcards((String) value)) {
	                                sb.append("UPPER(e." + property + ")" + (isNot ? " NOT" : "") + "LIKE :" + paramField + "");
	                            } else {
	                                sb.append("UPPER(e." + property + ")" + (isNot ? " <>" : " =") + " :" + paramField + "");
	                            }
	                        }
	                    } else {
	                        if (propertyClass.isEnum()) {
	                            try {
	                                Enum.valueOf(propertyClass, String.valueOf(value));
	                                sb.append("e." + property + " = :" + paramField + "");
	                            } catch (IllegalArgumentException e) {
	                                try {
	                                    propertyClass.getDeclaredMethod("getEnum", String.class).invoke(null,
	                                            String.valueOf(value));
	                                    sb.append("e." + property + (valueCount > 0 ? valueCount : "") + " = :" + paramField + "");
	                                } catch (IllegalAccessException e1) {
	                                } catch (IllegalArgumentException e1) {
	                                } catch (InvocationTargetException e1) {
	                                } catch (SecurityException e1) {
	                                }
	                                // Not a valid enum
	                            }
	                        } else {
	                            sb.append("e." + property + " = :" + paramField + "");
	                        }
	                    }
	                }
	            }
	            if (values.size() > 1) {
	                sb.append(")");
	            }
	            return sb.toString();
	        } catch (NoSuchMethodException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    @SuppressWarnings({ "unchecked", "rawtypes" })
	    protected Map<String, Object> getListFilterParams(Map<String, Object> filters) {
	        Class<T> clazz = CRUDUtil.getEntityClass(getClass());
	        Map<String, Object> filterParams = new HashMap<String, Object>();
	        if (filters != null && !filters.isEmpty()) {
	            for (Entry<String, Object> entry : filters.entrySet()) {
	                String property = entry.getKey();
	                try {
	                    Class propertyClass = getPropertyClass(clazz, property);
	                    List values;
	                    if (entry.getValue() instanceof List && !propertyClass.equals(String.class)) {
	                        values = (List) entry.getValue();
	                    } else {
	                        values = new ArrayList();
	                        values.add(entry.getValue());
	                    }
	                    for (int valueCount = 0; valueCount < values.size(); valueCount++) {
	                        String paramField = property.replaceAll("\\.", "_") + (valueCount > 0 ? valueCount : "");
	                        Object value = values.get(valueCount);
	                        if (withoutNot(String.valueOf(value)).equals("null")) {
	                            // Nothing
	                        } else {
	                            if (propertyClass.equals(String.class)) {
	                                value = withoutNot((String) value);
	                                if (value instanceof List) {
	                                    filterParams.put(paramField, value);
	                                } else {
	                                    if (hasWildcards((String) value)) {
	                                        filterParams.put(paramField, withProperWildcards(((String) value).toUpperCase()));
	                                    } else {
	                                        filterParams.put(paramField, ((String) value).toUpperCase());
	                                    }
	                                }
	                            } else {
	                                if (propertyClass.isEnum()) {
	                                    try {
	                                        filterParams.put(paramField, value);
	                                    } catch (IllegalArgumentException e) {
	                                        try {
	                                            filterParams.put(paramField, propertyClass
	                                                    .getDeclaredMethod("getEnum", String.class).invoke(null, value));
	                                        } catch (IllegalAccessException e1) {
	                                        } catch (IllegalArgumentException e1) {
	                                        } catch (InvocationTargetException e1) {
	                                        } catch (SecurityException e1) {
	                                        }
	                                        // Not a valid enum
	                                    }
	                                } else {
	                                    filterParams.put(paramField, value);
	                                }
	                            }
	                        }
	                    }
	                } catch (NoSuchMethodException e) {
	                }
	            }
	        }
	        return filterParams;
	    }

	    private boolean hasWildcards(String s) {
	        return s.startsWith("*") || s.endsWith("*") || s.startsWith("%") || s.endsWith("%");
	    }

	    private String withProperWildcards(String s) {
	        return s.replaceAll("\\*", "%");
	    }

	    private boolean isNot(String s) {
	        return s.startsWith("!");
	    }

	    private String withoutNot(String s) {
	        return isNot(s) ? s.substring(1) : s;
	    }

	    protected void resetListFilter() {
	    }

	    protected String getListOrder(String sortField, boolean sortOrder) {
	        String order = null;
	        if (!StringUtils.isEmpty(sortField)) {
	            for (String sortFieldElement : sortField.split(",")) {
	                if (hasProperty(getEntityClass(), sortFieldElement)) {
	                    if (order == null) {
	                        order = "";
	                    }
	                    if (sortField.toLowerCase().contains("date")) {
	                        order += ((!order.isEmpty() ? "," : "") + "e." + sortFieldElement + " " + (sortOrder ? "ASC" : "DESC"));
	                    } else {
	                        order += ((!order.isEmpty() ? "," : "") + "LOWER(e." + sortFieldElement + ") "
	                                + (sortOrder ? "ASC" : "DESC"));
	                    }
	                }
	            }
	        } else {
	            String defaultSortField = getDefaultListOrder();
	            if (!StringUtils.isEmpty(defaultSortField)) {
	                return getListOrder(defaultSortField, true);
	            }
	        }
	        return order;
	    }

	    protected String getDefaultListOrder() {
	        return "name";
	    }


	    @Override
	    public T getInstance() {
	        T entity = null;
	        try {
	            entity = getEntityClass().newInstance();
	        } catch (Exception e) {
	            logger.error("Error", e);
	        }
	        return entity;
	    }

	    @Override
	    public T getProperEntity(T entity) {
	        return entity;
	    }

	    @Override
	    public void validateEntity(T entity) throws EntityInvalidException {
	    }

	    @Override
	    public T getFromEntity(T entity) throws EntityNotAllowedException, EntityNotFoundException {
	        try {
	            String uuid = (String) getProperty(entity, "uuid");
	            return getByUuid(uuid);
	        } catch (NoSuchMethodException e) {
	            throw new EntityNotFoundException("Not a uuid-base entity");
	        }
	    }

	    @Override
	    public T get(T entity) throws EntityNotAllowedException, EntityNotFoundException {
	        T mergedentity = null;
	        try {
	            Object id = getProperty(entity, "Id");
	            getEntityManager().clear();
	            mergedentity = getEntityManager().find(getEntityClass(), id);
	            assertAccess(mergedentity);
	        } catch (Exception e) {
	            throw new EntityNotFoundException(e);
	        }
	        if (mergedentity == null) {
	            throw new EntityNotFoundException("Entity not found");
	        }
	        return mergedentity;
	    }

	    @Override
	    public T getById(Long id) throws EntityNotAllowedException, EntityNotFoundException {
	        T entity = null;
	        try {
	            entity = getEntityManager().find(getEntityClass(), id);
	        } catch (Exception e) {
	            throw new EntityNotFoundException(e);
	        }
	        if (entity == null) {
	            throw new EntityNotFoundException("Entity not found");
	        }
	        assertAccess(entity);
	        return entity;
	    }

	    @Override
	    public T getById(String id) throws EntityNotAllowedException, EntityNotFoundException {
	        T entity = null;
	        try {
	            entity = getEntityManager().find(getEntityClass(), id);
	        } catch (Exception e) {
	            throw new EntityNotFoundException(e);
	        }
	        if (entity == null) {
	            throw new EntityNotFoundException("Entity not found");
	        }
	        assertAccess(entity);
	        return entity;
	    }

	    @SuppressWarnings("unchecked")
		protected T getSingleResult(Query query) throws NoResultException {
	        List<T> list = query.getResultList();
	        if (list.isEmpty()) {
	            throw new NoResultException();
	        } else {
	            return list.get(0);
	        }
	    }

	    @Override
	    public T getByName(String name) throws EntityNotAllowedException, EntityNotFoundException {
	        T entity = null;
	        try {
	            Query query = getEntityManager().createNativeQuery(
	                    "SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE LOWER(e.name)=:name", getEntityClass());
	            query.setParameter("name", name.toLowerCase());
	            entity = getSingleResult(query);
	            assertAccess(entity);
	        } catch (NoResultException e) {
	            throw new EntityNotFoundException(e);
	        } catch (NonUniqueResultException e) {
	            throw new EntityNotFoundException(e);
	        } catch (Exception e) {
	            throw new EntityNotFoundException(e);
	        }
	        if (entity == null) {
	            throw new EntityNotFoundException("Entity not found");
	        }
	        return entity;
	    }

	    @Override
	    public T getByLabel(String label) throws EntityNotAllowedException, EntityNotFoundException {
	        T entity = null;
	        try {
	            Query query = getEntityManager().createNativeQuery(
	                    "SELECT e FROM " + getEntityClass().getSimpleName() + " e WHERE LOWER(e.label)=:label", getEntityClass());
	            query.setParameter("label", label.toLowerCase());
	            entity = getSingleResult(query);
	            assertAccess(entity);
	        } catch (NoResultException e) {
	            throw new EntityNotFoundException(e);
	        } catch (NonUniqueResultException e) {
	            throw new EntityNotFoundException(e);
	        } catch (Exception e) {
	            throw new EntityNotFoundException(e);
	        }
	        if (entity == null) {
	            throw new EntityNotFoundException("Entity not found");
	        }
	        return entity;
	    }

	    @Override
	    public T getByUuid(String uuid) throws EntityNotAllowedException, EntityNotFoundException {
	        getEntityManager().clear();
	        T entity = null;
	        try {
	            String sql = "SELECT e FROM " + getEntityClass().getSimpleName()
	                    + " e WHERE LOWER(e.uuid)=LOWER(:uuid) ORDER BY e.id";
	            logger.trace("sql: '" + sql + "'");
	            Query query = getEntityManager().createNativeQuery(sql, getEntityClass());
	            query.setParameter("uuid", uuid);
	            logger.trace("query: '" + query.toString() + "'");
	            entity = getSingleResult(query);
	            assertAccess(entity);
	        } catch (NoResultException e) {
	            throw new EntityNotFoundException(e);
	        } catch (NonUniqueResultException e) {
	            throw new EntityNotFoundException(e);
	        } catch (Exception e) {
	            throw new EntityNotFoundException(e);
	        }
	        if (entity == null) {
	            throw new EntityNotFoundException("Entity not found");
	        }
	        return entity;
	    }

	    @Override
	    public T manage(T entity) {
	        return getEntityManager().merge(entity);
	    }

	   

	    @Override
	    public T create(T entity) throws EntityCreateException, EntityNotAllowedException {
	        if (logger.isTraceEnabled()) {
	            logger.trace("create:init:entity=" + entity);
	        }
	        boolean emptyIdentifier = false;
	        if (!emptyIdentifier) {
	            entity = getProperEntity(entity);
	            try {
	                validateEntity(entity);
	            } catch (EntityInvalidException e) {
	                throw new EntityCreateException(e);
	            }
	            refreshProperties(entity);
	            assertAccess(entity);
	            try {
	               
	                getEntityManager().persist(entity);
	                // Force flush to detect EntityExistsException
	                getEntityManager().flush();
	                entity = getEntityManager().merge(entity);
	            } catch (Exception e) {
	                if (logger.isDebugEnabled()) {
	                    logger.debug("entity=" + entity, e);
	                }
	                throw new EntityCreateException(e);
	            }
	        } else {
	            throw new EntityCreateException("Identifier cannot be blank");
	        }
	        if (logger.isTraceEnabled()) {
	            logger.trace("create:done:entity=" + entity);
	        }
	        return entity;
	    }

	    @Override
	    public T createFromEntity(T entity) throws EntityCreateException, EntityNotAllowedException {
	        T newEntity;
	        if (entity != null) {
	            newEntity = getInstance();
	            copyProperties(newEntity, entity);
	            refreshProperties(newEntity);
	            newEntity = create(newEntity);
	        } else {
	            throw new EntityCreateException("No source entity specified");
	        }
	        return newEntity;
	    }

	    @Override
	    public T update(T entity) throws EntityUpdateException, EntityNotAllowedException {
	        if (logger.isTraceEnabled()) {
	            logger.trace("update:init:entity=" + entity);
	        }
	        boolean emptyIdentifier = false;
	        try {
	            String id = getIdentifier(entity);
	            emptyIdentifier = StringUtils.isEmpty(id);
	        } catch (NoSuchMethodException e) {
	            throw new EntityUpdateException(e);
	        }
	        if (!emptyIdentifier) {
	            entity = getProperEntity(entity);
	            try {
	                validateEntity(entity);
	            } catch (EntityInvalidException e) {
	                throw new EntityUpdateException(e);
	            }
	            assertAccess(entity);
	            try {
	               
	                entity = getEntityManager().merge(entity);
	                getEntityManager().flush();
	            } catch (Exception e) {
	                throw new EntityUpdateException(e);
	            }
	        } else {
	            throw new EntityUpdateException("Identifier cannot be blank");
	        }
	        if (logger.isTraceEnabled()) {
	            logger.trace("update:done:entity=" + entity);
	        }
	        return entity;
	    }

	    @Override
	    public T updateFromEntity(T entity) throws EntityUpdateException, EntityNotAllowedException, EntityNotFoundException {
	        return updateFromEntity(getFromEntity(entity), entity);
	    }

	    @Override
	    public T updateFromEntity(T currentEntity, T entity) throws EntityUpdateException, EntityNotAllowedException, EntityNotFoundException {
	        if (entity != null) {
	            currentEntity = get(currentEntity);
				copyProperties(currentEntity, entity);
				currentEntity = update(currentEntity);
	        }
	        return currentEntity;
	    }

	    @Override
	    public void delete(T entity) throws EntityDeleteException, EntityNotAllowedException {
	        if (logger.isTraceEnabled()) {
	            logger.trace("delete:init:entity=" + entity);
	        }
	        assertAccess(entity);
	        try {
	            entity = getEntityManager().merge(entity);
	            getEntityManager().remove(entity);
	        } catch (Exception e) {
	            throw new EntityDeleteException(e);
	        }
	        if (logger.isTraceEnabled()) {
	            logger.trace("delete:done:entity=" + entity);
	        }
	    }

	    protected void copyProperties(T targetEntity, T sourceEntity) {
	        copyProperties(targetEntity, sourceEntity, false);
	    }

	    @SuppressWarnings("serial")
	    private final List<String> invalidPropertyFilter = new ArrayList<String>() {
	        {
	            add("Id");
	            add("Uuid");
	            add("CreateDate");
	            add("CreatePrincipal");
	            add("UpdateDate");
	            add("UpdatePrincipal");
	        }
	    };

	    private void copyProperties(Object targetEntity, Object sourceEntity, boolean deepCopy) {
	        for (Method m : sourceEntity.getClass().getMethods()) {
	            // removed copying of static fields, like: getInstance, is, valueOf, etc.
	            if ((m.getName().startsWith("get") || m.getName().startsWith("is")) && m.getParameterTypes().length == 0 &&
	                ((m.getModifiers() & Modifier.STATIC) == 0)) {
	                String property = m.getName().startsWith("get") ? m.getName().substring(3) : m.getName().substring(2);
	                if (!invalidPropertyFilter.contains(property)) {
	                    if (m.getReturnType().equals(String.class) || m.getReturnType().equals(Integer.class)
	                            || m.getReturnType().equals(BigDecimal.class) || m.getReturnType().equals(int.class)
	                            || m.getReturnType().equals(Long.class) || m.getReturnType().equals(long.class)
	                            || m.getReturnType().equals(Date.class) || m.getReturnType().equals(Boolean.class)
	                            || m.getReturnType().equals(boolean.class) || m.getReturnType().isEnum()
	                            || (deepCopy && (m.getReturnType().equals(List.class)) || (m.getReturnType().getPackage() != null
	                                    && m.getReturnType().getPackage().getName().startsWith(entityPackage)))) {
	                        try {
	                            Object value = m.invoke(sourceEntity, new Object[] {});
	                            if (value != null) {
	                                if (value instanceof GenericEntity) {
	                                    Object targetValue = m.invoke(targetEntity, new Object[] {});
	                                    if (targetValue != null) {
	                                        copyProperties(targetValue, value, false);
	                                    }
	                                    else {
	                                        Method setter = targetEntity.getClass().getMethod("set" + property,
	                                                new Class[] { m.getReturnType() });
	                                        setter.invoke(targetEntity, new Object[] { value });
	                                    }
	                                } else {
	                                    Method setter = targetEntity.getClass().getMethod("set" + property,
	                                            new Class[] { m.getReturnType() });
	                                    setter.invoke(targetEntity, new Object[] { value });
	                                }
	                            }
	                        } catch (NoSuchMethodException e) {
	                          
	                        } catch (Exception e) {
	                            logger.error("Error", e);
	                        }
	                    }
	                }
	            }
	        }
	    }

	    protected void refreshProperties(T sourceEntity) {
	        for (Method m : sourceEntity.getClass().getMethods()) {
	            if ((m.getName().startsWith("get") || m.getName().startsWith("is")) && m.getParameterTypes().length == 0) {
	                String property = m.getName().startsWith("get") ? m.getName().substring(3) : m.getName().substring(2);
	                if (!property.equals("Id") && !property.equals("Uuid")) {
	                    if ((m.getReturnType().getPackage() != null
	                            && m.getReturnType().getPackage().getName().startsWith(entityPackage))) {
	                        try {
	                            Object value = m.invoke(sourceEntity, new Object[] {});
	                            if (value != null) {
	                                if ((m.getReturnType().getPackage() != null
	                                        && m.getReturnType().getPackage().getName().startsWith(entityPackage))) {
	                                    Object id = getRawProperty(value, "Id");
	                                    if (id != null && id instanceof Long) {
	                                        value = getEntityManager().find(m.getReturnType(), (Long) id);
	                                    }
	                                }
	                                Method setter = sourceEntity.getClass().getMethod("set" + property,
	                                        new Class[] { m.getReturnType() });
	                                setter.invoke(sourceEntity, new Object[] { value });
	                            }
	                        } catch (NoSuchMethodException e) {
	                            
	                        } catch (Exception e) {
	                            logger.error("Error", e);
	                        }
	                    }
	                }
	            }
	        }
	    }

	    protected void runJpqlUpdate(String jpql) throws EntityUpdateException {
	        try {
	            Query query = getEntityManager().createQuery(jpql);
	            query.executeUpdate();
	            getEntityManager().flush();
	        } catch (Exception e) {
	            throw new EntityUpdateException(e);
	        }
	    }

	    protected void runSqlUpdate(String sql) throws EntityUpdateException {
	        try {
	            Query query = getEntityManager().createNativeQuery(sql);
	            query.executeUpdate();
	            getEntityManager().flush();
	        } catch (Exception e) {
	            throw new EntityUpdateException(e);
	        }
	    }

}
