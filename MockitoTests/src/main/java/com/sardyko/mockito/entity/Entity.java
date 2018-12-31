package com.sardyko.mockito.entity;

import com.sardyko.mockito.service.Service;

public class Entity {
    private Service service;

    Entity(Service service) {
        this.service = service;
    }

    public int getNumber() {
        return this.service.getNuberFromService();
    }
}
