package ext.modular.procedure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * des:
 *
 * @author fxiao
 * @date 2019/6/6 10:02
 */
@Controller
@RequestMapping(value = "procedure")
public class ProcedureController {
    @Autowired
    ProcedureSer procedureSer;
    //private final Logger log=LoggerFactory.getLogger(this.getClass());
    @ResponseBody
    @RequestMapping(value = "procedures" ,method = RequestMethod.POST)
    public void add(ProcedureEntity procedureEntity){
        procedureSer.addProcedure(procedureEntity);
    }
}
