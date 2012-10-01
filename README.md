comp235-lab-verifier
====================

Web based automatic verifier for COMP235 Labs.

Uses the Play! framework (which allows using java as the server side language). 
Currently hosted at comp235labs.herokuapp.com

ChangeLog
====================
+ 27/09/2012 - v1.0 : Supports testing each machine with an arbitrary set of inputs (given in a text file).
+ 29/09/2012 - v1.1 : Supports tests for determinism, tapeBounds, Alphabet and Lab4.2.
+ 01/10/2012 - v1.2 : Added summary of results, converted determinism check into a warning,
and added handling of corrupt uploaded files.