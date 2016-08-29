package grails.plugin.recurly.helpers

import grails.plugin.recurly.interfaces.AttributeConstraints

abstract class ConstraintsValidator extends GenericNodeTypeCaster implements AttributeConstraints {

    public Map<String, String> propertiesWithErrors = [:]
    public Object beanUnderProcess
    public String beanClass

    Map<String, String> errors() {
        findErrors()
        return propertiesWithErrors
    }

    Boolean validate() {
        findErrors()
        return propertiesWithErrors.isEmpty()
    }

    String violateConstraints(String value, Integer maxSize, Boolean canBeNull, Boolean canBeBlank, Integer minSize) {
        String violations = null

        if (value || value?.size() == 0) {
            if (value.size() > maxSize) {
                violations = " ~The Length Of This Field Can Not Be More Than ${maxSize}~ "
            }
            if (value.size() == 0 && !canBeBlank) {
                violations = violations ?: ""
                violations += " ~This Field Can not Be Blank~ "
            }
            if (value.size() < minSize && !canBeBlank) {
                violations = violations ?: ""
                violations += " ~The Length Of This Field Can Not Be Less Than ${minSize}~ "
            }
        } else {
            if (!canBeNull) {
                violations = " ~This Field Can Not Be NULL~ "
            }
        }
        return violations
    }

    void checkProperty(String property, Integer maxSize, Boolean canBeNull, Boolean canBeBlank, Integer minSize = 0) {
        String errorString = violateConstraints(convertToCompatibleString(beanUnderProcess."${property}"), maxSize, canBeNull, canBeBlank, minSize)
        if (errorString) {
            propertiesWithErrors.put(property, errorString)
        }
    }
}