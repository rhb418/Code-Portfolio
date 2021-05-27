module decoder32(select, out);
    input [4:0] select;
    output [31:0] out; 
    wire [31:0] w1; 
    assign w1 = 1; 


    llsbarrel shift(out, w1, select); 

endmodule