package com.czw.webapp.controller;

import com.czw.webapp.entity.ImageCode;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@RestController
public class ValidateCodeController {
    public final static String SESSION_KEY_IMAGE_CODE = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = createImageCode();
        //设置request的属性 属性名为"SESSION_KEY_IMAGE_CODE"，属性值为imageCode
        //sessionStrategy是SpringFrameworkSocial的类，可以设置缓存
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_IMAGE_CODE, imageCode);
        //将验证码串以JPEG的形式按字符流返回
        ImageIO.write(imageCode.getImage(), "jpeg", response.getOutputStream());
    }

    /**
     * 生成一个验证码类
     * @return
     */
    private ImageCode createImageCode() {
        int width = 100;//验证码图片宽度
        int height = 36;//验证码图片高度
        int length = 4;//验证码位数
        int expireIn = 60;//验证码有效时间
//         BufferedImage是image的一个实现类
//        使用构造函数创建image对象
//         指定宽高、图像字节灰度
//        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
//        创建一个不带透明色的对象
//        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        创建一个带透明色的对象
//        new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        生成一个画板
        Graphics g = image.getGraphics();
        Random random = new Random();
//        用随机颜色填充
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
//        随机在画板上画155条线
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }
//      创建4个数字在画板上
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }
//      释放画板的资源
        g.dispose();
        return new ImageCode(image, sRand.toString(), expireIn);
    }

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
