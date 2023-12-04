CREATE OR REPLACE FUNCTION before_delete_call_func()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$

BEGIN
    UPDATE client
    SET remain_minutes = remain_minutes + OLD.call_duration
    WHERE client_id = OLD.client_id;

    RETURN OLD;
END;
$$;
CREATE OR REPLACE TRIGGER before_delete_call
    BEFORE DELETE
    ON call
    FOR EACH ROW
EXECUTE FUNCTION before_delete_call_func();