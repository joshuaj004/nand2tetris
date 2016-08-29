// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.
// Video Notes:
// Use Symbolic variables and labels
// Use sensible variable and label names
// Variables: Lower-case
// Labels: Upper-case
// Use Indentation 
// Start with Psedo-code 

// Book notes:
// D is used solely to store data values
// A can store a data register or an address register
// M refers to the data whose address is the current value in the A register
// E.g if we want the opreation D = Memory[516] - 1, we set A to 516
// and then D = M - 1
// A is also usedfor our goto commands. We set A to our goto address
// and then we jump to it 
// Every operation that involves a memory location requires 2 hack commands
// A instruction is an address instruction 
// Syntax: @value where value is either a non-negative decimal number or 
//    a symbol refering to such number.
// The A instruction is the only way to enter a constant into the computer.
// It setst he stage for a subsequent C-instruction to manipulate a certain
// Data memory location or an instruction that specicifies a jump
// C instruction is a compute instruction

// Psedo-code:
// Start with a variable i = 0
// while i < R1:
//   Ram[2] += R0 
//   i++;    
// 

@i      // Sets the variable i = 0
M=0     // Memory location of i = 0

(LOOP)  // Set initial place for the loop
@i      // Find memory address of i    
D=M     // Set the Data input to whatever is stored at i's memory address       
@R1     // Go to Register one's memory address
D=M-D   // Set the data input to current value of memory - whatever i's was
@END    // Sets the location for the jump, if it happens
D;JLE   // Says if R1 - i less than or equal to 0, jump to the end    
@i      // Go to i's location
D=M     // Set Data equal to what it currently is
M=D+1   // Set memory equal to data plus 1
@R0     // Go to Register 0
D=M     // Set Data equal to R0's value
@R2     // Go to Register 2
M=M+D   // Set the memory equal to current memory plus R0's data
@LOOP   // Loop location set to Labels
0;JMP   // Unconditional jump to LOOP    

(END)
@END
0;JMP

