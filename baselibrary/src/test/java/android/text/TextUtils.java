package android.text;

/**
 * Created by xinheng on 2018/5/18.<br/>
 * describe：
 */
public class TextUtils {
    public static boolean isEmpty(CharSequence s){
        if(null==s||s.length()==0){
            return true;
        }
        return false;
    }
}
