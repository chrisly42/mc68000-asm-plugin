FOO = 1234
BAR    =   1235

COUNTER SET COUNTER+1

FISH    MACRO
.fish\@ rts
    ENDM
      MACRO SOUP
        moveq.l #0,d0
        ENDM

; with space

; standalone comment

    moveq.l #10,d1; end of line comment
    FISH
 add.l #10,d1
 SOUP
  subq.b #2,d2   ; end of line comment
   bra.s      .foo
  nop
.foo move.l d2,d1
    rts
.verylonglabel: stop #$2000

.narf
    moveq.l #2,d0

globallabel: moveq.l #0,d0
