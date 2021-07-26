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
        move.w  a1,d2<caret>
        move.w  e<caret>
        move.w  ex<caret>
        move.w  PW<caret>
        move.w  A<caret>
        move.w  DEB<caret>
        move.w  A2<caret>
        move.w  D1<caret>
        rts

main    moveq.l #0,d0
        rts

exit    illegal
        rts