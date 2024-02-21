package com.example.api.codeEdny.web;

import com.example.util.*;
import com.example.util.annotion.Log;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * 二维码解码控制页
 * @author Enoki
 */
@RestController
public class CodeFileEdnyConteroller {

    private static final Logger log = Logger.getLogger(CodeFileEdnyConteroller.class);

    /**
     * 通过base64解析二维码
     * @param session
     */
    @Log(title = "使用base64解析二维码（公开）", type = LogEnum.UPLOAD)
    @PostMapping("open/loadImgBaseForId.act")
    public ResultBody loadImgBaseForId(HttpSession session, String baseId){
        //清理前缀
        baseId = baseId.substring(baseId.indexOf(",", 1) + 1, baseId.length());
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bit = decoder.decode(baseId);
        try {
            Map<String, String> hasMap = codeEdny(bit);
            return new ResultBody(ApiResultEnum.SUCCESS, hasMap);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResultBody(ApiResultEnum.ERR, e.getMessage());
        }
    }

    private Map<String,String> codeEdny(String path) throws Exception{
        //要解析的二维码路径
        Map map = new HashMap();
        String filename = path;
        BufferedImage image= ImageIO.read(new File(filename));
        return endy(image);
    }

    private Map<String,String> codeEdny(byte[] path) throws Exception{
        //要解析的二维码路径
        ByteArrayInputStream gry = new ByteArrayInputStream(path);
        BufferedImage image= ImageIO.read(gry);
        return endy(image);
    }

    private Map<String,String> endy(BufferedImage image) throws Exception{
        HashMap map = new HashMap();
        LuminanceSource source=new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap=new BinaryBitmap(new HybridBinarizer(source));
        Result result = new MultiFormatReader().decode(bitmap,map);
        map.put("code",result.toString());
        map.put("type",result.getBarcodeFormat());
        map.put("text",result.getText());
        return map;
    }

}
