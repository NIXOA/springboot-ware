package com.shinra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiaoxin
 * @date 2019/7/15
 */
public class StyleTest {
    private static final String KEY="key";
    private static final String VALUE="value";

    private static void test(){
        Map<String,String> map=new HashMap<>(2);
        map.put(KEY,VALUE);
        map.forEach((k,v)-> System.out.println("k= "+k+",value="+v));
    }


    public static void main(String[] args) {
        test();
    }
}
