module div (dividend, divisor, CTRL, clock, result, ready);

input [31:0] divisor, dividend; 
input CTRL, clock; 
output [31:0] result; 
output ready; 



wire [63:0] initAQBlock; 
assign initAQBlock[31:0] = dividend; 
assign initAQBlock[63:32] = 0; 


wire [63:0] newAQ, regout, regin; 

control_div control_div0(divisor, regout,  CTRL, clock, newAQ, ready);

assign regin = CTRL ? initAQBlock :newAQ; 

reg64 quot(regin, regout, clock, 1'b1, 1'b0); 


assign result = regout[31:0]; 


endmodule