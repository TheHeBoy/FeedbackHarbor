declare interface codeMessageMapTypes {
  [key: string]: string;
}

const codeMessageMap: codeMessageMapTypes = {
  404: '[404]:请求路径错误',
};

const showCodeMessage = (code: number): string => {
  return codeMessageMap[code];
};

export default showCodeMessage;
