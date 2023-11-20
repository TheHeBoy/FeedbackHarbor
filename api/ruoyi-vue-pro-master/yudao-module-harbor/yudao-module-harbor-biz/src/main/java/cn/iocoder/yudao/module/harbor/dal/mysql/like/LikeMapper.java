package cn.iocoder.yudao.module.harbor.dal.mysql.like;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.harbor.dal.dataobject.like.LikeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 反馈点赞 Mapper
 */
@Mapper
public interface LikeMapper extends BaseMapperX<LikeDO> {

    default LikeDO getByUidAndRid(Long uid, Long rid) {
        return selectOne(LikeDO::getRid, rid,
                LikeDO::getUid, uid);
    }
}
