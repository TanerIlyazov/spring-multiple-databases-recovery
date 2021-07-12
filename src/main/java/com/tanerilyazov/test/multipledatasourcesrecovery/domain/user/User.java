package com.tanerilyazov.test.multipledatasourcesrecovery.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@NoArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String name;
    private String email;
    private int age;
}
