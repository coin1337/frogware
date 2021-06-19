package com.esotericsoftware.asm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Type {
   public static final int VOID = 0;
   public static final int BOOLEAN = 1;
   public static final int CHAR = 2;
   public static final int BYTE = 3;
   public static final int SHORT = 4;
   public static final int INT = 5;
   public static final int FLOAT = 6;
   public static final int LONG = 7;
   public static final int DOUBLE = 8;
   public static final int ARRAY = 9;
   public static final int OBJECT = 10;
   public static final int METHOD = 11;
   public static final Type VOID_TYPE;
   public static final Type BOOLEAN_TYPE;
   public static final Type CHAR_TYPE;
   public static final Type BYTE_TYPE;
   public static final Type SHORT_TYPE;
   public static final Type INT_TYPE;
   public static final Type FLOAT_TYPE;
   public static final Type LONG_TYPE;
   public static final Type DOUBLE_TYPE;
   private final int a;
   private final char[] b;
   private final int c;
   private final int d;

   private Type(int var1, char[] var2, int var3, int var4) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.d = var4;
   }

   public static Type getType(String var0) {
      return a(var0.toCharArray(), 0);
   }

   public static Type getObjectType(String var0) {
      char[] var1 = var0.toCharArray();
      return new Type(var1[0] == '[' ? 9 : 10, var1, 0, var1.length);
   }

   public static Type getMethodType(String var0) {
      return a(var0.toCharArray(), 0);
   }

   public static Type getMethodType(Type var0, Type... var1) {
      return getType(getMethodDescriptor(var0, var1));
   }

   public static Type getType(Class var0) {
      if (var0.isPrimitive()) {
         if (var0 == Integer.TYPE) {
            return INT_TYPE;
         } else if (var0 == Void.TYPE) {
            return VOID_TYPE;
         } else if (var0 == Boolean.TYPE) {
            return BOOLEAN_TYPE;
         } else if (var0 == Byte.TYPE) {
            return BYTE_TYPE;
         } else if (var0 == Character.TYPE) {
            return CHAR_TYPE;
         } else if (var0 == Short.TYPE) {
            return SHORT_TYPE;
         } else if (var0 == Double.TYPE) {
            return DOUBLE_TYPE;
         } else {
            return var0 == Float.TYPE ? FLOAT_TYPE : LONG_TYPE;
         }
      } else {
         return getType(getDescriptor(var0));
      }
   }

   public static Type getType(Constructor var0) {
      return getType(getConstructorDescriptor(var0));
   }

   public static Type getType(Method var0) {
      return getType(getMethodDescriptor(var0));
   }

   public static Type[] getArgumentTypes(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = 1;
      int var3 = 0;

      while(true) {
         while(true) {
            char var4 = var1[var2++];
            if (var4 == ')') {
               Type[] var5 = new Type[var3];
               var2 = 1;

               for(var3 = 0; var1[var2] != ')'; ++var3) {
                  var5[var3] = a(var1, var2);
                  var2 += var5[var3].d + (var5[var3].a == 10 ? 2 : 0);
               }

               return var5;
            }

            if (var4 == 'L') {
               while(var1[var2++] != ';') {
               }

               ++var3;
            } else if (var4 != '[') {
               ++var3;
            }
         }
      }
   }

   public static Type[] getArgumentTypes(Method var0) {
      Class[] var1 = var0.getParameterTypes();
      Type[] var2 = new Type[var1.length];

      for(int var3 = var1.length - 1; var3 >= 0; --var3) {
         var2[var3] = getType(var1[var3]);
      }

      return var2;
   }

   public static Type getReturnType(String var0) {
      char[] var1 = var0.toCharArray();
      return a(var1, var0.indexOf(41) + 1);
   }

   public static Type getReturnType(Method var0) {
      return getType(var0.getReturnType());
   }

   public static int getArgumentsAndReturnSizes(String var0) {
      int var1 = 1;
      int var2 = 1;

      while(true) {
         while(true) {
            char var3 = var0.charAt(var2++);
            if (var3 == ')') {
               var3 = var0.charAt(var2);
               return var1 << 2 | (var3 == 'V' ? 0 : (var3 != 'D' && var3 != 'J' ? 1 : 2));
            }

            if (var3 == 'L') {
               while(var0.charAt(var2++) != ';') {
               }

               ++var1;
            } else if (var3 != '[') {
               if (var3 != 'D' && var3 != 'J') {
                  ++var1;
               } else {
                  var1 += 2;
               }
            } else {
               while((var3 = var0.charAt(var2)) == '[') {
                  ++var2;
               }

               if (var3 == 'D' || var3 == 'J') {
                  --var1;
               }
            }
         }
      }
   }

   private static Type a(char[] var0, int var1) {
      int var2;
      switch(var0[var1]) {
      case 'B':
         return BYTE_TYPE;
      case 'C':
         return CHAR_TYPE;
      case 'D':
         return DOUBLE_TYPE;
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
         return new Type(11, var0, var1, var0.length - var1);
      case 'F':
         return FLOAT_TYPE;
      case 'I':
         return INT_TYPE;
      case 'J':
         return LONG_TYPE;
      case 'L':
         for(var2 = 1; var0[var1 + var2] != ';'; ++var2) {
         }

         return new Type(10, var0, var1 + 1, var2 - 1);
      case 'S':
         return SHORT_TYPE;
      case 'V':
         return VOID_TYPE;
      case 'Z':
         return BOOLEAN_TYPE;
      case '[':
         for(var2 = 1; var0[var1 + var2] == '['; ++var2) {
         }

         if (var0[var1 + var2] == 'L') {
            ++var2;

            while(var0[var1 + var2] != ';') {
               ++var2;
            }
         }

         return new Type(9, var0, var1, var2 + 1);
      }
   }

   public int getSort() {
      return this.a;
   }

   public int getDimensions() {
      int var1;
      for(var1 = 1; this.b[this.c + var1] == '['; ++var1) {
      }

      return var1;
   }

   public Type getElementType() {
      return a(this.b, this.c + this.getDimensions());
   }

   public String getClassName() {
      switch(this.a) {
      case 0:
         return "void";
      case 1:
         return "boolean";
      case 2:
         return "char";
      case 3:
         return "byte";
      case 4:
         return "short";
      case 5:
         return "int";
      case 6:
         return "float";
      case 7:
         return "long";
      case 8:
         return "double";
      case 9:
         StringBuffer var1 = new StringBuffer(this.getElementType().getClassName());

         for(int var2 = this.getDimensions(); var2 > 0; --var2) {
            var1.append("[]");
         }

         return var1.toString();
      case 10:
         return (new String(this.b, this.c, this.d)).replace('/', '.');
      default:
         return null;
      }
   }

   public String getInternalName() {
      return new String(this.b, this.c, this.d);
   }

   public Type[] getArgumentTypes() {
      return getArgumentTypes(this.getDescriptor());
   }

   public Type getReturnType() {
      return getReturnType(this.getDescriptor());
   }

   public int getArgumentsAndReturnSizes() {
      return getArgumentsAndReturnSizes(this.getDescriptor());
   }

   public String getDescriptor() {
      StringBuffer var1 = new StringBuffer();
      this.a(var1);
      return var1.toString();
   }

   public static String getMethodDescriptor(Type var0, Type... var1) {
      StringBuffer var2 = new StringBuffer();
      var2.append('(');

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var1[var3].a(var2);
      }

      var2.append(')');
      var0.a(var2);
      return var2.toString();
   }

   private void a(StringBuffer var1) {
      if (this.b == null) {
         var1.append((char)((this.c & -16777216) >>> 24));
      } else if (this.a == 10) {
         var1.append('L');
         var1.append(this.b, this.c, this.d);
         var1.append(';');
      } else {
         var1.append(this.b, this.c, this.d);
      }

   }

   public static String getInternalName(Class var0) {
      return var0.getName().replace('.', '/');
   }

   public static String getDescriptor(Class var0) {
      StringBuffer var1 = new StringBuffer();
      a(var1, var0);
      return var1.toString();
   }

   public static String getConstructorDescriptor(Constructor var0) {
      Class[] var1 = var0.getParameterTypes();
      StringBuffer var2 = new StringBuffer();
      var2.append('(');

      for(int var3 = 0; var3 < var1.length; ++var3) {
         a(var2, var1[var3]);
      }

      return var2.append(")V").toString();
   }

   public static String getMethodDescriptor(Method var0) {
      Class[] var1 = var0.getParameterTypes();
      StringBuffer var2 = new StringBuffer();
      var2.append('(');

      for(int var3 = 0; var3 < var1.length; ++var3) {
         a(var2, var1[var3]);
      }

      var2.append(')');
      a(var2, var0.getReturnType());
      return var2.toString();
   }

   private static void a(StringBuffer var0, Class var1) {
      Class var2;
      for(var2 = var1; !var2.isPrimitive(); var2 = var2.getComponentType()) {
         if (!var2.isArray()) {
            var0.append('L');
            String var3 = var2.getName();
            int var4 = var3.length();

            for(int var5 = 0; var5 < var4; ++var5) {
               char var6 = var3.charAt(var5);
               var0.append(var6 == '.' ? '/' : var6);
            }

            var0.append(';');
            return;
         }

         var0.append('[');
      }

      char var7;
      if (var2 == Integer.TYPE) {
         var7 = 'I';
      } else if (var2 == Void.TYPE) {
         var7 = 'V';
      } else if (var2 == Boolean.TYPE) {
         var7 = 'Z';
      } else if (var2 == Byte.TYPE) {
         var7 = 'B';
      } else if (var2 == Character.TYPE) {
         var7 = 'C';
      } else if (var2 == Short.TYPE) {
         var7 = 'S';
      } else if (var2 == Double.TYPE) {
         var7 = 'D';
      } else if (var2 == Float.TYPE) {
         var7 = 'F';
      } else {
         var7 = 'J';
      }

      var0.append(var7);
   }

   public int getSize() {
      return this.b == null ? this.c & 255 : 1;
   }

   public int getOpcode(int var1) {
      return var1 != 46 && var1 != 79 ? var1 + (this.b == null ? (this.c & 16711680) >> 16 : 4) : var1 + (this.b == null ? (this.c & '\uff00') >> 8 : 4);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Type)) {
         return false;
      } else {
         Type var2 = (Type)var1;
         if (this.a != var2.a) {
            return false;
         } else {
            if (this.a >= 9) {
               if (this.d != var2.d) {
                  return false;
               }

               int var3 = this.c;
               int var4 = var2.c;

               for(int var5 = var3 + this.d; var3 < var5; ++var4) {
                  if (this.b[var3] != var2.b[var4]) {
                     return false;
                  }

                  ++var3;
               }
            }

            return true;
         }
      }
   }

   public int hashCode() {
      int var1 = 13 * this.a;
      if (this.a >= 9) {
         int var2 = this.c;

         for(int var3 = var2 + this.d; var2 < var3; ++var2) {
            var1 = 17 * (var1 + this.b[var2]);
         }
      }

      return var1;
   }

   public String toString() {
      return this.getDescriptor();
   }

   static {
      _clinit_();
      VOID_TYPE = new Type(0, (char[])null, 1443168256, 1);
      BOOLEAN_TYPE = new Type(1, (char[])null, 1509950721, 1);
      CHAR_TYPE = new Type(2, (char[])null, 1124075009, 1);
      BYTE_TYPE = new Type(3, (char[])null, 1107297537, 1);
      SHORT_TYPE = new Type(4, (char[])null, 1392510721, 1);
      INT_TYPE = new Type(5, (char[])null, 1224736769, 1);
      FLOAT_TYPE = new Type(6, (char[])null, 1174536705, 1);
      LONG_TYPE = new Type(7, (char[])null, 1241579778, 1);
      DOUBLE_TYPE = new Type(8, (char[])null, 1141048066, 1);
   }

   // $FF: synthetic method
   static void _clinit_() {
   }
}
