package com.esotericsoftware.asm;

class MethodWriter extends MethodVisitor {
   final ClassWriter b;
   private int c;
   private final int d;
   private final int e;
   private final String f;
   String g;
   int h;
   int i;
   int j;
   int[] k;
   private ByteVector l;
   private AnnotationWriter m;
   private AnnotationWriter n;
   private AnnotationWriter U;
   private AnnotationWriter V;
   private AnnotationWriter[] o;
   private AnnotationWriter[] p;
   private int S;
   private Attribute q;
   private ByteVector r = new ByteVector();
   private int s;
   private int t;
   private int T;
   private int u;
   private ByteVector v;
   private int w;
   private int[] x;
   private int[] z;
   private int A;
   private Handler B;
   private Handler C;
   private int Z;
   private ByteVector $;
   private int D;
   private ByteVector E;
   private int F;
   private ByteVector G;
   private int H;
   private ByteVector I;
   private int Y;
   private AnnotationWriter W;
   private AnnotationWriter X;
   private Attribute J;
   private boolean K;
   private int L;
   private final int M;
   private Label N;
   private Label O;
   private Label P;
   private int Q;
   private int R;

   MethodWriter(ClassWriter var1, int var2, String var3, String var4, String var5, String[] var6, boolean var7, boolean var8) {
      super(327680);
      if (var1.D == null) {
         var1.D = this;
      } else {
         var1.E.mv = this;
      }

      var1.E = this;
      this.b = var1;
      this.c = var2;
      if ("<init>".equals(var3)) {
         this.c |= 524288;
      }

      this.d = var1.newUTF8(var3);
      this.e = var1.newUTF8(var4);
      this.f = var4;
      this.g = var5;
      int var9;
      if (var6 != null && var6.length > 0) {
         this.j = var6.length;
         this.k = new int[this.j];

         for(var9 = 0; var9 < this.j; ++var9) {
            this.k[var9] = var1.newClass(var6[var9]);
         }
      }

      this.M = var8 ? 0 : (var7 ? 1 : 2);
      if (var7 || var8) {
         var9 = Type.getArgumentsAndReturnSizes(this.f) >> 2;
         if ((var2 & 8) != 0) {
            --var9;
         }

         this.t = var9;
         this.T = var9;
         this.N = new Label();
         Label var10000 = this.N;
         var10000.a |= 8;
         this.visitLabel(this.N);
      }

   }

   public void visitParameter(String var1, int var2) {
      if (this.$ == null) {
         this.$ = new ByteVector();
      }

      ++this.Z;
      this.$.putShort(var1 == null ? 0 : this.b.newUTF8(var1)).putShort(var2);
   }

   public AnnotationVisitor visitAnnotationDefault() {
      this.l = new ByteVector();
      return new AnnotationWriter(this.b, false, this.l, (ByteVector)null, 0);
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      ByteVector var3 = new ByteVector();
      var3.putShort(this.b.newUTF8(var1)).putShort(0);
      AnnotationWriter var4 = new AnnotationWriter(this.b, true, var3, var3, 2);
      if (var2) {
         var4.g = this.m;
         this.m = var4;
      } else {
         var4.g = this.n;
         this.n = var4;
      }

      return var4;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.a(var1, var2, var5);
      var5.putShort(this.b.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.b, true, var5, var5, var5.b - 2);
      if (var4) {
         var6.g = this.U;
         this.U = var6;
      } else {
         var6.g = this.V;
         this.V = var6;
      }

      return var6;
   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      ByteVector var4 = new ByteVector();
      if ("Ljava/lang/Synthetic;".equals(var2)) {
         this.S = Math.max(this.S, var1 + 1);
         return new AnnotationWriter(this.b, false, var4, (ByteVector)null, 0);
      } else {
         var4.putShort(this.b.newUTF8(var2)).putShort(0);
         AnnotationWriter var5 = new AnnotationWriter(this.b, true, var4, var4, 2);
         if (var3) {
            if (this.o == null) {
               this.o = new AnnotationWriter[Type.getArgumentTypes(this.f).length];
            }

            var5.g = this.o[var1];
            this.o[var1] = var5;
         } else {
            if (this.p == null) {
               this.p = new AnnotationWriter[Type.getArgumentTypes(this.f).length];
            }

            var5.g = this.p[var1];
            this.p[var1] = var5;
         }

         return var5;
      }
   }

   public void visitAttribute(Attribute var1) {
      if (var1.isCodeAttribute()) {
         var1.a = this.J;
         this.J = var1;
      } else {
         var1.a = this.q;
         this.q = var1;
      }

   }

   public void visitCode() {
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      if (this.M != 0) {
         int var6;
         int var7;
         if (var1 == -1) {
            if (this.x == null) {
               this.f();
            }

            this.T = var2;
            var6 = this.a(this.r.b, var2, var4);

            for(var7 = 0; var7 < var2; ++var7) {
               if (var3[var7] instanceof String) {
                  this.z[var6++] = 24117248 | this.b.c((String)var3[var7]);
               } else if (var3[var7] instanceof Integer) {
                  this.z[var6++] = (Integer)var3[var7];
               } else {
                  this.z[var6++] = 25165824 | this.b.a("", ((Label)var3[var7]).c);
               }
            }

            for(var7 = 0; var7 < var4; ++var7) {
               if (var5[var7] instanceof String) {
                  this.z[var6++] = 24117248 | this.b.c((String)var5[var7]);
               } else if (var5[var7] instanceof Integer) {
                  this.z[var6++] = (Integer)var5[var7];
               } else {
                  this.z[var6++] = 25165824 | this.b.a("", ((Label)var5[var7]).c);
               }
            }

            this.b();
         } else {
            if (this.v == null) {
               this.v = new ByteVector();
               var6 = this.r.b;
            } else {
               var6 = this.r.b - this.w - 1;
               if (var6 < 0) {
                  if (var1 == 3) {
                     return;
                  }

                  throw new IllegalStateException();
               }
            }

            label79:
            switch(var1) {
            case 0:
               this.T = var2;
               this.v.putByte(255).putShort(var6).putShort(var2);

               for(var7 = 0; var7 < var2; ++var7) {
                  this.a(var3[var7]);
               }

               this.v.putShort(var4);

               for(var7 = 0; var7 < var4; ++var7) {
                  this.a(var5[var7]);
               }
               break;
            case 1:
               this.T += var2;
               this.v.putByte(251 + var2).putShort(var6);
               var7 = 0;

               while(true) {
                  if (var7 >= var2) {
                     break label79;
                  }

                  this.a(var3[var7]);
                  ++var7;
               }
            case 2:
               this.T -= var2;
               this.v.putByte(251 - var2).putShort(var6);
               break;
            case 3:
               if (var6 < 64) {
                  this.v.putByte(var6);
               } else {
                  this.v.putByte(251).putShort(var6);
               }
               break;
            case 4:
               if (var6 < 64) {
                  this.v.putByte(64 + var6);
               } else {
                  this.v.putByte(247).putShort(var6);
               }

               this.a(var5[0]);
            }

            this.w = this.r.b;
            ++this.u;
         }

         this.s = Math.max(this.s, var4);
         this.t = Math.max(this.t, this.T);
      }
   }

   public void visitInsn(int var1) {
      this.Y = this.r.b;
      this.r.putByte(var1);
      if (this.P != null) {
         if (this.M == 0) {
            this.P.h.a(var1, 0, (ClassWriter)null, (Item)null);
         } else {
            int var2 = this.Q + Frame.a[var1];
            if (var2 > this.R) {
               this.R = var2;
            }

            this.Q = var2;
         }

         if (var1 >= 172 && var1 <= 177 || var1 == 191) {
            this.e();
         }
      }

   }

   public void visitIntInsn(int var1, int var2) {
      this.Y = this.r.b;
      if (this.P != null) {
         if (this.M == 0) {
            this.P.h.a(var1, var2, (ClassWriter)null, (Item)null);
         } else if (var1 != 188) {
            int var3 = this.Q + 1;
            if (var3 > this.R) {
               this.R = var3;
            }

            this.Q = var3;
         }
      }

      if (var1 == 17) {
         this.r.b(var1, var2);
      } else {
         this.r.a(var1, var2);
      }

   }

   public void visitVarInsn(int var1, int var2) {
      this.Y = this.r.b;
      int var3;
      if (this.P != null) {
         if (this.M == 0) {
            this.P.h.a(var1, var2, (ClassWriter)null, (Item)null);
         } else if (var1 == 169) {
            Label var10000 = this.P;
            var10000.a |= 256;
            this.P.f = this.Q;
            this.e();
         } else {
            var3 = this.Q + Frame.a[var1];
            if (var3 > this.R) {
               this.R = var3;
            }

            this.Q = var3;
         }
      }

      if (this.M != 2) {
         if (var1 != 22 && var1 != 24 && var1 != 55 && var1 != 57) {
            var3 = var2 + 1;
         } else {
            var3 = var2 + 2;
         }

         if (var3 > this.t) {
            this.t = var3;
         }
      }

      if (var2 < 4 && var1 != 169) {
         if (var1 < 54) {
            var3 = 26 + (var1 - 21 << 2) + var2;
         } else {
            var3 = 59 + (var1 - 54 << 2) + var2;
         }

         this.r.putByte(var3);
      } else if (var2 >= 256) {
         this.r.putByte(196).b(var1, var2);
      } else {
         this.r.a(var1, var2);
      }

      if (var1 >= 54 && this.M == 0 && this.A > 0) {
         this.visitLabel(new Label());
      }

   }

   public void visitTypeInsn(int var1, String var2) {
      this.Y = this.r.b;
      Item var3 = this.b.a(var2);
      if (this.P != null) {
         if (this.M == 0) {
            this.P.h.a(var1, this.r.b, this.b, var3);
         } else if (var1 == 187) {
            int var4 = this.Q + 1;
            if (var4 > this.R) {
               this.R = var4;
            }

            this.Q = var4;
         }
      }

      this.r.b(var1, var3.a);
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.Y = this.r.b;
      Item var5 = this.b.a(var2, var3, var4);
      if (this.P != null) {
         if (this.M == 0) {
            this.P.h.a(var1, 0, this.b, var5);
         } else {
            int var7;
            label73: {
               char var6 = var4.charAt(0);
               switch(var1) {
               case 178:
                  var7 = this.Q + (var6 != 'D' && var6 != 'J' ? 1 : 2);
                  break label73;
               case 179:
                  var7 = this.Q + (var6 != 'D' && var6 != 'J' ? -1 : -2);
                  break label73;
               case 180:
                  var7 = this.Q + (var6 != 'D' && var6 != 'J' ? 0 : 1);
                  break label73;
               }

               var7 = this.Q + (var6 != 'D' && var6 != 'J' ? -2 : -3);
            }

            if (var7 > this.R) {
               this.R = var7;
            }

            this.Q = var7;
         }
      }

      this.r.b(var1, var5.a);
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      this.Y = this.r.b;
      Item var6 = this.b.a(var2, var3, var4, var5);
      int var7 = var6.c;
      if (this.P != null) {
         if (this.M == 0) {
            this.P.h.a(var1, 0, this.b, var6);
         } else {
            if (var7 == 0) {
               var7 = Type.getArgumentsAndReturnSizes(var4);
               var6.c = var7;
            }

            int var8;
            if (var1 == 184) {
               var8 = this.Q - (var7 >> 2) + (var7 & 3) + 1;
            } else {
               var8 = this.Q - (var7 >> 2) + (var7 & 3);
            }

            if (var8 > this.R) {
               this.R = var8;
            }

            this.Q = var8;
         }
      }

      if (var1 == 185) {
         if (var7 == 0) {
            var7 = Type.getArgumentsAndReturnSizes(var4);
            var6.c = var7;
         }

         this.r.b(185, var6.a).a(var7 >> 2, 0);
      } else {
         this.r.b(var1, var6.a);
      }

   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.Y = this.r.b;
      Item var5 = this.b.a(var1, var2, var3, var4);
      int var6 = var5.c;
      if (this.P != null) {
         if (this.M == 0) {
            this.P.h.a(186, 0, this.b, var5);
         } else {
            if (var6 == 0) {
               var6 = Type.getArgumentsAndReturnSizes(var2);
               var5.c = var6;
            }

            int var7 = this.Q - (var6 >> 2) + (var6 & 3) + 1;
            if (var7 > this.R) {
               this.R = var7;
            }

            this.Q = var7;
         }
      }

      this.r.b(186, var5.a);
      this.r.putShort(0);
   }

   public void visitJumpInsn(int var1, Label var2) {
      this.Y = this.r.b;
      Label var3 = null;
      if (this.P != null) {
         Label var10000;
         if (this.M == 0) {
            this.P.h.a(var1, 0, (ClassWriter)null, (Item)null);
            var10000 = var2.a();
            var10000.a |= 16;
            this.a(0, var2);
            if (var1 != 167) {
               var3 = new Label();
            }
         } else if (var1 == 168) {
            if ((var2.a & 512) == 0) {
               var2.a |= 512;
               ++this.L;
            }

            var10000 = this.P;
            var10000.a |= 128;
            this.a(this.Q + 1, var2);
            var3 = new Label();
         } else {
            this.Q += Frame.a[var1];
            this.a(this.Q, var2);
         }
      }

      if ((var2.a & 2) != 0 && var2.c - this.r.b < -32768) {
         if (var1 == 167) {
            this.r.putByte(200);
         } else if (var1 == 168) {
            this.r.putByte(201);
         } else {
            if (var3 != null) {
               var3.a |= 16;
            }

            this.r.putByte(var1 <= 166 ? (var1 + 1 ^ 1) - 1 : var1 ^ 1);
            this.r.putShort(8);
            this.r.putByte(200);
         }

         var2.a(this, this.r, this.r.b - 1, true);
      } else {
         this.r.putByte(var1);
         var2.a(this, this.r, this.r.b - 1, false);
      }

      if (this.P != null) {
         if (var3 != null) {
            this.visitLabel(var3);
         }

         if (var1 == 167) {
            this.e();
         }
      }

   }

   public void visitLabel(Label var1) {
      this.K |= var1.a(this, this.r.b, this.r.a);
      if ((var1.a & 1) == 0) {
         if (this.M == 0) {
            Label var10000;
            if (this.P != null) {
               if (var1.c == this.P.c) {
                  var10000 = this.P;
                  var10000.a |= var1.a & 16;
                  var1.h = this.P.h;
                  return;
               }

               this.a(0, var1);
            }

            this.P = var1;
            if (var1.h == null) {
               var1.h = new Frame();
               var1.h.b = var1;
            }

            if (this.O != null) {
               if (var1.c == this.O.c) {
                  var10000 = this.O;
                  var10000.a |= var1.a & 16;
                  var1.h = this.O.h;
                  this.P = this.O;
                  return;
               }

               this.O.i = var1;
            }

            this.O = var1;
         } else if (this.M == 1) {
            if (this.P != null) {
               this.P.g = this.R;
               this.a(this.Q, var1);
            }

            this.P = var1;
            this.Q = 0;
            this.R = 0;
            if (this.O != null) {
               this.O.i = var1;
            }

            this.O = var1;
         }

      }
   }

   public void visitLdcInsn(Object var1) {
      this.Y = this.r.b;
      Item var2 = this.b.a(var1);
      int var3;
      if (this.P != null) {
         if (this.M == 0) {
            this.P.h.a(18, 0, this.b, var2);
         } else {
            if (var2.b != 5 && var2.b != 6) {
               var3 = this.Q + 1;
            } else {
               var3 = this.Q + 2;
            }

            if (var3 > this.R) {
               this.R = var3;
            }

            this.Q = var3;
         }
      }

      var3 = var2.a;
      if (var2.b != 5 && var2.b != 6) {
         if (var3 >= 256) {
            this.r.b(19, var3);
         } else {
            this.r.a(18, var3);
         }
      } else {
         this.r.b(20, var3);
      }

   }

   public void visitIincInsn(int var1, int var2) {
      this.Y = this.r.b;
      if (this.P != null && this.M == 0) {
         this.P.h.a(132, var1, (ClassWriter)null, (Item)null);
      }

      if (this.M != 2) {
         int var3 = var1 + 1;
         if (var3 > this.t) {
            this.t = var3;
         }
      }

      if (var1 <= 255 && var2 <= 127 && var2 >= -128) {
         this.r.putByte(132).a(var1, var2);
      } else {
         this.r.putByte(196).b(132, var1).putShort(var2);
      }

   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.Y = this.r.b;
      int var5 = this.r.b;
      this.r.putByte(170);
      this.r.putByteArray((byte[])null, 0, (4 - this.r.b % 4) % 4);
      var3.a(this, this.r, var5, true);
      this.r.putInt(var1).putInt(var2);

      for(int var6 = 0; var6 < var4.length; ++var6) {
         var4[var6].a(this, this.r, var5, true);
      }

      this.a(var3, var4);
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.Y = this.r.b;
      int var4 = this.r.b;
      this.r.putByte(171);
      this.r.putByteArray((byte[])null, 0, (4 - this.r.b % 4) % 4);
      var1.a(this, this.r, var4, true);
      this.r.putInt(var3.length);

      for(int var5 = 0; var5 < var3.length; ++var5) {
         this.r.putInt(var2[var5]);
         var3[var5].a(this, this.r, var4, true);
      }

      this.a(var1, var3);
   }

   private void a(Label var1, Label[] var2) {
      if (this.P != null) {
         int var3;
         if (this.M == 0) {
            this.P.h.a(171, 0, (ClassWriter)null, (Item)null);
            this.a(0, var1);
            Label var10000 = var1.a();
            var10000.a |= 16;

            for(var3 = 0; var3 < var2.length; ++var3) {
               this.a(0, var2[var3]);
               var10000 = var2[var3].a();
               var10000.a |= 16;
            }
         } else {
            --this.Q;
            this.a(this.Q, var1);

            for(var3 = 0; var3 < var2.length; ++var3) {
               this.a(this.Q, var2[var3]);
            }
         }

         this.e();
      }

   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.Y = this.r.b;
      Item var3 = this.b.a(var1);
      if (this.P != null) {
         if (this.M == 0) {
            this.P.h.a(197, var2, this.b, var3);
         } else {
            this.Q += 1 - var2;
         }
      }

      this.r.b(197, var3.a).putByte(var2);
   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      var1 = var1 & -16776961 | this.Y << 8;
      AnnotationWriter.a(var1, var2, var5);
      var5.putShort(this.b.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.b, true, var5, var5, var5.b - 2);
      if (var4) {
         var6.g = this.W;
         this.W = var6;
      } else {
         var6.g = this.X;
         this.X = var6;
      }

      return var6;
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      ++this.A;
      Handler var5 = new Handler();
      var5.a = var1;
      var5.b = var2;
      var5.c = var3;
      var5.d = var4;
      var5.e = var4 != null ? this.b.newClass(var4) : 0;
      if (this.C == null) {
         this.B = var5;
      } else {
         this.C.f = var5;
      }

      this.C = var5;
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.a(var1, var2, var5);
      var5.putShort(this.b.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.b, true, var5, var5, var5.b - 2);
      if (var4) {
         var6.g = this.W;
         this.W = var6;
      } else {
         var6.g = this.X;
         this.X = var6;
      }

      return var6;
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      if (var3 != null) {
         if (this.G == null) {
            this.G = new ByteVector();
         }

         ++this.F;
         this.G.putShort(var4.c).putShort(var5.c - var4.c).putShort(this.b.newUTF8(var1)).putShort(this.b.newUTF8(var3)).putShort(var6);
      }

      if (this.E == null) {
         this.E = new ByteVector();
      }

      ++this.D;
      this.E.putShort(var4.c).putShort(var5.c - var4.c).putShort(this.b.newUTF8(var1)).putShort(this.b.newUTF8(var2)).putShort(var6);
      if (this.M != 2) {
         char var7 = var2.charAt(0);
         int var8 = var6 + (var7 != 'J' && var7 != 'D' ? 1 : 2);
         if (var8 > this.t) {
            this.t = var8;
         }
      }

   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      ByteVector var8 = new ByteVector();
      var8.putByte(var1 >>> 24).putShort(var3.length);

      int var9;
      for(var9 = 0; var9 < var3.length; ++var9) {
         var8.putShort(var3[var9].c).putShort(var4[var9].c - var3[var9].c).putShort(var5[var9]);
      }

      if (var2 == null) {
         var8.putByte(0);
      } else {
         var9 = var2.a[var2.b] * 2 + 1;
         var8.putByteArray(var2.a, var2.b, var9);
      }

      var8.putShort(this.b.newUTF8(var6)).putShort(0);
      AnnotationWriter var10 = new AnnotationWriter(this.b, true, var8, var8, var8.b - 2);
      if (var7) {
         var10.g = this.W;
         this.W = var10;
      } else {
         var10.g = this.X;
         this.X = var10;
      }

      return var10;
   }

   public void visitLineNumber(int var1, Label var2) {
      if (this.I == null) {
         this.I = new ByteVector();
      }

      ++this.H;
      this.I.putShort(var2.c);
      this.I.putShort(var1);
   }

   public void visitMaxs(int var1, int var2) {
      if (this.K) {
         this.d();
      }

      Handler var3;
      Label var4;
      Label var5;
      Label var6;
      int var8;
      Edge var9;
      Label var17;
      if (this.M == 0) {
         for(var3 = this.B; var3 != null; var3 = var3.f) {
            var4 = var3.a.a();
            var5 = var3.c.a();
            var6 = var3.b.a();
            String var7 = var3.d == null ? "java/lang/Throwable" : var3.d;
            var8 = 24117248 | this.b.c(var7);

            for(var5.a |= 16; var4 != var6; var4 = var4.i) {
               var9 = new Edge();
               var9.a = var8;
               var9.b = var5;
               var9.c = var4.j;
               var4.j = var9;
            }
         }

         Frame var13 = this.N.h;
         Type[] var14 = Type.getArgumentTypes(this.f);
         var13.a(this.b, this.c, var14, this.t);
         this.b(var13);
         int var15 = 0;
         var17 = this.N;

         Label var18;
         while(var17 != null) {
            var18 = var17;
            var17 = var17.k;
            var18.k = null;
            var13 = var18.h;
            if ((var18.a & 16) != 0) {
               var18.a |= 32;
            }

            var18.a |= 64;
            int var21 = var13.d.length + var18.g;
            if (var21 > var15) {
               var15 = var21;
            }

            for(Edge var10 = var18.j; var10 != null; var10 = var10.c) {
               Label var11 = var10.b.a();
               boolean var12 = var13.a(this.b, var11.h, var10.a);
               if (var12 && var11.k == null) {
                  var11.k = var17;
                  var17 = var11;
               }
            }
         }

         for(var18 = this.N; var18 != null; var18 = var18.i) {
            var13 = var18.h;
            if ((var18.a & 32) != 0) {
               this.b(var13);
            }

            if ((var18.a & 64) == 0) {
               Label var22 = var18.i;
               int var23 = var18.c;
               int var24 = (var22 == null ? this.r.b : var22.c) - 1;
               if (var24 >= var23) {
                  var15 = Math.max(var15, 1);

                  int var25;
                  for(var25 = var23; var25 < var24; ++var25) {
                     this.r.a[var25] = 0;
                  }

                  this.r.a[var24] = -65;
                  var25 = this.a(var23, 0, 1);
                  this.z[var25] = 24117248 | this.b.c("java/lang/Throwable");
                  this.b();
                  this.B = Handler.a(this.B, var18, var22);
               }
            }
         }

         var3 = this.B;

         for(this.A = 0; var3 != null; var3 = var3.f) {
            ++this.A;
         }

         this.s = var15;
      } else if (this.M == 1) {
         for(var3 = this.B; var3 != null; var3 = var3.f) {
            var4 = var3.a;
            var5 = var3.c;

            for(var6 = var3.b; var4 != var6; var4 = var4.i) {
               Edge var19 = new Edge();
               var19.a = Integer.MAX_VALUE;
               var19.b = var5;
               if ((var4.a & 128) == 0) {
                  var19.c = var4.j;
                  var4.j = var19;
               } else {
                  var19.c = var4.j.c.c;
                  var4.j.c.c = var19;
               }
            }
         }

         int var16;
         if (this.L > 0) {
            var16 = 0;
            this.N.b((Label)null, 1L, this.L);

            for(var5 = this.N; var5 != null; var5 = var5.i) {
               if ((var5.a & 128) != 0) {
                  var6 = var5.j.c.b;
                  if ((var6.a & 1024) == 0) {
                     ++var16;
                     var6.b((Label)null, (long)var16 / 32L << 32 | 1L << var16 % 32, this.L);
                  }
               }
            }

            for(var5 = this.N; var5 != null; var5 = var5.i) {
               if ((var5.a & 128) != 0) {
                  for(var6 = this.N; var6 != null; var6 = var6.i) {
                     var6.a &= -2049;
                  }

                  var17 = var5.j.c.b;
                  var17.b(var5, 0L, this.L);
               }
            }
         }

         var16 = 0;
         var5 = this.N;

         while(var5 != null) {
            var6 = var5;
            var5 = var5.k;
            int var20 = var6.f;
            var8 = var20 + var6.g;
            if (var8 > var16) {
               var16 = var8;
            }

            var9 = var6.j;
            if ((var6.a & 128) != 0) {
               var9 = var9.c;
            }

            for(; var9 != null; var9 = var9.c) {
               var6 = var9.b;
               if ((var6.a & 8) == 0) {
                  var6.f = var9.a == Integer.MAX_VALUE ? 1 : var20 + var9.a;
                  var6.a |= 8;
                  var6.k = var5;
                  var5 = var6;
               }
            }
         }

         this.s = Math.max(var1, var16);
      } else {
         this.s = var1;
         this.t = var2;
      }

   }

   public void visitEnd() {
   }

   private void a(int var1, Label var2) {
      Edge var3 = new Edge();
      var3.a = var1;
      var3.b = var2;
      var3.c = this.P.j;
      this.P.j = var3;
   }

   private void e() {
      if (this.M == 0) {
         Label var1 = new Label();
         var1.h = new Frame();
         var1.h.b = var1;
         var1.a(this, this.r.b, this.r.a);
         this.O.i = var1;
         this.O = var1;
      } else {
         this.P.g = this.R;
      }

      this.P = null;
   }

   private void b(Frame var1) {
      int var2 = 0;
      int var3 = 0;
      int var4 = 0;
      int[] var5 = var1.c;
      int[] var6 = var1.d;

      int var7;
      int var8;
      for(var7 = 0; var7 < var5.length; ++var7) {
         var8 = var5[var7];
         if (var8 == 16777216) {
            ++var2;
         } else {
            var3 += var2 + 1;
            var2 = 0;
         }

         if (var8 == 16777220 || var8 == 16777219) {
            ++var7;
         }
      }

      for(var7 = 0; var7 < var6.length; ++var7) {
         var8 = var6[var7];
         ++var4;
         if (var8 == 16777220 || var8 == 16777219) {
            ++var7;
         }
      }

      int var9 = this.a(var1.b.c, var3, var4);

      for(var7 = 0; var3 > 0; --var3) {
         var8 = var5[var7];
         this.z[var9++] = var8;
         if (var8 == 16777220 || var8 == 16777219) {
            ++var7;
         }

         ++var7;
      }

      for(var7 = 0; var7 < var6.length; ++var7) {
         var8 = var6[var7];
         this.z[var9++] = var8;
         if (var8 == 16777220 || var8 == 16777219) {
            ++var7;
         }
      }

      this.b();
   }

   private void f() {
      int var1 = this.a(0, this.f.length() + 1, 0);
      if ((this.c & 8) == 0) {
         if ((this.c & 524288) == 0) {
            this.z[var1++] = 24117248 | this.b.c(this.b.I);
         } else {
            this.z[var1++] = 6;
         }
      }

      int var2 = 1;

      while(true) {
         int var3 = var2;
         switch(this.f.charAt(var2++)) {
         case 'B':
         case 'C':
         case 'I':
         case 'S':
         case 'Z':
            this.z[var1++] = 1;
            break;
         case 'D':
            this.z[var1++] = 3;
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
            this.z[1] = var1 - 3;
            this.b();
            return;
         case 'F':
            this.z[var1++] = 2;
            break;
         case 'J':
            this.z[var1++] = 4;
            break;
         case 'L':
            while(this.f.charAt(var2) != ';') {
               ++var2;
            }

            this.z[var1++] = 24117248 | this.b.c(this.f.substring(var3 + 1, var2++));
            break;
         case '[':
            while(this.f.charAt(var2) == '[') {
               ++var2;
            }

            if (this.f.charAt(var2) == 'L') {
               ++var2;

               while(this.f.charAt(var2) != ';') {
                  ++var2;
               }
            }

            int var10001 = var1++;
            ++var2;
            this.z[var10001] = 24117248 | this.b.c(this.f.substring(var3, var2));
         }
      }
   }

   private int a(int var1, int var2, int var3) {
      int var4 = 3 + var2 + var3;
      if (this.z == null || this.z.length < var4) {
         this.z = new int[var4];
      }

      this.z[0] = var1;
      this.z[1] = var2;
      this.z[2] = var3;
      return 3;
   }

   private void b() {
      if (this.x != null) {
         if (this.v == null) {
            this.v = new ByteVector();
         }

         this.c();
         ++this.u;
      }

      this.x = this.z;
      this.z = null;
   }

   private void c() {
      int var1 = this.z[1];
      int var2 = this.z[2];
      if ((this.b.b & '\uffff') < 50) {
         this.v.putShort(this.z[0]).putShort(var1);
         this.a(3, 3 + var1);
         this.v.putShort(var2);
         this.a(3 + var1, 3 + var1 + var2);
      } else {
         int var3 = this.x[1];
         int var4 = 255;
         int var5 = 0;
         int var6;
         if (this.u == 0) {
            var6 = this.z[0];
         } else {
            var6 = this.z[0] - this.x[0] - 1;
         }

         if (var2 == 0) {
            var5 = var1 - var3;
            switch(var5) {
            case -3:
            case -2:
            case -1:
               var4 = 248;
               var3 = var1;
               break;
            case 0:
               var4 = var6 < 64 ? 0 : 251;
               break;
            case 1:
            case 2:
            case 3:
               var4 = 252;
            }
         } else if (var1 == var3 && var2 == 1) {
            var4 = var6 < 63 ? 64 : 247;
         }

         if (var4 != 255) {
            int var7 = 3;

            for(int var8 = 0; var8 < var3; ++var8) {
               if (this.z[var7] != this.x[var7]) {
                  var4 = 255;
                  break;
               }

               ++var7;
            }
         }

         switch(var4) {
         case 0:
            this.v.putByte(var6);
            break;
         case 64:
            this.v.putByte(64 + var6);
            this.a(3 + var1, 4 + var1);
            break;
         case 247:
            this.v.putByte(247).putShort(var6);
            this.a(3 + var1, 4 + var1);
            break;
         case 248:
            this.v.putByte(251 + var5).putShort(var6);
            break;
         case 251:
            this.v.putByte(251).putShort(var6);
            break;
         case 252:
            this.v.putByte(251 + var5).putShort(var6);
            this.a(3 + var3, 3 + var1);
            break;
         default:
            this.v.putByte(255).putShort(var6).putShort(var1);
            this.a(3, 3 + var1);
            this.v.putShort(var2);
            this.a(3 + var1, 3 + var1 + var2);
         }

      }
   }

   private void a(int var1, int var2) {
      for(int var3 = var1; var3 < var2; ++var3) {
         int var4 = this.z[var3];
         int var5 = var4 & -268435456;
         if (var5 == 0) {
            int var7 = var4 & 1048575;
            switch(var4 & 267386880) {
            case 24117248:
               this.v.putByte(7).putShort(this.b.newClass(this.b.H[var7].g));
               break;
            case 25165824:
               this.v.putByte(8).putShort(this.b.H[var7].c);
               break;
            default:
               this.v.putByte(var7);
            }
         } else {
            StringBuffer var6 = new StringBuffer();
            var5 >>= 28;

            while(var5-- > 0) {
               var6.append('[');
            }

            if ((var4 & 267386880) == 24117248) {
               var6.append('L');
               var6.append(this.b.H[var4 & 1048575].g);
               var6.append(';');
            } else {
               switch(var4 & 15) {
               case 1:
                  var6.append('I');
                  break;
               case 2:
                  var6.append('F');
                  break;
               case 3:
                  var6.append('D');
                  break;
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
               default:
                  var6.append('J');
                  break;
               case 9:
                  var6.append('Z');
                  break;
               case 10:
                  var6.append('B');
                  break;
               case 11:
                  var6.append('C');
                  break;
               case 12:
                  var6.append('S');
               }
            }

            this.v.putByte(7).putShort(this.b.newClass(var6.toString()));
         }
      }

   }

   private void a(Object var1) {
      if (var1 instanceof String) {
         this.v.putByte(7).putShort(this.b.newClass((String)var1));
      } else if (var1 instanceof Integer) {
         this.v.putByte((Integer)var1);
      } else {
         this.v.putByte(8).putShort(((Label)var1).c);
      }

   }

   final int a() {
      if (this.h != 0) {
         return 6 + this.i;
      } else {
         int var1 = 8;
         if (this.r.b > 0) {
            if (this.r.b > 65535) {
               throw new RuntimeException("Method code too large!");
            }

            this.b.newUTF8("Code");
            var1 += 18 + this.r.b + 8 * this.A;
            if (this.E != null) {
               this.b.newUTF8("LocalVariableTable");
               var1 += 8 + this.E.b;
            }

            if (this.G != null) {
               this.b.newUTF8("LocalVariableTypeTable");
               var1 += 8 + this.G.b;
            }

            if (this.I != null) {
               this.b.newUTF8("LineNumberTable");
               var1 += 8 + this.I.b;
            }

            if (this.v != null) {
               boolean var2 = (this.b.b & '\uffff') >= 50;
               this.b.newUTF8(var2 ? "StackMapTable" : "StackMap");
               var1 += 8 + this.v.b;
            }

            if (this.W != null) {
               this.b.newUTF8("RuntimeVisibleTypeAnnotations");
               var1 += 8 + this.W.a();
            }

            if (this.X != null) {
               this.b.newUTF8("RuntimeInvisibleTypeAnnotations");
               var1 += 8 + this.X.a();
            }

            if (this.J != null) {
               var1 += this.J.a(this.b, this.r.a, this.r.b, this.s, this.t);
            }
         }

         if (this.j > 0) {
            this.b.newUTF8("Exceptions");
            var1 += 8 + 2 * this.j;
         }

         if ((this.c & 4096) != 0 && ((this.b.b & '\uffff') < 49 || (this.c & 262144) != 0)) {
            this.b.newUTF8("Synthetic");
            var1 += 6;
         }

         if ((this.c & 131072) != 0) {
            this.b.newUTF8("Deprecated");
            var1 += 6;
         }

         if (this.g != null) {
            this.b.newUTF8("Signature");
            this.b.newUTF8(this.g);
            var1 += 8;
         }

         if (this.$ != null) {
            this.b.newUTF8("MethodParameters");
            var1 += 7 + this.$.b;
         }

         if (this.l != null) {
            this.b.newUTF8("AnnotationDefault");
            var1 += 6 + this.l.b;
         }

         if (this.m != null) {
            this.b.newUTF8("RuntimeVisibleAnnotations");
            var1 += 8 + this.m.a();
         }

         if (this.n != null) {
            this.b.newUTF8("RuntimeInvisibleAnnotations");
            var1 += 8 + this.n.a();
         }

         if (this.U != null) {
            this.b.newUTF8("RuntimeVisibleTypeAnnotations");
            var1 += 8 + this.U.a();
         }

         if (this.V != null) {
            this.b.newUTF8("RuntimeInvisibleTypeAnnotations");
            var1 += 8 + this.V.a();
         }

         int var3;
         if (this.o != null) {
            this.b.newUTF8("RuntimeVisibleParameterAnnotations");
            var1 += 7 + 2 * (this.o.length - this.S);

            for(var3 = this.o.length - 1; var3 >= this.S; --var3) {
               var1 += this.o[var3] == null ? 0 : this.o[var3].a();
            }
         }

         if (this.p != null) {
            this.b.newUTF8("RuntimeInvisibleParameterAnnotations");
            var1 += 7 + 2 * (this.p.length - this.S);

            for(var3 = this.p.length - 1; var3 >= this.S; --var3) {
               var1 += this.p[var3] == null ? 0 : this.p[var3].a();
            }
         }

         if (this.q != null) {
            var1 += this.q.a(this.b, (byte[])null, 0, -1, -1);
         }

         return var1;
      }
   }

   final void a(ByteVector var1) {
      boolean var2 = true;
      int var3 = 917504 | (this.c & 262144) / 64;
      var1.putShort(this.c & ~var3).putShort(this.d).putShort(this.e);
      if (this.h != 0) {
         var1.putByteArray(this.b.M.b, this.h, this.i);
      } else {
         int var4 = 0;
         if (this.r.b > 0) {
            ++var4;
         }

         if (this.j > 0) {
            ++var4;
         }

         if ((this.c & 4096) != 0 && ((this.b.b & '\uffff') < 49 || (this.c & 262144) != 0)) {
            ++var4;
         }

         if ((this.c & 131072) != 0) {
            ++var4;
         }

         if (this.g != null) {
            ++var4;
         }

         if (this.$ != null) {
            ++var4;
         }

         if (this.l != null) {
            ++var4;
         }

         if (this.m != null) {
            ++var4;
         }

         if (this.n != null) {
            ++var4;
         }

         if (this.U != null) {
            ++var4;
         }

         if (this.V != null) {
            ++var4;
         }

         if (this.o != null) {
            ++var4;
         }

         if (this.p != null) {
            ++var4;
         }

         if (this.q != null) {
            var4 += this.q.a();
         }

         var1.putShort(var4);
         int var5;
         if (this.r.b > 0) {
            var5 = 12 + this.r.b + 8 * this.A;
            if (this.E != null) {
               var5 += 8 + this.E.b;
            }

            if (this.G != null) {
               var5 += 8 + this.G.b;
            }

            if (this.I != null) {
               var5 += 8 + this.I.b;
            }

            if (this.v != null) {
               var5 += 8 + this.v.b;
            }

            if (this.W != null) {
               var5 += 8 + this.W.a();
            }

            if (this.X != null) {
               var5 += 8 + this.X.a();
            }

            if (this.J != null) {
               var5 += this.J.a(this.b, this.r.a, this.r.b, this.s, this.t);
            }

            var1.putShort(this.b.newUTF8("Code")).putInt(var5);
            var1.putShort(this.s).putShort(this.t);
            var1.putInt(this.r.b).putByteArray(this.r.a, 0, this.r.b);
            var1.putShort(this.A);
            if (this.A > 0) {
               for(Handler var6 = this.B; var6 != null; var6 = var6.f) {
                  var1.putShort(var6.a.c).putShort(var6.b.c).putShort(var6.c.c).putShort(var6.e);
               }
            }

            var4 = 0;
            if (this.E != null) {
               ++var4;
            }

            if (this.G != null) {
               ++var4;
            }

            if (this.I != null) {
               ++var4;
            }

            if (this.v != null) {
               ++var4;
            }

            if (this.W != null) {
               ++var4;
            }

            if (this.X != null) {
               ++var4;
            }

            if (this.J != null) {
               var4 += this.J.a();
            }

            var1.putShort(var4);
            if (this.E != null) {
               var1.putShort(this.b.newUTF8("LocalVariableTable"));
               var1.putInt(this.E.b + 2).putShort(this.D);
               var1.putByteArray(this.E.a, 0, this.E.b);
            }

            if (this.G != null) {
               var1.putShort(this.b.newUTF8("LocalVariableTypeTable"));
               var1.putInt(this.G.b + 2).putShort(this.F);
               var1.putByteArray(this.G.a, 0, this.G.b);
            }

            if (this.I != null) {
               var1.putShort(this.b.newUTF8("LineNumberTable"));
               var1.putInt(this.I.b + 2).putShort(this.H);
               var1.putByteArray(this.I.a, 0, this.I.b);
            }

            if (this.v != null) {
               boolean var7 = (this.b.b & '\uffff') >= 50;
               var1.putShort(this.b.newUTF8(var7 ? "StackMapTable" : "StackMap"));
               var1.putInt(this.v.b + 2).putShort(this.u);
               var1.putByteArray(this.v.a, 0, this.v.b);
            }

            if (this.W != null) {
               var1.putShort(this.b.newUTF8("RuntimeVisibleTypeAnnotations"));
               this.W.a(var1);
            }

            if (this.X != null) {
               var1.putShort(this.b.newUTF8("RuntimeInvisibleTypeAnnotations"));
               this.X.a(var1);
            }

            if (this.J != null) {
               this.J.a(this.b, this.r.a, this.r.b, this.t, this.s, var1);
            }
         }

         if (this.j > 0) {
            var1.putShort(this.b.newUTF8("Exceptions")).putInt(2 * this.j + 2);
            var1.putShort(this.j);

            for(var5 = 0; var5 < this.j; ++var5) {
               var1.putShort(this.k[var5]);
            }
         }

         if ((this.c & 4096) != 0 && ((this.b.b & '\uffff') < 49 || (this.c & 262144) != 0)) {
            var1.putShort(this.b.newUTF8("Synthetic")).putInt(0);
         }

         if ((this.c & 131072) != 0) {
            var1.putShort(this.b.newUTF8("Deprecated")).putInt(0);
         }

         if (this.g != null) {
            var1.putShort(this.b.newUTF8("Signature")).putInt(2).putShort(this.b.newUTF8(this.g));
         }

         if (this.$ != null) {
            var1.putShort(this.b.newUTF8("MethodParameters"));
            var1.putInt(this.$.b + 1).putByte(this.Z);
            var1.putByteArray(this.$.a, 0, this.$.b);
         }

         if (this.l != null) {
            var1.putShort(this.b.newUTF8("AnnotationDefault"));
            var1.putInt(this.l.b);
            var1.putByteArray(this.l.a, 0, this.l.b);
         }

         if (this.m != null) {
            var1.putShort(this.b.newUTF8("RuntimeVisibleAnnotations"));
            this.m.a(var1);
         }

         if (this.n != null) {
            var1.putShort(this.b.newUTF8("RuntimeInvisibleAnnotations"));
            this.n.a(var1);
         }

         if (this.U != null) {
            var1.putShort(this.b.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.U.a(var1);
         }

         if (this.V != null) {
            var1.putShort(this.b.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.V.a(var1);
         }

         if (this.o != null) {
            var1.putShort(this.b.newUTF8("RuntimeVisibleParameterAnnotations"));
            AnnotationWriter.a(this.o, this.S, var1);
         }

         if (this.p != null) {
            var1.putShort(this.b.newUTF8("RuntimeInvisibleParameterAnnotations"));
            AnnotationWriter.a(this.p, this.S, var1);
         }

         if (this.q != null) {
            this.q.a(this.b, (byte[])null, 0, -1, -1, var1);
         }

      }
   }

   private void d() {
      byte[] var1 = this.r.a;
      int[] var2 = new int[0];
      int[] var3 = new int[0];
      boolean[] var4 = new boolean[this.r.b];
      int var5 = 3;

      int var6;
      int var8;
      int var9;
      int var10;
      do {
         if (var5 == 3) {
            var5 = 2;
         }

         var6 = 0;

         while(var6 < var1.length) {
            int var7 = var1[var6] & 255;
            var8 = 0;
            switch(ClassWriter.a[var7]) {
            case 0:
            case 4:
               ++var6;
               break;
            case 1:
            case 3:
            case 11:
               var6 += 2;
               break;
            case 2:
            case 5:
            case 6:
            case 12:
            case 13:
               var6 += 3;
               break;
            case 7:
            case 8:
               var6 += 5;
               break;
            case 9:
               if (var7 > 201) {
                  var7 = var7 < 218 ? var7 - 49 : var7 - 20;
                  var9 = var6 + c(var1, var6 + 1);
               } else {
                  var9 = var6 + b(var1, var6 + 1);
               }

               var10 = a(var2, var3, var6, var9);
               if ((var10 < -32768 || var10 > 32767) && !var4[var6]) {
                  if (var7 != 167 && var7 != 168) {
                     var8 = 5;
                  } else {
                     var8 = 2;
                  }

                  var4[var6] = true;
               }

               var6 += 3;
               break;
            case 10:
               var6 += 5;
               break;
            case 14:
               if (var5 == 1) {
                  var10 = a(var2, var3, 0, var6);
                  var8 = -(var10 & 3);
               } else if (!var4[var6]) {
                  var8 = var6 & 3;
                  var4[var6] = true;
               }

               var6 = var6 + 4 - (var6 & 3);
               var6 += 4 * (a(var1, var6 + 8) - a(var1, var6 + 4) + 1) + 12;
               break;
            case 15:
               if (var5 == 1) {
                  var10 = a(var2, var3, 0, var6);
                  var8 = -(var10 & 3);
               } else if (!var4[var6]) {
                  var8 = var6 & 3;
                  var4[var6] = true;
               }

               var6 = var6 + 4 - (var6 & 3);
               var6 += 8 * a(var1, var6 + 4) + 8;
               break;
            case 16:
            default:
               var6 += 4;
               break;
            case 17:
               var7 = var1[var6 + 1] & 255;
               if (var7 == 132) {
                  var6 += 6;
               } else {
                  var6 += 4;
               }
            }

            if (var8 != 0) {
               int[] var11 = new int[var2.length + 1];
               int[] var12 = new int[var3.length + 1];
               System.arraycopy(var2, 0, var11, 0, var2.length);
               System.arraycopy(var3, 0, var12, 0, var3.length);
               var11[var2.length] = var6;
               var12[var3.length] = var8;
               var2 = var11;
               var3 = var12;
               if (var8 > 0) {
                  var5 = 3;
               }
            }
         }

         if (var5 < 3) {
            --var5;
         }
      } while(var5 != 0);

      ByteVector var16 = new ByteVector(this.r.b);
      var6 = 0;

      while(true) {
         label232:
         while(var6 < this.r.b) {
            var8 = var1[var6] & 255;
            int var13;
            int var14;
            switch(ClassWriter.a[var8]) {
            case 0:
            case 4:
               var16.putByte(var8);
               ++var6;
               break;
            case 1:
            case 3:
            case 11:
               var16.putByteArray(var1, var6, 2);
               var6 += 2;
               break;
            case 2:
            case 5:
            case 6:
            case 12:
            case 13:
               var16.putByteArray(var1, var6, 3);
               var6 += 3;
               break;
            case 7:
            case 8:
               var16.putByteArray(var1, var6, 5);
               var6 += 5;
               break;
            case 9:
               if (var8 > 201) {
                  var8 = var8 < 218 ? var8 - 49 : var8 - 20;
                  var9 = var6 + c(var1, var6 + 1);
               } else {
                  var9 = var6 + b(var1, var6 + 1);
               }

               var10 = a(var2, var3, var6, var9);
               if (var4[var6]) {
                  if (var8 == 167) {
                     var16.putByte(200);
                  } else if (var8 == 168) {
                     var16.putByte(201);
                  } else {
                     var16.putByte(var8 <= 166 ? (var8 + 1 ^ 1) - 1 : var8 ^ 1);
                     var16.putShort(8);
                     var16.putByte(200);
                     var10 -= 3;
                  }

                  var16.putInt(var10);
               } else {
                  var16.putByte(var8);
                  var16.putShort(var10);
               }

               var6 += 3;
               break;
            case 10:
               var9 = var6 + a(var1, var6 + 1);
               var10 = a(var2, var3, var6, var9);
               var16.putByte(var8);
               var16.putInt(var10);
               var6 += 5;
               break;
            case 14:
               var13 = var6;
               var6 = var6 + 4 - (var6 & 3);
               var16.putByte(170);
               var16.putByteArray((byte[])null, 0, (4 - var16.b % 4) % 4);
               var9 = var13 + a(var1, var6);
               var6 += 4;
               var10 = a(var2, var3, var13, var9);
               var16.putInt(var10);
               var14 = a(var1, var6);
               var6 += 4;
               var16.putInt(var14);
               var14 = a(var1, var6) - var14 + 1;
               var6 += 4;
               var16.putInt(a(var1, var6 - 4));

               while(true) {
                  if (var14 <= 0) {
                     continue label232;
                  }

                  var9 = var13 + a(var1, var6);
                  var6 += 4;
                  var10 = a(var2, var3, var13, var9);
                  var16.putInt(var10);
                  --var14;
               }
            case 15:
               var13 = var6;
               var6 = var6 + 4 - (var6 & 3);
               var16.putByte(171);
               var16.putByteArray((byte[])null, 0, (4 - var16.b % 4) % 4);
               var9 = var13 + a(var1, var6);
               var6 += 4;
               var10 = a(var2, var3, var13, var9);
               var16.putInt(var10);
               var14 = a(var1, var6);
               var6 += 4;
               var16.putInt(var14);

               while(true) {
                  if (var14 <= 0) {
                     continue label232;
                  }

                  var16.putInt(a(var1, var6));
                  var6 += 4;
                  var9 = var13 + a(var1, var6);
                  var6 += 4;
                  var10 = a(var2, var3, var13, var9);
                  var16.putInt(var10);
                  --var14;
               }
            case 16:
            default:
               var16.putByteArray(var1, var6, 4);
               var6 += 4;
               break;
            case 17:
               var8 = var1[var6 + 1] & 255;
               if (var8 == 132) {
                  var16.putByteArray(var1, var6, 6);
                  var6 += 6;
               } else {
                  var16.putByteArray(var1, var6, 4);
                  var6 += 4;
               }
            }
         }

         int var15;
         if (this.M == 0) {
            for(Label var20 = this.N; var20 != null; var20 = var20.i) {
               var6 = var20.c - 3;
               if (var6 >= 0 && var4[var6]) {
                  var20.a |= 16;
               }

               a(var2, var3, var20);
            }

            if (this.b.H != null) {
               for(var15 = 0; var15 < this.b.H.length; ++var15) {
                  Item var17 = this.b.H[var15];
                  if (var17 != null && var17.b == 31) {
                     var17.c = a(var2, var3, 0, var17.c);
                  }
               }
            }
         } else if (this.u > 0) {
            this.b.L = true;
         }

         for(Handler var22 = this.B; var22 != null; var22 = var22.f) {
            a(var2, var3, var22.a);
            a(var2, var3, var22.b);
            a(var2, var3, var22.c);
         }

         for(var15 = 0; var15 < 2; ++var15) {
            ByteVector var18 = var15 == 0 ? this.E : this.G;
            if (var18 != null) {
               var1 = var18.a;

               for(var6 = 0; var6 < var18.b; var6 += 10) {
                  var9 = c(var1, var6);
                  var10 = a(var2, var3, 0, var9);
                  a(var1, var6, var10);
                  var9 += c(var1, var6 + 2);
                  var10 = a(var2, var3, 0, var9) - var10;
                  a(var1, var6 + 2, var10);
               }
            }
         }

         if (this.I != null) {
            var1 = this.I.a;

            for(var6 = 0; var6 < this.I.b; var6 += 4) {
               a(var1, var6, a(var2, var3, 0, c(var1, var6)));
            }
         }

         for(Attribute var19 = this.J; var19 != null; var19 = var19.a) {
            Label[] var21 = var19.getLabels();
            if (var21 != null) {
               for(var15 = var21.length - 1; var15 >= 0; --var15) {
                  a(var2, var3, var21[var15]);
               }
            }
         }

         this.r = var16;
         return;
      }
   }

   static int c(byte[] var0, int var1) {
      return (var0[var1] & 255) << 8 | var0[var1 + 1] & 255;
   }

   static short b(byte[] var0, int var1) {
      return (short)((var0[var1] & 255) << 8 | var0[var1 + 1] & 255);
   }

   static int a(byte[] var0, int var1) {
      return (var0[var1] & 255) << 24 | (var0[var1 + 1] & 255) << 16 | (var0[var1 + 2] & 255) << 8 | var0[var1 + 3] & 255;
   }

   static void a(byte[] var0, int var1, int var2) {
      var0[var1] = (byte)(var2 >>> 8);
      var0[var1 + 1] = (byte)var2;
   }

   static int a(int[] var0, int[] var1, int var2, int var3) {
      int var4 = var3 - var2;

      for(int var5 = 0; var5 < var0.length; ++var5) {
         if (var2 < var0[var5] && var0[var5] <= var3) {
            var4 += var1[var5];
         } else if (var3 < var0[var5] && var0[var5] <= var2) {
            var4 -= var1[var5];
         }
      }

      return var4;
   }

   static void a(int[] var0, int[] var1, Label var2) {
      if ((var2.a & 4) == 0) {
         var2.c = a(var0, var1, 0, var2.c);
         var2.a |= 4;
      }

   }
}
