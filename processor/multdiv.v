module multdiv(
	data_operandA, data_operandB, 
	ctrl_MULT, ctrl_DIV, 
	clock, 
	data_result, data_exception, data_resultRDY);

    input [31:0] data_operandA, data_operandB;
    input ctrl_MULT, ctrl_DIV, clock;

    output [31:0] data_result;
    output data_exception, data_resultRDY;
    wire mult_or_div; 

    dffe mulordiv(mult_or_div, 1'b1, ctrl_DIV, 1'b1, ctrl_MULT);

    wire [63:0] multResult; 
    wire [31:0] multFinalResult; 
    wire multReady, multException; 

    boothmult boothmult1(data_operandA, data_operandB, ctrl_MULT, clock, multResult, multReady);
    multOvf mOvf(multException, multResult, data_operandA, data_operandB); 

    
    assign multFinalResult = multResult[31:0];


    wire [31:0] preNegateResult, negatedResult, negatedDivisor, negatedDividend, divisor, dividend, divResult, divFinalResult;
    wire negateQuot, divException, divReady; 

    negate32 n1(data_operandA, negatedDividend);
    negate32 n2(data_operandB, negatedDivisor); 

    assign divisor = data_operandB[31] ? negatedDivisor : data_operandB;
    assign dividend =  data_operandA[31] ? negatedDividend : data_operandA; 


    xor(negateQuot, data_operandA[31], data_operandB[31]); 

    div div1(dividend, divisor, ctrl_DIV, clock, preNegateResult, divReady); 
    
    equals0 eq1(divException, data_operandB); 


    
    negate32 n32(preNegateResult,negatedResult);

    assign divResult = negateQuot ? negatedResult : preNegateResult; 
    assign divFinalResult = divException ? 32'b0 : divResult;   

    assign data_result = mult_or_div ? divFinalResult : multFinalResult; 
    assign data_exception = mult_or_div ? divException : multException; 
    assign data_resultRDY = mult_or_div ? divReady : multReady; 



endmodule