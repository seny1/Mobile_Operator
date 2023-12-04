CREATE OR REPLACE FUNCTION before_delete_client_func()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    DELETE
    FROM call
    WHERE client_id = OLD.client_id;

    DELETE
    FROM contract
    WHERE client_id = OLD.client_id;

    DELETE
    FROM "Check"
    WHERE client_id = OLD.client_id;

    DELETE
    FROM "Order"
    WHERE client_id = OLD.client_id;

    RETURN OLD;
END;
$$;
CREATE OR REPLACE TRIGGER before_delete_client
    BEFORE DELETE
    ON client
    FOR EACH ROW
EXECUTE FUNCTION before_delete_client_func();


CREATE OR REPLACE FUNCTION after_delete_client_func()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    DELETE
    FROM client_contact
    WHERE contact_id = NEW.contact_id;

    DELETE
    FROM client_passport
    WHERE passport_id = NEW.passport_id;

    RETURN NEW;
END;
$$;
CREATE OR REPLACE TRIGGER after_delete_client
    AFTER DELETE
    ON client
    FOR EACH ROW
EXECUTE FUNCTION after_delete_client_func();