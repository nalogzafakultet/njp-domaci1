package org.sekularac.njp.entitymanager;

import org.sekularac.njp.annotations.classes.Entity;
import org.sekularac.njp.annotations.classes.MappedSuperclass;
import org.sekularac.njp.annotations.classes.Table;
import org.sekularac.njp.annotations.enums.EnumeratedType;
import org.sekularac.njp.annotations.enums.TemporalType;
import org.sekularac.njp.annotations.field.Enumerated;
import org.sekularac.njp.annotations.field.Id;
import org.sekularac.njp.annotations.field.Column;
import org.sekularac.njp.annotations.field.Temporal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntityUtils {

    public static boolean isEntity(Class aClass) {
        return aClass.getAnnotation(Entity.class) != null;
    }

    public static List<Class> findMappedSuperClasses(Object obj) {
        Class aClass = obj.getClass();
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

    public static Object handleEnum(Field field, Object obj) {
        if (field.isAnnotationPresent(Enumerated.class)) {
            try {
                Object object = field.get(obj);
                Enumerated enumerated = (Enumerated) object;

                if (enumerated.type() == EnumeratedType.ORDINAL) {
                    return enumerated.type().ordinal();
                } else {
                    return enumerated.type().toString();
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getDatedString(Field field, Object obj) {

        DateFormat dateFormat;

        if (field.isAnnotationPresent(Temporal.class)) {
            Temporal declaredAnnotation = field.getDeclaredAnnotation(Temporal.class);
            TemporalType type = declaredAnnotation.value();
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
        Field[] fields = aClass.getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        return null;
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
}
