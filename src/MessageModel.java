import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import javax.imageio.ImageIO;

class MessageModel
{
	private final int Spacing = 6;
	private int size;
	private String Message;
	private String KeyImageLocation;
	private String MessageImageLocation;

	/*This constructor is for MessageModel, and takes in the message to be encoded
	and the location the KeyImage and MessageImage should be sent, if code is true, 
	and if code is false, newDirectory is KeyImageLocation, and newMessage as MessageImageLocation*/
	MessageModel(String newMessage, String newDirectory, boolean code)
	{
		if(code)
		{
		Calendar cal = Calendar.getInstance();
		String fileName = cal.getTime().toString() + "-EncodedMessage";
		fileName = fileName.replace(':', '_');
		Message = newMessage;
		KeyImageLocation = newDirectory + "\\KeyImage-" + fileName + ".png";
		MessageImageLocation = newDirectory + "\\" + fileName + ".png";
		}
		else
		{
			KeyImageLocation = newDirectory;
			MessageImageLocation = newMessage;	
		}
	}

	/*Encode creates the variables needed, then generates a KeyImage and it copies the KeyImage to the MessageImage.
	It moves randomly 1-6 pixels ahead, and adds the integer of the Unicode representation for the next character 
	to the RGB, also as an integer, of the current pixel. It repeats till every character is encoded. 
	After this, it writes the two ImageBuffers to files, throwing an exception if it can't.
	Finally, it runs decode() to ensure the message has been correctly encoded, testing the Message, before and after.
	If they match, it returns true, if not it deletes the files and returns false.*/
	public boolean encode() throws IOException
	{
		BufferedImage KeyImage = generateKeyImage();
		BufferedImage EncodedImage;
		File keyFile = new File (KeyImageLocation);
		File messageFile = new File (MessageImageLocation);
		
		try
		{
		ImageIO.write(KeyImage, "PNG", keyFile);
		EncodedImage = ImageIO.read(new File (KeyImageLocation));
		} catch (IOException i)
		{ 
			System.out.println("File Location Failure");
			return false;
		}
		Random rand = new Random();
		int count = 0;
		
		for (char c : Message.toCharArray())
		{
			count += rand.nextInt(Spacing)+1;
			EncodedImage.setRGB((int)(count/size), (count%size), EncodedImage.getRGB((int)(count/size), (count%size))+(int)c);
		}
				
		try
		{
		ImageIO.write(EncodedImage, "PNG", messageFile);
		} catch (IOException i)
		{ 
			System.out.println("File Location Failure");
			return false;
		}
		String tempMessage = Message;
		
		if(decode() && tempMessage.equals(Message))
			return true;
		else
		{		
			System.out.println("Encoding error. Clearing Images.");
			keyFile.delete();
			messageFile.delete();
			return false;
		}
			
		
	}

	/*Creates the variables required, and loads BufferedImages from the selected files. Throws IOException if it can't.
	Tests the dimensions of the BufferedImages, if they don't match it returns false.
	If the dimensions match, it tests the integer value of each Pixel against the pixel in the same position.
	If the two don't match, it takes the difference and converts this to it's unicode character equivalent.
	After adding each character to Message string, it returns true.*/
	public boolean decode() throws IOException
	{
		BufferedImage KeyImage;
		BufferedImage EncodedImage;
		try{
		KeyImage = ImageIO.read(new File (KeyImageLocation));
		EncodedImage = ImageIO.read(new File (MessageImageLocation));
		} catch (IOException i)
		{ 
			System.out.println("File Location Failure");
			return false;
		}
		Message = "";
		
		if(KeyImage.getHeight() == KeyImage.getWidth() && EncodedImage.getHeight() == EncodedImage.getWidth() && KeyImage.getHeight() == EncodedImage.getWidth())
		{
			size = KeyImage.getHeight();
			for (int i = 0; i < size; i++)
			{
				for(int j = 0; j < size; j++)
				{
					if(KeyImage.getRGB(i,j) != EncodedImage.getRGB(i, j))
					{
						int temp = Math.abs(EncodedImage.getRGB(i, j) - KeyImage.getRGB(i,j));
						Message += (char) temp;
					}
				}
			}
			System.out.println("Message:"+ Message);
			return true;
		}
		else
		{
			System.out.println("File Size Mismatch.");
			return false;
		}
	}
	
	/*Method takes length of Message, and multiplies this times the max number of pixels which could be between each encoded letter.
	This is the square rooted, and rounded up, to be used as the X and Y dimensions of the image.
	Next, it creates a new image, then randomizes each RGB value with a random integer, near the maximum possible value for RGB.
	Finally, it returns the BufferedImage.*/
	private BufferedImage generateKeyImage()
	{
		if(Message.length() < Spacing)
			size = Spacing;
		else
			size = (int)Math.ceil(Math.sqrt((double)(Message.length() * Spacing)));
		BufferedImage key = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		Random rand = new Random();
		
		for (int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				key.setRGB(i, j, rand.nextInt(16777000));
			}
		}
		
		return key;
	
	}
	
	public String getMessage()
	{
		return Message;
	}
}