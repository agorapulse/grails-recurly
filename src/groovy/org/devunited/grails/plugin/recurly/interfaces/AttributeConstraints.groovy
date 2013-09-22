package org.devunited.grails.plugin.recurly.interfaces

public interface AttributeConstraints {
    public static final Boolean OPTIONAL_FIELD = true
    public static final Boolean REQUIRED_FIELD = false
    public static final Boolean CAN_BE_BLANK = true
    public static final Boolean CAN_NOT_BE_BLANK = false

    public static final Integer MAX_SIZE_2 = 2
    public static final Integer MAX_SIZE_4 = 4
    public static final Integer MAX_SIZE_10 = 10
    public static final Integer MAX_SIZE_16 = 16
    public static final Integer MAX_SIZE_20 = 20
    public static final Integer MAX_SIZE_32 = 32
    public static final Integer MAX_SIZE_50 = 50
    public static final Integer MAX_SIZE_255 = 255
    public static final Integer MAX_SIZE_TEXT = 65000

    public static final Integer MIN_SIZE_1 = 1
    public static final Integer MIN_SIZE_3 = 3
    public static final Integer MIN_SIZE_4 = 4
    public static final Integer MIN_SIZE_15 = 15


    public Map<String, String> errors()



    public Boolean validate()



    public void checkConstraints()



    public String violateConstraints(String value, Integer maxSize, Boolean canBeNull, Boolean canBeBlank, Integer minSize)



    public void checkProperty(String property, Integer maxSize, Boolean canBeNull, Boolean canBeBlank, Integer minSize)



    public void findErrors()
}