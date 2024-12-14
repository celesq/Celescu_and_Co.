package org.poo.main;

import java.util.ArrayList;
import java.util.List;

public class Comerciant {
    private int id;
    private String description;
    private List<String> commerciants;

    public Comerciant(int id, String description, List<String> commerciants) {
        this.id = id;
        this.description = description;
        this.commerciants = commerciants;
    }

    public List<String> getCommerciants() {
        return commerciants;
    }

    public void setCommerciants(ArrayList<String> commerciants) {
        this.commerciants = commerciants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
