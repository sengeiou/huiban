package com.bshuiban.baselibrary.present;

import com.bshuiban.baselibrary.internet.RetrofitService;
import com.bshuiban.baselibrary.utils.aes.AESUtils;

import org.junit.Test;

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
        //String data1="B+OQYpIJCzNvYR3sTPuOqWE9DeoNww2qswpocyJzYuHnwWLOYmKHo5aTxxB0 cfOFYy+qqSBggUANazuWOdqJgX728GUjdJz9OHvpC3Dg3LwRHe6HzcSEBjH4 Yamgr6Vz+twmdqmOFq/H+XB7/rc20ZIgTEku7ob3u/F8+mj86S0Qd0A8FMDf NJizGw5oqGnk558pzcBUzt7hehzQ6sAFzmaWg7eJoqGm6dWcWp5mo5DLg0cW JyuxTE654mtFOL+kLOSL6WQ67PUQRUwzmCmw1A==";
        //String data="C4zM/Z8Fq5xkIaDZg0Ts1BCtIYnjDNao+E6fNq+10d5KzdUidMLkd9mzsFco U2ytu1u33G2g";
        //String data="qiE3uNYA8NNlyOPVtNhBtSefZc9J3YR%2FCuHGMN%2B9esUuDE5bWwM1RExb27IQQEGg";

        String data="VftGejaYqBDw6MTh6QnqWQNcQF2yN+CAp2dEhyH+uCpVhXeTJdyvdkKZNhL3UGeJ";
        String s = AESUtils.desEncrypt(data);
        System.out.print(s+"\n");
    }
    @Test
    public void des(){//aes加密
        //String data="{\"userPwd\":\"111111\",\"terminal\":\"7\",\"userId\":\"2030246\"}";
        String empty=" ";
        System.out.print(empty.getBytes());
        //String data="{\"code\":\"2002\",\"msg\":\"success\",\"data\":[{\"id\":\"5b2cc826f5ddaf74632209eb\",\"imgUrl\":\"\",\"send\":\"2030246\",\"sendRoleId\":1,\"sendName\":\"\\u6234\\u5e05\\u5e05\",\"content\":\"\\u4f60\\u597d\",\"addTime\":\"2018-06-22 17:57\",\"count\":0},{\"id\":\"5b2c48c2f5ddaf74632208d8\",\"imgUrl\":\"\",\"send\":\"2030246\",\"sendRoleId\":1,\"sendName\":\"\\u6234\\u5e05\\u5e05\",\"content\":\"\\u8df3\\u821e\\u6bef\",\"addTime\":\"2018-06-22 08:54\",\"slist\":[{\"id\":\"5b2cc8a2f5ddaf74632209ed\",\"send\":\"2030246\",\"sendRoleId\":1,\"sendName\":\"\\u6234\\u5e05\\u5e05\",\"receive\":\"2030246\",\"receiveName\":\"\\u6234\\u5e05\\u5e05\",\"content\":\"\\u554a\\u554a\\u554a\",\"addTime\":\"2018-06-22 18:00\"},{\"id\":\"5b2cc763f5ddaf74632209e9\",\"send\":\"2030246\",\"sendRoleId\":1,\"sendName\":\"\\u6234\\u5e05\\u5e05\",\"receive\":\"2030246\",\"receiveName\":\"\\u6234\\u5e05\\u5e05\",\"content\":\"\\u554a\\u554a\\u554a\",\"addTime\":\"2018-06-22 17:54\"},{\"id\":\"5b2cc6eaf5ddaf74632209e7\",\"send\":\"2030246\",\"sendRoleId\":1,\"sendName\":\"\\u6234\\u5e05\\u5e05\",\"receive\":\"2030246\",\"receiveName\":\"\\u6234\\u5e05\\u5e05\",\"content\":\"\\u54c8\\u54c8\\u54c8\\u54c8\\u54c8\",\"addTime\":\"2018-06-22 17:52\"},{\"id\":\"5b2cc6d1f5ddaf74632209e5\",\"send\":\"2030246\",\"sendRoleId\":1,\"sendName\":\"\\u6234\\u5e05\\u5e05\",\"receive\":\"2030246\",\"receiveName\":\"\\u6234\\u5e05\\u5e05\",\"content\":\"\\u60a8\\u9ed8\\u9ed8\",\"addTime\":\"2018-06-22 17:52\"}],\"count\":4}]}            ";
        String date="";
        String data="{\"filterModule\":\"stuWrong\",\"schoolId\":\"45\"}" ;
        //int i = data.indexOf(empty);//12
        System.out.print(1268 + (16 - (1268 % 16)));
        for (int i=data.length()-1;i>0;i--){
            if(String.valueOf(data.charAt(i)).equals(empty)){

            }else {
                System.out.print(i+"\n");
                break;
            }
        }
        String s = AESUtils.encrypt(data);
        System.out.print(s+"\n");
    }
    @Test
    public void login() {
//        String s = AESUtils.desEncrypt("1vxbnSZ65iLzwbwF6r/eBUMmxd44RNUVwcz1pGEjQGihyj02sIs5CryI5ptYces6JQGJLwP7frzSzpEWiaqohJUvDux51A9nvH/S3dOQTFo=");
//        System.out.print(s+"\n");
//        LoginPresent loginPresent=new LoginPresent(null);
        //loginPresent.login("2030246","111111");
        //loginPresent.askInternet("","{\"userId\":\"2030246\",\"userPwd\":\"111111\"}");
        //{"code":"2002","msg":"success","data":{"userId":2030219,"userType":3,"realName":"\u694a\u694a","icoPath":"","schoolId":45,"schoolName":"\u9752\u5c9b\u5b9e\u9a8c\u521d\u7ea7\u4e2d\u5b66","classId":[3000153,3000151],"gradeId":0,"otherId":"2030219","vipDays":0}}
        RetrofitService.getInstance().getServiceResult("logInByUidAndPwd","{\"userId\":\"2030219\",\"userPwd\":\"222222\"}",new RetrofitService.CallTest());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}