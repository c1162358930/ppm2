package ext.modular.common;

import com.google.gson.Gson;

/**
 * des:
 *  用作返回数据的类
 * @author fxiao
 * @date 2019/6/11 18:11
 */
public class ResultUtils {
    private static Gson gson=new Gson();
    public static<T> String succ(T t){
        DataPack<T> dataPack=new DataPack<>();
        dataPack.setData(t);
        dataPack.setSuccess(true);
        return gson.toJson(dataPack);
    }
    public static<T> String succ(T t,String message){
        DataPack<T> dataPack=new DataPack<>();
        dataPack.setData(t);
        dataPack.setMessage(message);
        dataPack.setSuccess(true);
        return gson.toJson(dataPack);
    }
    public static String error(String message){
        DataPack dataPack=new DataPack<>();
        dataPack.setSuccess(false);
        dataPack.setMessage(message);
        return gson.toJson(dataPack);
    }
}
