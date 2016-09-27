''' Countdown Animation Problem
Design an animation of a simple countdown. Your program should display a simple
countdown that starts at ten and decreases by one each clock tick until it
reaches zero and stays there. To make your countdown progress at a reasonable
speed you can use the rate option to If you say for example 1 then will wait 1
second between calls to Remember to follow the HtDW Be sure to do a proper
domain analysis before starting to work on the code file.

- Domain Analysis
1.sketch program scenarios
    - scenario 1: window displays 10, the start of the countdown
    - scenario 2: window displays 5, half way through the countdown
    - scenario 3: window displays 0, end of countdown

2. identify constant information
    - Speed of countdown 1 sec per tick
    - Timer starts at 10

3. Identify changing information
    - Count down state

4. Identify big-bang options
    - on-tick
'''

# CountDown.py
# A program that displays a countdown from 10 to 0

import time

# --------- Constants ----------

SPEED = 1
TIMER = 10

# ------- Data Definitions --------

## Count_Down_State is Integer [0,10]
## interp. the position in a count down
cds1 = 10 # Start of countdown
cds2 = 5  # Middle of countdown
cds3 = 0  # End of countdown

def fn_for_Count_Down_State(cds):
    #...cds
    pass

## Template rules used:
##     - atomic non-distinct: Integer[0,10]

# ------ Functions -------

# Main starts the program with count down state 10

def main():
    global TIMER
    global SPEED

    while TIMER > 0:
        print(TIMER)
        TIMER = Next_Count_Down_State(TIMER)
        time.sleep(SPEED)
    print(TIMER)

## Count_Down_State -> Count_Down_State
## takes n and produces n - 1

''' Stub
def Next_Count_Down_State(cds):
    return 0
'''

def Next_Count_Down_State(cds):
    return cds - 1 if cds > 0 else 0

assert Next_Count_Down_State(10) == 9
assert Next_Count_Down_State(1) == 0
assert Next_Count_Down_State(0) == 0

#-----------------------------------------------
main()

