import javax.swing.JFrame;


public class MainClass {

	
	public static void main(String[] args) {

		UserInterface mainForm = new UserInterface();
		
		mainForm.setTitle("Encode/Decode Program");
		mainForm.setSize(700, 500);
		mainForm.setLocationRelativeTo(null);
		mainForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainForm.setVisible(true);
	}

}
