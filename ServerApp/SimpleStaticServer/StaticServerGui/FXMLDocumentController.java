package StaticServerGui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import server.ServerAppRunnable;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextArea directoryInput;
    @FXML
    private TextField portInput;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        String directory = directoryInput.getText().trim();
        String port = portInput.getText().trim();
        int realPort = Integer.parseInt(port);

        ServerAppRunnable websiteServer = new ServerAppRunnable(realPort, directory);
        Thread t = new Thread(websiteServer);
        t.start();
        System.out.println(directory);
        System.out.println(realPort);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
