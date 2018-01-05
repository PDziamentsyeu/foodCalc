package main.common.utils;

import java.lang.reflect.ParameterizedType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import main.common.model.GenericEntityWithMeta;


public class CRUDUtil {

    @SuppressWarnings("unused")
    private static Log logger = LogFactory.getLog(CRUDUtil.class);

    @SuppressWarnings("rawtypes")
    public static Class getEntityClass(Class parameterizedClass) {
        Class entityClass = null;
        if (parameterizedClass.getGenericSuperclass() instanceof ParameterizedType) {
            entityClass = (Class) ((ParameterizedType) parameterizedClass.getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            if (parameterizedClass.getSuperclass() != null
                    && parameterizedClass.getSuperclass().getGenericSuperclass() instanceof ParameterizedType) {
                entityClass = (Class) ((ParameterizedType) parameterizedClass.getSuperclass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
            } else {
                entityClass = parameterizedClass;
            }
        }
        return entityClass;
    }

    @SuppressWarnings("rawtypes")
    public static String getEntityName(Class parameterizedClass) {
        return getEntityClass(parameterizedClass).getSimpleName();
    }

    @SuppressWarnings("rawtypes")
    public static String getEntityText(Class parameterizedClass) {
        String entityName = getEntityName(parameterizedClass);
        String entityText = "";
        for (int p = 0; p < entityName.length(); p++) {
            if (entityName.charAt(p) >= 'A' && entityName.charAt(p) <= 'Z') {
                if (entityText.length() > 0) {
                    entityText += " ";
                }
            }
            entityText += entityName.charAt(p);
        }
        return entityText;
    }

    public static <T extends GenericEntityWithMeta> T fromUuid(String uuid, Class<T> clazz) {
        try {
            T entityWithUuid = (T) clazz.newInstance();
            entityWithUuid.setUuid(uuid);
            return entityWithUuid;
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

}

