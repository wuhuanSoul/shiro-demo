package cn.com.systec.shiro.chapter5;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Assert;
import org.junit.Test;

import java.security.Key;

/**
 * Created by wh on 10/10/2017.
 */
public class Base64Test {

    @Test
    public void BaseTest() {
        String str = "hello";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString(base64Encoded);
        Assert.assertEquals(str, str2);
    }

    @Test
    public void MD5Test() {
        String str = "hello";
        String salt = "admin";
        //MD5加密
        String md5 = new Md5Hash(str, salt).toString();
        System.out.println(md5);
        //SHA加密
        String sha1 = new Sha256Hash(str, salt).toString();
        System.out.println(sha1);
        //shiro 通用散列支持
        String simpleHash = new SimpleHash("SHA-1", str, salt).toString();
        System.out.println(simpleHash);
    }

    @Test
    public void DefaultHashTest() {
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐
        hashService.setGeneratePublicSalt(true); //是否生成公盐
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator()); //用于生成公盐，默认就这个
        hashService.setHashIterations(1); //生成Hash值得迭代次数
        HashRequest request = new HashRequest.Builder().setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
        System.out.println(hex);
    }

    @Test
    public void RandomNum(){
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        secureRandomNumberGenerator.setSeed("123".getBytes());
        String hex = secureRandomNumberGenerator.nextBytes().toHex();
    }

    @Test
    public void AesTest(){
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);
        Key key = aesCipherService.generateNewKey();
        String text = "hello";
        String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        String text2 = new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());
        Assert.assertEquals(text, text2);
    }

    @Test
    public void MD5HashTest(){
        String algotithmName = "md5";
        String username ="wu";
        String password = "123";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIteration = 2;
        SimpleHash hash = new SimpleHash(algotithmName, password, salt1 + salt2, hashIteration);
        String encodePassword = hash.toHex();
        System.out.println(encodePassword);
    }
}
