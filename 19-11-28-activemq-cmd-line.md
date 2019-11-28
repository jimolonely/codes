
activemq的命令行操作。

# 总览

```s
Usage: ./activemq [--extdir <dir>] [task] [task-options] [task data]

Tasks:
    browse                   - Display selected messages in a specified destination.
    bstat                    - Performs a predefined query that displays useful statistics regarding the specified broker
    consumer                 - Receives messages from the broker
    create                   - Creates a runnable broker instance in the specified path.
    decrypt                  - Decrypts given text
    dstat                    - Performs a predefined query that displays useful tabular statistics regarding the specified destination type
    encrypt                  - Encrypts given text
    export                   - Exports a stopped brokers data files to an archive file
    list                     - Lists all available brokers in the specified JMX context
    producer                 - Sends messages to the broker
    purge                    - Delete selected destination's messages that matches the message selector
    query                    - Display selected broker component's attributes and statistics.
    start                    - Creates and starts a broker using a configuration file, or a broker URI.
    stop                     - Stops a running broker specified by the broker name.

Task Options (Options specific to each task):
    --extdir <dir>  - Add the jar files in the directory to the classpath.
    --version       - Display the version information.
    -h,-?,--help    - Display this help information. To display task specific help, use Main [task] -h,-?,--help

Task Data:
    - Information needed by each specific task.

```


# 写消息

```s
Usage: producer [OPTIONS]
Description: Demo producer that can be used to send messages to the broker
Options :
    [--brokerUrl                                   URL] - connection factory url; default ActiveMQConnectionFactory.DEFAULT_BROKER_URL
    [--user                                         ..] - connection user name
    [--password                                     ..] - connection password
    [--destination               queue://..|topic://..] - producer destination; default queue://TEST
    [--persistent                           true|false] - use persistent or non persistent messages; default true
    [--messageCount                                  N] - number of messages to send; default 1000
    [--sleep                                         N] - millisecond sleep period between sends or receives; default 0
    [--transactionBatchSize                          N] - use send transaction batches of size N; default 0, no jms transactions
    [--parallelThreads                               N] - number of threads to run in parallel; default 1
    [--msgTTL                                        N] - message TTL in milliseconds
    [--messageSize                                   N] - size in bytes of a BytesMessage; default 0, a simple TextMessage is used
    [--textMessageSize                               N] - size in bytes of a TextMessage, a Lorem ipsum demo TextMessage is used
    [--message                                      ..] - a text string to use as the message body
    [--payloadUrl                                  URL] - a url pointing to a document to use as the message body
    [--msgGroupID                                   ..] - JMS message group identifier
```

# 读消息

```s
Usage: consumer [OPTIONS]
Description: Demo consumer that can be used to receive messages to the broker
Options :
    [--brokerUrl                                   URL] - connection factory url; default ActiveMQConnectionFactory.DEFAULT_BROKER_URL
    [--user                                         ..] - connection user name
    [--password                                     ..] - connection password
    [--destination               queue://..|topic://..] - consumer destination; default queue://TEST
    [--messageCount                                  N] - number of messages to send; default 1000
    [--sleep                                         N] - millisecond sleep period between sends or receives; default 0
    [--ackMode     AUTO_ACKNOWLEDGE|CLIENT_ACKNOWLEDGE] - the type of message acknowledgement to use; default auto acknowledge
    [--batchSize                                     N] - batch size for transactions and client acknowledgment (default 10)
    [--durable                              true|false] - create durable topic
    [--clientId                                     ..] - connection client id; must be set for durable topics
    [--parallelThreads                               N] - number of threads to run in parallel; default 1
    [--bytesAsText                          true|false] - try to treat a BytesMessage as a text string
```

```s
activemq consumer --user xxx --password xxx --destination your-topic
```

