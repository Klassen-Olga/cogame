
package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return message by id=1"
    request {
        method 'GET'
        url '/messages/1'
    }
    response {
        status 200
        body([
                id       : value(producer(regex('[A-Za-z0-9]+'))),
                userId   : value(producer(regex('[A-Za-z0-9]+'))),
                userName : value(producer(regex('[A-Za-z0-9]+'))),
                eventId  : value(producer(regex('[A-Za-z0-9]+'))),
                text     : value(producer(regex('[A-Za-z0-9._ ]+'))),
                createdAt: value(producer(regex('[A-Za-z0-9,:-]+')))

        ])

    }
}
