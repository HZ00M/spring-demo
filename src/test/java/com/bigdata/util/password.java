package com.bigdata.util;

import java.security.interfaces.RSAPublicKey;
//   RSA/ECB/PKCS1Padding算法加密
public class password {
	public static void main(String[] args) throws Exception {
		//公钥字符串
		String publicKeyReturn = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDRftpe4bsj51UsWZfPy/IRrBET6ohGU4OHGxmB56UHbDm3ZdEaX5Lwn+PWFKv6aK+dqtSo6c3PSQgbYU+eAdNzUxiG9sgafERu/zfbl/9Wz8BCOIIDRiSDuNDJgTyMCVRP+k18v27IM7T1nkQHcSgAU7dMjM3rlzLWD6GomixIQIDAQAB";
		//还原公钥
		RSAPublicKey pubKey = RSAUtils.restorePublicKey(Base64Utils.decode(publicKeyReturn));
		//mi 为加密后的密文 654321 为明文密码 
        String mi = RSAUtils.encryptByPublicKey("gx123456", pubKey);
        System.out.println(mi);
	}
}
