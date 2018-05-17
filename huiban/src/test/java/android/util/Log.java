package android.util;

/**
 * Created by xinheng on 2018/5/15.<br/>
 * describe：
 */
public final class Log {
    public static int e(String tag,String msg){
        System.out.print("tag = "+msg+"\n");
        return 1;
    }
    public static int i(String tag,String msg){
        System.out.print("tag = "+msg+"\n");
        return 1;
    }
}
