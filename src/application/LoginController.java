package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	Boolean UserLogin = false;
	Boolean AdminLogin = false;

//	체크박스 : adminCheckBox  여기 명칭 잘못된 사람-성민이 자리 확인해야 함
//	사용자 아이디 : useridTextField
//	사용자 암호 : passwordPasswordField
//	로그인 버튼 : loginButton
//	회원가입 버튼 : joinButton
//	취소 버튼 : cancelButton
//	창닫기 버튼 : closeButton
//	확인용메세지 레이블 : messageLabel

	@FXML
	CheckBox adminCheckBox;
	@FXML
	TextField useridTextField;
	@FXML
	PasswordField passwordPasswordField;
	@FXML
	Button loginButton, joinButton, cancelButton, closeButton;
	@FXML
	Label messageLabel;

	@FXML
	private void adminCheckBoxOnAction(ActionEvent e) {
		if (adminCheckBox.isSelected() == true) {
			joinButton.setText("회원관리");
			joinButton.setDisable(true);
		} else {
			joinButton.setText("회원가입");
			joinButton.setDisable(false);
		}
	}

	@FXML
	private void loginButtonOnAction(ActionEvent e) {
//		로그인 버튼이면
//		아이디랑 비번이 빈간이 아니면
//			체크박스에 체크가 되어 있으면(관리자용)
//				관리자 로그인 성공이면
//				
//				관리자 로그인 실패이면
//					메세지 출력(아이디, 비번이 틀림)
//			체크박스에 체크가 안되어 있으면(사용자용)
//				사용자 로그인 실행
//				
//		아이디랑 비번이 빈칸이면
//			메세지 출력(아이디랑 비번을 모두 입력하세요)
//			
//	로그아웃 버튼이면
		if (loginButton.getText().equals("로그인")) {
			if (useridTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false) {
				if (adminCheckBox.isSelected() == true) {
					ValidAdminLogin();
				} else {
					ValidUserLogin();
				}
			} else {
				messageLabel.setText("아이디랑 비번을 모두 입력하세요.");
			}
		} else {
			Logout();
		}

	}

	private void ValidUserLogin() {
		if (isUserLogin() == true) {
			loginButton.setText("로그아웃");
			joinButton.setText("회원정보수정");
		} else {
			messageLabel.setText("아이디, 비번이 틀림");
		}
	}

	private void ValidAdminLogin() {
		if (isAdminLogin() == true) {
			joinButton.setDisable(false);
			loginButton.setText("로그아웃");
		} else {
			messageLabel.setText("아이디, 비번이 틀림");
		}
	}

	private void Logout() {
		loginButton.setText("로그인");
		adminCheckBox.setSelected(false);
		useridTextField.setText("");
		passwordPasswordField.setText("");
		joinButton.setText("회원가입");
		joinButton.setDisable(false);
		
		Main.Global_Userid=null;

		AdminLogin=false;
		UserLogin=false;
		
		messageLabel.setText("로그아웃 되었습니다");
	}

	public Boolean isAdminLogin() {
		DBConnection dbConn = new DBConnection();
		Connection conn = dbConn.getConnection();

		String sql = "select admin_id, admin_password" + " from admin_accounts" + " where admin_id=?"
				+ " and admin_password=?";

		Boolean result = false;

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs;

			ps.setString(1, useridTextField.getText());
			ps.setString(2, passwordPasswordField.getText());

			ps.executeUpdate();
			rs = ps.executeQuery();

			// rs값이 있으면 메시창에 로그인 성공 이라고 출력
			while (rs.next()) {
				result = true;
				AdminLogin = true;
			}

			if (result) {
				messageLabel.setText("관리자 로그인 성공함");
			} else {
				messageLabel.setText("관리자 로그인 실패함");
			}

			ps.close();
			rs.close();
			conn.close();

		} catch (Exception e1) {

		}
		return result;
	}

	public Boolean isUserLogin() {
		DBConnection dbConn = new DBConnection();
		Connection conn = dbConn.getConnection();

		String sql = "select user_id, user_password" + " from user_accounts" + " where user_id=?"
				+ " and user_password=?";

		Boolean result = false;

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs;

			ps.setString(1, useridTextField.getText());
			ps.setString(2, passwordPasswordField.getText());

			ps.executeUpdate();
			rs = ps.executeQuery();

			// rs값이 있으면 메시창에 로그인 성공 이라고 출력
			while (rs.next()) {
				result = true;
				Main.Global_Userid = useridTextField.getText();
				UserLogin = true;
			}

			if (result) {
				messageLabel.setText("사용자 로그인 성공함");
			} else {
				messageLabel.setText("사용자 로그인 실패함");
			}

			ps.close();
			rs.close();
			conn.close();

		} catch (Exception e1) {

		}
		return result;
	}

	@FXML
	private void joinButtonOnAction(ActionEvent e) {
		if(AdminLogin==true) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("member.fxml"));
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("회원관리 화면");
				stage.show();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if(UserLogin==true) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("modify.fxml"));
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("회원정보수정 화면");
				stage.show();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("join.fxml"));
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.setTitle("회원가입 화면");
				stage.show();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@FXML
	private void cancelButtonOnAction(ActionEvent e) {
		useridTextField.setText("");
		passwordPasswordField.setText("");
		adminCheckBox.setSelected(false);
		joinButton.setDisable(false);
		if(AdminLogin==true) {
			joinButton.setText("회원관리");
		} else if(UserLogin==true) {
			joinButton.setText("회원정보수정");
		} else {
			joinButton.setText("회원가입");
		}
	}

	@FXML
	private void closeButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}

}
