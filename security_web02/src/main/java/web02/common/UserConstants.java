package web02.common;

/**
 * 用户常量信息
 */
public class UserConstants {


    /**
     * 岗位名称是否唯一的返回结果码
     */
    public final static String JOB_NAME_UNIQUE = "0";
    public final static String JOB_NAME_NOT_UNIQUE = "1";

    /**
     * 用户名名称是否唯一的返回结果码
     */
    public final static String USER_NAME_UNIQUE = "0";
    public final static String USER_NAME_NOT_UNIQUE = "1";

    /**
     * 部门名称是否唯一的返回结果码
     */
    public final static String DEPT_NAME_UNIQUE = "0";
    public final static String DEPT_NAME_NOT_UNIQUE = "1";

    /**
     * 手机号码是否唯一的返回结果
     */
    public final static String USER_PHONE_UNIQUE = "0";
    public final static String USER_PHONE_NOT_UNIQUE = "1";

    /**
     * 是否唯一的返回结果
     */
    public final static String UNIQUE = "0";
    public final static String NOT_UNIQUE = "1";

    /**
     * 部门停用状态
     */
    public static final String DEPT_DISABLE = "0";

    /**
     * 部门正常状态
     */
    public static final String DEPT_NORMAL = "1";

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";
    public static final String LOGIN_TYPE_JSON = "JSON";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String TOKEN_REDIS_KEY = "login_token_key:";

    public static final String CAPTCHA_CODE_KEY = "captcha_code_key:";

    public static final String TOKEN_KEY = "token_key";

    public static final String UNKNOWN_IP = "XX XX";

    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";
}
