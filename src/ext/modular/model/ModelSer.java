package ext.modular.model;

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
 *  模型的ser层
 * @author fxiao
 * @date 2019/6/4 14:30
 */
@Service
public class ModelSer {
    private String selectField="id,createTime,updateTime,creator,name";
    private String insertField="id,creator,name";
    /**
     * 增加模板
     * @Author Fxiao
     * @Description
     * @Date 12:17 2019/6/5
     * @param modelEntity
     * @return void
     **/
    public void add(ModelEntity modelEntity)  {
        Connection connection= null;
        Statement statement=null;
        try {
            connection = ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("insert into ppm_template(%s) values(ppm_seq.nextval,'%s','%s')"
                    ,insertField,modelEntity.getCreator(),modelEntity.getName());
            statement.execute(sqlStr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionUtil.close(connection,statement);
        }
    }
    /**
     * 获取模板列表
     * @Author Fxiao
     * @Description
     * @Date 12:16 2019/6/5
     * @param
     * @return java.util.List<ext.modular.model.ModelEntity>
     **/
    public List<ModelEntity> getModelList(){
        Connection connection= null;
        Statement statement=null;
        ResultSet resultSet=null;
        List<ModelEntity> dataList=new LinkedList<>();
        try {
            connection = ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("SELECT %s FROM ppm_template order by createTime",selectField);
            resultSet=statement.executeQuery(sqlStr);
            if(resultSet!=null){
                while (resultSet.next()){
                    ModelEntity modelEntity=new ModelEntity();
                    modelEntity.setId(resultSet.getInt("ID"));
                    modelEntity.setCreator(resultSet.getString("creator"));
                    modelEntity.setName(resultSet.getString("NAME"));
                    modelEntity.setCreateTime(resultSet.getDate("createTime"));
                    modelEntity.setUpdateTime(resultSet.getDate("updateTime"));
                    dataList.add(modelEntity);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionUtil.close(connection,statement);
        }
        return dataList;
    }
    /**
     * 删除
     * @Author Fxiao
     * @Description
     * @Date 15:16 2019/6/5
     * @param id
     * @return void
     **/
    public void delete(int id){
        Connection connection= null;
        Statement statement=null;
        try {
            connection = ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("DELETE FROM ppm_template WHERE id=%s",id);
            statement.execute(sqlStr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionUtil.close(connection,statement);
        }
    }
    public void update(ModelEntity modelEntity){
        Connection connection= null;
        Statement statement=null;
        try {
            connection = ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("UPDATE ppm_template SET name='%s' WHERE id=%s",modelEntity.getName(),modelEntity.getId());
            statement.execute(sqlStr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionUtil.close(connection,statement);
        }
    }
}
