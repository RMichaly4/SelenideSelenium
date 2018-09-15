package ui.helper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ImageImpl {

    private WebDriver driver;

    public ImageImpl(WebDriver driver) {
        this.driver = driver;
    }

    private static int[] extractPixels(PixelGrabber pixelGrabberValue) throws InterruptedException {
        if (pixelGrabberValue.grabPixels()) {
            return (int[]) pixelGrabberValue.getPixels();
        }
        return ArrayUtils.EMPTY_INT_ARRAY;
    }


    public static boolean processImage(String filteName, String original) {

        String fileCopy = "src/test/screenshots/CopyOf_" + filteName + ".png";
        String fileOriginal = "src/test/screenshots/ImgList/" + original + ".png";

        Image image1 = Toolkit.getDefaultToolkit().getImage(fileOriginal);
        Image image2 = Toolkit.getDefaultToolkit().getImage(fileCopy);

        try {
            PixelGrabber grab1 = new PixelGrabber(image1, 0, 0, -1, -1, false);
            PixelGrabber grab2 = new PixelGrabber(image2, 0, 0, -1, -1, false);

            int[] data1 = extractPixels(grab1);
            int[] data2 = extractPixels(grab2);

            return java.util.Arrays.equals(data1, data2);

        } catch (InterruptedException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public void makeScreenshot(WebElement element, String name2) throws Exception {
        Thread.sleep(2000);

        String path = "src/test/screenshots/CopyOf_" + name2 + ".png";
        //Get entire page screenshot
        File screenshot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = null;
        try {
            fullImg = ImageIO.read(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Get the location of element on the page
        Point point = element.getLocation();
        //Get width and height of the element
        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();
        //Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        try {
            ImageIO.write(eleScreenshot, "png", screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Copy the element screenshot to disk
        File screenshotLocation = new File(path);
        try {
            FileUtils.copyFile(screenshot, screenshotLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.sleep(4000);
    }

    public static void cleanDirectory(File dir) {
        for (File file : dir.listFiles()) {
            if (file.getName().equals("ImgList")) {
                //do nothing
            } else {
                //delete file
                file.delete();
            }

        }
    }


}