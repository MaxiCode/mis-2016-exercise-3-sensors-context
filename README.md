# Mobile Information Systems Excercise III

## Group:
- Max Weber (110068)
- Muhammad Raisul Islam (113993)

## Views:
Upper View:
- plots the measured accelerometer data graph
- red = X
- green = Y
- blue = Z
- white = magnitude
Lower View:
- yellow = the FFT of the magnitude

## SeekBars:
Upper SeekBar:
- modify the sample rate (0 - 100.000)
Lower SeekBar:
- modify the FFT window size (0*2dp - 100*2dp)

## Movement Classification
- 3 States: Chilling, walking, running (was tested in real live so i get the thresholds ;) 
- printing of the average and the maximum frequency of the FFT result
- to classify the state i used only the average frequency (chilling < 25, 25 < walking < 31, runnung > 31)

## Problems
- i was not able to show an icon of a little running man on the left side of the notification ;)

## Test Environment
- HTC One M8
- the android emulator (but sensor stuff was simulated)
