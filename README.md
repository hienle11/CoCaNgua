RMIT University Vietnam
Course: INTE2512 Object-Oriented Programming
Semester: 2019C
Assessment name: Final Project
Team name: Team number 2
Team members: 
    Le Quang Hien (s3695516)
    Ninh Van Hoang Phuoc (s3777323)
    Doan Luong Hoang (s3749795)
    Le Ngoc Duy (s3757327)

1. INTRODUCTION
One short paragraph to describe what this software is about.
In this project, we created a "Co Ca Ngua" game with specific requirements from our lecturer

2. FEATURES
List the implemented features of the software in point-form.
Here are our completed features for this game: 
    1) Display game and its GUI components: All necessary elements of the GUI are correctly displayed. 
    2) Set up players: At the main menu, the user is able to select the number of players as well as selecting which player is a Computer player 	
    3) Set up the order of play: At the very beginning of a match, the pair of dices will be rolled to determine the order of play 	
    4) Roll dices: When the roll button is clicked, the pair of dices will spin and display two numbers once spinning is finished	
    5) Move: A horse will move with the corresponding dice number(s)	
    6) Stop: When a player has all of his or her horses up in the highest home positions, the match will stop and a message pane will appear declaring who and that player's score	
    7) Moving algorithm: Finished, with further description is the Design part 
    8) Sound: A sound effect for dice rolling, horse moving, kicking and winning 	
    9) Score: Scores are updated correctly in the GUI, once kicking or moving to home positions actions occur	
    10) Play again or Quit: All those buttons are handled correctly 
    11) Game status: All statuses of the match are updated to the GUI	
    12) Language: Able to interchange between English and Vietnamese	
    13) Networking: Can be set up to play from 2 different computers completely - other parts remain unfinished

3. DESIGN
Describe major design decisions and major algorithms.
For the moving algorithm, after the dice(s) result acquired, the program will check and generate a possible moves list. 
A possible move is one which allows a horse to move forward (not blocked), to kick another horse, to spawn, to move to home arrival, to move to another home position for both the results of a single dice and the sum of both dices.
Once this list is created, our team will set a priority level for the options within that list.
That level is: Move to home arrival > Move to home positions > Kick another horse > Spawn > Move forward.
The first two level are the most important because a horse will not be kicked once is gets to a home position.

4. INSTALLATION
Provide instructions on how to install and run the software.
Open IntelliJ IDE and choose run "Main.java"

5. KNOWN BUGS
Multiplayer mode can only player with only two computers. It has to be set up correctly.
There must be a host, a host tick on the "online" box of the color the other player wants to play.
The play who is not a host must tick on all "online" boxes, except the one corresponding to the color player he wants to play

6. ACKNOWLEDGEMENT
List the resources and help that you used to complete this project.
Failing to do so might be considered as plagiarism.
Resources acquired from lecture slides and from the book "Introduction to Java Programming"