package com.tanerilyazov.test.multipledatasourcesrecovery.domain.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter @Setter
public class Product {
    private int id;
    private String name;
    private double price;
}