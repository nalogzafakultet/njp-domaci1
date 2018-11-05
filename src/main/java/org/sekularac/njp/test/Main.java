package org.sekularac.njp.test;

import org.sekularac.njp.annotations.enums.GenerationType;
import org.sekularac.njp.entities.Entitet;
import org.sekularac.njp.entitymanager.EntityManager;

public class Main {
    public static void main(String[] args) {
//        Entitet entitet = new Entitet(1L, "Entitet1", "Tip", new Date(), GenerationType.DB_GENERATION);

//        Drugar drugar = new Drugar();
//        drugar.setFirstName("Nemanja");
//        drugar.setId(1L);

        EntityManager entityManager = new EntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist(drugar);
//        entityManager.getTransaction().commit();

        Entitet entitet = new Entitet();
//        entitet.setDrugar(drugar);
//        entitet.setDate(new Date());
        entitet.setId(1L);
        entitet.setGenerationType(GenerationType.DB_GENERATION);

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
    }

}