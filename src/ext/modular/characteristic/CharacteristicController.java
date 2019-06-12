package ext.modular.characteristic;

import ext.modular.common.ResultUtils;
import ext.modular.procedure.ProcedureEntity;
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
import java.util.Date;
import java.util.List;

@Controller
public class CharacteristicController {
    private final Logger log= LoggerFactory.getLogger(this.getClass());

    public CharacteristicController() {
    }
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST,RequestMethod.HEAD })
    public void characteristicRequest(HttpServletRequest request, HttpServletResponse response){
        String jsonStr="";
        String actionName=request.getParameter("actionName");
        log.info("actionName={}",actionName);
        CharacteristicSer ser=new CharacteristicSer();
        //获取工序检验特性列表
        if("getCharacList".equals(actionName)){
            List<CharacteristicEntity> characList =ser.getCharacList();
            if(characList!=null){
                log.info("获取工序检验特性列表成功");
                jsonStr=ResultUtils.succ(characList);
                log.info("工序检验特性列表characList为",jsonStr);
            }else{
                log.info("获取工序检验特性列表失败！");
                jsonStr=ResultUtils.error("获取工序检验特性列表失败！");
            }
        }
        //新增
        else if("postCharac".equals(actionName)){
            CharacteristicEntity entity=new CharacteristicEntity();
            String characteristicId=request.getParameter("id");
            entity.setName(request.getParameter("name"));
            entity.setCreator(request.getParameter("creator"));
            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());
            ProcedureEntity procedureEntity=new ProcedureEntity();
            procedureEntity.setId(Integer.parseInt(request.getParameter("tw_id")));
            entity.setProcedureEntity(procedureEntity);
            entity.setTotal(Integer.parseInt(request.getParameter("total")));
            entity.setCoefficient(Integer.parseInt(request.getParameter("coefficient")));
            entity.setPpm_order(Integer.parseInt(request.getParameter("ppm_order")));
            log.info("CharacteristicEntity:",entity.toString());
            if(StringUtils.isEmpty(characteristicId)){
                entity.setId(0);
                ser.addCharac(entity);
                jsonStr=ResultUtils.succ(null,"新增成功");
            }else{
                entity.setId(Integer.parseInt(characteristicId));
                ser.updateCharac(entity);
                jsonStr=ResultUtils.succ(null,"修改成功");
            }

        }
        //删除
        else if("deleteCharac".equals(actionName)){
            int id =Integer.parseInt(request.getParameter("id"));
            if(StringUtils.isEmpty(id)){
                jsonStr= ResultUtils.error("删除失败，缺少id信息");
            }else{
                ser.deleteCharac(id);
                jsonStr=ResultUtils.succ(null);
            }
        }

        response.setContentType("text/html;Charset='UTF-8'");
        response.setHeader("Cache-Control", "no-store,no-cache");
        response.setHeader("Pragma", "no-cache");
        try {
            PrintWriter pw =response.getWriter();
            if(jsonStr!=null){
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

