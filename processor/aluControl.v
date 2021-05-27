module aluControl(DXIR, aluOp, sham, SXmux);
 
input [31:0] DXIR; 
output [4:0] aluOp , sham; 
output SXmux; 

wire [4:0] op1, sham1, opcode, branchOp; 
wire multOrDiv, isBranch, isAluOp;


assign opcode = DXIR[31:27];

assign op1 = DXIR[6:2];
assign sham1 = DXIR[11:7]; 

assign multOrDiv = ((opcode == 0) && ((op1 == 6) || (op1 == 7))) ; 
assign isBranch = (opcode == 2) || (opcode == 6) || (opcode == 22); 
assign isAluOp = (opcode == 0) && !multOrDiv;


assign branchOp = isBranch ? 1 : 0; 
assign aluOp = isAluOp ? op1 : branchOp; 
assign sham = (opcode ==5'b0 ) ? sham1 :  5'b0;

assign SXmux = (opcode != 0) && (opcode !=2) && (opcode !=6) && (opcode != 22);


endmodule