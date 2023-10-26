package cn.iocoder.yudao.module.system.util.avatar;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import java.util.UUID;

/**
 * @author hehong
 * @date 2023-10-24
 */
@Slf4j
public class AvatarUtil {


    /**
     * 头像生成
     *
     * @param name 用户名称
     * @return 图片数据
     */
    public static byte[] generateImg(String name) {
        int nameLen = name.length();
        //定义最后在图片上显示的姓名
        String nameWritten;
        if (nameLen <= 2) {
            nameWritten = name;
        } else {
            //如果用户姓名大于三位,截取后两位
            nameWritten = StringUtils.right(name, 2);
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //生成图片
        BufferedImage bi = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setBackground(getRandomColor());
        g2.clearRect(0, 0, 100, 100);
        g2.setPaint(Color.WHITE);

        // 设置字体
        int fontSize = nameWritten.length() >= 2 ? 30 : 50;
        Font font = new Font("微软雅黑", Font.PLAIN, fontSize);
        g2.setFont(font);
        FontMetrics metrics = g2.getFontMetrics(font);
        int x = (100 - metrics.stringWidth(nameWritten)) / 2;
        int y = (100 - metrics.getHeight()) / 2 + metrics.getAscent();
        g2.drawString(nameWritten, x, y);

        //图片做圆角处理
        BufferedImage rounded = makeRoundedCorner(bi, 360);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(rounded, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("图像生成失败", e);
            return null;
        }
    }

    /**
     * 生成随机颜色
     */
    private static Color getRandomColor() {
        String[] beautifulColors = new String[]{
                "2,168,250", // 蓝色背景
        };
        int len = beautifulColors.length;
        Random random = new Random();
        String[] color = beautifulColors[random.nextInt(len)].split(",");
        return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]));
    }

    /**
     * 图片做圆角处理
     */
    private static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }

}
