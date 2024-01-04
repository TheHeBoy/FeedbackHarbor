package cn.hh.harbor.module.harbor.service.like;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hh.harbor.module.harbor.controller.app.like.vo.AppLikeReqVO;
import cn.hh.harbor.module.harbor.dal.dataobject.comment.CommentDO;
import cn.hh.harbor.module.harbor.dal.dataobject.feedback.FeedbackDO;
import cn.hh.harbor.module.harbor.dal.mysql.comment.CommentMapper;
import cn.hh.harbor.module.harbor.dal.mysql.feedback.FeedbackMapper;
import cn.hh.harbor.module.harbor.dal.dataobject.like.LikeDO;
import cn.hh.harbor.module.harbor.dal.mysql.like.LikeMapper;
import cn.hh.harbor.module.harbor.dal.redis.like.LikeRedisDAO;
import cn.hh.harbor.module.harbor.enums.common.BusTypeEnum;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @Transactional
    @Override
    public boolean like(Long uid, AppLikeReqVO appLikeReqVO) {
        Long rid = appLikeReqVO.getRid();
        BusTypeEnum busTypeEnum = BusTypeEnum.valueOf(appLikeReqVO.getBusType());
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
    public Set<Long> listByUid(Long uid, BusTypeEnum busTypeEnum) {
        Set<Long> resultSet = new HashSet<>();

        // 缓存中的点赞
        Set<Long> redisLikeSet = listByUidWithRedis(uid, true, busTypeEnum);

        // mysql 中的点赞
        Set<Long> mysqlSet = likeMapper.selectList(LikeDO::getUid, uid).stream().map(LikeDO::getRid).collect(Collectors.toSet());
        // 通过缓存中的取消点赞对 mysql 中的点赞进行过滤
        Set<Long> cancelLike = listByUidWithRedis(uid, false, busTypeEnum);
        Set<Long> mysqlLikeSet = mysqlSet.stream().filter(t -> !cancelLike.contains(t)).collect(Collectors.toSet());

        resultSet.addAll(redisLikeSet);
        resultSet.addAll(mysqlLikeSet);
        return resultSet;
    }

    @Transactional
    @Override
    public synchronized int syncLike(BusTypeEnum busTypeEnum) {
        return like(true, busTypeEnum) - like(false, busTypeEnum);
    }

    /**
     * 同步redis到数据库中
     *
     * @param action      true-点赞 false-取消点赞
     * @param busTypeEnum 业务枚举
     * @return int
     */
    private int like(boolean action, BusTypeEnum busTypeEnum) {
        Set<String> keys = likeRedisDAO.list(action, busTypeEnum);
        // redis 点赞列表
        List<LikeDO> redisLikes = new ArrayList<>();
        // 同步的业务表
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
                redisLikes.add(likeDO);
            }

            // 获取点赞数量
            long count = likeRedisDAO.sSize(rid, action, busTypeEnum);
            count = action ? count : -count;
            switch (busTypeEnum) {
                case FEEDBACK:
                    FeedbackDO feedbackDO = new FeedbackDO();
                    feedbackDO.setId(rid);
                    if(feedbackMapper.selectById(rid) == null){
                        log.error(rid + "-" + action);
                    }
                    feedbackDO.setLikes(feedbackMapper.selectById(rid).getLikes() + count);
                    feedbackDOs.add(feedbackDO);
                    break;
                case COMMENT:
                    CommentDO comment = new CommentDO();
                    comment.setId(rid);
                    if(commentMapper.selectById(rid) == null){
                        log.error(rid + "-" + action);
                    }
                    comment.setLikes(commentMapper.selectById(rid).getLikes() + count);
                    commentDOS.add(comment);
                    break;
            }
        }

        if (action) {
            likeMapper.insertBatch(redisLikes);
        } else { // 删除点赞
            List<LikeDO> likeList = redisLikes.stream()
                    .map(e -> likeMapper.getByUidAndRid(e.getUid(), e.getRid()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(likeList)) {
                likeMapper.deleteBatchIds(likeList);
            }
        }

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
        return redisLikes.size();
    }

    private boolean isLiked(Long uid, Long rid, BusTypeEnum busTypeEnum) {
        Boolean isLiked = likeRedisDAO.isMember(uid, rid, true, busTypeEnum);

        if (isLiked) {
            return true;
        }

        Boolean isCancelLiked = likeRedisDAO.isMember(uid, rid, false, busTypeEnum);

        //redis中没有点赞和取消点赞的数据从数据库查询
        if (!isCancelLiked) {
            LikeDO feedbackLikeDO = likeMapper.getByUidAndRid(uid, rid);
            return ObjectUtil.isNotNull(feedbackLikeDO);
        }

        return false;
    }


    /**
     * 通过用户 id 得到 redis 所有关联id
     *
     * @param uid         用户id
     * @param likeAction  点赞类型
     * @param busTypeEnum 业务类型
     * @return {@link List}<{@link Integer}>
     */
    private Set<Long> listByUidWithRedis(Long uid, boolean likeAction, BusTypeEnum busTypeEnum) {
        Set<Long> rids = new HashSet<>();
        //得到所有点赞或取消点赞的key
        Set<String> keyList = likeRedisDAO.list(likeAction, busTypeEnum);

        for (String key : keyList) {
            String rid = key.split(":")[2];
            Set<Long> uidSet = likeRedisDAO.sGet(key);
            for (Long uid2 : uidSet) {
                if (ObjectUtil.equal(uid2, uid)) {
                    rids.add(Long.valueOf(rid));
                }
            }
        }
        return rids;
    }
}
