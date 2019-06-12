package ext.modular.procedure;

import ext.modular.characteristic.CharacteristicEntity;
import ext.modular.common.ConnectionUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * des:
 *
 * @author fxiao
 * @date 2019/6/6 10:03
 */
@Service
public class ProcedureSer {
    private String selectField="id,createTime,updateTime,creator,name";
    private String insertField="id,createTime,updateTime,creator,name";
    /**
     * 获取工序列表
     * @Author Fxiao
     * @Description
     * @Date  2019/6/10
     * @param
     * @return java.util.List<ext.modular.characteristic.CharacteristicEntity>
     **/
    public List<ProcedureEntity> getProcedureList(){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        String str="";
        List<ProcedureEntity> procedureList=new LinkedList<ProcedureEntity>();
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement= connection.createStatement();
            String sqlStr=String.format("SELECT %s FROM ppm_working_procedure ORDER BY createTime",selectField);
            resultSet=statement.executeQuery(sqlStr);
            if(resultSet!=null){
                while (resultSet.next()){
                    ProcedureEntity procedureEntity=new ProcedureEntity();
                    procedureEntity.setId(resultSet.getInt("ID"));
                    procedureEntity.setCreator(resultSet.getString("creator"));
                    procedureEntity.setName(resultSet.getString("name"));
                    procedureEntity.setCreateTime(resultSet.getDate("createTime"));
                    procedureEntity.setUpdateTime(resultSet.getTime("updateTime"));
                    procedureList.add(procedureEntity);
                }
            }
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionUtil.close(connection,statement);
        }
        return procedureList;
    }
    /**
     * 增加工序
     * @Author Fxiao
     * @Description
     * @Date  2019/6/10
     * @param procedureEntity
     * @return void
     **/
    public void addProcedure(ProcedureEntity procedureEntity){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("INSERT INTO ppm_working_procedure(%s) VALUES(ppm_seq.nextval,'%s','%s','%s','%s')",
                    insertField,procedureEntity.getCreateTime(),procedureEntity.getUpdateTime(),
                    procedureEntity.getCreator(), procedureEntity.getName());
            statement.executeQuery(sqlStr);

        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 修改工序
     * @Author Fxiao
     * @Description
     * @Date  2019/6/10
     * @param procedureEntity
     * @return void
     **/
    public void updateProcedure(ProcedureEntity procedureEntity){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("UPDATE ppm_working_procedure SET name='%s' AND updateTime='%s' WHERE id ='%s'",
                    procedureEntity.getName(),procedureEntity.getUpdateTime(),procedureEntity.getId());
            statement.executeQuery(sqlStr);

        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除工序
     * @Author Fxiao
     * @Description
     * @Date  2019/6/10
     * @param id
     * @return void
     **/
    public void deleteProcedure(int id){
        Connection connection=null;
        Statement statement=null;
        try{
            connection= ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("DELETE FROM ppm_working_procedure where ID ='%s'",id);
            statement.executeQuery(sqlStr);

        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
