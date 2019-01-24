package application;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBConnection {
	static Connection con = null;

	public static boolean getConnexion(String host, String port, String db, String username, String mdp) {
		String val = "Jdbc:mysql://" + host + ":" + port + "/" + db;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(val, username,"");
			if (con != null) {
				JOptionPane.showConfirmDialog(null, "Serveur has been sucessfully connected. To continue click OK",
						"Connected", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Serveur Error! Check your configuration again please.",
						"Serveur Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Serveur Error! Check your configuration again please.");
			e.printStackTrace();
		}
		return (con != null);
	}

	public static boolean creation() {
		try {
			Statement str = con.createStatement();
			str.executeUpdate("Create Table User(ID INT NOT NULL AUTO_INCREMENT,username varchar(20), "
					+ "fullname varchar(20),mdp varchar(20),Dept_ID INT,PRIMARY KEY(ID));");

			str = con.createStatement();
			str.executeUpdate("ALTER TABLE User AUTO_INCREMENT=1;");

			str = con.createStatement();
			str.executeUpdate(
					"Create Table Doctor(ID INT NOT NULL AUTO_INCREMENT primary key,immat varchar(20),specialite varchar(20),"
							+ "FOREIGN KEY (ID) REFERENCES User(ID));");

			str = con.createStatement();
			str.executeUpdate("Create Table Departement(ID INT NOT NULL AUTO_INCREMENT primary key,nom varchar(20),"
					+ "chef_Dept INT,FOREIGN KEY (chef_Dept) REFERENCES User(ID));");
			
			str = con.createStatement();
			str.executeUpdate("ALTER TABLE Departement AUTO_INCREMENT=1;");

			str = con.createStatement();
			str.executeUpdate("ALTER TABLE User ADD CONSTRAINT fk_dept_id FOREIGN KEY (Dept_ID) REFERENCES Departement(ID);");
			
			str = con.createStatement();
			str.executeUpdate("Create Table Nurse(ID INT NOT NULL AUTO_INCREMENT primary key,specialite varchar(30),"
					+ "FOREIGN KEY (ID) REFERENCES User(ID));");

			str = con.createStatement();
			str.executeUpdate(
					"Create Table Patient(ID INT NOT NULL AUTO_INCREMENT primary key,nom varchar(20),age INT);");
			
			str = con.createStatement();
			str.executeUpdate("ALTER TABLE Patient AUTO_INCREMENT=1;");
			
			str = con.createStatement();
			str.executeUpdate(
					"Create Table MedicalFile(ID INT NOT NULL AUTO_INCREMENT primary key,date date,diagnosis varchar(30),"
							+ "treatment varchar(30),Doct_ID integer,Patient_ID integer,"
							+ "FOREIGN KEY (Doct_ID) REFERENCES Doctor(ID),FOREIGN KEY (Patient_ID) REFERENCES Patient(ID));");
			
			str = con.createStatement();
			str.executeUpdate("ALTER TABLE MedicalFile AUTO_INCREMENT=1;");
			
			str = con.createStatement();
			str.executeUpdate("Create Table Message(Sender_ID INT,Reciever_ID INT,text varchar(50),"
					+ "seen INT,PRIMARY KEY (Sender_ID,Reciever_ID),FOREIGN KEY (Sender_ID) REFERENCES User(ID),"
					+ "FOREIGN KEY (Reciever_ID) REFERENCES User(ID));");
			
			str = con.createStatement();
			str.executeUpdate("insert into Departement (nom) values('Internal Medecine');");
			str = con.createStatement();
			str.executeUpdate("insert into Departement (nom) values('Physiotherapy');");
			str = con.createStatement();
			str.executeUpdate("insert into Departement (nom) values('Dermatology');");
			str = con.createStatement();
			str.executeUpdate("insert into Departement (nom) values('Ear,Nose and Throat');");
			str = con.createStatement();
			str.executeUpdate("insert into Departement (nom) values('Cardiology Surgery');");
			str = con.createStatement();
			str.executeUpdate("insert into Departement (nom) values('Dental');");
			str = con.createStatement();
			str.executeUpdate("insert into Departement (nom) values('Neutrology');");
			str = con.createStatement();
			str.executeUpdate("insert into Departement (nom) values('Orphopedics');");
			str = con.createStatement();
			str.executeUpdate("insert into Departement (nom) values('Orphatomology');");
	
			return true;
		} catch (SQLException e) {
			if (e.getMessage().contains("already exists"))
				return true;
			else{
				try {
					Statement str = con.createStatement();
					str.executeUpdate("Drop Table Message;");
					str = con.createStatement();
					str.executeUpdate("Drop Table Nurse;");
					str = con.createStatement();
					str.executeUpdate("Drop Table MedicalFile;");
					str = con.createStatement();
					str.executeUpdate("Drop Table Patient;");
					str = con.createStatement();
					str.executeUpdate("Drop Table Doctor;");
					str = con.createStatement();
					str.executeUpdate("Drop Table Departement;");
					str = con.createStatement();
					str.executeUpdate("Drop Table User;");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.out.println(e.getMessage());
			}
			return false;
		}
	}

	public static Vector<User> selection() {
		try {
			Statement str = con.createStatement();
			ResultSet rs = str.executeQuery("select * from User;");
			Vector<User> liste = new Vector<User>();
			while (rs.next()) {
				liste.add(new User(rs.getString("username"), rs.getString("fullname"), rs.getString("mdp")));
			}
			return liste;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static int ajout(User e, int dept) {
		try {
			Statement str = con.createStatement();
			str.executeUpdate("insert into User (username,fullname,mdp,Dept_ID) values('" + e.getUsername() + "','"
					+ e.getFullname() + "','" + e.getMdp() + "'," + dept + ");");
			str = con.createStatement();
			ResultSet rs = str.executeQuery("select ID from User where username = '" + e.getUsername() + "';");
			while (rs.next()) {
				return rs.getInt("ID");
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return -1;
	}

	public static void addDoctor(int id, String immat, String speciality) {
		try {
			Statement str = con.createStatement();
			str.executeUpdate(
					"insert into Doctor (ID,immat,specialite) values(" + id + "," + immat + ",'" + speciality + "');");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static String getNotSeenMessages(int id) {
		try {
			String msg = "";
			Statement str = con.createStatement();
			ResultSet rs = str.executeQuery("select Sender_ID from Message where Reciever_ID=" + id + ";");
			while (rs.next()) {
				str = con.createStatement();
				ResultSet rs1 = str.executeQuery("select fullname from User where ID=" + rs.getString("Sender_ID") + ";");
				while(rs1.next()){
					msg=rs1.getString("fullname")+":\n";
				}
			}
			str = con.createStatement();
			rs = str.executeQuery("select text from Message where Reciever_ID=" + id + ";");
			while (rs.next()) {
				msg += rs.getString("text") + '\n';
			}
			return msg;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public static void setSeenMessage(int id) {
		try {
			Statement str = con.createStatement();
			str.executeUpdate("update Message set seen=1 where Reciever_ID=" + id + ";");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendMessage(int sender, int reciever, String text) {
		try {
			Statement str = con.createStatement();
			str.executeUpdate("insert into Message (Sender_ID,Reciever_ID,text,seen) values(" + sender + "," + sender
					+ ",'" + text + "'," + 0 + ");");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void addNurse(int id, String speciality) {
		try {
			Statement str = con.createStatement();
			str.executeUpdate("insert into Nurse (ID,specialite) values(" + id + ",'" + speciality + "');");
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void closeCon(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static User requetePreparee(String nom) {
		try {
			String req = "select * from User where username=? ;";
			PreparedStatement ps = con.prepareStatement(req);
			ps.setString(1, nom);
			ResultSet rs = ps.executeQuery();
			User u = null;
			while (rs.next()) {
				u = new User(rs.getString("username"), rs.getString("fullname"), rs.getString("mdp"));
			}
			return u;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static User adminLogin(String nom) {
		try {
			String req = "select * from User where Dept_ID is null and username=?;";
			PreparedStatement ps = con.prepareStatement(req);
			ps.setString(1, nom);
			ResultSet rs = ps.executeQuery();
			User u = null;
			while (rs.next()) {
				u = new User(rs.getString("username"), rs.getString("fullname"), rs.getString("mdp"));
			}
			return u;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static ObservableList<String> getDoctors() {
		try {
			Statement str = con.createStatement();
			ResultSet rs = str.executeQuery("select * from Doctor;");
			ObservableList<String> doctors = null;
			doctors = FXCollections.observableArrayList("Admin");
			while (rs.next()) {
				doctors.add(rs.getString("nom"));
			}
			return doctors;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static ObservableList<String> getDepartements() {
		try {
			Statement str = con.createStatement();
			ResultSet rs = str.executeQuery("select * from Departement;");
			ObservableList<String> depts = null;
			depts = FXCollections.observableArrayList("Choose a Departement");
			while (rs.next()) {
				depts.add(rs.getString("nom"));
			}
			return depts;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
