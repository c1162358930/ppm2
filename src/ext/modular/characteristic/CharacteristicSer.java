package ext.modular.characteristic;


import com.alibaba.fastjson.JSON;
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
 *  工序检验特性的ser层
 * @author fxiao
 * @date 2019/6/10
 */

@Service
public class CharacteristicSer {
    private String selectField="id,createTime,updateTime,creator,name,tw_id,total,coefficient,ppm_order";
    private String insertField="id,createTime,updateTime,creator,name,tw_id,total,coefficient,ppm_order";
    /**
     * 获取工序检验特性列表
     * @Author Fxiao
     * @Description
     * @Date  2019/6/10
     * @param
     * @return java.util.List<ext.modular.characteristic.CharacteristicEntity>
     **/
    public List<CharacteristicEntity> getCharacList(){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        String str="";
        List<CharacteristicEntity> characList=new LinkedList<CharacteristicEntity>();
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement= connection.createStatement();
            String sqlStr=String.format("SELECT %s FROM ppm_characteristic ORDER BY createTime",selectField);
            resultSet=statement.executeQuery(sqlStr);
            if(resultSet!=null){
                while (resultSet.next()){
                    CharacteristicEntity CharacteristicEntity=new CharacteristicEntity();
                    CharacteristicEntity.setId(resultSet.getInt("ID"));
                    CharacteristicEntity.setCreator(resultSet.getString("creator"));
                    CharacteristicEntity.setName(resultSet.getString("name"));
                    CharacteristicEntity.setCreateTime(resultSet.getDate("createTime"));
                    CharacteristicEntity.setUpdateTime(resultSet.getTime("updateTime"));
                    CharacteristicEntity.getProcedureEntity().setId(resultSet.getInt("tw_id"));
                    CharacteristicEntity.setTotal(resultSet.getInt("total"));
                    CharacteristicEntity.setCoefficient(resultSet.getInt("coefficient"));
                    CharacteristicEntity.setPpm_order(resultSet.getInt("ppm_order"));
                    characList.add(CharacteristicEntity);
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
        return characList;
    }
    /**
     * 增加工序检验特性
     * @Author Fxiao
     * @Description
     * @Date  2019/6/10
     * @param CharacteristicEntity
     * @return void
     **/
    public void addCharac(CharacteristicEntity CharacteristicEntity){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("INSERT INTO ppm_characteristic(%s) VALUES(ppm_seq.nextval,'%s','%s','%s','%s','%s','%s','%s','%s')",
                    insertField,CharacteristicEntity.getCreateTime(),CharacteristicEntity.getUpdateTime(),
                    CharacteristicEntity.getCreator(), CharacteristicEntity.getName(),CharacteristicEntity.getProcedureEntity().getId(),
                    CharacteristicEntity.getTotal(),CharacteristicEntity.getCoefficient(),CharacteristicEntity.getPpm_order());
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
     * 修改工序检验特性
     * @Author Fxiao
     * @Description
     * @Date  2019/6/10
     * @param CharacteristicEntity
     * @return void
     **/
    public void updateCharac(CharacteristicEntity CharacteristicEntity){
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("UPDATE ppm_characteristic SET name='%s' AND total='%s' AND updateTime='%s' And tw_id='%s'"+
                            "And coefficient='%s' And ppm_order='%s' WHERE id ='%s'",
                    CharacteristicEntity.getName(),CharacteristicEntity.getTotal(),CharacteristicEntity.getUpdateTime(),
                    CharacteristicEntity.getProcedureEntity().getId(),CharacteristicEntity.getCoefficient(),
                    CharacteristicEntity.getPpm_order(),CharacteristicEntity.getId());
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
     * 删除工序检验特性
     * @Author Fxiao
     * @Description
     * @Date  2019/6/10
     * @param id
     * @return void
     **/
    public void deleteCharac(int id){
        Connection connection=null;
        Statement statement=null;
        try{
            connection= ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("DELETE FROM ppm_characteristic where ID ='%s'",id);
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
