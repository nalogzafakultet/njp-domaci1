package org.sekularac.njp.test;

import org.sekularac.njp.entitymanager.EntityManager;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Entitet entitet = new Entitet(1L, "Entitet1", "Tip", new Date());

        EntityManager entityManager = new EntityManager();
        entityManager.getTransaction().begin();
        entityManager.find(Entitet.class, 2L);
        entityManager.find(Entitet.class, 1L);
        entityManager.getTransaction().commit();

//        for (Field field: entitet.getClass().getDeclaredFields()) {
//            System.out.println(EntityUtils.getColumnName(field, entitet));
//        }
    }
}