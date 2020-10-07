import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormSearch")
public class SimpleFormSearchKilzer extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormSearchKilzer() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "To-Do List";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionKilzer.getDBConnection(getServletContext());
         connection = DBConnectionKilzer.connection;

        String selectSQL = "SELECT * FROM ToDoTasks";
        preparedStatement = connection.prepareStatement(selectSQL);
         ResultSet rs = preparedStatement.executeQuery();
         
         out.println("<ul>");
         while (rs.next()) {
            String task = rs.getString("task").trim();
            String id = rs.getString("id").trim();

           out.println("<li>Task: " + task + " <form action=\"RemoveTask\" method=\"POST\">\r\n" +
        		"<input type = \"hidden\" name=\"id\" value=" + id + " />" +
           		"<input type=\"submit\" value=\"Remove\" />\r\n" + 
           		"	</form></li>");
         }
         out.println("</ul>");
         out.println("<a href=/webproject_techexercise_Kilzer/simpleFormInsert.html>Insert Tasks</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
