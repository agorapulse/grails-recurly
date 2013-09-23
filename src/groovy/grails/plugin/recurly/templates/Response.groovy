package grails.plugin.recurly.templates

class Response<T> {
    public T entity
    public String status
    public String message
    public Map errors

    public boolean success() {
        return this.status == '200' || this.status == '201'
    }

    public String toString(){
        return "RecurlyPlugin Response: Status: ${status}, Message: ${message}, EntityType: ${entity.class}, Errors: ${errors}"
    }
}