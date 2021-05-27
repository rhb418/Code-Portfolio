module control_div(divisor, AQBlock,  CTRL, clock, newAQ, ready);

input [31:0] divisor; 
input [63:0] AQBlock; 
input CTRL, clock; 

output [63:0] newAQ; 
output ready; 

wire [63:0] shiftAQ; 
wire [31:0] A, AmM, restA, count, countInput; 
wire [4:0] sub, noth, opcode, add; 
wire restore, dc1, dc2, dc3 ,dc4 , dc5, dc6, dc7, dc8, dc9;  

assign sub = 1; 
assign noth = 4; 
assign add = 0; 


assign shiftAQ = AQBlock << 1; 
assign A = shiftAQ[63:32];


alu alu0(A, divisor, sub, 5'b0, AmM, dc1, dc2, dc3); 

assign opcode = AmM[31] ? add: noth;
assign newAQ[0] = AmM[31] ? 1'b0 : 1'b1; 

alu alu1(AmM, divisor, opcode, 5'b0, restA, dc4, dc5, dc6); 



assign newAQ[63:32] = restA; 
assign newAQ[31:1] = shiftAQ[31:1]; 


reg32 counter(countInput, count, clock, 1'b1, CTRL); 
adder32 adder(countInput,dc7, dc8, dc9, count, 32'b1, 1'b0);


assign ready = (count[5])&(!count[4])&(!count[3])&(!count[2])&(!count[1])&(!count[0]);




endmodule