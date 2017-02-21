package dataGenerate;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Random;

public class BillGenerator {
	public static void main (String[] args){
		/*
		 * room detail
		 * */
		/*
		int roomNumber = 100;
		String roomType = null;
		int bedNumber = 0,price = 0;
		String bedType = null;
		for(int i=0;i<40;i++){
			if (i==20) roomNumber = 200;
			roomNumber ++;
			if(i<10){
				roomType = "Single";
				bedNumber = 1;
				bedType = "Queen";
				price = 100;
			}
			else if(i<20){
				roomType = "Double";
				bedNumber = 1;
				bedType = "King";
				price = 200;
			}
			else if(i<40){
				roomType = "Twin";
				bedNumber = 2;
				bedType = "Single";
				price = 180;
			}
			System.out.println("INSERT INTO guestroom VALUES ("+roomNumber+",'"+roomType+"','"+price+"','" +bedType+"'," +bedNumber+",NULL);");
		}*/
		
		
		/*
		 * calendar
		 * */
		int roomNumber = 0;
		long startDate = Timestamp.valueOf("2017-02-01 00:00:00").getTime();
		long oneday = 1 * 24 * 60 * 60 * 1000;
		for (int i=0;i<10;i++){
			for(int j=0;j<40;j++){
				if(j<20) {
					roomNumber = 101+j;
					}
				else if(j<40){ 
					roomNumber = 181 +j;
					}
				Timestamp startdays = new Timestamp(startDate);
				//System.out.println("INSERT INTO calendar VALUES ("+roomNumber+","+ "NULL" +",'"+ startdays+"');");
				}
				startDate+=oneday;
			}
			
		
		
		
		int reservationId = 10000;
		String[] firstName = {"Emma","Noah","Olivia","Liam","Sophia","Mason","Ava","Jacob",
				  "Isabella","William","Mia","Ethan","Abigail","James","Emily",
				  "Alexander","Charlotte","Michael","Harper","Benjamin","Madison",
				  "Elijah","Amelia","Daniel","Elizabeth","Aiden","Sofia", "Logan",
				  "Evelyn","Matthew","Avery","Lucas","Chloe","Jackson","Ella",
				  "David","Grace","Oliver","Victoria","Jayden","Aubrey","Joseph",
				  "Scarlett","Gabriel","Zoey","Samuel","Addison","Carter","Lily","Anthony",
				"Emma","Noah","Olivia","Liam","Sophia","Mason","Ava","Jacob",
				  "Isabella","William","Mia","Ethan","Abigail","James","Emily",
				  "Alexander","Charlotte","Michael","Harper","Benjamin","Madison",
				  "Elijah","Amelia","Daniel","Elizabeth","Aiden","Sofia", "Logan",
				  "Evelyn","Matthew","Avery","Lucas","Chloe","Jackson","Ella",
				  "David","Grace","Oliver","Victoria","Jayden","Aubrey","Joseph",
				  "Scarlett","Gabriel","Zoey","Samuel","Addison","Carter","Lily","Anthony"};

		String[] lastName = {"McCarthy","Wang","Chen","Lohan","Smith","Kennedy","Washington","Bush",
				"Clinton","Trump","Zhang","Li","White","Wu","Zidane","Watson","Jefferson",
				"Jackson","Lincoln","Grant","Cleveland","Taft","Hoover","Truman","Nixon",
				"Ford","Reagan","Obama","Harding","McKinley"};
		String[] email = {"@gmail.com", "@hotmail.com", "@yahoo.com", "@mcgill.ca", "@outlook.com", "@apple.com"};
		String[] bool = {"TRUE", "FALSE"};
		String[] phoneFirst = {"514","438","611","613","306","403","780","647"};
		String[] phoneSecond = {"578","593","424","864","926","803","546","832","819"};
		String[] phoneThird = {"2588","8196","6327","0613","2016","6075","3296","7809","2017","9259","9257"};
		int[] rooms = {101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,
				201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220};
		String[] payment = {"Debit","Credit","Cash","Cheque"};
		LinkedList idList = new LinkedList();
		LinkedList phonelist = new LinkedList();
		Random rand = new Random();
		int bill = 0;
		String billId = null;
		int amount = 0;
		
		
		
		for (int i = 0; i < 100; i++){
			int n = rand.nextInt(29) + 0;
			int e = rand.nextInt(5) + 0;
			int m = rand.nextInt(2) + 0;
			int res = rand.nextInt(2);
			
			String first = firstName[i];
			String last = lastName[n];
			String contact = email[e];
			String tf = bool[m];
			String id;
			String reservNum;  //reservation number
			if(i<9){
				id = "000000"+(i+1);
				reservNum = "0000000"+(i+1);
			}
			else if(i < 99){
				id = "00000"+(i+1);
				reservNum = "000000"+(i+1);
			}
			else{
				id = "0000"+(i+1);
				reservNum = "00000"+(i+1);
			}
			long offset = Timestamp.valueOf("2017-01-20 00:00:00").getTime();
			long end = Timestamp.valueOf("2017-02-11 00:00:00").getTime();
			long diff = end - offset + 1;
			long randomStart = (long)(Math.random() * diff);
			Timestamp startday = new Timestamp(offset + randomStart);											 //check in date
			Timestamp endday = new Timestamp((long) (offset + randomStart + oneday*rand.nextDouble() * 10.0) );	 //check out date
			String phone;
			do{
				int pf = rand.nextInt(8) + 0;
				int ps = rand.nextInt(9) + 0;
				int pt = rand.nextInt(11) + 0;
				String phoneF = phoneFirst[pf];
				String phoneS = phoneSecond[ps];
				String phoneT = phoneThird[pt];
				phone = phoneF + "-" + phoneS + "-" + phoneT;
			}
			while(phonelist.contains(phone));
			phonelist.add(phone);
			
			/*
			 * personalID
			 * */
			String drivingId;
			do{
				char character = (char)(65 + (int)(Math.random() * ((90 - 65) + 1)));
				drivingId = ""+character + (1000 + (int)(Math.random() * (8999))) + "-" + 
				(1000000 + (int)(Math.random() * (8999999))) + "-" + (10 + (int)(Math.random() * (89))) ;
				//System.out.println(drivingId);
			}while(idList.contains(drivingId));
			idList.add(drivingId);
			
			//System.out.println(phone);
			//System.out.println(startday);
			//System.out.println(endday);
			System.out.println("INSERT INTO GUEST VALUES ( '" + id + "' , '" + first + "' , '" + last + 
					"' , '" + phone + "' , '" + first + i + contact + "' , " + tf +  " , '" + drivingId+"' );");
			
			roomNumber = rand.nextInt(40);
			int pay = rand.nextInt(4);
			System.out.println("INSERT INTO reservation VALUES ('"+
					reservNum+"','"+startday+"','"+endday+"','"+id+"',"+rooms[roomNumber]+","+bool[res]+",'"+payment[pay]+"');");
			if(bill<9){
				billId = "000000"+(bill+1);
			}
			else if(bill < 99){
				billId = "00000"+(bill+1);
			}
			else{
				billId = "0000"+(bill+1);
			}
			if ((rooms[roomNumber]>100) && (rooms[roomNumber] <=110)){
				amount = 100;
			}
			else if ((rooms[roomNumber]>110) && (rooms[roomNumber] <=120)){
				 amount = 200;
			}
			else{
				 amount = 180;
			}
			bill++;
			System.out.println("INSERT INTO bill VALUES('" + billId + "'," + amount + ",'" + endday + "','" + reservNum + "');");
			System.out.println("INSERT INTO room_bill VALUES('" + billId + "','" + rooms[roomNumber] + "');");
			long startTime = Timestamp.valueOf("2017-02-01 00:00:00").getTime();
			Timestamp calendarStart = new Timestamp(startTime);
			long endTime = Timestamp.valueOf("2017-02-11 00:00:00").getTime();
			Timestamp calendarEnd = new Timestamp(endTime);
			if ((startday.after(calendarStart))&&(endday.before(calendarEnd))){
				System.out.println("UPDATE calendar SET reservation_number =" + "'" + reservNum + "' WHERE DATE >='" + startday +
						"' AND DATE <= '"  + endday + "' AND room_number=" + rooms[roomNumber]+";");
			}
			
			if (Math.random() > 0.4){
				if(bill<9){
					billId = "000000"+(bill+1);
				}
				else if(bill < 99){
					billId = "00000"+(bill+1);
				}
				else{
					billId = "0000"+(bill+1);
				}
				amount = rand.nextInt(50);
				bill++;
				System.out.println("INSERT INTO bill VALUES('" + billId + "'," + amount + ",'" + endday + "','" + reservNum + "');");
				System.out.println("INSERT INTO parking_bill VALUES('" + billId + "'," + (rand.nextInt(100)+1)  + ");");
			}
		}
	}
}
