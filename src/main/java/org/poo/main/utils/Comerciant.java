package org.poo.main.utils;

import java.util.ArrayList;
import java.util.List;

public class Comerciant {
    private int id;
    private String description;
    private List<String> commerciants;

    public Comerciant(final int id, final String description, final List<String> commerciants) {
        this.id = id;
        this.description = description;
        this.commerciants = commerciants;
    }

    /**
     *
     * @return commerciants
     */
    public List<String> getCommerciants() {
        return commerciants;
    }

    /**
     *
     * @param commerciants setter
     */
    public void setCommerciants(final ArrayList<String> commerciants) {
        this.commerciants = commerciants;
    }

    /**
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description setter
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id id
     */
    public void setId(final int id) {
        this.id = id;
    }
}
