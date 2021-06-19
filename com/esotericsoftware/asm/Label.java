package com.esotericsoftware.asm;

public class Label {
   public Object info;
   int a;
   int b;
   int c;
   private int d;
   private int[] e;
   int f;
   int g;
   Frame h;
   Label i;
   Edge j;
   Label k;

   public int getOffset() {
      if ((this.a & 2) == 0) {
         throw new IllegalStateException("Label offset position has not been resolved yet");
      } else {
         return this.c;
      }
   }

   void a(MethodWriter var1, ByteVector var2, int var3, boolean var4) {
      if ((this.a & 2) == 0) {
         if (var4) {
            this.a(-1 - var3, var2.b);
            var2.putInt(-1);
         } else {
            this.a(var3, var2.b);
            var2.putShort(-1);
         }
      } else if (var4) {
         var2.putInt(this.c - var3);
      } else {
         var2.putShort(this.c - var3);
      }

   }

   private void a(int var1, int var2) {
      if (this.e == null) {
         this.e = new int[6];
      }

      if (this.d >= this.e.length) {
         int[] var3 = new int[this.e.length + 6];
         System.arraycopy(this.e, 0, var3, 0, this.e.length);
         this.e = var3;
      }

      this.e[this.d++] = var1;
      this.e[this.d++] = var2;
   }

   boolean a(MethodWriter var1, int var2, byte[] var3) {
      boolean var4 = false;
      this.a |= 2;
      this.c = var2;
      int var5 = 0;

      while(true) {
         while(var5 < this.d) {
            int var6 = this.e[var5++];
            int var7 = this.e[var5++];
            int var8;
            if (var6 >= 0) {
               var8 = var2 - var6;
               if (var8 < -32768 || var8 > 32767) {
                  int var9 = var3[var7 - 1] & 255;
                  if (var9 <= 168) {
                     var3[var7 - 1] = (byte)(var9 + 49);
                  } else {
                     var3[var7 - 1] = (byte)(var9 + 20);
                  }

                  var4 = true;
               }

               var3[var7++] = (byte)(var8 >>> 8);
               var3[var7] = (byte)var8;
            } else {
               var8 = var2 + var6 + 1;
               var3[var7++] = (byte)(var8 >>> 24);
               var3[var7++] = (byte)(var8 >>> 16);
               var3[var7++] = (byte)(var8 >>> 8);
               var3[var7] = (byte)var8;
            }
         }

         return var4;
      }
   }

   Label a() {
      return this.h == null ? this : this.h.b;
   }

   boolean a(long var1) {
      if ((this.a & 1024) != 0) {
         return (this.e[(int)(var1 >>> 32)] & (int)var1) != 0;
      } else {
         return false;
      }
   }

   boolean a(Label var1) {
      if ((this.a & 1024) != 0 && (var1.a & 1024) != 0) {
         for(int var2 = 0; var2 < this.e.length; ++var2) {
            if ((this.e[var2] & var1.e[var2]) != 0) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   void a(long var1, int var3) {
      if ((this.a & 1024) == 0) {
         this.a |= 1024;
         this.e = new int[var3 / 32 + 1];
      }

      int[] var10000 = this.e;
      var10000[(int)(var1 >>> 32)] |= (int)var1;
   }

   void b(Label var1, long var2, int var4) {
      Label var5 = this;

      while(true) {
         Label var6;
         Edge var7;
         while(true) {
            if (var5 == null) {
               return;
            }

            var6 = var5;
            var5 = var5.k;
            var6.k = null;
            if (var1 != null) {
               if ((var6.a & 2048) == 0) {
                  var6.a |= 2048;
                  if ((var6.a & 256) != 0 && !var6.a(var1)) {
                     var7 = new Edge();
                     var7.a = var6.f;
                     var7.b = var1.j.b;
                     var7.c = var6.j;
                     var6.j = var7;
                  }
                  break;
               }
            } else if (!var6.a(var2)) {
               var6.a(var2, var4);
               break;
            }
         }

         for(var7 = var6.j; var7 != null; var7 = var7.c) {
            if (((var6.a & 128) == 0 || var7 != var6.j.c) && var7.b.k == null) {
               var7.b.k = var5;
               var5 = var7.b;
            }
         }
      }
   }

   public String toString() {
      return "L" + System.identityHashCode(this);
   }
}
