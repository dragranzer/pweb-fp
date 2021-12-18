//comment servlet

package com.finalwebpro.ourfilm.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.finalwebpro.ourfilm.bean.Comment;
import com.finalwebpro.ourfilm.dao.CommentDao;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommentDao CommentDao;
	
	public void init() {
		CommentDao = new CommentDao();
	}
       
    /**
     * @return 
     * @see HttpServlet#HttpServlet()
     */
    //public void commentServlet() {
      //  super();
        // TODO Auto-generated constructor stub
    //}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertComment(request, response);
				break;
			case "/delete":
				deleteComment(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateComment(request, response);
				break;
			default:
				listComment(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void listComment(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		
		try {
			List<Comment> listComment = CommentDao.selectAllComments();
			request.setAttribute("listComment", listComment);
			RequestDispatcher dispatcher = request.getRequestDispatcher("comment-list.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.PrintStackTrace();
		}
		
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("comment-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		//Comment existingComment;
		
		try {
			Comment existingComment = CommentDao.selectComment(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("comment-form.jsp");
			request.setAttribute("comment", existingComment);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace;
		}
		

	}

	private void insertComment(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String name_film = request.getParameter("name_film");
		String distributor_film = request.getParameter("distributor_film");
		String comment_film = request.getParameter("comment_film");
		String date_comment = request.getParameter("date_comment");
		//Comment newComment = new Comment();
		Comment newComment = new Comment(name_film, distributor_film, comment_film, date_comment);
		CommentDao.insertComment(newComment);
		response.sendRedirect("list");
	}

	private void updateComment(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name_film = request.getParameter("name_film");
		String distributor_film = request.getParameter("distributor_film");
		String comment_film = request.getParameter("comment_film");
		String date_comment = request.getParameter("date_comment");
		//Comment book = new Comment();
		Comment book = new Comment(id, name_film, distributor_film, comment_film, date_comment);
		CommentDao.updateComment(book);
		response.sendRedirect("list");
	}

	private void deleteComment(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			CommentDao.deleteComment(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("list");

	}

}
