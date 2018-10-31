package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.darkprograms.speech.translator.GoogleTranslate;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TranslateController implements Initializable  {
	
	@FXML
	private TextArea input;
	
	@FXML
	private TextArea output;
	
	@FXML
	private Button swapButton;
	
	@FXML
	private Button translateButton;
	
	@FXML
	private Button speakButton;
	
	@FXML
	private Label label1;
	
	@FXML
	private Label label2;
	
	@FXML
	private ImageView swapIV;
	
	private String word;
	private String mean;
	private String[] arr;
	
	private VoiceManager vm;

	private Voice voice;
	
	private boolean check = true; 
	
	public void run() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("TranslateView.fxml"));
			Scene scene = new Scene(root);
			Stage window = new Stage();
			window.initModality(Modality.APPLICATION_MODAL);
			window.getIcons().add(new Image("/GTranslate.png"));
			window.setTitle("Dịch văn bản");
			window.setScene(scene);
			window.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void translateButtonClicked() {
		try {
			mean = "";
			word = input.getText();
			arr = word.split("\\?|\\.");
			if(check) {
				for(String s: arr) {
					mean += GoogleTranslate.translate("en", "vi", s) + ". ";
				}
			}
			else {
				for(String s: arr) {
					mean += GoogleTranslate.translate("vi", "en", s) + ". ";
				}
			}
			output.setText(mean);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Cảnh báo");
			alert.setHeaderText(null);
			alert.setContentText("Vui lòng kiểm tra lại kết nối internet!");
			alert.showAndWait();
//			e.printStackTrace();
		}
		
	}
	
	public void swapButtonClicked() {
		if(check) {
			check = false;
			label1.setText("Tiếng Việt");
			label2.setText("Tiếng Anh");
		}
		else {
			check = true;
			label1.setText("Tiếng Anh");
			label2.setText("Tiếng Việt");
		}
		input.setText("");
		output.setText("");
	}
	
	public void speakButtonClicked() {
		vm = VoiceManager.getInstance();
		voice = vm.getVoice("kevin16");
		voice.allocate();
		voice.speak(input.getText());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Image image = new Image(getClass().getResourceAsStream("/swap.png"));
		swapIV.setImage(image);
	}
	
}
