package com.esotericsoftware.asm;

final class Item {
   int a;
   int b;
   int c;
   long d;
   String g;
   String h;
   String i;
   int j;
   Item k;

   Item() {
   }

   Item(int var1) {
      this.a = var1;
   }

   Item(int var1, Item var2) {
      this.a = var1;
      this.b = var2.b;
      this.c = var2.c;
      this.d = var2.d;
      this.g = var2.g;
      this.h = var2.h;
      this.i = var2.i;
      this.j = var2.j;
   }

   void a(int var1) {
      this.b = 3;
      this.c = var1;
      this.j = Integer.MAX_VALUE & this.b + var1;
   }

   void a(long var1) {
      this.b = 5;
      this.d = var1;
      this.j = Integer.MAX_VALUE & this.b + (int)var1;
   }

   void a(float var1) {
      this.b = 4;
      this.c = Float.floatToRawIntBits(var1);
      this.j = Integer.MAX_VALUE & this.b + (int)var1;
   }

   void a(double var1) {
      this.b = 6;
      this.d = Double.doubleToRawLongBits(var1);
      this.j = Integer.MAX_VALUE & this.b + (int)var1;
   }

   void a(int var1, String var2, String var3, String var4) {
      this.b = var1;
      this.g = var2;
      this.h = var3;
      this.i = var4;
      switch(var1) {
      case 7:
         this.c = 0;
      case 1:
      case 8:
      case 16:
      case 30:
         this.j = Integer.MAX_VALUE & var1 + var2.hashCode();
         return;
      case 12:
         this.j = Integer.MAX_VALUE & var1 + var2.hashCode() * var3.hashCode();
         return;
      default:
         this.j = Integer.MAX_VALUE & var1 + var2.hashCode() * var3.hashCode() * var4.hashCode();
      }
   }

   void a(String var1, String var2, int var3) {
      this.b = 18;
      this.d = (long)var3;
      this.g = var1;
      this.h = var2;
      this.j = Integer.MAX_VALUE & 18 + var3 * this.g.hashCode() * this.h.hashCode();
   }

   void a(int var1, int var2) {
      this.b = 33;
      this.c = var1;
      this.j = var2;
   }

   boolean a(Item var1) {
      switch(this.b) {
      case 1:
      case 7:
      case 8:
      case 16:
      case 30:
         return var1.g.equals(this.g);
      case 2:
      case 9:
      case 10:
      case 11:
      case 13:
      case 14:
      case 15:
      case 17:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      default:
         return var1.g.equals(this.g) && var1.h.equals(this.h) && var1.i.equals(this.i);
      case 3:
      case 4:
         return var1.c == this.c;
      case 5:
      case 6:
      case 32:
         return var1.d == this.d;
      case 12:
         return var1.g.equals(this.g) && var1.h.equals(this.h);
      case 18:
         return var1.d == this.d && var1.g.equals(this.g) && var1.h.equals(this.h);
      case 31:
         return var1.c == this.c && var1.g.equals(this.g);
      }
   }
}
