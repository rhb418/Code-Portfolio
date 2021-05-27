module LFSR(clk, out);

input clk;
output [31:0] out; 

reg [31:0] count = 2;

always @(posedge clk) begin

    if(count > 0) begin
        count <= count -1;
    end
    else begin
        count <= 0; 
    end
    
end

wire init;
assign init = (count != 0);

wire q0, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16, q17, q18, q19, q20, q21, q22, q23, q24, q25, q26, q27, q28, q29, q30, q31;

wire inDFF0, xor1Out, xor2Out, xor3Out; 
assign inDFF0 = init ? 1'b1 : xor3Out; 


dffe df0(q0, inDFF0, clk, 1'b1, 1'b0); 
dffe df1(q1, q0, clk, 1'b1, 1'b0); 
dffe df2(q2, q1, clk, 1'b1, 1'b0); 
dffe df3(q3, q2, clk, 1'b1, 1'b0); 
dffe df4(q4, q3, clk, 1'b1, 1'b0); 
dffe df5(q5, q4, clk, 1'b1, 1'b0); 
dffe df6(q6, q5, clk, 1'b1, 1'b0); 
dffe df7(q7, q6, clk, 1'b1, 1'b0); 
dffe df8(q8, q7, clk, 1'b1, 1'b0); 
dffe df9(q9, q8, clk, 1'b1, 1'b0); 
dffe df10(q10, q9, clk, 1'b1, 1'b0); 
dffe df11(q11, q10, clk, 1'b1, 1'b0); 
dffe df12(q12, q11, clk, 1'b1, 1'b0); 
dffe df13(q13, q12, clk, 1'b1, 1'b0); 
dffe df14(q14, q13, clk, 1'b1, 1'b0); 
dffe df15(q15, q14, clk, 1'b1, 1'b0); 
dffe df16(q16, q15, clk, 1'b1, 1'b0); 
dffe df17(q17, q16, clk, 1'b1, 1'b0); 
dffe df18(q18, q17, clk, 1'b1, 1'b0); 
dffe df19(q19, q18, clk, 1'b1, 1'b0); 
dffe df20(q20, q19, clk, 1'b1, 1'b0); 
dffe df21(q21, q20, clk, 1'b1, 1'b0); 
dffe df22(q22, q21, clk, 1'b1, 1'b0); 
dffe df23(q23, q22, clk, 1'b1, 1'b0); 
dffe df24(q24, q23, clk, 1'b1, 1'b0); 
dffe df25(q25, q24, clk, 1'b1, 1'b0); 
dffe df26(q26, q25, clk, 1'b1, 1'b0); 
dffe df27(q27, q26, clk, 1'b1, 1'b0); 
dffe df28(q28, q27, clk, 1'b1, 1'b0); 
dffe df29(q29, q28, clk, 1'b1, 1'b0); 
dffe df30(q30, q29, clk, 1'b1, 1'b0); 
dffe df31(q31, q30, clk, 1'b1, 1'b0); 


xor xor1(xor1Out, q31, q21);
xor xor2(xor2Out, xor1Out, q1);
xor xor3(xor3Out, xor2Out, q0);

assign out[0] = q0; 
assign out[1] = q1; 
assign out[2] = q2; 
assign out[3] = q3; 
assign out[4] = q4; 
assign out[5] = q5; 
assign out[6] = q6; 
assign out[7] = q7; 
assign out[8] = q8; 
assign out[9] = q9; 
assign out[10] = q10; 
assign out[11] = q11; 
assign out[12] = q12; 
assign out[13] = q13; 
assign out[14] = q14; 
assign out[15] = q15; 
assign out[16] = q16; 
assign out[17] = q17; 
assign out[18] = q18; 
assign out[19] = q19; 
assign out[20] = q20; 
assign out[21] = q21; 
assign out[22] = q22; 
assign out[23] = q23; 
assign out[24] = q24; 
assign out[25] = q25; 
assign out[26] = q26; 
assign out[27] = q27; 
assign out[28] = q28; 
assign out[29] = q29; 
assign out[30] = q30; 
assign out[31] = q31; 



endmodule