module lls2bit(A, out);

input [31:0] A;
output [31:0] out; 

assign out[31:2] = A[29:0];
assign out[0] = 0; 
assign out[1] = 0; 


endmodule