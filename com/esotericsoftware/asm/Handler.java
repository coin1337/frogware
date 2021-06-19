package com.esotericsoftware.asm;

class Handler {
   Label a;
   Label b;
   Label c;
   String d;
   int e;
   Handler f;

   static Handler a(Handler var0, Label var1, Label var2) {
      if (var0 == null) {
         return null;
      } else {
         var0.f = a(var0.f, var1, var2);
         int var3 = var0.a.c;
         int var4 = var0.b.c;
         int var5 = var1.c;
         int var6 = var2 == null ? Integer.MAX_VALUE : var2.c;
         if (var5 < var4 && var6 > var3) {
            if (var5 <= var3) {
               if (var6 >= var4) {
                  var0 = var0.f;
               } else {
                  var0.a = var2;
               }
            } else if (var6 >= var4) {
               var0.b = var1;
            } else {
               Handler var7 = new Handler();
               var7.a = var2;
               var7.b = var0.b;
               var7.c = var0.c;
               var7.d = var0.d;
               var7.e = var0.e;
               var7.f = var0.f;
               var0.b = var1;
               var0.f = var7;
            }
         }

         return var0;
      }
   }
}
