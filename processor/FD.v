module FD(ins, pc, clk, reset, en, outpc, outins);

input [31:0] ins, pc; 
input clk, reset, en; 

output [31:0] outpc, outins; 


reg32 PC(pc, outpc, clk, en, reset);
reg32 IR(ins, outins, clk, en, reset);


endmodule