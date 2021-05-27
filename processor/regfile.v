module regfile (
	clock,
	ctrl_writeEnable, ctrl_reset, ctrl_writeReg,
	ctrl_readRegA, ctrl_readRegB, data_writeReg,
	data_readRegA, data_readRegB
);

	input clock, ctrl_writeEnable, ctrl_reset;
	input [4:0] ctrl_writeReg, ctrl_readRegA, ctrl_readRegB;
	input [31:0] data_writeReg;

	output [31:0] data_readRegA, data_readRegB;

	// add your code here
	wire [31:0] writeDecode, readADecode, readBDecode; 
	wire r0en, r1en, r2en, r3en, r4en, r5en, r6en, r7en, r8en, r9en, r10en, r11en, r12en, r13en, r14en, r15en, r16en, r17en, r18en, r19en, r20en, r21en, r22en, r23en, r24en, r25en, r26en, r27en, r28en, r29en, r30en, r31en; 
	wire [31:0] r0out, r1out, r2out, r3out, r4out, r5out, r6out, r7out, r8out, r9out, r10out, r11out, r12out, r13out, r14out, r15out, r16out, r17out, r18out, r19out, r20out, r21out, r22out, r23out, r24out, r25out, r26out, r27out, r28out, r29out, r30out, r31out;


	decoder32 wDec(ctrl_writeReg, writeDecode); 
	decoder32 rADec(ctrl_readRegA, readADecode);
	decoder32 rBDec(ctrl_readRegB, readBDecode);

	and a0(r0en, writeDecode[0], ctrl_writeEnable); 
	and a1(r1en, writeDecode[1], ctrl_writeEnable); 
	and a2(r2en, writeDecode[2], ctrl_writeEnable); 
	and a3(r3en, writeDecode[3], ctrl_writeEnable); 
	and a4(r4en, writeDecode[4], ctrl_writeEnable); 
	and a5(r5en, writeDecode[5], ctrl_writeEnable); 
	and a6(r6en, writeDecode[6], ctrl_writeEnable); 
	and a7(r7en, writeDecode[7], ctrl_writeEnable); 
	and a8(r8en, writeDecode[8], ctrl_writeEnable); 
	and a9(r9en, writeDecode[9], ctrl_writeEnable); 
	and a10(r10en, writeDecode[10], ctrl_writeEnable); 
	and a11(r11en, writeDecode[11], ctrl_writeEnable); 
	and a12(r12en, writeDecode[12], ctrl_writeEnable); 
	and a13(r13en, writeDecode[13], ctrl_writeEnable); 
	and a14(r14en, writeDecode[14], ctrl_writeEnable); 
	and a15(r15en, writeDecode[15], ctrl_writeEnable); 
	and a16(r16en, writeDecode[16], ctrl_writeEnable); 
	and a17(r17en, writeDecode[17], ctrl_writeEnable); 
	and a18(r18en, writeDecode[18], ctrl_writeEnable); 
	and a19(r19en, writeDecode[19], ctrl_writeEnable); 
	and a20(r20en, writeDecode[20], ctrl_writeEnable); 
	and a21(r21en, writeDecode[21], ctrl_writeEnable); 
	and a22(r22en, writeDecode[22], ctrl_writeEnable); 
	and a23(r23en, writeDecode[23], ctrl_writeEnable); 
	and a24(r24en, writeDecode[24], ctrl_writeEnable); 
	and a25(r25en, writeDecode[25], ctrl_writeEnable); 
	and a26(r26en, writeDecode[26], ctrl_writeEnable); 
	and a27(r27en, writeDecode[27], ctrl_writeEnable); 
	and a28(r28en, writeDecode[28], ctrl_writeEnable); 
	and a29(r29en, writeDecode[29], ctrl_writeEnable); 	
	and a30(r30en, writeDecode[30], ctrl_writeEnable); 
	and a31(r31en, writeDecode[31], ctrl_writeEnable); 

	reg32 r0(32'b0, r0out, clock, r0en,ctrl_reset);
	reg32 r1(data_writeReg, r1out, clock, r1en,ctrl_reset);
	reg32 r2(data_writeReg, r2out, clock, r2en,ctrl_reset);	
	reg32 r3(data_writeReg, r3out, clock, r3en,ctrl_reset);
	reg32 r4(data_writeReg, r4out, clock, r4en,ctrl_reset);
	reg32 r5(data_writeReg, r5out, clock, r5en,ctrl_reset);
	reg32 r6(data_writeReg, r6out, clock, r6en,ctrl_reset);
	reg32 r7(data_writeReg, r7out, clock, r7en,ctrl_reset);
	reg32 r8(data_writeReg, r8out, clock, r8en,ctrl_reset);
	reg32 r9(data_writeReg, r9out, clock, r9en,ctrl_reset);
	reg32 r10(data_writeReg, r10out, clock, r10en,ctrl_reset);
	reg32 r11(data_writeReg, r11out, clock, r11en,ctrl_reset);
	reg32 r12(data_writeReg, r12out, clock, r12en,ctrl_reset);	
	reg32 r13(data_writeReg, r13out, clock, r13en,ctrl_reset);
	reg32 r14(data_writeReg, r14out, clock, r14en,ctrl_reset);
	reg32 r15(data_writeReg, r15out, clock, r15en,ctrl_reset);
	reg32 r16(data_writeReg, r16out, clock, r16en,ctrl_reset);
	reg32 r17(data_writeReg, r17out, clock, r17en,ctrl_reset);
	reg32 r18(data_writeReg, r18out, clock, r18en,ctrl_reset);
	reg32 r19(data_writeReg, r19out, clock, r19en,ctrl_reset);
	reg32 r20(data_writeReg, r20out, clock, r20en,ctrl_reset);
	reg32 r21(data_writeReg, r21out, clock, r21en,ctrl_reset);
	reg32 r22(data_writeReg, r22out, clock, r22en,ctrl_reset);	
	reg32 r23(data_writeReg, r23out, clock, r23en,ctrl_reset);
	reg32 r24(data_writeReg, r24out, clock, r24en,ctrl_reset);
	reg32 r25(data_writeReg, r25out, clock, r25en,ctrl_reset);
	reg32 r26(data_writeReg, r26out, clock, r26en,ctrl_reset);
	reg32 r27(data_writeReg, r27out, clock, r27en,ctrl_reset);
	reg32 r28(data_writeReg, r28out, clock, r28en,ctrl_reset);
	reg32 r29(data_writeReg, r29out, clock, r29en,ctrl_reset);
	reg32 r30(data_writeReg, r30out, clock, r30en,ctrl_reset);
	reg32 r31(data_writeReg, r31out, clock, r31en,ctrl_reset);

	tristate32 tA0(r0out, readADecode[0], data_readRegA);
	tristate32 tA1(r1out, readADecode[1], data_readRegA);
	tristate32 tA2(r2out, readADecode[2], data_readRegA);
	tristate32 tA3(r3out, readADecode[3], data_readRegA);
	tristate32 tA4(r4out, readADecode[4], data_readRegA);
	tristate32 tA5(r5out, readADecode[5], data_readRegA);
	tristate32 tA6(r6out, readADecode[6], data_readRegA);
	tristate32 tA7(r7out, readADecode[7], data_readRegA);
	tristate32 tA8(r8out, readADecode[8], data_readRegA);
	tristate32 tA9(r9out, readADecode[9], data_readRegA);
	tristate32 tA10(r10out, readADecode[10], data_readRegA);
	tristate32 tA11(r11out, readADecode[11], data_readRegA);
	tristate32 tA12(r12out, readADecode[12], data_readRegA);
	tristate32 tA13(r13out, readADecode[13], data_readRegA);
	tristate32 tA14(r14out, readADecode[14], data_readRegA);
	tristate32 tA15(r15out, readADecode[15], data_readRegA);
	tristate32 tA16(r16out, readADecode[16], data_readRegA);
	tristate32 tA17(r17out, readADecode[17], data_readRegA);
	tristate32 tA18(r18out, readADecode[18], data_readRegA);
	tristate32 tA19(r19out, readADecode[19], data_readRegA);
	tristate32 tA20(r20out, readADecode[20], data_readRegA);
	tristate32 tA21(r21out, readADecode[21], data_readRegA);
	tristate32 tA22(r22out, readADecode[22], data_readRegA);
	tristate32 tA23(r23out, readADecode[23], data_readRegA);
	tristate32 tA24(r24out, readADecode[24], data_readRegA);
	tristate32 tA25(r25out, readADecode[25], data_readRegA);
	tristate32 tA26(r26out, readADecode[26], data_readRegA);
	tristate32 tA27(r27out, readADecode[27], data_readRegA);
	tristate32 tA28(r28out, readADecode[28], data_readRegA);
	tristate32 tA29(r29out, readADecode[29], data_readRegA);
	tristate32 tA30(r30out, readADecode[30], data_readRegA);
	tristate32 tA31(r31out, readADecode[31], data_readRegA);

	tristate32 tB0(r0out, readBDecode[0], data_readRegB);
	tristate32 tB1(r1out, readBDecode[1], data_readRegB);
	tristate32 tB2(r2out, readBDecode[2], data_readRegB);
	tristate32 tB3(r3out, readBDecode[3], data_readRegB);
	tristate32 tB4(r4out, readBDecode[4], data_readRegB);
	tristate32 tB5(r5out, readBDecode[5], data_readRegB);
	tristate32 tB6(r6out, readBDecode[6], data_readRegB);
	tristate32 tB7(r7out, readBDecode[7], data_readRegB);
	tristate32 tB8(r8out, readBDecode[8], data_readRegB);
	tristate32 tB9(r9out, readBDecode[9], data_readRegB);
	tristate32 tB10(r10out, readBDecode[10], data_readRegB);
	tristate32 tB11(r11out, readBDecode[11], data_readRegB);
	tristate32 tB12(r12out, readBDecode[12], data_readRegB);
	tristate32 tB13(r13out, readBDecode[13], data_readRegB);
	tristate32 tB14(r14out, readBDecode[14], data_readRegB);
	tristate32 tB15(r15out, readBDecode[15], data_readRegB);
	tristate32 tB16(r16out, readBDecode[16], data_readRegB);
	tristate32 tB17(r17out, readBDecode[17], data_readRegB);
	tristate32 tB18(r18out, readBDecode[18], data_readRegB);
	tristate32 tB19(r19out, readBDecode[19], data_readRegB);
	tristate32 tB20(r20out, readBDecode[20], data_readRegB);
	tristate32 tB21(r21out, readBDecode[21], data_readRegB);
	tristate32 tB22(r22out, readBDecode[22], data_readRegB);
	tristate32 tB23(r23out, readBDecode[23], data_readRegB);
	tristate32 tB24(r24out, readBDecode[24], data_readRegB);
	tristate32 tB25(r25out, readBDecode[25], data_readRegB);
	tristate32 tB26(r26out, readBDecode[26], data_readRegB);
	tristate32 tB27(r27out, readBDecode[27], data_readRegB);
	tristate32 tB28(r28out, readBDecode[28], data_readRegB);
	tristate32 tB29(r29out, readBDecode[29], data_readRegB);
	tristate32 tB30(r30out, readBDecode[30], data_readRegB);
	tristate32 tB31(r31out, readBDecode[31], data_readRegB);


endmodule
