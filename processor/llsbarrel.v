module llsbarrel(out, A, sham);

input [31:0] A;
input [4:0] sham; 
output [31:0] out; 

wire [31:0] out16, out8, out4, out2, out1, mux16, mux8 ,mux4, mux2;

lls16bit s1(A,out16);
mux_2 m16(mux16, sham[4], A,out16);
lls8bit s2(mux16, out8); 
mux_2 m8(mux8, sham[3], mux16,out8);
lls4bit s3(mux8, out4);
mux_2 m4(mux4, sham[2], mux8,out4);
lls2bit s4(mux4, out2);
mux_2 m2(mux2, sham[1], mux4,out2);
lls1bit s5(mux2, out1);
mux_2 m1(out, sham[0], mux2,out1);

endmodule

