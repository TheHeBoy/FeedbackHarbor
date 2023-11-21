package cn.hh.harbor.module.infra.dal.mysql.db;

import cn.hh.harbor.framework.mybatis.core.mapper.BaseMapperX;
import cn.hh.harbor.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * 
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
