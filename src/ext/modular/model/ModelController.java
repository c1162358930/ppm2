package ext.modular.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * des:
 *
 * @author fxiao
 * @date 2019/6/4 12:13
 */
@Controller
@RequestMapping("model")
public class ModelController {

    @Autowired
    private ModelSer modelSer;
    @RequestMapping(value = "modelhome",method = RequestMethod.GET)
    public String page(){
        return "model/model";
    }
    @ResponseBody
    @RequestMapping(value = "models",method = RequestMethod.GET)
    public List<ModelEntity> List(){
        return modelSer.getModelList();
    }
    @ResponseBody
    @RequestMapping(value = "models",method = RequestMethod.POST)
    public void addOrUpdate(ModelEntity modelEntity){
        if (modelEntity.getId()==0) {
            modelSer.add(modelEntity);
        }else{
            modelSer.update(modelEntity);
        }
    }
    @ResponseBody
    @RequestMapping(value = "models/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id){
        modelSer.delete(id);
    }
}
