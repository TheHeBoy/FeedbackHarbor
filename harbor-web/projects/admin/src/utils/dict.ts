/**
 * 数据字典工具类
 */
import { useDictStoreWithOut } from '@/store/modules/dict';
import { ElementPlusInfoType } from '@/types/elementPlus';

const dictStore = useDictStoreWithOut();

/**
 * 获取 dictType 对应的数据字典数组
 *
 * @param dictType 数据类型
 * @returns {*|Array} 数据字典数组
 */
export interface DictDataType {
  dictType: string;
  label: string;
  value: string | number | boolean;
  colorType: ElementPlusInfoType | '';
  cssClass: string;
}

export const getDictOptions = (dictType: string) => {
  return dictStore.getDictByType(dictType) || [];
};

export const getIntDictOptions = (dictType: string) => {
  const dictOption: DictDataType[] = [];
  const dictOptions: DictDataType[] = getDictOptions(dictType);
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: parseInt(dict.value + ''),
    });
  });
  return dictOption;
};

export const getStrDictOptions = (dictType: string) => {
  const dictOption: DictDataType[] = [];
  const dictOptions: DictDataType[] = getDictOptions(dictType);
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: dict.value + '',
    });
  });
  return dictOption;
};

export const getBoolDictOptions = (dictType: string) => {
  const dictOption: DictDataType[] = [];
  const dictOptions: DictDataType[] = getDictOptions(dictType);
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: dict.value + '' === 'true',
    });
  });
  return dictOption;
};

export const getDictObj = (dictType: string, value: any) => {
  const dictOptions: DictDataType[] = getDictOptions(dictType);
  dictOptions.forEach((dict: DictDataType) => {
    if (dict.value === value.toString()) {
      return dict;
    }
  });
};

/**
 * 获得字典数据的文本展示
 *
 * @param dictType 字典类型
 * @param value 字典数据的值
 */
export const getDictLabel = (dictType: string, value: any) => {
  const dictOptions: DictDataType[] = getDictOptions(dictType);
  const dictLabel = ref('');
  dictOptions.forEach((dict: DictDataType) => {
    if (dict.value === value.toString()) {
      dictLabel.value = dict.label;
    }
  });
  return dictLabel.value;
};

export enum DICT_TYPE {
  USER_TYPE = 'user_type',
  COMMON_STATUS = 'common_status',

  // ========== SYSTEM 模块 ==========
  SYSTEM_USER_SEX = 'system_user_sex',
  SYSTEM_MENU_TYPE = 'system_menu_type',
  SYSTEM_INVITE_STATUS = 'system_invite_status',
  SYSTEM_OPERATE_TYPE = 'system_operate_type',
  SYSTEM_LOGIN_TYPE = 'system_login_type',
  SYSTEM_LOGIN_RESULT = 'system_login_result',
  SYSTEM_MAIL_SEND_STATUS = 'system_mail_send_status',
  SYSTEM_ROLE_CODE = 'system_role_code',
  // ========== INFRA 模块 ==========
  INFRA_BOOLEAN_STRING = 'infra_boolean_string',
  INFRA_JOB_STATUS = 'infra_job_status',
  INFRA_JOB_LOG_STATUS = 'infra_job_log_status',
  INFRA_API_ERROR_LOG_PROCESS_STATUS = 'infra_api_error_log_process_status',
  INFRA_CONFIG_TYPE = 'infra_config_type',
  INFRA_FILE_STORAGE = 'infra_file_storage',
  // ========== Harbor 模块 ==========
  HARBOR_REPLY_STATE = 'harbor_reply_state',
}
