package com.example.token.web;

import com.example.person.enity.Person;
import com.example.util.*;
import com.example.util.config.RedisUtils;
import com.example.util.dic.ConfigDicEnum;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * token信息处理
 * @author Enoki
 */
@RestController
public class TokenSession {

    private static final String keyItem = "wuhu";//懒得做加密处理了，密参就这么用着

    @Resource
    private RedisUtils redisUtils;

    public ResultBody tokenAssDemo(String sessionId, String tokenItem){
        try{
            Person person = (Person)redisUtils.get(sessionId);
            if(tokenItem == null || person == null){//无token
                return new ResultBody(ApiResultEnum.OVER_TOKEN, "用户信息失效，请重新登入");
            }else{//有token
                String tokEn = assSysItemKey(tokenItem);//一层解锁，用于解密Token的参数
                String[] tokItem = tokEn.split("\\.");//处理分隔符
                if(tokItem.length!=3){//token不符合规格
                    return new ResultBody(ApiResultEnum.ERROR_TOKRN, "token非法，请重新登入");
                }else{//token符合规格
                    String tokDouble = assSysItemKey(tokItem[0]);//二层解锁，用于解锁token
                    if(!tokDouble.equals(keyItem)){//token不符合密钥
                        return new ResultBody(ApiResultEnum.ERROR_TOKRN, "token非法，请重新登入");
                    }
                    //当前时间
                    long nowCurr = System.currentTimeMillis() / 1000;
                    //token时间
                    long curr = Long.parseLong(tokItem[2]);
                    if(nowCurr>curr || !tokItem[1].equals(person.getIds())){//时间超时或者不是同一个人
                        return new ResultBody(ApiResultEnum.OVER_TOKEN, "token过期，请重新登入");
                    }else{
                        int timeOut = Integer.parseInt(redisUtils.getConfig(ConfigDicEnum.tokenTimeOut.message));
                        if(timeOut == 0){
                            timeOut = 1000;
                        }
                        if(curr - timeOut < nowCurr){
                            String token = AseToken(person.getIds());
                            redisUtils.set(sessionId, person, (long) timeOut, TimeUnit.SECONDS);
                            return new ResultBody(ApiResultEnum.UPDATA_TOKRN,"更新成功", token);
                        }
                        return new ResultBody(ApiResultEnum.SUCCESS,"成功", tokenItem);
                    }
                }
            }
        }catch (Exception e){
            return new ResultBody(ApiResultEnum.OVER_TOKEN, "token过期，请重新登入");
        }
    }

    /**
     * 制造Token
     */
    public String AseToken(String personId){
        long sysCurr = assSysCuur(); //时间戳
        String keys = assSysKey(keyItem);//密钥处理
        String ids = assTypeIp(personId);//id处理
        String doubleKey = keys + "." + ids + "." + sysCurr;
        return assSysKey(doubleKey);
    }

    /**
     * 制造时间戳
     * @return time时间戳
     */
    public long assSysCuur(){
        return (System.currentTimeMillis() / 1000) + 1800; //半小时后的时间戳
    }

    /**
     * 制造唯一键
     * @param keyItem 这玩意在做一级加密的时候可以传入item，二级加密的时候传入的是特殊数据
     * @return
     */
    public String assSysKey(String keyItem){
        byte[] bytes = keyItem.getBytes();
        String tyKey = (new BASE64Encoder()).encodeBuffer(bytes);
        tyKey = tyKey.replaceAll("\r|\n", "");//全平台兼容，替换/r/n换行
        return tyKey;
    }

    /**
     * 解锁唯一键
     * @return
     */
    public String assSysItemKey(String getKey) throws IOException {
        if(StringBlack.isBlackString(getKey)){
            return "1";
        }
        return new String((new BASE64Decoder()).decodeBuffer(getKey));
    }

    /**
     * 本机IP处理
     * @return
     */
    public String assTypeIp(String ip){
        if(StringBlack.isBlackString(ip)){
            return "2";
        }
        return ip;
    }

}