package io.github.hzhilong.base.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import io.github.hzhilong.base.error.BusinessException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码工具
 *
 * @author hzhilong
 * @version 1.0
 */
public class QRUtil {
    public static void printQR(String content) throws BusinessException {
        // 设置字符集编码
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置二维码白边的范围(此值可能不生效)
        hints.put(EncodeHintType.MARGIN, 1);
        // 生成二维码矩阵
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 36, 36, hints);
            for (int j = 0; j < bitMatrix.getHeight(); j++) {
                for (int i = 0; i < bitMatrix.getWidth(); i++) {
                    // https://github.com/anotheros/java-qrcode-terminal/blob/master/QRterminal.java
                    if (bitMatrix.get(i, j)) {
                        System.out.print("  ");
                    } else {
                        System.out.print("■");
                    }
                }
                System.out.println();
            }
        } catch (WriterException e) {
            throw new BusinessException("生成二维码失败");
        }
    }

    public static BufferedImage createQRImage(String content) throws BusinessException {
        // 设置字符集编码
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置二维码白边的范围(此值可能不生效)
        hints.put(EncodeHintType.MARGIN, 1);
        // 生成二维码矩阵
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 256, 256, hints);
            BufferedImage bufferedImage = new BufferedImage(256, 256, BufferedImage.TYPE_INT_BGR);
            for (int j = 0; j < bitMatrix.getHeight(); j++) {
                for (int i = 0; i < bitMatrix.getWidth(); i++) {
                    bufferedImage.setRGB(i, j, bitMatrix.get(i, j) ? 0x000000 : 0xFFFFFF);
                }
            }
            return bufferedImage;
        } catch (WriterException e) {
            throw new BusinessException("生成二维码失败");
        }
    }

    public static File writeQRFile(String content, String filePath) throws BusinessException {
        File codeImgFile = new File(filePath);
        try {
            ImageIO.write(createQRImage(content), "png", codeImgFile);
            return codeImgFile;
        } catch (IOException e) {
            throw new BusinessException("保存二维码失败");
        }
    }
}
