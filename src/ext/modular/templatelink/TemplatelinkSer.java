package ext.modular.templatelink;

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
 *  工序模板关系的ser层
 * @author fxiao
 * @date 2019/6/10
 */

@Service
public class TemplatelinkSer {
    private String selectField="id,createTime,updateTime,creator,tw_id,template_id,ppm_order";
    private String insertField="id,createTime,updateTime,creator,tw_id,template_id,ppm_order";
    /**
     * 获取工序模板关系列表
     * @Author Fxiao
     * @Description
     * @Date  2019/6/11
     * @param
     * @return java.util.List<ext.modular.templatelink.TemplatelinkEntity>
     **/
    public List<TemplatelinkEntity> getTemplinkList(){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        String str="";
        List<TemplatelinkEntity> templinkList=new LinkedList<TemplatelinkEntity>();
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement= connection.createStatement();
            String sqlStr=String.format("SELECT %s FROM ppm_template_work_link ORDER BY createTime",selectField);
            resultSet=statement.executeQuery(sqlStr);
            if(resultSet!=null){
                while (resultSet.next()){
                    TemplatelinkEntity templatelinkEntity=new TemplatelinkEntity();
                    templatelinkEntity.setId(resultSet.getInt("ID"));
                    templatelinkEntity.setCreator(resultSet.getString("creator"));
                    templatelinkEntity.setCreateTime(resultSet.getDate("createTime"));
                    templatelinkEntity.setUpdateTime(resultSet.getTime("updateTime"));
                    templatelinkEntity.getTemplateEntity().setId(resultSet.getInt("template_id"));
                    templatelinkEntity.getProcedureEntity().setId(resultSet.getInt("tw_id"));
                    templatelinkEntity.setPpm_order(resultSet.getInt("ppm_order"));
                    templinkList.add(templatelinkEntity);
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
        return templinkList;
    }
    /**
     * 增加工序模板关系
     * @Author Fxiao
     * @Description
     * @Date  2019/6/11
     * @param templatelinkEntity
     * @return void
     **/
    public void addTemplink(TemplatelinkEntity templatelinkEntity){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("INSERT INTO ppm_template_work_link(%s) VALUES(ppm_seq.nextval,'%s','%s','%s','%s','%s','%s')",
                    insertField,templatelinkEntity.getCreateTime(),templatelinkEntity.getUpdateTime(),
                    templatelinkEntity.getCreator(),templatelinkEntity.getProcedureEntity().getId(),
                    templatelinkEntity.getTemplateEntity().getId(),templatelinkEntity.getPpm_order());
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
     * 修改工序模板关系
     * @Author Fxiao
     * @Description
     * @Date  2019/6/11
     * @param templatelinkEntity
     * @return void
     **/
    public void updateTemplink(TemplatelinkEntity templatelinkEntity){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("UPDATE ppm_template_work_link SET updateTime='%s' And tw_id='%s'"+
                            "And template_id='%s' And ppm_order='%s' WHERE id ='%s'",
                    templatelinkEntity.getUpdateTime(),templatelinkEntity.getProcedureEntity().getId(),
                    templatelinkEntity.getTemplateEntity().getId(),
                    templatelinkEntity.getPpm_order(),templatelinkEntity.getId());
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
     * 删除工序模板关系
     * @Author Fxiao
     * @Description
     * @Date  2019/6/11
     * @param id
     * @return void
     **/
    public void deleteTemplink(int id){
        Connection connection=null;
        Statement statement=null;
        try{
            connection= ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("DELETE FROM ppm_template_work_link where ID ='%s'",id);
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
