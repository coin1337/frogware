package com.esotericsoftware.asm;

public class ByteVector {
   byte[] a;
   int b;

   public ByteVector() {
      this.a = new byte[64];
   }

   public ByteVector(int var1) {
      this.a = new byte[var1];
   }

   public ByteVector putByte(int var1) {
      int var2 = this.b;
      if (var2 + 1 > this.a.length) {
         this.a(1);
      }

      this.a[var2++] = (byte)var1;
      this.b = var2;
      return this;
   }

   ByteVector a(int var1, int var2) {
      int var3 = this.b;
      if (var3 + 2 > this.a.length) {
         this.a(2);
      }

      byte[] var4 = this.a;
      var4[var3++] = (byte)var1;
      var4[var3++] = (byte)var2;
      this.b = var3;
      return this;
   }

   public ByteVector putShort(int var1) {
      int var2 = this.b;
      if (var2 + 2 > this.a.length) {
         this.a(2);
      }

      byte[] var3 = this.a;
      var3[var2++] = (byte)(var1 >>> 8);
      var3[var2++] = (byte)var1;
      this.b = var2;
      return this;
   }

   ByteVector b(int var1, int var2) {
      int var3 = this.b;
      if (var3 + 3 > this.a.length) {
         this.a(3);
      }

      byte[] var4 = this.a;
      var4[var3++] = (byte)var1;
      var4[var3++] = (byte)(var2 >>> 8);
      var4[var3++] = (byte)var2;
      this.b = var3;
      return this;
   }

   public ByteVector putInt(int var1) {
      int var2 = this.b;
      if (var2 + 4 > this.a.length) {
         this.a(4);
      }

      byte[] var3 = this.a;
      var3[var2++] = (byte)(var1 >>> 24);
      var3[var2++] = (byte)(var1 >>> 16);
      var3[var2++] = (byte)(var1 >>> 8);
      var3[var2++] = (byte)var1;
      this.b = var2;
      return this;
   }

   public ByteVector putLong(long var1) {
      int var3 = this.b;
      if (var3 + 8 > this.a.length) {
         this.a(8);
      }

      byte[] var4 = this.a;
      int var5 = (int)(var1 >>> 32);
      var4[var3++] = (byte)(var5 >>> 24);
      var4[var3++] = (byte)(var5 >>> 16);
      var4[var3++] = (byte)(var5 >>> 8);
      var4[var3++] = (byte)var5;
      var5 = (int)var1;
      var4[var3++] = (byte)(var5 >>> 24);
      var4[var3++] = (byte)(var5 >>> 16);
      var4[var3++] = (byte)(var5 >>> 8);
      var4[var3++] = (byte)var5;
      this.b = var3;
      return this;
   }

   public ByteVector putUTF8(String var1) {
      int var2 = var1.length();
      if (var2 > 65535) {
         throw new IllegalArgumentException();
      } else {
         int var3 = this.b;
         if (var3 + 2 + var2 > this.a.length) {
            this.a(2 + var2);
         }

         byte[] var4 = this.a;
         var4[var3++] = (byte)(var2 >>> 8);
         var4[var3++] = (byte)var2;

         for(int var5 = 0; var5 < var2; ++var5) {
            char var6 = var1.charAt(var5);
            if (var6 < 1 || var6 > 127) {
               this.b = var3;
               return this.c(var1, var5, 65535);
            }

            var4[var3++] = (byte)var6;
         }

         this.b = var3;
         return this;
      }
   }

   ByteVector c(String var1, int var2, int var3) {
      int var4 = var1.length();
      int var5 = var2;

      int var6;
      char var7;
      for(var6 = var2; var6 < var4; ++var6) {
         var7 = var1.charAt(var6);
         if (var7 >= 1 && var7 <= 127) {
            ++var5;
         } else if (var7 > 2047) {
            var5 += 3;
         } else {
            var5 += 2;
         }
      }

      if (var5 > var3) {
         throw new IllegalArgumentException();
      } else {
         var6 = this.b - var2 - 2;
         if (var6 >= 0) {
            this.a[var6] = (byte)(var5 >>> 8);
            this.a[var6 + 1] = (byte)var5;
         }

         if (this.b + var5 - var2 > this.a.length) {
            this.a(var5 - var2);
         }

         int var8 = this.b;

         for(int var9 = var2; var9 < var4; ++var9) {
            var7 = var1.charAt(var9);
            if (var7 >= 1 && var7 <= 127) {
               this.a[var8++] = (byte)var7;
            } else if (var7 > 2047) {
               this.a[var8++] = (byte)(224 | var7 >> 12 & 15);
               this.a[var8++] = (byte)(128 | var7 >> 6 & 63);
               this.a[var8++] = (byte)(128 | var7 & 63);
            } else {
               this.a[var8++] = (byte)(192 | var7 >> 6 & 31);
               this.a[var8++] = (byte)(128 | var7 & 63);
            }
         }

         this.b = var8;
         return this;
      }
   }

   public ByteVector putByteArray(byte[] var1, int var2, int var3) {
      if (this.b + var3 > this.a.length) {
         this.a(var3);
      }

      if (var1 != null) {
         System.arraycopy(var1, var2, this.a, this.b, var3);
      }

      this.b += var3;
      return this;
   }

   private void a(int var1) {
      int var2 = 2 * this.a.length;
      int var3 = this.b + var1;
      byte[] var4 = new byte[var2 > var3 ? var2 : var3];
      System.arraycopy(this.a, 0, var4, 0, this.b);
      this.a = var4;
   }
}
