""" 
Short program to play rock paper scissors against the computer.
"""
import random

# Returns choice for computer
def getCompChoice():
    options = ['r', 'p', 's']
    return options[random.randint(0, 2)]

# Prints results of who won based on choices
def result(player, computer):
    if player == computer:
        print('DRAW!')
    elif player == 'r' and computer == 'p':
        print('COMPUTER WINS!')
    elif player == 'r' and computer == 's':
        print('PLAYER WINS!')
    elif player == 's' and computer == 'p':
        print('PLAYER WINS!')
    elif player == 's' and computer == 'r':
        print('COMPUTER WINS!')
    elif player == 'p' and computer == 'r':
        print('PLAYER WINS!')
    elif player == 'p' and computer == 's':
        print('COMPUTER WINS!')
    else:
        print('incorrect input use r, s, or p')

# Get user input and change to lowercase
userInput = input('rock(r), paper(p), or scissors(s): ')
userInput = userInput.lower()

# Get computer opponents choice
compChoice = getCompChoice()

# Display results
print('Player chooses: ' + userInput + ', Computer chooses: ' + compChoice)
print(userInput + ' vs ' + compChoice)
result(userInput, compChoice)
