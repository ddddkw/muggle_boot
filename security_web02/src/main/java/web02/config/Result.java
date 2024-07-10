package web02.config;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 统一返回结果的类
 * @author dkw
 */
@Data
public class Result<T> implements Serializable {



    private Boolean success;

    private Integer code;

    private String msg;

    private Long count;

    private List<T> data = new ArrayList<T>();

    private String jwt;
    /**
     * 把构造方法私有
     */
    private Result() {

    }


    /**
     * 成功静态方法
     * @return
     */
    public static Result ok() {


        Result r = new Result();
        r.setSuccess(true);
        r.setCode(200);
        r.setMsg("成功");
        return r;
    }
    /**
     * 失败静态方法
     * @return
     */
    public static Result error() {


        Result r = new Result();
        r.setSuccess(false);
        r.setCode(500);
        r.setMsg("失败");
        return r;
    }

    public static Result judge(int n,String msg){


        return n > 0 ? ok().message(msg + "成功") : error().message(msg +"失败");
    }

    public Result success(Boolean success){


        this.setSuccess(success);
        return this;
    }

    public Result message(String message){


        this.setMsg(message);
        return this;
    }

    public Result code(Integer code){


        this.setCode(code);
        return this;
    }
    public Result data(List<T> list){


        this.data.addAll(list);
        return this;
    }
    public Result count(Long count){


        this.count = count;
        return this;
    }
    public Result jwt(String jwt){


        this.jwt = jwt;
        return this;
    }
}
