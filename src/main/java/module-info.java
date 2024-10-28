module org.example.javafxdb_sql_shellcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    //error told me i needed to add this line
    opens org.example.javafxdb_sql_shellcode.db to javafx.base;

    opens org.example.javafxdb_sql_shellcode to javafx.fxml;
    exports org.example.javafxdb_sql_shellcode;
}