package com.finalwebpro.ourfilm.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.finalwebpro.ourfilm.bean.LoginBean;
import com.finalwebpro.ourfilm.bean.RegisterBean;

public class UserDao {
	private String dbUrl = "jdbc:mysql://localhost:3306/userdb?useSSL=false";
	private String dbUname = "root";
	private String dbPassword = "";
	private String dbDriver = "com.mysql.cj.jdbc.Driver";
	
	public void loadDriver(String dbDriver) {
		try {
			Class.forName(dbDriver);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(dbUrl, dbUname, dbPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	public boolean validate(LoginBean loginBean) {
		loadDriver(dbDriver);
		Connection connection = getConnection();
		boolean status = false;
		
		String sql = "select * from users where email_user = ? and password_user = ?";
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, loginBean.getEmail_user());
			preparedStatement.setString(2, loginBean.getPassword_user());
			
			ResultSet resultSet = preparedStatement.executeQuery();
			status = resultSet.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	public int insert(RegisterBean registerBean) {
		loadDriver(dbDriver);
		Connection connection = getConnection();
		int status = 0;
		String sql = "insert into users (name_user,email_user,password_user) values (?,?,?)";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, registerBean.getName_user());
			preparedStatement.setString(2, registerBean.getEmail_user());
			preparedStatement.setString(3, registerBean.getPassword_user());
			preparedStatement.executeUpdate();
			status = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	
}
