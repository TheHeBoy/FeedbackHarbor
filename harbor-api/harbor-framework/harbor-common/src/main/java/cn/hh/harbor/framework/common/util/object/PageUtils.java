package cn.hh.harbor.framework.common.util.object;

import cn.hh.harbor.framework.common.pojo.PageParam;

/**
 * {@link cn.hh.harbor.framework.common.pojo.PageParam} 工具类
 *
 *
 */
public class PageUtils {

    public static int getStart(PageParam pageParam) {
        return (pageParam.getPageNo() - 1) * pageParam.getPageSize();
    }

}
