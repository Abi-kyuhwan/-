package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class JoinController {

	@FXML
	TextField usernameTextField, useridTextField;
	@FXML
	PasswordField password1PasswordField, password2PasswordField;
	@FXML
	TextField hakTextField, banTextField, bunTextField;
	@FXML
	Button submitButton, cancelButton, closeButton;
	@FXML
	Label messageLabel;

	@FXML
	private void submitButtonOnAction(ActionEvent e) {
		Boolean checkEmpty = ischeckEmpty();
		Boolean checkPasswordSame = ischeckPasswordSame();
		Boolean checkNumber = ischeckNumber();
		Boolean checkIdDouble = ischeckIdDouble();

		if (checkEmpty == true && checkPasswordSame == true && checkNumber == true && checkIdDouble == true) {
			// 디비 연결
			DBConnection dbconn = new DBConnection();
			Connection conn = dbconn.getConnection();
			// sql문 작성(삽입)
			String sql = "insert into user_accounts"
					+ " (idx, user_name, user_id, user_password, user_hak, user_ban, user_bun)" + " values"
					+ " (user_idx_pk.nextval, ?, ?, ?, ?, ?, ?)";

			try {
				PreparedStatement ps = conn.prepareStatement(sql);

				ps.setString(1, usernameTextField.getText());
				ps.setString(2, useridTextField.getText());
				ps.setString(3, password1PasswordField.getText());
				ps.setString(4, hakTextField.getText());
				ps.setString(5, banTextField.getText());
				ps.setString(6, bunTextField.getText());

				ps.executeUpdate();

				ps.close();
				conn.close();

				messageLabel.setText(usernameTextField.getText() + "��! ȸ�������� �Ϸ�Ǿ����ϴ�.");

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			if (!checkEmpty) {
				messageLabel.setText("모든 정보를 입력하세요.");
			} else if (!checkPasswordSame) {
				messageLabel.setText("비밀번호가 일치하지 않습니다.");
			} else if (!checkNumber) {
				messageLabel.setText("학년, 반, 번호를 다시 입력하세요.");
			} else if (!checkIdDouble) {
				messageLabel.setText("사용할 수 없는 아이디입니다.");
			}
		}

	}

	private Boolean ischeckIdDouble() {
		Boolean result = true;
		ResultSet rs;

		DBConnection dbconn = new DBConnection();
		Connection conn = dbconn.getConnection();

		String sql = "select user_id from user_accounts where user_id = '" + useridTextField.getText() + "'";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				result = false;
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
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
	private void cancelButtonOnAction(ActionEvent e) {

	}

	@FXML
	private void closeButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
}
