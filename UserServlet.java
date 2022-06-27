import java.io.File;
import java.io.IOException;
import java.io.Printwriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.HttpServletRequest;
import javax.servlet.HttpServletResponse;
import javax.servlet.http.part;


@WebServlet("/UserServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2,
                maxFileSize=1024*1024*10,
                maxRequestSize=1024*1024*50)


public class UserServlet extends HttpServlet{
    private static final String save_dir="images";

    @Override

    protected void doPost(HttpServletRequest request,HttpServletResponse response)
        throws ServletException, IOException{
            response.setContentType("text/html;charset=UTF-8");
            Printwriter out=response.getWritter();
                String svaepath = "C:/Program Files/Apache Software Foundation/Tomcat 9.0/webapps/memory/"+File.separator+save_dir;
                    File fileSaveDir=new File(svaepath);

                Part part=request.getPart("file");
                String fileName=extractFileName(part);
                part.write(svaepath + File.separator + fileName);

                try{
                    Connection con= null;
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","2002");
                    PrepareStatement pst = con.prepareStatement("insert into image values(?)");
                    String filePath= savePath + File.separartor + fileName;
                    pst.setString(1,filePath);
                    pst.executeUpdate();
                    out.println("image inserted");
                }
                catch(Exception e){
                    out.println(e);
                }
        }
} 