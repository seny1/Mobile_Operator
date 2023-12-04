CREATE OR REPLACE FUNCTION before_delete_tariff_func()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$

BEGIN
    DELETE
    FROM contract
    WHERE plan_id = OLD.plan_id;
    RETURN OLD;
END;
$$;
CREATE OR REPLACE TRIGGER before_delete_tariff
    BEFORE DELETE
    ON tariff_plan
    FOR EACH ROW
EXECUTE FUNCTION before_delete_tariff_func();
