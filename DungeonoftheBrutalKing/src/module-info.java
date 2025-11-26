module DungeonoftheBrutalKing  {
    requires java.desktop;
	requires java.management;
	requires org.junit.jupiter.api;
    requires jlayer; // This is correct for automatic modules, but the warning will persist unless the JAR is renamed or a module-info.class is added to the JAR.

}