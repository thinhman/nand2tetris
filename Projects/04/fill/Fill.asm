// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
(loop)
@8192
D=A
@offset
M=D
@KBD
D=M
@white
D;JEQ
@black
0;JMP

(white)
@offset
D=M
@SCREEN
A=A+D
M=0
@offset
M=M-1
D=M
@white
D;JGT
D;JEQ
@loop
0;JMP

(black)
@offset
D=M
@SCREEN
A=A+D
M=-1
@offset
M=M-1
D=M
@black
D;JGT
D;JEQ
@loop
0;JMP