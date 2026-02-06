// just have it be with a 7/8 limit Willow
// you don't have all the time in the world
// button values: Null, 0/8, 2/8, 4/8, 14/16, 15/16, 16/16

package io.github.harpolewillow.complexkebabcalculator;

import java.util.Scanner;

public class ButtonInterpreter {
    
    boolean[] hasBeenPressed;
    boolean[] currentButtonMultipress;
    
    public ButtonInterpreter()
    {
        hasBeenPressed = new boolean[] {false,false,false,false};
        currentButtonMultipress = new boolean[] {false,false,false,false};
    }
    
    public ButtonInterpreter(boolean inputFinalBoolean)
    {
        hasBeenPressed = new boolean[] {false,false,false,false};
        currentButtonMultipress = new boolean[] {false,false,false,inputFinalBoolean};
    }
    
    public boolean[] calculate(boolean b1, boolean b2, boolean b3, boolean b4)
    {
        
        if (b1) {hasBeenPressed[0]=true;}
        if (b2) {hasBeenPressed[1]=true;}
        if (b3) {hasBeenPressed[2]=true;}
        if (b4) {hasBeenPressed[3]=true;}
        
        //r-tick
        if (!b1&&!b2&&!b3&&!b4)
        {
            if      (!hasBeenPressed[0]&&!hasBeenPressed[1]&&!hasBeenPressed[2]&&!hasBeenPressed[3]){}
            else
            {
                currentButtonMultipress = new boolean[] {hasBeenPressed[0],hasBeenPressed[1],hasBeenPressed[2],hasBeenPressed[3]};
            }
            hasBeenPressed[0]=false;
            hasBeenPressed[1]=false;
            hasBeenPressed[2]=false;
            hasBeenPressed[3]=false;
        }
        
        return(currentButtonMultipress);
    }
    
    public static void main(String[] args)
    {
        ButtonInterpreter testButtonInterpreter = new ButtonInterpreter();
        Scanner myScanner = new Scanner(System.in);
        while (true)
        {
            System.out.print("Boolean 1: ");
            boolean firstBoolean  = myScanner.nextBoolean();
            System.out.print("Boolean 2: ");
            boolean secondBoolean = myScanner.nextBoolean();
            System.out.print("Boolean 3: ");
            boolean thirdBoolean  = myScanner.nextBoolean();
            System.out.print("Boolean 4: ");
            boolean fourthBoolean = myScanner.nextBoolean();
            boolean[] myValues = testButtonInterpreter.calculate(firstBoolean,secondBoolean,thirdBoolean,fourthBoolean);
            System.out.println(myValues[0]+", "+myValues[1]+", "+myValues[2]+", "+myValues[3]);
        }
    }
}