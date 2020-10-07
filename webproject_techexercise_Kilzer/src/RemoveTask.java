
/**
 * @file RemoveTask.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RemoveTask")
public class RemoveTask extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public RemoveTask() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String id = request.getParameter("id");

      Connection connection = null;
      String deleteSql = "DELETE FROM ToDoTasks WHERE id=?";

      try {
         DBConnectionKilzer.getDBConnection(getServletContext());
         connection = DBConnectionKilzer.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(deleteSql);
         preparedStmt.setString(1, id);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Removed!";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n"//
            );

      out.println("<a href=\"SimpleFormSearch\" action=\"POST\">See all tasks</a> <br>");
      out.println("<a href=/webproject_techexercise_Kilzer/simpleFormInsert.html>Insert Tasks</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
