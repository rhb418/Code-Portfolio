module multOvf(out, product, multiplicand, multiplier);

input[63:0] product; 
input[31:0] multiplicand, multiplier; 
output out; 

wire w0, w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w13, w14, w15, w16, w17, w18, w19, w20, w21, w22, w23, w24, w25, w26, w27, w28, w29, w30, w31, highbit, w32, w33, unaryovf, w34, w35; 
assign highbit = product[31]; 

xor xo0(w0, product[32], highbit); 
xor xo1(w1, product[33], highbit); 
xor xo2(w2, product[34], highbit); 
xor xo3(w3, product[35], highbit); 
xor xo4(w4, product[36], highbit); 
xor xo5(w5, product[37], highbit); 
xor xo6(w6, product[38], highbit); 
xor xo7(w7, product[39], highbit); 
xor xo8(w8, product[40], highbit); 
xor xo9(w9, product[41], highbit); 
xor xo10(w10, product[42], highbit); 
xor xo11(w11, product[43], highbit); 
xor xo12(w12, product[44], highbit); 
xor xo13(w13, product[45], highbit); 
xor xo14(w14, product[46], highbit); 
xor xo15(w15, product[47], highbit); 
xor xo16(w16, product[48], highbit); 
xor xo17(w17, product[49], highbit); 
xor xo18(w18, product[50], highbit); 
xor xo19(w19, product[51], highbit); 
xor xo20(w20, product[52], highbit); 
xor xo21(w21, product[53], highbit); 
xor xo22(w22, product[54], highbit); 
xor xo23(w23, product[55], highbit); 
xor xo24(w24, product[56], highbit); 
xor xo25(w25, product[57], highbit); 
xor xo26(w26, product[58], highbit); 
xor xo27(w27, product[59], highbit); 
xor xo28(w28, product[60], highbit); 
xor xo29(w29, product[61], highbit); 
xor xo30(w30, product[62], highbit); 
xor xo31(w31, product[63], highbit); 


and and1(w32, !multiplicand[0], !multiplicand[1], !multiplicand[2], !multiplicand[3], !multiplicand[4], !multiplicand[5], !multiplicand[6], !multiplicand[7], !multiplicand[8], !multiplicand[9], !multiplicand[10], !multiplicand[11], !multiplicand[12], !multiplicand[13], !multiplicand[14], !multiplicand[15], !multiplicand[16], !multiplicand[17], !multiplicand[18], !multiplicand[19], !multiplicand[20], !multiplicand[21], !multiplicand[22], !multiplicand[23], !multiplicand[24], !multiplicand[25], !multiplicand[26], !multiplicand[27], !multiplicand[28], !multiplicand[29], !multiplicand[30], multiplicand[31] );
and and2(w33,  multiplier[0], multiplier[1], multiplier[2], multiplier[3], multiplier[4], multiplier[5], multiplier[6], multiplier[7], multiplier[8], multiplier[9], multiplier[10], multiplier[11], multiplier[12], multiplier[13], multiplier[14], multiplier[15], multiplier[16], multiplier[17], multiplier[18], multiplier[19], multiplier[20], multiplier[21], multiplier[22], multiplier[23], multiplier[24], multiplier[25], multiplier[26], multiplier[27], multiplier[28], multiplier[29], multiplier[30], multiplier[31]);
and and3(w34,  multiplier[0], !multiplier[1], !multiplier[2], !multiplier[3], !multiplier[4], !multiplier[5], !multiplier[6], !multiplier[7], !multiplier[8], !multiplier[9], !multiplier[10], !multiplier[11], !multiplier[12], !multiplier[13], !multiplier[14], !multiplier[15], !multiplier[16], !multiplier[17], !multiplier[18], !multiplier[19], !multiplier[20], !multiplier[21], !multiplier[22], !multiplier[23], !multiplier[24], !multiplier[25], !multiplier[26], !multiplier[27], !multiplier[28], !multiplier[29], !multiplier[30], !multiplier[31]);

and and4(unaryovf, w32, w33); 
or orout(w35,w0, w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w13, w14, w15, w16, w17, w18, w19, w20, w21, w22, w23, w24, w25, w26, w27, w28, w29, w30, w31, unaryovf); 
and and5(out, !w34, w35);

endmodule