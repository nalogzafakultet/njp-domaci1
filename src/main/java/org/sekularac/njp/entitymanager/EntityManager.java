package org.sekularac.njp.entitymanager;

import org.sekularac.njp.annotations.classes.Entity;
import org.sekularac.njp.entitymanager.exceptions.NoEntityException;
import org.sekularac.njp.entitymanager.exceptions.NoPrimaryKeyException;
import org.sekularac.njp.entitymanager.exceptions.NoTransactionException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class EntityManager {

    private Transaction transaction;

    public Transaction getTransaction() {
        if (transaction == null) {
            transaction = new Transaction();
        }
        return transaction;
    }

    public void persist(Object obj) {

        // If persist isn't within the scope of transaction
        if (transaction == null || !transaction.isActive()) {
            throw new NoTransactionException("Transaction doesn't exist! Please begin a transaction.");
        }

        // If we're trying to persist a non-entity
        if (!EntityUtils.isEntity(obj.getClass())) {
            transaction.rollback();
            throw new NoEntityException("This object isnt an Entity!");
        }

        if (!EntityUtils.isPrimaryKeyPresent(obj)) {
            throw new NoPrimaryKeyException("Class " + obj.getClass() + " has no primary key field!");
        }

        Map<String, Object> keyVals = EntityUtils.getEntityValues(obj);

        String tableName = EntityUtils.getTableName(obj.getClass());
        List<String> columnNames = EntityUtils.getColumnNames(keyVals);

        String columnsQuery = EntityUtils.insertColumnNamesQuery(columnNames);

        String valuesForColumns = EntityUtils.valuesForColumnsQuery(columnNames, keyVals);

        String insertStatement = String.format(
                "INSERT INTO %s (%s) VALUES (%s)",
                tableName, columnsQuery, valuesForColumns
        );

        transaction.addQuery(insertStatement);
    }

    public Object find(Class aClass, Object primaryKey) {

        if (primaryKey == null) {
            throw new NullPointerException("primaryKey is null");
        }

        if (!EntityUtils.isEntity(aClass)) {
            throw new NoEntityException("This class is not an entity!");
        }

        if (transaction == null || !transaction.isActive()) {
            throw new NoTransactionException("There should be a transaction scope active!");
        }

        Field primaryKeyField = EntityUtils.findPrimaryKeyField(aClass);
        if (primaryKeyField == null) {
            transaction.rollback();
            throw new NoPrimaryKeyException("Class " + aClass + " has no primary key field!");
        }

        Class primaryKeyClass = primaryKeyField.getType();

        if (primaryKey.getClass() != primaryKeyClass) {
            System.out.println(primaryKey.getClass().getSimpleName());
            System.out.println(primaryKeyClass);
            transaction.rollback();
            throw new RuntimeException("Primary key provided is not the same as the one with ID");
        }

        String tableName = EntityUtils.getTableName(aClass);
        String columnName = EntityUtils.getIdColumnName(primaryKeyField);
        String query = String.format(
                "SELECT * FROM %s WHERE %s=%s",
                tableName, columnName, primaryKey.toString()
        );

        transaction.addQuery(query);
        return null;
    }

    public void remove(Object obj) {

    }

    public Object merge(Object obj) {
        return null;
    }

}
