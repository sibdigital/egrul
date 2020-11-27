package ru.sibdigital.egrul.model;

public enum ModelTypes {
    EGRUL_LOAD(0),
    EGRIP_LOAD(1);

    private final int value;

    private ModelTypes(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }
}
