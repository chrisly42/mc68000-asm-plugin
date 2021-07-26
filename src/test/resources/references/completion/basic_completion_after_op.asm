PIC_WIDTH = 320
PIC_HEIGHT equ 256
DEBUG_LEVEL set 10
DOUBLE_BUFFER_1 = 1
AUTO_COMPLETE_2 = 1
dood2 = 1

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
        move.w  a1,d2
        move.w  e
        move.w  exit
        move.w  PIC_WIDTH
        move.w  AUTO_COMPLETE_2
        move.w  DEBUG_LEVEL
        move.w  AUTO_COMPLETE_2
        move.w  DOUBLE_BUFFER_1
        rts

main    moveq.l #0,d0
        rts

exit    illegal
        rts