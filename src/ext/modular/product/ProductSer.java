package ext.modular.product;

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
 *  产品的ser层
 * @author renkai
 * @date 2019/6/12
 */

@Service
public class ProductSer {
    private final Logger log= LoggerFactory.getLogger(this.getClass());
    private String selectField="id,createTime,updateTime,creator,name,model_id,product_code,model_type,batch,quantity";
    private String insertField="id,creator,name,model_id,product_code,model_type,batch,quantity";
    /**
     * 获取产品列表
     * @Author renkai
     * @Description
     * @Date  2019/6/12
     * @param
     * @return java.util.List<ext.modular.product.ProductEntity>
     **/
    public List<ProductEntity> getProductList(){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        List<ProductEntity> productList=new LinkedList<ProductEntity>();
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement= connection.createStatement();
            String sqlStr=String.format("SELECT %s FROM ppm_product ORDER BY createTime",selectField);
            resultSet=statement.executeQuery(sqlStr);
            log.info("查询的sql为“{}”,查询到的结果resultSet为：“{}”",sqlStr,resultSet);
            if(resultSet!=null){
                while (resultSet.next()){
                    ProductEntity productEntity=new ProductEntity();
                    productEntity.setId(resultSet.getInt("ID"));
                    productEntity.setCreator(resultSet.getString("creator"));
                    productEntity.setName(resultSet.getString("name"));
                    productEntity.setCreateTime(resultSet.getDate("createTime"));
                    productEntity.setUpdateTime(resultSet.getTime("updateTime"));
                    productEntity.setModel_id(resultSet.getString("model_id"));
                    productEntity.setProduct_code(resultSet.getString("product_code"));
                    productEntity.setModel_type(resultSet.getString("model_type"));
                    productEntity.setBatch(resultSet.getString("batch"));
                    productEntity.setQuantity(resultSet.getInt("quantity"));
                    productList.add(productEntity);
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
        return productList;
    }
    /**
     * 根据型号id获取产品列表
     * @Author renkai
     * @Description
     * @Date  2019/6/15
     * @param id
     * @return java.util.List<ext.modular.product.ProductEntity>
     **/
    public List<ProductEntity> getProductListByModelId(String id){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        List<ProductEntity> productList=new LinkedList<ProductEntity>();
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement= connection.createStatement();
            String sqlStr=String.format("SELECT %s FROM ppm_product WHERE model_id='%s' ORDER BY createTime",selectField,id);
            resultSet=statement.executeQuery(sqlStr);
            log.info("查询的sql为“{}”,查询到的结果resultSet为：“{}”",sqlStr,resultSet);
            if(resultSet!=null){
                while (resultSet.next()){
                    ProductEntity productEntity=new ProductEntity();
                    productEntity.setId(resultSet.getInt("ID"));
                    productEntity.setCreator(resultSet.getString("creator"));
                    productEntity.setName(resultSet.getString("name"));
                    productEntity.setCreateTime(resultSet.getDate("createTime"));
                    productEntity.setUpdateTime(resultSet.getTime("updateTime"));
                    productEntity.setModel_id(resultSet.getString("model_id"));
                    productEntity.setProduct_code(resultSet.getString("product_code"));
                    productEntity.setModel_type(resultSet.getString("model_type"));
                    productEntity.setBatch(resultSet.getString("batch"));
                    productEntity.setQuantity(resultSet.getInt("quantity"));
                    productList.add(productEntity);
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
        return productList;
    }
    /**
     * 获取单个产品
     * @Author renkai
     * @Description
     * @Date  2019/6/15
     * @param id
     * @return ProductEntity
     **/
     public ProductEntity getProductById(int id){
         Connection connection=null;
         Statement statement=null;
         ResultSet resultSet=null;
         ProductEntity productEntity=new ProductEntity();
         try{
             connection=ConnectionUtil.getJdbcConnection();
             statement=connection.createStatement();
             String sqlStr=String.format("SELECT %s FROM ppm_product WHERE id=%s",
                     selectField, id);
             log.info("新增单个产品的sql为“{}”",sqlStr);
             resultSet=statement.executeQuery(sqlStr);
             log.info("resultSet：{}",resultSet);
             if(resultSet!=null) {
                 while (resultSet.next()) {
                     productEntity.setId(resultSet.getInt("ID"));
                     productEntity.setCreator(resultSet.getString("creator"));
                     productEntity.setName(resultSet.getString("name"));
                     productEntity.setCreateTime(resultSet.getDate("createTime"));
                     productEntity.setUpdateTime(resultSet.getDate("updateTime"));
                     productEntity.setModel_id(resultSet.getString("model_id"));
                     productEntity.setProduct_code(resultSet.getString("product_code"));
                     productEntity.setModel_type(resultSet.getString("model_type"));
                     productEntity.setBatch(resultSet.getString("batch"));
                     productEntity.setQuantity(resultSet.getInt("quantity"));
                     log.info("productEntity为{}",productEntity);
                 }
             }

         }
         catch (ClassNotFoundException e){
             e.printStackTrace();
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
         return productEntity;
     }
    /**
     * 增加产品
     * @Author renkai
     * @Description
     * @Date  2019/6/12
     * @param productEntity
     * @return void
     **/
    public void addProduct(ProductEntity productEntity){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("INSERT INTO ppm_product(%s) VALUES(ppm_seq.nextval,'%s','%s',%s,'%s','%s','%s',%s)",
                    insertField, productEntity.getCreator(), productEntity.getName(),productEntity.getModel_id(),
                    productEntity.getProduct_code(),productEntity.getModel_type(),productEntity.getBatch(),productEntity.getQuantity());
            log.info("新增的sql为“{}”",sqlStr);
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
     * 修改产品
     * @Author renkai
     * @Description
     * @Date  2019/6/12
     * @param productEntity
     * @return void
     **/
    public void updateProduct(ProductEntity productEntity){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("UPDATE ppm_product SET name='%s', model_id=%s"+
                            ", product_code='%s' ,model_type='%s', batch='%s',quantity='%s' WHERE id =%s",
                    productEntity.getName(),productEntity.getModel_id(),
                    productEntity.getProduct_code(),productEntity.getModel_type(),productEntity.getBatch(),
                    productEntity.getQuantity(),productEntity.getId());
            log.info("修改的sql为“{}”",sqlStr);
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
     * 删除产品
     * @Author renkai
     * @Description
     * @Date  2019/6/12
     * @param id
     * @return void
     **/
    public void deleteProduct(int id){
        Connection connection=null;
        Statement statement=null;
        try{
            connection= ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("DELETE FROM ppm_product where ID ='%s'",id);
            log.info("删除的sql为“{}”",sqlStr);
            log.info("id={}",id);
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
