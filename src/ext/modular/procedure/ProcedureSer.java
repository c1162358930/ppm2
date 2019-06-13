package ext.modular.procedure;

import ext.modular.common.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * des:
 *  工序service
 * @author fxiao
 * @date 2019/6/6 10:03
 */
@Service
public class ProcedureSer {
    private final Logger log= LoggerFactory.getLogger(this.getClass());
    private String selectField="id,createTime,updateTime,creator,name";
    private String insertField="id,creator,name";
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
            String sqlStr=String.format("INSERT INTO ppm_working_procedure(%s) VALUES(ppm_seq.nextval,'%s','%s')",
                    insertField,
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

    public void addIntoTemplate(int templateId,String[]procedureIds,String currentUserId){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            //如果当前模板有旧的工序，则删除旧工序关系，再插入新工序关系
            String sqlStr="DELETE FROM PPM_TEMPLATE_WORK_LINK WHERE TEMPLATE_ID="+templateId;
            statement.execute(sqlStr);

            log.info("procedureIds={}", Arrays.toString(procedureIds));
            //循环插入关系数据
            for (int i = 0; i <procedureIds.length; i++) {
                sqlStr=String.format(
                        "INSERT INTO PPM_TEMPLATE_WORK_LINK(id,creator,template_id,tw_id,ppm_order) " +
                                "VALUES (ppm_seq.nextval,'%s',%s,%s,ppm_order_num_seq.nextval)",
                        currentUserId,templateId,procedureIds[i]);
                log.info("插入id为\"{}\"的工序时的sql为\"{}\"",procedureIds[i],sqlStr);
                statement.execute(sqlStr);
            }

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
    public List<ProcedureEntity> getByTemplate(int templateId){
        Connection connection=null;
        Statement statement=null;
        List<ProcedureEntity> list=new LinkedList<>();
        try{
            connection= ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            //每个字段前面拼接一个“a.”
            String[] arr=selectField.split(",");
            StringBuilder sb=new StringBuilder();
            for (int i = 0; i < arr.length - 1; i++) {
                sb.append("a.").append(arr[i]).append(",");
            }
            sb.append("a.").append(arr[arr.length - 1]);
            String sqlStr=String.format(
                    "SELECT %s FROM PPM_WORKING_PROCEDURE a,PPM_TEMPLATE_WORK_LINK b WHERE a.ID=b.TW_ID AND b.TEMPLATE_ID=%s"
                    ,sb.toString(),templateId);
            log.info("合成后的sql={}",sqlStr);
            ResultSet resultSet=statement.executeQuery(sqlStr);
            if(resultSet!=null){
                while(resultSet.next()){
                    ProcedureEntity procedureEntity=new ProcedureEntity();
                    procedureEntity.setId(resultSet.getInt("id"));
                    procedureEntity.setCreateTime(resultSet.getDate("createTime"));
                    procedureEntity.setUpdateTime(resultSet.getDate("updateTime"));
                    procedureEntity.setCreator(resultSet.getString("creator"));
                    procedureEntity.setName(resultSet.getString("name"));
                    list.add(procedureEntity);
                }
            }

        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
