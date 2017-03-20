import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;

public class test {
	public static void main(String args[]) throws SQLException{
		//Class.forName("com.ibm.db2.jcc.")
		//Date today = Date.valueOf("2017-02-07");
		//System.out.println(checkIn("W2138-7750741-38", today));
		//cancelReservation("00000002");
		Date originalDep = Date.valueOf("2017-02-06");
		System.out.println(extendDate(111, originalDep, 2));
		
	}
	
	
	public static Connection connect() throws SQLException{
		DriverManager.registerDriver(new org.postgresql.Driver());
		Connection conn = DriverManager.getConnection(
				"jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421",
				"cs421g45","Comp421g45"
				);
		return conn;	
	}
	
	//guest check-in (check room calendar)
	public static String checkIn(String personal_id, Date today) throws SQLException{
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
				if(date.toString().compareTo(today.toString()) == 0)
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
	
	public static String cancelReservation(String reservationNum) throws SQLException{
		// delete info in calendar first then reservation
		String result = "Success";
		Connection conn = connect();
		Statement stmt = conn.createStatement();
		String reservationCheckSql = "SELECT reservation_number FROM reservation WHERE reservation_number = '" + reservationNum + "'";
		ResultSet rs = stmt.executeQuery(reservationCheckSql);
		if(!rs.next())
			return "reservation number doesn't exist!";
		rs.close();
			
		String calendarSql = "UPDATE calendar SET reservation_number = NULL WHERE reservation_number = '" + reservationNum + "'";
		stmt.executeUpdate(calendarSql);
		String reservationSql = "DELETE FROM reservation WHERE reservation_number = '" + reservationNum + "'";
		stmt.executeUpdate(reservationSql);
		stmt.close();
		conn.close();
		return result;
	}
	
	public static String extendDate(int roomNum, Date leave, int duration) throws SQLException{
		boolean Yes = false;
		//String guest_id = "";
		String myReservation = "";
		String otherReservation = "";
		//String type = "";
		Date next = null;
		Connection conn = connect();
		Statement stmt = conn.createStatement();
		
		String reservationSql = "SELECT reservation_number FROM reservation WHERE room_number="+roomNum+" AND depature_date::date = date '"+leave.toString()+"'";
		
		ResultSet reservationNum = stmt.executeQuery(reservationSql);
		if(reservationNum.next())
			myReservation = reservationNum.getString("reservation_number");
		reservationNum.close();
		
		if(myReservation == "")
			return "Invalid Input.";
		
		//System.out.println(myReservation);
		LocalDate start = leave.toLocalDate();
		
		for(int i=0; i<duration; i++){
			start = start.plusDays(1);
			next = Date.valueOf(start);
			//System.out.println("next date " + next);
			
			String calendarSql = "SELECT reservation_number FROM calendar WHERE room_number = " + roomNum + "AND date::date = date '" + next.toString() + "'";
			ResultSet otherReservationNum = stmt.executeQuery(calendarSql);
			if(otherReservationNum.next())
				otherReservation = otherReservationNum.getString("reservation_number");
			//System.out.println("other reservation is " + otherReservation);
			
			if(otherReservation != null && otherReservation != ""){
				String timeSql = "SELECT arrival_date, depature_date FROM reservation WHERE reservation_number = '"+otherReservation+"'";
				ResultSet timeResult = stmt.executeQuery(timeSql);
				if(timeResult.next()){
					//int otherRoomNum = timeResult.getInt("room_number");
					Date arr = new Date(timeResult.getTimestamp("arrival_date").getTime());
					Date dep = new Date(timeResult.getTimestamp("depature_date").getTime());
					
					//Yes = changeRoom(roomNum, arr, dep);
					if(changeRoom(roomNum, arr, dep).compareTo("No available room!") == 0) 
						Yes = false;
					else{
						Yes = true;
						String calendarSql2 = "UPDATE calendar SET reservation_number = '"+myReservation+"' WHERE room_number = " + roomNum + "AND date::date = date '" + next.toString() + "'";
						stmt.executeUpdate(calendarSql2);
					}
					
				}
				timeResult.close();
			}
			else{
				//System.out.println("other reservation is null");
				String calendarSql2 = "UPDATE calendar SET reservation_number = '"+myReservation+"' WHERE room_number = " + roomNum + "AND date::date = date '" + next.toString() + "'";
				stmt.executeUpdate(calendarSql2);
				Yes = true;
			}
			otherReservationNum.close();	
		}
	
		//System.out.println("new dep date " + next);
		if(Yes){
			Timestamp newDep = new Timestamp(next.getTime());
			String reservationSql2 = "UPDATE reservation SET depature_date = '" + newDep + "' WHERE reservation_number = '"+ myReservation + "'";
			stmt.executeUpdate(reservationSql2);
		}
		
		stmt.close();
		return String.valueOf(Yes);
	}
	
	
	//UPDATE reservation SET depature_date = '2017-02-08 00:00:00.000000' WHERE reservation_number = '00000079';

	//UPDATE calendar SET reservation_number = null WHERE room_number = 208 AND date::date > date '2017-02-08';
	
	/*DriverManager.registerDriver(new org.postgresql.Driver());
	Connection conn = DriverManager.getConnection(
			"jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421",
			"cs421g45","Comp421g45"
			);
	Statement stmt = conn.createStatement();
	
	String sqlString = "INSERT INTO cs421g45.guest VALUES ('15', 'auggi2', 'cat2', '514-560-9226', 'auggi@hello2', FALSE , 'jelkj22222')" ;
	stmt.executeUpdate(sqlString);
	System.out.println("connected");
	
	
	
	conn.close();*/

	//download postgresql Driver then add to eclipse library
	
	
	//check room calendar to find empty room with certain type
		public static String check_calendar_op1(Date start_date , Date end_date , String room_type, String old_room_number) throws SQLException{
			//build connect and execute query
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			String empty_room_num = "";
			//find all dates the guest need to stay
			LocalDate start = start_date.toLocalDate();
			LocalDate end = end_date.toLocalDate();

			LocalDate stay_date = start;
			ArrayList<Vector<String>> overall_date = new ArrayList<Vector<String>>();
			
			while(stay_date.isBefore(end)){
				Vector<String> rooms_avail_on_certain_date = new Vector<String>();
				Date sql_stay_date = java.sql.Date.valueOf( stay_date );
				String stay_date_string = sql_stay_date.toString();
				stay_date_string += " 00:00:00";
				
				String find_room_query = "SELECT room_number FROM calendar WHERE room_number in (SELECT room_number FROM  guestroom WHERE type = '"+
						room_type+"' )AND date = '"+stay_date_string+"' AND reservation_number ISNULL;" ;
				ResultSet room_result = stmt.executeQuery(find_room_query);
				while(room_result.next()){
					empty_room_num = room_result.getString("room_number");
					if(empty_room_num!=""){
						rooms_avail_on_certain_date.add(empty_room_num);
					}
				}
				overall_date.add(rooms_avail_on_certain_date);	
				
				stay_date = stay_date.plusDays(1);
			}
			
			//now check overall_Date to find intersection of room
		
			
			stmt.close();
			conn.close();
			
			return find_intersection_op1(overall_date, old_room_number);
		}
		
		
		//helper method for check_calendar
		public static String find_intersection_op1(ArrayList<Vector<String>> overall_date, String old_room_number){
			for(int i = 0 ; i<overall_date.get(0).size();i++){
					int days = 0;
					String room = new String(overall_date.get(0).get(i));
					for(int j = 0 ; j < overall_date.size();j++){
						if(overall_date.get(j).contains(room) && room.compareTo(old_room_number)!=0){
							days++;
						}
					}
					if(days == overall_date.size()){
						return room;
					}
					
				}
			return "no available room";
		}
			
			public static String find_intersection(ArrayList<Vector<String>> overall_date){
				for(int i = 0 ; i<overall_date.get(0).size();i++){
						int days = 0;
						String room = new String(overall_date.get(0).get(i));
						for(int j = 0 ; j < overall_date.size();j++){
							if(overall_date.get(j).contains(room))
								days++;
						}
						if(days == overall_date.size())
							return room;	
					}
				return "no available room";
			}

		//roomNo, start, end
		public static String changeRoom(int roomNo, Date start, Date end) throws SQLException{
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			
			String string = "";
		
			String sql = "SELECT * FROM guestroom WHERE room_number =" + roomNo;
			ResultSet rs = stmt.executeQuery(sql);
			//String r_num="";
			String type="";
			while (rs.next()){
				type = rs.getString("type");
			}
			rs.close();
			
			String newRoom = check_calendar_op1(start, end, type,Integer.toString(roomNo));
			//System.out.println(newRoom);
			if(newRoom.compareTo("no available room")==0)
				string= "No available room!";
			else{
			int newRoomNum = Integer.parseInt(newRoom);
			string="New room is "+newRoomNum;
			
			String sql1 = "SELECT * FROM reservation WHERE room_number =" + roomNo;
			ResultSet rs1 = stmt.executeQuery(sql1);
			String r_num="";
			
			while (rs1.next()){
				Date d = new Date(rs1.getTimestamp("depature_date").getTime());
				if(d.toString().compareTo(end.toString())==0)
					r_num = rs1.getString("reservation_number");
			}
			rs1.close();
			
			//System.out.println(r_num);
			//System.out.println(newRoomNum);
			
			//changed
			String sql2 = "UPDATE reservation SET room_number ="+newRoomNum+" WHERE reservation_number = '" + r_num + "'";
			stmt.executeUpdate(sql2);
			
			String sql4 = "UPDATE calendar SET reservation_number = null WHERE reservation_number = '" + r_num+"'";
			stmt.executeUpdate(sql4);
			
			//change
			String sql5 = "SELECT * FROM calendar WHERE room_number =" + newRoomNum;
			ResultSet rs2 = stmt.executeQuery(sql5);
			while (rs2.next()){
				Date d = new Date(rs2.getTimestamp("date").getTime());
				//System.out.println("date is " + d);
				if((d.toString().compareTo(start.toString())>0 && d.toString().compareTo(end.toString())<0) ||d.toString().compareTo(start.toString())==0||d.toString().compareTo(end.toString())==0 ){	
					Statement stmt1 = conn.createStatement();
					String sql6 = "UPDATE calendar SET reservation_number = '"+r_num+"' WHERE date::date = date '"+d.toString()+"' AND room_number ="+newRoomNum;
					stmt1.executeUpdate(sql6);	
				}		
			}	
			}
			return string;	
		}
		

}
