package me.kix.lotus.property;

public interface IProperty<T> {
   String getLabel();

   T getValue();

   void setValue(T var1);

   void setValue(String var1);
}
