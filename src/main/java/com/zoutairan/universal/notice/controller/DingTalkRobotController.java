package com.zoutairan.universal.notice.controller;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zoutairan.universal.notice.dao.DingTalkUserDao;
import com.zoutairan.universal.notice.entity.DingTalkUserEntity;
import com.zoutairan.universal.notice.pojo.Message;
import com.zoutairan.universal.notice.pojo.Message.At;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static cn.hutool.core.collection.CollUtil.newArrayList;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @package: com.zoutairan.universal.notice.controller
 * @className: DingTalkController
 * @author: tairan.zou
 * @createDate: 7/2/2021 9:59 AM
 * @updateUser: tairan.zou
 * @updateDate: 7/2/2021 9:59 AM
 * @updateRemark:
 * @version: 1.0
 * @copyright: Copyright (c) 2021
 */
@RestController
@RequestMapping("robot")
public class DingTalkRobotController {
    private static final String URL_FORMAT_TEXT = "{}?access_token={}&timestamp={}&sign={}";

    @Autowired
    private DingTalkUserDao dingTalkUserDao;

    @Value("${ding-talk.robot.send-message.url}")
    private String url;

    @Value("${ding-talk.robot.send-message.access-token}")
    private String accessToken;

    @Value("${ding-talk.robot.send-message.secret}")
    private String secret;

    @PostMapping("send")
    public String send(@RequestParam("ownerName") String ownerName, @RequestBody Message message) throws NoSuchAlgorithmException, InvalidKeyException {
        url = buildUrl(url, accessToken, secret);
        var restTemplate = new RestTemplate();
        QueryWrapper<DingTalkUserEntity> queryWrapper = new QueryWrapper<DingTalkUserEntity>().eq("name", ownerName);
        var dingTalkUserEntity = dingTalkUserDao.selectOne(queryWrapper);
        At at = At.of().setAtMobiles(newArrayList(dingTalkUserEntity.getCellphoneNumber()));
        message.setAt(at);
        var request = new HttpEntity<>(message);
        return restTemplate.postForObject(url, request, String.class);
    }

    private String buildUrl(String url, String accessToken, String secret) throws NoSuchAlgorithmException,
            InvalidKeyException {
        long timestamp = System.currentTimeMillis();
        var stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(UTF_8));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), UTF_8);
        return CharSequenceUtil.format(URL_FORMAT_TEXT, url, accessToken, timestamp, sign);
    }
}
