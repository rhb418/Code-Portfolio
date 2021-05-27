module exceptionHandler (DXIR, alu_out, ovf, XMIR_input, XMO_input);

input [31:0] DXIR, alu_out;
input ovf;

output [31:0] XMIR_input, XMO_input; 

wire [31:0] alteredIns, alteredO_input , addi_altered, alu_altered, w2, w3, w4, rd , w5, w6; 
wire [4:0] opcode, aluop, w1;

assign w1 = 30; 
assign w4 = 2;
assign w2 = 1; 
assign w3 = 3; 

assign rd = DXIR[26:22];
assign opcode = DXIR [31:27];
assign aluop = DXIR [6:2]; 

assign alteredIns[31:27] = DXIR[31:27];
assign alteredIns[21:0] = DXIR[21:0];
assign alteredIns[26:22] = w1; 

assign addi_altered = w4;
assign alu_altered = (aluop == 5'b0) ? w2: w3; 

assign alteredO_input = (opcode == 5'b0) ? alu_altered : addi_altered; 

assign w5 = ovf ? alteredO_input : alu_out; 
assign XMO_input = ((rd == 5'b0) && opcode !=21) ? 0 : w5;
assign w6 = ovf ? alteredIns : DXIR; 

assign XMIR_input = (opcode == 0 &&(aluop==6||aluop==7)) ? 0 : w6; 


endmodule