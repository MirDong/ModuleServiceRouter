package com.dzk.biz_ogin;

import com.dzk.shell.moduleservice.stub.LoginServiceStub;



public class LoginService implements LoginServiceStub {
    @Override
    public void login(String userName, String password, final LoginCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //模拟网络请求
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (System.currentTimeMillis() % 2 == 0){
                    callBack.onSuccess(new LoginData("登录成功"));
                }else {
                    callBack.onFailed(-1,"net error");
                }
            }
        }).start();
    }
}
