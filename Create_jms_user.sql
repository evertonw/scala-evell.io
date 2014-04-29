grant connect, resource, aq_administrator_role to jmsuser identified by jmsuser;
grant execute on sys.dbms_aqadm to jmsuser;
grant execute on sys.dbms_aq to jmsuser;
grant execute on sys.dbms_aqin to jmsuser;
grant execute on sys.dbms_aqjms to jmsuser;
exec dbms_aqadm.grant_system_privilege('ENQUEUE_ANY','jmsuser');
exec dbms_aqadm.grant_system_privilege('DEQUEUE_ANY','jmsuser');

