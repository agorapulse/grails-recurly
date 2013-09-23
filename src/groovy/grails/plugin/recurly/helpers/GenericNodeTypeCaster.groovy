package grails.plugin.recurly.helpers

class GenericNodeTypeCaster{

    public String convertToCompatibleString(Object delegate) {
        String convertedString = null
        if (delegate != null) {
            convertedString = delegate as String
        }
        return convertedString
    }

    public Integer convertNodeToInteger(Object node) {
        if (node == null) {
            return 0
        }
        String nodeString = node
        Integer convertedInt
        if (nodeString == "") {
            convertedInt = 0
        } else {
            try {
                convertedInt = (nodeString as Integer)
            } catch (Exception e) {
                convertedInt = 0
            }
        }
        return convertedInt
    }
}
