package ext.modular.model;

import ext.modular.common.ConnectionUtil;
import ext.modular.product.ProductEntity;
import ext.modular.product.ProductSer;
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
 *  型号的ser层
 * @author renkai
 * @date 2019/6/10
 */
@Service
public class ModelSer {
    private final Logger log= LoggerFactory.getLogger(this.getClass());
    private String selectField="id,createTime,updateTime,creator,name,model_code";
    private String insertField="id,creator,name,model_code";
    /**
     * 获取型号列表
     * @Author renkai
     * @Description
     * @Date 2019/6/10
     * @param
     * @return java.util.List<ext.modular.model.ModelEntity>
     **/
    public List<ModelEntity> getModelList(){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        List<ModelEntity> modelList=new LinkedList<ModelEntity>();
        ProductSer productSer=new ProductSer();
        List<ProductEntity> productList=new LinkedList<ProductEntity>();
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("SELECT %s FROM ppm_model ORDER BY createTime",selectField);
            resultSet=statement.executeQuery(sqlStr);
            log.info("查询的sql为:“{}”,查询到的结果resultSet为：“{}”",sqlStr,resultSet);
            if(resultSet!=null){
                while (resultSet.next()){
                    ModelEntity modelEntity=new ModelEntity();
                    modelEntity.setId(resultSet.getInt("ID"));
                    modelEntity.setCreator(resultSet.getString("creator"));
                    modelEntity.setName(resultSet.getString("name"));
                    modelEntity.setCreateTime(resultSet.getDate("createTime"));
                    modelEntity.setUpdateTime(resultSet.getDate("updateTime"));
                    modelEntity.setModel_code(resultSet.getString("model_code"));
                    productList=productSer.getProductListByModelId(modelEntity.getId());
                    modelEntity.setProductEntityList(productList);
                    log.info("型号对象为{}",modelEntity.toString());
                    log.info("型号id为{}",modelEntity.getId());
                    log.info("产品列表为{}",productList);
                    modelList.add(modelEntity);
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
        return modelList;
    }
    /**
     * 增加型号
     * @Author renkai
     * @Description
     * @Date 2019/6/10
     * @param modelEntity
     * @return void
     **/
    public void addModel(ModelEntity modelEntity){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("INSERT INTO ppm_model(%s) VALUES(ppm_seq.nextval,'%s','%s','%s')",
                    insertField,modelEntity.getCreator(), modelEntity.getName(),modelEntity.getModel_code());
            log.info("新增的sql为:“{}”",sqlStr);
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
     * 修改型号
     * @Author renkai
     * @Description
     * @Date  2019/6/10
     * @param modelEntity
     * @return void
     **/
    public void updateModel(ModelEntity modelEntity){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("UPDATE ppm_model SET name='%s'， model_code ='%s' WHERE id =%s",
                    modelEntity.getName(),modelEntity.getModel_code(),modelEntity.getId());
            log.info("修改的sql为:“{}”",sqlStr);
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
     * 删除型号
     * @Author renkai
     * @Description
     * @Date 2019/6/10
     * @param id
     * @return void
     **/
    public void deleteModel(int id){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("DELETE FROM ppm_model where ID =%s",id);
            log.info("删除的sql为:“{}”",sqlStr);
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
