package com.jiazhao.ebankspringkafka.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContacts {
    private String username;
    private ArrayList<String> contacts = new ArrayList<>();

    private void addContact(String name) {
        contacts.add(name);
    }
}
