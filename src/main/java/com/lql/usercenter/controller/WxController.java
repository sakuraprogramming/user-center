package com.lql.usercenter.controller;


import com.alibaba.fastjson.JSONObject;
import com.lql.usercenter.common.BaseResponse;
import com.lql.usercenter.common.ResultUtils;
import com.lql.usercenter.config.WxConfig;
import com.lql.usercenter.mapper.UserMapper;
import com.lql.usercenter.model.CodeLoginKey;
import com.lql.usercenter.model.request.UserLoginRequest;
import com.lql.usercenter.service.UserService;
import com.lql.usercenter.service.WebSocketService;
import com.lql.usercenter.util.CodeLoginUtil;
import com.lql.usercenter.util.HttpClientUtil;
import com.lql.usercenter.util.ResultJson;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("qrCodeFirstLogin")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8000"}, allowCredentials = "true")

public class WxController {
    private static final String SALT = "sakura";

    @Autowired
    private WxConfig wxConfig;

    @Resource
    private WebSocketService webSocketService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;
    // 模拟数据库存储或者缓存存储
    Map<String, CodeLoginKey> loginMap = new ConcurrentHashMap<>(64);

    /**
     * 获取登录二维码
     *
     * @return
     */
    @GetMapping("/getQrCode")
    private ResultJson getQrCode() {
        try {
            // 获取token开发者
            String accessToken = getAccessToken();
            String getQrCodeUrl = wxConfig.getQrCodeUrl().replace("TOKEN", accessToken);
            // 这里生成一个带参数的二维码，参数是scene_str
            String sceneStr = CodeLoginUtil.getRandomString(8);
            String json = "{\"expire_seconds\": 604800, \"action_name\": \"QR_STR_SCENE\"" + ", \"action_info\": {\"scene\": {\"scene_str\": \"" + sceneStr + "\"}}}";
            String result = HttpClientUtil.doPostJson(getQrCodeUrl, json);
            JSONObject jsonObject = JSONObject.parseObject(result);
            jsonObject.put("sceneStr", sceneStr);
            return ResultJson.ok(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultJson.error(e.getMessage());
        }
    }

    /**
     * 获取accessToken
     *
     * @return
     */
    public String getAccessToken() {
        String accessToken = null;
        String getTokenUrl = wxConfig.getTokenUrl().replace("APPID", wxConfig.getAppId()).replace("SECRET", wxConfig.getAppSecret());
        String result = HttpClientUtil.doGet(getTokenUrl);
        JSONObject jsonObject = JSONObject.parseObject(result);
        accessToken = jsonObject.getString("access_token");
        return accessToken;
    }

    /**
     * 验证签名
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/checkSign")
    public String checkSign(HttpServletRequest request) throws Exception {
        log.info("===========>checkSign");
        //获取微信请求参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //参数排序。 token 就要换成自己实际写的 token
        String[] params = new String[]{timestamp, nonce, "123456"};
        Arrays.sort(params);
        //拼接
        String paramstr = params[0] + params[1] + params[2];
        //加密
        //获取 shal 算法封装类
        MessageDigest Sha1Dtgest = MessageDigest.getInstance("SHA-1");
        //进行加密
        byte[] digestResult = Sha1Dtgest.digest(paramstr.getBytes("UTF-8"));
        //拿到加密结果
        String mysignature = CodeLoginUtil.bytes2HexString(digestResult);
        mysignature = mysignature.toLowerCase(Locale.ROOT);
        //是否正确
        boolean signsuccess = mysignature.equals(signature);
        //逻辑处理
        if (signsuccess && echostr != null) {
            //peizhi  token
            return echostr;//不正确就直接返回失败提示．
        } else {
            JSONObject jsonObject = callback(request);
            return jsonObject.toJSONString();
        }
    }

    /**
     * 回调方法
     *
     * @param request
     * @return
     * @throws Exception
     */
    public JSONObject callback(HttpServletRequest request) throws Exception {
        log.info("===========>callback");
        //request中有相应的信息，进行解析
        WxMpXmlMessage message = WxMpXmlMessage.fromXml(request.getInputStream());//获取消息流,并解析xml
        String messageType = message.getMsgType();                                //消息类型
        String messageEvent = message.getEvent();                                    //消息事件
        // openid
        String fromUser = message.getFromUser();                                    //发送者帐号
        String touser = message.getToUser();                                        //开发者微信号
        String text = message.getContent();                                        //文本消息  文本内容
        // 生成二维码时穿过的特殊参数
        String eventKey = message.getEventKey();//二维码参数
        String uuid = "";                                                            //从二维码参数中获取uuid通过该uuid可通过websocket前端传数据
        String userid = "";

        //if判断，判断查询
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        if (messageType.equals("event")) {
            jsonObject = null;
            String userPassword = "12345678";
            String checkedPassword = "12345678";
            //先根据openid从数据库查询  => 从自己数据库中查取用户信息 => jsonObject
            if (messageEvent.equals("SCAN")) {
                String messages = "@," + fromUser + "," + userPassword;
                webSocketService.sendMessage("123", messages);
                //扫描二维码
                //return "欢迎回来";
            }
            if (messageEvent.equals("subscribe")) {
                Random random = new Random();
                int num = random.nextInt(25) + 1;
                String planeCode = String.valueOf(num);
                userService.userRegister(fromUser, userPassword, checkedPassword, planeCode);
                String messages = "@," + fromUser + "," + userPassword;
                webSocketService.sendMessage("123", messages);
                //关注
                //return "谢谢您的关注";
            }
            //没有该用户
            if (jsonObject == null) {
                //从微信上中拉取用户信息
                String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + getAccessToken() +
                        "&openid=" + fromUser +
                        "&lang=zh_CN";
                String result = HttpClientUtil.doGet(url);
                jsonObject = JSONObject.parseObject(result);
                /**
                 * 用户信息处理....
                 */
            }
            // 扫码成功，存入缓存
            loginMap.put(eventKey, new CodeLoginKey(eventKey, fromUser));
            return jsonObject;
        }
        return jsonObject;
    }

    /**
     * 根据二维码标识获取用户openId=>获取用户信息
     *
     * @param eventKey
     * @return
     */
    @RequestMapping("/getOpenId")
    public ResultJson getOpenId(String eventKey) {
        if (loginMap.get(eventKey) == null) {
            return ResultJson.error("未扫码成功！");
        }
        CodeLoginKey codeLoginKey = loginMap.get(eventKey);
        loginMap.remove(eventKey);
        return ResultJson.ok(codeLoginKey);
    }


}
