PIC_WIDTH = 320
PIC_HEIGTH equ 256

        bsr     init
        bsr     main
        bsr     exit
        rts

init    move.w  #PIC_WIDTH,d0
        move.w  #PIC_<caret>HEIGTH,d1
        rts

main    moveq.l #0,d0
        rts

exit    illegal
        rts