# Nerdle-Lite
A simplified version of the web-based game Nerdle. This simplified version only has addition and subtraction, and features 1 or 2 digit numbers for each term.

The game allows the user to attempt to guess the equation 6 times, with feedback through green, yellow, or gray coloring based on the accuracy of the guess. The game features a check to detect whether the equation inputted by the user is valid. Furthermore the game features keyboard support.

The game also tracks user statistics, including:
- The amount of games played
- Win rate
- Current win streak
- Largest win streak

Whenever the game is closed, the current game state is automatically saved, including all guesses made and user statistics. When the user next opens the game, the saved state is loaded immediately, allowing the user to come back to a previous game seamlessly.
