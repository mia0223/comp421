CREATE TABLE Guest(
 guest_id VARCHAR(20) PRIMARY KEY,
 first_name VARCHAR(20) NOT NULL,
 last_name VARCHAR(20) NOT NULL,
 phone VARCHAR(20),
 email VARCHAR(50) NOT NULL,
 membership BOOLEAN NOT NULL ,
 personal_id VARCHAR(20) NOT NULL
);

CREATE TABLE GuestRoom
(room_number INT PRIMARY KEY,
 type VARCHAR(20) NOT NULL,
 price FLOAT NOT NULL,
 bed_type VARCHAR(20) NOT NULL,
 bed_number INT NOT NULL,
 guest_id VARCHAR(20) REFERENCES Guest
   CONSTRAINT check_number CHECK(room_number>=0 AND price >=0 AND bed_number >0));

 CREATE TABLE Reservation(
   reservation_number INT PRIMARY KEY ,
   arrival_date TIMESTAMP NOT NULL ,
   depature_date TIMESTAMP NOT NULL ,
   guest_id VARCHAR(20) REFERENCES Guest NOT NULL ,
   room_number INT NOT NULL REFERENCES GuestRoom,
   online_reserved BOOLEAN NOT NULL,
   payment_type VARCHAR(20),
   CONSTRAINT valid_data CHECK(reservation_number>0)
 );

 CREATE TABLE Calendar(
   room_number INT,
   reservation_number INT,
   DATE TIMESTAMP,
   PRIMARY KEY (room_number,date),
   FOREIGN KEY (room_number) REFERENCES GuestRoom,
   FOREIGN KEY (reservation_number) REFERENCES Reservation
 );

 CREATE TABLE Parking_lot(
   parking_number INT PRIMARY KEY ,
   size INT NOT NULL ,
   Type VARCHAR NOT NULL ,
   price INT NOT NULL,
   CONSTRAINT data_check CHECK (parking_number>0 AND size>0 AND price>0)
 );

 CREATE TABLE ParksAt(
   parking_number INT,
   guest_id VARCHAR(20) NOT NULL ,
   duration INT,
   PRIMARY KEY (parking_number),
   FOREIGN KEY (guest_id) REFERENCES Guest,
   FOREIGN KEY (parking_number) REFERENCES Parking_lot
 );

 CREATE TABLE Bill(
   Bill_id INT,
   amount INT NOT NULL ,
   DATE TIMESTAMP NOT NULL ,
   reservation_number INT NOT NULL,
   PRIMARY KEY (Bill_id),
   FOREIGN KEY (reservation_number) REFERENCES Reservation
 );

 CREATE TABLE Parking_bill(
   Bill_id INT,
   parking_number INT,
   PRIMARY KEY (Bill_id),
   FOREIGN KEY (Bill_id) REFERENCES Bill,
   FOREIGN KEY (parking_number) REFERENCES Parking_lot
 );

 CREATE TABLE Room_bill(
   Bill_id INT,
   room_number INT,
   PRIMARY KEY (Bill_id),
   FOREIGN KEY (Bill_id) REFERENCES Bill,
   FOREIGN KEY (room_number) REFERENCES GuestRoom
 );