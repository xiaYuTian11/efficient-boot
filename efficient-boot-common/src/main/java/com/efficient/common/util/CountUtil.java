package com.efficient.common.util;

/**
 * @author TMW
 * @since 2024/5/22 11:22
 */
public class CountUtil {
    /**
     * 计算一个数占另一个数的百分比
     *
     * @param part     部分值
     * @param whole    整体值
     * @return 计算得到的百分比，保留两位小数
     */
    public static double calculatePercentage(double part, double whole) {
        if (whole == 0) {
            throw new IllegalArgumentException("整体值不能为0");
        }
        return Math.round((part / whole) * 100 * 100.0) / 100.0;
    }

    /**
     * 根据总数和百分比计算具体数值
     *
     * @param total 总数
     * @param percentage 百分比，以小数形式给出
     * @return 根据百分比计算出的具体数值，保留两位小数
     */
    public static double calculateValueFromPercentage(double total, double percentage) {
        if (percentage < 0 || percentage > 1) {
            throw new IllegalArgumentException("百分比必须在0到1之间");
        }
        return Math.round(total * percentage * 100.0) / 100.0;
    }

    public static void main(String[] args) {
        // 示例：计算25是100的百分之多少
        System.out.println("25是100的" + calculatePercentage(25, 100) + "%");

        // 示例：根据100的30%计算具体数值
        System.out.println("100的30%是" + calculateValueFromPercentage(100, 0.3));
    }
}
