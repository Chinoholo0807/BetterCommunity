DELIMITER |
DROP PROCEDURE IF EXISTS e_exspiration |
CREATE PROCEDURE e_exspiration()
    BEGIN
            
            
			update requests set state=1
			where (now()>=end_time and state=0);
            
            update responses
            set state = 2
            where exists(SELECT * from (select* from requests 
										where now()>=requests.end_time and requests.state=0 and requests.id = responses.request_id) as tmp)
										and  (responses.state = 0);
            
    END
|

SET GLOBAL event_scheduler = 1; 
CREATE EVENT IF NOT EXISTS e_minute
ON SCHEDULE EVERY 1 MINUTE
ON COMPLETION PRESERVE  
DO CALL e_exspiration();




