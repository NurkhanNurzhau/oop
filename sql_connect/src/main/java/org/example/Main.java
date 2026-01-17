package org.example;

import org.example.config.Db;
import org.example.dao.ProductDAO;
import org.example.dao.ShopperDAO;
import org.example.entity.Product;
import org.example.entity.Shopper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        try {
            createTablesIfNotExist();

            ShopperDAO shopperDAO = new ShopperDAO();
            ProductDAO productDAO = new ProductDAO();

            // WRITE (INSERT)
            shopperDAO.create(new Shopper(0, "Nurkhan Nurzhau", "nurkhan@example.com", "+7 777 000 00 00"));
            productDAO.create(new Product(0, "Keyboard", 15000.00, 20));

            // READ (SELECT)
            System.out.println("=== SHOPPERS ===");
            shopperDAO.readAll().forEach(System.out::println);

            System.out.println("\n=== PRODUCTS (e_commerce table) ===");
            productDAO.readAll().forEach(System.out::println);

            // UPDATE
            shopperDAO.updateEmail(1, "nurkhan.new@example.com");
            productDAO.updateStock(1, 15);

            System.out.println("\n=== AFTER UPDATE ===");
            shopperDAO.readAll().forEach(System.out::println);
            productDAO.readAll().forEach(System.out::println);

            // DELETE
            shopperDAO.delete(1);
            productDAO.delete(1);

            System.out.println("\n=== AFTER DELETE ===");
            System.out.println("shoppers = " + shopperDAO.readAll().size());
            System.out.println("products = " + productDAO.readAll().size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTablesIfNotExist() throws SQLException {
        String shopperSql = """
                CREATE TABLE IF NOT EXISTS shopper (
                    shopper_id SERIAL PRIMARY KEY,
                    full_name  VARCHAR(120) NOT NULL,
                    email      VARCHAR(120) UNIQUE NOT NULL,
                    phone      VARCHAR(30),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
                """;

        String ecommerceSql = """
                CREATE TABLE IF NOT EXISTS e_commerce (
                    product_id SERIAL PRIMARY KEY,
                    name       VARCHAR(120) NOT NULL,
                    price      NUMERIC(10,2) NOT NULL CHECK (price >= 0),
                    stock      INT NOT NULL CHECK (stock >= 0),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );
                """;

        try (Connection con = Db.getConnection(); Statement st = con.createStatement()) {
            st.execute(shopperSql);
            st.execute(ecommerceSql);
        }
    }
}
