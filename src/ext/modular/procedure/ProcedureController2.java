package ext.modular.procedure;

import ext.modular.common.ResultUtils;
import ext.modular.template.TemplateEntity;
import ext.modular.template.TemplateSer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * des:
 *
 * @author fxiao
 * @date 2019/6/6 10:02
 */
@Controller
public class ProcedureController2 {
    private final Logger log= LoggerFactory.getLogger(this.getClass());

    public ProcedureController2() {
    }
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST,RequestMethod.HEAD })
    public void processRequest(HttpServletRequest request, HttpServletResponse response){
        String jsonStr = "";
        String actionName = request.getParameter("actionName");
        log.info("actionName={}",actionName);
        TemplateSer templateSer=new TemplateSer();
        //获取模板列表
        if("get".equals(actionName)) {
            List templateList = templateSer.getModelList();

            //存储或修改模板的方法
        }else if("post".equals(actionName)){

        }else if("delete".equals(actionName)){

        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store,no-cache");
        response.setHeader("Pragma", "no-cache");
        try {
            PrintWriter pw = response.getWriter();
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
