//commentdao

package com.finalwebpro.ourfilm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.finalwebpro.ourfilm.bean.Comment;
//import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

public class CommentDao {
	private String jdbcURL = "jdbc:mysql://localhost:3306/userdb?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "root";
	//private String jdbcDriver = "com.mysql.jdbd.Driver";

	private static final String INSERT_COMMENTS_SQL = "INSERT INTO comments" + "  (name_film, distributor_film, comment_film, date_comment) VALUES "
			+ " (?, ?, ?, ?);";

	private static final String SELECT_COMMENT_BY_ID = "select id, name_film, distributor_film, comment_film, date_comment from comments where id =?";
	private static final String SELECT_ALL_COMMENTS = "select * from comments";
	private static final String DELETE_COMMENTS_SQL = "delete from comments where id = ?;";
	private static final String UPDATE_COMMENTS_SQL = "update comments set name_film = ?,distributor_film= ?, comment_film =?, date_comment= ?, where id = ?;";

	public CommentDao() {
	}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public void insertComment(Comment comment) throws SQLException {
		System.out.println(INSERT_COMMENTS_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMMENTS_SQL)) {
			preparedStatement.setString(1, comment.getname_film());
			preparedStatement.setString(2, comment.getdistributor_film());
			preparedStatement.setString(3, comment.getcomment_film());
			preparedStatement.setString(4, comment.getdate_comment());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Comment selectComment(int id) {
		Comment comment = null;
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();
				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMMENT_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name_film = rs.getString("name_film");
				String distributor_film = rs.getString("distributor_film");
				String comment_film = rs.getString("comment_film");
				String date_comment = rs.getString("date_comment");
				comment = new Comment();
				//comment = new Comment(id, name_film, distributor_film, comment_film, date_comment);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return comment;
	}

	public List<Comment> selectAllComments() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Comment> comments = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COMMENTS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name_film = rs.getString("name_film");
				String distributor_film = rs.getString("distributor_film");
				String comment_film = rs.getString("comment_film");
				String date_comment = rs.getString("date_comment");
				comments.add(new Comment(id, name_film, distributor_film, comment_film, date_comment));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return comments;
	}

	public boolean deleteComment(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_COMMENTS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean updateComment(Comment comment) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_COMMENTS_SQL);) {
			System.out.println("Your comment has been updated:"+statement);
			statement.setString(1, comment.getname_film());
			statement.setString(2, comment.getdistributor_film());
			statement.setString(3, comment.getcomment_film());
			statement.setString(4, comment.getdate_comment());
			statement.setInt(5, comment.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
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
