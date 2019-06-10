package ext.modular;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * des:
 *
 * @author fxiao
 * @date 2019/6/6 16:26
 */
//@Controller
public class HelloAbc {
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST,RequestMethod.HEAD})
    public void String(HttpServletRequest request, HttpServletResponse response){
        System.out.println("来了");
        String param1=request.getParameter("param1");
        try {
            PrintWriter pw=response.getWriter();
            pw.print(param1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
