package org.sekularac.njp.entitymanager;

import org.sekularac.njp.annotations.classes.Entity;
import org.sekularac.njp.entitymanager.exceptions.NoEntityException;
import org.sekularac.njp.entitymanager.exceptions.NoTransactionException;

import java.lang.annotation.Annotation;

public class EntityManager {
    private Transaction transaction;

    public Transaction getTransaction() {
        return new Transaction();
    }

    public void persist(Object obj) {

        if (transaction == null || !transaction.isActive()) {
            throw new NoTransactionException("Transaction doesn't exist! Please begin a transaction.");
        }

        if (!isEntity(obj)) {
            transaction.rollback();
            throw new NoEntityException("This object isnt an Entity!");
        }


    }

    public Object find(Class aClass, Object primaryKey) {
        return null;
    }

    public void remove(Object obj) {

    }

    public Object merge(Object obj) {
        return null;
    }

    private boolean isEntity(Object obj) {
        Class objectClass = obj.getClass();
        Annotation[] annotations = objectClass.getAnnotations();

        for (Annotation annotation: annotations) {
            if (annotation instanceof Entity) {
                return true;
            }
        }

        return false;
    }
}
