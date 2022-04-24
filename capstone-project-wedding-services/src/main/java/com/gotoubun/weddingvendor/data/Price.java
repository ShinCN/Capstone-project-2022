package com.gotoubun.weddingvendor.data;

/**
 * The enum Price.
 */
public enum Price {

    /**
     * The From 10 m.
     */
    FROM10M((short) 0, "<10.000.000 VND"),

    /**
     * From 10 mto 20 m price.
     */
    FROM10MTO20M((short) 1, "10.000.000- 20.000.000 VND"),

    /**
     * From 20 mto 30 m price.
     */
    FROM20MTO30M((short) 2, "20.000.000- 30.000.000 VND"),

    /**
     * From 30 mto 40 m price.
     */
    FROM30MTO40M((short) 3, "30.000.000- 40.000.000 VND"),

    /**
     * From 40 mto 50 m price.
     */
    FROM40MTO50M((short) 4, "40.000.000- 50.000.000 VND"),

    /**
     * Greater than 50 m price.
     */
    GREATER50M((short) 5, ">50.000.000 VND");

    private final short value;
    private final String name;

    Price(short value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * As short short.
     *
     * @return the short
     */
    public short asShort() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Of price.
     *
     * @param name the name
     * @return the price
     */
    public static Price of(String name) {
        for (Price price : Price.values()) {
            if (price.name.equals(name)) {
                return price;
            }
        }
       return null;
    }
}