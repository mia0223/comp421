import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;

public class CheckOut {
	public static void main(String args[]) throws SQLException{
		//Class.forName("com.ibm.db2.jcc.")
		/*DriverManager.registerDriver(new org.postgresql.Driver());
		Connection conn = DriverManager.getConnection(
				"jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421",
				"cs421g45","Comp421g45"
				);
		Statement stmt = conn.createStatement();
		
		//String sqlString = "INSERT INTO cs421g45.guest VALUES ('15', 'auggi2', 'cat2', '514-560-9226', 'auggi@hello2', FALSE , 'jelkj22222')" ;
		//stmt.executeUpdate(sqlString);
		System.out.println("connected");
		conn.close();*/
		
		//java.sql.Timestamp t = java.sql.Timestamp.valueOf("2017-02-11 17:10:59.598000");
		Date d1 = Date.valueOf("2017-02-04");
		Date d2 = Date.valueOf("2017-02-09");
		//System.out.println(d1);
		System.out.println(checkOut("207","2017-01-31" ));
		//System.out.println(changeRoom(211,"2017-02-03","2017-02-09"));
		//test(d1,d2);
	}
	
	public static Connection connect() throws SQLException{
		DriverManager.registerDriver(new org.postgresql.Driver());
		Connection conn = DriverManager.getConnection(
				"jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421",
				"cs421g45","Comp421g45"
				);
		return conn;
	}
	
	public static String checkOut(String roomNo1, String date) throws SQLException{
		Connection conn = connect();
		Statement stmt = conn.createStatement();
		String string="";
		
		int roomNo = Integer.parseInt(roomNo1);
		//Date date1 = Date.valueOf(date);
		
		String check = "SELECT *  FROM reservation WHERE room_number =" + roomNo+"AND depature_date::date = date '"+ date + "'";
		ResultSet rs = stmt.executeQuery(check);
		int c=0;
		while (rs.next()){
			c++;
		}
		if (c==0)
			string="Invalid combination of room number and departure date!";
		else{
		
		String sql_rnum = "SELECT * FROM reservation WHERE room_number =" + roomNo;
		rs = stmt.executeQuery(sql_rnum);
		String r_num="";
		
		while (rs.next()){
			Date d = new Date(rs.getTimestamp("depature_date").getTime());
			if(d.toString().compareTo(date)==0)
				r_num = rs.getString("reservation_number");
		}
		rs.close();
		
		String sql_bill = "SELECT * FROM bill WHERE reservation_number = '" + r_num + "'"; 
		ResultSet rs1 = stmt.executeQuery(sql_bill);
		int totalAmount = 0;
		String out_date = "";
		int p_num=0;
		int room_num=0;
		int p_amount=0;
		int r_amount=0;
		while (rs1.next()){
			String bill_id = rs1.getString("bill_id");
			int amount = rs1.getInt("amount");
			out_date = rs1.getTimestamp("date").toString();
			totalAmount += amount;
			
			Statement stmt1 = conn.createStatement();
			String sql2 = "SELECT * FROM parking_bill WHERE bill_id = '" + bill_id + "'"; 
			ResultSet rs2 = stmt1.executeQuery(sql2);
			
			while (rs2.next())
				p_num = rs2.getInt("parking_number");
				
			if(p_num>0 && p_amount==0)
				p_amount = amount;
			
			
			Statement stmt2 = conn.createStatement();
			String sql3 = "SELECT * FROM room_bill WHERE bill_id = '" + bill_id + "'"; 
			ResultSet rs3 = stmt2.executeQuery(sql3);
			
			while (rs3.next())
				room_num = rs3.getInt("room_number");

			if(room_num>0 && r_amount==0)
				r_amount = amount;

			rs2.close();
			rs3.close();
			stmt1.close();
			stmt2.close();
		}

		string = "Reservation number: "+r_num+"\n"+"Check-out date: "+out_date+"\n"+"Total amount: "+totalAmount+"\n"
				+"Room bill: "+r_amount+"\n"+"Parking bill: "+p_amount;
		//System.out.println("Reservation number: "+r_num+"\n"+"Check-out date: "+out_date+"\n"+"Total amount: "+totalAmount+"\n"
		//+"Room bill: "+r_amount+"\n"+"Parking bill: "+p_amount);
		
		String sql4 = "UPDATE guestroom SET guest_id = null WHERE room_number = " + roomNo;
		stmt.executeUpdate(sql4);
		
		rs1.close();
		stmt.close();
		conn.close();
		}
		
		return string;
	}

	
	
	
	
}
