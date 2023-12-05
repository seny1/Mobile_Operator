CREATE OR REPLACE FUNCTION before_delete_service_func()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    DELETE
    FROM "Order"
    WHERE service_id = OLD.service_id;
    RETURN OLD;
END;
$$;
CREATE OR REPLACE TRIGGER before_delete_service
    BEFORE DELETE
    ON extra_service
    FOR EACH ROW
EXECUTE FUNCTION before_delete_service_func();