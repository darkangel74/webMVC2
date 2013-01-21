/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import action.ActionInterface;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import javax.print.DocFlavor;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author DARKANGEL
 */
@WebServlet(name = "ControllerServlet", urlPatterns = {"/controller"})
public class ControllerServlet extends HttpServlet {

     Properties props = null;
    RequestDispatcher rds = null;
    
    @Override
    public void init(ServletConfig config) throws ServletException{
        try {
            props = new Properties();
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("conf.properties");
            props.load(in);
        } catch (Exception x) {
            System.err.println("tidak dapat mengakses halaman ini");
        }
    }
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     String urlPath = request.getParameter("action");
     String reqHelperClassName = (String) props.get(urlPath);
        if (reqHelperClassName != null) {
            try {
                action.ActionInterface helper = (ActionInterface)Class.forName(reqHelperClassName).newInstance();
                String nextView = helper.execute(request);
                if(!nextView.contains("cotroller")){
                    nextView = "/WEB-INF/page" + nextView;
                }
                rds = request.getRequestDispatcher(nextView);
                rds.forward(request, response);
            } catch (Exception e) {
            }
            
        }
        }
      /*  response.setContentType("text/h
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
