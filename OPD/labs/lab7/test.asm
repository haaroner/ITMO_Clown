ORG 0X20;  вход
  WORD 0X0000;
  WORD 0X1234;
  WORD 0XFFFF;
  word 0xabcd;
org 0x40
  word 0x0000
  word 0x091A
  word 0x7fff;
  word 0x55e6;

org 0x80
cnt1:  word 4
cnt2:  word 4
res:  word 0xffff
in_pointer:  word 0x20
res_pointer:  word 0x40
out_pointer:   word 0x60

START:  cla
  ld(in_pointer)+;
  word 0xf20;
  cmp(res_pointer)+;
  beq correct;
  ld #0;
  jump save_res
correct:  ld#1;
save_res:  st(out_pointer)+;
  loop cnt1;
  jump START;
checker:  cla
  ld -(out_pointer)
  cmp #1
  beq new_iter
  ld #0;
  st res;
  hlt;
new_iter:  loop cnt2;
  jump checker;
  ld #1
  st res
  hlt
