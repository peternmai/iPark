# iPark - Community Based Parking Reservation Made Easy

![LOGO](/app/src/main/res/mipmap-hdpi/ic_launcher.png?raw=true)

Want to manage parking reservations? Don't have the resources to build expensive
metering hardware to keep track of your parking lot status? Are customers leaving
your parking lot frustrated that there are not spots available?

Have no fear. iPark is here to help! iPark is a ***proof of concept*** community based
Android app that allows parking structure companies to set up automatic
parking reservation for their users. Users can then reserve a parking spot through
the app and the owners can rely on the community to ensure that users are
following parking lot guidelines.

## How Does This Work?

iPark was built from the ground up as a community based app. We wanted to create an
intuitive way for users to quickly locate available parking spots in a busy
community, while also incentivizing users to only park during their designated
time.

How would we achieve this? The idea is actually quite simple. Customers
always have to walk pass rows of cars on their way out of the parking lot. Why
not have them spend that time helping security staffs check for who is parked
illegally? Customers can report a vehicle and be rewarded for their good deed
by the discretion of the parking lot owner (free parking lot hours in the future,
etc).

Parking lot staffs can then give a citation or tow the illegally parked vehicle.
Customers parking at the lot are now incentivize to leave on time.
Customers coming in can have confidence that their reserved spot is available.
Parking lot management can ensure that their parking lots are being operated at
maximum utility. Customers are having a good experience. Management have their
work cut out. It's a win win for everyone!


## Features

- **Customers (Those who want to find parking)**
  - *Parking Reservation*
    - Self parking reservation before arrival with automatic spot assignment
    - Automatic parking re-assignment if a car is parked illegally in reserved spot
    - Rate shown up front during reservation
    - Early checkout, forfeiting remaining reservation time and re-adjusted fees (less)
    - Automatic checkout upon end of reservation
    - View history of past parking
  - *Community Features*
    - Easy access to map to see which spot should be available
    - Ability to report users who are parked without a reservation
    - Parking structure owner will incentivize those who report
    - Can view history of reports and incentives
  - *Account Sign Up*
    - Easy in app account sign up
    - Checks for valid information (user name, email, password)
    - Password reset through email
  - *Other*
    - Can report of any emergencies
    - Can receive notifications from parking lot management

- **Parking Lot Management**
  - Can see status of their parking lots using the same app
  - Live map displaying status of parking lots
  - Live notification of emergencies or illegal parkings
  - Ability to change parking spot status (Owner Reserved, Available, Illegal, Taken)
  - Ability to change parking lot pricing
  - Can send out notifications to specific group(s)
  - Can see past customer reviews

## App Preview

#### Parking Reservation
This walks through the steps of reserving a parking spot. It'll also highlight what will
happens when a user tries to see reservation status before reserving, arriving at
reservation and realizing parking spot is taken, as well as the reservation status and
early check out page.

![Parking Reservation](/doc/demo/ParkingReservation_Small.gif?raw=true)

#### Live Parking Lot Information
This shows the live map displayed on both the parking lot management's (left) and customer's
(right) interface. Users can report a parking spot and it'll show up as a live notification
for the parking management staffs. Security can then handle the situation and set the spot
back to being available. They can also make a spot owner reserved. Other features highlighted
will be price change, reviews, announcement, and emergency logs.

![Boss and User Interface](/doc/demo/BossAndUserInterface.gif?raw=true)

## Backend

This app uses Firebase as the back end server. This allows for handling of account creation and
authentication through Firebase. It also allows for user to easily reset passwords through their
registered account email if they forget. All parking lot informations are stored in Firebase as well.
This ensures that every user is getting reliable, live, parking lot information, allowing for a quick
and seamless parking reservation experience.

## Note
This is a UCSD CSE 110 Software Engineering Project. Our group decided the main topic for our app
and a client group was assigned to us based on their interest in our project. The project therefore
consisted of two teams, the client and the developer team, and spanned the course of 10 weeks. We
followed the agile development process in which the client and developer team would meet every week
to discuss progress made on the app and adjust the course of the app to fit the client's changing
needs and desires.
