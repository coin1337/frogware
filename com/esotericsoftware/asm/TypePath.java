package com.esotericsoftware.asm;

public class TypePath {
   public static final int ARRAY_ELEMENT = 0;
   public static final int INNER_TYPE = 1;
   public static final int WILDCARD_BOUND = 2;
   public static final int TYPE_ARGUMENT = 3;
   byte[] a;
   int b;

   TypePath(byte[] var1, int var2) {
      this.a = var1;
      this.b = var2;
   }

   public int getLength() {
      return this.a[this.b];
   }

   public int getStep(int var1) {
      return this.a[this.b + 2 * var1 + 1];
   }

   public int getStepArgument(int var1) {
      return this.a[this.b + 2 * var1 + 2];
   }

   public static TypePath fromString(String var0) {
      if (var0 != null && var0.length() != 0) {
         int var1 = var0.length();
         ByteVector var2 = new ByteVector(var1);
         var2.putByte(0);
         int var3 = 0;

         while(true) {
            while(var3 < var1) {
               char var4 = var0.charAt(var3++);
               if (var4 == '[') {
                  var2.a(0, 0);
               } else if (var4 == '.') {
                  var2.a(1, 0);
               } else if (var4 == '*') {
                  var2.a(2, 0);
               } else if (var4 >= '0' && var4 <= '9') {
                  int var5;
                  for(var5 = var4 - 48; var3 < var1 && (var4 = var0.charAt(var3)) >= '0' && var4 <= '9'; ++var3) {
                     var5 = var5 * 10 + var4 - 48;
                  }

                  if (var3 < var1 && var0.charAt(var3) == ';') {
                     ++var3;
                  }

                  var2.a(3, var5);
               }
            }

            var2.a[0] = (byte)(var2.b / 2);
            return new TypePath(var2.a, 0);
         }
      } else {
         return null;
      }
   }

   public String toString() {
      int var1 = this.getLength();
      StringBuffer var2 = new StringBuffer(var1 * 2);

      for(int var3 = 0; var3 < var1; ++var3) {
         switch(this.getStep(var3)) {
         case 0:
            var2.append('[');
            break;
         case 1:
            var2.append('.');
            break;
         case 2:
            var2.append('*');
            break;
         case 3:
            var2.append(this.getStepArgument(var3)).append(';');
            break;
         default:
            var2.append('_');
         }
      }

      return var2.toString();
   }
}
