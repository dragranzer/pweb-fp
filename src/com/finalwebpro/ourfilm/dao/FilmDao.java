package com.finalwebpro.ourfilm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.finalwebpro.ourfilm.bean.Film;

public class FilmDao {
	
	private static String dbUrl = "jdbc:mysql://localhost:3306/userdb?useSSL=false";
	private static String dbUname = "root";
	private static String dbPassword = "";
	
	private static final String INSERT_FILMS_SQL = "INSERT INTO films" + "  (name, distributor, genre, year, country, duration,trailer) VALUES "
			+ " (?, ?, ?,?, ?, ?,?);";

	private static final String SELECT_FILM_BY_ID = "select id, name, distributor, genre, year, country, duration,trailer from films where id =?";
	private static final String SELECT_ALL_FILMS = "select * from films";
	private static final String DELETE_FILMS_SQL = "delete from films where id = ?;";
	private static final String UPDATE_FILMS_SQL = "update films set name=?, distributor=?, genre=?, year=?, country=?, duration=?,trailer=? where id = ?;";
	
	public FilmDao() {
		
	}
	
	protected static Connection getConnection()
	{
		Connection connection = null;
		try {
			//Class.forName("dbDriver");
			connection = DriverManager.getConnection(dbUrl, dbUname, dbPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	//insert film
	public static void insertFilm(Film film)
	{
		System.out.println(INSERT_FILMS_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FILMS_SQL)) {
			preparedStatement.setString(1, film.getName());
			preparedStatement.setString(2, film.getDistributor());
			preparedStatement.setString(3, film.getGenre());
			preparedStatement.setString(4, film.getYear());
			preparedStatement.setString(5, film.getCountry());
			preparedStatement.setString(6, film.getDuration());
			preparedStatement.setString(7, film.getTrailer());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
		
	//select film by id
	public static Film selectFilm(int id)
	{
		Film film = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FILM_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("name");
				String distributor = rs.getString("distributor");
				String genre = rs.getString("genre");
				String year = rs.getString("year");
				String country = rs.getString("country");
				String duration = rs.getString("duration");
				String trailer = rs.getString("trailer");
				film = new Film(id, name, distributor, genre, year, country, duration,trailer);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return film;
	}
		
	//select all films
	public List<Film> selectAllFilms()
	{
		// using try-with-resources to avoid closing resources (boiler plate code)
				List<Film> films = new ArrayList<>();
				// Step 1: Establishing a Connection
				try (Connection connection = getConnection();

						// Step 2:Create a statement using connection object
					PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FILMS);) {
					System.out.println(preparedStatement);
					// Step 3: Execute the query or update query
					ResultSet rs = preparedStatement.executeQuery();

					// Step 4: Process the ResultSet object.
					while (rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						String distributor = rs.getString("distributor");
						String genre = rs.getString("genre");
						String year = rs.getString("year");
						String country = rs.getString("country");
						String duration = rs.getString("duration");
						String trailer = rs.getString("trailer");
						films.add(new Film(id, name, distributor, genre, year, country, duration,trailer));
					}
				} catch (SQLException e) {
					printSQLException(e);
				}
				return films;
	}
	
	//update film
	public static boolean updateFilm(Film film) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_FILMS_SQL);) {
			System.out.println("Your Film has been updated:"+statement);
			statement.setString(1, film.getName());
			statement.setString(2, film.getDistributor());
			statement.setString(3, film.getGenre());
			statement.setString(4, film.getYear());
			statement.setString(5, film.getCountry());
			statement.setString(6, film.getDuration());
			statement.setString(7, film.getTrailer());
			statement.setInt(8, film.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	
	//delete film
	
	public boolean deleteFilm(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_FILMS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	private static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
	
}
