// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

// Psuedo-code:
// Loop at the top
// Check keyboard register
// If keyboard is blank/0:
//      Go through screen and change all the values to 0x16
// Else:
//      Go through screen and change all the values to 1x16
// Go through screen by setting a for loop for 8192 times 
// and set M=-1 for black or 0 for white.
// Loop back up to the top

(LOOP)          // Sets loop to be up here
@8192           // Goes to memory address 8192
D=A             // Puts 8192 into the Data slot
@i              // Defines the variable i
M=D             // Sets it to be 8192 because there are 8192 words in the screen
@0              // Same thing as above but with 0
D=A             // Ditto
@j              // Defines the variable j
M=D             // Sets it to be 0 to act as a counter
@KBD            // Go to the keyboard register
D=M             // Sets the data to whatever value the keyboard has currently
@BLACK          // Sets the jump condition to be black
D;JNE           // If the keyboard is being pressed (D is not 0), jump to BLACK Line 11
@0
D=A
@color          // Defines color
M=D             // Set the data to 0 so the color will be white
@SCREENCHANGE   // Sets the jump location to SCREENCHANGE
0;JMP           // Jumps to SCREENCHANGE Line 17

(SCREENCHANGE)  // Sets SCREENCHANGE to be here
@j              // Go to j's memory address
D=M             // Set Data equal to j's value
@i              // Go to i's memory address
D=M-D           // Set Data equal to i's value - j's value
@LOOP           // Defines loop as the jump location
D;JLE           // Jump if 8192 - counter <= 0 Line 23
@SCREEN         // Go to Screen's start address 
D=A             // Sets Data equal to the screen's memory address
@j              // Go to j's memory address
D=D+M           // Adds j's value to D so that it can be used
@temp
M=D
@color
D=M
@temp
A=M
M=D             // Set to whatever value (0/-1) was stored in color 
@j              // Go to j
M=M+1           // Increment j by 1
@SCREENCHANGE   // Set the jump location to SCREENCHANGE
0;JMP           // Unconditional jump Line 39



@LOOP           // Sets the jump location to LOOP
0; JMP          // Jumps to LOOP Line 41    

(BLACK)         // Sets black to be here
@color          // Defines the variable color
M=-1            // Sets the data to -1 so the color will be black    
@SCREENCHANGE   // Sets the jump location to SCREENCHANGE
0;JMP           // Jumps to SCREENCHANGE Line 45