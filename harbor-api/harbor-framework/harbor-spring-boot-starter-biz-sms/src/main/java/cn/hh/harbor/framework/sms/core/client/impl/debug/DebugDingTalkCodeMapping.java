package cn.hh.harbor.framework.sms.core.client.impl.debug;

import cn.hh.harbor.framework.common.exception.ErrorCode;
import cn.hh.harbor.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.hh.harbor.framework.sms.core.client.SmsCodeMapping;
import cn.hh.harbor.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.Objects;

/**
 * 钉钉的 SmsCodeMapping 实现类
 *
 * 
 */
public class DebugDingTalkCodeMapping implements SmsCodeMapping {

    @Override
    public ErrorCode apply(String apiCode) {
        return Objects.equals(apiCode, "0") ? GlobalErrorCodeConstants.SUCCESS : SmsFrameworkErrorCodeConstants.SMS_UNKNOWN;
    }

}
