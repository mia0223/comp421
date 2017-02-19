import java.util.Random;
public class Name{
	public static void main(String args[]){
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
		Random rand = new Random();

		//System.out.println(firstName.length);

		/*for(int i=0; i<100; i++){
			int n = rand.nextInt(29) + 0;
			int e = rand.nextInt(5) + 0;
			int m = rand.nextInt(2) + 0;

			String first = firstName[i];
			String last = lastName[n];
			String contact = email[e];
			String tf = bool[m];
			String id;
			if(i<9)
				id = "000000"+(i+1);
			else if(i == 99)
				id = "0000"+(i+1);
			else
				id = "00000"+(i+1);
			System.out.println("INSERT INTO cs421g45.guest VALUES ( '" + id + "' , '" + first + "' , '" + last + "' , '" + "514560123" + i + "' , '" + first + i + contact + "' , " + tf +  " , '" + "abc1234560"+i+"' );");
		}*/

		/*int slot[] = new int[100];

		for(int i=1; i<101; i++)
			slot[i-1] = i;

		for(int i=10001; i<10101; i++){
			int index = rand.nextInt(100) + 0;
			System.out.println("INSERT INTO cs421g45.parking_bill VALUES ( " + i + " , " + slot[index] + " );");
		}*/

		INSERT INTO reservation(reservation_number, reservation_date, guest_id, room_number, online_reserved, payment_type) VALUES (10001, date '2016-12-23'+time '03:00', '0000005', 103, TRUE , 'credit');
		for(int i=0; i<20; i++){

			if(i<10)
				System.out.println("INSERT INTO reservation(reservation_number, reservation_date, guest_id, room_number, online_reserved, payment_type) VALUES (");
			else if(i<15)
				System.out.println("INSERT INTO reservation(reservation_number, reservation_date, guest_id, room_number, online_reserved, payment_type) VALUES (");
			else
				System.out.println("INSERT INTO reservation(reservation_number, reservation_date, guest_id, room_number, online_reserved, payment_type) VALUES (");
		}
	}
}











INSERT INTO reservation(reservation_number, arrival_date, depature_date, reservation_date, guest_id, room_number, online_reserved) 
VALUES (10001, date '2017-01-01'+time '15:00', date '2017-01-03'+time '08:00', date '2016-11-20'+time '08:00', '0000013', 105, TRUE);

INSERT INTO reservation(reservation_number, arrival_date, depature_date, reservation_date, guest_id, room_number, online_reserved) 
VALUES (10002, date '2017-01-08'+time '12:00', date '2017-01-13'+time '09:30', date '2016-12-23'+time '09:30', '0000023', 102, TRUE);

INSERT INTO reservation(reservation_number, arrival_date, depature_date, reservation_date, guest_id, room_number, online_reserved) 
VALUES (10003, date '2017-01-11'+time '16:20', date '2017-01-20'+time '17:00', date '2017-01-11'+time '16:20', '0000067', 101, FALSE);

INSERT INTO reservation(reservation_number, arrival_date, depature_date, reservation_date, guest_id, room_number, online_reserved) 
VALUES (10004, date '2016-01-20'+time '05:20', date '2016-01-25'+time '19:05', date '2016-10-13'+time '09:30', '0000012', 103, TRUE);

INSERT INTO reservation(reservation_number, arrival_date, depature_date, reservation_date, guest_id, room_number, online_reserved) 
VALUES (10005, date '2016-01-09'+time '15:10', date '2016-01-13'+time '14:00', date '2016-12-13'+time '09:30', '0000009', 104, TRUE);

INSERT INTO reservation(reservation_number, arrival_date, depature_date, reservation_date, guest_id, room_number, online_reserved) 
VALUES (10006, date '2016-01-03'+time '11:00', date '2016-01-05'+time '19:00', date '2016-01-03'+time '11:00', '0000099', 106, FALSE);

INSERT INTO reservation(reservation_number, arrival_date, depature_date, reservation_date, guest_id, room_number, online_reserved) 
VALUES (10001, date '2016-01-28'+time '15:00', date '2016-01-31'+time '08:00', date '2016-01-03'+time '09:30', '0000100', 107, TRUE);

