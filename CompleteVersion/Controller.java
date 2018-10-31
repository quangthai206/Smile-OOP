package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller extends Application implements Initializable {
	@FXML
	private Button addButton;
	
	@FXML
	private Button changeButton;
	
	@FXML
	private Button deleteButton;
	
	@FXML
	private Button searchButton;

	@FXML
	private Button speakUS;
	
	@FXML
	private Button speakUK;
	
	@FXML
	private Button translateButton;
	
	@FXML
	private ToggleGroup tool;
	
	@FXML
	private RadioMenuItem EVItem;
	
	@FXML
	private RadioMenuItem VEItem;
	
	@FXML
	private TextField input;
	
	@FXML
	private ListView<String> listView;
	
	@FXML
	private TextArea define;
	
	@FXML
	private Label engWord;
	
	@FXML
	private MenuItem closeItem;
	
	@FXML
	private MenuItem saveItem;
	
	@FXML
	private MenuItem exportItem;
	
	@FXML
	private MenuItem aboutItem;
	
	@FXML
	private ImageView speakUSIV;
	
	@FXML
	private ImageView speakUKIV;
	
	@FXML
	private ImageView translateIV;
	
	private static DictionaryManagement dm = new DictionaryManagement();
	
	public static boolean isChanged = false;
	
	private VoiceManager vm;

	private Voice voice;

	private ObservableList<String> list;
	
	private AddController addWindow = new AddController();
	private ModifyController modifyWindow = new ModifyController();
	private DeleteController deleteWindow = new DeleteController();
	private TranslateController translate = new TranslateController();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list = FXCollections.observableArrayList();
		try {
			dm.insertFromFile();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		for(String s: dm.getAllWords()) {
			list.add(s);
		}
		listView.setItems(list);
		listView.getSelectionModel().select(0);
		System.setProperty("mbrola.base", "mbrola");
		vm = VoiceManager.getInstance();
		voice = vm.getVoice("kevin16");
		voice.allocate();
		Image image = new Image(getClass().getResourceAsStream("/speak.png"));
		speakUSIV.setImage(image);
		speakUS.setGraphic(speakUSIV);
		speakUKIV.setImage(image);
		speakUK.setGraphic(speakUKIV);
		image = new Image(getClass().getResourceAsStream("/GTranslate.png"));
		translateIV.setImage(image);
		translateButton.setGraphic(translateIV);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> {
			if(isChanged) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Thoát");
				alert.setHeaderText(null);
				alert.setContentText("Bạn có muốn lưu lại những thay đổi trước khi thoát?");
				Optional<ButtonType> action = alert.showAndWait();
				if(action.get() == ButtonType.OK) {
					dm.saveFile();
				}
			}
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Thoát");
				alert.setHeaderText(null);
				alert.setContentText("Bạn có chắc chắn đồng ý muốn thoát?");
				Optional<ButtonType> action = alert.showAndWait();
				if(action.get() == ButtonType.OK) {
					primaryStage.close();
				}
				e.consume();
			}
		});
		primaryStage.setTitle("SMILE DICTIONARY");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public void inputChanged(KeyEvent event) {
			if (event.getCode() == KeyCode.DOWN) {
				listView.requestFocus();
			}
			else {
				String prefix = input.getText().toLowerCase();
				
				if(prefix.length() == 0) {
					list = FXCollections.observableArrayList(dm.getAllWords());
					listView.getItems().clear();
					listView.setItems(list);				
					listView.getSelectionModel().select(0);
				}
				else {
					List<String> words = dm.prefixSeach(prefix);
					list = FXCollections.observableArrayList(words);
					listView.getItems().clear();
					listView.setItems(list);
					listView.getSelectionModel().select(0);
				}
			}
	}
	
	public void clickedListView() {
		define.setText(dm.get(listView.getSelectionModel().getSelectedItem()));
		engWord.setText(listView.getSelectionModel().getSelectedItem());
	}
	
	public void handleEnterPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			String word = listView.getSelectionModel().getSelectedItem();
			if(word != null) {
				define.setText(dm.get(word));
				engWord.setText(word);
			}
			else {
				define.setText("Từ \"" + input.getText() + "\" không có trong dữ liệu từ điển!");
				engWord.setText("");
			}
		}
	}
	
	public void seachClicked() {
		String word = listView.getSelectionModel().getSelectedItem();
		if(word != null) {
			define.setText(dm.get(word));
			engWord.setText(word);
		}
		else {
			define.setText("Từ \"" + input.getText() + "\" không có trong dữ liệu từ điển!" );
			engWord.setText("");
		}
	}
	
	public void male() {
		voice.deallocate();
		voice = vm.getVoice("kevin16");
		voice.allocate();
		voice.speak(engWord.getText());
	}
	
	public void female() {
		voice.deallocate();
		voice = vm.getVoice("mbrola_us1");
		voice.allocate();
		voice.speak(engWord.getText());
	}
	
	public void closeAction() {
		if(isChanged) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Thoát");
			alert.setHeaderText(null);
			alert.setContentText("Bạn có muốn lưu lại những thay đổi trước khi thoát?");
			
			Optional<ButtonType> action = alert.showAndWait();
			if(action.get() == ButtonType.OK) {
				dm.saveFile();
			}
			Platform.exit();
		}
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Thoát");
			alert.setHeaderText(null);
			alert.setContentText("Bạn có chắc chắn đồng ý muốn thoát?");
			
			Optional<ButtonType> action = alert.showAndWait();
			if(action.get() == ButtonType.OK) {
				Platform.exit();
			}
		}
	}
	
	public void saveItemClicked() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText(null);
		alert.setContentText("Lưu dữ liệu thành công!");
		alert.showAndWait();
		if(EVItem.isSelected()) {
			dm.saveFile();
//			System.out.println("E-V");
		}
		else {
			dm.saveFileVE();
//			System.out.println("V-E");
		}
		
	}
	
	public void exportItemClicked() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Thông báo");
		alert.setHeaderText(null);
		alert.setContentText("Xuất dữ liệu thành công!");
		alert.showAndWait();
		dm.exportToFile();
	}
	
	public void openAddWindow() {
		addWindow.run();
	}
	
	public void openModifyWindow() {
		modifyWindow.run();
	}
	
	public void openDeleteWindow() {
		deleteWindow.run();
	}
	
	public void openTranslateWindow() {
		translate.run();
	}
	
	public void openAbout() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("About.fxml"));
			Scene scene = new Scene(root);
			Stage window = new Stage();
			window.initModality(Modality.APPLICATION_MODAL);
			window.setResizable(false);
			window.getIcons().add(new Image("file:images/info.png"));
			window.setTitle("Từ điển");
			window.setScene(scene);
			window.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean contains(String key) {
		return dm.contains(key);
	}
	
	public static void add(String word, String mean) {
		dm.put(word, mean);
	}

	public void EVButton_Clicked() {
		dm.insertFromFile();
		input.setText("");
		engWord.setText("");
		define.setText("");
		speakUK.setDisable(false);
		speakUS.setDisable(false);
		input.setPromptText("Tra từ Anh Việt Anh");
		list = FXCollections.observableArrayList();
		for(String s: dm.getAllWords()) {
			list.add(s);
		}
		listView.setItems(list);
		listView.getSelectionModel().select(0);
	}
	
	public void VEButton_Clicked() {
		dm.insertFromFileVE();
		input.setText("");
		engWord.setText("");
		define.setText("");
		speakUK.setDisable(true);
		speakUS.setDisable(true);
		input.setPromptText("Tra từ Việt Anh");
		list = FXCollections.observableArrayList();
		for(String s: dm.getAllWords()) {
			list.add(s);
		}
		listView.setItems(list);
		listView.getSelectionModel().select(0);
	}

}
