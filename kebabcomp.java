package org.firstinspires.ftc.teamcode;


// this is the code for calculating Kebab movement
// the Kebab is the spinning wheel & such

/*

HOW TO USE THIS:

This is another plug-and-play piece of code for controlling the robot.
Instead of handling the driving, however, this handles the kebab: the
spinning wheel on the robot that allows objects to be launched.

To use this, create an object of the class kebabcomp. That object has one
method that is really going to be called in an interesting capacity:
'new_speed'. This function calculates the new speed given two parameters:
the old speed ('old_speed'), and a list of buttons pressed ('buttons').
'old_speed' is a value from -1.0 to 1.0, representing the old speed.
Meanwhile, the list of buttons pressed is different. It is 3 booleans,
representing the status of whether the buttons Y, B, and A are being
pressed, in order. {Y, B, A}.

*/

public class kebabcomp {
    private double speed_change;
    private double speed_cap;
    
    public kebabcomp () {
        speed_change = .3; // how much the speed changes by per tick
        speed_cap = 1; // maximum speed
    }
    
    public double new_speed (double old_speed, boolean[] buttons) {
        // Buttons is: Y, B, A. Booleans, true if pressed.
        
        double speed_to_return = old_speed;
        
        //detect button presses
        if (buttons[0]) {
            speed_to_return += speed_change;
        }
        if (buttons[1]) {
            speed_to_return = 0;
        }
        if (buttons[2]) {
            speed_to_return -= speed_change/3;
        }
        
        //compressing values
        if (speed_to_return>speed_cap) {
            speed_to_return = speed_cap;
        }
        if (speed_to_return<-speed_cap) {
            speed_to_return = -speed_cap;
        }
        
        return speed_to_return;
        
    }
}