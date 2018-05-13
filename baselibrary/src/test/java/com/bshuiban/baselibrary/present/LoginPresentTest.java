package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.utils.aes.AESUtils;
import com.bshuiban.baselibrary.view.activity.LoginActivity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by xinheng on 2018/4/26.<br/>
 * describe：
 */
public class LoginPresentTest {
    @Test
    public void desEncode(){//aes解码
        //http://192.168.0.3/interface/HuiBanApi.php?key=logInByUidAndPwd&param=bzasOy2lURJECjTg9Xe8hvLjwfypUbgLc6ll7/M02uFm4QL5lEzlo5a5K7TPgn254Smcd81WBtc9%0AJqXuztYb/w%3D%3D
        //String data = "1vxbnSZ65iLzwbwF6r/eBUMmxd44RNUVwcz1pGEjQGihyj02sIs5CryI5ptYces6JQGJLwP7frzSzpEWiaqohJUvDux51A9nvH/S3dOQTFo=";
        //String data = "FHx79dn0VNSICOffairXsJgV8NN35sPM508IN32we5j0qe9PytEbpJcOpFGbcNhl5iExqUUousKNgrnLvG3okwaEjP9CFYcDRXIKecXN+r69tThTv4IL49NzYjlKIkxtnTym4DpUpd8wEjCSOQTuDlp05r3ND/cJ9X+rQOZIiMXq/WaoR8ZdL86m1d1GEzZvWPmoCg1+n98uwc94vCCd4VhFYqtQEk5jfl88SaRec22zip+N0JLz+gstCyFNx+8hZmzC4iwv5TwWFA89PUvZjXpJ+COhU4vupkpnjHJngKtuNrN6tZD1xAdAzl0sC+z5kEYal4MrXl6T/wUcEvkejKyx3owMHMlROr+SSTr8ESw=";
//        String data = "bzasOy2lURJECjTg9Xe8hvLjwfypUbgLc6ll7/M02uFm4QL5lEzlo5a5K7TPgn254Smcd81WBtc9%0AJqXuztYb/w%3D%3D";
        //String data = "qiE3uNYA8NNlyOPVtNhBtSefZc9J3YR/CuHGMN+9esWRQkAj9yRhTbAipX12YtTk";
        String data = "CDr6pUtYsFjLTwN+Toz7U3jsf7TRl/YeIW+L8EG+0F8=";
//        String data = "bzasOy2lURJECjTg9Xe8hi/yyN5F249ToeSOwHPMqPLc9Ut8wClP0xvNWk8RhKwPyaIQYh5F70xf\n" +
//                "qv8zZjDLnA==";
        String s = AESUtils.desEncrypt(data);
        System.out.print(s+"\n");
    }
    @Test
    public void des(){//aes加密
        String data="{\"userPwd\":\"111111\",\"terminal\":\"7\",\"userId\":\"2030246\"}";
        String s = AESUtils.encrypt(data);
        System.out.print(s+"\n");
    }
    @Test
    public void login() {
//        String s = AESUtils.desEncrypt("1vxbnSZ65iLzwbwF6r/eBUMmxd44RNUVwcz1pGEjQGihyj02sIs5CryI5ptYces6JQGJLwP7frzSzpEWiaqohJUvDux51A9nvH/S3dOQTFo=");
//        System.out.print(s+"\n");
        LoginActivity loginActivity=new LoginActivity();
        LoginPresent loginPresent=new LoginPresent(loginActivity);
        loginPresent.login("2030246","111111");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}