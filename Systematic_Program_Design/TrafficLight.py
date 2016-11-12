''' traffic light
Design an animation of a traffic light. Your program should show a traffic light
that is red then green then yellow then red etc. For this program your changing
world state data definition should be an enumeration. Here is what your program
might look like if the initial world state was the red traffic Next is red and
so on. To make your lights change at a reasonable speed you can use the rate
option to If you say for example 1 then will wait 1 second between calls to
Remember to follow the HtDW Be sure to do a proper domain analysis before
starting to work on the code file. If you want to design a slightly simpler
version of the program you can modify it to display a single circle that changes
color rather than three stacked circles.

- Domain analysis (use a piece of paper!)

1. Sketch program scenarios
    - scene 1: window displays 'green'
    - scene 2: window displays 'yellow'
    - scene 3: window displays 'red'
    
2. Identify constant information
    - Rate of change of light 1 second each
    - duration of program 10 seconds
    
3. Identify changing information
    - color of light
    
4. Identify big-bang options
    - on tick (per second)
'''
import time

## TrafficLight.py
## displays the different states of a traffic light for 10 seconds.

# ---------- Constants ----------
SPEED = 1  # rate of change
TIMER = 10 # length of program in seconds

# ---------- Data definitions ----------

## Traffic_Light is one of:
##     - 'red'
##     - 'green'
##     - 'yellow'
## interp. the color of a traffic light  

def fn_for_Traffic_Light(tl):
    if tl == 'red':
        pass
    elif tl == 'green':
        pass
    elif tl == 'yellow':
        pass

## Template rule used:
##     - one of: 3 cases
##     - atomic distinct: 'red'
##     - atomic distinct: 'green'
##     - atomic distinct: 'yellow'
    
# ---------- Functions ----------

# Traffic_Light -> Traffic_Light
# Starts the program with the current world state (tl)

def main(tl):
    global SPEED
    global TIMER

    while TIMER > 0:
        print(tl)
        tl = next_traffic_light(tl)
        time.sleep(SPEED)
        TIMER = TIMER - 1
    
# Traffic_Light -> Traffic_Light
# produces the next traffic light

'''Stub
def next_traffic_light(tl):
    if tl == 'red':
        pass
    elif tl == 'green':
        pass
    elif tl == 'yellow':
        pass
'''

def next_traffic_light(tl):
    if tl == 'red':
        return 'green'
    elif tl == 'green':
        return 'yellow'
    elif tl == 'yellow':
        return 'red'
    
assert next_traffic_light('green') == 'yellow'
assert next_traffic_light('red') == 'green'
assert next_traffic_light('yellow') == 'red'
#--------------------------------------------------#

main('green')
