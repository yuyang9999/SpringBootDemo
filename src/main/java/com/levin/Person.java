package com.levin;


import lombok.Data;

/**
 * Created by yangyu on 13/8/17.
 */

@Data
public class Person {
    private String name;
    private String email;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
