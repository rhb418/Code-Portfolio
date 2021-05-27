module CLA_8bit_adder(S,c8,A,B,c0);
    input [7:0] A, B;
    input c0; 
    output [7:0] S; 
    output c8;

    wire c1, c2, c3, c4, c5, c6, c7, g0, g1, g2, g3, g4, g5, g6, g7, p0, p1, p2, p3, p4, p5, p6, p7; 
    wire u0, u1, u2, u3, u4, u5, u6 ,u7; 

    and g0a(g0, A[0], B[0]); 
    and g1a(g1, A[1], B[1]); 
    and g2a(g2, A[2], B[2]); 
    and g3a(g3, A[3], B[3]); 
    
    and g4a(g4, A[4], B[4]); 
    and g5a(g5, A[5], B[5]); 
    and g6a(g6, A[6], B[6]); 
    and g7a(g7, A[7], B[7]); 
    

    or p0o(p0, A[0], B[0]); 
    or p1o(p1, A[1], B[1]); 
    or p2o(p2, A[2], B[2]); 
    or p3o(p3, A[3], B[3]); 
    
    
    or p4o(p4, A[4], B[4]); 
    or p5o(p5, A[5], B[5]); 
    or p6o(p6, A[6], B[6]); 
    or p7o(p7, A[7], B[7]); 
    

    //Calculations for c1:

    wire c1w1; 
    and c1w1a(c1w1, p0, c0); 
    or c1o(c1, g0, c1w1);

    //Calculations for c2:

    wire c2w1, c2w2; 
    and c2w1a(c2w1, p1, p0, c0);
    and c2w2a(c2w2, p1, g0);
    or c2o(c2, g1, c2w1, c2w2);

    //Calculations for c3:

    wire c3w1, c3w2, c3w3; 
    and c3w1a(c3w1, p2, p1, p0, c0);
    and c3w2a(c3w2, p2, p1, g0);
    and c3w3a(c3w3, p2, g1); 
    or c3o(c3, g2, c3w1, c3w2, c3w3); 
    
    //Calculations for c4:

    wire c4w1, c4w2, c4w3, c4w4; 
    and c4w1a(c4w1, p3, p2, p1, p0, c0);
    and c4w2a(c4w2, p3, p2, p1, g0);
    and c4w3a(c4w3, p3, p2, g1);
    and c4w4a(c4w4, p3, g2); 
    or c4o(c4, g3, c4w1, c4w2, c4w3, c4w4);

    //Calculations for c5:
    
    wire c5w1, c5w2, c5w3, c5w4, c5w5; 
    and c5w1a(c5w1, p4, p3, p2, p1, p0, c0);
    and c5w2a(c5w2, p4, p3, p2, p1, g0); 
    and c5w3a(c5w3, p4, p3, p2, g1);
    and c5w4a(c5w4, p4, p3, g2); 
    and c5w5a(c5w5, p4, g3); 
    or c5o(c5, g4, c5w1, c5w2, c5w3, c5w4, c5w5);

    //Calculations for c6:
    wire c6w1, c6w2, c6w3, c6w4, c6w5, c6w6;
    and c6w1a(c6w1, p5, p4, p3, p2, p1, p0, c0); 
    and c6w2a(c6w2, p5, p4, p3, p2, p1, g0);
    and c6w3a(c6w3, p5, p4, p3, p2, g1); 
    and c6w4a(c6w4, p5, p4, p3, g2);
    and c6w5a(c6w5, p5, p4, g3); 
    and c6w6a(c6w6, p5, g4); 
    or c6o(c6, g5, c6w1, c6w2, c6w3, c6w4, c6w5, c6w6); 


    //Calculations for c7:
    wire c7w1, c7w2, c7w3, c7w4, c7w5, c7w6, c7w7;
    and c7w1a(c7w1, p6, p5, p4, p3, p2, p1, p0, c0);
    and c7w2a(c7w2, p6, p5, p4, p3, p2, p1, g0); 
    and c7w3a(c7w3, p6, p5, p4, p3, p2, g1); 
    and c7w4a(c7w4, p6, p5, p4, p3, g2);
    and c7w5a(c7w5, p6, p5, p4, g3);
    and c7w6a(c7w6, p6, p5, g4); 
    and c7w7a(c7w7, p6, g5); 
    or c7o(c7, g6, c7w1, c7w2, c7w3, c7w4, c7w5, c7w6, c7w7);


    full_adder adder0(S[0], u0, A[0], B[0], c0); 
    full_adder adder1(S[1], u1, A[1], B[1], c1); 
    full_adder adder2(S[2], u2, A[2], B[2], c2); 
    full_adder adder3(S[3], u3, A[3], B[3], c3); 
    full_adder adder4(S[4], u4, A[4], B[4], c4); 
    
    full_adder adder5(S[5], u5, A[5], B[5], c5); 
    full_adder adder6(S[6], u6, A[6], B[6], c6); 
    full_adder adder7(S[7], c8, A[7], B[7], c7); 



endmodule