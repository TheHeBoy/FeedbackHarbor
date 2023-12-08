import { computed, ref } from 'vue';

export enum LoginStateEnum {
  LOGIN,
  REGISTER,
  FORGET_PASSWD,
}

const currentState = ref(LoginStateEnum.LOGIN);

export function useLoginState() {
  function setLoginState(state: LoginStateEnum) {
    currentState.value = state;
  }

  function checkLoginState(loginState: LoginStateEnum) {
    return loginState == currentState.value;
  }

  function handleBackLogin() {
    setLoginState(LoginStateEnum.LOGIN);
  }

  return {
    setLoginState,
    checkLoginState,
    handleBackLogin,
  };
}
