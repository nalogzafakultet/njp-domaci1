package org.sekularac.njp.test;

import org.sekularac.njp.annotations.classes.Entity;
import org.sekularac.njp.annotations.enums.GenerationType;
import org.sekularac.njp.entitymanager.EntityManager;
import org.sekularac.njp.entitymanager.EntityUtils;

import java.lang.reflect.Field;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
//        Entitet entitet = new Entitet(1L, "Entitet1", "Tip", new Date(), GenerationType.DB_GENERATION);

        Entitet entitet = new Entitet();
//        entitet.setDate(new Date());
        entitet.setId(1L);
        entitet.setGenerationType(GenerationType.DB_GENERATION);

        EntityManager entityManager = new EntityManager();
        entityManager.getTransaction().begin();
        entityManager.find(Entitet.class, 2L);
        entityManager.find(Entitet.class, 1L);
        entityManager.persist(entitet);
        entityManager.getTransaction().commit();
//
//        for (Field field: entitet.getClass().getDeclaredFields()) {
//            System.out.println(EntityUtils.getColumnName(field, entitet));
//        }



//        System.out.println(EntityUtils.getEntityValues(entitet));

        Integer integer = new Integer(5);
        System.out.println(integer.toString());
    }

}