# JPMC Movie Theater
This program is a movie theater reservation system. It is designed to help theater owners and managers organize their movie showings and customer reservations in an efficient and organized way. The system allows theater managers to add or remove movie showings, print a schedule of all showings in text or json format, make and track customer reservations, and more.

This project was a coding exercise for my job application and required me to utilize my knowledge and experience to greatly improve a poorly written application by identifying and resolving several deliberate design, code quality and test issues as well as implementing testing strategies and frameworks to refine the code base.

## Improved Features
* Customer can make a reservation for the movie.
  * System can calculate the ticket fee for customer's reservation based on ticket price, discounts, and group size.
  * System can store and allow management of movie showings for the day.
  * System can track and manage reservations made by customers.
* The theater has the following discount rules:
  * 20% discount for the 'special movies' which can be applied at the discretion of the theater manager.
  * $3 discount for the first movie showing of the day.
  * $2 discount for the second movie showing of the day.
* System can display the movie schedule of the current day in a simple text format.

## Required Features Added
* Additional discounts and discount features added:
  * 25% discount for any movies showing starting between 11AM ~ 4pm.
  * $1 discount for any movies showing on 7th day of the month.
  * If a movie showing qualifies for multiple discounts, the largest one will be applied.
* System can also display the movie schedule of the current day in a json format.

## Additional Features Added
* A daily schedule is added so that theater managers can add and remove movie showings in a simpler and more efficient way.
* A reservation list is added so that theater managers can keep track of all reservations.
* The movie schedule display is re-formatted and aligned to improve organization.
* JUnit test cases and documentation added for each class to improve code coverage.
