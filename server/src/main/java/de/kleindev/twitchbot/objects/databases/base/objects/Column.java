package de.kleindev.twitchbot.objects.databases.base.objects;

public class Column {
    private String name;
    private boolean primary = false;
    private boolean unique = false;
    private boolean permitNull = false;
    private String comment;
    private ColumnType type;
    private Long length;
    private String standartFill;
    private boolean isAutoFill = false;

    public Column(String name, ColumnType columnType){
        this.name = name;
        this.type = columnType;
    }

    public Column setUnique(boolean unique) {
        this.unique = unique;
        return this;
    }

    public Column setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    public Column setPermitNull(boolean permitNull) {
        this.permitNull = permitNull;
        return this;
    }

    public Column setLength(long length) {
        this.length = length;
        return this;
    }

    public Column setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Column setStandartFill(String standartFill) {
        this.isAutoFill = true;
        this.standartFill = standartFill;
        return this;
    }

    public String parseToSQL(){
        StringBuilder sb = new StringBuilder();

        //Name
        sb.append("`").append(name).append("`");

        //Type and length
        if(length != null)  sb.append(" ").append(type.name()).append("(").append(length).append(")");
        else  sb.append(" ").append(type.name());

        //Permit null
        if (permitNull) sb.append(" NULL").append(" ");
        else sb.append(" NOT NULL").append(" ");

        //Default value
        if(isAutoFill) {
            if(standartFill == null)
                sb.append(" DEFAULT NULL");
            else sb.append(" DEFAULT '").append(standartFill).append("' ");
        }

        //Comment
        if(comment != null)
            sb.append(" COMMENT '").append(comment).append("' ");

        //Primary key
        if(primary)
            sb.append(" PRIMARY KEY");

        //Is unique
        if(unique)
            sb.append(" UNIQUE ");

        return sb.toString();
    }
}
