nop 	
nop 
nop
nop
nop
addi    $r1, $r0, 4    
addi    $r2, $r0, 5    
nop
nop
sub     $r3, $r0, $r1 
sub     $r4, $r0, $r2   
nop
nop
nop 	
addi	$r31, $r0, 100	
jal 	j2	
addi 	$r20, $r20, 1	
addi 	$r20, $r20, 1	
addi 	$r20, $r20, 1	
j2:
addi	$r10, $r10, 1	
nop
add	    $r10, $r10, $r31 
nop
nop
nop