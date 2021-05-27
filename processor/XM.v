module XM(ins, PC, O, B, clk, reset, en, outins, outPC, outO, outB);

input [31:0] ins, O, B, PC; 
input clk, reset, en; 

output [31:0] outins, outO, outB, outPC; 

reg32 IR(ins, outins, clk, en, reset);
reg32 OReg(O, outO, clk, en, reset);
reg32 BReg(B,outB, clk, en, reset);
reg32 PCReg(PC,outPC, clk, en, reset);

endmodule