package ext.modular.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * des:新的模板方式
 *
 * @author fxiao
 * @date 2019/6/4 12:13
 */
@Controller
public class TemplateController2 {
    private final Logger log= LoggerFactory.getLogger(this.getClass());
    /*@Autowired
    private TemplateSer templateSer;
    @RequestMapping(value = "templatehome",method = RequestMethod.GET)
    public String page(){
        return "template/template";
    }
    @ResponseBody
    @RequestMapping(value = "templates",method = RequestMethod.GET)
    public List<TemplateEntity> List(){
        return templateSer.getModelList();
    }
    @ResponseBody
    @RequestMapping(value = "templates",method = RequestMethod.POST)
    public void addOrUpdate(TemplateEntity templateEntity){
        if (templateEntity.getId()==0) {
            templateSer.add(templateEntity);
        }else{
            templateSer.update(templateEntity);
        }
    }
    @ResponseBody
    @RequestMapping(value = "templates/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id){
        templateSer.delete(id);
    }*/

    public TemplateController2() {
    }
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST,RequestMethod.HEAD })
    public void processRequest(HttpServletRequest request, HttpServletResponse response){
        String jsonStr = "";
        String actionName = request.getParameter("actionName");
        log.info("actionName={}",actionName);
        TemplateSer templateSer=new TemplateSer();
        if("get".equals(actionName)){
            List templateList=templateSer.getModelList();

            if(templateList!=null){
                log.info("获取的模板列表长度为{}",String.valueOf(templateList.size()));
                log.info("list内容：{}", Arrays.toString(templateList.toArray()));
                log.info("尝试转换null:{}",JSON.toJSONString(null));
                log.info("尝试转换空list，转化结果为：{}",JSON.toJSONString(new ArrayList<>()));
                Gson gson=new Gson();
                jsonStr = gson.toJson(templateList);
                log.info("gson转化后的json"+jsonStr);

            }else{
                log.info("获取模板列表失败！templateList==null");
            }
        }else if("post".equals(actionName)){
            TemplateEntity templateEntity=new TemplateEntity();
            String templateId=request.getParameter("id");
            templateEntity.setId(Integer.valueOf(templateId));
            templateEntity.setName(request.getParameter("name"));

            if (templateEntity.getId()==0) {
                templateSer.add(templateEntity);
            }else{
                templateSer.update(templateEntity);
            }
        }else if("delete".equals(actionName)){
            String templateId=request.getParameter("id");
            templateSer.delete(Integer.valueOf(templateId));
        }

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store,no-cache");
        response.setHeader("Pragma", "no-cache");
        try {
            PrintWriter pw = response.getWriter();
            if (null != jsonStr){
                pw.print("结果是："+jsonStr);
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
