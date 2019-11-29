package com.dzk.shell.moduleservice.stub;

import com.dzk.shell.moduleservice.core.ServiceTarget;

@ServiceTarget(value = "com.dzk.biz_ogin.LoginService")
public interface LoginServiceStub {
    void login(String userName,String password,LoginCallBack callBack);

    interface LoginCallBack{
        void onSuccess(LoginData loginData);
        void onFailed(int errorCode,String reason);
    }

    class LoginData{
        public String result;

        public LoginData(String result) {
            this.result = result;
        }
    }
}
