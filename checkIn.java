	import java.sql.*;

	public static Connection connect() throws SQLException{
		DriverManager.registerDriver(new org.postgresql.Driver());
		Connection conn = DriverManager.getConnection(
				"jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421",
				"cs421g45","Comp421g45"
				);
		return conn;	
	}


	public static String checkIn(String personal_id, String today) throws SQLException{
		//Date today = Date.valueOf(inputToday);
		int room_number = 0;
		String guest_id = "";
		// get room number
		String sql1 = "SELECT guest_id FROM guest WHERE personal_id = '" + personal_id + "'";
		Connection conn = connect();
		Statement stmt = conn.createStatement();
		ResultSet guestId = stmt.executeQuery(sql1);
		if(guestId.next()){
			guest_id = guestId.getString("guest_id");
			String sql2 = "SELECT room_number, arrival_date FROM reservation WHERE guest_id = '" + guest_id + "'";
			ResultSet roomNum = stmt.executeQuery(sql2);
			while(roomNum.next()){
				Date date = new Date(roomNum.getTimestamp("arrival_date").getTime());
				if(date.toString().compareTo(today) == 0)
					room_number = roomNum.getInt("room_number");
			}
			roomNum.close();
		}
		guestId.close();
		if(room_number == 0 || guest_id.compareTo("") == 0)
			return "Guest doesn't have any reservation.";
		
		// set guestId in guest room
		String sql3 = "UPDATE guestroom SET guest_id = '"+ guest_id + "' WHERE room_number = '" + room_number + "'";
		stmt.executeUpdate(sql3);
		
		stmt.close();
		conn.close();
		return String.valueOf(room_number);		
	}
