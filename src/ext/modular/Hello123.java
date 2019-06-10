package ext.modular;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 工艺规划相关操作servlet
 * @author clz
 *
 */
//@Controller
public class Hello123 {

	public Hello123() {

	}
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST,RequestMethod.HEAD })
	protected void processRequest(HttpServletRequest request,HttpServletResponse response)  {
		String jsonStr = "http://pdmtest.xsm.com/Windchill/app/#ptc1/library/listFiles?";
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-store,no-cache");
		response.setHeader("Pragma", "no-cache");
		try {
			PrintWriter	pw = response.getWriter();
			if (null != jsonStr){
				pw.print(jsonStr);
			}
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
