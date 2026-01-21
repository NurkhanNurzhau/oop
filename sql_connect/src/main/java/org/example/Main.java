package org.example;

import org.example.config.Db;
import org.example.dao.ProductDAO;
import org.example.dao.ShopperDAO;
import org.example.entity.Product;
import org.example.entity.Shopper;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            createTablesIfNotExist();
            resetTables();

            ShopperDAO shopperDAO = new ShopperDAO();
            ProductDAO productDAO = new ProductDAO();
            Scanner sc = new Scanner(System.in);

            while (true) {
                printMenu();
                int choice = readInt(sc, "Choose option: ");

                switch (choice) {
                    case 1 -> {
                        shopperDAO.create(new Shopper(
                                0,
                                readLine(sc, "Full name: "),
                                readLine(sc, "Email: "),
                                readLine(sc, "Phone: ")
                        ));
                        printShoppers(shopperDAO);
                    }
                    case 2 -> printShoppers(shopperDAO);
                    case 3 -> {
                        shopperDAO.updateEmail(
                                readInt(sc, "Shopper ID: "),
                                readLine(sc, "New email: ")
                        );
                        printShoppers(shopperDAO);
                    }
                    case 4 -> {
                        printShoppers(shopperDAO);
                        boolean ok = shopperDAO.delete(readInt(sc, "Shopper ID to delete: "));
                        System.out.println(ok ? "Deleted" : "No such ID");
                        printShoppers(shopperDAO);
                    }
                    case 5 -> {
                        productDAO.create(new Product(
                                0,
                                readLine(sc, "Product name: "),
                                readDouble(sc, "Price: "),
                                readInt(sc, "Stock: ")
                        ));
                        printProducts(productDAO);
                    }
                    case 6 -> printProducts(productDAO);
                    case 7 -> {
                        productDAO.updateStock(
                                readInt(sc, "Product ID: "),
                                readInt(sc, "New stock: ")
                        );
                        printProducts(productDAO);
                    }
                    case 8 -> {
                        printProducts(productDAO);
                        boolean ok = productDAO.delete(readInt(sc, "Product ID to delete: "));
                        System.out.println(ok ? "Deleted" : "No such ID");
                        printProducts(productDAO);
                    }
                    case 9 -> {
                        System.out.println("Bye!");
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void resetTables() throws SQLException {
        try (Connection con = Db.getConnection(); Statement st = con.createStatement()) {
            st.execute("TRUNCATE TABLE e_commerce RESTART IDENTITY");
            st.execute("TRUNCATE TABLE shopper RESTART IDENTITY");
        }
    }

    private static void createTablesIfNotExist() throws SQLException {
        try (Connection con = Db.getConnection(); Statement st = con.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS shopper (
                    shopper_id SERIAL PRIMARY KEY,
                    full_name VARCHAR(120) NOT NULL,
                    email VARCHAR(120) UNIQUE NOT NULL,
                    phone VARCHAR(30),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
            st.execute("""
                CREATE TABLE IF NOT EXISTS e_commerce (
                    product_id SERIAL PRIMARY KEY,
                    name VARCHAR(120) NOT NULL,
                    price NUMERIC(10,2) NOT NULL CHECK (price >= 0),
                    stock INT NOT NULL CHECK (stock >= 0),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
        }
    }

    private static void printMenu() {
        System.out.println("""
                1 Add Shopper
                2 Show Shoppers
                3 Update Shopper Email
                4 Delete Shopper
                5 Add Product
                6 Show Products
                7 Update Product Stock
                8 Delete Product
                9 Exit
                """);
    }

    private static void printShoppers(ShopperDAO dao) throws SQLException {
        List<Shopper> list = dao.readAll();
        if (list.isEmpty()) System.out.println("(empty)");
        else list.forEach(System.out::println);
    }

    private static void printProducts(ProductDAO dao) throws SQLException {
        List<Product> list = dao.readAll();
        if (list.isEmpty()) System.out.println("(empty)");
        else list.forEach(System.out::println);
    }

    private static String readLine(Scanner sc, String p) {
        System.out.print(p);
        return sc.nextLine();
    }

    private static int readInt(Scanner sc, String p) {
        while (true) {
            try {
                System.out.print(p);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception ignored) {}
        }
    }

    private static double readDouble(Scanner sc, String p) {
        while (true) {
            try {
                System.out.print(p);
                return Double.parseDouble(sc.nextLine());
            } catch (Exception ignored) {}
        }
    }
}
