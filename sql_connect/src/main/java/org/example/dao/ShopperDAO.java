package org.example.dao;

import org.example.config.Db;
import org.example.entity.Shopper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopperDAO {

    public void create(Shopper s) throws SQLException {
        String sql = "INSERT INTO shopper(full_name, email, phone) VALUES (?, ?, ?)";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, s.getFullName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPhone());
            ps.executeUpdate();
        }
    }

    public List<Shopper> readAll() throws SQLException {
        String sql = "SELECT shopper_id, full_name, email, phone FROM shopper ORDER BY shopper_id";
        List<Shopper> list = new ArrayList<>();

        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Shopper s = new Shopper();
                s.setShopperId(rs.getInt("shopper_id"));
                s.setFullName(rs.getString("full_name"));
                s.setEmail(rs.getString("email"));
                s.setPhone(rs.getString("phone"));
                list.add(s);
            }
        }
        return list;
    }

    public void updateEmail(int shopperId, String newEmail) throws SQLException {
        String sql = "UPDATE shopper SET email=? WHERE shopper_id=?";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newEmail);
            ps.setInt(2, shopperId);
            ps.executeUpdate();
        }
    }

    public boolean delete(int shopperId) throws SQLException {
        String sql = "DELETE FROM shopper WHERE shopper_id=?";
        try (Connection con = Db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, shopperId);
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }
}
