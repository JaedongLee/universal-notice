package com.zoutairan.universal.notice.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @package: com.zoutairan.universal.notice.pojo
 * @className: Message
 * @author: tairan.zou
 * @createDate: 7/2/2021 10:10 AM
 * @updateUser: tairan.zou
 * @updateDate: 7/2/2021 10:10 AM
 * @updateRemark:
 * @version: 1.0
 * @copyright: Copyright (c) 2021
 */
@Data
@Accessors(chain = true)
public class Message {
    private String msgtype;
    private At at;
    private Text text;

    public static Message of() {
        return new Message();
    }

    @Data
    @Accessors(chain = true)
    public static class Text {
        private String content;
    }

    @Data
    @Accessors(chain = true)
    public static class At {
        public static At of() {
            return new At();
        }

        private List<String> atMobiles;
    }

}
