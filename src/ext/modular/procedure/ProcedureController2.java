package ext.modular.procedure;

import ext.modular.common.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        ProcedureSer procedureSer=new ProcedureSer();
        //获取全部工序列表
        if("get".equals(actionName)) {
            List templateList = procedureSer.getProcedureList();
            jsonStr=ResultUtils.succ(templateList);
        //根据模板获得工序
        }else if("getByTemplate".equals(actionName)){
            String templateIdStr=request.getParameter("templateId");
            log.info("获取的templateIdStr=",templateIdStr);
            if(StringUtils.isEmpty(templateIdStr)){
                jsonStr=ResultUtils.error("没有找到模板id");
            }else{
                int templateId=Integer.valueOf(templateIdStr);
                List<ProcedureEntity> list=procedureSer.getByTemplate(templateId);
                jsonStr=ResultUtils.succ(list);
            }
        }
        //增加工序
        else if("add".equals(actionName)){
            String name=request.getParameter("procedureName");
            String creatorName="无名";
            ProcedureEntity entity=new ProcedureEntity();
            entity.setName(name);
            entity.setCreator(creatorName);
            procedureSer.addProcedure(entity);
            jsonStr=ResultUtils.succ(null,"工序保存成功");
        }else if("delete".equals(actionName)){
            jsonStr=ResultUtils.error("功能暂未开发");
        }else{
            jsonStr=ResultUtils.error("actionName错误，actionName="+actionName);
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
