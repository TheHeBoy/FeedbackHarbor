package cn.hh.harbor.framework.desensitize.core.regex.handler;

import cn.hh.harbor.framework.desensitize.core.regex.annotation.RegexDesensitize;

/**
 * {@link RegexDesensitize} 的正则脱敏处理器
 *
 *  gaibu
 */
public class DefaultRegexDesensitizationHandler extends AbstractRegexDesensitizationHandler<RegexDesensitize> {

    @Override
    String getRegex(RegexDesensitize annotation) {
        return annotation.regex();
    }

    @Override
    String getReplacer(RegexDesensitize annotation) {
        return annotation.replacer();
    }
}
