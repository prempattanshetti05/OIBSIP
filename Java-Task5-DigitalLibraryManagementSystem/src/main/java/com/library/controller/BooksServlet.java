package com.library.controller;

import com.library.dao.BookDAO;
import com.library.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/books")
public class BooksServlet extends HttpServlet {

    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        List<Book> books = bookDAO.getAllBooks();

        request.setAttribute("books", books);

        request.getRequestDispatcher("/admin/books.jsp")
                .forward(request, response);
    }
}