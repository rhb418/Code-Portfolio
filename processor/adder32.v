module adder32(S,ovf, lt, ne,A,B,c0); 

input [31:0] A,B; 
input c0; 
output [31:0] S;
output ovf, lt, ne; 

wire c1, c2, c3,c4; 

CLA_8bit_adder add1(S[7:0],c1,A[7:0],B[7:0],c0);
CLA_8bit_adder add2(S[15:8],c2,A[15:8],B[15:8],c1);
CLA_8bit_adder add3(S[23:16],c3,A[23:16],B[23:16],c2);
CLA_8bit_adder add4(S[31:24],c4,A[31:24],B[31:24],c3);

wire na31, nb31, ns31 , a1, a2; 

not n1(na31, A[31]);
not n2(nb31, B[31]); 
not n3(ns31, S[31]); 

and and1(a1, na31, nb31, S[31]); 
and and2(a2, A[31], B[31], ns31); 

or or1(ovf, a1, a2);


wire lt1, lt2, novf; 

not n4(novf, ovf); 
and and3(lt1, novf, S[31]); 
and and4(lt2, ovf, A[31]);
or ltor(lt, lt1, lt2); 

or neor(ne, S[0], S[1], S[2] , S[3], S[4], S[5], S[6], S[7], S[8], S[9], S[10], S[11], S[12], S[13], S[14], S[15] ,S[16], S[17], S[18], S[19], S[20] ,S[21], S[22], S[23], S[24], S[25], S[26], S[27], S[28], S[29], S[30], S[31], ovf);


endmodule