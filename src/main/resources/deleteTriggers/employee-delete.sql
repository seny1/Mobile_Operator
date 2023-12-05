CREATE
    OR REPLACE FUNCTION after_delete_employee_func()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    DELETE
    FROM employee_contact
    WHERE contact_id = NEW.contact_id;

    DELETE
    FROM employee_passport
    WHERE passport_id = NEW.passport_id;

    RETURN NEW;
END;
$$;
CREATE
    OR REPLACE TRIGGER after_delete_employee
    AFTER DELETE
    ON employee
    FOR EACH ROW
EXECUTE FUNCTION after_delete_employee_func();


CREATE OR REPLACE FUNCTION before_delete_employee_func()
RETURNS TRIGGER
LANGUAGE plpgsql AS
$$
    DECLARE free_master INTEGER;
    BEGIN
        SELECT master INTO free_master
        FROM check_free_masters();

        UPDATE "Order"
        SET employee_id = free_master
        WHERE employee_id = OLD.employee_id;

        RETURN OLD;
    END;
$$;
CREATE OR REPLACE TRIGGER before_delete_employee
BEFORE DELETE ON employee
FOR EACH ROW
EXECUTE FUNCTION before_delete_employee_func();