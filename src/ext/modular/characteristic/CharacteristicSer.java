package ext.modular.characteristic;


import ext.modular.common.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
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
    private final Logger log= LoggerFactory.getLogger(this.getClass());
    private String selectField="id,createTime,updateTime,creator,name,tw_id,total,coefficient,ppm_order";
    private String insertField="id,creator,name,tw_id,total,coefficient,ppm_order";
    /**
     * 获取工序检验特性列表
     * @Author Fxiao
     * @Description
     * @Date  2019/6/10
     * @param
     * @return java.util.List<ext.modular.characteristic.CharacteristicEntity>
     **/
    public List<CharacteristicEntity> getCharacList(int procedureId){
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        String str="";
        List<CharacteristicEntity> characList=new LinkedList<CharacteristicEntity>();
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format("SELECT %s FROM ppm_characteristic WHERE tw_id=%s ORDER BY PPM_ORDER ",selectField,procedureId);
            resultSet=statement.executeQuery(sqlStr);
            if(resultSet!=null){
                while (resultSet.next()){
                    CharacteristicEntity CharacteristicEntity=new CharacteristicEntity();
                    CharacteristicEntity.setId(resultSet.getInt("ID"));
                    CharacteristicEntity.setCreator(resultSet.getString("creator"));
                    CharacteristicEntity.setName(resultSet.getString("name"));
                    CharacteristicEntity.setCreateTime(resultSet.getDate("createTime"));
                    CharacteristicEntity.setUpdateTime(resultSet.getTime("updateTime"));
                    CharacteristicEntity.setTotal(resultSet.getInt("total"));
                    CharacteristicEntity.setCoefficient(resultSet.getInt("coefficient"));
                    CharacteristicEntity.setPpmOrder(resultSet.getInt("ppm_order"));
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
     * @Date 22:19 2019/6/13
     * @param characte 特性
     * @param procedureId 工序id
     * @return void
     **/
    public boolean addCharac(CharacteristicEntity characte,int procedureId){
        log.info("增加工序检验特性,工序id={}，特性id={},特性id必须为空",procedureId,characte.getId());
        Connection connection=null;
        PreparedStatement ps=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            String sqlStr=String.format("INSERT INTO ppm_characteristic(%s)" +
                            " VALUES(ppm_seq.nextval,?,?,?,?,?,ppm_order_num_seq.nextval)",
                    insertField);
            log.info("合成sql为：“{}”",sqlStr);
            ps=connection.prepareStatement(sqlStr) ;
            ps.setString(1,characte.getCreator());
            ps.setString(2,characte.getName());
            ps.setInt(3,procedureId);
            ps.setInt(4,characte.getTotal());
            ps.setInt(5,characte.getCoefficient());
            return ps.execute();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionUtil.close(connection,ps);
        }
        return false;
    }
    /**
     * 修改工序检验特性
     * @Author Fxiao
     * @Description
     * @Date 22:14 2019/6/13
     * @param charac 检验特性
     * @param procedureId 所属工序id
     * @return void
     **/
    public void updateCharac(CharacteristicEntity charac,int procedureId){
        log.info("修改工序检验特性,工序id={}，特性id={}",procedureId,charac.getId());
        Connection connection=null;
        Statement statement=null;
        try{
            connection=ConnectionUtil.getJdbcConnection();
            statement=connection.createStatement();
            String sqlStr=String.format(
                    "UPDATE ppm_characteristic SET name='%s' , total='%s' , updateTime=current_timestamp , tw_id='%s'"+
                            ", coefficient='%s' , ppm_order=ppm_order_num_seq.nextval WHERE id ='%s'",
                    charac.getName(),charac.getTotal(),procedureId,charac.getCoefficient()
                    ,charac.getId());
            log.info("最终sql为：“{}”",sqlStr);
            statement.executeQuery(sqlStr);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionUtil.close(connection,statement);
        }
    }
    /**
     * 获取单个特性
     * @Author Fxiao
     * @Description
     * @Date 13:51 2019/6/14
     * @param id
     * @return ext.modular.characteristic.CharacteristicEntity
     **/
    public CharacteristicEntity get(int id){
        CharacteristicEntity characteristicEntity=new CharacteristicEntity();
        Connection connection=null;
        PreparedStatement ps=null;
        try{
            connection= ConnectionUtil.getJdbcConnection();
            String sqlStr=String.format("SELECT * FROM ppm_characteristic WHERE id=? ");
            ps=connection.prepareStatement(sqlStr);
            ps.setInt(1,id);
            ResultSet resultSet=ps.executeQuery();
            if(resultSet!=null){
                while (resultSet.next()){
                    characteristicEntity.setId(resultSet.getInt("id"));
                    characteristicEntity.setCreateTime(resultSet.getDate("createTime"));
                    characteristicEntity.setUpdateTime(resultSet.getDate("updateTime"));
                    characteristicEntity.setCreator(resultSet.getString("creator"));
                    characteristicEntity.setName(resultSet.getString("name"));
                    characteristicEntity.setTotal(resultSet.getInt("total"));
                    characteristicEntity.setCoefficient(resultSet.getInt("coefficient"));
                    characteristicEntity.setPpmOrder(resultSet.getInt("ppm_order"));
                }
            }
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionUtil.close(connection,ps);
        }
        return characteristicEntity;
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
            String sqlStr=String.format("UPDATE ppm_characteristic SET DEL_FLAG=0 where ID ='%s'",id);
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
