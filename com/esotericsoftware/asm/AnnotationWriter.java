package com.esotericsoftware.asm;

final class AnnotationWriter extends AnnotationVisitor {
   private final ClassWriter a;
   private int b;
   private final boolean c;
   private final ByteVector d;
   private final ByteVector e;
   private final int f;
   AnnotationWriter g;
   AnnotationWriter h;

   AnnotationWriter(ClassWriter var1, boolean var2, ByteVector var3, ByteVector var4, int var5) {
      super(327680);
      this.a = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
   }

   public void visit(String var1, Object var2) {
      ++this.b;
      if (this.c) {
         this.d.putShort(this.a.newUTF8(var1));
      }

      if (var2 instanceof String) {
         this.d.b(115, this.a.newUTF8((String)var2));
      } else if (var2 instanceof Byte) {
         this.d.b(66, this.a.a((Byte)var2).a);
      } else if (var2 instanceof Boolean) {
         int var3 = (Boolean)var2 ? 1 : 0;
         this.d.b(90, this.a.a(var3).a);
      } else if (var2 instanceof Character) {
         this.d.b(67, this.a.a((Character)var2).a);
      } else if (var2 instanceof Short) {
         this.d.b(83, this.a.a((Short)var2).a);
      } else if (var2 instanceof Type) {
         this.d.b(99, this.a.newUTF8(((Type)var2).getDescriptor()));
      } else {
         int var4;
         if (var2 instanceof byte[]) {
            byte[] var5 = (byte[])((byte[])var2);
            this.d.b(91, var5.length);

            for(var4 = 0; var4 < var5.length; ++var4) {
               this.d.b(66, this.a.a(var5[var4]).a);
            }
         } else if (var2 instanceof boolean[]) {
            boolean[] var6 = (boolean[])((boolean[])var2);
            this.d.b(91, var6.length);

            for(var4 = 0; var4 < var6.length; ++var4) {
               this.d.b(90, this.a.a(var6[var4] ? 1 : 0).a);
            }
         } else if (var2 instanceof short[]) {
            short[] var7 = (short[])((short[])var2);
            this.d.b(91, var7.length);

            for(var4 = 0; var4 < var7.length; ++var4) {
               this.d.b(83, this.a.a(var7[var4]).a);
            }
         } else if (var2 instanceof char[]) {
            char[] var8 = (char[])((char[])var2);
            this.d.b(91, var8.length);

            for(var4 = 0; var4 < var8.length; ++var4) {
               this.d.b(67, this.a.a(var8[var4]).a);
            }
         } else if (var2 instanceof int[]) {
            int[] var9 = (int[])((int[])var2);
            this.d.b(91, var9.length);

            for(var4 = 0; var4 < var9.length; ++var4) {
               this.d.b(73, this.a.a(var9[var4]).a);
            }
         } else if (var2 instanceof long[]) {
            long[] var10 = (long[])((long[])var2);
            this.d.b(91, var10.length);

            for(var4 = 0; var4 < var10.length; ++var4) {
               this.d.b(74, this.a.a(var10[var4]).a);
            }
         } else if (var2 instanceof float[]) {
            float[] var11 = (float[])((float[])var2);
            this.d.b(91, var11.length);

            for(var4 = 0; var4 < var11.length; ++var4) {
               this.d.b(70, this.a.a(var11[var4]).a);
            }
         } else if (var2 instanceof double[]) {
            double[] var12 = (double[])((double[])var2);
            this.d.b(91, var12.length);

            for(var4 = 0; var4 < var12.length; ++var4) {
               this.d.b(68, this.a.a(var12[var4]).a);
            }
         } else {
            Item var13 = this.a.a(var2);
            this.d.b(".s.IFJDCS".charAt(var13.b), var13.a);
         }
      }

   }

   public void visitEnum(String var1, String var2, String var3) {
      ++this.b;
      if (this.c) {
         this.d.putShort(this.a.newUTF8(var1));
      }

      this.d.b(101, this.a.newUTF8(var2)).putShort(this.a.newUTF8(var3));
   }

   public AnnotationVisitor visitAnnotation(String var1, String var2) {
      ++this.b;
      if (this.c) {
         this.d.putShort(this.a.newUTF8(var1));
      }

      this.d.b(64, this.a.newUTF8(var2)).putShort(0);
      return new AnnotationWriter(this.a, true, this.d, this.d, this.d.b - 2);
   }

   public AnnotationVisitor visitArray(String var1) {
      ++this.b;
      if (this.c) {
         this.d.putShort(this.a.newUTF8(var1));
      }

      this.d.b(91, 0);
      return new AnnotationWriter(this.a, false, this.d, this.d, this.d.b - 2);
   }

   public void visitEnd() {
      if (this.e != null) {
         byte[] var1 = this.e.a;
         var1[this.f] = (byte)(this.b >>> 8);
         var1[this.f + 1] = (byte)this.b;
      }

   }

   int a() {
      int var1 = 0;

      for(AnnotationWriter var2 = this; var2 != null; var2 = var2.g) {
         var1 += var2.d.b;
      }

      return var1;
   }

   void a(ByteVector var1) {
      int var2 = 0;
      int var3 = 2;
      AnnotationWriter var4 = this;

      AnnotationWriter var5;
      for(var5 = null; var4 != null; var4 = var4.g) {
         ++var2;
         var3 += var4.d.b;
         var4.visitEnd();
         var4.h = var5;
         var5 = var4;
      }

      var1.putInt(var3);
      var1.putShort(var2);

      for(var4 = var5; var4 != null; var4 = var4.h) {
         var1.putByteArray(var4.d.a, 0, var4.d.b);
      }

   }

   static void a(AnnotationWriter[] var0, int var1, ByteVector var2) {
      int var3 = 1 + 2 * (var0.length - var1);

      int var4;
      for(var4 = var1; var4 < var0.length; ++var4) {
         var3 += var0[var4] == null ? 0 : var0[var4].a();
      }

      var2.putInt(var3).putByte(var0.length - var1);

      for(var4 = var1; var4 < var0.length; ++var4) {
         AnnotationWriter var5 = var0[var4];
         AnnotationWriter var6 = null;

         int var7;
         for(var7 = 0; var5 != null; var5 = var5.g) {
            ++var7;
            var5.visitEnd();
            var5.h = var6;
            var6 = var5;
         }

         var2.putShort(var7);

         for(var5 = var6; var5 != null; var5 = var5.h) {
            var2.putByteArray(var5.d.a, 0, var5.d.b);
         }
      }

   }

   static void a(int var0, TypePath var1, ByteVector var2) {
      switch(var0 >>> 24) {
      case 0:
      case 1:
      case 22:
         var2.putShort(var0 >>> 16);
         break;
      case 19:
      case 20:
      case 21:
         var2.putByte(var0 >>> 24);
         break;
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
         var2.putInt(var0);
         break;
      default:
         var2.b(var0 >>> 24, (var0 & 16776960) >> 8);
      }

      if (var1 == null) {
         var2.putByte(0);
      } else {
         int var3 = var1.a[var1.b] * 2 + 1;
         var2.putByteArray(var1.a, var1.b, var3);
      }

   }
}
