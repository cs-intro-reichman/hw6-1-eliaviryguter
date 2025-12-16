import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		//// Replace the following statement with your code.
		for (int i = 0; i< numRows;i++)
		{
			for (int j = 0; j<numCols;j++)
			{
				int R = in.readInt();
				int G = in.readInt();
				int B = in.readInt();
				Color pixel = new Color(R,G,B);
				image[i][j] = pixel;
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
		//// Notice that all you have to so is print every element (i,j) of the array using the print(Color) function.
		for (int i = 0; i< image.length;i++)
		{
			for (int j = 0; j<image[i].length;j++)
			{
				print(image[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {

		Color [][] newimage = new Color[image.length][image[0].length];

		for (int i = 0; i<image.length;i++)
		{
			int indexRow = i;
			int indexCol = 0;
			for (int j = image[i].length-1; j>=0 ; j--)
			{
				newimage[indexRow][indexCol] = image[i][j];
				indexCol++;
			}
		}

		return newimage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){

		Color [][] newimage = new Color[image.length][image[0].length];

		int indexRow = 0;
		for (int i = image.length-1; i>=0;i--)
		{
			for (int j = 0; j<image[0].length ; j++)
			{
				newimage[indexRow][j] = image[i][j];
			}
			indexRow++;
		}

		return newimage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
	 int r = pixel.getRed();
   	 int g = pixel.getGreen();
     int b = pixel.getBlue();

     int lum = (int)(0.299 * r + 0.587 * g + 0.114 * b);

     return new Color(lum, lum, lum);
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color [][] newimage = new Color[image.length][image[0].length];
		for ( int i = 0; i< image.length;i++)
		{
			for (int j = 0; j<image[0].length;j++)
			{
				newimage[i][j]=luminance(image[i][j]);
			}
		}
		return newimage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {

	Color[][] newimage = new Color[height][width];

	 int srcRows = image.length;
   	 int srcCols = image[0].length;

     for (int i = 0; i < height; i++)
	 {
        int srcI = (int)((double) i * srcRows / height);
        for (int j = 0; j < width; j++)
		{
            int srcJ = (int)((double) j * srcCols / width);
            newimage[i][j] = image[srcI][srcJ];
        }
     }

		return newimage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {

		int c1Red = c1.getRed();
		int c2Red = c2.getRed();
		int R = (int)(alpha*c1Red+(1-alpha)*c2Red);

		int c1Green = c1.getGreen();
		int c2Green = c2.getGreen();
		int G = (int)(alpha*c1Green+(1-alpha)*c2Green);

		int c1Blue = c1.getBlue();
		int c2Blue = c2.getBlue();
		int B = (int)(alpha*c1Blue+(1-alpha)*c2Blue);

		Color blendedColor = new Color (R, G, B);

		return blendedColor;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {

		int height = image1.length;
		int width = image1[0].length;

		Color [][] blendImage = new Color[height][width];

		for (int i = 0; i<height; i++)
		{
			for(int j = 0; j<width; j++)
			{
				blendImage[i][j] = blend(image1[i][j],image2[i][j], alpha);
			}
		}

		return blendImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
	
		int height = source.length;
		int width = source[0].length;	

		if ((target.length!=height)||(target[0].length!=width))
		{
			target = scaled(target, width, height);
		}

		for (int i = 0; i<=n;i++)
		{
			double alpha = (double)(n-i)/n;
			Color [][] frame = blend(source, target, alpha);
			Runigram.display(frame);
			StdDraw.pause(500); 
		}
	
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

