package cn.iocoder.yudao.module.harbor.service.like;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.harbor.controller.app.like.vo.AppLikeReqVO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.comment.CommentDO;
import cn.iocoder.yudao.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.module.harbor.dal.mysql.comment.CommentMapper;
import cn.iocoder.yudao.module.harbor.dal.mysql.feedback.FeedbackMapper;
import cn.iocoder.yudao.module.harbor.dal.dataobject.like.LikeDO;
import cn.iocoder.yudao.module.harbor.dal.mysql.like.LikeMapper;
import cn.iocoder.yudao.module.harbor.dal.redis.like.LikeRedisDAO;
import cn.iocoder.yudao.module.harbor.enums.like.LikeBusTypeEnum;
import cn.iocoder.yudao.module.harbor.enums.like.LikeStateEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户点赞 Service 实现类
 * <p>
 */
@Service
@Validated
public class LikeServiceImpl implements LikeService {

    @Resource
    private LikeRedisDAO likeRedisDAO;

    @Resource
    private LikeMapper likeMapper;

    @Resource
    private FeedbackMapper feedbackMapper;

    @Resource
    private CommentMapper commentMapper;

    @Override
    public boolean like(Long uid, AppLikeReqVO appLikeReqVO) {
        Long rid = appLikeReqVO.getRid();
        LikeBusTypeEnum busTypeEnum = LikeBusTypeEnum.valueOf(appLikeReqVO.getBusType());
        boolean isLiked = isLiked(uid, rid, busTypeEnum);
        //用户已经点赞了
        if (isLiked) {
            Long count = likeRedisDAO.remove(uid, rid, true, busTypeEnum);
            //删除了点赞的记录就不需要添加取消点赞的记录了
            if (count == 0) {
                likeRedisDAO.set(uid, rid, false, busTypeEnum);
            }
        } else {
            likeRedisDAO.set(uid, rid, true, busTypeEnum);
        }
        return !isLiked;
    }

    @Override
    public Set<Long> listByUid(Long uid, LikeBusTypeEnum busTypeEnum) {
        return listByUid(uid, true, busTypeEnum);
    }

    @Transactional
    @Override
    public int syncLike(LikeBusTypeEnum busTypeEnum) {
        return like(true, busTypeEnum) - like(false, busTypeEnum);
    }

    private int like(boolean action, LikeBusTypeEnum busTypeEnum) {
        Set<String> keys = likeRedisDAO.list(action, busTypeEnum);
        // 用户id点赞列表
        List<LikeDO> likes = new ArrayList<>();
        //同步的业务表
        List<FeedbackDO> feedbackDOs = new ArrayList<>();
        List<CommentDO> commentDOS = new ArrayList<>();

        for (String key : keys) {
            // 关联id
            Long rid = Long.valueOf(key.split(":")[2]);
            // 获取点赞用户id列表
            Set<Long> uidList = likeRedisDAO.sGet(key);
            for (Long uid : uidList) {
                LikeDO likeDO = new LikeDO();
                likeDO.setUid(uid);
                likeDO.setRid(rid);
                likeDO.setBusType(busTypeEnum.getCode());
                likeDO.setState(action ? LikeStateEnum.LIKED.getCode() : LikeStateEnum.LIKED_CANCEL.getCode());
                likes.add(likeDO);
            }

            // 获取评论点赞数量
            long count = likeRedisDAO.sSize(rid, action, busTypeEnum);
            count = action ? count : -count;
            switch (busTypeEnum) {
                case FEEDBACK:
                    FeedbackDO feedbackDO = new FeedbackDO();
                    feedbackDO.setId(rid);
                    feedbackDO.setLikes(feedbackMapper.selectById(rid).getLikes() + count);
                    feedbackDOs.add(feedbackDO);
                    break;
                case COMMENT:
                    CommentDO comment = new CommentDO();
                    comment.setId(rid);
                    comment.setLikes(commentMapper.selectById(rid).getLikes() + count);
                    commentDOS.add(comment);
                    break;
            }
        }

        // 添加点赞状态
        List<LikeDO> likeSavaList = likes.stream()
                .filter(e ->likeMapper.updateByUidAndRid(e, e.getUid(), e.getRid()) == 0)
                .collect(Collectors.toList());
        likeMapper.insertBatch(likeSavaList);

        // 修改点赞数量
        switch (busTypeEnum) {
            case FEEDBACK:
                if (CollectionUtil.isNotEmpty(feedbackDOs)) {
                    feedbackMapper.updateBatch(feedbackDOs);
                }
                break;
            case COMMENT:
                if (CollectionUtil.isNotEmpty(commentDOS)) {
                    commentMapper.updateBatch(commentDOS);
                }
                break;
        }
        // 清空redis点赞数据
        likeRedisDAO.removeBatch(action, busTypeEnum);
        return likes.size();
    }

    private boolean isLiked(Long uid, Long rid, LikeBusTypeEnum busTypeEnum) {
        Boolean isLiked = likeRedisDAO.isMember(uid, rid, true, busTypeEnum);

        if (isLiked) {
            return true;
        }

        Boolean isCancelLiked = likeRedisDAO.isMember(uid, rid, false, busTypeEnum);

        //redis中没有点赞和取消点赞的数据从数据库查询
        if (!isCancelLiked) {
            LikeDO feedbackLikeDO = likeMapper.getByUidAndRid(uid, rid);
            return ObjectUtil.isNotNull(feedbackLikeDO) &&
                    ObjectUtil.equal(feedbackLikeDO.getState(), LikeStateEnum.LIKED.getCode());
        }

        return false;
    }


    /**
     * 通过用户id得到所有关联id
     *
     * @param uid         用户id
     * @param likeAction  点赞类型
     * @param busTypeEnum 业务类型
     * @return {@link List}<{@link Integer}>
     */
    private Set<Long> listByUid(Long uid, boolean likeAction, LikeBusTypeEnum busTypeEnum) {
        Set<Long> feedbackIds = new HashSet<>();
        //得到所有点赞或取消点赞的key
        Set<String> keyList = likeRedisDAO.list(likeAction, busTypeEnum);

        for (String key : keyList) {
            String feedbackId = key.split(":")[2];
            Set<Long> uidSet = likeRedisDAO.sGet(key);
            for (Long uid2 : uidSet) {
                if (ObjectUtil.equal(uid2, uid)) {
                    feedbackIds.add(Long.valueOf(feedbackId));
                }
            }
        }

        Set<Long> mysqlSet = likeMapper
                .selectList(LikeDO::getUid, uid).stream().map(LikeDO::getRid).collect(Collectors.toSet());

        feedbackIds.addAll(mysqlSet);
        return feedbackIds;
    }
}
