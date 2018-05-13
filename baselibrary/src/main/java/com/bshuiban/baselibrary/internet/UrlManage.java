package com.bshuiban.baselibrary.internet;

public class UrlManage {
    private static final String NEI_WANG_URL = "http://192.168.0.3/";
    private static final String WAI_WANG_URL = "http://hcloud.bszhihui.com/";
    public final String BASE_URL;
    public static final int NEI_WANG = 0;
    public static final int WAI_WANG = 1;

    private static UrlManage manage;

    private static final int type=NEI_WANG;
    public static UrlManage getInstance() {
        if(manage==null){
            synchronized (UrlManage.class){
                if(manage==null){
                    manage=new UrlManage(type);
                }
            }
        }
        return manage;
    }
    private UrlManage(int type) {
        switch (type) {
            case NEI_WANG:
                BASE_URL = NEI_WANG_URL;
                break;
            case WAI_WANG:
                BASE_URL = WAI_WANG_URL;
                break;
            default:
                BASE_URL = NEI_WANG_URL;
        }
    }

    public String getBASE_URL() {
        return BASE_URL;
    }
}
