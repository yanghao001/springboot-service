package com.haozi.nio.utils;

import java.nio.ByteBuffer;

/**
 * @author hao.yang
 * @date 2019/7/11
 */
public class ByteUtils {

    public static byte[] decodeValue(ByteBuffer bytes) {
        int len = bytes.limit() - bytes.position();
        byte[] bytes1 = new byte[len];
        bytes.get(bytes1);
        return bytes1;
    }

}
