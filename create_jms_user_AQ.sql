exec dbms_aqadm.create_queue_table(queue_table=>'JMSDEMO_QUEUE_TABLE', queue_payload_type=>'sys.aq$_jms_text_message',multiple_consumers=>true);
exec dbms_aqadm.create_queue(queue_name=>'JMSDEMO_TOPIC', queue_table=>'JMSDEMO_QUEUE_TABLE');
exec dbms_aqadm.start_queue(queue_name=>'JMSDEMO_TOPIC');
commit;

