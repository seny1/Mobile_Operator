CREATE OR REPLACE FUNCTION before_delete_product_func()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$

BEGIN
    DELETE
    FROM "Check"
    WHERE product_id = OLD.product_id;
    RETURN OLD;
END;
$$;
CREATE OR REPLACE TRIGGER before_delete_product
    BEFORE DELETE
    ON product
    FOR EACH ROW
EXECUTE FUNCTION before_delete_product_func();