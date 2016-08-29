@i  
M=0

(LOOP)
@i
M=D        
@R1
M=D     
@END
D;JLE       
D=M
@i
D=M
M=M+1
@R0
D=M
@R2
M=M+D
@LOOP
0;JMP

(END)
@END
0;JMP