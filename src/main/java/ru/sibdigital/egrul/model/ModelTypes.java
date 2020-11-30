package ru.sibdigital.egrul.model;

public enum ModelTypes {
    EGRUL_LOAD((short) 0),
    EGRIP_LOAD((short) 1);

    private final short value;

    private ModelTypes(short value) {
        this.value = value;
    }


    public short getValue() {
        return value;
    }
}
