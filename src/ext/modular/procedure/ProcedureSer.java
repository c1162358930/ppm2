package ext.modular.procedure;

import ext.modular.common.ConnectionUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * des:
 *
 * @author fxiao
 * @date 2019/6/6 10:03
 */
@Service
public class ProcedureSer {
    private String selectField="id,createTime,updateTime,creator,name";
    private String insertField="id,creator,name";
    public void addProcedure(ProcedureEntity procedureEntity){
        Connection connection=null;
        Statement statement=null;
        try {
            connection= ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("INSERT INTO PPM_WORKING_PROCEDURE (%s) VALUES (ppm_seq.nextval,'%s','%s')"
                    ,insertField,procedureEntity.getCreator(),procedureEntity.getName());
            statement.execute(sqlStr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionUtil.close(connection,statement);
        }
    }
}
