package org.java.nations.sub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.TimeZone;

public class nations {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/nations_db";
		String user = "root";
		String password = "199801";

		try (Scanner sc = new Scanner(System.in); Connection con = DriverManager.getConnection(url, user, password)) {

			String sql = "SELECT c.name ,c.country_id, c2.name , r.name\r\n" + "FROM continents c2 \r\n"
					+ "JOIN regions r \r\n" + "ON c2.continent_id  = r.continent_id \r\n" + "JOIN  countries c \r\n"
					+ "ON r.region_id  = c.region_id \r\n" + " WHERE c.name LIKE ? " + "ORDER BY c.name ;";

			System.out.print("Ricerca Nazione: ");
			String searchS = sc.nextLine();

			try (PreparedStatement ps = con.prepareStatement(sql)) {

				ps.setString(1, "%" + searchS + "%");

				try (ResultSet rs = ps.executeQuery()) {

					while (rs.next()) {

						final String name = rs.getString(1);
						final String countryId = rs.getString(2);
						final String continentName = rs.getString(3);
						final String regionName = rs.getString(4);
						System.out
								.println(name + " - " + countryId + " - " + continentName + " - " + regionName + " - ");
					}
				}
			} catch (SQLException ex) {
				System.err.println(ex);
			}
		}

		catch (SQLException ex) {
			System.err.println("Error during connection to db");
		}
	}

	public static LocalDateTime getLocalDateTime(Timestamp time) {

		return LocalDateTime.ofInstant(Instant.ofEpochMilli(time.getTime()), TimeZone.getDefault().toZoneId());
	}
}
