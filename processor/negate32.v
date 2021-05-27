module negate32(in, out);

input [31:0] in;
output [31:0] out; 

wire [31:0] intermediate; 
wire dc1, dc2, dc3, dc4;

not32bit n1(intermediate, in); 
adder32 a1(out, dc1, dc2, dc3, intermediate , 32'b1,1'b0); 

endmodule