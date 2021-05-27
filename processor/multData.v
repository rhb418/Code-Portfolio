module multData(A, B, DXIR, clock, reset, outA, outB, outIR, out_ctrl_Mult, out_ctrl_DIV);

input [31:0] A, B, DXIR;
input clock, reset; 

output [31:0] outA, outB, outIR;
output  out_ctrl_Mult, out_ctrl_DIV; 

wire [4:0] DXIROP, DXIRALUOP; 
wire enable, ctrl_DIV, ctrl_MULT;

assign DXIROP = DXIR[31:27];
assign DXIRALUOP = DXIR[6:2]; 

assign enable = (DXIROP == 0) && (DXIRALUOP == 6 || DXIRALUOP == 7);
assign ctrl_DIV = (DXIROP == 0) && (DXIRALUOP == 7);
assign ctrl_MULT = (DXIROP == 0) && (DXIRALUOP == 6);

reg32 regA(A, outA, clock, enable, reset);
reg32 regB(B, outB, clock, enable, reset);
reg32 regIR(DXIR,outIR,clock,enable,reset);

dffe ctrlMult(out_ctrl_Mult, ctrl_MULT, clock, 1'b1, 1'b0);
dffe ctrlDiv(out_ctrl_DIV, ctrl_DIV, clock, 1'b1, 1'b0);
/*
always @ (posedge clock) begin
    $display("AData: %d", outA);
    $display("BData: %d", outB);
    $display("CTRLMULT: %d", out_ctrl_Mult);
    $display("CTRLDIV: %d", out_ctrl_DIV);
    $display("ALUOP: %d", DXIRALUOP);
end
*/
endmodule