
public class ProgramController {

	private static MessageModel messageModel;
	
	public static String encodeMessage(String message, String fileDirectory) {
		
		messageModel = new MessageModel(message, fileDirectory, true);
		
		try {
			if(messageModel.encode()) {
				return "Encoding operation successful!";
			}
			else
				return "Error occurred, please try again!";
		}
		catch(Exception e) {
			return "Error has occurred during encoding: " + e.getMessage();
		}
	}
	
	public static String decodeMessage(String imageFileDirectory, String keyImageFileDirectory) {
		
		messageModel = new MessageModel(imageFileDirectory, keyImageFileDirectory, false);
		
		try {
			if(messageModel.decode()) {
				return messageModel.getMessage();
			}
			else
				return "Unable to decode file, please check that the correct key image was selected for the encoded message file!";
		}
		catch(Exception e) {
			return "Error has occurred during encoding: " + e.getMessage();
		}
	}
}
