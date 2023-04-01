package edu.ktu.signalrclient;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {
	
	public static Image loadImage(String filepath) {
		try {
			return ImageIO.read(ImageUtils.class.getResource(filepath));
			
		} catch (IOException e) {
			System.out.println("Could not load image from path" + filepath);
		}
		
		return null;
	}
	
	

}
