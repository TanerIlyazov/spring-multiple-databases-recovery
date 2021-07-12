package com.tanerilyazov.test.multipledatasourcesrecovery.mapper;

import com.tanerilyazov.test.multipledatasourcesrecovery.domain.product.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Taner - Delta Source Bulgaria on 9.07.21.
 */
public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        product.setName(rs.getString("name"));
        product.setPrice(rs.getDouble("price"));
        product.setId(rs.getInt("id"));

        return product;
    }
}
