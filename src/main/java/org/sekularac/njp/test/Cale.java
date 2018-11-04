package org.sekularac.njp.test;

import org.sekularac.njp.annotations.classes.MappedSuperclass;
import org.sekularac.njp.annotations.field.Id;

@MappedSuperclass
public class Cale {

    @Id
    private Long id;

    public Cale() {

    }

    public Cale(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
