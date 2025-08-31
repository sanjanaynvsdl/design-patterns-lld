package RestrictToOneObject;

import javax.sound.midi.SysexMessage;

public class Main {
   public static void main(String[] args) {
      // goal - is to create a class where only one single object can be created.

      // DB Connection step-1 creating object in the attribute,
      DBConnection db1 = DBConnection.getInstance();
      DBConnection db2 = DBConnection.getInstance();
      // Static methods belong to the CLASS, not the object

      System.out.println(db1);
      System.out.println(db2);
      // prints same address,

      if (db1 == db2) {
         System.out.println("Yes!! Same address");
      } else {
         System.out.println("Different objects are being created!");
      }

      System.out.println(db1.getType());
      System.out.println(DBConnection.getType());

      String callingNOnStaticMethod = db1.getTypeNonstatic();
      String callingStaticMethod = db1.getType();
      // warnign - just java telling us to use better coding style

      System.out.println("Static Method type : " + callingStaticMethod);
      System.out.println("Non-Static Method type : " + callingNOnStaticMethod);
      // insight: Once you get the object reference from getInstance(), you
      // can call both static AND instance methods on it!

      System.out.println("--------------creating obj whenever it's needed avoiding memory wastage!---");

      DBConnectionLazy dbLazy1 = DBConnectionLazy.getInstance3();
      DBConnectionLazy dbLazy2 = DBConnectionLazy.getInstance3();
      if (dbLazy1 == dbLazy2) {
         System.out.println("Same address - " + dbLazy1);
      } else {
         System.out.println("something is wrong!" + dbLazy1 + dbLazy2);
      }

      System.out.println("--------------- enum implemnetation");
      DBConnectionEnum dbEnum1 = DBConnectionEnum.INSTANCE;
      DBConnectionEnum dbEnum2 = DBConnectionEnum.INSTANCE;

      if(dbEnum1==dbEnum2) {
         System.out.println("Same address!");
      }

      String type00= dbEnum1.getType();
      System.out.println("Type of enum "+type00);

      System.out.println("----------- inner class implementation!");
      DBConnectionInnerClass dbInnercls1 = DBConnectionInnerClass.getInstance();
      DBConnectionInnerClass dbInnercls2 = DBConnectionInnerClass.getInstance();

      if(dbInnercls1==dbInnercls2) {
         System.out.println("same address!"+dbInnercls1);
      }
      
   }



}