package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifyController implements Initializable {

	@FXML
	TextField usernameTextField, useridTextField;
	@FXML
	PasswordField password1PasswordField, password2PasswordField;
	@FXML
	TextField hakTextField, banTextField, bunTextField;
	@FXML
	Button modifyButton, clearlButton, closeButton;
	@FXML
	Label messageLabel;

	@FXML
	private void modifyButtonOnAction(ActionEvent e) {
		Boolean checkEmpty = ischeckEmpty();
		Boolean checkPasswordSame = ischeckPasswordSame();
		Boolean checkNumber = ischeckNumber();

		if (checkEmpty == true && checkPasswordSame == true && checkNumber == true) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("사용자-회원정보 수정 모듈");
			alert.setHeaderText("회원정보가 수정되었습니다.");
			alert.setContentText(usernameTextField.getText() + "님의 회원정보를 수정하시겠습니까?");
			Optional<ButtonType> alertrs = alert.showAndWait();

			if (alertrs.get() == ButtonType.OK) {
				DBConnection dbconn = new DBConnection();
				Connection conn = dbconn.getConnection();

				String sql = "Update user_accounts set user_name=?, user_password=?, user_hak=?, user_ban=?, user_bun=? where user_id = ?";

				try {
					PreparedStatement ps = conn.prepareStatement(sql);

					ps.setString(1, usernameTextField.getText());
					ps.setString(2, password1PasswordField.getText());
					ps.setString(3, hakTextField.getText());
					ps.setString(4, banTextField.getText());
					ps.setString(5, bunTextField.getText());
					ps.setString(6, useridTextField.getText());

					ps.executeUpdate();

					ps.close();
					conn.close();
					messageLabel.setText(usernameTextField.getText() + "님의 회원정보가 수정되었습니다.");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else {
			if (!checkEmpty) {
				messageLabel.setText("모든 정보를 입력하세요.");
			} else if (!checkPasswordSame) {
				messageLabel.setText("비밀번호가 일치하지 않습니다.");
			} else if (!checkNumber) {
				messageLabel.setText("학년, 반, 번호를 다시 입력하세요.");
			}
		}
	}

	private Boolean ischeckNumber() {
		Boolean result = false;
		int hak;
		int ban;
		int bun;

		try {
			hak = Integer.parseInt(hakTextField.getText());
			ban = Integer.parseInt(banTextField.getText());
			bun = Integer.parseInt(bunTextField.getText());

			if ((hak <= 3 && hak >= 1) && (ban <= 15 && ban >= 1) && (bun <= 25 && bun >= 1)) {
				result = true;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return result;
	}

	private Boolean ischeckPasswordSame() {
		Boolean result = false;
		if (password1PasswordField.getText().equals(password2PasswordField.getText())) {
			result = true;
		}
		return result;
	}

	private Boolean ischeckEmpty() {
		Boolean result = false;
		if (usernameTextField.getText().isEmpty() == false && useridTextField.getText().isEmpty() == false
				&& password1PasswordField.getText().isEmpty() == false
				&& password2PasswordField.getText().isEmpty() == false && hakTextField.getText().isEmpty() == false
				&& banTextField.getText().isEmpty() == false && bunTextField.getText().isEmpty() == false) {
			result = true;
		}
		return result;
	}

	@FXML
	private void clearButtonOnAction(ActionEvent e) {
		usernameTextField.setText("");
		useridTextField.setText("");
		password1PasswordField.setText("");
		password2PasswordField.setText("");
		hakTextField.setText("");
		banTextField.setText("");
		bunTextField.setText("");
	}

	@FXML
	private void closeButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 디비 접속
		DBConnection dbconn = new DBConnection();
		Connection conn = dbconn.getConnection();
		// sql 쿼리문 실행
		String sql = "select *" + " from user_accounts" + " where user_id = '" + Main.Global_Userid + "'";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				usernameTextField.setText(rs.getString(2));
				useridTextField.setText(rs.getString(3));
				password1PasswordField.setText(rs.getString(4));
				password2PasswordField.setText(rs.getString(4));
				hakTextField.setText(rs.getString(5));
				banTextField.setText(rs.getString(6));
				bunTextField.setText(rs.getString(7));
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
