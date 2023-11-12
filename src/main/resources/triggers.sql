CREATE OR REPLACE FUNCTION insert_in_client_contact_func()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    IF NEW.type != 'Рабочий' AND NEW.type != 'Домашний' AND NEW.type != 'Мобильный'
    THEN
        NEW.type = 'Мобильный';
    END IF;
    RETURN NEW;
END;
$$;
CREATE TRIGGER insert_in_client_contact
    BEFORE INSERT
    ON client_contact
    FOR EACH ROW
EXECUTE FUNCTION insert_in_client_contact_func();



CREATE OR REPLACE FUNCTION insert_product()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    IF NEW.price > 500000
    THEN
        NEW.price = 500000;
        NEW.product_description = 'ПРОВЕРИТЬ ЦЕНУ!';
    END IF;
    RETURN NEW;
END;
$$;
CREATE TRIGGER insert_product
    BEFORE INSERT
    ON product
    FOR EACH ROW
EXECUTE FUNCTION insert_product();



CREATE OR REPLACE FUNCTION check_contract_func()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
BEGIN
    IF NEW.date > date(now())
    THEN
        NEW.date = date(now());
        RETURN NEW;
    END IF;
END;
$$;
CREATE TRIGGER check_contract
    BEFORE INSERT
    ON contract
    FOR EACH ROW
EXECUTE FUNCTION check_contract_func();



CREATE OR REPLACE FUNCTION validate_number_func()
    RETURNS TRIGGER
    LANGUAGE plpgsql AS
$$
DECLARE
    regex VARCHAR = '^\+7\d{10}$';
BEGIN
    IF NEW.number NOT LIKE regex
    THEN
        RAISE EXCEPTION 'Invalid number!';
    END IF;
    INSERT INTO client_contact (number, type)
    VALUES (NEW.number, NEW.type);
    RETURN NEW;
END;
$$;
CREATE TRIGGER validate_number
    BEFORE INSERT
    ON client_contact
    FOR EACH ROW
EXECUTE FUNCTION validate_number_func();



CREATE OR REPLACE FUNCTION price_in_check_func()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
DECLARE
    price DOUBLE PRECISION = (SELECT price
                              FROM product
                              WHERE product_id = NEW.product_id);
    count INT = NEW.product_count;
BEGIN
    RAISE NOTICE '(%)', count * price;
    RETURN NEW;
END
$$;
CREATE TRIGGER price_in_check
BEFORE INSERT ON "Check"
FOR EACH ROW
EXECUTE FUNCTION price_in_check_func();