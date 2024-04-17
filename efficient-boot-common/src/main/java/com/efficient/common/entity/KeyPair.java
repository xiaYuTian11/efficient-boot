package com.efficient.common.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author TMW
 * @since 2024/4/16 16:07
 */
@Data
@Builder
public class KeyPair {
    private String publicKey;
    private String privateKey;
}
