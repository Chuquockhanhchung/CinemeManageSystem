/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;

import dal.DBContext;
import dal.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Event;

/**
 *
 * @author datla
 */
public class EventServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EventServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EventServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {

            String EventName = request.getParameter("EventName");
            String EventCode = request.getParameter("EventCode");
            String EventDetail = request.getParameter("EventDetail");
            String StartDate = request.getParameter("start-datetimes");
            String EndDate = request.getParameter("end-datetimes");
            String Status = request.getParameter("Status");

            HttpSession session = request.getSession();

            Event event = new Event();
            event.setEventName(EventName);
            event.setEventCode(EventCode);
            event.setEventDetail(EventDetail);
            event.setStartDate(StartDate);
            event.setEndDate(EndDate);
            event.setStatus(Status);

            EventDAO dao = new EventDAO(DBContext.getConn());
            boolean f = dao.addEvent(event);

            if (f) {
                session.setAttribute("succMess", "Add event successful!");
                response.sendRedirect("manager/CMS/event.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}