module PW(result, DXIR, ready, clk, en, reset, readyOut, resultOut, insOut);

input [31:0] result, DXIR;
input ready, clk, en, reset;
output readyOut;
output [31:0] resultOut, insOut; 

reg32 RES(result, resultOut, clk, en, reset);
reg32 IR(DXIR, insOut, clk, en, reset);
dffe rd(readyOut, ready, clk, en, 1'b0);
/*

always @ (negedge clk) begin
    $display("Result: %d", resultOut);
end

always @ (negedge clk) begin
    $display("ReadyOut: %d", readyOut);
end

always @ (negedge clk) begin
    $display("InsOUT: %d", insOut[26:22]);
end
*/
endmodule