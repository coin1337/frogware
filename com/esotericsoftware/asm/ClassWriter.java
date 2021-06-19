package com.esotericsoftware.asm;

public class ClassWriter extends ClassVisitor {
   public static final int COMPUTE_MAXS = 1;
   public static final int COMPUTE_FRAMES = 2;
   static final byte[] a;
   ClassReader M;
   int b;
   int c;
   final ByteVector d;
   Item[] e;
   int f;
   final Item g;
   final Item h;
   final Item i;
   final Item j;
   Item[] H;
   private short G;
   private int k;
   private int l;
   String I;
   private int m;
   private int n;
   private int o;
   private int[] p;
   private int q;
   private ByteVector r;
   private int s;
   private int t;
   private AnnotationWriter u;
   private AnnotationWriter v;
   private AnnotationWriter N;
   private AnnotationWriter O;
   private Attribute w;
   private int x;
   private ByteVector y;
   int z;
   ByteVector A;
   FieldWriter B;
   FieldWriter C;
   MethodWriter D;
   MethodWriter E;
   private boolean K;
   private boolean J;
   boolean L;

   public ClassWriter(int var1) {
      super(327680);
      this.c = 1;
      this.d = new ByteVector();
      this.e = new Item[256];
      this.f = (int)(0.75D * (double)this.e.length);
      this.g = new Item();
      this.h = new Item();
      this.i = new Item();
      this.j = new Item();
      this.K = (var1 & 1) != 0;
      this.J = (var1 & 2) != 0;
   }

   public ClassWriter(ClassReader var1, int var2) {
      this(var2);
      var1.a(this);
      this.M = var1;
   }

   public final void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      this.b = var1;
      this.k = var2;
      this.l = this.newClass(var3);
      this.I = var3;
      if (var4 != null) {
         this.m = this.newUTF8(var4);
      }

      this.n = var5 == null ? 0 : this.newClass(var5);
      if (var6 != null && var6.length > 0) {
         this.o = var6.length;
         this.p = new int[this.o];

         for(int var7 = 0; var7 < this.o; ++var7) {
            this.p[var7] = this.newClass(var6[var7]);
         }
      }

   }

   public final void visitSource(String var1, String var2) {
      if (var1 != null) {
         this.q = this.newUTF8(var1);
      }

      if (var2 != null) {
         this.r = (new ByteVector()).c(var2, 0, Integer.MAX_VALUE);
      }

   }

   public final void visitOuterClass(String var1, String var2, String var3) {
      this.s = this.newClass(var1);
      if (var2 != null && var3 != null) {
         this.t = this.newNameType(var2, var3);
      }

   }

   public final AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      ByteVector var3 = new ByteVector();
      var3.putShort(this.newUTF8(var1)).putShort(0);
      AnnotationWriter var4 = new AnnotationWriter(this, true, var3, var3, 2);
      if (var2) {
         var4.g = this.u;
         this.u = var4;
      } else {
         var4.g = this.v;
         this.v = var4;
      }

      return var4;
   }

   public final AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.a(var1, var2, var5);
      var5.putShort(this.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this, true, var5, var5, var5.b - 2);
      if (var4) {
         var6.g = this.N;
         this.N = var6;
      } else {
         var6.g = this.O;
         this.O = var6;
      }

      return var6;
   }

   public final void visitAttribute(Attribute var1) {
      var1.a = this.w;
      this.w = var1;
   }

   public final void visitInnerClass(String var1, String var2, String var3, int var4) {
      if (this.y == null) {
         this.y = new ByteVector();
      }

      Item var5 = this.a(var1);
      if (var5.c == 0) {
         ++this.x;
         this.y.putShort(var5.a);
         this.y.putShort(var2 == null ? 0 : this.newClass(var2));
         this.y.putShort(var3 == null ? 0 : this.newUTF8(var3));
         this.y.putShort(var4);
         var5.c = this.x;
      }

   }

   public final FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      return new FieldWriter(this, var1, var2, var3, var4, var5);
   }

   public final MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      return new MethodWriter(this, var1, var2, var3, var4, var5, this.K, this.J);
   }

   public final void visitEnd() {
   }

   public byte[] toByteArray() {
      if (this.c > 65535) {
         throw new RuntimeException("Class file too large!");
      } else {
         int var1 = 24 + 2 * this.o;
         int var2 = 0;

         FieldWriter var3;
         for(var3 = this.B; var3 != null; var3 = (FieldWriter)var3.fv) {
            ++var2;
            var1 += var3.a();
         }

         int var4 = 0;

         MethodWriter var5;
         for(var5 = this.D; var5 != null; var5 = (MethodWriter)var5.mv) {
            ++var4;
            var1 += var5.a();
         }

         int var6 = 0;
         if (this.A != null) {
            ++var6;
            var1 += 8 + this.A.b;
            this.newUTF8("BootstrapMethods");
         }

         if (this.m != 0) {
            ++var6;
            var1 += 8;
            this.newUTF8("Signature");
         }

         if (this.q != 0) {
            ++var6;
            var1 += 8;
            this.newUTF8("SourceFile");
         }

         if (this.r != null) {
            ++var6;
            var1 += this.r.b + 6;
            this.newUTF8("SourceDebugExtension");
         }

         if (this.s != 0) {
            ++var6;
            var1 += 10;
            this.newUTF8("EnclosingMethod");
         }

         if ((this.k & 131072) != 0) {
            ++var6;
            var1 += 6;
            this.newUTF8("Deprecated");
         }

         if ((this.k & 4096) != 0 && ((this.b & '\uffff') < 49 || (this.k & 262144) != 0)) {
            ++var6;
            var1 += 6;
            this.newUTF8("Synthetic");
         }

         if (this.y != null) {
            ++var6;
            var1 += 8 + this.y.b;
            this.newUTF8("InnerClasses");
         }

         if (this.u != null) {
            ++var6;
            var1 += 8 + this.u.a();
            this.newUTF8("RuntimeVisibleAnnotations");
         }

         if (this.v != null) {
            ++var6;
            var1 += 8 + this.v.a();
            this.newUTF8("RuntimeInvisibleAnnotations");
         }

         if (this.N != null) {
            ++var6;
            var1 += 8 + this.N.a();
            this.newUTF8("RuntimeVisibleTypeAnnotations");
         }

         if (this.O != null) {
            ++var6;
            var1 += 8 + this.O.a();
            this.newUTF8("RuntimeInvisibleTypeAnnotations");
         }

         if (this.w != null) {
            var6 += this.w.a();
            var1 += this.w.a(this, (byte[])null, 0, -1, -1);
         }

         var1 += this.d.b;
         ByteVector var7 = new ByteVector(var1);
         var7.putInt(-889275714).putInt(this.b);
         var7.putShort(this.c).putByteArray(this.d.a, 0, this.d.b);
         int var8 = 393216 | (this.k & 262144) / 64;
         var7.putShort(this.k & ~var8).putShort(this.l).putShort(this.n);
         var7.putShort(this.o);

         int var9;
         for(var9 = 0; var9 < this.o; ++var9) {
            var7.putShort(this.p[var9]);
         }

         var7.putShort(var2);

         for(var3 = this.B; var3 != null; var3 = (FieldWriter)var3.fv) {
            var3.a(var7);
         }

         var7.putShort(var4);

         for(var5 = this.D; var5 != null; var5 = (MethodWriter)var5.mv) {
            var5.a(var7);
         }

         var7.putShort(var6);
         if (this.A != null) {
            var7.putShort(this.newUTF8("BootstrapMethods"));
            var7.putInt(this.A.b + 2).putShort(this.z);
            var7.putByteArray(this.A.a, 0, this.A.b);
         }

         if (this.m != 0) {
            var7.putShort(this.newUTF8("Signature")).putInt(2).putShort(this.m);
         }

         if (this.q != 0) {
            var7.putShort(this.newUTF8("SourceFile")).putInt(2).putShort(this.q);
         }

         if (this.r != null) {
            var9 = this.r.b;
            var7.putShort(this.newUTF8("SourceDebugExtension")).putInt(var9);
            var7.putByteArray(this.r.a, 0, var9);
         }

         if (this.s != 0) {
            var7.putShort(this.newUTF8("EnclosingMethod")).putInt(4);
            var7.putShort(this.s).putShort(this.t);
         }

         if ((this.k & 131072) != 0) {
            var7.putShort(this.newUTF8("Deprecated")).putInt(0);
         }

         if ((this.k & 4096) != 0 && ((this.b & '\uffff') < 49 || (this.k & 262144) != 0)) {
            var7.putShort(this.newUTF8("Synthetic")).putInt(0);
         }

         if (this.y != null) {
            var7.putShort(this.newUTF8("InnerClasses"));
            var7.putInt(this.y.b + 2).putShort(this.x);
            var7.putByteArray(this.y.a, 0, this.y.b);
         }

         if (this.u != null) {
            var7.putShort(this.newUTF8("RuntimeVisibleAnnotations"));
            this.u.a(var7);
         }

         if (this.v != null) {
            var7.putShort(this.newUTF8("RuntimeInvisibleAnnotations"));
            this.v.a(var7);
         }

         if (this.N != null) {
            var7.putShort(this.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.N.a(var7);
         }

         if (this.O != null) {
            var7.putShort(this.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.O.a(var7);
         }

         if (this.w != null) {
            this.w.a(this, (byte[])null, 0, -1, -1, var7);
         }

         if (this.L) {
            this.u = null;
            this.v = null;
            this.w = null;
            this.x = 0;
            this.y = null;
            this.z = 0;
            this.A = null;
            this.B = null;
            this.C = null;
            this.D = null;
            this.E = null;
            this.K = false;
            this.J = true;
            this.L = false;
            (new ClassReader(var7.a)).accept(this, 4);
            return this.toByteArray();
         } else {
            return var7.a;
         }
      }
   }

   Item a(Object var1) {
      int var8;
      if (var1 instanceof Integer) {
         var8 = (Integer)var1;
         return this.a(var8);
      } else if (var1 instanceof Byte) {
         var8 = ((Byte)var1).intValue();
         return this.a(var8);
      } else if (var1 instanceof Character) {
         char var10 = (Character)var1;
         return this.a(var10);
      } else if (var1 instanceof Short) {
         var8 = ((Short)var1).intValue();
         return this.a(var8);
      } else if (var1 instanceof Boolean) {
         var8 = (Boolean)var1 ? 1 : 0;
         return this.a(var8);
      } else if (var1 instanceof Float) {
         float var7 = (Float)var1;
         return this.a(var7);
      } else if (var1 instanceof Long) {
         long var9 = (Long)var1;
         return this.a(var9);
      } else if (var1 instanceof Double) {
         double var3 = (Double)var1;
         return this.a(var3);
      } else if (var1 instanceof String) {
         return this.b((String)var1);
      } else if (var1 instanceof Type) {
         Type var6 = (Type)var1;
         int var5 = var6.getSort();
         if (var5 == 10) {
            return this.a(var6.getInternalName());
         } else {
            return var5 == 11 ? this.c(var6.getDescriptor()) : this.a(var6.getDescriptor());
         }
      } else if (var1 instanceof Handle) {
         Handle var2 = (Handle)var1;
         return this.a(var2.a, var2.b, var2.c, var2.d, var2.e);
      } else {
         throw new IllegalArgumentException("value " + var1);
      }
   }

   public int newConst(Object var1) {
      return this.a(var1).a;
   }

   public int newUTF8(String var1) {
      this.g.a(1, var1, (String)null, (String)null);
      Item var2 = this.a(this.g);
      if (var2 == null) {
         this.d.putByte(1).putUTF8(var1);
         var2 = new Item(this.c++, this.g);
         this.b(var2);
      }

      return var2.a;
   }

   Item a(String var1) {
      this.h.a(7, var1, (String)null, (String)null);
      Item var2 = this.a(this.h);
      if (var2 == null) {
         this.d.b(7, this.newUTF8(var1));
         var2 = new Item(this.c++, this.h);
         this.b(var2);
      }

      return var2;
   }

   public int newClass(String var1) {
      return this.a(var1).a;
   }

   Item c(String var1) {
      this.h.a(16, var1, (String)null, (String)null);
      Item var2 = this.a(this.h);
      if (var2 == null) {
         this.d.b(16, this.newUTF8(var1));
         var2 = new Item(this.c++, this.h);
         this.b(var2);
      }

      return var2;
   }

   public int newMethodType(String var1) {
      return this.c(var1).a;
   }

   Item a(int var1, String var2, String var3, String var4, boolean var5) {
      this.j.a(20 + var1, var2, var3, var4);
      Item var6 = this.a(this.j);
      if (var6 == null) {
         if (var1 <= 4) {
            this.b(15, var1, this.newField(var2, var3, var4));
         } else {
            this.b(15, var1, this.newMethod(var2, var3, var4, var5));
         }

         var6 = new Item(this.c++, this.j);
         this.b(var6);
      }

      return var6;
   }

   /** @deprecated */
   public int newHandle(int var1, String var2, String var3, String var4) {
      return this.newHandle(var1, var2, var3, var4, var1 == 9);
   }

   public int newHandle(int var1, String var2, String var3, String var4, boolean var5) {
      return this.a(var1, var2, var3, var4, var5).a;
   }

   Item a(String var1, String var2, Handle var3, Object... var4) {
      ByteVector var5 = this.A;
      if (var5 == null) {
         var5 = this.A = new ByteVector();
      }

      int var6 = var5.b;
      int var7 = var3.hashCode();
      var5.putShort(this.newHandle(var3.a, var3.b, var3.c, var3.d, var3.isInterface()));
      int var8 = var4.length;
      var5.putShort(var8);

      for(int var9 = 0; var9 < var8; ++var9) {
         Object var10 = var4[var9];
         var7 ^= var10.hashCode();
         var5.putShort(this.newConst(var10));
      }

      byte[] var14 = var5.a;
      int var15 = 2 + var8 << 1;
      var7 &= Integer.MAX_VALUE;
      Item var11 = this.e[var7 % this.e.length];

      int var12;
      label44:
      while(var11 != null) {
         if (var11.b == 33 && var11.j == var7) {
            var12 = var11.c;
            int var13 = 0;

            while(true) {
               if (var13 >= var15) {
                  break label44;
               }

               if (var14[var6 + var13] != var14[var12 + var13]) {
                  var11 = var11.k;
                  break;
               }

               ++var13;
            }
         } else {
            var11 = var11.k;
         }
      }

      if (var11 != null) {
         var12 = var11.a;
         var5.b = var6;
      } else {
         var12 = this.z++;
         var11 = new Item(var12);
         var11.a(var6, var7);
         this.b(var11);
      }

      this.i.a(var1, var2, var12);
      var11 = this.a(this.i);
      if (var11 == null) {
         this.a(18, var12, this.newNameType(var1, var2));
         var11 = new Item(this.c++, this.i);
         this.b(var11);
      }

      return var11;
   }

   public int newInvokeDynamic(String var1, String var2, Handle var3, Object... var4) {
      return this.a(var1, var2, var3, var4).a;
   }

   Item a(String var1, String var2, String var3) {
      this.i.a(9, var1, var2, var3);
      Item var4 = this.a(this.i);
      if (var4 == null) {
         this.a(9, this.newClass(var1), this.newNameType(var2, var3));
         var4 = new Item(this.c++, this.i);
         this.b(var4);
      }

      return var4;
   }

   public int newField(String var1, String var2, String var3) {
      return this.a(var1, var2, var3).a;
   }

   Item a(String var1, String var2, String var3, boolean var4) {
      int var5 = var4 ? 11 : 10;
      this.i.a(var5, var1, var2, var3);
      Item var6 = this.a(this.i);
      if (var6 == null) {
         this.a(var5, this.newClass(var1), this.newNameType(var2, var3));
         var6 = new Item(this.c++, this.i);
         this.b(var6);
      }

      return var6;
   }

   public int newMethod(String var1, String var2, String var3, boolean var4) {
      return this.a(var1, var2, var3, var4).a;
   }

   Item a(int var1) {
      this.g.a(var1);
      Item var2 = this.a(this.g);
      if (var2 == null) {
         this.d.putByte(3).putInt(var1);
         var2 = new Item(this.c++, this.g);
         this.b(var2);
      }

      return var2;
   }

   Item a(float var1) {
      this.g.a(var1);
      Item var2 = this.a(this.g);
      if (var2 == null) {
         this.d.putByte(4).putInt(this.g.c);
         var2 = new Item(this.c++, this.g);
         this.b(var2);
      }

      return var2;
   }

   Item a(long var1) {
      this.g.a(var1);
      Item var3 = this.a(this.g);
      if (var3 == null) {
         this.d.putByte(5).putLong(var1);
         var3 = new Item(this.c, this.g);
         this.c += 2;
         this.b(var3);
      }

      return var3;
   }

   Item a(double var1) {
      this.g.a(var1);
      Item var3 = this.a(this.g);
      if (var3 == null) {
         this.d.putByte(6).putLong(this.g.d);
         var3 = new Item(this.c, this.g);
         this.c += 2;
         this.b(var3);
      }

      return var3;
   }

   private Item b(String var1) {
      this.h.a(8, var1, (String)null, (String)null);
      Item var2 = this.a(this.h);
      if (var2 == null) {
         this.d.b(8, this.newUTF8(var1));
         var2 = new Item(this.c++, this.h);
         this.b(var2);
      }

      return var2;
   }

   public int newNameType(String var1, String var2) {
      return this.a(var1, var2).a;
   }

   Item a(String var1, String var2) {
      this.h.a(12, var1, var2, (String)null);
      Item var3 = this.a(this.h);
      if (var3 == null) {
         this.a(12, this.newUTF8(var1), this.newUTF8(var2));
         var3 = new Item(this.c++, this.h);
         this.b(var3);
      }

      return var3;
   }

   int c(String var1) {
      this.g.a(30, var1, (String)null, (String)null);
      Item var2 = this.a(this.g);
      if (var2 == null) {
         var2 = this.c(this.g);
      }

      return var2.a;
   }

   int a(String var1, int var2) {
      this.g.b = 31;
      this.g.c = var2;
      this.g.g = var1;
      this.g.j = Integer.MAX_VALUE & 31 + var1.hashCode() + var2;
      Item var3 = this.a(this.g);
      if (var3 == null) {
         var3 = this.c(this.g);
      }

      return var3.a;
   }

   private Item c(Item var1) {
      ++this.G;
      Item var2 = new Item(this.G, this.g);
      this.b(var2);
      if (this.H == null) {
         this.H = new Item[16];
      }

      if (this.G == this.H.length) {
         Item[] var3 = new Item[2 * this.H.length];
         System.arraycopy(this.H, 0, var3, 0, this.H.length);
         this.H = var3;
      }

      this.H[this.G] = var2;
      return var2;
   }

   int a(int var1, int var2) {
      this.h.b = 32;
      this.h.d = (long)var1 | (long)var2 << 32;
      this.h.j = Integer.MAX_VALUE & 32 + var1 + var2;
      Item var3 = this.a(this.h);
      if (var3 == null) {
         String var4 = this.H[var1].g;
         String var5 = this.H[var2].g;
         this.h.c = this.c(this.getCommonSuperClass(var4, var5));
         var3 = new Item(0, this.h);
         this.b(var3);
      }

      return var3.c;
   }

   protected String getCommonSuperClass(String var1, String var2) {
      ClassLoader var3 = this.getClass().getClassLoader();

      Class var4;
      Class var5;
      try {
         var4 = Class.forName(var1.replace('/', '.'), false, var3);
         var5 = Class.forName(var2.replace('/', '.'), false, var3);
      } catch (Exception var7) {
         throw new RuntimeException(var7.toString());
      }

      if (var4.isAssignableFrom(var5)) {
         return var1;
      } else if (var5.isAssignableFrom(var4)) {
         return var2;
      } else if (!var4.isInterface() && !var5.isInterface()) {
         do {
            var4 = var4.getSuperclass();
         } while(!var4.isAssignableFrom(var5));

         return var4.getName().replace('.', '/');
      } else {
         return "java/lang/Object";
      }
   }

   private Item a(Item var1) {
      Item var2;
      for(var2 = this.e[var1.j % this.e.length]; var2 != null && (var2.b != var1.b || !var1.a(var2)); var2 = var2.k) {
      }

      return var2;
   }

   private void b(Item var1) {
      int var2;
      if (this.c + this.G > this.f) {
         var2 = this.e.length;
         int var3 = var2 * 2 + 1;
         Item[] var4 = new Item[var3];

         Item var8;
         for(int var5 = var2 - 1; var5 >= 0; --var5) {
            for(Item var6 = this.e[var5]; var6 != null; var6 = var8) {
               int var7 = var6.j % var4.length;
               var8 = var6.k;
               var6.k = var4[var7];
               var4[var7] = var6;
            }
         }

         this.e = var4;
         this.f = (int)((double)var3 * 0.75D);
      }

      var2 = var1.j % this.e.length;
      var1.k = this.e[var2];
      this.e[var2] = var1;
   }

   private void a(int var1, int var2, int var3) {
      this.d.b(var1, var2).putShort(var3);
   }

   private void b(int var1, int var2, int var3) {
      this.d.a(var1, var2).putShort(var3);
   }

   static {
      _clinit_();
      byte[] var0 = new byte[220];
      String var1 = "AAAAAAAAAAAAAAAABCLMMDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANAAAAAAAAAAAAAAAAAAAAJJJJJJJJJJJJJJJJDOPAAAAAAGGGGGGGHIFBFAAFFAARQJJKKJJJJJJJJJJJJJJJJJJ";

      for(int var2 = 0; var2 < var0.length; ++var2) {
         var0[var2] = (byte)(var1.charAt(var2) - 65);
      }

      a = var0;
   }

   // $FF: synthetic method
   static void _clinit_() {
   }
}
