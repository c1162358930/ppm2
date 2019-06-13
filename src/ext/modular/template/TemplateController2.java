package ext.modular.template;

import ext.modular.common.ResultUtils;
import ext.modular.procedure.ProcedureSer;
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
 *  des:新的模板方式
 *
 * @author fxiao
 * @date 2019/6/4 12:13
 */
@Controller
public class TemplateController2 {
    private final Logger log= LoggerFactory.getLogger(this.getClass());

    public TemplateController2() {
    }
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST,RequestMethod.HEAD })
    public void processRequest(HttpServletRequest request, HttpServletResponse response){
        String jsonStr = "";
        String actionName = request.getParameter("actionName");
        log.info("actionName={}",actionName);
        TemplateSer templateSer=new TemplateSer();
        //获取模板列表
        if("get".equals(actionName)){
            List templateList=templateSer.getModelList();
            if(templateList!=null){
                log.info("获取的模板列表长度为{}",String.valueOf(templateList.size()));
                jsonStr = ResultUtils.succ(templateList);
                log.info(jsonStr);
            }else{
                log.info("获取模板列表失败！templateList==null");
                jsonStr=ResultUtils.error("获取模板列表失败！");
            }
        }
        //存储或修改模板的方法
        else if("post".equals(actionName)){
            TemplateEntity templateEntity=new TemplateEntity();
            String templateId=request.getParameter("id");
            log.info("templateId={}",templateId);
            templateEntity.setName(request.getParameter("name"));
            if(StringUtils.isEmpty(templateId)){
                templateEntity.setId(0);
                TemplateEntity newTemplate=templateSer.add(templateEntity);
                if(newTemplate==null){
                    jsonStr=ResultUtils.error("插入模板数据成功，但是未插入工序关系失败");
                }else{
                    log.info("新增加的模板的id为：{}",newTemplate.getId());
                    //添加工序到模板里去
                    ProcedureSer procedureSer=new ProcedureSer();
                    String []procedureList=request.getParameterValues("procedure.id");

                    procedureSer.addIntoTemplate(newTemplate.getId(),procedureList,"无名");
                    jsonStr=ResultUtils.succ(null,"新增成功");
                }
            }else{
                templateEntity.setId(Integer.valueOf(templateId));
                templateSer.update(templateEntity);
                jsonStr=ResultUtils.succ(null,"修改成功");
            }
        }else if("delete".equals(actionName)){
            String templateId=request.getParameter("id");
            log.info("templateId={}",templateId);
            if(StringUtils.isEmpty(templateId)){
                jsonStr= ResultUtils.error("删除失败，缺少id信息");
            }else{
                templateSer.delete(Integer.valueOf(templateId));
                jsonStr=ResultUtils.succ(null);
            }
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
