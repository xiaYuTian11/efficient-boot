package com.sjr.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author TMW
 * @since 2022/6/13 16:35
 */
public class AsposeLicense {

    public static final String LICENSE_STR = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<License>\n" +
            "    <Data>\n" +
            "        <Products>\n" +
            "            <Product>Aspose.Total for Java</Product>\n" +
            "            <Product>Aspose.Words for Java</Product>\n" +
            "        </Products>\n" +
            "        <EditionType>Enterprise</EditionType>\n" +
            "        <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
            "        <LicenseExpiry>20991231</LicenseExpiry>\n" +
            "        <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
            "    </Data>\n" +
            "    <Signature>\n" +
            "        sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=\n" +
            "    </Signature>\n" +
            "</License>\n";

    public static InputStream getInputStream() {
        return new ByteArrayInputStream(LICENSE_STR.getBytes(StandardCharsets.UTF_8));
    }
}
