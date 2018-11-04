package org.sekularac.njp.entitymanager;

import org.sekularac.njp.annotations.classes.Entity;
import org.sekularac.njp.annotations.classes.MappedSuperclass;
import org.sekularac.njp.annotations.classes.Table;
import org.sekularac.njp.annotations.enums.EnumeratedType;
import org.sekularac.njp.annotations.enums.TemporalType;
import org.sekularac.njp.annotations.field.*;
import org.sekularac.njp.entitymanager.exceptions.ShouldntBeNullException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class EntityUtils {

    public static boolean isEntity(Class aClass) {
        return aClass.getAnnotation(Entity.class) != null;
    }

    public static List<Class> findMappedSuperClasses(Class aClass) {
        List<Class> superClasses = new ArrayList<>();

        Class superClass = aClass.getSuperclass();

        while (!superClass.getName().equals("java.lang.Object")) {

            Annotation annotation = superClass.getAnnotation(MappedSuperclass.class);
            if (annotation == null) {
                throw new RuntimeException("No mapped superclass annotation, while extending a class!");
            }

            superClasses.add(superClass);
            superClass = superClass.getSuperclass();
        }

        return superClasses;
    }

    public static Object handleEnum(Field field, Object object) {
        if (field.isAnnotationPresent(Enumerated.class)) {
            Enumerated enumerated = field.getAnnotation(Enumerated.class);
            field.setAccessible(true);

            Object obj = null;
            try {
                obj = (Object) field.get(object);
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }

            Enum enumObj = (Enum) obj;
            if (enumerated.value() == EnumeratedType.ORDINAL) {
                return enumObj.ordinal();
            } else {
                return enumObj.toString();
            }
        }
        return null;
    }

    public static String getDatedString(Field field, Object obj) {

        DateFormat dateFormat;

        if (field.isAnnotationPresent(Temporal.class)) {
            Temporal temporalAnnotation = field.getAnnotation(Temporal.class);
            TemporalType type = temporalAnnotation.value();
            switch (type) {
                case DATE:
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    break;
                case TIME:
                    dateFormat = new SimpleDateFormat("HH:mm:ss");
                    break;
                case TIMESTAMP:
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    break;
                default:
                    dateFormat = null;
                    return null;
            }

            field.setAccessible(true);
            try {
                Date objectDate = (Date) field.get(obj);
                return dateFormat.format(objectDate);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Field findPrimaryKeyField(Class aClass) {

        List<Class> superClasses = findMappedSuperClasses(aClass);

        for (Class superClass: superClasses) {
            Field pk = findPKWithinAClass(superClass);
            if (pk != null) {
                return pk;
            }
        }

        return findPKWithinAClass(aClass);
    }

    private static Field findPKWithinAClass(Class aClass) {
        Field[] fields = aClass.getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        return null;
    }

    public static boolean isPrimaryKeyPresent(Object object) {
        List<Class> superClasses = findMappedSuperClasses(object.getClass());

        for (Class aClass: superClasses) {
            if (findMappedSuperClasses(aClass) != null) {
                return true;
            }
        }

        return findPrimaryKeyField(object.getClass()) != null;
    }

    /**
     * Gets the column name from a field.
     * If there's no column name provided returns an upper case version of property name.
     * @param field
     * @return
     */
    public static String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (column.name().equals("")) {
                return field.getName().toUpperCase();
            } else {
                return column.name();
            }
        } else {
            return null;
        }
    }

    public static String getIdColumnName(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            if (field.isAnnotationPresent(Column.class)) {
                return getColumnName(field);
            } else {
                return field.getName().toUpperCase();
            }
        }
        return null;
    }

    public static String getTableName(Class aClass) {

        Annotation annotation = aClass.getAnnotation(Table.class);

        if (annotation == null) {
            Entity entity = (Entity) aClass.getAnnotation(Entity.class);
            if (entity.table().isEmpty()) {
                return aClass.getSimpleName().toUpperCase();
            } else {
                return entity.table();
            }
        }

        Table tableAnnotation = (Table) annotation;

        return tableAnnotation.name();
    }

    public static Map<String, Object> getEntityValues(Object object) {
        Map<String, Object> keyVal = new HashMap<>();

        List<Class> superClasses = findMappedSuperClasses(object.getClass());

        for (Class aClass : superClasses) {
            keyVal.putAll(extractValuesForClass(aClass, object));
        }

        keyVal.putAll(extractValuesForClass(object.getClass(), object));

        return keyVal;
    }

    private static Map<String, Object> extractValuesForClass(Class aClass, Object object) {
        Map<String, Object> keyVal = new HashMap<>();

        Field[] fields = aClass.getDeclaredFields();

        for (Field field: fields) {
            if (isColumn(field)) {
                String columnName = getColumnName(field);
                Object value = valueForAnnotatedField(field, object);
                keyVal.put(columnName, value);
            } else if (isId(field)) {
                String columnName = getIdColumnName(field);
                Object value = valueForAnnotatedField(field, object);
                keyVal.put(columnName, value);
            }
        }

        return keyVal;
    }

    private static boolean isColumn(Field field) {
        return field.isAnnotationPresent(Column.class);
    }

    private static boolean isId(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    /**
     * Gets the column value for the annotated field with Object or Id
     * @param field     Field that we're investigating.
     * @param object    Object that we're investigating on.
     * @return          Value of the field if present, null if not.
     */
    public static Object valueForAnnotatedField(Field field, Object object) {

        validateNotNull(field, object);

        if (field.isAnnotationPresent(Enumerated.class)) {
            return handleEnum(field, object);
        }

        if (field.isAnnotationPresent(Temporal.class)) {
            return getDatedString(field, object);
        }

        if (field.isAnnotationPresent(Column.class) ||
            field.isAnnotationPresent(Id.class)) {
            field.setAccessible(true);
            try {
                return field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void validateNotNull(Field field, Object object) {
        if (field.isAnnotationPresent(NotNull.class)) {
            field.setAccessible(true);
            try {
                Object nullableObject = field.get(object);
                if (nullableObject == null) {
                    throw new ShouldntBeNullException("Field '" + field.getName() + "' shouldn't be null!");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> getColumnNames(Map<String, Object> keyValMap) {
        return new ArrayList<>(keyValMap.keySet());
    }

    public static String insertColumnNamesQuery(List<String> columns) {
        StringJoiner joiner = new StringJoiner(",");
        for (String column: columns) {
            joiner.add(column);
        }
        return joiner.toString();
    }

    public static String valuesForColumnsQuery(List<String> columnNames, Map<String, Object> keyVals) {
        StringJoiner joiner = new StringJoiner(",");
        for (String columnName: columnNames) {
            Object ret = keyVals.get(columnName);
            if (ret == null) {
                joiner.add("NULL");
            } else {
                joiner.add(ret.toString());
            }
        }
        return joiner.toString();
    }
}
