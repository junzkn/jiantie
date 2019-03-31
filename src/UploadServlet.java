import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;



import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String result = "{\"status\":\"success\"}" ;
        String name = "";
        String url = "";



        //首先判断一下 上传的数据是表单数据还是一个带文件的数据
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        HttpSession session=request.getSession();
        session.setAttribute("progressBar",0);
        if (isMultipart) {   //如果为true 说明是一个带有文件的数据
            //拿到servlet的真实路径
            String realpath = request.getSession().getServletContext().getRealPath("/music");
            //打印一下路径
            File dir = new File(realpath);
            if (!dir.exists())
                dir.mkdirs(); //如果目录不存在 把这个目录给创建出来
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory); //获取到上传文件的对象upload
            upload.setHeaderEncoding("UTF-8");
            try {
                //判断一下上传的数据类型
                List<FileItem> items = upload.parseRequest(request);
                for (FileItem item : items) {
                    if (item.isFormField()) { //上传的数据类型 是一个表单类型
                        String name1 = item.getFieldName();// 得到请求参数的名称
                        String value = item.getString("UTF-8");// 得到参数值
                    } else  {
                        //说明是一个文件类型   进行上传
//                        item.write(new File(dir, System.currentTimeMillis()
//                                + item.getName().substring(item.getName().lastIndexOf("."))));
                        item.write(new File(dir, item.getName()));

                        name = item.getName();
                        url = "http://jun:8080/music/"+item.getName() ;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
            }
        }


        if(!name.equals("") && !url.equals("")){
            result = Dao.saveMusic(name,url);
        }


        response.setHeader("Content-Type", "text/plain");
        response.setHeader("charset", "utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        OutputStream out = response.getOutputStream();
        out.write(result.getBytes("UTF-8"));
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
