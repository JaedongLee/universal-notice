package com.zoutairan.universal.notice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zoutairan.universal.notice.dao.DingTalkUserDao;
import com.zoutairan.universal.notice.entity.DingTalkUserEntity;
import com.zoutairan.universal.notice.pojo.Message;
import com.zoutairan.universal.notice.pojo.Message.At;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private DingTalkUserDao dingTalkUserDao;

    @PostMapping("send")
    public String send(@RequestParam("ownerName") String ownerName, @RequestBody Message message) throws NoSuchAlgorithmException, InvalidKeyException {
        var url = "https://oapi.dingtalk" +
                ".com/robot/send?access_token=644bba67c37b732c35d01809c5c55e3e3e26a6c9bfa373b5c16f7b56ed88e654";
        var secret = "SECf58d5116f64f5cd0e2d2200f70b4cb772b90e11a49706bdfa56e7c5ee9465c2b";
        url = buildUrl(url, secret);
        var restTemplate = new RestTemplate();
        QueryWrapper<DingTalkUserEntity> queryWrapper = new QueryWrapper<DingTalkUserEntity>().eq("name", ownerName);
        var dingTalkUserEntity = dingTalkUserDao.selectOne(queryWrapper);
        At at = At.of().setAtMobiles(newArrayList(dingTalkUserEntity.getCellphoneNumber()));
        message.setAt(at);
        var request = new HttpEntity<>(message);
        return restTemplate.postForObject(url, request, String.class);
    }

    private String buildUrl(String url, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        long timestamp = System.currentTimeMillis();
        var stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(UTF_8));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), UTF_8);
        url = url + "&timestamp=" + timestamp + "&sign=" + sign;
        return url;
    }
}
