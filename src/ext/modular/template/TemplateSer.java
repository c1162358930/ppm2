package ext.modular.template;

import ext.modular.common.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TemplateSer {
    private final Logger log= LoggerFactory.getLogger(this.getClass());
    private String selectField="id,createTime,updateTime,creator,name";
    private String insertField="id,creator,name";
    /**
     * 增加模板
     * @Author Fxiao
     * @Description
     * @Date 12:17 2019/6/5
     * @param templateEntity
     * @return void
     **/
    public TemplateEntity add(TemplateEntity templateEntity)  {
        Connection connection= null;
        Statement statement=null;
        try {
            connection = ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("insert into ppm_template(%s) values(ppm_seq.nextval,'%s','%s')"
                    ,insertField, templateEntity.getCreator(), templateEntity.getName());
            statement.execute(sqlStr);
            sqlStr=String.format("SELECT %s FROM (SELECT %s FROM PPM_TEMPLATE ORDER BY createTime desc)WHERE rownum=1",
                    selectField,selectField);
            ResultSet resultSet=statement.executeQuery(sqlStr);
            log.info("查询的sql为“{}”,查询到的结果resultSet为：“{}”",sqlStr,resultSet);
            if(resultSet!=null){
                while(resultSet.next()){
                    TemplateEntity templateEntity2=new TemplateEntity();
                    templateEntity2.setId(resultSet.getInt("id"));
                    templateEntity2.setCreateTime(resultSet.getDate("createTime"));
                    templateEntity2.setUpdateTime(resultSet.getDate("updateTime"));
                    templateEntity2.setCreator(resultSet.getString("creator"));
                    templateEntity2.setName(resultSet.getString("name"));
                    return templateEntity2;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionUtil.close(connection,statement);
        }
        return null;
    }
    /**
     * 获取模板列表
     * @Author Fxiao
     * @Description
     * @Date 12:16 2019/6/5
     * @param
     * @return java.util.List<ext.modular.template.TemplateEntity>
     **/
    public List<TemplateEntity> getModelList(){
        Connection connection= null;
        Statement statement=null;
        ResultSet resultSet=null;
        List<TemplateEntity> dataList=new LinkedList<>();
        try {
            connection = ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("SELECT %s FROM ppm_template order by createTime",selectField);
            log.info("sqlStr={}",sqlStr);
            resultSet=statement.executeQuery(sqlStr);
            if(resultSet!=null){
                while (resultSet.next()){
                    TemplateEntity templateEntity =new TemplateEntity();
                    templateEntity.setId(resultSet.getInt("ID"));
                    templateEntity.setCreator(resultSet.getString("creator"));
                    templateEntity.setName(resultSet.getString("NAME"));
                    templateEntity.setCreateTime(resultSet.getDate("createTime"));
                    templateEntity.setUpdateTime(resultSet.getDate("updateTime"));
                    dataList.add(templateEntity);
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
    /*
     * 更新模板
     * @Author Fxiao
     * @Description
     * @Date 10:38 2019/6/12
     * @param templateEntity
     * @return void
     **/
    public void update(TemplateEntity templateEntity){
        Connection connection= null;
        Statement statement=null;
        try {
            connection = ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("UPDATE ppm_template SET name='%s' WHERE id=%s", templateEntity.getName(), templateEntity.getId());
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
