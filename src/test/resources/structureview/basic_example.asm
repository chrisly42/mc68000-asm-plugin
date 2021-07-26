PIC_WIDTH = 320
PIC_HEIGHT equ 256
DEBUG_LEVEL set 10

entry:
        bsr     init
        bsr     main
        bsr     exit
        rts

init
        move.w  #PIC_HEIGHT,d1
.looph  move.w  #PIC_WIDTH,d0
.loopw  clr.b   (a0)+
        subq.w  #1,d0
        bne.s   .loopw
        subq.w  #1,d1
        bne.s   .looph
        rts

main    moveq.l #0,d0
        rts

exit    illegal
        rts