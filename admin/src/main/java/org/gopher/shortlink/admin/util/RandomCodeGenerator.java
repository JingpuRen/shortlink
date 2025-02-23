package org.gopher.shortlink.admin.util;
import java.util.Random;

/**
 * gid随机生成器 ： 随机生成六位数的gid
 */
public class RandomCodeGenerator {

    // 定义包含所有可能字符的字符串
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 生成指定长度的随机字符串
     * @param length 随机字符串的长度
     * @return 生成的随机字符串
     */
    public static String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            // 生成一个随机索引
            int index = random.nextInt(CHARACTERS.length());
            // 根据索引从 CHARACTERS 中取出一个字符并添加到 StringBuilder 中
            code.append(CHARACTERS.charAt(index));
        }

        return code.toString();
    }

    /**
     * 生成 6 位随机字符串的便捷方法
     * @return 6 位随机字符串
     */
    public static String generate6DigitRandomCode() {
        return generateRandomCode(6);
    }
}

