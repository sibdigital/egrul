package ru.sibdigital.egrul.dto;

import ru.sibdigital.egrul.dto.egrip.EGRIP;

public class EgripResponse {

    private EGRIP.СвИП data;

    public EGRIP.СвИП getData() {
        return data;
    }

    public void setData(EGRIP.СвИП data) {
        this.data = data;
    }
}
