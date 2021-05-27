module control(prod, multiplicand, CTRL, clock, newProd, ready);

input [64:0] prod;
input [31:0] multiplicand; 
input CTRL, clock; 
output [64:0] newProd; 
output ready; 

wire signed [31:0] productHighBits, aluResult, count, countInput; 
wire bit0, bit1, dc1, dc2, dc3, dc4, dc5, dc6; 
wire [4:0] out1, out2, opcode; 
wire signed [64:0] preshiftResult; 

assign bit0 = prod[0]; 
assign bit1 = prod[1]; 
assign productHighBits = prod[64:33];

wire [4:0] sum, sub, shift; 
assign sum = 0; 
assign sub = 1; 
assign shift = 4; 

assign out1 = bit0 ? sum : shift;
assign out2 = bit0 ? shift : sub; 
assign opcode = bit1 ? out2 : out1; 



alu alu0(productHighBits, multiplicand, opcode, 5'b0, aluResult, dc1, dc2, dc3); 

assign preshiftResult[64:33] = aluResult; 
assign preshiftResult[32:0] = prod[32:0]; 

assign newProd = preshiftResult >>> 1; 


reg32 counter(countInput, count, clock, 1'b1, CTRL); 
adder32 adder(countInput,dc4, dc5, dc6, count, 32'b1, 1'b0);


assign ready = (count[5])&(!count[4])&(!count[3])&(!count[2])&(!count[1])&(!count[0]);


endmodule