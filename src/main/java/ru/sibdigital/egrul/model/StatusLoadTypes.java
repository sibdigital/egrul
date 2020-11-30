package ru.sibdigital.egrul.model;

public enum StatusLoadTypes {
    LOAD_START((short) 0),
    SUCCESSFULLY_LOADED((short) 1),
    COMPLETED_WITH_ERRORS((short) 2);


    private final short value;

    private StatusLoadTypes(short value) {
        this.value = value;
    }


    public short getValue() {
        return value;
    }
}
