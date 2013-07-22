/**
 * Kamhoops events
 *
 * Provides type-safe events that may be broadcasted/emitted from controllers or services
 */
angular.module('Kamhoops.events', [])
    .constant("events", {
        USER_ADDED_TO_ACCOUNT: "event:userAddedToAccount"
    })
;