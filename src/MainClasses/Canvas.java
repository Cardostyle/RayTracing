package MainClasses;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Canvas {
    private Color[][] picture;
    private int width;
    private int height;
    private String fileName;

    public Canvas(int width, int height){
        picture= new Color[width][height];
        this.width = width;
        this.height = height;
        this.fileName="";
    }

    public Canvas(int width, int height,String fileName){
        picture= new Color[width][height];
        this.width = width;
        this.height = height;
        this.fileName=fileName;
    }

    public Canvas(String fileName){
        readFromFile(fileName);
    }

    public Color[][] getPicture() {
        return picture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPicture(Color[][] picture) {
        this.picture = picture;
        this.height=picture[0].length;
        this.width =picture.length;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPixel(int x, int y, Color color){
        picture[x][y]=color;
    }

    public Color getPixel(int x, int y){
        return picture[x][y];
    }

    public void saveToFile() {
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = getPixel(x, y);
                if (color != null) {  // Überprüfen, ob die Farbe null ist
                    int intRGB = color.toInt();
                    outputImage.setRGB(x, y, intRGB);
                } else {
                    // Setzen Sie die Pixel auf einen Standardwert (Schwarz?)
                    outputImage.setRGB(x, y, 0);
                }
            }
        }
        try {
            File outputfile = new File(fileName);
            ImageIO.write(outputImage, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(String filename) {
        try {
            BufferedImage inputImage = ImageIO.read(new File(filename));
            this.width = inputImage.getWidth();
            this.height = inputImage.getHeight();
            this.picture = new Color[width][height];

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int intRGB = inputImage.getRGB(x, y);
                    Color color = Color.fromInt(intRGB);
                    setPixel(x, y, color);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
