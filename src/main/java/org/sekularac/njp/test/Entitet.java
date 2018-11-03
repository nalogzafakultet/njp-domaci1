package org.sekularac.njp.test;

import org.sekularac.njp.annotations.classes.Entity;
import org.sekularac.njp.annotations.classes.Table;

@Entity
@Table(name = "ENTITET")
public class Entitet {

    private long id;
    private String name;
    private String type;

    public Entitet() {
        System.out.println("Instanciram entitet.");
    }

}
