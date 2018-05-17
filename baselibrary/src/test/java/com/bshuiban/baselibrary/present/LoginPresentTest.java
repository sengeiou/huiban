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
        //String data = "CDr6pUtYsFjLTwN+Toz7U3jsf7TRl/YeIW+L8EG+0F8=";
        //String data = "qiE3uNYA8NNlyOPVtNhBtRQ7RGyYsaQa4ih7YCxmWscfVBw25YTb5hyuw9zl605D";
//        String data = "B+OQYpIJCzNvYR3sTPuOqWE9DeoNww2qswpocyJzYuHnwWLOYmKHo5aTxxB0cfOFYy+qqSBggUAN\n" +
//                "azuWOdqJgX728GUjdJz9OHvpC3Dg3LwRHe6HzcSEBjH4Yamgr6Vz+twmdqmOFq/H+XB7/rc20ZIg\n" +
//                "TEku7ob3u/F8+mj86S0Qd0A8FMDfNJizGw5oqGnk558pzcBUzt7hehzQ6sAFzmaWg7eJoqGm6dWc\n" +
//        String data="B+OQYpIJCzNvYR3sTPuOqWE9DeoNww2qswpocyJzYuHnwWLOYmKHo5aTxxB0cfOFYy+qqSBggUAN\n" +
//                "azuWOdqJgX728GUjdJz9OHvpC3Dg3LwRHe6HzcSEBjH4Yamgr6Vz+twmdqmOFq/H+XB7/rc20ZIg\n" +
//                "TEku7ob3u/F8+mj86S0Qd0A8FMDfNJizGw5oqGnk558pzcBUzt7hehzQ6sAFzmaWg7eJoqGm6dWc\n" +
//                "Wp5mo5DLg0cWJyuxTE654mtFOL+kLOSL6WQ67PUQRUwzmCmw1A==";
//        String data = "bzasOy2lURJECjTg9Xe8hi/yyN5F249ToeSOwHPMqPLc9Ut8wClP0xvNWk8RhKwPyaIQYh5F70xf\n" +
//                "qv8zZjDLnA==";
        String data1="B+OQYpIJCzNvYR3sTPuOqWE9DeoNww2qswpocyJzYuHnwWLOYmKHo5aTxxB0 cfOFYy+qqSBggUANazuWOdqJgX728GUjdJz9OHvpC3Dg3LwRHe6HzcSEBjH4 Yamgr6Vz+twmdqmOFq/H+XB7/rc20ZIgTEku7ob3u/F8+mj86S0Qd0A8FMDf NJizGw5oqGnk558pzcBUzt7hehzQ6sAFzmaWg7eJoqGm6dWcWp5mo5DLg0cW JyuxTE654mtFOL+kLOSL6WQ67PUQRUwzmCmw1A==";
        //String data="C4zM/Z8Fq5xkIaDZg0Ts1BCtIYnjDNao+E6fNq+10d5KzdUidMLkd9mzsFco U2ytu1u33G2g";
        //String data="6F36AC3B2DA55112440A34E0F577BC862FF2C8DE45DB8F53A1E48EC073CCA8F2DCF54B7CC0294FD31BCD5A4F1184AC0F74E1BA9F762CDCB285532D2F8B4A4A0B";
        String data="bEZQqtO9wRGRnoE3BOgY9x5F6dNWWeGrwY2glQ2s5v+V4o8NoiU1cMkkySTSF3KhDVlUOih4Jr/l0RBXupLAZmUvG4sQulUTW5zKKwz5jq8=";
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
        LoginPresent loginPresent=new LoginPresent(null);
        loginPresent.login("2030246","111111");
        //loginPresent.askInternet("","{\"userId\":\"2030246\",\"userPwd\":\"111111\"}");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}