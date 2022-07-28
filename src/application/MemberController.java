package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MemberController {

	@FXML
	TextField usernameTextField, useridTextField;
	@FXML
	PasswordField password1PasswordField, password2PasswordField;
	@FXML
	TextField hakTextField, banTextField, bunTextField;
	@FXML
	Button updateButton, deleteButton, readlistButton, closeButton;
	@FXML
	Label messageLabel;

	@FXML
	TableView<Member> membershipTableView;
	@FXML
	TableColumn<Member, String> usernameTableColumn;
	@FXML
	TableColumn<Member, String> useridTableColumn;
	@FXML
	TableColumn<Member, String> password1TableColumn;
	@FXML
	TableColumn<Member, String> hakTableColumn;
	@FXML
	TableColumn<Member, String> banTableColumn;
	@FXML
	TableColumn<Member, String> bunTableColumn;

	@FXML
	private void membershipTableViewOnAction(MouseEvent e) {

		if (membershipTableView.getSelectionModel().getSelectedItem() != null) {
			usernameTextField.setText(membershipTableView.getSelectionModel().getSelectedItem().getName());
			useridTextField.setText(membershipTableView.getSelectionModel().getSelectedItem().getId());
			password1PasswordField.setText(membershipTableView.getSelectionModel().getSelectedItem().getPassword());
			password2PasswordField.setText(membershipTableView.getSelectionModel().getSelectedItem().getPassword());
			hakTextField.setText(membershipTableView.getSelectionModel().getSelectedItem().getHak());
			banTextField.setText(membershipTableView.getSelectionModel().getSelectedItem().getBan());
			bunTextField.setText(membershipTableView.getSelectionModel().getSelectedItem().getBun());
		}
	}

	@FXML
	private void updateButtonOnAction(ActionEvent e) {
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
	private void deleteButtonOnAction(ActionEvent e) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("사용자-회원정보 삭제 모듈");
		alert.setHeaderText("회원정보가 삭제되었습니다.");
		alert.setContentText(usernameTextField.getText() + "님의 회원정보를 삭제하시겠습니까?");
		Optional<ButtonType> alertrs = alert.showAndWait();

		if (alertrs.get() == ButtonType.OK) {
			// 디비 접속
			DBConnection dbconn = new DBConnection();
			Connection conn = dbconn.getConnection();
			// sql 쿼리문 실행
			String sql = "delete from user_accounts where user_id = ?";

			try {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, useridTextField.getText());
				ps.execute();

				ps.close();
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@FXML
	private void readlistButtonOnAction(ActionEvent e) {
		usernameTableColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("Name"));
		useridTableColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("Id"));
		password1TableColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("Password"));
		hakTableColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("Hak"));
		banTableColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("Ban"));
		bunTableColumn.setCellValueFactory(new PropertyValueFactory<Member, String>("Bun"));

		messageLabel.setText("각 칼람의 값을 멤버 변수와 연결 설정 완료함");

		DBConnection dbconn = new DBConnection();
		Connection conn = dbconn.getConnection();

		String sql = "select * from user_accounts order by idx";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			ObservableList<Member> userlist = FXCollections.observableArrayList();

			while (rs.next()) {
				userlist.add(
						new Member(rs.getString("user_name"), rs.getString("user_id"), rs.getString("user_password"),
								rs.getString("user_hak"), rs.getString("user_ban"), rs.getString("user_bun")));
			}

			membershipTableView.setItems(userlist);

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	private void closeButtonOnAction(ActionEvent e) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();
	}
}
