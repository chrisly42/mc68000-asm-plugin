; this is a test

FOO = 1
<fold text='[[[ demo_main ]]]'>; this is the main demo routine
demo_main:
        moveq.l #0,d0
        rts

; data area starts here
.data   dc.w    10,0
        even</fold>

; this is an unrelated comment

<fold text='[[[ intro_part1 ]]]'>; this is another folding area
; could be anything.

intro_part1
        dc.w    $47fe
        illegal
        rts</fold>

; this comment is two lines away


<fold text='[[[ intro_part2 ]]]'>intro_part2:
        moveq.l #0,d1
        rts</fold>

; standard macros
<fold text='[[[ MACRO BLTWAIT ]]]'>BLTWAIT MACRO
.bw\@
        btst	#DMAB_BLTDONE-8,dmaconr(a5)
        bne.s	.bw\@
        ENDM</fold>

********************** foobar


<fold text='[[[ some_more_data ]]]'>some_more_data:
        dc.w    $123
        dc.w    $345
        dc.w    $333
        dc.w    $222</fold>

CUBE_SIZE = 100
