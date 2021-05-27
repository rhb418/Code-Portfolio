module DX(ins, pc, A, B, clk, reset, en, outins, outpc, outA, outB);

input [31:0] ins, pc, A, B; 
input clk, reset, en; 

output [31:0] outpc, outins, outA, outB; 


reg32 PC(pc, outpc, clk, en, reset);
reg32 IR(ins, outins, clk, en, reset);
reg32 AReg(A,outA, clk, en, reset); 
reg32 BReg(B, outB, clk, en, reset); 


endmodule