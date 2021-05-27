module boothmult(multiplicand, multiplier, CTRL, clock, result, ready);
input [31:0] multiplicand, multiplier; 
input clock, CTRL; 
output [63:0] result; 
output ready, exception; 

wire [64:0] initProductBlock; 
assign initProductBlock[0] = 1'b0; 
assign initProductBlock[32:1] = multiplier; 
assign initProductBlock[64:33] = 0; 

wire [64:0] newProd, regout, regin; 

control control0(regout, multiplicand, CTRL, clock, newProd, ready);

assign regin = CTRL ? initProductBlock : newProd; 

reg65 prod(regin, regout, clock, 1'b1, 1'b0); 


assign result = regout[64:1]; 

endmodule