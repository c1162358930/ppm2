package ext.modular.model;

import ext.modular.common.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wt.util.WTException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class ModelController {
    private final Logger log= LoggerFactory.getLogger(this.getClass());
    public ModelController() {
    }

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST,RequestMethod.HEAD })
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws WTException {
        String jsonStr="";
        String actionName=request.getParameter("actionName");
        log.info("actionName={}",actionName);
        ModelSer ser=new ModelSer();
        //获取型号列表
        if("get".equals(actionName)){
            List<ModelEntity> modelList =ser.getModelList();
            if(modelList!=null){
                log.info("获取型号列表成功");
                jsonStr= ResultUtils.succ(modelList);
            }else{
                log.info("获取型号列表失败！");
                jsonStr=ResultUtils.error("获取型号列表失败！");
            }
        }
        //新增
        else if("post".equals(actionName)){
            ModelEntity entity=new ModelEntity();
            String modelId=request.getParameter("id");
            entity.setName(request.getParameter("name"));
            entity.setCreator(request.getParameter("creator"));
            entity.setModel_code(request.getParameter("model_code"));
            log.info("ModelEntity={}",entity.toString());
            if(StringUtils.isEmpty(modelId)){
                entity.setId(0);
                ser.addModel(entity);
                jsonStr=ResultUtils.succ(null,"新增成功");
            }else{
                entity.setId(Integer.parseInt(modelId));
                ser.updateModel(entity);
                jsonStr=ResultUtils.succ(null,"修改成功");
            }
        }
        //删除
        else if("delete".equals(actionName)){
            String id =(request.getParameter("id"));
            log.info("id={}",id);
            if(StringUtils.isEmpty(id)){
                jsonStr= ResultUtils.error("删除失败，缺少id信息");
            }else{
                ser.deleteModel(Integer.parseInt(id));
                jsonStr=ResultUtils.succ("删除型号信息成功");
            }
        }else if("getListByWindch".equals(actionName)){
            log.info("在windchill中获取所有的型号信息");
            List list= ModelSer.getProduct();
            if(list==null){
                jsonStr=ResultUtils.error("查询到的list为null");
            }else{
                jsonStr=ResultUtils.succ(list);
            }
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;Charset=UTF-8");
        response.setHeader("Cache-Control", "no-store,no-cache");
        response.setHeader("Pragma", "no-cache");
        try {
            PrintWriter pw =response.getWriter();
            if(null!=jsonStr){
                pw.print(jsonStr);
            }
            pw.flush();
            pw.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}