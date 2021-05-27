module lls1bit_tb;
    wire[31:0] A;
    wire [31:0] out;  
    wire [4:0] sham; 

    arsbarrel memes(out, A, sham); 

    integer i;

    assign{A} = i[31:0];
    assign sham = 3; 

    initial begin
        for (i = -10; i< 10; i =i+1) begin
            #20
            $display("A:%b, Out:%b", A, out);
        end
        $finish; 
    end
endmodule