package org.example.dao;

import org.example.config.Db;
import org.example.entity.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void create(Product p) throws SQLException {
        String sql = "INSERT INTO e_commerce(name, price, stock) VALUES (?, ?, ?)";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, BigDecimal.valueOf(p.getPrice()));
            ps.setInt(3, p.getStock());
            ps.executeUpdate();
        }
    }

    public List<Product> readAll() throws SQLException {
        String sql = "SELECT product_id, name, price, stock FROM e_commerce ORDER BY product_id";
        List<Product> list = new ArrayList<>();

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getBigDecimal("price").doubleValue());
                p.setStock(rs.getInt("stock"));
                list.add(p);
            }
        }
        return list;
    }

    public void updateStock(int productId, int newStock) throws SQLException {
        String sql = "UPDATE e_commerce SET stock=? WHERE product_id=?";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, newStock);
            ps.setInt(2, productId);
            ps.executeUpdate();
        }
    }

    public boolean delete(int productId) throws SQLException {
        String sql = "DELETE FROM e_commerce WHERE product_id=?";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }
}
