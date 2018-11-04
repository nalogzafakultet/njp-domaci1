package org.sekularac.njp.entities;

import org.sekularac.njp.annotations.classes.Entity;
import org.sekularac.njp.annotations.field.Id;

@Entity(table = "BOOK")
public class Book {

    @Id
    private Long id;



}
