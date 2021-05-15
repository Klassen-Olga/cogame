package contracts

import org.springframework.cloud.contract.spec.Contract
/*
* Describes the message behaviour on GET request from the event-service
* */
Contract.make {
    description "Should return messages with id 21 and 22"
    request {
        method 'GET'
        url '/events/20/messages'
    }
    response {
        status 200
        body([[
                      id       : value(producer('21')),
                      userId   : value(producer('21')),
                      userName : value(producer("Dom")),
                      eventId  : value(producer('20')),
                      text     : value(producer("Great Event, Dom")),
                      createdAt: value(producer("2023-01-01T14:00"))],


              [id       : value(producer('22')),
               userId   : value(producer('22')),
               userName : value(producer("Bom")),
               eventId  : value(producer('20')),
               text     : value(producer("Great Event, Bom")),
               createdAt: value(producer("2023-01-01T13:00"))]
        ])
        headers {
            header('Content-Type': value(
                    producer('application/json'),
                    consumer('application/json'))
            )
        }

    }
}
