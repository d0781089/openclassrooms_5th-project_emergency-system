package com.safetynet.emergencysystem.model;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("personWithAgeFilter")
public class PersonWithAge extends Person {

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
