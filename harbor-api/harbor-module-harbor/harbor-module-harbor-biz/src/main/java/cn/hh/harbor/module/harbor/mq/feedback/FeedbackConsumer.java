package cn.hh.harbor.module.harbor.mq.feedback;

import cn.hh.harbor.module.harbor.dal.dataobject.feedback.FeedbackES;
import cn.hh.harbor.module.harbor.dal.es.feedback.FeedbackRepository;
import cn.hh.harbor.module.harbor.mq.MQKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 用于反馈信息和es数据同步
 *
 * @author hehong
 * @date 2023-12-24
 */
@EnableRabbit
@Slf4j
@Component
public class FeedbackConsumer {

    @Resource
    private FeedbackRepository feedbackRepository;

    @RabbitListener(queues = MQKeyConstants.HARBOR_FEEDBACK_UPDATE_QUEUE)
    public void listenHotelInsertOrUpdate(FeedbackES feedbackES) {
        feedbackRepository.save(feedbackES);
    }
}
