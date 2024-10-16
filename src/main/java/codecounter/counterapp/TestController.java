package codecounter.counterapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class TestController {

    @FXML
    private TextField filePathField;

    @FXML
    private TextArea resultArea;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private Button browseButton;

    @FXML
    private Button startButton;

    @FXML
    private Button clearButton;

    private File selectedFile;

    // 点击浏览按钮时打开文件选择对话框
    @FXML
    void onBrowseButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择代码文件");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Java Files", "*.java"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        Stage stage = (Stage) browseButton.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
            fileListView.getItems().add(selectedFile.getName());
        }
    }

    // 点击开始统计按钮时统计代码
    @FXML
    void onStartButtonClick(ActionEvent event) {
        if (selectedFile != null) {
            try {
                String content = new String(Files.readAllBytes(selectedFile.toPath()));
                int lineCount = countLines(content);
                int wordCount = countWords(content);

                resultArea.setText("文件: " + selectedFile.getName() + "\n" +
                        "行数: " + lineCount + "\n" +
                        "单词数: " + wordCount);
            } catch (IOException e) {
                resultArea.setText("无法读取文件内容: " + e.getMessage());
            }
        } else {
            resultArea.setText("请选择一个文件！");
        }
    }

    // 点击清空按钮时清空所有输入和结果
    @FXML
    void onClearButtonClick(ActionEvent event) {
        filePathField.clear();
        resultArea.clear();
        fileListView.getItems().clear();
        selectedFile = null;
    }

    // 统计行数
    private int countLines(String content) {
        return content.split("\r\n|\r|\n").length;
    }

    // 统计单词数
    private int countWords(String content) {
        return content.split("\\s+").length;
    }
}
