package com.esotericsoftware.asm;

import java.io.IOException;
import java.io.InputStream;

public class ClassReader {
   public static final int SKIP_CODE = 1;
   public static final int SKIP_DEBUG = 2;
   public static final int SKIP_FRAMES = 4;
   public static final int EXPAND_FRAMES = 8;
   public final byte[] b;
   private final int[] a;
   private final String[] c;
   private final int d;
   public final int header;

   public ClassReader(byte[] var1) {
      this(var1, 0, var1.length);
   }

   public ClassReader(byte[] var1, int var2, int var3) {
      this.b = var1;
      if (this.readShort(var2 + 6) > 52) {
         throw new IllegalArgumentException();
      } else {
         this.a = new int[this.readUnsignedShort(var2 + 8)];
         int var4 = this.a.length;
         this.c = new String[var4];
         int var5 = 0;
         int var6 = var2 + 10;

         for(int var7 = 1; var7 < var4; ++var7) {
            this.a[var7] = var6 + 1;
            int var8;
            switch(var1[var6]) {
            case 1:
               var8 = 3 + this.readUnsignedShort(var6 + 1);
               if (var8 > var5) {
                  var5 = var8;
               }
               break;
            case 2:
            case 7:
            case 8:
            case 13:
            case 14:
            case 16:
            case 17:
            default:
               var8 = 3;
               break;
            case 3:
            case 4:
            case 9:
            case 10:
            case 11:
            case 12:
            case 18:
               var8 = 5;
               break;
            case 5:
            case 6:
               var8 = 9;
               ++var7;
               break;
            case 15:
               var8 = 4;
            }

            var6 += var8;
         }

         this.d = var5;
         this.header = var6;
      }
   }

   public int getAccess() {
      return this.readUnsignedShort(this.header);
   }

   public String getClassName() {
      return this.readClass(this.header + 2, new char[this.d]);
   }

   public String getSuperName() {
      return this.readClass(this.header + 4, new char[this.d]);
   }

   public String[] getInterfaces() {
      int var1 = this.header + 6;
      int var2 = this.readUnsignedShort(var1);
      String[] var3 = new String[var2];
      if (var2 > 0) {
         char[] var4 = new char[this.d];

         for(int var5 = 0; var5 < var2; ++var5) {
            var1 += 2;
            var3[var5] = this.readClass(var1, var4);
         }
      }

      return var3;
   }

   void a(ClassWriter var1) {
      char[] var2 = new char[this.d];
      int var3 = this.a.length;
      Item[] var4 = new Item[var3];

      int var5;
      for(var5 = 1; var5 < var3; ++var5) {
         int var6 = this.a[var5];
         byte var7 = this.b[var6 - 1];
         Item var8 = new Item(var5);
         int var9;
         int var10;
         switch(var7) {
         case 1:
            String var11 = this.c[var5];
            if (var11 == null) {
               var6 = this.a[var5];
               var11 = this.c[var5] = this.a(var6 + 2, this.readUnsignedShort(var6), var2);
            }

            var8.a(var7, var11, (String)null, (String)null);
            break;
         case 2:
         case 7:
         case 8:
         case 13:
         case 14:
         case 16:
         case 17:
         default:
            var8.a(var7, this.readUTF8(var6, var2), (String)null, (String)null);
            break;
         case 3:
            var8.a(this.readInt(var6));
            break;
         case 4:
            var8.a(Float.intBitsToFloat(this.readInt(var6)));
            break;
         case 5:
            var8.a(this.readLong(var6));
            ++var5;
            break;
         case 6:
            var8.a(Double.longBitsToDouble(this.readLong(var6)));
            ++var5;
            break;
         case 9:
         case 10:
         case 11:
            var9 = this.a[this.readUnsignedShort(var6 + 2)];
            var8.a(var7, this.readClass(var6, var2), this.readUTF8(var9, var2), this.readUTF8(var9 + 2, var2));
            break;
         case 12:
            var8.a(var7, this.readUTF8(var6, var2), this.readUTF8(var6 + 2, var2), (String)null);
            break;
         case 15:
            var10 = this.a[this.readUnsignedShort(var6 + 1)];
            var9 = this.a[this.readUnsignedShort(var10 + 2)];
            var8.a(20 + this.readByte(var6), this.readClass(var10, var2), this.readUTF8(var9, var2), this.readUTF8(var9 + 2, var2));
            break;
         case 18:
            if (var1.A == null) {
               this.a(var1, var4, var2);
            }

            var9 = this.a[this.readUnsignedShort(var6 + 2)];
            var8.a(this.readUTF8(var9, var2), this.readUTF8(var9 + 2, var2), this.readUnsignedShort(var6));
         }

         var10 = var8.j % var4.length;
         var8.k = var4[var10];
         var4[var10] = var8;
      }

      var5 = this.a[1] - 1;
      var1.d.putByteArray(this.b, var5, this.header - var5);
      var1.e = var4;
      var1.f = (int)(0.75D * (double)var3);
      var1.c = var3;
   }

   private void a(ClassWriter var1, Item[] var2, char[] var3) {
      int var4 = this.a();
      boolean var5 = false;

      int var6;
      for(var6 = this.readUnsignedShort(var4); var6 > 0; --var6) {
         String var7 = this.readUTF8(var4 + 2, var3);
         if ("BootstrapMethods".equals(var7)) {
            var5 = true;
            break;
         }

         var4 += 6 + this.readInt(var4 + 4);
      }

      if (var5) {
         var6 = this.readUnsignedShort(var4 + 8);
         int var13 = 0;

         for(int var8 = var4 + 10; var13 < var6; ++var13) {
            int var9 = var8 - var4 - 10;
            int var10 = this.readConst(this.readUnsignedShort(var8), var3).hashCode();

            for(int var11 = this.readUnsignedShort(var8 + 2); var11 > 0; --var11) {
               var10 ^= this.readConst(this.readUnsignedShort(var8 + 4), var3).hashCode();
               var8 += 2;
            }

            var8 += 4;
            Item var15 = new Item(var13);
            var15.a(var9, var10 & Integer.MAX_VALUE);
            int var12 = var15.j % var2.length;
            var15.k = var2[var12];
            var2[var12] = var15;
         }

         var13 = this.readInt(var4 + 4);
         ByteVector var14 = new ByteVector(var13 + 62);
         var14.putByteArray(this.b, var4 + 10, var13 - 2);
         var1.z = var6;
         var1.A = var14;
      }
   }

   public ClassReader(InputStream var1) throws IOException {
      this(a(var1, false));
   }

   public ClassReader(String var1) throws IOException {
      this(a(ClassLoader.getSystemResourceAsStream(var1.replace('.', '/') + ".class"), true));
   }

   private static byte[] a(InputStream var0, boolean var1) throws IOException {
      if (var0 == null) {
         throw new IOException("Class not found");
      } else {
         try {
            byte[] var2 = new byte[var0.available()];
            int var3 = 0;

            while(true) {
               int var4 = var0.read(var2, var3, var2.length - var3);
               if (var4 == -1) {
                  byte[] var10;
                  if (var3 < var2.length) {
                     var10 = new byte[var3];
                     System.arraycopy(var2, 0, var10, 0, var3);
                     var2 = var10;
                  }

                  var10 = var2;
                  return var10;
               }

               var3 += var4;
               if (var3 == var2.length) {
                  int var5 = var0.read();
                  byte[] var6;
                  if (var5 < 0) {
                     var6 = var2;
                     return var6;
                  }

                  var6 = new byte[var2.length + 1000];
                  System.arraycopy(var2, 0, var6, 0, var3);
                  var6[var3++] = (byte)var5;
                  var2 = var6;
               }
            }
         } finally {
            if (var1) {
               var0.close();
            }

         }
      }
   }

   public void accept(ClassVisitor var1, int var2) {
      this.accept(var1, new Attribute[0], var2);
   }

   public void accept(ClassVisitor var1, Attribute[] var2, int var3) {
      int var4 = this.header;
      char[] var5 = new char[this.d];
      Context var6 = new Context();
      var6.a = var2;
      var6.b = var3;
      var6.c = var5;
      int var7 = this.readUnsignedShort(var4);
      String var8 = this.readClass(var4 + 2, var5);
      String var9 = this.readClass(var4 + 4, var5);
      String[] var10 = new String[this.readUnsignedShort(var4 + 6)];
      var4 += 8;

      for(int var11 = 0; var11 < var10.length; ++var11) {
         var10[var11] = this.readClass(var4, var5);
         var4 += 2;
      }

      String var28 = null;
      String var12 = null;
      String var13 = null;
      String var14 = null;
      String var15 = null;
      String var16 = null;
      int var17 = 0;
      int var18 = 0;
      int var19 = 0;
      int var20 = 0;
      int var21 = 0;
      Attribute var22 = null;
      var4 = this.a();

      int var23;
      for(var23 = this.readUnsignedShort(var4); var23 > 0; --var23) {
         String var24 = this.readUTF8(var4 + 2, var5);
         if ("SourceFile".equals(var24)) {
            var12 = this.readUTF8(var4 + 8, var5);
         } else if ("InnerClasses".equals(var24)) {
            var21 = var4 + 8;
         } else {
            int var31;
            if ("EnclosingMethod".equals(var24)) {
               var14 = this.readClass(var4 + 8, var5);
               var31 = this.readUnsignedShort(var4 + 10);
               if (var31 != 0) {
                  var15 = this.readUTF8(this.a[var31], var5);
                  var16 = this.readUTF8(this.a[var31] + 2, var5);
               }
            } else if ("Signature".equals(var24)) {
               var28 = this.readUTF8(var4 + 8, var5);
            } else if ("RuntimeVisibleAnnotations".equals(var24)) {
               var17 = var4 + 8;
            } else if ("RuntimeVisibleTypeAnnotations".equals(var24)) {
               var19 = var4 + 8;
            } else if ("Deprecated".equals(var24)) {
               var7 |= 131072;
            } else if ("Synthetic".equals(var24)) {
               var7 |= 266240;
            } else if ("SourceDebugExtension".equals(var24)) {
               var31 = this.readInt(var4 + 4);
               var13 = this.a(var4 + 8, var31, new char[var31]);
            } else if ("RuntimeInvisibleAnnotations".equals(var24)) {
               var18 = var4 + 8;
            } else if ("RuntimeInvisibleTypeAnnotations".equals(var24)) {
               var20 = var4 + 8;
            } else if (!"BootstrapMethods".equals(var24)) {
               Attribute var30 = this.a(var2, var24, var4 + 8, this.readInt(var4 + 4), var5, -1, (Label[])null);
               if (var30 != null) {
                  var30.a = var22;
                  var22 = var30;
               }
            } else {
               int[] var25 = new int[this.readUnsignedShort(var4 + 8)];
               int var26 = 0;

               for(int var27 = var4 + 10; var26 < var25.length; ++var26) {
                  var25[var26] = var27;
                  var27 += 2 + this.readUnsignedShort(var27 + 2) << 1;
               }

               var6.d = var25;
            }
         }

         var4 += 6 + this.readInt(var4 + 4);
      }

      var1.visit(this.readInt(this.a[1] - 7), var7, var8, var28, var9, var10);
      if ((var3 & 2) == 0 && (var12 != null || var13 != null)) {
         var1.visitSource(var12, var13);
      }

      if (var14 != null) {
         var1.visitOuterClass(var14, var15, var16);
      }

      int var29;
      if (var17 != 0) {
         var23 = this.readUnsignedShort(var17);

         for(var29 = var17 + 2; var23 > 0; --var23) {
            var29 = this.a(var29 + 2, var5, true, var1.visitAnnotation(this.readUTF8(var29, var5), true));
         }
      }

      if (var18 != 0) {
         var23 = this.readUnsignedShort(var18);

         for(var29 = var18 + 2; var23 > 0; --var23) {
            var29 = this.a(var29 + 2, var5, true, var1.visitAnnotation(this.readUTF8(var29, var5), false));
         }
      }

      if (var19 != 0) {
         var23 = this.readUnsignedShort(var19);

         for(var29 = var19 + 2; var23 > 0; --var23) {
            var29 = this.a(var6, var29);
            var29 = this.a(var29 + 2, var5, true, var1.visitTypeAnnotation(var6.i, var6.j, this.readUTF8(var29, var5), true));
         }
      }

      if (var20 != 0) {
         var23 = this.readUnsignedShort(var20);

         for(var29 = var20 + 2; var23 > 0; --var23) {
            var29 = this.a(var6, var29);
            var29 = this.a(var29 + 2, var5, true, var1.visitTypeAnnotation(var6.i, var6.j, this.readUTF8(var29, var5), false));
         }
      }

      while(var22 != null) {
         Attribute var32 = var22.a;
         var22.a = null;
         var1.visitAttribute(var22);
         var22 = var32;
      }

      if (var21 != 0) {
         var23 = var21 + 2;

         for(var29 = this.readUnsignedShort(var21); var29 > 0; --var29) {
            var1.visitInnerClass(this.readClass(var23, var5), this.readClass(var23 + 2, var5), this.readUTF8(var23 + 4, var5), this.readUnsignedShort(var23 + 6));
            var23 += 8;
         }
      }

      var4 = this.header + 10 + 2 * var10.length;

      for(var23 = this.readUnsignedShort(var4 - 2); var23 > 0; --var23) {
         var4 = this.a(var1, var6, var4);
      }

      var4 += 2;

      for(var23 = this.readUnsignedShort(var4 - 2); var23 > 0; --var23) {
         var4 = this.b(var1, var6, var4);
      }

      var1.visitEnd();
   }

   private int a(ClassVisitor var1, Context var2, int var3) {
      char[] var4 = var2.c;
      int var5 = this.readUnsignedShort(var3);
      String var6 = this.readUTF8(var3 + 2, var4);
      String var7 = this.readUTF8(var3 + 4, var4);
      var3 += 6;
      String var8 = null;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      int var12 = 0;
      Object var13 = null;
      Attribute var14 = null;

      int var17;
      for(int var15 = this.readUnsignedShort(var3); var15 > 0; --var15) {
         String var16 = this.readUTF8(var3 + 2, var4);
         if ("ConstantValue".equals(var16)) {
            var17 = this.readUnsignedShort(var3 + 8);
            var13 = var17 == 0 ? null : this.readConst(var17, var4);
         } else if ("Signature".equals(var16)) {
            var8 = this.readUTF8(var3 + 8, var4);
         } else if ("Deprecated".equals(var16)) {
            var5 |= 131072;
         } else if ("Synthetic".equals(var16)) {
            var5 |= 266240;
         } else if ("RuntimeVisibleAnnotations".equals(var16)) {
            var9 = var3 + 8;
         } else if ("RuntimeVisibleTypeAnnotations".equals(var16)) {
            var11 = var3 + 8;
         } else if ("RuntimeInvisibleAnnotations".equals(var16)) {
            var10 = var3 + 8;
         } else if ("RuntimeInvisibleTypeAnnotations".equals(var16)) {
            var12 = var3 + 8;
         } else {
            Attribute var20 = this.a(var2.a, var16, var3 + 8, this.readInt(var3 + 4), var4, -1, (Label[])null);
            if (var20 != null) {
               var20.a = var14;
               var14 = var20;
            }
         }

         var3 += 6 + this.readInt(var3 + 4);
      }

      var3 += 2;
      FieldVisitor var18 = var1.visitField(var5, var6, var7, var8, var13);
      if (var18 == null) {
         return var3;
      } else {
         int var19;
         if (var9 != 0) {
            var19 = this.readUnsignedShort(var9);

            for(var17 = var9 + 2; var19 > 0; --var19) {
               var17 = this.a(var17 + 2, var4, true, var18.visitAnnotation(this.readUTF8(var17, var4), true));
            }
         }

         if (var10 != 0) {
            var19 = this.readUnsignedShort(var10);

            for(var17 = var10 + 2; var19 > 0; --var19) {
               var17 = this.a(var17 + 2, var4, true, var18.visitAnnotation(this.readUTF8(var17, var4), false));
            }
         }

         if (var11 != 0) {
            var19 = this.readUnsignedShort(var11);

            for(var17 = var11 + 2; var19 > 0; --var19) {
               var17 = this.a(var2, var17);
               var17 = this.a(var17 + 2, var4, true, var18.visitTypeAnnotation(var2.i, var2.j, this.readUTF8(var17, var4), true));
            }
         }

         if (var12 != 0) {
            var19 = this.readUnsignedShort(var12);

            for(var17 = var12 + 2; var19 > 0; --var19) {
               var17 = this.a(var2, var17);
               var17 = this.a(var17 + 2, var4, true, var18.visitTypeAnnotation(var2.i, var2.j, this.readUTF8(var17, var4), false));
            }
         }

         while(var14 != null) {
            Attribute var21 = var14.a;
            var14.a = null;
            var18.visitAttribute(var14);
            var14 = var21;
         }

         var18.visitEnd();
         return var3;
      }
   }

   private int b(ClassVisitor var1, Context var2, int var3) {
      char[] var4 = var2.c;
      var2.e = this.readUnsignedShort(var3);
      var2.f = this.readUTF8(var3 + 2, var4);
      var2.g = this.readUTF8(var3 + 4, var4);
      var3 += 6;
      int var5 = 0;
      int var6 = 0;
      String[] var7 = null;
      String var8 = null;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      int var12 = 0;
      int var13 = 0;
      int var14 = 0;
      int var15 = 0;
      int var16 = 0;
      int var17 = var3;
      Attribute var18 = null;

      int var26;
      for(int var19 = this.readUnsignedShort(var3); var19 > 0; --var19) {
         String var20 = this.readUTF8(var3 + 2, var4);
         if ("Code".equals(var20)) {
            if ((var2.b & 1) == 0) {
               var5 = var3 + 8;
            }
         } else if ("Exceptions".equals(var20)) {
            var7 = new String[this.readUnsignedShort(var3 + 8)];
            var6 = var3 + 10;

            for(var26 = 0; var26 < var7.length; ++var26) {
               var7[var26] = this.readClass(var6, var4);
               var6 += 2;
            }
         } else if ("Signature".equals(var20)) {
            var8 = this.readUTF8(var3 + 8, var4);
         } else if ("Deprecated".equals(var20)) {
            var2.e |= 131072;
         } else if ("RuntimeVisibleAnnotations".equals(var20)) {
            var10 = var3 + 8;
         } else if ("RuntimeVisibleTypeAnnotations".equals(var20)) {
            var12 = var3 + 8;
         } else if ("AnnotationDefault".equals(var20)) {
            var14 = var3 + 8;
         } else if ("Synthetic".equals(var20)) {
            var2.e |= 266240;
         } else if ("RuntimeInvisibleAnnotations".equals(var20)) {
            var11 = var3 + 8;
         } else if ("RuntimeInvisibleTypeAnnotations".equals(var20)) {
            var13 = var3 + 8;
         } else if ("RuntimeVisibleParameterAnnotations".equals(var20)) {
            var15 = var3 + 8;
         } else if ("RuntimeInvisibleParameterAnnotations".equals(var20)) {
            var16 = var3 + 8;
         } else if ("MethodParameters".equals(var20)) {
            var9 = var3 + 8;
         } else {
            Attribute var21 = this.a(var2.a, var20, var3 + 8, this.readInt(var3 + 4), var4, -1, (Label[])null);
            if (var21 != null) {
               var21.a = var18;
               var18 = var21;
            }
         }

         var3 += 6 + this.readInt(var3 + 4);
      }

      var3 += 2;
      MethodVisitor var23 = var1.visitMethod(var2.e, var2.f, var2.g, var8, var7);
      if (var23 == null) {
         return var3;
      } else {
         if (var23 instanceof MethodWriter) {
            MethodWriter var24 = (MethodWriter)var23;
            if (var24.b.M == this && var8 == var24.g) {
               boolean var28 = false;
               if (var7 == null) {
                  var28 = var24.j == 0;
               } else if (var7.length == var24.j) {
                  var28 = true;

                  for(int var22 = var7.length - 1; var22 >= 0; --var22) {
                     var6 -= 2;
                     if (var24.k[var22] != this.readUnsignedShort(var6)) {
                        var28 = false;
                        break;
                     }
                  }
               }

               if (var28) {
                  var24.h = var17;
                  var24.i = var3 - var17;
                  return var3;
               }
            }
         }

         int var25;
         if (var9 != 0) {
            var25 = this.b[var9] & 255;

            for(var26 = var9 + 1; var25 > 0; var26 += 4) {
               var23.visitParameter(this.readUTF8(var26, var4), this.readUnsignedShort(var26 + 2));
               --var25;
            }
         }

         if (var14 != 0) {
            AnnotationVisitor var27 = var23.visitAnnotationDefault();
            this.a(var14, var4, (String)null, var27);
            if (var27 != null) {
               var27.visitEnd();
            }
         }

         if (var10 != 0) {
            var25 = this.readUnsignedShort(var10);

            for(var26 = var10 + 2; var25 > 0; --var25) {
               var26 = this.a(var26 + 2, var4, true, var23.visitAnnotation(this.readUTF8(var26, var4), true));
            }
         }

         if (var11 != 0) {
            var25 = this.readUnsignedShort(var11);

            for(var26 = var11 + 2; var25 > 0; --var25) {
               var26 = this.a(var26 + 2, var4, true, var23.visitAnnotation(this.readUTF8(var26, var4), false));
            }
         }

         if (var12 != 0) {
            var25 = this.readUnsignedShort(var12);

            for(var26 = var12 + 2; var25 > 0; --var25) {
               var26 = this.a(var2, var26);
               var26 = this.a(var26 + 2, var4, true, var23.visitTypeAnnotation(var2.i, var2.j, this.readUTF8(var26, var4), true));
            }
         }

         if (var13 != 0) {
            var25 = this.readUnsignedShort(var13);

            for(var26 = var13 + 2; var25 > 0; --var25) {
               var26 = this.a(var2, var26);
               var26 = this.a(var26 + 2, var4, true, var23.visitTypeAnnotation(var2.i, var2.j, this.readUTF8(var26, var4), false));
            }
         }

         if (var15 != 0) {
            this.b(var23, var2, var15, true);
         }

         if (var16 != 0) {
            this.b(var23, var2, var16, false);
         }

         while(var18 != null) {
            Attribute var29 = var18.a;
            var18.a = null;
            var23.visitAttribute(var18);
            var18 = var29;
         }

         if (var5 != 0) {
            var23.visitCode();
            this.a(var23, var2, var5);
         }

         var23.visitEnd();
         return var3;
      }
   }

   private void a(MethodVisitor var1, Context var2, int var3) {
      byte[] var4 = this.b;
      char[] var5 = var2.c;
      int var6 = this.readUnsignedShort(var3);
      int var7 = this.readUnsignedShort(var3 + 2);
      int var8 = this.readInt(var3 + 4);
      var3 += 8;
      int var9 = var3;
      int var10 = var3 + var8;
      Label[] var11 = var2.h = new Label[var8 + 2];
      this.readLabel(var8 + 1, var11);

      while(true) {
         int var12;
         int var14;
         while(var3 < var10) {
            var12 = var3 - var9;
            int var13 = var4[var3] & 255;
            switch(ClassWriter.a[var13]) {
            case 0:
            case 4:
               ++var3;
               break;
            case 1:
            case 3:
            case 11:
               var3 += 2;
               break;
            case 2:
            case 5:
            case 6:
            case 12:
            case 13:
               var3 += 3;
               break;
            case 7:
            case 8:
               var3 += 5;
               break;
            case 9:
               this.readLabel(var12 + this.readShort(var3 + 1), var11);
               var3 += 3;
               break;
            case 10:
               this.readLabel(var12 + this.readInt(var3 + 1), var11);
               var3 += 5;
               break;
            case 14:
               var3 = var3 + 4 - (var12 & 3);
               this.readLabel(var12 + this.readInt(var3), var11);

               for(var14 = this.readInt(var3 + 8) - this.readInt(var3 + 4) + 1; var14 > 0; --var14) {
                  this.readLabel(var12 + this.readInt(var3 + 12), var11);
                  var3 += 4;
               }

               var3 += 12;
               break;
            case 15:
               var3 = var3 + 4 - (var12 & 3);
               this.readLabel(var12 + this.readInt(var3), var11);

               for(var14 = this.readInt(var3 + 4); var14 > 0; --var14) {
                  this.readLabel(var12 + this.readInt(var3 + 12), var11);
                  var3 += 8;
               }

               var3 += 8;
               break;
            case 16:
            default:
               var3 += 4;
               break;
            case 17:
               var13 = var4[var3 + 1] & 255;
               if (var13 == 132) {
                  var3 += 6;
               } else {
                  var3 += 4;
               }
            }
         }

         for(var12 = this.readUnsignedShort(var3); var12 > 0; --var12) {
            Label var38 = this.readLabel(this.readUnsignedShort(var3 + 2), var11);
            Label var41 = this.readLabel(this.readUnsignedShort(var3 + 4), var11);
            Label var15 = this.readLabel(this.readUnsignedShort(var3 + 6), var11);
            String var16 = this.readUTF8(this.a[this.readUnsignedShort(var3 + 8)], var5);
            var1.visitTryCatchBlock(var38, var41, var15, var16);
            var3 += 8;
         }

         var3 += 2;
         int[] var37 = null;
         int[] var39 = null;
         var14 = 0;
         int var40 = 0;
         int var42 = -1;
         int var17 = -1;
         int var18 = 0;
         int var19 = 0;
         boolean var20 = true;
         boolean var21 = (var2.b & 8) != 0;
         int var22 = 0;
         int var23 = 0;
         int var24 = 0;
         Context var25 = null;
         Attribute var26 = null;

         int var27;
         int var29;
         int var31;
         int var46;
         for(var27 = this.readUnsignedShort(var3); var27 > 0; --var27) {
            String var28 = this.readUTF8(var3 + 2, var5);
            Label var10000;
            if ("LocalVariableTable".equals(var28)) {
               if ((var2.b & 2) == 0) {
                  var18 = var3 + 8;
                  var29 = this.readUnsignedShort(var3 + 8);

                  for(var46 = var3; var29 > 0; --var29) {
                     var31 = this.readUnsignedShort(var46 + 10);
                     if (var11[var31] == null) {
                        var10000 = this.readLabel(var31, var11);
                        var10000.a |= 1;
                     }

                     var31 += this.readUnsignedShort(var46 + 12);
                     if (var11[var31] == null) {
                        var10000 = this.readLabel(var31, var11);
                        var10000.a |= 1;
                     }

                     var46 += 10;
                  }
               }
            } else if ("LocalVariableTypeTable".equals(var28)) {
               var19 = var3 + 8;
            } else if ("LineNumberTable".equals(var28)) {
               if ((var2.b & 2) == 0) {
                  var29 = this.readUnsignedShort(var3 + 8);

                  for(var46 = var3; var29 > 0; --var29) {
                     var31 = this.readUnsignedShort(var46 + 10);
                     if (var11[var31] == null) {
                        var10000 = this.readLabel(var31, var11);
                        var10000.a |= 1;
                     }

                     Label var32;
                     for(var32 = var11[var31]; var32.b > 0; var32 = var32.k) {
                        if (var32.k == null) {
                           var32.k = new Label();
                        }
                     }

                     var32.b = this.readUnsignedShort(var46 + 12);
                     var46 += 4;
                  }
               }
            } else if ("RuntimeVisibleTypeAnnotations".equals(var28)) {
               var37 = this.a(var1, var2, var3 + 8, true);
               var42 = var37.length != 0 && this.readByte(var37[0]) >= 67 ? this.readUnsignedShort(var37[0] + 1) : -1;
            } else if (!"RuntimeInvisibleTypeAnnotations".equals(var28)) {
               if ("StackMapTable".equals(var28)) {
                  if ((var2.b & 4) == 0) {
                     var22 = var3 + 10;
                     var23 = this.readInt(var3 + 4);
                     var24 = this.readUnsignedShort(var3 + 8);
                  }
               } else if ("StackMap".equals(var28)) {
                  if ((var2.b & 4) == 0) {
                     var20 = false;
                     var22 = var3 + 10;
                     var23 = this.readInt(var3 + 4);
                     var24 = this.readUnsignedShort(var3 + 8);
                  }
               } else {
                  for(var29 = 0; var29 < var2.a.length; ++var29) {
                     if (var2.a[var29].type.equals(var28)) {
                        Attribute var30 = var2.a[var29].read(this, var3 + 8, this.readInt(var3 + 4), var5, var9 - 8, var11);
                        if (var30 != null) {
                           var30.a = var26;
                           var26 = var30;
                        }
                     }
                  }
               }
            } else {
               var39 = this.a(var1, var2, var3 + 8, false);
               var17 = var39.length != 0 && this.readByte(var39[0]) >= 67 ? this.readUnsignedShort(var39[0] + 1) : -1;
            }

            var3 += 6 + this.readInt(var3 + 4);
         }

         var3 += 2;
         int var43;
         if (var22 != 0) {
            var25 = var2;
            var2.o = -1;
            var2.p = 0;
            var2.q = 0;
            var2.r = 0;
            var2.t = 0;
            var2.s = new Object[var7];
            var2.u = new Object[var6];
            if (var21) {
               this.a(var2);
            }

            for(var27 = var22; var27 < var22 + var23 - 2; ++var27) {
               if (var4[var27] == 8) {
                  var43 = this.readUnsignedShort(var27 + 1);
                  if (var43 >= 0 && var43 < var8 && (var4[var9 + var43] & 255) == 187) {
                     this.readLabel(var43, var11);
                  }
               }
            }
         }

         var3 = var9;

         String var53;
         int var54;
         while(var3 < var10) {
            var27 = var3 - var9;
            Label var44 = var11[var27];
            if (var44 != null) {
               Label var47 = var44.k;
               var44.k = null;
               var1.visitLabel(var44);
               if ((var2.b & 2) == 0 && var44.b > 0) {
                  var1.visitLineNumber(var44.b, var44);

                  while(var47 != null) {
                     var1.visitLineNumber(var47.b, var44);
                     var47 = var47.k;
                  }
               }
            }

            while(var25 != null && (var25.o == var27 || var25.o == -1)) {
               if (var25.o != -1) {
                  if (var20 && !var21) {
                     var1.visitFrame(var25.p, var25.r, var25.s, var25.t, var25.u);
                  } else {
                     var1.visitFrame(-1, var25.q, var25.s, var25.t, var25.u);
                  }
               }

               if (var24 > 0) {
                  var22 = this.a(var22, var20, var21, var25);
                  --var24;
               } else {
                  var25 = null;
               }
            }

            var29 = var4[var3] & 255;
            Label[] var33;
            int var34;
            switch(ClassWriter.a[var29]) {
            case 0:
               var1.visitInsn(var29);
               ++var3;
               break;
            case 1:
               var1.visitIntInsn(var29, var4[var3 + 1]);
               var3 += 2;
               break;
            case 2:
               var1.visitIntInsn(var29, this.readShort(var3 + 1));
               var3 += 3;
               break;
            case 3:
               var1.visitVarInsn(var29, var4[var3 + 1] & 255);
               var3 += 2;
               break;
            case 4:
               if (var29 > 54) {
                  var29 -= 59;
                  var1.visitVarInsn(54 + (var29 >> 2), var29 & 3);
               } else {
                  var29 -= 26;
                  var1.visitVarInsn(21 + (var29 >> 2), var29 & 3);
               }

               ++var3;
               break;
            case 5:
               var1.visitTypeInsn(var29, this.readClass(var3 + 1, var5));
               var3 += 3;
               break;
            case 6:
            case 7:
               var46 = this.a[this.readUnsignedShort(var3 + 1)];
               boolean var52 = var4[var46 - 1] == 11;
               var53 = this.readClass(var46, var5);
               var46 = this.a[this.readUnsignedShort(var46 + 2)];
               String var55 = this.readUTF8(var46, var5);
               String var57 = this.readUTF8(var46 + 2, var5);
               if (var29 < 182) {
                  var1.visitFieldInsn(var29, var53, var55, var57);
               } else {
                  var1.visitMethodInsn(var29, var53, var55, var57, var52);
               }

               if (var29 == 185) {
                  var3 += 5;
               } else {
                  var3 += 3;
               }
               break;
            case 8:
               var46 = this.a[this.readUnsignedShort(var3 + 1)];
               var31 = var2.d[this.readUnsignedShort(var46)];
               Handle var51 = (Handle)this.readConst(this.readUnsignedShort(var31), var5);
               var54 = this.readUnsignedShort(var31 + 2);
               Object[] var56 = new Object[var54];
               var31 += 4;

               for(int var35 = 0; var35 < var54; ++var35) {
                  var56[var35] = this.readConst(this.readUnsignedShort(var31), var5);
                  var31 += 2;
               }

               var46 = this.a[this.readUnsignedShort(var46 + 2)];
               String var58 = this.readUTF8(var46, var5);
               String var36 = this.readUTF8(var46 + 2, var5);
               var1.visitInvokeDynamicInsn(var58, var36, var51, var56);
               break;
            case 9:
               var1.visitJumpInsn(var29, var11[var27 + this.readShort(var3 + 1)]);
               var3 += 3;
               break;
            case 10:
               var1.visitJumpInsn(var29 - 33, var11[var27 + this.readInt(var3 + 1)]);
               var3 += 5;
               break;
            case 11:
               var1.visitLdcInsn(this.readConst(var4[var3 + 1] & 255, var5));
               var3 += 2;
               break;
            case 12:
               var1.visitLdcInsn(this.readConst(this.readUnsignedShort(var3 + 1), var5));
               var3 += 3;
               break;
            case 13:
               var1.visitIincInsn(var4[var3 + 1] & 255, var4[var3 + 2]);
               var3 += 3;
               break;
            case 14:
               var3 = var3 + 4 - (var27 & 3);
               var46 = var27 + this.readInt(var3);
               var31 = this.readInt(var3 + 4);
               int var50 = this.readInt(var3 + 8);
               var33 = new Label[var50 - var31 + 1];
               var3 += 12;

               for(var34 = 0; var34 < var33.length; ++var34) {
                  var33[var34] = var11[var27 + this.readInt(var3)];
                  var3 += 4;
               }

               var1.visitTableSwitchInsn(var31, var50, var11[var46], var33);
               break;
            case 15:
               var3 = var3 + 4 - (var27 & 3);
               var46 = var27 + this.readInt(var3);
               var31 = this.readInt(var3 + 4);
               int[] var49 = new int[var31];
               var33 = new Label[var31];
               var3 += 8;

               for(var34 = 0; var34 < var31; ++var34) {
                  var49[var34] = this.readInt(var3);
                  var33[var34] = var11[var27 + this.readInt(var3 + 4)];
                  var3 += 8;
               }

               var1.visitLookupSwitchInsn(var11[var46], var49, var33);
               break;
            case 16:
            default:
               var1.visitMultiANewArrayInsn(this.readClass(var3 + 1, var5), var4[var3 + 3] & 255);
               var3 += 4;
               break;
            case 17:
               var29 = var4[var3 + 1] & 255;
               if (var29 == 132) {
                  var1.visitIincInsn(this.readUnsignedShort(var3 + 2), this.readShort(var3 + 4));
                  var3 += 6;
               } else {
                  var1.visitVarInsn(var29, this.readUnsignedShort(var3 + 2));
                  var3 += 4;
               }
            }

            for(var3 += 5; var37 != null && var14 < var37.length && var42 <= var27; var42 = var14 < var37.length && this.readByte(var37[var14]) >= 67 ? this.readUnsignedShort(var37[var14] + 1) : -1) {
               if (var42 == var27) {
                  var46 = this.a(var2, var37[var14]);
                  this.a(var46 + 2, var5, true, var1.visitInsnAnnotation(var2.i, var2.j, this.readUTF8(var46, var5), true));
               }

               ++var14;
            }

            while(var39 != null && var40 < var39.length && var17 <= var27) {
               if (var17 == var27) {
                  var46 = this.a(var2, var39[var40]);
                  this.a(var46 + 2, var5, true, var1.visitInsnAnnotation(var2.i, var2.j, this.readUTF8(var46, var5), false));
               }

               ++var40;
               var17 = var40 < var39.length && this.readByte(var39[var40]) >= 67 ? this.readUnsignedShort(var39[var40] + 1) : -1;
            }
         }

         if (var11[var8] != null) {
            var1.visitLabel(var11[var8]);
         }

         if ((var2.b & 2) == 0 && var18 != 0) {
            int[] var45 = null;
            if (var19 != 0) {
               var3 = var19 + 2;
               var45 = new int[this.readUnsignedShort(var19) * 3];

               for(var43 = var45.length; var43 > 0; var3 += 10) {
                  --var43;
                  var45[var43] = var3 + 6;
                  --var43;
                  var45[var43] = this.readUnsignedShort(var3 + 8);
                  --var43;
                  var45[var43] = this.readUnsignedShort(var3);
               }
            }

            var3 = var18 + 2;

            for(var43 = this.readUnsignedShort(var18); var43 > 0; --var43) {
               var29 = this.readUnsignedShort(var3);
               var46 = this.readUnsignedShort(var3 + 2);
               var31 = this.readUnsignedShort(var3 + 8);
               var53 = null;
               if (var45 != null) {
                  for(var54 = 0; var54 < var45.length; var54 += 3) {
                     if (var45[var54] == var29 && var45[var54 + 1] == var31) {
                        var53 = this.readUTF8(var45[var54 + 2], var5);
                        break;
                     }
                  }
               }

               var1.visitLocalVariable(this.readUTF8(var3 + 4, var5), this.readUTF8(var3 + 6, var5), var53, var11[var29], var11[var29 + var46], var31);
               var3 += 10;
            }
         }

         if (var37 != null) {
            for(var27 = 0; var27 < var37.length; ++var27) {
               if (this.readByte(var37[var27]) >> 1 == 32) {
                  var43 = this.a(var2, var37[var27]);
                  this.a(var43 + 2, var5, true, var1.visitLocalVariableAnnotation(var2.i, var2.j, var2.l, var2.m, var2.n, this.readUTF8(var43, var5), true));
               }
            }
         }

         if (var39 != null) {
            for(var27 = 0; var27 < var39.length; ++var27) {
               if (this.readByte(var39[var27]) >> 1 == 32) {
                  var43 = this.a(var2, var39[var27]);
                  this.a(var43 + 2, var5, true, var1.visitLocalVariableAnnotation(var2.i, var2.j, var2.l, var2.m, var2.n, this.readUTF8(var43, var5), false));
               }
            }
         }

         while(var26 != null) {
            Attribute var48 = var26.a;
            var26.a = null;
            var1.visitAttribute(var26);
            var26 = var48;
         }

         var1.visitMaxs(var6, var7);
         return;
      }
   }

   private int[] a(MethodVisitor var1, Context var2, int var3, boolean var4) {
      char[] var5 = var2.c;
      int[] var6 = new int[this.readUnsignedShort(var3)];
      var3 += 2;

      for(int var7 = 0; var7 < var6.length; ++var7) {
         var6[var7] = var3;
         int var8 = this.readInt(var3);
         int var9;
         switch(var8 >>> 24) {
         case 0:
         case 1:
         case 22:
            var3 += 2;
            break;
         case 19:
         case 20:
         case 21:
            ++var3;
            break;
         case 64:
         case 65:
            for(var9 = this.readUnsignedShort(var3 + 1); var9 > 0; --var9) {
               int var10 = this.readUnsignedShort(var3 + 3);
               int var11 = this.readUnsignedShort(var3 + 5);
               this.readLabel(var10, var2.h);
               this.readLabel(var10 + var11, var2.h);
               var3 += 6;
            }

            var3 += 3;
            break;
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
            var3 += 4;
            break;
         default:
            var3 += 3;
         }

         var9 = this.readByte(var3);
         if (var8 >>> 24 == 66) {
            TypePath var12 = var9 == 0 ? null : new TypePath(this.b, var3);
            var3 += 1 + 2 * var9;
            var3 = this.a(var3 + 2, var5, true, var1.visitTryCatchAnnotation(var8, var12, this.readUTF8(var3, var5), var4));
         } else {
            var3 = this.a(var3 + 3 + 2 * var9, var5, true, (AnnotationVisitor)null);
         }
      }

      return var6;
   }

   private int a(Context var1, int var2) {
      int var3;
      int var4;
      var3 = this.readInt(var2);
      label29:
      switch(var3 >>> 24) {
      case 0:
      case 1:
      case 22:
         var3 &= -65536;
         var2 += 2;
         break;
      case 19:
      case 20:
      case 21:
         var3 &= -16777216;
         ++var2;
         break;
      case 64:
      case 65:
         var3 &= -16777216;
         var4 = this.readUnsignedShort(var2 + 1);
         var1.l = new Label[var4];
         var1.m = new Label[var4];
         var1.n = new int[var4];
         var2 += 3;
         int var5 = 0;

         while(true) {
            if (var5 >= var4) {
               break label29;
            }

            int var6 = this.readUnsignedShort(var2);
            int var7 = this.readUnsignedShort(var2 + 2);
            var1.l[var5] = this.readLabel(var6, var1.h);
            var1.m[var5] = this.readLabel(var6 + var7, var1.h);
            var1.n[var5] = this.readUnsignedShort(var2 + 4);
            var2 += 6;
            ++var5;
         }
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
         var3 &= -16776961;
         var2 += 4;
         break;
      default:
         var3 &= var3 >>> 24 < 67 ? -256 : -16777216;
         var2 += 3;
      }

      var4 = this.readByte(var2);
      var1.i = var3;
      var1.j = var4 == 0 ? null : new TypePath(this.b, var2);
      return var2 + 1 + 2 * var4;
   }

   private void b(MethodVisitor var1, Context var2, int var3, boolean var4) {
      int var5 = this.b[var3++] & 255;
      int var6 = Type.getArgumentTypes(var2.g).length - var5;

      int var7;
      AnnotationVisitor var8;
      for(var7 = 0; var7 < var6; ++var7) {
         var8 = var1.visitParameterAnnotation(var7, "Ljava/lang/Synthetic;", false);
         if (var8 != null) {
            var8.visitEnd();
         }
      }

      for(char[] var9 = var2.c; var7 < var5 + var6; ++var7) {
         int var10 = this.readUnsignedShort(var3);

         for(var3 += 2; var10 > 0; --var10) {
            var8 = var1.visitParameterAnnotation(var7, this.readUTF8(var3, var9), var4);
            var3 = this.a(var3 + 2, var9, true, var8);
         }
      }

   }

   private int a(int var1, char[] var2, boolean var3, AnnotationVisitor var4) {
      int var5 = this.readUnsignedShort(var1);
      var1 += 2;
      if (var3) {
         while(var5 > 0) {
            var1 = this.a(var1 + 2, var2, this.readUTF8(var1, var2), var4);
            --var5;
         }
      } else {
         while(var5 > 0) {
            var1 = this.a(var1, var2, (String)null, var4);
            --var5;
         }
      }

      if (var4 != null) {
         var4.visitEnd();
      }

      return var1;
   }

   private int a(int var1, char[] var2, String var3, AnnotationVisitor var4) {
      if (var4 == null) {
         switch(this.b[var1] & 255) {
         case 64:
            return this.a(var1 + 3, var2, true, (AnnotationVisitor)null);
         case 91:
            return this.a(var1 + 1, var2, false, (AnnotationVisitor)null);
         case 101:
            return var1 + 5;
         default:
            return var1 + 3;
         }
      } else {
         switch(this.b[var1++] & 255) {
         case 64:
            var1 = this.a(var1 + 2, var2, true, var4.visitAnnotation(var3, this.readUTF8(var1, var2)));
         case 65:
         case 69:
         case 71:
         case 72:
         case 75:
         case 76:
         case 77:
         case 78:
         case 79:
         case 80:
         case 81:
         case 82:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         case 97:
         case 98:
         case 100:
         case 102:
         case 103:
         case 104:
         case 105:
         case 106:
         case 107:
         case 108:
         case 109:
         case 110:
         case 111:
         case 112:
         case 113:
         case 114:
         default:
            break;
         case 66:
            var4.visit(var3, new Byte((byte)this.readInt(this.a[this.readUnsignedShort(var1)])));
            var1 += 2;
            break;
         case 67:
            var4.visit(var3, new Character((char)this.readInt(this.a[this.readUnsignedShort(var1)])));
            var1 += 2;
            break;
         case 68:
         case 70:
         case 73:
         case 74:
            var4.visit(var3, this.readConst(this.readUnsignedShort(var1), var2));
            var1 += 2;
            break;
         case 83:
            var4.visit(var3, new Short((short)this.readInt(this.a[this.readUnsignedShort(var1)])));
            var1 += 2;
            break;
         case 90:
            var4.visit(var3, this.readInt(this.a[this.readUnsignedShort(var1)]) == 0 ? Boolean.FALSE : Boolean.TRUE);
            var1 += 2;
            break;
         case 91:
            int var5 = this.readUnsignedShort(var1);
            var1 += 2;
            if (var5 == 0) {
               return this.a(var1 - 2, var2, false, var4.visitArray(var3));
            }

            int var7;
            switch(this.b[var1++] & 255) {
            case 66:
               byte[] var6 = new byte[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var6[var7] = (byte)this.readInt(this.a[this.readUnsignedShort(var1)]);
                  var1 += 3;
               }

               var4.visit(var3, var6);
               --var1;
               return var1;
            case 67:
               char[] var10 = new char[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var10[var7] = (char)this.readInt(this.a[this.readUnsignedShort(var1)]);
                  var1 += 3;
               }

               var4.visit(var3, var10);
               --var1;
               return var1;
            case 68:
               double[] var14 = new double[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var14[var7] = Double.longBitsToDouble(this.readLong(this.a[this.readUnsignedShort(var1)]));
                  var1 += 3;
               }

               var4.visit(var3, var14);
               --var1;
               return var1;
            case 69:
            case 71:
            case 72:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            default:
               var1 = this.a(var1 - 3, var2, false, var4.visitArray(var3));
               return var1;
            case 70:
               float[] var13 = new float[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var13[var7] = Float.intBitsToFloat(this.readInt(this.a[this.readUnsignedShort(var1)]));
                  var1 += 3;
               }

               var4.visit(var3, var13);
               --var1;
               return var1;
            case 73:
               int[] var11 = new int[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var11[var7] = this.readInt(this.a[this.readUnsignedShort(var1)]);
                  var1 += 3;
               }

               var4.visit(var3, var11);
               --var1;
               return var1;
            case 74:
               long[] var12 = new long[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var12[var7] = this.readLong(this.a[this.readUnsignedShort(var1)]);
                  var1 += 3;
               }

               var4.visit(var3, var12);
               --var1;
               return var1;
            case 83:
               short[] var9 = new short[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var9[var7] = (short)this.readInt(this.a[this.readUnsignedShort(var1)]);
                  var1 += 3;
               }

               var4.visit(var3, var9);
               --var1;
               return var1;
            case 90:
               boolean[] var8 = new boolean[var5];

               for(var7 = 0; var7 < var5; ++var7) {
                  var8[var7] = this.readInt(this.a[this.readUnsignedShort(var1)]) != 0;
                  var1 += 3;
               }

               var4.visit(var3, var8);
               --var1;
               return var1;
            }
         case 99:
            var4.visit(var3, Type.getType(this.readUTF8(var1, var2)));
            var1 += 2;
            break;
         case 101:
            var4.visitEnum(var3, this.readUTF8(var1, var2), this.readUTF8(var1 + 2, var2));
            var1 += 4;
            break;
         case 115:
            var4.visit(var3, this.readUTF8(var1, var2));
            var1 += 2;
         }

         return var1;
      }
   }

   private void a(Context var1) {
      String var2 = var1.g;
      Object[] var3 = var1.s;
      int var4 = 0;
      if ((var1.e & 8) == 0) {
         if ("<init>".equals(var1.f)) {
            var3[var4++] = Opcodes.UNINITIALIZED_THIS;
         } else {
            var3[var4++] = this.readClass(this.header + 2, var1.c);
         }
      }

      int var5 = 1;

      while(true) {
         int var6 = var5;
         switch(var2.charAt(var5++)) {
         case 'B':
         case 'C':
         case 'I':
         case 'S':
         case 'Z':
            var3[var4++] = Opcodes.INTEGER;
            break;
         case 'D':
            var3[var4++] = Opcodes.DOUBLE;
            break;
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
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            var1.q = var4;
            return;
         case 'F':
            var3[var4++] = Opcodes.FLOAT;
            break;
         case 'J':
            var3[var4++] = Opcodes.LONG;
            break;
         case 'L':
            while(var2.charAt(var5) != ';') {
               ++var5;
            }

            var3[var4++] = var2.substring(var6 + 1, var5++);
            break;
         case '[':
            while(var2.charAt(var5) == '[') {
               ++var5;
            }

            if (var2.charAt(var5) == 'L') {
               ++var5;

               while(var2.charAt(var5) != ';') {
                  ++var5;
               }
            }

            int var10001 = var4++;
            ++var5;
            var3[var10001] = var2.substring(var6, var5);
         }
      }
   }

   private int a(int var1, boolean var2, boolean var3, Context var4) {
      char[] var5 = var4.c;
      Label[] var6 = var4.h;
      int var7;
      if (var2) {
         var7 = this.b[var1++] & 255;
      } else {
         var7 = 255;
         var4.o = -1;
      }

      var4.r = 0;
      int var8;
      if (var7 < 64) {
         var8 = var7;
         var4.p = 3;
         var4.t = 0;
      } else if (var7 < 128) {
         var8 = var7 - 64;
         var1 = this.a(var4.u, 0, var1, var5, var6);
         var4.p = 4;
         var4.t = 1;
      } else {
         var8 = this.readUnsignedShort(var1);
         var1 += 2;
         if (var7 == 247) {
            var1 = this.a(var4.u, 0, var1, var5, var6);
            var4.p = 4;
            var4.t = 1;
         } else if (var7 >= 248 && var7 < 251) {
            var4.p = 2;
            var4.r = 251 - var7;
            var4.q -= var4.r;
            var4.t = 0;
         } else if (var7 == 251) {
            var4.p = 3;
            var4.t = 0;
         } else {
            int var9;
            int var10;
            if (var7 < 255) {
               var9 = var3 ? var4.q : 0;

               for(var10 = var7 - 251; var10 > 0; --var10) {
                  var1 = this.a(var4.s, var9++, var1, var5, var6);
               }

               var4.p = 1;
               var4.r = var7 - 251;
               var4.q += var4.r;
               var4.t = 0;
            } else {
               var4.p = 0;
               var9 = this.readUnsignedShort(var1);
               var1 += 2;
               var4.r = var9;
               var4.q = var9;

               for(var10 = 0; var9 > 0; --var9) {
                  var1 = this.a(var4.s, var10++, var1, var5, var6);
               }

               var9 = this.readUnsignedShort(var1);
               var1 += 2;
               var4.t = var9;

               for(var10 = 0; var9 > 0; --var9) {
                  var1 = this.a(var4.u, var10++, var1, var5, var6);
               }
            }
         }
      }

      var4.o += var8 + 1;
      this.readLabel(var4.o, var6);
      return var1;
   }

   private int a(Object[] var1, int var2, int var3, char[] var4, Label[] var5) {
      int var6 = this.b[var3++] & 255;
      switch(var6) {
      case 0:
         var1[var2] = Opcodes.TOP;
         break;
      case 1:
         var1[var2] = Opcodes.INTEGER;
         break;
      case 2:
         var1[var2] = Opcodes.FLOAT;
         break;
      case 3:
         var1[var2] = Opcodes.DOUBLE;
         break;
      case 4:
         var1[var2] = Opcodes.LONG;
         break;
      case 5:
         var1[var2] = Opcodes.NULL;
         break;
      case 6:
         var1[var2] = Opcodes.UNINITIALIZED_THIS;
         break;
      case 7:
         var1[var2] = this.readClass(var3, var4);
         var3 += 2;
         break;
      default:
         var1[var2] = this.readLabel(this.readUnsignedShort(var3), var5);
         var3 += 2;
      }

      return var3;
   }

   protected Label readLabel(int var1, Label[] var2) {
      if (var2[var1] == null) {
         var2[var1] = new Label();
      }

      return var2[var1];
   }

   private int a() {
      int var1 = this.header + 8 + this.readUnsignedShort(this.header + 6) * 2;

      int var2;
      int var3;
      for(var2 = this.readUnsignedShort(var1); var2 > 0; --var2) {
         for(var3 = this.readUnsignedShort(var1 + 8); var3 > 0; --var3) {
            var1 += 6 + this.readInt(var1 + 12);
         }

         var1 += 8;
      }

      var1 += 2;

      for(var2 = this.readUnsignedShort(var1); var2 > 0; --var2) {
         for(var3 = this.readUnsignedShort(var1 + 8); var3 > 0; --var3) {
            var1 += 6 + this.readInt(var1 + 12);
         }

         var1 += 8;
      }

      return var1 + 2;
   }

   private Attribute a(Attribute[] var1, String var2, int var3, int var4, char[] var5, int var6, Label[] var7) {
      for(int var8 = 0; var8 < var1.length; ++var8) {
         if (var1[var8].type.equals(var2)) {
            return var1[var8].read(this, var3, var4, var5, var6, var7);
         }
      }

      return (new Attribute(var2)).read(this, var3, var4, (char[])null, -1, (Label[])null);
   }

   public int getItemCount() {
      return this.a.length;
   }

   public int getItem(int var1) {
      return this.a[var1];
   }

   public int getMaxStringLength() {
      return this.d;
   }

   public int readByte(int var1) {
      return this.b[var1] & 255;
   }

   public int readUnsignedShort(int var1) {
      byte[] var2 = this.b;
      return (var2[var1] & 255) << 8 | var2[var1 + 1] & 255;
   }

   public short readShort(int var1) {
      byte[] var2 = this.b;
      return (short)((var2[var1] & 255) << 8 | var2[var1 + 1] & 255);
   }

   public int readInt(int var1) {
      byte[] var2 = this.b;
      return (var2[var1] & 255) << 24 | (var2[var1 + 1] & 255) << 16 | (var2[var1 + 2] & 255) << 8 | var2[var1 + 3] & 255;
   }

   public long readLong(int var1) {
      long var2 = (long)this.readInt(var1);
      long var4 = (long)this.readInt(var1 + 4) & 4294967295L;
      return var2 << 32 | var4;
   }

   public String readUTF8(int var1, char[] var2) {
      int var3 = this.readUnsignedShort(var1);
      if (var1 != 0 && var3 != 0) {
         String var4 = this.c[var3];
         if (var4 != null) {
            return var4;
         } else {
            var1 = this.a[var3];
            return this.c[var3] = this.a(var1 + 2, this.readUnsignedShort(var1), var2);
         }
      } else {
         return null;
      }
   }

   private String a(int var1, int var2, char[] var3) {
      int var4 = var1 + var2;
      byte[] var5 = this.b;
      int var6 = 0;
      byte var7 = 0;
      char var8 = 0;

      while(true) {
         while(var1 < var4) {
            byte var9 = var5[var1++];
            switch(var7) {
            case 0:
               int var10 = var9 & 255;
               if (var10 < 128) {
                  var3[var6++] = (char)var10;
               } else {
                  if (var10 < 224 && var10 > 191) {
                     var8 = (char)(var10 & 31);
                     var7 = 1;
                     continue;
                  }

                  var8 = (char)(var10 & 15);
                  var7 = 2;
               }
               break;
            case 1:
               var3[var6++] = (char)(var8 << 6 | var9 & 63);
               var7 = 0;
               break;
            case 2:
               var8 = (char)(var8 << 6 | var9 & 63);
               var7 = 1;
            }
         }

         return new String(var3, 0, var6);
      }
   }

   public String readClass(int var1, char[] var2) {
      return this.readUTF8(this.a[this.readUnsignedShort(var1)], var2);
   }

   public Object readConst(int var1, char[] var2) {
      int var3 = this.a[var1];
      switch(this.b[var3 - 1]) {
      case 3:
         return new Integer(this.readInt(var3));
      case 4:
         return new Float(Float.intBitsToFloat(this.readInt(var3)));
      case 5:
         return new Long(this.readLong(var3));
      case 6:
         return new Double(Double.longBitsToDouble(this.readLong(var3)));
      case 7:
         return Type.getObjectType(this.readUTF8(var3, var2));
      case 8:
         return this.readUTF8(var3, var2);
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      default:
         int var4 = this.readByte(var3);
         int[] var5 = this.a;
         int var6 = var5[this.readUnsignedShort(var3 + 1)];
         boolean var7 = this.b[var6 - 1] == 11;
         String var8 = this.readClass(var6, var2);
         var6 = var5[this.readUnsignedShort(var6 + 2)];
         String var9 = this.readUTF8(var6, var2);
         String var10 = this.readUTF8(var6 + 2, var2);
         return new Handle(var4, var8, var9, var10, var7);
      case 16:
         return Type.getMethodType(this.readUTF8(var3, var2));
      }
   }
}
