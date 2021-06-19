package com.esotericsoftware.asm;

final class Frame {
   static final int[] a;
   Label b;
   int[] c;
   int[] d;
   private int[] e;
   private int[] f;
   private int g;
   private int h;
   private int[] i;

   private int a(int var1) {
      if (this.e != null && var1 < this.e.length) {
         int var2 = this.e[var1];
         if (var2 == 0) {
            var2 = this.e[var1] = 33554432 | var1;
         }

         return var2;
      } else {
         return 33554432 | var1;
      }
   }

   private void a(int var1, int var2) {
      if (this.e == null) {
         this.e = new int[10];
      }

      int var3 = this.e.length;
      if (var1 >= var3) {
         int[] var4 = new int[Math.max(var1 + 1, 2 * var3)];
         System.arraycopy(this.e, 0, var4, 0, var3);
         this.e = var4;
      }

      this.e[var1] = var2;
   }

   private void b(int var1) {
      if (this.f == null) {
         this.f = new int[10];
      }

      int var2 = this.f.length;
      if (this.g >= var2) {
         int[] var3 = new int[Math.max(this.g + 1, 2 * var2)];
         System.arraycopy(this.f, 0, var3, 0, var2);
         this.f = var3;
      }

      this.f[this.g++] = var1;
      int var4 = this.b.f + this.g;
      if (var4 > this.b.g) {
         this.b.g = var4;
      }

   }

   private void a(ClassWriter var1, String var2) {
      int var3 = b(var1, var2);
      if (var3 != 0) {
         this.b(var3);
         if (var3 == 16777220 || var3 == 16777219) {
            this.b(16777216);
         }
      }

   }

   private static int b(ClassWriter var0, String var1) {
      int var2 = var1.charAt(0) == '(' ? var1.indexOf(41) + 1 : 0;
      String var3;
      switch(var1.charAt(var2)) {
      case 'B':
      case 'C':
      case 'I':
      case 'S':
      case 'Z':
         return 16777217;
      case 'D':
         return 16777219;
      case 'E':
      case 'G':
      case 'H':
      case 'K':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'T':
      case 'U':
      case 'W':
      case 'X':
      case 'Y':
      default:
         int var4;
         for(var4 = var2 + 1; var1.charAt(var4) == '['; ++var4) {
         }

         int var5;
         switch(var1.charAt(var4)) {
         case 'B':
            var5 = 16777226;
            break;
         case 'C':
            var5 = 16777227;
            break;
         case 'D':
            var5 = 16777219;
            break;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            var3 = var1.substring(var4 + 1, var1.length() - 1);
            var5 = 24117248 | var0.c(var3);
            break;
         case 'F':
            var5 = 16777218;
            break;
         case 'I':
            var5 = 16777217;
            break;
         case 'J':
            var5 = 16777220;
            break;
         case 'S':
            var5 = 16777228;
            break;
         case 'Z':
            var5 = 16777225;
         }

         return var4 - var2 << 28 | var5;
      case 'F':
         return 16777218;
      case 'J':
         return 16777220;
      case 'L':
         var3 = var1.substring(var2 + 1, var1.length() - 1);
         return 24117248 | var0.c(var3);
      case 'V':
         return 0;
      }
   }

   private int a() {
      return this.g > 0 ? this.f[--this.g] : 50331648 | -(--this.b.f);
   }

   private void c(int var1) {
      if (this.g >= var1) {
         this.g -= var1;
      } else {
         Label var10000 = this.b;
         var10000.f -= var1 - this.g;
         this.g = 0;
      }

   }

   private void a(String var1) {
      char var2 = var1.charAt(0);
      if (var2 == '(') {
         this.c((Type.getArgumentsAndReturnSizes(var1) >> 2) - 1);
      } else if (var2 != 'J' && var2 != 'D') {
         this.c(1);
      } else {
         this.c(2);
      }

   }

   private void d(int var1) {
      if (this.i == null) {
         this.i = new int[2];
      }

      int var2 = this.i.length;
      if (this.h >= var2) {
         int[] var3 = new int[Math.max(this.h + 1, 2 * var2)];
         System.arraycopy(this.i, 0, var3, 0, var2);
         this.i = var3;
      }

      this.i[this.h++] = var1;
   }

   private int a(ClassWriter var1, int var2) {
      int var3;
      if (var2 == 16777222) {
         var3 = 24117248 | var1.c(var1.I);
      } else {
         if ((var2 & -1048576) != 25165824) {
            return var2;
         }

         String var4 = var1.H[var2 & 1048575].g;
         var3 = 24117248 | var1.c(var4);
      }

      for(int var8 = 0; var8 < this.h; ++var8) {
         int var5 = this.i[var8];
         int var6 = var5 & -268435456;
         int var7 = var5 & 251658240;
         if (var7 == 33554432) {
            var5 = var6 + this.c[var5 & 8388607];
         } else if (var7 == 50331648) {
            var5 = var6 + this.d[this.d.length - (var5 & 8388607)];
         }

         if (var2 == var5) {
            return var3;
         }
      }

      return var2;
   }

   void a(ClassWriter var1, int var2, Type[] var3, int var4) {
      this.c = new int[var4];
      this.d = new int[0];
      int var5 = 0;
      if ((var2 & 8) == 0) {
         if ((var2 & 524288) == 0) {
            this.c[var5++] = 24117248 | var1.c(var1.I);
         } else {
            this.c[var5++] = 16777222;
         }
      }

      for(int var6 = 0; var6 < var3.length; ++var6) {
         int var7 = b(var1, var3[var6].getDescriptor());
         this.c[var5++] = var7;
         if (var7 == 16777220 || var7 == 16777219) {
            this.c[var5++] = 16777216;
         }
      }

      while(var5 < var4) {
         this.c[var5++] = 16777216;
      }

   }

   void a(int var1, int var2, ClassWriter var3, Item var4) {
      int var5;
      int var6;
      int var7;
      String var9;
      switch(var1) {
      case 0:
      case 116:
      case 117:
      case 118:
      case 119:
      case 145:
      case 146:
      case 147:
      case 167:
      case 177:
         break;
      case 1:
         this.b(16777221);
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 16:
      case 17:
      case 21:
         this.b(16777217);
         break;
      case 9:
      case 10:
      case 22:
         this.b(16777220);
         this.b(16777216);
         break;
      case 11:
      case 12:
      case 13:
      case 23:
         this.b(16777218);
         break;
      case 14:
      case 15:
      case 24:
         this.b(16777219);
         this.b(16777216);
         break;
      case 18:
         switch(var4.b) {
         case 3:
            this.b(16777217);
            return;
         case 4:
            this.b(16777218);
            return;
         case 5:
            this.b(16777220);
            this.b(16777216);
            return;
         case 6:
            this.b(16777219);
            this.b(16777216);
            return;
         case 7:
            this.b(24117248 | var3.c("java/lang/Class"));
            return;
         case 8:
            this.b(24117248 | var3.c("java/lang/String"));
            return;
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            this.b(24117248 | var3.c("java/lang/invoke/MethodHandle"));
            return;
         case 16:
            this.b(24117248 | var3.c("java/lang/invoke/MethodType"));
            return;
         }
      case 19:
      case 20:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
      case 76:
      case 77:
      case 78:
      case 196:
      case 197:
      default:
         this.c(var2);
         this.a(var3, var4.g);
         break;
      case 25:
         this.b(this.a(var2));
         break;
      case 46:
      case 51:
      case 52:
      case 53:
         this.c(2);
         this.b(16777217);
         break;
      case 47:
      case 143:
         this.c(2);
         this.b(16777220);
         this.b(16777216);
         break;
      case 48:
         this.c(2);
         this.b(16777218);
         break;
      case 49:
      case 138:
         this.c(2);
         this.b(16777219);
         this.b(16777216);
         break;
      case 50:
         this.c(1);
         var5 = this.a();
         this.b(-268435456 + var5);
         break;
      case 54:
      case 56:
      case 58:
         var5 = this.a();
         this.a(var2, var5);
         if (var2 > 0) {
            var6 = this.a(var2 - 1);
            if (var6 != 16777220 && var6 != 16777219) {
               if ((var6 & 251658240) != 16777216) {
                  this.a(var2 - 1, var6 | 8388608);
               }
            } else {
               this.a(var2 - 1, 16777216);
            }
         }
         break;
      case 55:
      case 57:
         this.c(1);
         var5 = this.a();
         this.a(var2, var5);
         this.a(var2 + 1, 16777216);
         if (var2 > 0) {
            var6 = this.a(var2 - 1);
            if (var6 != 16777220 && var6 != 16777219) {
               if ((var6 & 251658240) != 16777216) {
                  this.a(var2 - 1, var6 | 8388608);
               }
            } else {
               this.a(var2 - 1, 16777216);
            }
         }
         break;
      case 79:
      case 81:
      case 83:
      case 84:
      case 85:
      case 86:
         this.c(3);
         break;
      case 80:
      case 82:
         this.c(4);
         break;
      case 87:
      case 153:
      case 154:
      case 155:
      case 156:
      case 157:
      case 158:
      case 170:
      case 171:
      case 172:
      case 174:
      case 176:
      case 191:
      case 194:
      case 195:
      case 198:
      case 199:
         this.c(1);
         break;
      case 88:
      case 159:
      case 160:
      case 161:
      case 162:
      case 163:
      case 164:
      case 165:
      case 166:
      case 173:
      case 175:
         this.c(2);
         break;
      case 89:
         var5 = this.a();
         this.b(var5);
         this.b(var5);
         break;
      case 90:
         var5 = this.a();
         var6 = this.a();
         this.b(var5);
         this.b(var6);
         this.b(var5);
         break;
      case 91:
         var5 = this.a();
         var6 = this.a();
         var7 = this.a();
         this.b(var5);
         this.b(var7);
         this.b(var6);
         this.b(var5);
         break;
      case 92:
         var5 = this.a();
         var6 = this.a();
         this.b(var6);
         this.b(var5);
         this.b(var6);
         this.b(var5);
         break;
      case 93:
         var5 = this.a();
         var6 = this.a();
         var7 = this.a();
         this.b(var6);
         this.b(var5);
         this.b(var7);
         this.b(var6);
         this.b(var5);
         break;
      case 94:
         var5 = this.a();
         var6 = this.a();
         var7 = this.a();
         int var8 = this.a();
         this.b(var6);
         this.b(var5);
         this.b(var8);
         this.b(var7);
         this.b(var6);
         this.b(var5);
         break;
      case 95:
         var5 = this.a();
         var6 = this.a();
         this.b(var5);
         this.b(var6);
         break;
      case 96:
      case 100:
      case 104:
      case 108:
      case 112:
      case 120:
      case 122:
      case 124:
      case 126:
      case 128:
      case 130:
      case 136:
      case 142:
      case 149:
      case 150:
         this.c(2);
         this.b(16777217);
         break;
      case 97:
      case 101:
      case 105:
      case 109:
      case 113:
      case 127:
      case 129:
      case 131:
         this.c(4);
         this.b(16777220);
         this.b(16777216);
         break;
      case 98:
      case 102:
      case 106:
      case 110:
      case 114:
      case 137:
      case 144:
         this.c(2);
         this.b(16777218);
         break;
      case 99:
      case 103:
      case 107:
      case 111:
      case 115:
         this.c(4);
         this.b(16777219);
         this.b(16777216);
         break;
      case 121:
      case 123:
      case 125:
         this.c(3);
         this.b(16777220);
         this.b(16777216);
         break;
      case 132:
         this.a(var2, 16777217);
         break;
      case 133:
      case 140:
         this.c(1);
         this.b(16777220);
         this.b(16777216);
         break;
      case 134:
         this.c(1);
         this.b(16777218);
         break;
      case 135:
      case 141:
         this.c(1);
         this.b(16777219);
         this.b(16777216);
         break;
      case 139:
      case 190:
      case 193:
         this.c(1);
         this.b(16777217);
         break;
      case 148:
      case 151:
      case 152:
         this.c(4);
         this.b(16777217);
         break;
      case 168:
      case 169:
         throw new RuntimeException("JSR/RET are not supported with computeFrames option");
      case 178:
         this.a(var3, var4.i);
         break;
      case 179:
         this.a(var4.i);
         break;
      case 180:
         this.c(1);
         this.a(var3, var4.i);
         break;
      case 181:
         this.a(var4.i);
         this.a();
         break;
      case 182:
      case 183:
      case 184:
      case 185:
         this.a(var4.i);
         if (var1 != 184) {
            var5 = this.a();
            if (var1 == 183 && var4.h.charAt(0) == '<') {
               this.d(var5);
            }
         }

         this.a(var3, var4.i);
         break;
      case 186:
         this.a(var4.h);
         this.a(var3, var4.h);
         break;
      case 187:
         this.b(25165824 | var3.a(var4.g, var2));
         break;
      case 188:
         this.a();
         switch(var2) {
         case 4:
            this.b(285212681);
            return;
         case 5:
            this.b(285212683);
            return;
         case 6:
            this.b(285212674);
            return;
         case 7:
            this.b(285212675);
            return;
         case 8:
            this.b(285212682);
            return;
         case 9:
            this.b(285212684);
            return;
         case 10:
            this.b(285212673);
            return;
         default:
            this.b(285212676);
            return;
         }
      case 189:
         var9 = var4.g;
         this.a();
         if (var9.charAt(0) == '[') {
            this.a(var3, '[' + var9);
         } else {
            this.b(292552704 | var3.c(var9));
         }
         break;
      case 192:
         var9 = var4.g;
         this.a();
         if (var9.charAt(0) == '[') {
            this.a(var3, var9);
         } else {
            this.b(24117248 | var3.c(var9));
         }
      }

   }

   boolean a(ClassWriter var1, Frame var2, int var3) {
      boolean var4 = false;
      int var5 = this.c.length;
      int var6 = this.d.length;
      if (var2.c == null) {
         var2.c = new int[var5];
         var4 = true;
      }

      int var7;
      int var8;
      int var9;
      int var10;
      int var11;
      for(var7 = 0; var7 < var5; ++var7) {
         if (this.e != null && var7 < this.e.length) {
            var8 = this.e[var7];
            if (var8 == 0) {
               var9 = this.c[var7];
            } else {
               var10 = var8 & -268435456;
               var11 = var8 & 251658240;
               if (var11 == 16777216) {
                  var9 = var8;
               } else {
                  if (var11 == 33554432) {
                     var9 = var10 + this.c[var8 & 8388607];
                  } else {
                     var9 = var10 + this.d[var6 - (var8 & 8388607)];
                  }

                  if ((var8 & 8388608) != 0 && (var9 == 16777220 || var9 == 16777219)) {
                     var9 = 16777216;
                  }
               }
            }
         } else {
            var9 = this.c[var7];
         }

         if (this.i != null) {
            var9 = this.a(var1, var9);
         }

         var4 |= a(var1, var9, var2.c, var7);
      }

      if (var3 > 0) {
         for(var7 = 0; var7 < var5; ++var7) {
            var9 = this.c[var7];
            var4 |= a(var1, var9, var2.c, var7);
         }

         if (var2.d == null) {
            var2.d = new int[1];
            var4 = true;
         }

         var4 |= a(var1, var3, (int[])var2.d, 0);
         return var4;
      } else {
         int var12 = this.d.length + this.b.f;
         if (var2.d == null) {
            var2.d = new int[var12 + this.g];
            var4 = true;
         }

         for(var7 = 0; var7 < var12; ++var7) {
            var9 = this.d[var7];
            if (this.i != null) {
               var9 = this.a(var1, var9);
            }

            var4 |= a(var1, var9, var2.d, var7);
         }

         for(var7 = 0; var7 < this.g; ++var7) {
            var8 = this.f[var7];
            var10 = var8 & -268435456;
            var11 = var8 & 251658240;
            if (var11 == 16777216) {
               var9 = var8;
            } else {
               if (var11 == 33554432) {
                  var9 = var10 + this.c[var8 & 8388607];
               } else {
                  var9 = var10 + this.d[var6 - (var8 & 8388607)];
               }

               if ((var8 & 8388608) != 0 && (var9 == 16777220 || var9 == 16777219)) {
                  var9 = 16777216;
               }
            }

            if (this.i != null) {
               var9 = this.a(var1, var9);
            }

            var4 |= a(var1, var9, var2.d, var12 + var7);
         }

         return var4;
      }
   }

   private static boolean a(ClassWriter var0, int var1, int[] var2, int var3) {
      int var4 = var2[var3];
      if (var4 == var1) {
         return false;
      } else {
         if ((var1 & 268435455) == 16777221) {
            if (var4 == 16777221) {
               return false;
            }

            var1 = 16777221;
         }

         if (var4 == 0) {
            var2[var3] = var1;
            return true;
         } else {
            int var5;
            if ((var4 & 267386880) != 24117248 && (var4 & -268435456) == 0) {
               if (var4 == 16777221) {
                  var5 = (var1 & 267386880) != 24117248 && (var1 & -268435456) == 0 ? 16777216 : var1;
               } else {
                  var5 = 16777216;
               }
            } else {
               if (var1 == 16777221) {
                  return false;
               }

               int var6;
               if ((var1 & -1048576) == (var4 & -1048576)) {
                  if ((var4 & 267386880) == 24117248) {
                     var5 = var1 & -268435456 | 24117248 | var0.a(var1 & 1048575, var4 & 1048575);
                  } else {
                     var6 = -268435456 + (var4 & -268435456);
                     var5 = var6 | 24117248 | var0.c("java/lang/Object");
                  }
               } else if ((var1 & 267386880) != 24117248 && (var1 & -268435456) == 0) {
                  var5 = 16777216;
               } else {
                  var6 = ((var1 & -268435456) != 0 && (var1 & 267386880) != 24117248 ? -268435456 : 0) + (var1 & -268435456);
                  int var7 = ((var4 & -268435456) != 0 && (var4 & 267386880) != 24117248 ? -268435456 : 0) + (var4 & -268435456);
                  var5 = Math.min(var6, var7) | 24117248 | var0.c("java/lang/Object");
               }
            }

            if (var4 != var5) {
               var2[var3] = var5;
               return true;
            } else {
               return false;
            }
         }
      }
   }

   static {
      _clinit_();
      int[] var0 = new int[202];
      String var1 = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE";

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var0[var2] = var1.charAt(var2) - 69;
      }

      a = var0;
   }

   // $FF: synthetic method
   static void _clinit_() {
   }
}
