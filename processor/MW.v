module MW(ins, PC, O, D, clk, reset, en, outins, outPC, outO, outD);

input [31:0] ins, O, D, PC; 
input clk, reset, en; 

output [31:0] outins, outO, outD, outPC; 

reg32 IR(ins, outins, clk, en, reset);
reg32 OReg(O, outO, clk, en, reset);
reg32 DReg(D, outD, clk, en, reset);
reg32 PCReg(PC,outPC, clk, en, reset);

endmodule