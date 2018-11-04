package org.sekularac.njp.entitymanager;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private List<String> queries;
    private boolean active;

    public void addQuery(String query) {
        queries.add(query);
    }

    public void begin() {
        queries = new ArrayList<>();
        active = true;
    }

    public void commit() {
        for (String query: queries) {
            System.out.println(query);
        }

        active = false;
    }

    public void rollback() {
        active = false;
        queries = new ArrayList<>();
    }

    public boolean isActive() {
        return active;
    }
}
