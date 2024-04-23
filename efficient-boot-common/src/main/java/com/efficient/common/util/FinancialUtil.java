package com.efficient.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;

/**
 * 金融工具
 *
 * @author TMW
 * @since 2024/4/16 13:54
 */
@Slf4j
public class FinancialUtil {
    public static DecimalFormat df = new DecimalFormat("#.##");

    /**
     * 复利计算器
     *
     * @param yearlyDeposit      每年存入金额
     * @param annualInterestRate 每年利率
     * @param years              年份
     * @return 总存款金额
     */

    public static double calculateTotalAmount(double yearlyDeposit, double annualInterestRate, int years) {
        double totalAmount = 0;
        double totalDeposit = yearlyDeposit * years;
        for (int i = 1; i <= years; i++) {
            totalAmount = (totalAmount + yearlyDeposit) * (1 + annualInterestRate);
        }
        double totalInterest = totalAmount - totalDeposit;
        log.info("{}年后，累计本金：{}元，累计利息：{}元，总共存款金额：{} 元。", years, df.format(totalDeposit), df.format(totalInterest), df.format(totalAmount));
        return Double.parseDouble(df.format(totalAmount));
    }
}
