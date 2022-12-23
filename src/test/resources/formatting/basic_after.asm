; this is a test

FOO = 1
; this is the main demo routine
demo_main:
        moveq.l #0,d0       ; end of line comment
        rts ; some more comments

; data area starts here
.data   dc.w    10,0
        even

this_is_a_very_long_label: moveq.l #0,d0 ; and an end of line comment, too

; this is another folding area
; could be anything.

intro_part1
        ; What is this doing here?
        dc.w    $47fe
        illegal
        rts

; this comment is two lines away


intro_part2:
        moveq.l #0,d1
        rts

; standard macros
BLTWAIT MACRO
.bw\@
        btst	#DMAB_BLTDONE-8,dmaconr(a5)
        bne.s	.bw\@
        ENDM

        MACRO BLTHOGOFF
        moveq.l #0,d0
        ENDM

********************** foobar


some_more_data:
        dc.w    $123
        dc.w    $345
        dc.w    $333
        dc.w    $222

CUBE_SIZE = 100
