@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@EQUAL1
D;JEQ
@SP
A=M-1
M=0
@EXIT1
0;JMP
(EQUAL1)
@SP
A=M-1
M=-1
(EXIT1)
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@EQUAL2
D;JEQ
@SP
A=M-1
M=0
@EXIT2
0;JMP
(EQUAL2)
@SP
A=M-1
M=-1
(EXIT2)
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@EQUAL3
D;JEQ
@SP
A=M-1
M=0
@EXIT3
0;JMP
(EQUAL3)
@SP
A=M-1
M=-1
(EXIT3)
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@LESS4
D;JLT
@SP
A=M-1
M=0
@EXIT4
0;JMP
(LESS4)
@SP
A=M-1
M=-1
(EXIT4)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@LESS5
D;JLT
@SP
A=M-1
M=0
@EXIT5
0;JMP
(LESS5)
@SP
A=M-1
M=-1
(EXIT5)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@LESS6
D;JLT
@SP
A=M-1
M=0
@EXIT6
0;JMP
(LESS6)
@SP
A=M-1
M=-1
(EXIT6)
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@GREATER7
D;JGT
@SP
A=M-1
M=0
@EXIT7
0;JMP
(GREATER7)
@SP
A=M-1
M=-1
(EXIT7)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@GREATER8
D;JGT
@SP
A=M-1
M=0
@EXIT8
0;JMP
(GREATER8)
@SP
A=M-1
M=-1
(EXIT8)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
D=M-D
@GREATER9
D;JGT
@SP
A=M-1
M=0
@EXIT9
0;JMP
(GREATER9)
@SP
A=M-1
M=-1
(EXIT9)
@57
D=A
@SP
A=M
M=D
@SP
M=M+1
@31
D=A
@SP
A=M
M=D
@SP
M=M+1
@53
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
M=D+M
@112
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
M=M-D
@SP
A=M-1
D=0
M=D-M
@SP
AM=M-1
D=M
A=A-1
M=D&M
@82
D=A
@SP
A=M
M=D
@SP
M=M+1
@SP
AM=M-1
D=M
A=A-1
M=D|M
@SP
A=M-1
M=!M