package org.example;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageAPITest {

    // Тест на успешную инициализацию OpenCV
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

    // Тест на применение операторов Собеля и Лапласа
    @Test
    public void testSobelAndLaplaceOperators() {
        String inputPath = "Images/testSobelAndLaplaceOperators/image.jpg";
        String outputDir = "Images/testSobelAndLaplaceOperators/";

        Mat srcImage = Imgcodecs.imread(inputPath, Imgcodecs.IMREAD_COLOR);
        Mat grayImage = new Mat();
        Imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Применение оператора Собеля
        Mat sobelX = new Mat();
        Mat sobelY = new Mat();
        Imgproc.Sobel(grayImage, sobelX, CvType.CV_32F, 1, 0);
        Imgproc.Sobel(grayImage, sobelY, CvType.CV_32F, 0, 1);
        Core.convertScaleAbs(sobelX, sobelX); // Конвертация в CV_8U
        Core.convertScaleAbs(sobelY, sobelY); // Конвертация в CV_8U
        Imgcodecs.imwrite(outputDir + "sobelX.jpg", sobelX);
        Imgcodecs.imwrite(outputDir + "sobelY.jpg", sobelY);

        // Применение оператора Лапласа
        Mat laplace = new Mat();
        Imgproc.Laplacian(grayImage, laplace, CvType.CV_32F);
        Mat absLaplace = new Mat();
        Core.convertScaleAbs(laplace, absLaplace);
        Imgcodecs.imwrite(outputDir + "laplace.jpg", absLaplace);
    }

    // Тест на трансформации изображения
    @Test
    public void testImageTransformations() {
        String inputPath = "Images/testImageTransformations/image.jpg";
        String outputDir = "Images/testImageTransformations/";

        Mat srcImage = Imgcodecs.imread(inputPath, Imgcodecs.IMREAD_COLOR);

        // Зеркальные трансформации
        Mat flipV = new Mat();
        Mat flipH = new Mat();
        Mat flipHV = new Mat();
        Core.flip(srcImage, flipV, 0);
        Core.flip(srcImage, flipH, 1);
        Core.flip(srcImage, flipHV, -1);
        Imgcodecs.imwrite(outputDir + "flipV.jpg", flipV);
        Imgcodecs.imwrite(outputDir + "flipH.jpg", flipH);
        Imgcodecs.imwrite(outputDir + "flipHV.jpg", flipHV);

        // Повторение изображения
        Mat repeated = new Mat();
        Core.repeat(srcImage, 2, 2, repeated);
        Imgcodecs.imwrite(outputDir + "repeated.jpg", repeated);

        // Изменение размера изображения
        Mat resized = new Mat();
        Imgproc.resize(srcImage, resized, new Size(100, 100));
        Imgcodecs.imwrite(outputDir + "resized.jpg", resized);

        // Конкатенация изображений
        Mat hConcat = new Mat();
        Mat vConcat = new Mat();
        Core.hconcat(List.of(srcImage, srcImage), hConcat);
        Core.vconcat(List.of(srcImage, srcImage), vConcat);
        Imgcodecs.imwrite(outputDir + "hConcat.jpg", hConcat);
        Imgcodecs.imwrite(outputDir + "vConcat.jpg", vConcat);
    }

    // Тест на вращение изображения
    @Test
    public void testImageRotation() {
        String inputPath = "Images/testImageRotation/image.jpg";
        String outputDir = "Images/testImageRotation/";

        Mat srcImage = Imgcodecs.imread(inputPath, Imgcodecs.IMREAD_COLOR);
        Point center = new Point(srcImage.width() / 2.0, srcImage.height() / 2.0);

        // Вращение с обрезкой
        Mat rotationMat = Imgproc.getRotationMatrix2D(center, 45, 1);
        Mat rotated = new Mat();
        Imgproc.warpAffine(srcImage, rotated, rotationMat, srcImage.size());
        Imgcodecs.imwrite(outputDir + "rotated_cropped.jpg", rotated);

        // Вращение без обрезки
        Size newSize = new Size(srcImage.width() * 1.5, srcImage.height() * 1.5);
        Imgproc.warpAffine(srcImage, rotated, rotationMat, newSize, Imgproc.INTER_LINEAR, Core.BORDER_CONSTANT, new Scalar(0, 0, 0));
        Imgcodecs.imwrite(outputDir + "rotated_full.jpg", rotated);
    }

    // Тест на сдвиг изображения
    @Test
    public void testImageShift() {
        String inputPath = "Images/testImageShift/image.jpg";
        String outputDir = "Images/testImageShift/";

        Mat srcImage = Imgcodecs.imread(inputPath, Imgcodecs.IMREAD_COLOR);

        // Сдвиг изображения
        Mat shifted = new Mat();
        Mat shiftMat = Mat.eye(2, 3, CvType.CV_32F);
        shiftMat.put(0, 2, 50); // Сдвиг по x на 50
        shiftMat.put(1, 2, 30); // Сдвиг по y на 30
        Imgproc.warpAffine(srcImage, shifted, shiftMat, srcImage.size());
        Imgcodecs.imwrite(outputDir + "shifted.jpg", shifted);
    }

    // Тест на перспективное преобразование
    @Test
    public void testPerspectiveTransformation() {
        String inputPath = "Images/testPerspectiveTransformation/image.jpg";
        String outputDir = "Images/testPerspectiveTransformation/";

        Mat srcImage = Imgcodecs.imread(inputPath, Imgcodecs.IMREAD_COLOR);

        // Перспективное преобразование
        Mat dst = new Mat();
        Mat srcPoints = new Mat(4, 1, CvType.CV_32FC2);
        Mat dstPoints = new Mat(4, 1, CvType.CV_32FC2);

        srcPoints.put(0, 0, 0, 0, srcImage.cols(), 0, srcImage.cols(), srcImage.rows(), 0, srcImage.rows());
        dstPoints.put(0, 0, 50, 50, srcImage.cols() - 50, 50, srcImage.cols() - 50, srcImage.rows() - 50, 50, srcImage.rows() - 50);

        Mat perspectiveMat = Imgproc.getPerspectiveTransform(srcPoints, dstPoints);
        Imgproc.warpPerspective(srcImage, dst, perspectiveMat, srcImage.size());
        Imgcodecs.imwrite(outputDir + "perspective.jpg", dst);
    }
}
