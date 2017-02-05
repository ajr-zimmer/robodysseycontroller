## Motivation

To provide a "universal" method of control to the Pioneer as part of the ROBOdyssey project.
The goal of the app is to capture swipe gestures from the user and pass this input off to the laptop piggybacking on the Pioneer.

## ROBOdyssey
The ROBOdyssey system was an exercise in Human-Robot Interaction at the University of Calgary.
The robot used for this system was the Pioneer P3-DX. My partner and I developed an Android app to display a view from the robot’s front-facing camera and control its movements with swipe gestures.
We relayed the touch commands from this app to a laptop piggybacking on the robot.
The commands were interpreted into C++ code that would control the robot’s motors and monitor the onboard sonar array for obstacles. Additionally, we developed an external tracking system using computer vision to monitor the robot’s position on a specified path using OpenCV in Python.
