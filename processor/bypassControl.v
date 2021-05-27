module bypassControl(DXIR, XMIR, MWIR, aSelect, bSelect, memSelect);

input [31:0] DXIR, XMIR, MWIR; 
output[1:0] aSelect, bSelect; 
output memSelect;

wire [4:0] XMRD, XMRS, MWRD, DXRS, DXRT, DXOP, XMOP, MWOP, DXRD; 

assign XMRD = XMIR[26:22];
assign MWRD = MWIR[26:22]; 
assign XMRS = XMIR[21:17]; 

assign XMOP = XMIR[31:27]; 
assign MWOP = MWIR[31:27]; 

assign DXRS = DXIR[21:17];
assign DXRT = DXIR[16:12];
assign DXOP = DXIR[31:27]; 
assign DXRD = DXIR[26:22]; 

wire aSelect2, aSelect1, aSelect0, bSelect2, bSelect1, bSelect0, XMWriteReg, MWWriteReg, isAluOp, isAddi, isLoadStore, isBranch, aluLoadStoreAXM, aluLoadStoreAMW, branchAXM, branchAMW, isJr; 


assign XMWriteReg = (XMOP == 0) || (XMOP == 5) || (XMOP ==8); 
assign MWWriteReg = (MWOP == 0) || (MWOP == 5) || (MWOP ==8); 

assign isAluOp = (DXOP == 0);
assign isLoadStore = (DXOP == 7) || (DXOP ==8);
assign isBranch = (DXOP == 2) || (DXOP == 6); 
assign isAddi = (DXOP == 5); 
assign isJr = (DXOP == 4);

assign aluLoadStoreAXM = (isAluOp || isLoadStore || isAddi) && (DXRS == XMRD) && XMWriteReg; 
assign aluLoadStoreAMW = (isAluOp || isLoadStore || isAddi) && (DXRS == MWRD) && !aluLoadStoreAXM; 
assign branchAXM = (isBranch||isJr) && (DXRD ==  XMRD) && XMWriteReg; 
assign branchAMW = (isBranch||isJr) && (DXRD == MWRD) && !branchAXM; 

assign aSelect1 = (aluLoadStoreAXM || branchAXM) && XMWriteReg; 
assign aSelect2 = (aluLoadStoreAMW || branchAMW) && MWWriteReg; 


assign aSelect[0] = aSelect1; 
assign aSelect[1] = aSelect2; 

wire aluBXM, loadStoreBXM, branchBXM, aluBMW, loadStoreBMW, branchBMW; 

assign aluBXM = isAluOp && (DXRT == XMRD) && XMWriteReg; 
assign loadStoreBXM = isLoadStore && (DXRD == XMRD) && XMWriteReg; 
assign branchBXM = isBranch && (DXRS == XMRD) && XMWriteReg; 

assign aluBMW = isAluOp && (DXRT == MWRD) && !aluBXM; 
assign loadStoreBMW = isLoadStore &&(DXRD == MWRD) && !loadStoreBXM; 
assign branchBMW = isBranch && (DXRS == MWRD) && !branchBXM; 

assign bSelect1 = (aluBXM || loadStoreBXM || branchBXM) && XMWriteReg || ((DXOP == 22) && (XMOP == 21));
assign bSelect2 = ((aluBMW || loadStoreBMW ||branchBMW) && MWWriteReg || ((DXOP == 22) && (MWOP == 21))) && !bSelect1;

assign bSelect[0] = bSelect1; 
assign bSelect[1] = bSelect2; 

assign memSelect = (MWOP == 8) && (XMOP == 7) &&(MWRD == XMRD); 

endmodule