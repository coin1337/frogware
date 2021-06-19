package com.esotericsoftware.asm;

public class Attribute {
   public final String type;
   byte[] b;
   Attribute a;

   protected Attribute(String var1) {
      this.type = var1;
   }

   public boolean isUnknown() {
      return true;
   }

   public boolean isCodeAttribute() {
      return false;
   }

   protected Label[] getLabels() {
      return null;
   }

   protected Attribute read(ClassReader var1, int var2, int var3, char[] var4, int var5, Label[] var6) {
      Attribute var7 = new Attribute(this.type);
      var7.b = new byte[var3];
      System.arraycopy(var1.b, var2, var7.b, 0, var3);
      return var7;
   }

   protected ByteVector write(ClassWriter var1, byte[] var2, int var3, int var4, int var5) {
      ByteVector var6 = new ByteVector();
      var6.a = this.b;
      var6.b = this.b.length;
      return var6;
   }

   final int a() {
      int var1 = 0;

      for(Attribute var2 = this; var2 != null; var2 = var2.a) {
         ++var1;
      }

      return var1;
   }

   final int a(ClassWriter var1, byte[] var2, int var3, int var4, int var5) {
      Attribute var6 = this;

      int var7;
      for(var7 = 0; var6 != null; var6 = var6.a) {
         var1.newUTF8(var6.type);
         var7 += var6.write(var1, var2, var3, var4, var5).b + 6;
      }

      return var7;
   }

   final void a(ClassWriter var1, byte[] var2, int var3, int var4, int var5, ByteVector var6) {
      for(Attribute var7 = this; var7 != null; var7 = var7.a) {
         ByteVector var8 = var7.write(var1, var2, var3, var4, var5);
         var6.putShort(var1.newUTF8(var7.type)).putInt(var8.b);
         var6.putByteArray(var8.a, 0, var8.b);
      }

   }
}
