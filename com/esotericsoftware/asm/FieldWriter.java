package com.esotericsoftware.asm;

final class FieldWriter extends FieldVisitor {
   private final ClassWriter b;
   private final int c;
   private final int d;
   private final int e;
   private int f;
   private int g;
   private AnnotationWriter h;
   private AnnotationWriter i;
   private AnnotationWriter k;
   private AnnotationWriter l;
   private Attribute j;

   FieldWriter(ClassWriter var1, int var2, String var3, String var4, String var5, Object var6) {
      super(327680);
      if (var1.B == null) {
         var1.B = this;
      } else {
         var1.C.fv = this;
      }

      var1.C = this;
      this.b = var1;
      this.c = var2;
      this.d = var1.newUTF8(var3);
      this.e = var1.newUTF8(var4);
      if (var5 != null) {
         this.f = var1.newUTF8(var5);
      }

      if (var6 != null) {
         this.g = var1.a(var6).a;
      }

   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      ByteVector var3 = new ByteVector();
      var3.putShort(this.b.newUTF8(var1)).putShort(0);
      AnnotationWriter var4 = new AnnotationWriter(this.b, true, var3, var3, 2);
      if (var2) {
         var4.g = this.h;
         this.h = var4;
      } else {
         var4.g = this.i;
         this.i = var4;
      }

      return var4;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.a(var1, var2, var5);
      var5.putShort(this.b.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.b, true, var5, var5, var5.b - 2);
      if (var4) {
         var6.g = this.k;
         this.k = var6;
      } else {
         var6.g = this.l;
         this.l = var6;
      }

      return var6;
   }

   public void visitAttribute(Attribute var1) {
      var1.a = this.j;
      this.j = var1;
   }

   public void visitEnd() {
   }

   int a() {
      int var1 = 8;
      if (this.g != 0) {
         this.b.newUTF8("ConstantValue");
         var1 += 8;
      }

      if ((this.c & 4096) != 0 && ((this.b.b & '\uffff') < 49 || (this.c & 262144) != 0)) {
         this.b.newUTF8("Synthetic");
         var1 += 6;
      }

      if ((this.c & 131072) != 0) {
         this.b.newUTF8("Deprecated");
         var1 += 6;
      }

      if (this.f != 0) {
         this.b.newUTF8("Signature");
         var1 += 8;
      }

      if (this.h != null) {
         this.b.newUTF8("RuntimeVisibleAnnotations");
         var1 += 8 + this.h.a();
      }

      if (this.i != null) {
         this.b.newUTF8("RuntimeInvisibleAnnotations");
         var1 += 8 + this.i.a();
      }

      if (this.k != null) {
         this.b.newUTF8("RuntimeVisibleTypeAnnotations");
         var1 += 8 + this.k.a();
      }

      if (this.l != null) {
         this.b.newUTF8("RuntimeInvisibleTypeAnnotations");
         var1 += 8 + this.l.a();
      }

      if (this.j != null) {
         var1 += this.j.a(this.b, (byte[])null, 0, -1, -1);
      }

      return var1;
   }

   void a(ByteVector var1) {
      boolean var2 = true;
      int var3 = 393216 | (this.c & 262144) / 64;
      var1.putShort(this.c & ~var3).putShort(this.d).putShort(this.e);
      int var4 = 0;
      if (this.g != 0) {
         ++var4;
      }

      if ((this.c & 4096) != 0 && ((this.b.b & '\uffff') < 49 || (this.c & 262144) != 0)) {
         ++var4;
      }

      if ((this.c & 131072) != 0) {
         ++var4;
      }

      if (this.f != 0) {
         ++var4;
      }

      if (this.h != null) {
         ++var4;
      }

      if (this.i != null) {
         ++var4;
      }

      if (this.k != null) {
         ++var4;
      }

      if (this.l != null) {
         ++var4;
      }

      if (this.j != null) {
         var4 += this.j.a();
      }

      var1.putShort(var4);
      if (this.g != 0) {
         var1.putShort(this.b.newUTF8("ConstantValue"));
         var1.putInt(2).putShort(this.g);
      }

      if ((this.c & 4096) != 0 && ((this.b.b & '\uffff') < 49 || (this.c & 262144) != 0)) {
         var1.putShort(this.b.newUTF8("Synthetic")).putInt(0);
      }

      if ((this.c & 131072) != 0) {
         var1.putShort(this.b.newUTF8("Deprecated")).putInt(0);
      }

      if (this.f != 0) {
         var1.putShort(this.b.newUTF8("Signature"));
         var1.putInt(2).putShort(this.f);
      }

      if (this.h != null) {
         var1.putShort(this.b.newUTF8("RuntimeVisibleAnnotations"));
         this.h.a(var1);
      }

      if (this.i != null) {
         var1.putShort(this.b.newUTF8("RuntimeInvisibleAnnotations"));
         this.i.a(var1);
      }

      if (this.k != null) {
         var1.putShort(this.b.newUTF8("RuntimeVisibleTypeAnnotations"));
         this.k.a(var1);
      }

      if (this.l != null) {
         var1.putShort(this.b.newUTF8("RuntimeInvisibleTypeAnnotations"));
         this.l.a(var1);
      }

      if (this.j != null) {
         this.j.a(this.b, (byte[])null, 0, -1, -1, var1);
      }

   }
}
