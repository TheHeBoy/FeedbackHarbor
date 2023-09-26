package cn.iocoder.yudao.module.harbor.service.like;

import cn.iocoder.yudao.module.harbor.controller.app.like.vo.AppLikeReqVO;
import cn.iocoder.yudao.module.harbor.enums.like.LikeBusTypeEnum;

import java.util.List;
import java.util.Set;

/**
 * 用户点赞 Service 接口
 *
 *
 */
public interface LikeService {


    /**
     * 点赞或取消点赞
     * @param appLikeReqVO 点赞参数
     * @return boolean 点赞成功后的状态，true 已点赞 ，false 未点赞
     */
    boolean like(Long uid, AppLikeReqVO appLikeReqVO);

    /**
     * 通过用户id得到所有点赞的反馈
     * @param uid 用户id
     * @return {@link List}<{@link Long}>
     */
    Set<Long> listByUid(Long uid, LikeBusTypeEnum busTypeEnum);


    /**
     * 同步缓存中的反馈点赞数据到数据库中
     *
     * @return int 同步点赞数量
     */
    int syncLike(LikeBusTypeEnum busTypeEnum);
}
