module full_adder(S,Cout,A,B,Cin);
    input A, B, Cin;
    output S, Cout;
    wire w1, w2, w3;

    and A_and_B(w1, A,B);
    and A_and_Cin(w2,A,Cin);
    and B_and_Cin(w3, B, Cin);

    or Cout_gate(Cout, w1, w2, w3);
    xor Sresults(S,A,B,Cin);

endmodule