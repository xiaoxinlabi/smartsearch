package info.puton.product.smartsearch.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by taoyang on 11/5/15.
 */
public class QueryResult {

    /** 成功标志 */
    private boolean success;
    /** 数据数量 */
    private int total;
    /** 返回信息 */
    private Map message;
    /** 数据 */
    private Object data;

    private final static String KEY_SUCCESS="success" ;
    private final static String KEY_ERROR="error" ;

    public QueryResult(boolean success, int total, Map message, Object data) {
        this.success = success;
        this.total = total;
        this.message = message;
        this.data = data;
    }

    public QueryResult() {
        this(true, 0, null, null);
    }

    public QueryResult(boolean success, String message) {
        if (success)
            this.putMessage(KEY_SUCCESS, message);
        else
            this.putMessage(KEY_ERROR, message);
    }

    public QueryResult(Object data) {
        this(true, 0, null, data);
        if (data instanceof Map) {
            this.total = 1;
        } else if (data instanceof List) {
            this.total = ((List) data).size();
        } else {
            this.total = 1;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Map getMessage() {
        return message;
    }

    public void setMessage(Map message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void putMessage(String key,Object message){
        if (this.message==null){
            this.message=new HashMap();
        }
        this.message.put(key, message);
    }

}
