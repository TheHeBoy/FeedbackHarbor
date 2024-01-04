/**
 * 通过时间戳得到过期时间(秒)
 * @param param 日期
 */
export function getExp(param: number) {
  let time: number = new Date().getTime();
  return (param - time) / 1000;
}
