CREATE TABLE User (
	sinNumber CHAR(9) PRIMARY KEY,
	fullName VARCHAR(30) NOT NULL,
	occupation VARCHAR(15),
	address VARCHAR(50),
	dateOfBirth DATE
);

CREATE TABLE Renter (
	sinNumber CHAR(9) PRIMARY KEY,
	paymentInfo VARCHAR(30),
	FOREIGN KEY (sinNumber) REFERENCES User(sinNumber) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Host (
	sinNumber CHAR(9) PRIMARY KEY,
	FOREIGN KEY (sinNumber) REFERENCES User(sinNumber) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Listing (
	listingId INTEGER AUTO_INCREMENT PRIMARY KEY,
	listingType VARCHAR(30),
	latitude REAL CHECK (latitude >= -90 AND latitude <= 90),
	longitude REAL CHECK (longitude >= -180 AND longitude <= 180),
	streetAddress VARCHAR(30) NOT NULL,
	postalCode CHAR(6) NOT NULL,
	city VARCHAR(30) NOT NULL,
	country VARCHAR(30) NOT NULL
);

CREATE TABLE Posting (
	hostSin CHAR(9),
	listingId INTEGER,
	PRIMARY KEY(hostSin, listingId),
	FOREIGN KEY (hostSin) REFERENCES User(sinNumber) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (listingId) REFERENCES Listing(listingId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Booking (	
	listingId INTEGER,
	renterSin CHAR(9),
	startDate DATE,
	endDate DATE NOT NULL,
	PRIMARY KEY(listingId, renterSin, startDate),
	FOREIGN KEY (listingId) REFERENCES Listing(listingId) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (renterSin) REFERENCES User(sinNumber) ON DELETE CASCADE ON UPDATE CASCADE,
	CHECK (startDate <= endDate)
);

CREATE TABLE Rating (
	authorSin CHAR(9),
	listingId INTEGER,
	startDate DATE,
	commentBody TEXT,
	score INTEGER CHECK (score >= 1 AND score <= 5) NOT NULL,
	object VARCHAR(30),
	PRIMARY KEY (authorSin, listingId, startDate, object),
	FOREIGN KEY (authorSin) REFERENCES User(sinNumber) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (listingId) REFERENCES Listing(listingId) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (authorSin, listingId, startDate) REFERENCES Booking(renterSin, listingId, startDate) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Amenity (
	itemId INTEGER PRIMARY KEY
);

CREATE TABLE ProvidedAmenity (
	itemId INTEGER,
	listingId INTEGER,
	price REAL CHECK (price >= 0),
	PRIMARY KEY(itemId, listingId),
	FOREIGN KEY (itemId) REFERENCES Amenity(itemId) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (listingId) REFERENCES Listing(listingId) ON DELETE CASCADE ON UPDATE CASCADE
);
	
CREATE TABLE AvailableDate (
	listingId INTEGER,
	startDate DATE,
	price REAL CHECK (price >= 0),
	PRIMARY KEY(listingId, startDate),
	FOREIGN KEY (listingId) REFERENCES Listing(listingId) ON DELETE CASCADE ON UPDATE CASCADE
);