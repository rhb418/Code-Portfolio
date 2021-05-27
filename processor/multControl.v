module multControl(DXIR, ready, clock, multStall, commitOp);

input [31:0] DXIR; 
input ready, clock;
output multStall, commitOp; 

wire [4:0] DXIROP, DXIRALUOP; 

assign DXIROP = DXIR[31:27];
assign DXIRALUOP = DXIR[6:2]; 

wire q, d, multOrDiv;
assign multOrDiv = (DXIROP == 0) && (DXIRALUOP == 6 || DXIRALUOP == 7);

assign d = (q || multOrDiv);
assign commitOp = ready; 

dffe staller(q, d, clock, 1'b1, ready);

assign multStall = q; 
/*
always @ (negedge clock) begin
    $display("STALL ",multStall);
end
*/
endmodule