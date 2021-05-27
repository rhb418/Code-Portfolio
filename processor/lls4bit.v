module lls4bit(A, out);

input [31:0] A;
output [31:0] out; 

assign out[31:4] = A[27:0];
assign out[0] = 0; 
assign out[1] = 0; 
assign out[2] = 0; 
assign out[3] = 0; 


endmodule