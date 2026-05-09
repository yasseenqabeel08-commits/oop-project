
    package model;

import java.sql.*;

    /**
     * DatabaseConnection — sets up SQLite and creates all tables if they don't exist.
     * The database file is created at: hotel.db (in your project root)
     */
    public class DatabaseConnection {

        private static final String URL = "jdbc:sqlite:hotel.db";
        private static Connection connection;

        public static Connection getConnection() throws SQLException {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
                connection.setAutoCommit(true);
                createTables();
            }
            return connection;
        }

        private static void createTables() throws SQLException {
            Statement stmt = connection.createStatement();

            // ROOM TYPES
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS room_types (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                description TEXT,
                base_price REAL
            )
        """);

            // ROOMS
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS rooms (
                id INTEGER PRIMARY KEY,
                room_number TEXT NOT NULL,
                room_type_id INTEGER,
                price_per_night REAL,
                floor INTEGER,
                has_view INTEGER,
                is_available INTEGER,
                FOREIGN KEY (room_type_id) REFERENCES room_types(id)
            )
        """);

            // AMENITIES
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS amenities (
                id INTEGER PRIMARY KEY,
                name TEXT NOT NULL,
                description TEXT,
                price REAL
            )
        """);

            // ROOM AMENITIES (junction table)
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS room_amenities (
                room_id INTEGER,
                amenity_id INTEGER,
                PRIMARY KEY (room_id, amenity_id),
                FOREIGN KEY (room_id) REFERENCES rooms(id),
                FOREIGN KEY (amenity_id) REFERENCES amenities(id)
            )
        """);

            // GUESTS
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS guests (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                name TEXT,
                dob TEXT,
                gender TEXT,
                balance REAL,
                address TEXT
            )
        """);

            // STAFF
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS staff (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                name TEXT,
                dob TEXT,
                gender TEXT,
                role TEXT,
                hours_per_week INTEGER
            )
        """);

            // RESERVATIONS
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS reservations (
                id INTEGER PRIMARY KEY,
                room_id INTEGER,
                guest_username TEXT,
                check_in TEXT,
                check_out TEXT,
                status TEXT DEFAULT 'PENDING',
                FOREIGN KEY (room_id) REFERENCES rooms(id),
                FOREIGN KEY (guest_username) REFERENCES guests(username)
            )
        """);

            // INVOICES
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS invoices (
                id INTEGER PRIMARY KEY,
                reservation_id INTEGER,
                payment_method TEXT,
                is_paid INTEGER DEFAULT 0,
                FOREIGN KEY (reservation_id) REFERENCES reservations(id)
            )
        """);

            stmt.close();
        }
    }

