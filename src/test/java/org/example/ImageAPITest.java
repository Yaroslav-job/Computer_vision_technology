package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;

public class ImageAPITest {

    @Test
    public void testOpenCVInitialization() {
        try {
            ImageAPI api = new ImageAPI();
            String os = api.getOperatingSystemType().name();
            String version = org.opencv.core.Core.getVersionString();

            System.out.println("OS version - " + os);
            System.out.println("Open CV version - " + version);

            assertNotNull(version, "OpenCV version should not be null");
            assertFalse(version.isEmpty(), "OpenCV version should not be empty");
        } catch (Exception e) {
            fail("OpenCV initialization failed: " + e.getMessage());
        }
    }

    @Test
    public void testImageProcessing() throws Exception {
        ImageAPI api = new ImageAPI();
        String inputPath = "/home/ya/computer_vision_technology/lab2/Images/image1.jpg";
        String outputPath = "/home/ya/computer_vision_technology/lab2/Images/image2.jpg";

        Mat image = api.loadImage(inputPath);
        assertNotNull(image, "Image should be loaded successfully");

        api.showImage(image);
        Thread.sleep(3000); // Задержка 3 секунды

        api.zeroChannel(image, 0); // Set the first channel to 0
        api.showImage(image);
        Thread.sleep(3000); // Задержка 3 секунды

        api.saveImage(outputPath, image);
        Mat savedImage = api.loadImage(outputPath);
        assertNotNull(savedImage, "Saved image should be loaded successfully");
    }
}
