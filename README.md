# Cinema-Management-System
This is a small cinema booking system that allows users to browse movies, view available showtimes, select seats, and create bookings. Users can also view existing bookings or cancel them. The program stores all booking data in memory while running and provides a simple console menu for navigation.

This project was made by utilising Java classes, ArrayLists, basic validation, and a clean separation between models, services, and the main application layer.

-- Features --

Browse Movies — displays a list of available movies currently showing at the cinema.

View Showtimes — allows users to select a movie and view its scheduled showtimes.

Seat Selection — users can choose seats for a selected showtime, with validation to prevent double booking.

Create Booking — stores a booking containing the movie, showtime, and selected seats.

View Bookings — shows all active bookings made during the session.

Cancel Booking — allows users to remove an existing booking.

-- Class Structure --

Movie — represents a single movie, storing its title and available showtimes.

Showtime — stores the time of a screening and the list of seats associated with it.

Seat — represents an individual seat and whether it is booked.

Booking — stores the details of a user’s booking, including movie, showtime, and selected seats.

CinemaService — handles the logic for the system, including browsing movies, selecting showtimes, validating seats, creating bookings, and managing booking lists.

CinemaApp — the main class that runs the menu. It displays options, reads user input, calls service methods, and manages the overall flow of the program.

How to Run --
--Compile all .java files.

--Run the main application (e.g., CinemaApp).

--Use the menu to browse movies, select showtimes, choose seats, and manage bookings.

--All data is stored in memory for the duration of the program.
