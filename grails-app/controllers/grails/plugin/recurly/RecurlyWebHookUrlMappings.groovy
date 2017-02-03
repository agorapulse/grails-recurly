package grails.plugin.recurly

class RecurlyWebHookUrlMappings {

	static mappings = {

        // Recurly WebHook
        "/recurlyWebHook"(controller: 'recurlyWebHook')

    }
}